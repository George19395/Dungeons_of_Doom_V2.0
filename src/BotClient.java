import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;



/**
 * Starts the game with a Bot Player and gets all information from the BotPlayer class
 *version: 1.0
 *release : 4/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class BotClient implements Runnable {
	//client socket
	private static Socket clientSocket=null;

	//input stream
	private static BufferedReader in;
	//output stream
	private static PrintWriter out;

	private static	BotPlayer bot;  

	/**
	 * Runs the bot
	 */
	public void run() {
		try{

			out= new PrintWriter(clientSocket.getOutputStream(),true);
			out.println(0); 	// provies number 0 to server to know that its a bot player
			bot=new BotPlayer(); // instance of the bot
			String inputString;
			while((inputString=bot.selectNextAction())!=null) { // while the command is not null

//				System.out.println(inputString);				// 	
				out.println(inputString);						// outputs the command
				Thread.sleep ( 2000 );							// delay of 2s between each time the bot makes its next command

			}
		}
		catch (IOException e){
			System.err.println("Disconnected from the Server! The system will now exit...");
			System.exit(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main method
	 *
	 */
	public static void main(String[] args){
		if (args.length != 2) {					// 2 arguments required to run the bot client
			System.err.println("Usage: java HumanClient <host name> <port number>");
			System.exit(1);
		}

		String hostName = args[0];						// first argument
		int portNumber = Integer.parseInt(args[1]);		// 2nd argument


		try
		{
			System.out.println("client connected to " + hostName + ": "+ portNumber + "..." );	// when both arguments are passed print on bots screen
			clientSocket= new Socket(hostName, portNumber);		// client socket with two parameters which are passed as arguments
			in= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			new Thread(new BotClient()).start();		// creates a new thread of the bot

			String output =null; 
			while((output=in.readLine())!=null)
			{
				// #####
//				String[] splitInput=output.trim().split("/");
//				for(int i=0; i<splitInput.length;i++) {
//					System.out.println("AAAAAAAA"+splitInput[i]);
//				}
				if(bot.hasLooked()&&!(output.isEmpty()))		// fills the map in the BotPlayer class so that the bot can look for Gold when needed
				{
//					System.out.println(output);
					String upToNCharacters = output.substring(5);
//					System.out.println(upToNCharacters);
					bot.fillMap(upToNCharacters);
					for(int i =0; i<5;i++) {
						for(int j =0; j<5;j++) {
							System.out.print(bot.tempMap[i][j]);
						}
						System.out.println();
					}

				
				}
//				if(bot.usePickUp) {
//					System.out.println("My Gold:" + output);
//				}
			}

		}
		catch (UnknownHostException e)
		{
			System.err.println("don't know host" + hostName);
			System.exit(1);
		} 
		catch (IOException e)
		{
			System.err.println("Couldn' get I/O for connection " + hostName);
			System.exit(1);
		}
	}
}