package humanPlayer;

public class ShowGamesThread extends Thread {
	
	private Run myRun;

	
	public ShowGamesThread(Run myRun) {
		this.myRun=myRun;
	}
	
	
	public void run() {
		while(true) {
			myRun.sendToServer("showGames");
			try {
				Thread.sleep(1000);
			}
			catch(Exception e) {
				System.out.println("SHOWGAMES THREAD couldnt sleep");
			}
		}
	}
}
