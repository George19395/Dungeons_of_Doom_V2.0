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
	public static ArrayList<Integer[]> gamesAndId= new ArrayList<>(); //[0]=id [1]=gameId
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
		String allUsernames="Usernames"+" ";
		for ( Integer[] key : gamesAndId) {
		    System.out.println( key );
		    if(key[1]==gameIdOfPlayer) {
		    	allUsernames+= key[0]+" ";
//		    	allUsernames.add(key[0]);
		    }

		}
		
		
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
	
}
