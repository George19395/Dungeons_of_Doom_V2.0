import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * ThreadServer allows you to create many threads of the clients
 *version: 1.0
 *release : 4/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class ThreadServer extends Thread{

	private Socket socket;
	private GameLogic gameLogic;
	public int id;
	private PrintWriter out;

	/**
	 * assigns values to global variables
	 *
	 */
	public ThreadServer(Socket socket, GameLogic gameLogic, int id){
		super("ThreadServer");
		this.socket=socket;
		this.gameLogic=gameLogic;
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

			int player=Integer.parseInt(in.readLine());		//convers the input to integer
			if(player==0) {
				System.out.println("bot"+player+" has connected");
			}
			else {
				System.out.println("PlayerA"+player+" has connected");
			}
			
			gameLogic.addPlayer(id,player);						// adds the player in the map


			String command;
			String username=in.readLine();				// reads the username
			//	CHANGE TO RUN SERVER WHEN YOU FIX IT
			DODServer.setClientUsername(this.id,username);
			while((command=in.readLine())!=null)
			{
				//System.out.println("Client says: "+ command);
				String message;
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
				if(gameLogic.playerWins(id)==true)
				{
					out.println("Player with username: "+username+" wins");
					System.out.println("Player with username wins: " + username );
					System.exit(0);
				}

				else
				{
					out.println(action);
					message= "Success "+ command;

				}

			}

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
			gameLogic.removePlayer(id);
		}
	}
}
