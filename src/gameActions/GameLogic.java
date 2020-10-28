package gameActions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import botPlayer.BotMemory;
import server.DODServer;



/**
 * Contains the main logic part of the game, as it processes.
 *version: 1.0
 *release : 4/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class GameLogic 
{		
	//array list to hold all the players.
	private ArrayList<String[]> players = new ArrayList<String[]>(); // [0]=y [1]=x [2]=gold [3] if human 1 if bot 0, [4] = id

	public Map map;						// instance of the map
	private BotMemory botMemory;

////----------------------------------------------------------------------------------------------------------
	
	/**
	 * Converts the map from a 2D char array to a single string.
	 *This code was taken from the provided code on moodle
	 * @return : A String representation of the game map.
	 */
	public synchronized String lookB(int id) 
	{
		// get look positions [0,1]for current player
//		char[][] look = map.look(getPlayersPosition(id)[1], getPlayersPosition(id)[0]);
		ArrayList<int[]> look = map.lookB(getPlayersPosition(id)[1], getPlayersPosition(id)[0]);
		botMemory.storeNewViewLocations(id, look);
		ArrayList<int[]> newLook = botMemory.getBotView(id);
//		for(int i=0; i<(newLook.size());i++) {
//			System.out.println(Arrays.toString(newLook.get(i)));
//		}
		String look2 = map.lookBNew(newLook);
		// add current player's icon to look window
		// is opponent visible? if they are then add them to the look window
//		for(String[] playerStuff: players){
//
//			int xDistance = getPlayersPosition(id)[1] - Integer.parseInt(playerStuff[1]);
//			int yDistance = getPlayersPosition(id)[0] - Integer.parseInt(playerStuff[0]);
//			if(xDistance <= 2 && xDistance >= -2 && yDistance <= 2 && yDistance >= -2){
//				look2[2-xDistance][2-yDistance] = getPlayersIcon(Integer.parseInt(playerStuff[4]));
//			}
//		}
		// return look window as a String for printing
		String lookWindow = "";
//		for(int i=0; i<look2.length; i++){
//			for(int j=0; j<look2[i].length; j++){
//				lookWindow += look2[j][i];
//			}
//			//			lookWindow += "\n";
//		}
		lookWindow = look2;
//		HashMap<String, ArrayList<int[]>> mapAndCoordinates = new HashMap<String, ArrayList<int[]>>();
//		mapAndCoordinates.put(lookWindow, newLook);
		return lookWindow;

	}
	//BotMemory botMemory
///---------------------------------	-------------------------------------------------------------------------------
	public GameLogic(){
		map=new Map();			
		map.readMap("example_map.txt");		// call the function read map from map and read the file
///-----/home/ubuntu/DOD_Server/example_map.txt when using for actual server
		//		this.botMemory=botMemory;
	}	
