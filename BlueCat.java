/**
 * BlueCat.java
 * Listens for ClientCommunicate and ServerCommunicate
 * 
 * @author Arielle Davenport
 * 
 * Last modified: 4-15-15
 */
import java.io.*;


public class BlueCat {
	public static void main(String[] args){
		
		//check if argument is empty or if it was not typed correctly
		if(args.length == 0){
			System.out.println("Usage:");
			//has to be either server or client
			//since this one file will handle both
			System.out.println(" java BlueCat -l <port number> 'to run server'");
			System.out.println("java BlueCat localhost <port number> 'to run client'");
			System.out.println("java BlueCat localhost -r <filename> <portnumber> 'to readfile line by line '");
			System.out.println("java BlueCat localhost -f <filename> <portnumber> 'to readfile in client'");
			System.out.println("java BlueCat localhost -o <filename> <portnumber> 'to write a file to client'");
			System.out.println("java BlueCat localhost -p <filename> <portnumber> 'to write a file to server'");
			return;
		}//end if 
		boolean serverconnect = false;
	
		int port_number; // Integer.valueOf( args[1] ).intValue();
		int i = 0;
		
		for(i = 0; i < args.length; i++){
		
			if(args[i].equals("-l")){
				//then is server
				serverconnect = true;
			}
		}
		
		ServerCommunicate server = null;
		ClientCommunicate client = null;
		
		port_number = Integer.valueOf( args[i-1] ).intValue();
		
		if(serverconnect){
			boolean one_line = false;
			String file_oneline = null;
			String whole_file = null;
			boolean server_file = false;
			boolean packet = false;
			String packet_file = null;
			System.out.println("I am the server");
			for(i = 0; i < args.length; i++){
				if(args[i].equals("-r")){
					one_line = true;
					file_oneline = args[i+1];
				}else if(args[i].equals("-f")){
					server_file = true;
					whole_file = args[i+1];
				}else if(args[i].equals("-p")){
					packet = true;
					packet_file = args[i+1];
				}
			}//end for loop
	
			if(one_line){
				System.out.println("This does one line file");
				try
				{
					if(packet){
						server= new ServerCommunicate( port_number );
						server.serverOneBYOneFile(file_oneline, packet_file);
					}else{
						server= new ServerCommunicate( port_number );
						server.fileline( file_oneline);
					}
					
					server.close();
				}
				catch ( Exception e )
				{
					System.out.println( e.getMessage() );
				}
			}else if(server_file){
				System.out.println("Prints Whole File");
				try
				{
					if(packet){
						server= new ServerCommunicate( port_number );
						server.serverReceiveWholeFile(whole_file, packet_file);
	
					}else{
						server= new ServerCommunicate( port_number );
						server.filecommunicate(whole_file);
	
					}
					server.close();

				}
				catch ( Exception e )
				{
					System.out.println( e.getMessage() );
				}
			}
			else{
				try
				{
					server= new ServerCommunicate( port_number );
					server.listen();
					server.close();
				}
				catch ( Exception e )
				{
					System.out.println( e.getMessage() );
				}
			}
			
		}else{
			System.out.println("I am the client");
			boolean client_oneline = false;
			boolean client_file = false;
			boolean client_write = false;
			String oneline_file = null;
			String whole_file = null;
			String write_file = null;
			
			for(i = 0; i < args.length; i++){
				if(args[i].contains("-r")){
					client_oneline = true;
					oneline_file = args[i+1];
					
				}else if(args[i].equals("-f")){
					 client_file = true;
					 whole_file = args[i+1];
				}else if(args[i].equals("-o")){
					client_write = true;
					write_file = args[i+1];
				}
			}//end for loop
			
			
			if(client_oneline){
				System.out.println("this will print one line at a time");

				try
				{
					if(client_write){
						client = new ClientCommunicate(port_number);
						client.writeoneline(oneline_file, write_file);
						
					}else{
						
						client = new ClientCommunicate(port_number);
						client.fileline(oneline_file);
					}
					client.close();

				}
				catch ( Exception e )
				{
					System.out.println( e.getMessage() );
				}
				//client.close();
				
			}else if(client_file){
				System.out.println("Client prints whole file");
				try
				{	
					if(client_write){
						BufferedReader file= new BufferedReader( new FileReader( whole_file ) );
						
						String s = "";
						// read the contents and output it to stdout
						String line= null;
						while( (line= file.readLine()) != null )
							s += line ;
						file.close();
						
						client = new ClientCommunicate(port_number);
						client.writefile(s, write_file);
						//write file if it wants right
						
					}else{
						BufferedReader file= new BufferedReader( new FileReader( whole_file ) );
						
						String s = "";
						// read the contents and output it to stdout
						String line= null;
						while( (line= file.readLine()) != null )
							s += line ;
						file.close();
						
						client = new ClientCommunicate(port_number);
						client.communicate(s);
						//write file if it wants right

						//client.close();
					}
	
					client.close();
				}
				catch ( Exception e )
				{
					System.out.println( e.getMessage() );
				}
			}else if(client_write){
				System.out.println("Writes to a file");
				try
				{
					client = new ClientCommunicate(port_number);
					// open the file for reading
					PrintWriter file= new PrintWriter( new BufferedWriter( new FileWriter(write_file) ) );
					
					// read from standard input and write to file
					BufferedReader input= new BufferedReader( 
							 new InputStreamReader(System.in) );
					System.out.println( "Enter text; press CTRL+Z on Windows to quit; CTRL+D on Linux to quit" );
					String line= null;
					while( (line= input.readLine()) != null )
						file.println( line );
					file.close();
				}
				catch ( Exception e )
				{
					System.out.println( e.getMessage() );
				}
			}
			else{
				try
				{
					//copy this 
					// input the message from standard input
					BufferedReader reader= new BufferedReader( 
							 new InputStreamReader(System.in) );
					System.out.print( "Enter message: " );
					String message= reader.readLine();
					
					//object of client communicator class
				    client = new ClientCommunicate( port_number );
					client.communicate(message);
					client.close();
				}
				catch ( Exception e )
				{
					System.out.println( e.getMessage() );
				}
				
			}
		}
		
	}
}
