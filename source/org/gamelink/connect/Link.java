package org.gamelink.connect;

import java.io.*;
import java.util.Enumeration;
import java.util.Scanner;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.NetworkInterface;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * The Link class is responsible for creating the connection between player 1 and player 2
 */
public class Link{
   
    /** Serverside socket to be connected to by the clientside socket */
	private ServerSocket serverSocket = null;

    /** The client side socket used by the client to connect to the serverside socket, using the IP address supplied by the server */
    private Socket clientSocket = null;

    /** Printwritter utilizing the output stream of the socket to send a String to the opposing connected socket */
    private PrintWriter out;

    /** Buffered reader utilizing the inputStream of the socket to receive a String from the opposing connected socket */
    private BufferedReader in;

    /** integer number representing the player number of the user, which is used to determine if the user will act as the client or the server when connected. (1 = server, 2 = client) */
    private int playerNumber = 0;

    /** The integer representation of player 1s player number. */
    private final int PLAYER1 = 1;

    /** Boolean value to indicate the game is in tournament mode thus limiting the command line output */
    private boolean tournamentMode = false;

    /**
    * Sets the value of the playerNumber
    * @param the playerNumber of the current user
    */
    public void setPlayerNumber(int pNum){
    	playerNumber = pNum;
    }

    /**
    * Returns the non-loopback IP address of the local machine, the address is used by the client to specify the hostname of the server
    * @return the InetAddress representation of the non-loopback address of the local machine
    */
	public static InetAddress getNonLoopbackAddress(){
		try {
        	Enumeration en = NetworkInterface.getNetworkInterfaces();
	        while (en.hasMoreElements()) {
	            NetworkInterface i = (NetworkInterface) en.nextElement();
	            for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements();) {
	                InetAddress addr = (InetAddress) en2.nextElement();
	                if (!addr.isLoopbackAddress()) {
	                    if (addr instanceof Inet4Address) return addr;
	                }
	            }
	        }
        } catch (SocketException se){
        	System.out.println("Error: Unable to find IP address");
        	return null;
        }
        System.out.println("Error: No IPV4 non-loopBack address found");
        System.out.println("Please check your network connection and try again");
        System.exit(1);
        return null;
    }

    /** Closes the connection between the client and the server, as well as the input and output buffers used for the transfer of information between client and server. */
    public void disconnect(){
    	try{
            in.close();
            out.close();
            clientSocket.close();
            if (playerNumber == PLAYER1) serverSocket.close();
        } catch (IOException e){
            System.out.println("Error: Unable to close connection");
        }
    }

    /** Establishes the player as the server, creating a serversocket and supplying an IP address to be used by the client to connect. */
    private void connectAsPlayer1()
    {
        InetAddress ip = getNonLoopbackAddress();
        if (!tournamentMode) System.out.println("Address: " + ip.toString().substring(1));
        try{
            serverSocket = createServerSocket(new int[] {10007, 10008, 10009, 10010, 10011, 10012, 10013, 10014}, ip);
            if (!tournamentMode) System.out.println ("Waiting for connection.....");
            clientSocket = serverSocket.accept();  
            if (!tournamentMode) System.out.println ("Connection successful");
            out = new PrintWriter(clientSocket.getOutputStream(), true); 
            in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
        } catch(IOException e){
            System.out.println("Error: Unable to connect to client");
            System.exit(1);
        }
    }

    /**
     * Creates a server socket with the specified ports and ip address
     * @param  ports       An integer array containing all the port numbers to be used
     * @param  ip          The IP address of the server 
     * @return             The created server socket
     * @throws IOException An error indicating that the system was unable to create a server socket
     */
    private ServerSocket createServerSocket(int[] ports, InetAddress ip) throws IOException{
        for (int port : ports){
            try{
                return new ServerSocket(port, 0, ip);
            } catch (IOException ex) {
                continue;
            }
        }
        throw new IOException("no free ports found");
    }

    /**
    * Establishes the player as the Client, creates a socket and uses the IP address provided by the Server to connect to said Server.
    * @param the String representation of the IP address of the Server
    */
	public void connectAsPlayer2(String addr){
        String serverHostname = addr;
        if (!tournamentMode) System.out.println("Attemping to connect to host " + serverHostname);
        try {
            clientSocket = createClientSocket(serverHostname, new int[] {10007, 10008, 10009, 10010, 10011, 10012, 10013, 10014});
            out = new PrintWriter(clientSocket.getOutputStream(), true); 
            in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream())); 
            if (!tournamentMode) System.out.println("Connection successful");
        } catch (IOException e) {
            System.err.println("Error: Unable to connect to host" + serverHostname);
            System.err.println("Please check the hostname or your network connection and try again");
            System.exit(1);
        }
	}

    /**
     * Creates a client socket with the specified host and ip address
     * @param  hostName    The name of the host (player 1's ip)
     * @param  ports       An integer array containing all the port numbers to be used 
     * @return             The created server socket
     * @throws IOException An error indicating that the system was unable to create a client socket
     */
    private Socket createClientSocket(String hostName, int[] ports) throws IOException{
        for (int port : ports){
            try {
                return new Socket(hostName, port);
            } catch (IOException ioe){
                continue;
            }
        }
        throw new IOException("No connection found on ports");
    }




    /**
    * Connects the player as either the Client or the Server depending on the players playerNumber.
    */
	public void connect(boolean useOwnIpAddress){
        if (useOwnIpAddress) tournamentMode = true;
		if (playerNumber == PLAYER1) connectAsPlayer1();
        else {
            if (useOwnIpAddress) connectAsPlayer2(getNonLoopbackAddress().toString().substring(1));
            else connectAsPlayer2(getHostIpAddressFromUser());
        }
	}

    /**
     * Prompts the user to enter the IP address of the host so 
     * @return The Ip address of the host as entered by the user
     */
    public String getHostIpAddressFromUser(){
        String serverHostname = "";
        while (serverHostname.length() == 0){
            Scanner hostNameInputScanner = new Scanner(System.in);
            System.out.println("Please enter the address of your opponent. Example: 10.20.12.169");
            serverHostname = hostNameInputScanner.nextLine();
        }
        return serverHostname;
    }

    /**
    * Sends a String msg to the connected user (used by both Client or Server).
    * @param the String message to be sent to the opposing player.
    */
	public void sendMsg(String msg){
		out.println(msg);
	}

    /**
    * Receives a String msg from the connected user (used by both Server and Client).
    * @return the String message sent by the opposing player.
    */
	public String getMsg(){
		try{
            String msg = in.readLine();/////
            return msg;
		} catch (IOException e){
			System.out.println("Error: Unable to get msg");
			return null;
		}
	}
}