//	public static void main(String[] args) {
//		GameLogic gamelogic = new GameLogic();
//		System.out.println(gamelogic.getMapHeight()+" "+gamelogic.getMapWidth());
//	}

	/**
	 *Pickups the gold based on the players that calls the command
	 * @return :  The number of gold the player has
	 */
	public synchronized String pickup(int id)
	{
		int index=indexFromId(id);


		int currentGold=Integer.parseInt(players.get(index)[2]);
		int[] currentPosition = new int[2];
		currentPosition[0]= Integer.parseInt(players.get(index)[0]);
		currentPosition[1]=Integer.parseInt(players.get(index)[1]);
		if(map.getTile(currentPosition)=='G')				//if player is standing on gold
		{
			map.updateMapLocation(currentPosition,'.');		//if picks up the gold replace G with .

			currentGold+=1;									// increases the gold by 1 if the pick up succesful
			String[] player = players.get(index);
			player[2]=""+currentGold;
		}

		return ""+currentGold;
	}
	public synchronized String getPlayersCurrentGold(int id) {
		
		int index=indexFromId(id);
		int currentGold= Integer.parseInt(players.get(index)[2]);
		
		return ""+currentGold;
		
	}

	/**
	 *Goes trough the player list
	 * @return :  The ID of certain player.
	 */
	private int indexFromId(int id) {

		for (int i = 0; i < players.size(); i++) {
			if (Integer.parseInt(players.get(i)[4]) == id) {
				return i;
			}
		}
		System.err.println("id: "+id);
		System.err.println("size: "+players.size());
//		System.err.println("first id: "+players.get(0)[4]);
		throw new RuntimeException("id not found");
	}

	/**
	 * @return : The position of the player.
	 */
	protected synchronized int[] getPlayersPosition(int id) 
	{
		int index=indexFromId(id);
		int[] currentPosition = new int[2];
		currentPosition[0]= Integer.parseInt(players.get(index)[0]); ///stored in player [0][1]
		currentPosition[1]=Integer.parseInt(players.get(index)[1]);
		return currentPosition;
	}

	/**
	 * Updates the stored in memory location of the player.
	 *
	 * @param location : New location of the player.
	 */
	protected synchronized void updatePlayerPosition(int[] location,int id)
	{
		int index=indexFromId(id);


		players.get(index)[0]=new Integer(location[0]).toString();
		players.get(index)[1]=new Integer(location[1]).toString();					
	}

	/**
	 * generate random position within the map to place the player
	 *
	 */
	public synchronized void addPlayer(int id,int playerBot)
	{
		int n;
		int k;
		Random rand = new Random();
		do{
			n = rand.nextInt(map.getMapWidth());
			k = rand.nextInt(map.getMapHeight());
		}
		while(!(isValidStartPosition(new int[]{n,k})));   // while the position is equal to a Wall or Gold generate a new position

		this.players.add(new String[]{
				new Integer(k).toString(),
				new Integer(n).toString(),
				"0",
				new Integer(playerBot).toString(),
				new Integer(id).toString()});
		assert(indexFromId(id)==this.players.size()-1);
		
		if(playerBot==0) {
			botMemory.addBot(id);
			
		}
	}
	/**
	 *Checks whether is a valid position start Position
	 * @return :  Whether is a valid position to move.
	 */
	private boolean isValidStartPosition(int[] pos) 
	{
		if(this.map.getTile(pos) == 'G') {
			return false;
		}

		return isValidMovePosition(pos);
	}

	/**
	 *Checks whether is a valid Position to move
	 */
	private boolean isValidMovePosition(int[] pos )
	{
		if(this.map.getTile(pos) == '#') {
			return false;
		}

		for(String[] playerStuff: players){

			int xDistance = pos[0] - Integer.parseInt(playerStuff[0]);
			int yDistance = pos[1] - Integer.parseInt(playerStuff[1]);
			if(xDistance == 0 && yDistance ==0){
				return false;
			}

		}
		return true;

	}

	/*
	 * @return : Returns back gold player requires to exit the Dungeon.
	 */
	protected String hello(int id) 
	{

		return "Gold: "+this.map.getGoldRequired();
	}



	/**
	 * Checks if movement is legal and updates player's location on the map.
	 *
	 * @param direction : The direction of the movement.
	 * @return : Protocol if success or not.
	 */
	protected String move(int id, char direction) 
	{
		int [] newPosition= new int[2];
		int	[] currentPosition= new int[2];
		currentPosition=getPlayersPosition(id);			// gets the players position

		if(direction=='N')								// if player moves north then set the players new position
		{
			newPosition[0]=currentPosition[0]-1;		//new position is 1 to the north
			newPosition[1]=currentPosition[1];			// horizontal movement is the same since moved in the vertical axis
		}
		else if(direction=='E')
		{
			newPosition[0]=currentPosition[0];		//vertical movement is the same since moved in the horizontal axis
			newPosition[1]=currentPosition[1]+1;	// new position is 1 towards east
		}
		else if(direction=='S')
		{
			newPosition[0]=currentPosition[0]+1;	
			newPosition[1]=currentPosition[1];
		}
		else if(direction=='W')
		{
			newPosition[0]=currentPosition[0];
			newPosition[1]=currentPosition[1]-1;
		}
		else										// if anything else return invalid
		{
			return "Invalid";
		}

		char newTile=this.map.getTile(newPosition); 	// new tile and get a copy of the tile in map of
		if(!isValidMovePosition(newPosition))							// if the player trys to move to a wall invalid
		{
			return "Invalid";
		}
		else											//else update the position and return success
		{
			this.updatePlayerPosition(newPosition,id);
//			System.out.println(newPosition[0]);
//			System.out.println(newPosition[1]);

			boolean output=playerWins(id);
			if (output==(false))
				return "Success";
			else
				return "Eligible to Exit";

		}
	}


	/**
	 * Converts the map from a 2D char array to a single string.
	 *This code was taken from the provided code on moodle
	 * @return : A String representation of the game map.
	 */
	public synchronized String look(int id) 
	{
		// get look window for current player
		char[][] look = map.look(getPlayersPosition(id)[1], getPlayersPosition(id)[0]);
		// add current player's icon to look window
		// is opponent visible? if they are then add them to the look window
		for(String[] playerStuff: players){

			int xDistance = getPlayersPosition(id)[1] - Integer.parseInt(playerStuff[1]);
			int yDistance = getPlayersPosition(id)[0] - Integer.parseInt(playerStuff[0]);
			if(xDistance <= 2 && xDistance >= -2 && yDistance <= 2 && yDistance >= -2){
				look[2-xDistance][2-yDistance] = getPlayersIcon(Integer.parseInt(playerStuff[4]));
			}
		}
		// return look window as a String for printing
		String lookWindow = "";
		for(int i=0; i<look.length; i++){
			for(int j=0; j<look[i].length; j++){
				lookWindow += look[j][i];
			}
			//			lookWindow += "\n";
		}
//		System.out.println("lookWINDOW");////Code goes in here -----THIS IS CHECKED
		return lookWindow;
		
	}
	
	/**
	 * Gets the map for the Map class and adds the players
	 *
	 * @return : A char[][] representation of the game map.
	 */
	public char[][] getMap() 
	{
		char[][] curMap= map.lookAllMap();
		int playerNumber;
		for(playerNumber=0; playerNumber<players.size();playerNumber++)
		{
			if(players.get(playerNumber)[3]=="1")
			{
				curMap[Integer.parseInt(players.get(playerNumber)[0])][Integer.parseInt(players.get(playerNumber)[1])]='B';
			}
			else
			{
				curMap[Integer.parseInt(players.get(playerNumber)[0])][Integer.parseInt(players.get(playerNumber)[1])]='P';
			}

		}

		return curMap;
	}


	/**
	 * Converts the map from char[][] to string
	 *
	 * @return : A string representation of the game map.
	 */
	public String convertMapToString()
	{

		// return look window as a String for printing
		String lookWindow = "";
		char[][] map = getMap();
		for(int i=0; i<map.length; i++){
			for(int j=0; j<map[i].length; j++){
				lookWindow += map[i][j];
			}
//			lookWindow += "\n";
		}
		return lookWindow;

	}


	/**
	 * Checks whether it is human player or bot.
	 * @return Returns the icon of the player
	 */
	private synchronized char getPlayersIcon(int id) {
		int index=indexFromId(id);
		if(players.get(index)[3].equals("0"))
		{
			return 'B';
		}

		return 'P';
	}
	public void map()
	{
		map.printMap();

	}
	/**
	 * Quits the game, shutting down the application.
	 */
	//	protected synchronized String quitGame(int id) 
	//	{
	//		int index=indexFromId(id);
	//		if(map.getTile(getPlayersPosition(id))=='E' 
	//				&& Integer.parseInt(players.get(index)[2])==map.getGoldRequired())			// if player has the gold required and steps on an exit quit game
	//		{
	//			return "Player Wins";
	//		}
	//		else {
	//			return "not end";
	//		}
	//	}

	/**
	 * Calls a function to remove certain player from araylist.
	 */
	public synchronized  void removePlayer(int id)
	{
		int index=indexFromId(id);
		this.players.remove(index);
	}
	public synchronized int countGoldLeftOnMap() {
		int goldLeft =0;
		String mapString = convertMapToString();
		for(int i=0;i<mapString.length();i++) {
			char c = mapString.charAt(i); 
			if(c == 'G') {
				goldLeft++;
			}
		}
		return goldLeft;
	}

	/**
	 *proccess the command
	 *@param the command the player inputs
	 *@return the method it needs to follow in game logic to execute the command
	 */
	public synchronized  String  processCommand(int id, String command)

	{
		String input = command.toUpperCase();
		if(input.equals("HELLO"))
		{
			return hello(id);
		}
		else if(input.equals("MOVE N"))
		{
			return move(id,command.toString().substring(5).toCharArray()[0]);
		}
		else if(input.equals("MOVE S"))
		{
			return move(id,command.toString().substring(5).toCharArray()[0]);
		}
		else if(input.equals("MOVE W"))
		{
			return move(id,command.toString().substring(5).toCharArray()[0]);
		}
		else if(input.equals("MOVE E"))
		{
			return move(id,command.toString().substring(5).toCharArray()[0]);
		}
		else if(input.equals("LOOK"))
		{
			return "LOOK "+ look(id);

		}
		else if(input.equals("LOOKB"))////Stoped here --- continue use methods for Bot
		{
			return "LOOK "+ look(id);

		}
		else if(input.equals("PICKUP"))
		{
			return pickup(id);
		}
		else if(input.equals("ALLGOLD")) {
			return ""+countGoldLeftOnMap();
		}
		else if(input.equals("GOLDOWNED")) {
			return getPlayersCurrentGold(id);
		}
		else if(input.equals("QUIT"))
		{
			return playerQuit(id);
		}
		else if(input.equals("USERNAMES"))
		{
//			System.out.println(input);
			DODServer.Username(id);
//			return "Usernames found";
			return "Usernames";
		}
//		else if(input.equals("SHOW GAMES"))
//		{
////			System.out.println(input);
//			DODServer.showGames(id);
//			return "Games Found";
//		}		
		else
		{
			String[] splitInput=input.split(" ");					//split the string when ever the is space
			if (splitInput[0].equals("SHOUT")&&splitInput.length>1) {		// if first string is equal to shout and there are more than 1 strings
				StringBuilder stringBuilder = new StringBuilder();
				String message;
				for (int i=1; i<splitInput.length; i++){
					stringBuilder.append(splitInput[i]+"");
				}
				message=stringBuilder.toString();
				DODServer.shout(id, message);
				return "Message sent!";
			}
			if (splitInput[0].equals("WHISPER")&&splitInput.length>1){		// same as above but with the whisper command which required more than 2 substrings since it requires the players username aswell.
				StringBuilder stringBuilder = new StringBuilder();
				String message;
				for (int i=2; i<splitInput.length; i++){
					stringBuilder.append(splitInput[i]);
				}
				message=stringBuilder.toString();
//				DODServer.whisper(id,Integer.parseInt(splitInput[1]),message);
				DODServer.whisper(id,splitInput[1],message);
				return "Message sent!";
			}

		}
		return "Invalid";
	}

	/**
	 * Checks whether player wins
	 */
	public synchronized boolean playerWins(int id)
	{
		int index=indexFromId(id);
		if(map.getTile(getPlayersPosition(id))=='E'&& Integer.parseInt(players.get(index)[2])>=(map.getGoldRequired()))
		{
			return true;
		}
		return false;
	}

	/**
	 * @return Disconnect 
	 */
	public String playerQuit(int id)
	{
		removePlayer(id);
		return "Disconnect";
	}

}

