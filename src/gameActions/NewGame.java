package gameActions;
import java.util.HashMap;
import java.util.Random;

import botPlayer.BotMemory;

public class NewGame {
	GameLogic gameLogic;
	BotMemory botMemory;
	
	public NewGame() {
		botMemory=new BotMemory();
		gameLogic= new GameLogic();
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
	
	
	public static void main(String[] args) {
//		NewGame g=new NewGame();
//		System.out.println(g.generateGameNumber());
		HashMap<Integer,GameLogic> games = new HashMap<Integer, GameLogic>();
		GameLogic gameLogic = new GameLogic();
		games.put(3, gameLogic);
		games.put(1, gameLogic);
		games.put(23,gameLogic);
		games.put(2, gameLogic);
		
		for (Integer game: games.keySet())
			System.out.println(game);
	
	}
}
