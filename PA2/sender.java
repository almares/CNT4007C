import java.io.*;
import java.net.*;

public class sender {
    //user input command line arguments
    static String hostURL;
    static int portNum;
    static String fileName;

    public static void main (String [] args) 
    {
        //if invalid command line arguments
        if (args.length != 3)
            System.out.println("Invalid command line arguments, format should be as follows: java sender [URL] [portNumber] [MessageFileName]");
        else 
        {
            hostURL = args[0];
            portNum = Integer.parseInt(args[1]);
            fileName = args[2];
            
            try 
            {
                //create sender object
                new sender().createSender(hostURL, portNum, fileName);
            }
            catch (Exception ex) 
            {
                System.out.println("Sender failure: " + ex.getMessage());
            }
        }
    }

    public void createSender (String hostURL, int portNum, String fileName) 
    {
        Socket socket = null;
        BufferedReader senderInput = null;
        PrintWriter senderOutput = null;

        try
        {
            socket = new Socket(hostURL, portNum);
            senderInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            senderOutput = new PrintWriter(socket.getOutputStream(), true);
            
            String ACK;

            try
            {
                //read in the file contents
                FileInputStream fname = new FileInputStream(fileName);
                DataInputStream dataInput = new DataInputStream(fname);
                BufferedReader fileInput = new BufferedReader(new InputStreamReader(dataInput));

                String content;
                int packetSent = 0;

                packet p = new packet();
            
                while ((content = fileInput.readLine()) != null)
                {
                    //split message at whitespaces to separate words
                    String [] split = content.split("\\s+");
                    int cnt = 0;
                    while (cnt < split.length)
                    {
                        //create packet
                        p.createPacket(split[cnt]);
                        senderOutput.println(p.returnMessage());
                        p.ID++;
                        ACK = senderInput.readLine();
                        
                        //increment total packets sent
                        packetSent++;

                        //print out current state, total number of packets sent, the received packet, and resulting action
                        if (ACK.equals("ACK2")) //DROP
                            System.out.println("Waiting: " + ACK + ", " + packetSent + ", DROP, resend Packet" + p.seqNum);  
                        else if (checkACK(ACK, p.seqNum) == true) //PASS
                        {
                            cnt++;
                            if (p.period == true) //if last packet
                                System.out.println("Waiting: " + ACK + ", " + packetSent + ", " + ACK + ", no more packets to send");
                            else System.out.println("Waiting: " + ACK + ", " + packetSent + ", " + ACK + ", send Packet" + p.seqNum);
                        }
                        //CORRUPT
                        else System.out.println("Waiting: " + ACK + ", " + packetSent + ", " + ACK + ", send Packet" + p.seqNum);
                    }
                    //send termination signal upon completion
                    senderOutput.println(-1);
                }
                dataInput.close();
                fileInput.close();
            }
            catch (Exception ex)
            {
                System.err.println("Error: " + ex.getMessage());
            }
        }

        catch (UnknownHostException uex)
        {
            System.err.println("Cannot find host: " + hostURL);
            System.exit(1);
        }
        catch (IOException ioex)
        {
            System.err.println("Couldn't perform operation(s) with the connection: " + ioex.getMessage());
            System.exit(1);
        }

        //close connection and readers/writers
        try
        {    
            senderOutput.close();
            senderInput.close();
            socket.close();
        }
        catch (IOException ioex)
        {
            System.err.println("Couldn't perform operation(s) with the connection: " + ioex.getMessage());
            System.exit(1);
        }
    }

    public boolean checkACK (String ACK, Integer seqNum)
    {
        String acknow = "ACK" + seqNum.toString();
        if (acknow.equals(ACK))
            return true;
        else return false;
    }
}