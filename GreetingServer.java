   // File Name GreetingServer.java

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class GreetingServer extends Thread {
	private ServerSocket serverSocket;
   
	public GreetingServer(int port) throws IOException {
		//insert missing line here for binding a port to a socket

		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(999999);
	}
	
	public class ClientThread extends Thread {
		
		Socket clientSocket;
		
		public ClientThread(Socket clientSocket) {
			this.clientSocket = clientSocket;
			//~ this.threads = threads;
		}
		
		public void run(){
			try{
				/* Read data from the ClientSocket */
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				//System.out.println(">"+in.readUTF());
				
				String clientMsg = in.readUTF().toString();
				
				String[] tokens = clientMsg.split("\\`");
				// tokens[0] == client address
				// tokens[1] == message
				
				System.out.println(tokens[0] + ": " + tokens[1]);
				
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

				/* Send data to the ClientSocket */
				//out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
				
				//
				if(in.readUTF().toString().compareTo("/exit")==0) {
					System.out.println("foo");
					clientSocket.close();
					System.out.println("Server ended the connection to "+ clientSocket.getRemoteSocketAddress());
				}
			}catch(IOException e) {
				
			}
		}
	}

	public void run() {
      boolean connected = true;
      
      ArrayList<Socket> clientSockets = new ArrayList<Socket>();
      ArrayList<ClientThread> clientThreads = new ArrayList<ClientThread>();
      
      while(connected) {
		try {
			System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");

			/* Start accepting data from the ServerSocket */
			//insert missing line for accepting connection from client here]
			Socket clientSocket = serverSocket.accept();
			
			clientSockets.add(clientSocket);
			
			ClientThread clientThread = new ClientThread(clientSocket);
			clientThread.start();
			
			System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
			
			
			
			
			
			// connected = false;
			}catch(SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			}catch(IOException e) {
				//e.printStackTrace();
				System.out.println("Usage: java GreetingServer <port no.>");
				break;
			}
		} 
	}
	public static void main(String [] args) {
		try {
			int port = Integer.parseInt(args[0]);
			Thread t = new GreetingServer(port);
			t.start();
			}catch(IOException e) {
				//e.printStackTrace();
				System.out.println("Usage: java GreetingServer <port no.>");
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Usage: java GreetingServer <port no.> ");
			}
	   }
}
/**
* a) Socket server = serverSocket.accept();
* b) serverSocket = new ServerSocket(port);
**/
