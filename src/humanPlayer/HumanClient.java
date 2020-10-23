package humanPlayer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import gameActions.CloseListener;

/**
 * HumanClien contain all methods needed for the GUI to be created
 *version: 1.0
 *release : 25/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class HumanClient {


	private static JScrollPane scroll;
	private static JLabel[] labels= new JLabel[25];
	private static ImageIcon humanIcon;
	private static ImageIcon floorIcon;
	private static ImageIcon goldIcon;
	private static ImageIcon wallIcon;
	private static JTextArea textArea;
	private static ImageIcon exitIcon;
	private static JTextArea outputText;
	private static JComboBox combo1;
	private static JComboBox combo2;
	JPopupMenu popup;
	
	private Run myRun;	


	/**
	 * constructor of class
	 *
	 */
	public HumanClient(Run myRun) {
		this.myRun=myRun;
		displayUsernameFrame();
		displayGame();
	}
	
	/**
	 * appends text to text area for the commands answer from the server
	 *
	 */
	public void appendToTextArea(String message) {
		textArea.append(message);
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	
	/**
	 * appends text to text area for the chat between the players
	 *
	 */
	public void appendToOutput(String message) {
		outputText.append(message);
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	
	/**append to combobox
	 * 
	 */
////////----THIS ADDS TOO MANY USERNAMES WHEN you press button again even when i clearALL.
	public void addToComboBox(ArrayList<String> message) {
//		String[] splitInput=message.trim().split(" ");
		
		for(int i=0; i<message.size();i++) {
			combo1.addItem(message.get(i));
		}
//		combo1.addItem(message.toArray());
//		for(int i =0; i<splitInput.length-1;i++) {
////			System.out.println(splitInput[i]);
//			combo1.addItem(splitInput[i]);
//		}
	}
	
	@SuppressWarnings("unchecked")
	public void addToJoinGame(ArrayList<String> message) {
//		String[] splitInput=message.trim().split(" ");
		System.out.println("Human Client FUCK ME");
		String newMessage="";
		for(int i=0; i<message.size();i++) {
			newMessage=message.get(i);
			combo2.addItem(newMessage) ;
		}
		System.out.println("Human Client FUCK ME 2");
	}

	/**
	 * display the Gui on screen when calles and starts the server.
	 *
	 */
	private void displayGame()
	{
		
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				Images();
//				displayGUI();
//				iniGui();
//				displayUsernameFrame();
				myRun.startServer();
			}
		});
	}

	/**
	 * method which imports the images needed replace the character in map
	 *
	 */
	private void Images()
	{

		java.net.URL humanUrl = HumanClient.class.getResource("/aPlayer.png");
		assert(humanUrl != null);
		ImageIcon human =new ImageIcon(humanUrl);
		Image image = human.getImage(); // transform it 
		Image newimg = image.getScaledInstance(160, 110,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		humanIcon = new ImageIcon(newimg);  // transform it back


		java.net.URL floorUrl = HumanClient.class.getResource("/floor.png");
		assert(floorUrl != null);
		ImageIcon floor =new ImageIcon(floorUrl);
		Image image1 = floor.getImage(); // transform it 
		Image newimg1 = image1.getScaledInstance(160, 110,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		floorIcon = new ImageIcon(newimg1);  // transform it back
		//assert(floor != null);

		java.net.URL goldUrl = HumanClient.class.getResource("/gold.png");
		assert(goldUrl != null);
		ImageIcon Gold =new ImageIcon(goldUrl);
		Image image12 = Gold.getImage(); // transform it 
		Image newimg12 = image12.getScaledInstance(160, 110,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		goldIcon = new ImageIcon(newimg12);  // transform it back
		

		java.net.URL wallUrl = HumanClient.class.getResource("/wall.png");
		assert(wallUrl != null);
		ImageIcon wall =new ImageIcon(wallUrl);
		Image image123 = wall.getImage(); // transform it 
		Image newimg123 = image123.getScaledInstance(160, 110,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		wallIcon = new ImageIcon(newimg123);  // transform it back
//		assert(wall != null);

		java.net.URL exitUrl = HumanClient.class.getResource("/exit.png");
		assert(exitUrl != null);
		ImageIcon exit =new ImageIcon(exitUrl);
		Image image1234 = exit.getImage(); // transform it 
		Image newimg1234 = image1234.getScaledInstance(160, 110,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		exitIcon = new ImageIcon(newimg1234);  // transform it back
		//assert(floor != null);

	}

	/**
	 * method which updates tiles of the map based on the char with certain image icon
	 *
	 */
	public void updateLook(String look)
	{

		for (int k = 0 ; k < look.length() ; k++) {
			char c = look.charAt(k);

			if( c=='.')
			{
				labels[k].setIcon(floorIcon);
			}
			else if( c=='G')
			{
				labels[k].setIcon(goldIcon);
			}
			else if( c=='P')
			{
				labels[k].setIcon(humanIcon);
			}
			else if(c=='E')
			{
				labels[k].setIcon(exitIcon);
			}
			else
			{
				labels[k].setIcon(wallIcon);
			}
		}
	}

	/**
	 * method which calls the thread for the look which will allow the look to autorefresh
	 *
	 */
	private void startLooking(){

		new lookThread(myRun).start();
	}

	//	private void startGeneratingUsernames(){
	//
	//		new usernameThread(myRun).start();
	//	}

	public void createGameFrame() {
		JFrame frame = new JFrame("Front Page");
		frame.setPreferredSize(new Dimension(1000,500));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
        frame.setLocation(0, 0);
        
        JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.BLACK);						// setting color
		frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);	// where to place in in the frame
		
		JPanel midPanelC = new JPanel();
		midPanelC.setBackground(Color.WHITE);


		JPanel midPanelL = new JPanel();
		midPanelL.setBackground(Color.BLUE);


		JPanel midPanelR = new JPanel();
		midPanelR.setBackground(Color.WHITE);
	    
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.YELLOW);

		// add panel to frame
		frame.getContentPane().add(midPanelC, BorderLayout.CENTER);
//		frame.getContentPane().add(midPanelL, BorderLayout.LINE_START);
//		frame.getContentPane().add(midPanelR, BorderLayout.LINE_END);
//		frame.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
		
		
		
		JButton startGame = new JButton("Create Game");
		startGame.setPreferredSize(new Dimension(100,100));
		midPanelC.add(startGame);
		
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				topPanel.setToolTipText("");
				frame.setVisible(false);
				myRun.sendToServer("CreateGame");
				iniGui();
				startLooking();
			}
		});
		
		JButton joinGame = new JButton("Join Game");
		combo2 = new JComboBox();
		joinGame.setPreferredSize(new Dimension(100,100));
		midPanelC.add(joinGame);
		midPanelC.add(combo2);
