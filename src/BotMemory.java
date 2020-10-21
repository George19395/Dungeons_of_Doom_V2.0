import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class BotMemory {
//	
	Integer id;
	HashMap<Integer, ArrayList<int[]>> mapOfLists = new HashMap<Integer, ArrayList<int[]>>();
	ArrayList<int[]> botView;
	
//	char[][] map=null;
//	char[][] map1;
//	ArrayList<char[]> botMap=new ArrayList<>();
//	
//	
	public BotMemory() {
		
	}
	
	public void addBot(int idr) {
		botView = new ArrayList<>();
		mapOfLists.put(idr, botView);
	}
	
	public ArrayList<int[]> getBotView(int ids){
		return mapOfLists.get(ids);
		
	}
	
	public int getBotWidth(int ids) {
		int width = 0;
		ArrayList<int[]> bot= getBotView(ids);
		
		
		
		return width;
	}

	public int getBotheight(int ids) {
		int height = 0;
		
		
		
		return height;
	}
	public void storeNewViewLocations(int ids,ArrayList<int[]> list) {
		
		ArrayList<int[]> temp = mapOfLists.get(ids);
		ArrayList<int[]> same = new ArrayList<>();
		ArrayList<int[]> different = new ArrayList<>();
		

		if(mapOfLists.get(ids).isEmpty()) {
			mapOfLists.get(ids).addAll(list);
			System.out.println("AAAAAAA");
		}
		
		int count;
		for(int[] item : list) {
			count =0;
			for(int[] item2 : mapOfLists.get(ids)) {
				if(Arrays.equals(item, item2)) {
					System.out.println("Already exists"+Arrays.toString(item));	
				}
				else {
					count++;
				}
			}
			if(count == mapOfLists.get(ids).size()) {
				mapOfLists.get(ids).add(item);
			}
		}

		Collections.sort(mapOfLists.get(ids), new Comparator<int[]>() {
	         public int compare(int[] a, int[] b) {
	              if(a[0] - b[0]==0) //if equals
	              {
	                  return a[1]-b[1];//recompare 
	              }
	              else
	                  return a[0]-b[0];
	         }
	    });
	}
	
}
