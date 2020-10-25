package server;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import botPlayer.BotMemory;
import gameActions.GameLogic;
import gameActions.Map;
import humanPlayer.HumanClient;


/**
 * Server which process the commands of the players
 *version: 1.0
 *release : 4/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class DODServer {
	///----------------------------------
	

	private static JLabel[] labels= new JLabel[352];
	private static ImageIcon humanIcon;
	private static ImageIcon floorIcon;
	private static ImageIcon goldIcon;
	private static ImageIcon wallIcon;
	private static ImageIcon exitIcon;
	
	private static JPanel midPanelC;
	
	//----------------------------------------

	public static ArrayList<ThreadServer> threadList = new ArrayList<ThreadServer>(); 	// creates the threadserver array list
	private static ArrayList<String[]> usernameList = new ArrayList<>();				// creates array list which holds all the usernames of the users
	public static HashMap<Integer,GameLogic> games = new HashMap<Integer, GameLogic>();
	
//	private static GameLogic gameLogic;
	private static BotMemory botMemory;

	private static JTextArea textArea;
	private static JScrollPane scroll;
	private static Map map;
	
	static ServerSocket serverSocket;
	
	public DODServer() {
		
		Images();
		init() ;
	}
	static int idNow=0;

	/**
	 * gets all the usernames from the string array
	 * @return a string with the usernames
	 */
	public static void Username(int id)
	{
		String allUsernames="Usernames"+" ";
		for(int i=0; i<threadList.size();i++)
		{
			allUsernames+= threadList.get(i).id+" ";
		}

		for (int i=0; i<threadList.size(); i++){
			if ((threadList.get(i).id)==id){
//				threadList.get(Integer.parseInt(usernameList.get(i)[0])).shoutMessage("SHOUT "+usernameList.get(id)[1]+ " says: " + message);
				threadList.get(i).sendUsernames(allUsernames);
			}
		}
//		return allUsernames;	
	}
	
	public static void showGames(int id)
	{
		String allGames="GamesAll"+" ";
//		for (GameLogic game: games.values())
//			allGames+= game+" ";
		for (Integer game: games.keySet()) {
			System.out.println(game);
			allGames+= game+" ";
		}
			
		for (int i=0; i<threadList.size(); i++){
			if ((threadList.get(i).id)==id){
//				threadList.get(Integer.parseInt(usernameList.get(i)[0])).shoutMessage("SHOUT "+usernameList.get(id)[1]+ " says: " + message);
				threadList.get(i).sendGames(allGames);
			}
		}
//		System.out.println("DODServer string of names created");
//		return allUsernames;	
	}
	
	/**
	 * sets the username to a string array
	 */
	public static void setClientUsername(int id, String username) {
		String[] element=new String[2];
		element[0]=""+id;
		element[1]=username;
		usernameList.add(element);
	}

	/**
	 * Whisper command which allows users to communicate between in the in private
	 */
	public static void whisper(int idSender,int idReceiver, String message) {
//---------- finds my username---------------------------------------
		String myUsername="";
		for (int i=0; i<usernameList.size(); i++){
			if (Integer.parseInt(usernameList.get(i)[0])==idSender){
				myUsername=usernameList.get(i)[1];
			}
		}
//		for (int i=0; i<usernameList.size(); i++){
//			if (Integer.parseInt(usernameList.get(i)[0])==id){
//				threadList.get(Integer.parseInt(usernameList.get(i)[0])).shoutMessage("SHOUT "+usernameList.get(id)[1]+ " says: " + message);
//			}
//		}
		for (int i=0; i<threadList.size(); i++){
			if ((threadList.get(i).id)==idReceiver){
//				threadList.get(Integer.parseInt(usernameList.get(i)[0])).shoutMessage("SHOUT "+usernameList.get(id)[1]+ " says: " + message);
				threadList.get(i).shoutMessage("Whisper " +myUsername+ " says: " + message);
			}
		}
		
	}

	/**
	 * allows a user to send a message o all other users
	 */
	public static void shout(int id, String message) {
		for (int i=0; i<threadList.size(); i++){
			threadList.get(i).shoutMessage("SHOUT " +usernameList.get(id)[1]+ " says: " + message);
//			System.out.println(threadList.get(i).id);
		}
	}

	
	
	/**
	 * does all the checking for correct argument number and creates new serversocket
	 * @return a string with the usernames
	 */
	public static void connectToServer(String[] args) {
//		if (args.length != 1) {
//			System.err.println("Usage: java DODServer <port number>");
//			System.exit(1);
//
//		}

		int portNumber = 4000;
//		int portNumber = Integer.parseInt(args[0]);
		try {
			serverSocket = new ServerSocket(portNumber); 				// connects server socket with specific port number so tha clients can communicate with server if the connect to the same port
			System.out.println("dodServer running on " + serverSocket.getLocalPort()); 
			System.out.println("dodServer running on " + InetAddress.getLocalHost()); 
			
//			int idNow=0;

			while(true){
				Socket clientsocket = serverSocket.accept();

				threadList.add(new ThreadServer(clientsocket,idNow)); 
//				threadList.get(idNow).start();										//starts the thread
//--------------TEMPORARY SOLUTION FIX THIS-----------------------
				threadList.get(threadList.size() - 1).start();
				idNow++;														// increments id so that users have different id's
			}

		}
		catch(Exception e) {

		}
	}
	
	/**
	 * basic structure and button functionality of GUI is created
	 *
	 */
	private static void displayGUI()
	{
		//set up window
		JFrame frame= new JFrame("GODS EYE");
		frame.setPreferredSize(new Dimension(1500,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		

		//new panels
		final JPanel topPanel=new JPanel();
		midPanelC=new JPanel();
		final JPanel midPanelL=new JPanel();
		final JPanel midPanelR=new JPanel();
		final JPanel bottomPanel=new JPanel();
		topPanel.add(midPanelL);

		// design panel
		topPanel.setBackground(Color.DARK_GRAY);
		topPanel.setPreferredSize(new Dimension(100,100));
		midPanelC.setBackground(Color.white);
		midPanelC.setPreferredSize(new Dimension(100,100));
		midPanelL.setBackground(Color.LIGHT_GRAY);
		midPanelL.setPreferredSize(new Dimension(100,100));
		midPanelR.setBackground(Color.LIGHT_GRAY);
		midPanelR.setPreferredSize(new Dimension(100,100));
		bottomPanel.setBackground(Color.DARK_GRAY);
		bottomPanel.setPreferredSize(new Dimension(100,100));


		frame.getContentPane().add(midPanelC, BorderLayout.CENTER);
		frame.getContentPane().add(midPanelR, BorderLayout.LINE_END);


///////////----------------------Try to dynamically change map---------------------------------------------		

		GridLayout lookPrint2 = new GridLayout(11, 32);
		midPanelC.setLayout(lookPrint2);
		
		for (int i = 0; i < 352; i++)		
		{   
			labels[i] = new JLabel("");
			midPanelC.add(labels[i]);
		}
/////-------------Re evaluate this as it will be hard to do so---------------		
//		int delay = 1000; //milliseconds
//		ActionListener taskPerformer = new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//
//				midPanelC.setToolTipText("");
//				String map = games.get(threadList.get(idNow).rand_int1)
//						.convertMapToString();
//				updateMap(map);
//			}
//		};
//		new Timer(delay, taskPerformer).start(); //Timer class is used which allows a command to be executed as often as the delay allows it
/////----------------up to here---- comment rest----------------------------------		

		//Create buttons
		final JButton showMap=new JButton("Show Map");
		showMap.setPreferredSize(new Dimension(150,30));
		midPanelR.add(showMap);

		final JButton hideMap=new JButton("Hide Map");
		hideMap.setPreferredSize(new Dimension(150,30));
		midPanelR.add(hideMap);
		showMap.setVisible(false);

		JLabel portChange=new JLabel("Port Number: ");
//		portChange.setPreferredSize(new Dimension(150,30));
		midPanelR.add(portChange);
		JTextArea tex= new JTextArea();
		int a =serverSocket.getLocalPort();
		tex.append(Integer.toString(a));
		midPanelR.add(tex);
		
		JLabel ip=new JLabel("IP address :");
//		portChange.setPreferredSize(new Dimension(150,30));
		midPanelR.add(ip);
		JTextArea tex2= new JTextArea();
		InetAddress a2 = null ;
		
		try {
			a2 =InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String a3=""+a2;
		String[] splitInput=a3.trim().split("/");
		tex2.append(""+splitInput[1]);
		midPanelR.add(tex2);
		

		// action Listener to for show map button
		showMap.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				midPanelC.setToolTipText("");
				midPanelC.show();
				showMap.setVisible(false);		//hides the show map button and shows the hide map button
				hideMap.setVisible(true);

			}
		});		



		hideMap.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				midPanelC.setToolTipText("");
				showMap.setVisible(true);			
				hideMap.setVisible(false);			//hides the show map button and shows the hide map button
				midPanelC.hide();

			}
		});		

