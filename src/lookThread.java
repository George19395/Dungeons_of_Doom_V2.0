
/**
 *lookThread allows a certain command to be generated in constant intervals
 *version: 1.0
 *release : 25/3/2017
 * @author : The unsung tutor.
 * @author : George
 */
public class lookThread extends Thread{

	private Run myRun;

	/**
	 * constructor of class
	 *
	 */
	public lookThread(Run myRun){
		this.myRun=myRun;
	}

	/**
	 * when the thread call sends to the server the command LOOK all the time with specific delay
	 *
	 */
	public void run(){
		while(true){
			myRun.sendToServer("LOOK");		
			try{
				Thread.sleep(100);
			}
			catch(Exception e){

			}
		}
	}

}
