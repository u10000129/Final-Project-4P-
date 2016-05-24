package server;

public class GameThread extends Thread{
	
	public Main transmission;
	
	public GameThread(Main transmission) {
		this.transmission = transmission;
	}

	
	public void run() {
		System.out.println("1");
		while(transmission.getClientNum()<2) {for(int i=0;i<100;i++);}	//wait for client
		System.out.println("2");
		transmission.broadcast("test123");
		transmission.receiveMessage(0);
		transmission.receiveMessage(1);
	}

}
