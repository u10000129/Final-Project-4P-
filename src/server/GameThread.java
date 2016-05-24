package server;

public class GameThread extends Thread{
	
	public Main transmission;
	
	public GameThread(Main transmission) {
		this.transmission = transmission;
	}

	
	public void run() {		
		while(transmission.getClientNum()<1) {for(int i=0;i<100;i++);}	//wait for client		
		transmission.broadcast("test123");
		transmission.receiveMessage(0);
		//transmission.receiveMessage(1);
	}

}
