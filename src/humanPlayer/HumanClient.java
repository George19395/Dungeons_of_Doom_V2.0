package humanPlayer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import gameActions.BackgroundPanel;
import gameActions.CloseListener;

/**
 * HumanClien contain all methods needed for the GUI to be created
 *version: 1.0
 *release : 25/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class HumanClient {

	BufferedImage img;
	JFrame frame;

	private static JScrollPane scroll;
	private static JLabel[] labels= new JLabel[25];
	private static ImageIcon humanIcon;
	private static ImageIcon floorIcon;
	private static ImageIcon goldIcon;
	private static ImageIcon wallIcon;
	private static JTextArea textArea;
	private static ImageIcon exitIcon;
	private static ImageIcon lavaTileIcon;
	private static ImageIcon lavaIcon;
	private static ImageIcon initialPage;
	
	
	private static JTextArea outputText;
	private static JComboBox combo1=new JComboBox();
	private JComboBox combo2 =new JComboBox();
	JPopupMenu popup;
	JLabel jlabelGold = new JLabel("Gold Available: 4");//This label has to have 6words
	JLabel jlabelGold2 = new JLabel("Gold Required: 3");//This label has to have 6words
	JLabel jlabelGold3 = new JLabel("Gold Owned: 0");//This label has to have 6words
	JLabel jlabelGold4 = new JLabel("Game Code: ");//This label has to have 6words
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
	public void addToComboBox(ArrayList<String> messages) {
//		String[] splitInput=message.trim().split(" ");
		
		for(int i=0; i<messages.size();i++) {
			System.out.println(messages.size());
			combo1.addItem(messages.get(i).toString());
			
		}
//		combo1.addItem(message.toArray());
//		for(int i =0; i<splitInput.length-1;i++) {
////			System.out.println(splitInput[i]);
//			combo1.addItem(splitInput[i]);
//		}
	}
	

	public void addToJoinGame(String message) {
		String[] splitInput=message.trim().split(" ");
		System.out.println("Human Client FUCK ME");
		String newMessage="";
		combo2.removeAllItems();
		for(int i=1; i<splitInput.length;i++) {
			newMessage=splitInput[i];
			combo2.addItem(newMessage) ;
			System.out.println("Human Client add to join game"+newMessage);
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

		java.net.URL humanUrl = HumanClient.class.getResource("/aPlayer2.png");
		assert(humanUrl != null);
		ImageIcon human =new ImageIcon(humanUrl);
		Image image = human.getImage(); // transform it 
		Image newimg = image.getScaledInstance(750/5, 460/5,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		humanIcon = new ImageIcon(newimg);  // transform it back
		

		java.net.URL floorUrl = HumanClient.class.getResource("/walkTile.png");
		assert(floorUrl != null);
		ImageIcon floor =new ImageIcon(floorUrl);
		Image image1 = floor.getImage(); // transform it 
		Image newimg1 = image1.getScaledInstance(750/5, 460/5,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		floorIcon = new ImageIcon(newimg1);  // transform it back
		//assert(floor != null);

		java.net.URL goldUrl = HumanClient.class.getResource("/Gold2.png");
		assert(goldUrl != null);
		ImageIcon Gold =new ImageIcon(goldUrl);
		Image image12 = Gold.getImage(); // transform it 
		Image newimg12 = image12.getScaledInstance(750/5, 460/5,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		goldIcon = new ImageIcon(newimg12);  // transform it back
		

		java.net.URL wallUrl = HumanClient.class.getResource("/Wall.PNG");
		assert(wallUrl != null);
		ImageIcon wall =new ImageIcon(wallUrl);
		Image image123 = wall.getImage(); // transform it 
		Image newimg123 = image123.getScaledInstance(750/5, 460/5,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		wallIcon = new ImageIcon(newimg123);  // transform it back
//		assert(wall != null);

		java.net.URL exitUrl = HumanClient.class.getResource("/Exit2.png");
		assert(exitUrl != null);
		ImageIcon exit =new ImageIcon(exitUrl);
		Image image1234 = exit.getImage(); // transform it 
		Image newimg1234 = image1234.getScaledInstance(750/5, 460/5,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		exitIcon = new ImageIcon(newimg1234);  // transform it back
		//assert(floor != null);

		java.net.URL lavaUrl = HumanClient.class.getResource("/Lava.png");
		assert(lavaUrl != null);
		ImageIcon lava =new ImageIcon(lavaUrl);
		Image image12345 = lava.getImage(); // transform it 
		Image newimg12345 = image12345.getScaledInstance(750/5, 460/5,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		lavaIcon = new ImageIcon(newimg12345);  // transform it back
		//assert(floor != null);	

		java.net.URL lavatileUrl = HumanClient.class.getResource("/wallTile2.png");
		assert(lavatileUrl != null);
		ImageIcon lavaTile =new ImageIcon(lavatileUrl);
		Image image123456 = lavaTile.getImage(); // transform it 
		Image newimg123456 = image123456.getScaledInstance(750/5, 460/5,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		lavaTileIcon = new ImageIcon(newimg123456);  // transform it back
		//assert(floor != null);	
		
		java.net.URL asd = HumanClient.class.getResource("/Initial.png");
		assert(asd != null);
		ImageIcon huma =new ImageIcon(asd);
		Image imagea = huma.getImage(); // transform it 
		Image newimga = imagea.getScaledInstance(1000, 500,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		initialPage = new ImageIcon(newimga);  // transform it back
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
			else if(c=='#')
			{
				labels[k].setIcon(lavaTileIcon);
			}
			else {
				labels[k].setIcon(lavaIcon);
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

	public void createGameFrame() {
		
		frame = new JFrame("Front Page");
		frame.setPreferredSize(new Dimension(1000,500));
//		frame.add(new JLabel(initialPage));
		
		Action ca = new AbstractAction()
		{
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
        
        JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.BLACK);						// setting color
		frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);	// where to place in in the frame
		
//		JPanel midPanelC = new JPanel();
		BackgroundPanel midPanelC = new BackgroundPanel();
//		midPanelC.setLayout();
//		midPanelC.add(new GridBagConstraints());
//		midPanelC.setBackground(Color.WHITE);
//		ImagePanel midPanelC = new ImagePanel(initialImage);
//		midPanelC.isOptimizedDrawingEnabled();


		JPanel midPanelL = new JPanel();
		midPanelL.setBackground(Color.BLUE);


		JPanel midPanelR = new JPanel();
		midPanelR.setBackground(Color.WHITE);
	    
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.YELLOW);
		
		JButton startGame = new JButton("Create Game");
		startGame.setPreferredSize(new Dimension(200,30));
		startGame.setFont(new Font("Arial", Font.PLAIN, 15));
//		startGame.setPreferredSize(new Dimension(200, 50));
//		startGame.setBackground(Color.BLACK);
//		startGame.setForeground(Color.WHITE);
		startGame.setFocusPainted( false );
		startGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		midPanelC.add(startGame);

		frame.getContentPane().add(midPanelC, BorderLayout.CENTER);

		
		
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				topPanel.setToolTipText("");
				frame.setVisible(false);
				myRun.sendToServer("CreateGame");
				iniGui();
				startLooking();
				myRun.sendToServer("ALLGOLD");
				myRun.sendToServer("GAMECODE");
			}
		});
		
		JButton joinGame = new JButton("Join Game");
//		combo2 = new JComboBox();
//		combo2.addItem("HEY");
		joinGame.setPreferredSize(new Dimension(200,30));
		joinGame.setFont(new Font("Arial", Font.PLAIN, 15));
//		startGame.setPreferredSize(new Dimension(200, 50));
//		startGame.setBackground(Color.BLACK);
//		startGame.setForeground(Color.WHITE);
		joinGame.setFocusPainted( false );
		joinGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		midPanelC.add(joinGame);
		midPanelC.add(combo2);
/////----------------HERE IS THE POPUP MENu-----------------------------------------------------------		
//		popup = new JPopupMenu();
		joinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				combo2.removeAllItems();
				topPanel.setToolTipText("");
				System.out.println("HumanClient "+"JoinGame2");
//				String gameSelection = "000123";
				System.out.println("jComboBox1 : " + combo2);                   
				

				if(combo2.getItemCount()==0) {
					System.out.println("HELLO");
					JOptionPane.showMessageDialog(frame, "No Available Games to Join, Create one yourself");
					
				}
				else {
					String gameSelection= combo2.getSelectedItem().toString();
					System.out.println("support : " + gameSelection);
					
					myRun.sendToServer("JoinGame "+gameSelection);///////////THIS IS THE PROBLEM
					frame.setVisible(false);
					iniGui();
					startLooking();
					myRun.sendToServer("ALLGOLD");
					myRun.sendToServer("GAMECODE");
				}

			}
		});
		

		frame.pack();
	}
	private void displayUsernameFrame() {
		JFrame frame = new JFrame("Front Page");
		frame.setPreferredSize(new Dimension(1000, 650));
		Action ca = new AbstractAction()
		{
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
        
        JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.BLACK);						// setting color
		frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);	// where to place in in the frame
		
//		JPanel midPanelC = new JPanel();
//		midPanelC.setBackground(Color.WHITE);
//		GridLayout lookPrint = new GridLayout(5, 5);
		BackgroundPanel midPanelC = new BackgroundPanel();
//		midPanelC.setLayout(lookPrint);
		
//		midPanelC.setLayout(new GridBagLayout());
//        JPanel newPanel = new JPanel(new GridBagLayout());
        
//        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.anchor = GridBagConstraints.NORTH;
//        constraints.insets = new Insets(0, 0, 0, 0);

		JButton bUsername = new JButton("Set Username:");
		bUsername.setFont(new Font("Arial", Font.PLAIN, 15));
		bUsername.setPreferredSize(new Dimension(200, 30));
//		bUsername.setBackground(Color.BLACK);
//		bUsername.setForeground(Color.WHITE);
		bUsername.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JTextField userName = new JTextField("Enter_Username:");
		userName.setFont(new Font("Arial", Font.PLAIN, 15));
		userName.setPreferredSize(new Dimension(200, 30));
//		userName.setBackground(Color.BLACK);
//		userName.setForeground(Color.WHITE);
		userName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//		userName.setLocation(300, 200);
//		userName.setBounds(100, 100, 150, 20);
		    
        midPanelC.add(bUsername);
		
//		midPanelC.add(bUsername);
//        constraints.gridx = 1;
        midPanelC.add(userName);
//		midPanelC.add(userName);

		// add panel to frame
		frame.getContentPane().add(midPanelC, BorderLayout.CENTER);
//		frame.getContentPane().add(midPanelL, BorderLayout.LINE_START);
//		frame.getContentPane().add(midPanelR, BorderLayout.LINE_END);
//		frame.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
		
		
		

		
		
		userName.setEditable(false);
		userName.addFocusListener(new FocusAdapter() {
		    public void focusGained(FocusEvent e) {
		    	userName.setEditable(true);
		        JTextField source = (JTextField)e.getComponent();
		        source.setText("");
		        source.removeFocusListener(this);
		    }
		});
		bUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				midPanelC.setToolTipText("");
				myRun.sendToServer("1");///Declares that it is a user and not a bot
				myRun.sendToServer(userName.getText());
				frame.setVisible(false);
				createGameFrame();
//				myRun.sendToServer("Games");
//				startShowingGames();
				
			
			}
		});
		
		frame.setVisible(true);
		frame.pack();
	}
	/**
	 * basic structure and button functionality of GUI is created
	 *
	 */
	private void iniGui() {

		/////////// set up window
		JFrame frame = new JFrame("Demo");
		frame.setPreferredSize(new Dimension(1000, 650));
//----------------Add confimartion for exit and if do send to server to quit to remove players thread
		Action ca = new AbstractAction()
		{
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
//		topPanel.setBackground(Color.BLACK);						// setting color
//		topPanel.setPreferredSize(new Dimension(1300, 50));				// dimension of panel
////		frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);	// where to place in in the frame
		
		JPanel midPanelC = new JPanel();
		midPanelC.setBackground(Color.BLACK);
		midPanelC.setPreferredSize(new Dimension(670, 500));
		midPanelC.setLayout(lookPrint);

		JPanel midPanelL = new JPanel();
		midPanelL.setBackground(Color.BLACK);
		midPanelL.setPreferredSize(new Dimension(100, 500));
		

		JPanel midPanelR = new JPanel();
		midPanelR.setBackground(Color.BLACK);
		midPanelR.setPreferredSize(new Dimension(230, 500));
		midPanelR.setLayout(right);
		
		JPanel panelR1=new JPanel(new BorderLayout());
		panelR1.setBorder(BorderFactory.createEtchedBorder());
		panelR1.setBackground(Color.white);
		
		
		JPanel panelR=new JPanel(new BorderLayout());
		panelR.setBorder(BorderFactory.createEtchedBorder());
		panelR.setBackground(Color.white);
		JPanel panelR2=new JPanel(new BorderLayout());
		panelR2.setBorder(BorderFactory.createEtchedBorder());
		panelR2.setBackground(Color.white);
		JPanel panelR3=new JPanel(new BorderLayout());
		panelR3.setBorder(BorderFactory.createEtchedBorder());
		panelR3.setBackground(Color.white);
		JPanel panelR4=new JPanel(new BorderLayout());
		panelR4.setBorder(BorderFactory.createEtchedBorder());
		panelR4.setBackground(Color.white);
		
		 
		 jlabelGold4.setFont(new Font("Verdana",1,20));
		 panelR1.add(jlabelGold4);
		 jlabelGold.setFont(new Font("Verdana",1,20));
		 panelR.add(jlabelGold);
		 jlabelGold2.setFont(new Font("Verdana",1,20));
		 panelR2.add(jlabelGold2);
		 jlabelGold3.setFont(new Font("Verdana",1,20));
		 panelR4.add(jlabelGold3);
		 
		//new GridBagConstraints(columnNumber, rowNumber, columnSpan, rowSpan, columnWeigth, rowWeigth, alignment, fillType, insets, padX, pady)
		midPanelR.add(panelR,  new GridBagConstraints(0, 1, 1, 1, 0.3, 0.15, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
		midPanelR.add(panelR2,  new GridBagConstraints(0, 2, 1, 1, 0.7, 0.15, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
		midPanelR.add(panelR4,  new GridBagConstraints(0, 3, 1, 1, 0.5, 0.15, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
		midPanelR.add(panelR3,  new GridBagConstraints(0, 4, 1, 1, 0.5, 0.45, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
		midPanelR.add(panelR1,  new GridBagConstraints(0, 0, 1, 1, 0.3, 0.15, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
	    
	    
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.BLACK);
		bottomPanel.setPreferredSize(new Dimension(1000, 150));
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
//        bottomPanel.add(panel,  new GridBagConstraints(0, 0, 1, 2, 0.17, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
//                2, 2), 0, 0));
        bottomPanel.add(panel2,  new GridBagConstraints(1, 0, 1, 1, 0.73, 0.9, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
        bottomPanel.add(panel3,  new GridBagConstraints(2, 0, 1, 2, 0.10, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
        //next row
        bottomPanel.add(panel4,  new GridBagConstraints(1, 1, 1, 1, 0.73, 0.1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));

		// add panel to frame
		frame.getContentPane().add(midPanelC, BorderLayout.CENTER);
//		frame.getContentPane().add(midPanelL, BorderLayout.LINE_START);
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
//		combo1 = new JComboBox();
//		combo1.addItem("asdasd");
//		combo1.addItem("asdasd");
		panel4.add(combo1,BorderLayout.WEST);

		outputText= new JTextArea(40,40);
		outputText.setEditable(false);
		JScrollPane scroll1=new JScrollPane (outputText);
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		

		panel2.add(scroll1);
		
		chatText.setEnabled(true);

		
		
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
				
				myRun.sendToServer("ALLGOLD");
				myRun.sendToServer("GOLDOWNED");

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
				if(splitInput.length>=1) {
					if(!(String.valueOf(combo1.getSelectedItem()).isEmpty()) ){
//						myRun.sendToServer("WHISPER "+combo1+chatText.getText());
//						String selectedUsername= combo1.GetItemText(combo1.Selected);
						String selectedUsername = String.valueOf(combo1.getSelectedItem());
//						System.out.println("I AM HERE"+ selectedUsername);
						myRun.sendToServer("WHISPER "+selectedUsername+" "+chatText.getText());
					}

					
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
		
		frame.pack();
		frame.setVisible(true);
	}

}