/////----------------HERE IS THE POPUP MENu-----------------------------------------------------------		
//		popup = new JPopupMenu();
		joinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combo2.removeAllItems();
				topPanel.setToolTipText("");
				System.out.println("HumanClient "+"JoinGame2");
//				myRun.sendToServer("JoinGame");

			}
		});
		
		JButton acceptGame = new JButton("Enter the dungeon");
		midPanelC.add(acceptGame);
		acceptGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topPanel.setToolTipText("");
				String gameSelection = combo2.getSelectedItem().toString();	
				myRun.sendToServer("Selecting"+" "+gameSelection);
				frame.setVisible(false);
				iniGui();
				startLooking();

			}
		});

		


		
//		joinGame.addMouseListener(new MouseAdapter() {
//	            public void mousePressed(MouseEvent e) {
//	                popup.show(e.getComponent(), e.getX(), e.getY());
//	            }
//	        });
		
		frame.pack();
	}
	private void displayUsernameFrame() {
		JFrame frame = new JFrame("Front Page");
		frame.setPreferredSize(new Dimension(1300, 800));

		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
        frame.setLocation(0, 0);
        
        JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.BLACK);						// setting color
		frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);	// where to place in in the frame
		
		JPanel midPanelC = new JPanel();
		midPanelC.setBackground(Color.WHITE);


		JPanel midPanelL = new JPanel();
		midPanelL.setBackground(Color.BLUE);


		JPanel midPanelR = new JPanel();
		midPanelR.setBackground(Color.WHITE);
	    
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.YELLOW);

		// add panel to frame
		frame.getContentPane().add(midPanelC, BorderLayout.CENTER);
//		frame.getContentPane().add(midPanelL, BorderLayout.LINE_START);
//		frame.getContentPane().add(midPanelR, BorderLayout.LINE_END);
//		frame.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
		
		
		
		JButton bUsername = new JButton("Set Username:");
		bUsername.setPreferredSize(new Dimension(150, 20));
		JTextField userName = new JTextField("Enter_Username:");
		userName.setPreferredSize(new Dimension(150, 30));
		midPanelC.add(bUsername);
		midPanelC.add(userName);
		
		bUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				midPanelC.setToolTipText("");
				myRun.sendToServer("1");///Declares that it is a user and not a bot
				myRun.sendToServer(userName.getText());
				frame.setVisible(false);
				createGameFrame();
			
			}
		});
		
		
		frame.pack();
	}
	/**
	 * basic structure and button functionality of GUI is created
	 *
	 */
	private void iniGui() {

		/////////// set up window
		JFrame frame = new JFrame("Demo");
		frame.setPreferredSize(new Dimension(1300, 800));
//----------------Add confimartion for exit and if do send to server to quit to remove players thread
		Action ca = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        JFrame frame = (JFrame)e.getSource();
		        myRun.sendToServer("QUIT");
		    }
		};
		 
		CloseListener cl = new CloseListener(
				"Are you sure you want to exit the application",
			    "Exit Application",
			    ca
				);	
		frame.addWindowListener( cl );
		
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
        frame.setLocation(0, 0);
        
		