//		portChange.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				midPanelC.setToolTipText("");
//				
//
//			}
//		});		

		frame.pack();
		frame.setVisible(true);
	}
	public void init() {
//		botMemory = new BotMemory();
//		gameLogic=new GameLogic(); // instance of the game logic
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
		
				displayGUI();	
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
		Image newimg = image.getScaledInstance(1500/32, 600/11,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		humanIcon = new ImageIcon(newimg);  // transform it back


		java.net.URL floorUrl = HumanClient.class.getResource("/Floor.png");
		assert(floorUrl != null);
		ImageIcon floor =new ImageIcon(floorUrl);
		Image image1 = floor.getImage(); // transform it 
		Image newimg1 = image1.getScaledInstance(1500/32, 600/11,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		floorIcon = new ImageIcon(newimg1);  // transform it back
		//assert(floor != null);

		java.net.URL goldUrl = HumanClient.class.getResource("/Gold.png");
		assert(goldUrl != null);
		ImageIcon Gold =new ImageIcon(goldUrl);
		Image image12 = Gold.getImage(); // transform it 
		Image newimg12 = image12.getScaledInstance(1500/32,600/11,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		goldIcon = new ImageIcon(newimg12);  // transform it back
		

		java.net.URL wallUrl = HumanClient.class.getResource("/Wall.png");
		assert(wallUrl != null);
		ImageIcon wall =new ImageIcon(wallUrl);
		Image image123 = wall.getImage(); // transform it 
		Image newimg123 = image123.getScaledInstance(1500/32, 600/11,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		wallIcon = new ImageIcon(newimg123);  // transform it back
//		assert(wall != null);

		java.net.URL exitUrl = HumanClient.class.getResource("/Exit.png");
		assert(exitUrl != null);
		ImageIcon exit =new ImageIcon(exitUrl);
		Image image1234 = exit.getImage(); // transform it 
		Image newimg1234 = image1234.getScaledInstance(1500/32,600/11,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		exitIcon = new ImageIcon(newimg1234);  // transform it back
		//assert(floor != null);

	}
	
	/**
	 * method which updates tiles of the map based on the char with certain image icon
	 *
	 */
	private static void updateMap(String look)
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
}
