package gameActions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.util.ArrayList;
import java.util.*; 
/**
 * Reads and contains in memory the map of the game.
 * @version: 1.0
 * @release : 4/3/2017
 * @author : George
 * @author: The unnamed tutor.
 */

public class Map
{
	String mapName="";
	int gold=2;
	private char[][] map1; 					// declare a map array 
	private static final int LOOK_RADIUS = 5;

	
	
	
	
	/**
	 * @return : The look window around a players coordinates
	 * This code was taken from the given code on moodle
	 */
	/**
	 * @return : The look window around a players coordinates
	 * This code was taken from the given code on moodle
	 */
	public ArrayList<int[]> lookB(int x, int y) {
		char[][] reply = new char[LOOK_RADIUS][LOOK_RADIUS];
		ArrayList<int[]> pos = new ArrayList<>();
		for (int row = 0; row < LOOK_RADIUS; row++) {
			for (int col = 0; col < LOOK_RADIUS; col++) {
				int posX = x + col - LOOK_RADIUS/2;
				int posY = y + row - LOOK_RADIUS/2;
				if (posX >= 0 && posX < getMapWidth() && 
						posY >= 0 && posY < getMapHeight()){
					reply[col][row] = map1[posY][posX];
					pos.add(new int[]{posY,posX});
				}
				else{
					reply[col][row] = '#';
					pos.add(new int[]{posY,posX});
				}
			}
		}
		return pos;
	}
	public String lookBNew(ArrayList<int[]> coordinates) {
//		char[][] reply = new char[6][]; 
		String tiles = "";
//		int posX;
//		int posY;
		for(int i=0; i<coordinates.size();i++) {
//			posX = coordinates.get(i)[0];
//			posY = coordinates.get(i)[1];
			char a=this.getTile(coordinates.get(i));
			tiles+=a;
		}
		
		return tiles;
	}
	

	/**
	 * @return : The look window around a players coordinates
	 * This code was taken from the given code on moodle
	 */
//	public char[][] lookBNew(ArrayList<int[]> coordinates) {
//		
////		int length=coordinates[0].length;
////		System.out.println(getLargest(hey,si));
//		int[] posXs =new int[coordinates.size()];
//		int[] posYs =new int[coordinates.size()];
//
//		for(int i=0; i<coordinates.size();i++) {
//			int posX = coordinates.get(i)[0];
//			int posY = coordinates.get(i)[1];
//			posXs[i]=posX;
//			posYs[i]=posY;
//		}
//		int largestRow = getLargest(posYs,posYs.length);
//		int largestCol = getLargest(posXs,posXs.length);
//		int smallestRow = getSmallest(posYs,posYs.length);
//		int smallestCol = getSmallest(posXs,posXs.length);
//		int rowSize= (largestRow-smallestRow)+1;
//		int colSize= (largestCol-smallestCol)+1;
//		char[][] reply = new char[rowSize][colSize];
////		System.out.println("a+"+posXs.length);
////		System.out.println("b+"+coordinates.size());
//		int t=0;
//		for(int j=0;j<rowSize;j++) {
//			for(int k=0;k<colSize;k++) {
//				if (posXs[t] >= 0 && posXs[t] < getMapWidth() && 
//						posYs[t] >= 0 && posYs[t] < getMapHeight()){
//					reply[k][j] = map1[posXs[t]][posYs[t]];	
//				}
//				else{
//					reply[k][j] = '#';
//				}
//				t++;
//			}
//		}
//		return reply;
//	}
	
	public static int getLargest(int[] a, int total){  
	int temp;  
	for (int i = 0; i < total; i++)   
	        {  
	            for (int j = i + 1; j < total; j++)   
	            {  
	                if (a[i] > a[j])   
	                {  
	                    temp = a[i];  
	                    a[i] = a[j];  
	                    a[j] = temp;  
	                }  
	            }  
	        }  
	       return a[total-1];  
	} 
	
	public static int getSmallest(int[] a, int total){  
		int temp;  
		for (int i = 0; i < total; i++)   
		        {  
		            for (int j = i + 1; j < total; j++)   
		            {  
		                if (a[i] > a[j])   
		                {  
		                    temp = a[i];  
		                    a[i] = a[j];  
		                    a[j] = temp;  
		                }  
		            }  
		        }  
		return a[0];  
	}
	
	
	
	
	
	
	
	

