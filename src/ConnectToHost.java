import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * ConnectToHost contains the GUI for the port number and hostname window for the client to connect to the sever
 *version: 1.0
 *release : 25/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class ConnectToHost {

	// Connect status constants
	final static int DISCONNECTED = 0;
	final static int BEGIN_CONNECT = 1;
	final static int CONNECTED = 2;

	// Various GUI components and info
	public static JFrame mainFrame;
	public static JTextArea chatText;
	public static JTextField chatLine;
	public static JLabel statusBar;
	public static JTextField ipField;
	public static JTextField portField;
	public static JRadioButton hostOption;
	public static JRadioButton guestOption;
	public static JButton connectButton;
	public static JButton disconnectButton;

	// Connection info
	public static String hostIP = "localhost";
	public static int port = 4000;
	public static int connectionStatus = DISCONNECTED;
	public static boolean isHost = true;

	private Run myRun;

	/**
	 * closes the window when called
	 *
	 */
	public void closeWindow() {
		mainFrame.dispose();
	}

	/**
	 * constructor of class
	 *
	 */
	public ConnectToHost(Run myRun){
		this.myRun=myRun;
		initGUI();
	}

	/**
	 * creates a Jpanel which contains  frames and buttons for the GUI
	 *
	 */
	private JPanel initOptiosPane()
	{
		JPanel pane = null;
		ActionAdapter buttonListener = null;

		// Create an options pane
		JPanel optionsPane = new JPanel(new GridLayout(4, 1));

		// IP address input
		pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pane.add(new JLabel("Host IP:"));
		ipField = new JTextField(10); ipField.setText(hostIP);
		ipField.setEditable(true);
		pane.add(ipField);
		optionsPane.add(pane);

		// Port input
		pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pane.add(new JLabel("Port:"));
		portField = new JTextField(10); portField.setEditable(true);
		portField.setText((new Integer(port)).toString());
		pane.add(portField);
		optionsPane.add(pane);

		// Connect/disconnect buttons
		JPanel buttonPane = new JPanel(new GridLayout(1, 2));
		buttonListener = new ActionAdapter() {
			public void actionPerformed(ActionEvent e) {
				// Request a connection initiation
				connectButton.setEnabled(false);
				connectionStatus = BEGIN_CONNECT;
				ipField.setEnabled(false);
				portField.setEnabled(false);
				statusBar.setText("Online");
				mainFrame.repaint();

				myRun.connect(ipField.getText(),portField.getText());

			}
		};
		connectButton = new JButton("Connect");
		connectButton.setActionCommand("connect");
		connectButton.addActionListener(buttonListener);
		connectButton.setEnabled(true);


		buttonPane.add(connectButton);
		optionsPane.add(buttonPane);

		return optionsPane;
	}

//	/**
//	 * arguments are set when you all this method
//	 *
//	 */
//	public void setArgs(String hostName, int portNumber){
//		ipField.setText(hostName);
//		portField.setText(""+portNumber);
//	}

	/**
	 * creates the connection window
	 *
	 */
	public void initGUI() {
		// Set up the status bar
		statusBar = new JLabel();
		statusBar.setText("Offline");


		// Set up the main pane
		JPanel mainPane = new JPanel(new BorderLayout());
		mainPane.add(statusBar, BorderLayout.SOUTH);
		mainPane.add(initOptiosPane(), BorderLayout.WEST);
		//mainPane.add(chatPane, BorderLayout.CENTER);

		// Set up the main frame
		mainFrame = new JFrame("Simple TCP Chat");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setContentPane(mainPane);
		mainFrame.setSize(mainFrame.getPreferredSize());
		mainFrame.setLocation(200, 200);
//		setArgs(hostName,portNumber);


		mainFrame.pack();
		mainFrame.setVisible(true);
	}

}