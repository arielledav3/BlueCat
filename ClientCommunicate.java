/**
 *ClientCommunicate.java
 * Listens for BlueCat server
 * 
 * @author Arielle Davenport
 * 
 * Last modified: 4-15-15
 */
import java.net.*;
import java.io.*;

public class ClientCommunicate {
	//global variables
	int port_number;
	InetAddress loopback;
	Socket client_socket;
	PrintWriter output;
	BufferedReader input;
	
	//constructor with exception
	public ClientCommunicate(int port) throws IOException{
		port_number = port;
		loopback = InetAddress.getLoopbackAddress();
		//try to connect to server
		client_socket = new Socket(loopback,port_number);
		//grab the output and input streams
		output = new PrintWriter(client_socket.getOutputStream(), true);
		input = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
		
	}
	
	void communicate(String message) throws IOException{
		//send message
		System.out.println("Sending message to the server...");
		output.println(message);
		
		//receive a message
		String response = input.readLine();
		if(response.isEmpty())
			System.out.println("(server did not reply with a message)");
		else{
			System.out.println("Server response:");
			System.out.println(response);
		}
	}
	
	void filecommunicate(String filename) throws IOException{
		// open the file for reading
		BufferedReader file= new BufferedReader( new FileReader( filename ) );
		
		// read the contents and output it to stdout
		String line= null;
		//send message
		System.out.println("Sending message to the server...");
		
		while( (line= file.readLine()) != null ){
			  String onebyone = line;
               output.println(onebyone);
		}
		file.close();
		
		//receive a message
		String response = input.readLine();
		if(response.isEmpty())
			System.out.println("(server did not reply with a message)");
		else{
			System.out.println("Server response:");
			System.out.println(response);
		}
		
	}
	
	void fileline(String filename) throws IOException{
		// open the file for reading
		BufferedReader file= new BufferedReader( new FileReader( filename ) );
		
		// read the contents and output it to stdout
		String line= null;
		//send message
		System.out.println("Sending message to the server...");
		
		while( (line= file.readLine()) != null ){
			  String onebyone = line;
               output.println(onebyone);
		}
		file.close();
		
		//receive a message
		String response = input.readLine();
		if(response.isEmpty())
			System.out.println("(server did not reply with a message)");
		else{
			System.out.println("Server response:");
			
			System.out.println(response);
			
		}
	}
	void writefile(String message, String filename2) throws IOException{
		//send message
		System.out.println("Sending message to the server...");
		output.println(message);

		
		// open the file for reading
		PrintWriter file2= new PrintWriter( new BufferedWriter( new FileWriter(filename2) ) );
		String line2 = input.readLine();
		//receive a message
		//String response = input.readLine();
		if(line2.isEmpty())
			System.out.println("(server did not reply with a message)");
		else{
			System.out.println("Writing to file...:");	
			//String line= null;
			
			file2.println( line2 );
			file2.close();
		}
		System.out.println("Done!");

	}
	void writeoneline(String filename1, String filename2) throws IOException{
		// open the file for reading
		BufferedReader file= new BufferedReader( new FileReader( filename1 ) );
		
		String line= null;
		//send message
		System.out.println("Sending message to the server...");
		
		while( (line= file.readLine()) != null ){
			  String onebyone = line;
               output.println(onebyone);
		}
		file.close();
		
		// open the file for reading
		PrintWriter file2= new PrintWriter( new BufferedWriter( new FileWriter(filename2) ) );
		String line2 = input.readLine();
		//receive a message
		//String response = input.readLine();
		if(line2.isEmpty())
			System.out.println("(server did not reply with a message)");
		else{
			System.out.println("Writing to file...:");	
			//String line= null;
			
			file2.println( line2 );
			file2.close();
		}
		System.out.println("Done!");
	}

	void close(){
		try
		{
			client_socket.close();
		}catch(Exception e){
			//do nothing
		}
	}

}

