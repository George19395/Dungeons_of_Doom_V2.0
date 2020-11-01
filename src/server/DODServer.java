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

	//---------------------------------------- MAKE LISTS TO PRIVATE AND USE GETTERS AND SETTERS--------------------------------

	public static ArrayList<ThreadServer> threadList = new ArrayList<ThreadServer>(); 	// creates the threadserver array list
	public static ArrayList<String[]> usernameList = new ArrayList<>();				// creates array list which holds all the usernames of the users
	public static HashMap<Integer,GameLogic> games = new HashMap<Integer, GameLogic>();
	public static ArrayList<Integer[]> gamesAndId= new ArrayList<>(); //[0]=id [1]=gameId
	///--------------------------------------------------------------------------------------------------------------------------

	//	private static BotMemory botMemory;

	private static JTextArea textArea;
	private static JScrollPane scroll;
	private static Map map;

	static ServerSocket serverSocket;

	public DODServer() {

		//		Images();
		//		init() ;
	}
	static int idNow=0;



	/**
	 * gets all the usernames from the string array
	 * @return a string with the usernames
	 */
	public static void Username(int id)
	{
		//i need the hashmap to include the ID as well in order to find users in same
		// Game and filter them out.
		GameLogic gameLogic;
		int gameIdOfPlayer = 0;
		for (int i=0; i<threadList.size(); i++){
			if ((threadList.get(i).id)==id){
				for(int j=0;j<gamesAndId.size();j++) {
					if(id==(gamesAndId.get(j)[0])) {
						gameIdOfPlayer=gamesAndId.get(j)[1];
					}
				}
			}
		}

		//		ArrayList<Integer>allUsernames= new ArrayList<Integer>();
		ArrayList<Integer> users=new ArrayList<Integer>();
		for ( Integer[] key : gamesAndId) {
			//		    System.out.println( key );
			if(key[1]==gameIdOfPlayer) {
				users.add(key[0]);
				//		    	allUsernames.add(key[0]);
			}

		}

		String allUsernames="Usernames"+" ";
		for (int i=0; i<usernameList.size(); i++){
			for(int user:users) {
				if (Integer.parseInt(usernameList.get(i)[0])==user){
					allUsernames+=usernameList.get(i)[1]+" ";
					//					break;
				}

			}

		}
		System.out.println(allUsernames);

		//		String allUsernames="Usernames"+" ";
		//		for(int i=0; i<threadList.size();i++)
		//		{
		//			allUsernames+= threadList.get(i).id+" ";
		//		}

		for (int i=0; i<threadList.size(); i++){
			if ((threadList.get(i).id)==id){
				//				threadList.get(Integer.parseInt(usernameList.get(i)[0])).shoutMessage("SHOUT "+usernameList.get(id)[1]+ " says: " + message);
				threadList.get(i).sendUsernames(allUsernames);
			}
		}
		//		return allUsernames;	
	}

	public static void gameOver(int id) {
		int gameNum = 0;
		for(int i=0; i<gamesAndId.size();i++) {
			if(gamesAndId.get(i)[0]==id) {
				gameNum=gamesAndId.get(i)[1];
			}
		}
		ArrayList<Integer> allPlayers = new ArrayList<Integer>();
		for(int i=0; i<gamesAndId.size();i++) {
			if(gamesAndId.get(i)[1]==gameNum) {
				allPlayers.add(gamesAndId.get(i)[0]);
			}
		}	
		for(int i=0;i<allPlayers.size();i++) {
			if(allPlayers.get(i)==id) {
				allPlayers.remove(i);
			}
		}

		for(int i=0;i<threadList.size();i++) {
			for(int j=0;j<allPlayers.size();j++) {
				if(threadList.get(i).id==allPlayers.get(j)) {
					threadList.get(i).sendToClient("GAMEOVER "+ id);
				}
			}
		}



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
	public static void whisper(int idSender,String receiverUsername, String message) {
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
		int idReceiver=1;
		for(int i=0;i<usernameList.size();i++) {
			System.out.println("I AM HERE YOU DUMB FUCK");
			System.out.println(receiverUsername);
			System.out.println(usernameList.get(i)[1].toUpperCase());
			if(usernameList.get(i)[1].toUpperCase().equals(receiverUsername)) {
				idReceiver=Integer.parseInt(usernameList.get(i)[0]);
				System.out.println("I AM HERE YOU DUMB FUCK "+ Integer.parseInt(usernameList.get(i)[0]));
			}
		}
		//		System.out.println("I AM HERE YOU DUMB FUCK "+idReceiver);

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

	//	/**
	//	 * basic structure and button functionality of GUI is created
	//	 *
	//	 */
	//	
	//	public void init() {
	////		botMemory = new BotMemory();
	////		gameLogic=new GameLogic(); // instance of the game logic
	//		javax.swing.SwingUtilities.invokeLater(new Runnable(){
	//			public void run(){
	//		
	////				displayGUI();	
	//			}
	//		});
	//		
	//	}

//	private static void displayGUI()
//	{
//		//set up window
//		JFrame frame= new JFrame("GODS EYE");
//		frame.setPreferredSize(new Dimension(1500,600));
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setResizable(false);
//
//
//		//new panels
//		final JPanel topPanel=new JPanel();
//		midPanelC=new JPanel();
//		final JPanel midPanelL=new JPanel();
//		final JPanel midPanelR=new JPanel();
//		final JPanel bottomPanel=new JPanel();
//		topPanel.add(midPanelL);
//
//		// design panel
//		topPanel.setBackground(Color.DARK_GRAY);
//		topPanel.setPreferredSize(new Dimension(100,100));
//		midPanelC.setBackground(Color.white);
//		midPanelC.setPreferredSize(new Dimension(100,100));
//		midPanelL.setBackground(Color.LIGHT_GRAY);
//		midPanelL.setPreferredSize(new Dimension(100,100));
//		midPanelR.setBackground(Color.LIGHT_GRAY);
//		midPanelR.setPreferredSize(new Dimension(100,100));
//		bottomPanel.setBackground(Color.DARK_GRAY);
//		bottomPanel.setPreferredSize(new Dimension(100,100));
//
//
//		frame.getContentPane().add(midPanelC, BorderLayout.CENTER);
//		frame.getContentPane().add(midPanelR, BorderLayout.LINE_END);
//
//
//		///////////----------------------Try to dynamically change map---------------------------------------------		
//
//		GridLayout lookPrint2 = new GridLayout(11, 32);
//		midPanelC.setLayout(lookPrint2);
//
//		for (int i = 0; i < 352; i++)		
//		{   
//			labels[i] = new JLabel("");
//			midPanelC.add(labels[i]);
//		}
//		/////-------------Re evaluate this as it will be hard to do so---------------		
//		//		int delay = 1000; //milliseconds
//		//		ActionListener taskPerformer = new ActionListener() {
//		//			public void actionPerformed(ActionEvent evt) {
//		//
//		//				midPanelC.setToolTipText("");
//		//				String map = games.get(threadList.get(idNow).rand_int1)
//		//						.convertMapToString();
//		//				updateMap(map);
//		//	}
//		//	};
//		//	new Timer(delay, taskPerformer).start(); //Timer class is used which allows a command to be executed as often as the delay allows it
//		/////----------------up to here---- comment rest----------------------------------		
//
//		//Create buttons
//		final JButton showMap=new JButton("Show Map");
//		showMap.setPreferredSize(new Dimension(150,30));
//		midPanelR.add(showMap);
//
//		final JButton hideMap=new JButton("Hide Map");
//		hideMap.setPreferredSize(new Dimension(150,30));
//		midPanelR.add(hideMap);
//		showMap.setVisible(false);
//
//		JLabel portChange=new JLabel("Port Number: ");
//		//	portChange.setPreferredSize(new Dimension(150,30));
//		midPanelR.add(portChange);
//		JTextArea tex= new JTextArea();
//		int a =serverSocket.getLocalPort();
//		tex.append(Integer.toString(a));
//		midPanelR.add(tex);
//
//		JLabel ip=new JLabel("IP address :");
//		//	portChange.setPreferredSize(new Dimension(150,30));
//		midPanelR.add(ip);
//		JTextArea tex2= new JTextArea();
//		InetAddress a2 = null ;
//
//		try {
//			a2 =InetAddress.getLocalHost();
//		} catch (UnknownHostException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		String a3=""+a2;
//		String[] splitInput=a3.trim().split("/");
//		tex2.append(""+splitInput[1]);
//		midPanelR.add(tex2);
//
//
//		// action Listener to for show map button
//		showMap.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				midPanelC.setToolTipText("");
//				midPanelC.show();
//				showMap.setVisible(false);		//hides the show map button and shows the hide map button
//				hideMap.setVisible(true);
//
//			}
//		});		
//
//
//
//		hideMap.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				midPanelC.setToolTipText("");
//				showMap.setVisible(true);			
//				hideMap.setVisible(false);			//hides the show map button and shows the hide map button
//				midPanelC.hide();
//
//			}
//		});		
//
//		//	portChange.addActionListener(new ActionListener()
//		//	{
//		//		public void actionPerformed(ActionEvent e)
//		//		{
//		//			midPanelC.setToolTipText("");
//		//			
//		//
//		//		}
//		//	});		
//
//		frame.pack();
//		frame.setVisible(true);
//	}	
}
