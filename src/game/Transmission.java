package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Transmission {
	/*private String destinationIPAddr;
	private int destinationPortNum;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private  ClientThread connection;
	
	public Transmission() {
		this.setIPAddress("127.0.0.1").setPort(8000).connect();	//set client socket
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
	}
	
	public void run() {		
				
	}
	
}

	public void sendMessage( String message) {	//this method can be call outside the class
		writer.println(message);
		 writer.flush();
		System.out.println("send: "+message);
	}
	
	public String receiveMessage() {
		String message = null;
		try {
			message = this.reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;		
	}*/

}
