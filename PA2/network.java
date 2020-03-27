import java.io.*;
import java.net.*;
import java.util.*;

public class network 
{
	//create ArrayList of objects for threads
	static List<Object> allMessages = new ArrayList<Object>();
    static ServerSocket serverSocket;

    //multithreaded class for communicating between hosts
    public static class MessageThread extends Thread 
    {
        private Socket socket = null;
        MessageThread thread = null;
        
        //ID variable to distinguish between sender and receiver
        int id = 0;

        public MessageThread (Socket socket) 
        {
            this.socket = socket;
            allMessages.add(this);
            id = allMessages.size() - 1;
        }

        //send message to output PrintWriter
        public void send (String packMsg) 
        {
            PrintWriter networkOutput = null;
            try 
            {
                networkOutput = new PrintWriter(socket.getOutputStream(), true);
                networkOutput.println(packMsg);	
            }
            catch (IOException ioex) 
            {
                System.err.println("Couldn't perform I/O: " + ioex.getMessage());
            }
        }

        public void run() 
        {
            String message = "";
            String ACK2 = "ACK2";
            String ACK1 = "ACK1";
            String ACK0 = "ACK0";
            
            try 
            {
                PrintWriter networkOutput = null;
                BufferedReader networkInput = null;
                networkOutput = new PrintWriter(socket.getOutputStream(), true);
                networkInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                //as long as there are messages in the input buffer
                while ((message = networkInput.readLine()) != null) 
                {
                    //if sent termination signal
                    if (message.equals("-1")) 
                    {
                        //if sent by sender thread
                        if (id == 1)
                            //send -1 termination signal to receiver 
                            switchThread("-1");
                        break; 
                    }

                    //split message at whitespaces
                    String[] split = message.split("\\s+");

                    //generate random value between 0 and 1
                    double rand = Math.random();
                    
                    //PASS 50% of the time
                    if (rand < 0.5 || split.length == 1) 
                    {
                        //pass acknowledgement of message
                        if (split[0].contains("ACK"))
                            System.out.println("Received: " + split[0] + ", PASS");
                        else System.out.println("Received: ACK" + split[0] + ", PASS");
                        
                        //send to other thread
                        switchThread(message);
                    }
                    //CORRUPT 25% of the time
                    else if (rand >= 0.5 && rand <= 0.75)
                    {
                        //create packet
                        packet p = new packet();
                        p.splitMessage(message);
                        
                        //corrupt the generated packet's checksum
                        p.checksum += 1;
                        System.out.println("Received: Packet" + split[0] + ", " + split[1] + ", CORRUPT");
                        
                        //send to other thread
                        switchThread(p.returnMessage());
                    }
                    //DROP 25% of the time
                    else //send ACK2 back to sender
                    {
                        System.out.println("Received: Packet" + split[0] + ", " + split[1] + ", DROP");
                        
                        //ACK2 back to sender
                        networkOutput.println(ACK2);
                    }
                }
                socket.close();
            }
            catch (IOException ioex) 
            {
                System.err.println("Couldn't perform I/O: " + ioex.getMessage());
            }
        }

        //keep track of the sender/receiver threads and switches between them 
        public void switchThread (String packMsg) 
        {
            //switch threads and send to opposite
            if (id == 0) {
                thread = (MessageThread)allMessages.get(1);
            }
            else {
                thread = (MessageThread)allMessages.get(0);
            }
            thread.send(packMsg);
        }
    }
    
    public static void main (String[] args) throws IOException 
    {
		int portNum;

        //if invalid command line arguments
        if (args.length != 1)
            System.err.println("Invalid command line arguments, format should be as follows: java network [portNumber]");
        else
        {
            portNum = Integer.parseInt(args[0]);
            
            try 
            {
                //initialize channel
                serverSocket = new ServerSocket(portNum);
                System.out.println("Waiting for receiver connection...");

                //Connect to receiver and sender hosts and runs each thread
                new MessageThread(serverSocket.accept()).start();
                new MessageThread(serverSocket.accept()).start();
            } 
            catch (Exception ex) 
            {
                System.out.println("Exception error: " + ex.getMessage());
            }
        }
    }	
}