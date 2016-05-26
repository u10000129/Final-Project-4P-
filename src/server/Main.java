package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	
	
		int portNum;
		private ServerSocket serverSocket;
		private List<ConnectionThread> connections = new ArrayList<ConnectionThread>();
		
		public Main(int portNum) {
			try {
				this.serverSocket = new ServerSocket(portNum);// create the server socket in contructor
				System.out.printf("Server starts listening on port %d.\n", portNum);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		
		public void runForever() {
			System.out.println("Server starts waiting for client.");
			while(true) {	//make it waiting for client forever
				
				
				try {
					Socket connectionToClient = this.serverSocket.accept();	// new served client thread start
					System.out.println( "Get connection from client "
							 + connectionToClient. getInetAddress() + ":"
							 + connectionToClient. getPort());
					ConnectionThread connThread = new ConnectionThread(connectionToClient);
					connThread.start();
					this.connections.add(connThread);	// add the connection thread to a ArrayList, so that we can access it afteresrd.
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
		
		class ConnectionThread extends Thread {
			Socket socket;
			private BufferedReader reader;
			private PrintWriter writer;
			public ConnectionThread(Socket socket) {	//set writer and reader for the ConnectionThread
				this.socket = socket;
				try {
					this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
					this.writer = new PrintWriter(new OutputStreamWriter( this.socket.getOutputStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("construct port "+socket.getPort());
			}
			
			public void sendMessage(String message){	//this method can send message
				this.writer.println(message);
				this.writer.flush();
			}
			
			public String receiveMessage() {	//this method can receive message
				String message = null;
				try {
					message = reader.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return message;			
			}
			
			public void run(){	
				
			}		
		}
		
		public void broadcast(String message) {	//send message to all client
			for (ConnectionThread connection: connections) {
				connection.sendMessage(message);
			}
			System.out.println("host broadcast: "+message);			
		}
		
		public String receiveMessage(int clientNum) {
			String message = connections.get(clientNum).receiveMessage();
			System.out.println("receive from "+clientNum+": "+message);
			return message;
		}
		
		public void constructGameThread(Main transmission) {	//construct a gameThread
			GameThread gameThread = new GameThread(transmission);
			gameThread.start();			
		}
		
		public int getClientNum() {
			return this.connections.size();
		}
		
		public static void main(String[] args) {	
			
			Main server = new Main(8000);	//construct a server
			server.constructGameThread(server);	//construct a gameThread
			server.runForever();	//waiting for client forever
			
		}
}

