import java.io.*;
import java.net.*;

public class SocketClient{

	public void run(){

		//Read the port number from a text file
		BufferedReader br = new BufferedReader(new FileReader("portnumber.txt"));
		String Port = br.readline();
		br.close();

		//Convert string to int 
		int Portnumber = Integer.parseInt(Port);

		//Open the socket
		Socket MyClient;
		MyClient = new Socket("localhost", Portnumber);

		//Create an input stream
		DataInputStream input = null;
		//Create an output stream
		DataOutputStream output = null;

		//WRITE WHAT WE WANT TO SEND OR RECEIVE HERE


		//Close the input, output stream and thread
		try{
			output.close();
			input.close();
			MyClient.close();

		}catch(Exception e)
		{
			System.out.println(e);
		}

	}

}