//-------------------GRID LAYOUTs-----------------------------------------------------------------		
		GridLayout lookPrint = new GridLayout(5, 5);
		GridLayout commands=new GridLayout(3,3);
		GridBagLayout bottom=new GridBagLayout();
		GridBagLayout right = new GridBagLayout();

		// new panels
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.BLACK);						// setting color
		topPanel.setPreferredSize(new Dimension(1300, 50));				// dimension of panel
		frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);	// where to place in in the frame
		
		JPanel midPanelC = new JPanel();
		midPanelC.setBackground(Color.LIGHT_GRAY);
		midPanelC.setPreferredSize(new Dimension(800, 550));
		midPanelC.setLayout(lookPrint);

		JPanel midPanelL = new JPanel();
		midPanelL.setBackground(Color.BLACK);
		midPanelL.setPreferredSize(new Dimension(150, 550));
		

		JPanel midPanelR = new JPanel();
		midPanelR.setBackground(Color.BLACK);
		midPanelR.setPreferredSize(new Dimension(350, 550));
		midPanelR.setLayout(right);
		
		JPanel panelR=new JPanel(new BorderLayout());
		panelR.setBorder(BorderFactory.createEtchedBorder());
		panelR.setBackground(Color.white);
		JPanel panelR2=new JPanel(new BorderLayout());
		panelR2.setBorder(BorderFactory.createEtchedBorder());
		panelR2.setBackground(Color.BLACK);
		JPanel panelR3=new JPanel(new BorderLayout());
		panelR3.setBorder(BorderFactory.createEtchedBorder());
		panelR3.setBackground(Color.white);
		
		//new GridBagConstraints(columnNumber, rowNumber, columnSpan, rowSpan, columnWeigth, rowWeigth, alignment, fillType, insets, padX, pady)
		midPanelR.add(panelR,  new GridBagConstraints(0, 0, 1, 1, 0.3, 0.7, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
		midPanelR.add(panelR2,  new GridBagConstraints(1, 0, 1, 2, 0.7, 0.7, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
		midPanelR.add(panelR3,  new GridBagConstraints(0, 1, 1, 1, 0.3, 0.2, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
	    
	    
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.BLACK);
		bottomPanel.setPreferredSize(new Dimension(1300, 200));
		bottomPanel.setLayout(bottom);
		
		JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setBackground(Color.white);
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setBorder(BorderFactory.createEtchedBorder());
        panel.setBackground(Color.blue);
        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.setBorder(BorderFactory.createEtchedBorder());
        panel3.setBackground(Color.BLACK);
        panel3.setLayout(commands);
        
        JPanel panel4 = new JPanel(new BorderLayout());
        panel4.setBorder(BorderFactory.createEtchedBorder());
        panel.setBackground(Color.BLACK);
//new GridBagConstraints(columnNumber, rowNumber, columnSpan, rowSpan, columnWeigth, rowWeigth, alignment, fillType, insets, padX, pady)
        bottomPanel.add(panel,  new GridBagConstraints(0, 0, 1, 2, 0.17, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
        bottomPanel.add(panel2,  new GridBagConstraints(1, 0, 1, 1, 0.73, 0.9, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
        bottomPanel.add(panel3,  new GridBagConstraints(2, 0, 1, 2, 0.10, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
        //next row
        bottomPanel.add(panel4,  new GridBagConstraints(1, 1, 1, 1, 0.73, 0.1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));

		// add panel to frame
		frame.getContentPane().add(midPanelC, BorderLayout.CENTER);
		frame.getContentPane().add(midPanelL, BorderLayout.LINE_START);
		frame.getContentPane().add(midPanelR, BorderLayout.LINE_END);
		frame.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);

//--------------------looping through the labels and addint them to the panel ----------------------------------------------
		for (int i = 0; i < 25; i++)		
		{   
			labels[i] = new JLabel("");
			midPanelC.add(labels[i]);
		}


//------------------------- Create huamns Buttons----------------------------------
		JButton bMoveN = new JButton("N");
		bMoveN.setPreferredSize(new Dimension(20, 20));
		JButton bMoveW = new JButton("W");
		bMoveW.setPreferredSize(new Dimension(20, 20));
		JButton bMoveE = new JButton("E");
		bMoveE.setPreferredSize(new Dimension(20, 20));
		JButton bMoveS = new JButton("S");
		bMoveS.setPreferredSize(new Dimension(20, 20));
		JButton bHello = new JButton("HELLO");
		bHello.setPreferredSize(new Dimension(20, 20));
		JButton bPickUp = new JButton("PICKUP");
		bPickUp.setPreferredSize(new Dimension(20, 20));
		JButton bQuit = new JButton("QUIT");
		bQuit.setPreferredSize(new Dimension(20, 20));
		JButton bShout = new JButton("SHOUT");							//creating more buttons
		bShout.setPreferredSize(new Dimension(20, 20));
		JButton bWhisper = new JButton("WHISPER");
		bWhisper.setPreferredSize(new Dimension(20, 20));

		// add buttons to pannel
		panel3.add(bQuit);
		panel3.add(bMoveN);
		panel3.add(bWhisper);
		panel3.add(bMoveW);
		panel3.add(bPickUp);
		panel3.add(bMoveE);
		panel3.add(bHello);
		panel3.add(bMoveS);
		panel3.add(bShout);


//-------------------------creating a text area and a scroll pane for the return of the commands----------------------
		textArea=new JTextArea(5,15);
		textArea.setEditable(false);
//---------------------needed text area otherwise client freezes-------------------------------------------------------		
		scroll = new JScrollPane (textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelR3.add(scroll);

//--------------chat of player------------------------------------------------------
		JTextField chatText= new JTextField("Chat");
		panel4.add(chatText);
		JButton button = new JButton("Send");
		panel4.add(button,BorderLayout.LINE_END);
		

		JButton showUsernames = new JButton("ShowUsernames");
		panel4.add(showUsernames,BorderLayout.LINE_END);
		combo1 = new JComboBox();
//		combo1.add("asdasd");
		panel4.add(combo1,BorderLayout.WEST);

		outputText= new JTextArea(40,40);
		outputText.setEditable(false);
		JScrollPane scroll1=new JScrollPane (outputText);
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		

		panel2.add(scroll1);
		
		chatText.setEnabled(false);

		
		
//-------------------------action listeners to determine what each button should do------------------------------------------------
		bQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				topPanel.setToolTipText("");
				myRun.sendToServer("QUIT");
			}
		});

		bMoveN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				topPanel.setToolTipText("");
				myRun.sendToServer("MOVE N");

			}
		});
		bMoveS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				topPanel.setToolTipText("");
				myRun.sendToServer("MOVE S");

			}
		});
		bMoveE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				topPanel.setToolTipText("");
				myRun.sendToServer("MOVE E");

			}
		});
		bMoveW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				topPanel.setToolTipText("");
				myRun.sendToServer("MOVE W");

			}
		});
		bPickUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				topPanel.setToolTipText("");
				myRun.sendToServer("PICKUP");

			}
		});
		bHello.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				topPanel.setToolTipText("");
				myRun.sendToServer("HELLO");

			}
		});
		bShout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				topPanel.setToolTipText("");
				myRun.sendToServer("SHOUT "+chatText.getText());
