import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *Run Thread runs all the time when called doing certain tasks
 *version: 1.0
 *release : 25/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class RunThread extends Thread{

	private BufferedReader in;
	private Socket socket;
	private HumanClient humanClient;

	/**
	 * constructor of class
	 *
	 */
	public RunThread(BufferedReader in, Socket socket, HumanClient humanClient){
		this.in=in;
		this.socket=socket;
		this.humanClient=humanClient;
	}

	/**
	 * checks for the response from the server and acts appropriately 
	 *
	 */
	public void run(){
		try{
			String output;	
			while((output=in.readLine())!=null)			// when input is not null
			{
				if(output.equals("Disconnect"))				// if the return type of the command is Disconnect do the following
				{
					System.exit(0);
				}

				String[] as= output.trim().split(" ");
				if(as[0].equals("LOOK"))
				{
					humanClient.updateLook(as[1]);

				}
				if(as[0].equals("SHOUT"))
				{
					for(int i=1;i<as.length;i++)
					{
						humanClient.appendToOutput(as[i]+ " ");
					}
					humanClient.appendToOutput(""+ "\n");

				}
				if(as[0].equals("Whisper"))
				{
					for(int i=1;i<as.length;i++)
					{
						humanClient.appendToOutput(as[i]+ " ");
					}

				}
				if(as[0].equals("Usernames"))
				{
					ArrayList<String> str = new ArrayList<String>();
//					System.out.println("COME HERE");
					for(int i=1;i<as.length;i++)
					{
						str.add(as[i]);
//						System.out.println(as[i]);
//						humanClient.appendToTextArea(as[i]+ "\n");
						
					}
					humanClient.addToComboBox(str);
				}
				if(!as[0].equals("LOOK"))
				{
					humanClient.appendToTextArea(output+ "\n");
				}



//				System.out.println(output);

			}
		}
		catch(Exception e){
			System.err.println("mmmmmmmmmmmmmmmm");
		}
	}
}