	/**
	 * @return : The look window around a players coordinates
	 * This code was taken from the given code on moodle
	 */
	public char[][] look(int x, int y) {
		char[][] reply = new char[LOOK_RADIUS][LOOK_RADIUS];
		for (int row = 0; row < LOOK_RADIUS; row++) {
			for (int col = 0; col < LOOK_RADIUS; col++) {
				int posX = x + col - LOOK_RADIUS/2;
				int posY = y + row - LOOK_RADIUS/2;
				if (posX >= 0 && posX < getMapWidth() && 
						posY >= 0 && posY < getMapHeight()){
					reply[col][row] = map1[posY][posX];
				}
				else{
					reply[col][row] = '#';
				}
			}
		}
		return reply;
	}
	/**
	 * @return : The look window around a players coordinates
	 * This code was taken from the given code on moodle
	 */
	public char[][] lookAllMap() {
		char[][] reply = new char[getMapHeight()][getMapWidth()];
		for (int row = 0; row < getMapHeight(); row++) {
			for (int col = 0; col < getMapWidth(); col++) {
				int[] coordinates={row,col};
				reply[row][col]=getTile(coordinates);
			}
		}
		return reply;
	}
	/**
     * Prints the map
     */
	public void printMap() 
	{
		for (int row = 0; row < map1.length; row++) 
		{
			for (int column = 0; column < map1[row].length; column++)
			{
				System.out.print(this.map1[row][column] + " ");
				
			}
        System.out.println();
		}
	}

	/**
	 * @return : Gold required to exit the current map.
	 */

	protected int getGoldRequired() 
	{
		return gold;
	}


	/**
	 * @return : The map as stored in memory.
	 */
	protected char[][] getMap() {
		return map1;
	}

	/**
	 * @return : The height of the current map.
	 */
	protected int getMapHeight() 
	{
		return this.map1.length;

	}

	/**
	 * @return : The name of the current map.
	 */
	protected String getMapName() 
	{
		return mapName;
	}

	/**
	 * @return : The width of the current map.
	 */
	protected int getMapWidth() 
	{
		return this.map1[0].length;
	}

	/**
	 * Reads the map from file.
	 *
	 * @param : Name of the map's file.
	 */
	protected void readMap(String map)
	{
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		//String fileName= "example_map.txt";
		BufferedReader br=null;
		FileReader fr=null;
		try
		{
			fr=new FileReader(map);
			br=new BufferedReader(fr);
			br=new BufferedReader(new FileReader(map));
			while((line=br.readLine())!=null)
			{
				lines.add(line);
			}
		}
		catch(Exception e) 
		{
			System.err.println(e.getMessage());
			System.exit(1);
		}	
		mapName=lines.get(0).substring(5);					// gets the map name from the file
		gold=Integer.parseInt(lines.get(1).substring(4));	// gets the gold required from file
		int rowCount=lines.size()-2;
		int columnCount=lines.get(2).length();

		this.map1=new char[rowCount][columnCount];

		for(int i=0;i<rowCount;i++)
		{
			for (int j = 0; j<columnCount; j++)
			{
				map1[i][j] = lines.get(i+2).charAt(j);
			}
		}
	}
	/**
	 * Retrieves a tile on the map. If the location requested is outside bounds of the map, it returns 'X' wall.
	 *
	 * @param coordinates : Coordinates of the tile as a 2D array.
	 * @return : What the tile at the location requested contains.
	 */
	protected char getTile(int[] coordinates) {
		if(!validPosition(coordinates))
		{
			return '#';
		}

		return map1[coordinates[0]][coordinates[1]];

	}

	/**
	 * Updates a floor tile in the map, as it is stored in the memory.
	 *
	 * @param coordinates : The coordinates of the tile to be updated.
	 * @param updatedTile : The new tile.
	 */
	protected void updateMapLocation(int[] coordinates, char updatedTile)
	{
		map1[coordinates[0]][coordinates[1]]=updatedTile;
	}


	/**
	 *checks whether is a valid position to place the player
	 */
	private boolean validPosition(int[] coordinates)
	{
		if(coordinates[0]<0)
		{
			return false;
		}
		if(coordinates[1]<0)
		{
			return false;
		}
		if(coordinates[0]>=getMapHeight())
		{
			return false;
		}
		if(coordinates[1]>=getMapWidth())
		{
			return false;
		}

		return true;
	}

}
