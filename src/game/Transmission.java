package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;



public class Transmission {
	private String destinationIPAddr;
	private int destinationPortNum;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private  ClientThread connection;
	
	public HashMap<Integer, List<Integer>> playersMap;
	public HashMap<Integer, List<Integer>> huntersMap;
	public HashMap<Integer, List<Integer>> jewelsMap;
	public boolean gameStatus = false;
	public long time = 0;
	
	public int myX, myY;
	public String name = "abc";
	public int myId;
	
	public Transfer transfer;
	public String jsonString;	
	
	public Transmission() {
		this.setIPAddress("127.0.0.1").setPort(8000).connect();	//set client socket
		transfer = new Transfer();
	}
	
	public Transmission setIPAddress(String IPAddress) {	//this method set IP address
		this.destinationIPAddr = IPAddress;	
		return this;
	}
	
	public Transmission setPort(int portNum) {	//this method set port
		this.destinationPortNum = portNum;		
		return this;
	}
	
public void connect() {	//this method create a clientThread
		
		try {
			this.socket = new Socket(this.destinationIPAddr,this.destinationPortNum);	//create a socket
			this.connection = new ClientThread(socket);	//create a ClientThread
			this.connection.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

class ClientThread extends Thread {
	private Socket socket;	
	public ClientThread(Socket socket) {	//set writer and reader for ClientThread
		this.socket = socket;
		try {
			writer =   new PrintWriter( new	OutputStreamWriter( this.socket. getOutputStream()));
			reader = new BufferedReader( new InputStreamReader( this.socket. getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendMessage( String message) {	//this method send message
		writer.println(message);
		 writer.flush();
		 //System.out.println("send: "+message);
	}
	
	
	
	public void run() {		
		String message = null;
		
		try {
			
			message = reader.readLine();
			System.out.println("receive id: "+message);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myId = Integer.valueOf(message);
		
		
		while(true) {
		try {
			
			message = reader.readLine();
			//System.out.println("receive: "+message);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		transfer.decode(message);		
		time = transfer.getTime();
		gameStatus = transfer.getGameStatus();
		playersMap = (HashMap<Integer, List<Integer>>) transfer.getPlayers();
		huntersMap = (HashMap<Integer, List<Integer>>) transfer.getHunters();
		jewelsMap = (HashMap<Integer, List<Integer>>) transfer.getJewel();
		
		jsonString = transfer.encode(name, myX, myY, jewelsMap, huntersMap);
		
		sendMessage(jsonString);
		
		try {
			sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
}

	public int getMyId() {
		return myId;
	}

	public long getTime() {
		return transfer.getTime();
	}
	public boolean getGameStatus() {
		return gameStatus;
	}
	
	public HashMap<Integer, List<Integer>> getPlayers() {		
		return (HashMap<Integer, List<Integer>>) playersMap;
	}
	
	public HashMap<Integer, List<Integer>> gethunters() {		
		return (HashMap<Integer, List<Integer>>) transfer.getHunters();
	}
	
	public HashMap<Integer, List<Integer>> getJewel() {		
		return (HashMap<Integer, List<Integer>>) transfer.getJewel();
	}
	
	public void setMyPosition(int x, int y) {
		this.myX = x;
		this.myY = y;		
	}
	
	public void setHunters(HashMap<Integer, List<Integer>> hashMap) {
		this.huntersMap = hashMap;
	}
	
	public void setJewel(HashMap<Integer, List<Integer>> hashMap) {
		this.jewelsMap = hashMap;
	}

	public void sendMessage( String message) {	//this method can be call outside the class
		writer.println(message);
		 writer.flush();
		//System.out.println("send: "+message);
	}
	/*
	public String receiveMessage() {
		String message = null;
		try {
			message = this.reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("receive: "+message);
		return message;		
	}*/

}
