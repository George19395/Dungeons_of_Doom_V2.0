package server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

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
//		this.gameLogic=gameLogic;
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
		System.out.println("ThreadServer send games"+allgames);
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
	public String generateGameNumber() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
		
	}


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
			
			
////------------server receives if player creates game or joins game--------------------
			Random rand = new Random();
//	        int rand_int1;
			
			String createOrJoin=in.readLine();
			if(createOrJoin.equals("CreateGame")) {
				System.out.println("BBBBBBBBBBBBBB");
				///create new GameLogic as it is a new game
				rand_int1= rand.nextInt(1000000);
				GameLogic gameLogic = new GameLogic();
				DODServer.games.put(rand_int1,gameLogic);
				System.out.println("New Game was created and the host is "+username);
				gameLogic.addPlayer(id,playerOrBot);
			
			
				String command;
				while((command=in.readLine())!=null)
				{

//					System.out.println("Clients next Command is: "+ command);
					String message;
	/////--------------get game Logic of the specific game using ID of game from above------------				
					String action=gameLogic.processCommand(id,command);
					String[] comm=command.split(" ");
					if(command.equals("PICKUP"))
					{
						out.println(action);
						message= "Success "+ command;
						System.out.println("PickUP gold =: " + action);

						
					}
					if(command.equals("QUIT")) {
						for(int i=0;i<DODServer.threadList.size();i++) {
							if(DODServer.threadList.get(i).id == id) {
								DODServer.threadList.remove(i);
							}
						}
					}
					if(DODServer.games.get(rand_int1).playerWins(id)==true)
					{
						out.println("Player with username: "+username+" wins");
						System.out.println("Player with username wins: " + username );
						System.exit(0);
					}

					else
					{
//						System.out.println("HELLOO U STUPUD FUCK");
						out.println(action);
						message= "Success "+ command;
//						System.out.println("FUCKME ACTION "+action);
					}

				}

			}
			else {
//				System.out.println("ThreadServer received");
//				DODServer.showGames(id);
//				System.out.println("ThreadServer games sent toclient");
				///joins game with specific id
				//sent this>>>>????URGENT?/////
//				rand_int1= Integer.parseInt(in.readLine());
//				GameLogic gameLogic = DODServer.games.get(rand_int1);
//				gameLogic.addPlayer(id,playerOrBot);
//				String[] gameSelected= in.readLine().split(" ");
//				rand_int1=Integer.parseInt(gameSelected[1]);
//				GameLogic gameLogic = DODServer.games.get(rand_int1);
//				gameLogic.addPlayer(id,playerOrBot);
				
//				System.out.println("Player Joins Game: " +username+" "+rand_int1);
				
				String[] com;
				String command;
				GameLogic gameLogic = null;
				String message;
				
				while((com=in.readLine().split(" "))!=null)
				{
					command=com[0];
					String action="";
					if(command.equals("JoinGame")) {
						DODServer.showGames(id);
						System.out.println("THREAD SERVER SHOWING GAMES");
					}
					else if(command.equals("Selecting")) {
						rand_int1 = Integer.parseInt(com[1]);
						gameLogic = DODServer.games.get(rand_int1);
						
						System.out.println("THREAD SERVER Player JOINED THE GAME");
					}

//					System.out.println("Clients next Command is: "+ command);
					
	/////--------------get game Logic of the specific game using ID of game from above------------				
					else {
						action=gameLogic.processCommand(id,command);
						
//						System.out.println(action);

						if(command.equals("PICKUP"))
						{
							out.println(action);
							message= "Success "+ command;
							System.out.println("PickUP gold = " + action);

							
						}
						if(command.equals("QUIT")) {
							for(int i=0;i<DODServer.threadList.size();i++) {
								if(DODServer.threadList.get(i).id == id) {
									DODServer.threadList.remove(i);
								}
							}
						}
						if(DODServer.games.get(rand_int1).playerWins(id)==true)
						{
							out.println("Player with username: "+username+" wins");
							System.out.println("Player with username wins: " + username );
							System.exit(0);
						}

						else
						{
//							System.out.println("HELLOO U STUPUD FUCK");
							out.println(action);
							System.out.println("FUCKME ACTION "+action);
							message= "Success "+ command;
							
						}
					}


				}

			}
			
//			gameLogic.addPlayer(id,playerOrBot);						// adds the player in the map

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