//				bottomPanel.revalidate();
//				bottomPanel.repaint();

			}

		});
		bWhisper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				topPanel.setToolTipText("");
				String[] splitInput=chatText.getText().trim().split(" ");
				if(splitInput.length>1) {
					myRun.sendToServer("WHISPER "+chatText.getText());
				}
				



			}

		});
		showUsernames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combo1.removeAllItems();
				myRun.sendToServer("USERNAMES");
//				System.out.println(DODServer.threadList.get(1).id);
			}
		});

//		bUsername.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//				midPanelC.setToolTipText("");
//				myRun.sendToServer("1");///Declares that it is a user and not a bot
//				myRun.sendToServer(InputText.getText());
//				topPanel.remove(bUsername);
//				topPanel.remove(InputText);
//				topPanel.revalidate();
//				topPanel.repaint();
//				bMoveN.setEnabled(true);
//				bMoveW.setEnabled(true);
//				bMoveE.setEnabled(true);
//				bMoveS.setEnabled(true);
//				bHello.setEnabled(true);
//				bPickUp.setEnabled(true);
//				bShout.setEnabled(true);
//				bWhisper.setEnabled(true);
//				bQuit.setEnabled(true);
//				chatText.setEnabled(true);
//
//				startLooking();
//				//				startGeneratingUsernames();
//				frame.getContentPane().remove(topPanel);
//			}
//			
//		});
		
		frame.pack();
		frame.setVisible(true);
	}

}


