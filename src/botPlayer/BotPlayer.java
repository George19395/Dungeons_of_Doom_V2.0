package botPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *  Contains code for bot's decision making.
 * @version: 1.0
 * @release : 4/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class BotPlayer
{
	String mapS = null;		// gets the look bot method
	Boolean hasLooked = false;
	char[][] previousMap;
	char[][] tempMap;
	char previousMove;
	int row;
	boolean usePickUp=false;
//////////-------------------------------------------------------------------------	
	public int xradius=5;
	public int yradius=5;
	
//	 HashMap<Integer, String> botPositions = new HashMap<Integer, String>();
//	private static ArrayList<String[]> botP = new ArrayList<>();
	
////////////////////////////---------------------------------------------------------------------------------	
	
	public BotPlayer()
	{
		tempMap=new char[xradius][yradius];		//temporary map
		
		
	}
	
	public void lookB(String mapS) {
		this.mapS=mapS;
	}

	/**
     * Bot sees whether it's on gold and picks up if it is.
     */
	private boolean usePickup(){
		boolean result=false;
		switch (previousMove) {
			case 'N': {
				if (previousMap[1][2]=='G'){
					return true;
				}
			}
			break;
			
			case 'E': {
				if (previousMap[2][3]=='G'){
					return true;
				}
			}
			break;
			
			case 'W': {
				if (previousMap[2][1]=='G'){
					return true;
				}
			}
			break;
			
			case 'S': {
				if (previousMap[3][2]=='G'){
					return true;
				}
			}
		}
		
		return result;
	}
	
	/**
     * Selects the next action the bot will perform. Outputs in console the final result.
     */
    public String selectNextAction() 
	{
    	if (usePickup()){
    		hasLooked=false;							// if use pick up true pick up
    		previousMap=new char[5][5];
    		return "PICKUP";
    	}
    	if(!hasLooked){									// bot looks on the mini map
    		previousMap=tempMap.clone();				
    		tempMap=new char[xradius][yradius];
    		row=0;
    		hasLooked = !hasLooked;
    		return "LOOKB"; ///change to LOOK if not work
    	} else {
    		hasLooked = !hasLooked;
    		//selectMoveDirection(char[][] map);
    		char commandDirection = selectMoveDirection(tempMap);	
    		previousMove=commandDirection;
			return ("MOVE "+commandDirection);		//process the command 
    	}
    } 
    /**
     * method to allow me to know on my commands whether the bot has looked already
     */
    public boolean hasLooked()
    {
    	return  hasLooked;
    }
    /**
     * gets the look map from the bot client and fill into a temporary map for the bot to be able to look for gold
     */
    public void fillMap(String line)
    {
    	// # # # # # 
    	for(int i=0; i<xradius;i++)
    		for(int j=0;j<yradius;j++)
    	{
    		tempMap[i][j] = line.charAt(j+(5*i));
    		
    	}
//    	row++;
    }
    /**
     * @return :  The direction the bot will move.
     */
    protected char selectMoveDirection(char[][] map)
	{
		
		
		// map passed in the parameters
		int i,j;
		int[] goldPosition={-1,-1};
		for(i=0;i<xradius;i++)					// loops through the map
		{
			for(j=0;j<yradius;j++)
			{
				
				if(map[i][j]=='G')				//if it does finds Gold in the 5x5 map of the bot calculates in which direction it should move
				{
					goldPosition[0]=i;
					goldPosition[1]=j;
				}
			}
		}
		if (goldPosition[0]!=-1)				//if the gold is in the bot's maps call the the chaseGold method
		{
			return chaseGold(goldPosition);
		}
		else									// else call the selectRandom method
		{
			return selectRandom();
		}
				
    }
	 /**
		 *generate a random number between 0-4
	     * @return :  The direction the agent will move base on the random number generated.
	     */
	public char selectRandom()
	{
		int n;
		Random rand = new Random(); // call a new random method
		n = rand.nextInt(4);		// generate a random number from 0-3
		if(n==0)
		{		
			return 'N';
		}
		else if(n==1)
		{		
			return 'E';
		}
		else if(n==2)
		{
			return 'S';
		}
		else
		{
			return 'W';
		}
	}	
    
	
	 /**
	 *check in which direction the bot should move if the gold is in vision
     * @return :  The direction the agent will move based on the position of the Gold.
     */
	public char chaseGold(int[] goldPosition)
	{
		
		if(Math.abs(goldPosition[0]-2)>Math.abs(goldPosition[1]-2)) // if the absolute value of the distance from the bot of the vertical axis is greated than the absolute value of the distance from the bot of the horizontal axis do the following
		{
			if(goldPosition[0]>2) // if player position is greater than the bot position move in certain direction
			{
				return 'S';
				
			}
			else								// else move in the opposite direction
			{
				return 'N';
			}
			
		}
		else  //  if the absolute value of the distance from the bot of the vertical axis is less than the absolute value of the distance from the bot of the horizontal axis do the following
		{
			if(goldPosition[1]>2) // if player position is greater than the bot position move in certain direction
			{
				return 'E';
			}
			else								// else move in the opposite direction
			{
				return 'W';
			}
		}	
	}
}