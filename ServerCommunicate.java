 /**
 * ServerCommunicate.java
 * Listens for an BlueCat client
 * 
 * @author Arielle Davenport
 * 
 * Last modified: 4-15-15
 */
import java.io.*;
import java.net.*;

public class ServerCommunicate {
	//global variables
	int port_number;
	Socket client_socket;
	ServerSocket server_socket;
	PrintWriter out = null;
	
	public ServerCommunicate(int port) throws IOException{
		port_number = port;
		server_socket = new ServerSocket(port_number);
		System.out.println("Listening to port " + Integer.toString(port_number));
	}
	
	public void listen() throws IOException{
		//listen for connection
		client_socket = server_socket.accept();
		
		//grab input and output
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				client_socket.getInputStream()));
		PrintWriter output = new PrintWriter(client_socket.getOutputStream(), true);
		
		//read input
		String inputLine = reader.readLine();
		System.out.println("Recieved from client: ");
		System.out.println(inputLine);
		
		
		//send output back to client
		output.println(inputLine);
		
	}
	
	void filecommunicate(String filename) throws IOException{
		//listen for connection
		client_socket = server_socket.accept();
		
		//grab input and output
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				client_socket.getInputStream()));
		PrintWriter output = new PrintWriter(client_socket.getOutputStream(), true);
		
		//read input
		String inputLine = reader.readLine();
		System.out.println("Recieved from client: ");
		System.out.println(inputLine);
		
		BufferedReader file= new BufferedReader( new FileReader( filename ) );
		
		String s = "";
		// read the contents and output it to stdout
		String line= null;
		while( (line= file.readLine()) != null )
			s += line ;
		file.close();
		
		// send the message back to the client
		output.println(s);
	}
	
	void fileline(String filename) throws IOException{
		//listen for connection
		client_socket = server_socket.accept();
		
		//grab input and output
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				client_socket.getInputStream()));
		PrintWriter output = new PrintWriter(client_socket.getOutputStream(), true);
		
		//read input
		String inputLine = reader.readLine();
		System.out.println("Recieved from client: ");
		System.out.println(inputLine);
		
		BufferedReader file= new BufferedReader( new FileReader( filename ) );
		
		// read the contents and output it to stdout
		String line= null;
		while( (line= file.readLine()) != null )
			// send the message back to the client
			output.println(line);
		file.close();
		
	}
	void serverOneBYOneFile(String filename1, String filename2) throws IOException{
		//listen for connection
		client_socket = server_socket.accept();
		
		//grab input and output
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				client_socket.getInputStream()));
		PrintWriter output = new PrintWriter(client_socket.getOutputStream(), true);
		
		//read input
		String inputLine = reader.readLine();
		System.out.println("Recieved from client: ");
		System.out.println(inputLine);
		
		BufferedReader file= new BufferedReader( new FileReader( filename1 ) );
		
		// read the contents and output it to stdout
		String line= null;
		StringBuilder builder = new StringBuilder();
		while( (line= file.readLine()) != null ){
			// send the message back to the client
			output.println(line);
			builder.append(line);
		}
		file.close();
		
		//write data to file
		//write what you received from client
		// open the file for reading
		PrintWriter file2= new PrintWriter( new BufferedWriter( new FileWriter(filename2) ) );

		//receive a message
		//String response = input.readLine();
		String c = "";
		if(inputLine.isEmpty())
			System.out.println("(server did not reply with a message)");
		else{
			System.out.println("Writing to file...:");	
			//String line= null;
			c += inputLine;
			file2.println("Stuff recieved from client");
			file2.println( c );
			file2.println("");
			file2.println("Content sending to client:");
			file2.println(builder.toString());
			file2.close();
		}
		System.out.println("Done!");
	}
	void serverReceiveWholeFile(String filename1, String filename2) throws IOException{
		//
		//listen for connection
		client_socket = server_socket.accept();
		
		//grab input and output
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				client_socket.getInputStream()));
		PrintWriter output = new PrintWriter(client_socket.getOutputStream(), true);
		
		//read input
		String inputLine = reader.readLine();
		System.out.println("Recieved from client: ");
		System.out.println(inputLine);
		
		BufferedReader file= new BufferedReader( new FileReader( filename1 ) );
		
		String s = "";
		// read the contents and output it to stdout
		String line= null;
		while( (line= file.readLine()) != null )
			s += line ;
		file.close();
		
		// send the message back to the client
		output.println(s);
		
		//write what you received from client
		// open the file for reading
		PrintWriter file2= new PrintWriter( new BufferedWriter( new FileWriter(filename2) ) );
		//String c = "";
		//receive a message
		//String response = input.readLine();
		if(inputLine.isEmpty())
			System.out.println("(server did not reply with a message)");
		else{
			System.out.println("Writing to file...:");	
			//String line= null;
			
			file2.println("Stuff recieved from client");
			file2.println( inputLine );
			file2.println("");
			file2.println("Content sending to client:");
			file2.println(s);
			file2.close();
			

		}
		System.out.println("Done!");
		
		
	}

	
	void close(){
		try{
			client_socket.close();
			System.out.println("Listening concluded; shutting down...");
			server_socket.close();
		}catch(Exception e){
			//do nothing
		}
	}

}


