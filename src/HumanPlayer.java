import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;

/**
 * Runs the game with a human player and contains code needed to read inputs.
 * @version: 1.0
 * @release : 4/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class HumanPlayer
 {
	private BufferedReader reader;
	public HumanPlayer()
	{
									
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

    /**
     * Reads player's to the console.
     * <p>
     * return : A string containing the input the player entered.
     */
    protected String selectNextAction() 
	{
//    	System.out.println("asa");
    	String command="";

		try
		{
			
			while(true)
			{
//				System.out.println("asa22");
				command= reader.readLine();
//				System.out.println("asa23");
				String input = command.toUpperCase();	//converts to upper Case
//				System.out.println("asa24");
				return input;
			}
		}
		catch(IOException e) 
		{
          System.err.println(e.getMessage());
          System.exit(1);
        }
//		System.out.println("asa12");
        return command;
    }
}