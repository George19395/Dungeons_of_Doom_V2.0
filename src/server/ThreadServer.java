package server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import gameActions.GameLogic;

/**
 * ThreadServer allows you to create many threads of the clients
 *version: 1.0
 *release : 4/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class ThreadServer extends Thread{

	private Socket socket;
//	private GameLogic gameLogic;
	public int id;
	private PrintWriter out;
	public  int rand_int1;
	

	/**
	 * assigns values to global variables
	 *
	 */
	public ThreadServer(Socket socket, int id){
		super("ThreadServer");
		this.socket=socket;
		this.id=id;
	}


	/**
	 *writes to the port and all clients get this msg
	 */
	public void shoutMessage(String message){
		out.println(message);
	}
	public void sendUsernames(String usernames){
		out.println(usernames);
	}
	public void sendGames(String allgames){
		out.println(allgames);
		System.out.println("ThreadServer send games to client "+allgames);
	}

	
	//format: "whisper <user> text...
	public void whisperMessage(String username,String message) {
		out.println(message);
		
	}
	
	/**
	 *sends answer to all the clients
	 */
	public void sendToClient(String command)
	{
		System.out.println(command);
		out.println(command);
	}

////------YOU CAN USE THIS LATER ON FOR GAME GENERATION NUMBERS-----------------	
//	public String generateGameNumber() {
//		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//        StringBuilder salt = new StringBuilder();
//        Random rnd = new Random();
//        while (salt.length() < 6) { // length of the random string.
//            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//            salt.append(SALTCHARS.charAt(index));
//        }
//        String saltStr = salt.toString();
//        return saltStr;
//		
//	}
/////------------------------------------------------------------------------------------------

	/**
	 *runs the thread
	 */
	public void run()
	{
		try
		{
			System.out.println("sever"+" has connected");

			out = new PrintWriter(socket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

////------server receivers first if player is a bot or human-------------------------
			int playerOrBot=Integer.parseInt(in.readLine());		//convers the input to integer
			if(playerOrBot==0) {
				System.out.println("New Bot"+" has connected");
			}
			else {
				System.out.println("New Human Player"+" has connected");
			}

////------------------server receives username of player----------------------------------------			
			String username=in.readLine();	
			System.out.println(username+" has connected");
			DODServer.setClientUsername(this.id,username);
			
//			String activeGames=in.readLine();
			System.out.println("Getting Active games...");
			DODServer.showGames(id);
			
			
////------------server receives if player creates game or joins game--------------------
			GameLogic gameLogic;
			Random rand = new Random();
			
			String createOrJoin = in.readLine();
			if(createOrJoin.equals("CreateGame")) {
				System.out.println("Creating Game");
				rand_int1= rand.nextInt(1000000);
				gameLogic = new GameLogic();
				DODServer.games.put(rand_int1,gameLogic);
				System.out.println("New Game was created and the host is "+username);
				gameLogic.addPlayer(id,playerOrBot);
				DODServer.gamesAndId.add(new Integer[]{
						new Integer(this.id),
						new Integer(rand_int1)});
			}
//			(createOrJoin.equals("JoinGame")) 
			else {
				String[] splitInput=createOrJoin.trim().split(" ");
				
////-------------RANDOM GAME SELECTIOM--------------------------------------				
				Set<Integer> keySet = DODServer.games.keySet();
				ArrayList<Integer> keyList = new ArrayList<>(keySet);
				int size = keyList.size();
			    rand_int1 = rand.nextInt(size);
			    int randomKey= keyList.get(rand_int1);
//			    gameLogic = DODServer.games.get(randomKey);
/////------------PLAYER SELECTION OF GAME TO JOIN----------------------
//			    Set<Integer> keySet = DODServer.games.keySet();
//				ArrayList<Integer> keyList = new ArrayList<>(keySet);
				if(splitInput.length >1) {
					int gameId= Integer.parseInt(splitInput[1]);;
					if(keyList.contains(gameId)) {
						gameId= Integer.parseInt(splitInput[1]);
						gameLogic = DODServer.games.get(gameId);
						System.out.println("THREAD SERVER Player JOINED THE GAME");
						gameLogic.addPlayer(id, playerOrBot);
						DODServer.gamesAndId.add(new Integer[]{
								new Integer(this.id),
								new Integer(gameId)});
					}
					else {
						System.err.println("TJREAD SERVER THIS GAME DOESNT EXIST");
						gameLogic = DODServer.games.get(randomKey);
						System.out.println("THREAD SERVER Player JOINED random GAME");
						gameLogic.addPlayer(id, playerOrBot);
						DODServer.gamesAndId.add(new Integer[]{
								new Integer(this.id),
								new Integer(randomKey)});
					}
				}
				else {
					System.err.println("TJREAD SERVER THIS GAME DOESNT EXIST");
					gameLogic = DODServer.games.get(randomKey);
					System.out.println("THREAD SERVER Player JOINED random GAME");
					gameLogic.addPlayer(id, playerOrBot);
					DODServer.gamesAndId.add(new Integer[]{
							new Integer(this.id),
							new Integer(randomKey)});
				}
			    

			}


//			else {
//				System.out.println("Unrecognised Inpute");
//			}
			
			
			
			String command="";
			while((command=in.readLine())!=null) {
				String message;
				/////--------------get game Logic of the specific game using ID of game from above------------				
//				System.out.println("AAAAAAAAAAAAAAA"+command);
				String action=gameLogic.processCommand(id,command);
				if(command.equals("PICKUP"))
				{
					out.println(action);
					message= "Success "+ command;
					System.out.println("PickUP gold =: " + action);
					
					
				}
				if(command.equals("ALLGOLD")) {
					out.println("ALLGOLD "+ action);
				}
				if(command.equals("GOLDOWNED")) {
					out.println("GOLDOWNED "+action);
				}
				if(command.equals("QUIT")) {
					for(int i=0;i<DODServer.threadList.size();i++) {
						if(DODServer.threadList.get(i).id == id) {
							DODServer.threadList.remove(i);
						if(Integer.parseInt(DODServer.usernameList.get(i)[0])==id) {
							DODServer.usernameList.remove(i);
						}
						if(DODServer.gamesAndId.get(i)[0]==id) {
							DODServer.gamesAndId.remove(i);
						}
						///check hashMap, if the player that left is the last player in the game, dispose the this.GameLogic
						
//							gameLogic.removePlayer(id);
						}
					}
				}
				if(gameLogic.playerWins(id)==true)
				{
					out.println("Player with username: "+username+" wins");
					System.out.println("Player with username wins: " + username );
					System.exit(0);
				}

				else
				{
//									System.out.println("HELLOO U STUPUD FUCK");
					out.println(action);
					message= "Success "+ command;
//									System.out.println("FUCKME ACTION "+action);
				}				
			}
			

////----------------------Server receives first actual command---------------------------
			
		}
		catch(SocketException se){
			System.out.println("the client disconnected");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{
				socket.close();
			}
			catch(Exception e){}
//			DODServer.games.get(rand_int1).removePlayer(id);
		}
	}
}
