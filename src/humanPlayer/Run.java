package humanPlayer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Run where the client handles the processes for the in and out of commands.
 *version: 1.0
 *release : 4/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class Run{

	private HumanClient humanClient;
//	private ConnectToHost connectToHost;

//	private int portNumber;
//	private String hostName;

	//client socket
	private Socket clientSocket=null;
	//input stream
	private BufferedReader in;
	//output stream
	private PrintWriter out;
	private String portNumber ="4000";
	private String ip="localhost";
//	18.134.172.246
	/**
	 * constructor of class creates and new serverhost instance.
	 */
	public Run() {
		connect(ip,portNumber);
	}

	/**
	 * actions to be done when the connection is succesful.
	 */
	private void connectSuccess(){
		humanClient = new HumanClient(this);

	}


	/**
	 * Starts the server by creating a new RunTHread
	 */
	public void startServer() {
		new RunThread(in,clientSocket,humanClient).start();	
	}

	/**
	 * method which sends information to server
	 */
	public void sendToServer(String command) {
//		System.out.println("Run "+command);
		this.out.println(command);
	}

	/**
	 * connects to the server using the portnuber and hostname
	 */
	public void connect(String hostName, String portNumberString){
		try
		{
			int portNumber=-1;
			try {
				portNumber= Integer.parseInt(portNumberString);
			}
			catch(Exception e){
				System.err.println(e);
			}
			System.out.println("client connected to " + hostName + ": "+ portNumber + "..." ); 	// if both provided print this
			clientSocket= new Socket(hostName, portNumber);
			in= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));    // creates a new input stream
			out = new PrintWriter(clientSocket.getOutputStream(),true);
			connectSuccess();

		}
		catch (UnknownHostException e)
		{
			System.err.println("don't know host " + hostName);
			System.exit(1);
		} 
		catch (IOException e)
		{
			System.err.println("Couldn' get I/O for connection " + hostName);
			System.exit(1);
		}
	}
}


