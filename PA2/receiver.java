import java.io.*;
import java.net.*;

public class receiver
{
    static String hostURL;
    static int portNum;

    public static void main (String [] args)
    {
        //if invalid command line arguments
        if (args.length != 2)
            System.err.println("Invalid command line arguments, format should be as follows: java receiver [URL] [portNumber]");
        else
        {
            hostURL = args[0];
            portNum = Integer.parseInt(args[1]);

            try
            {
                //create receiver object
                new receiver().createReceiver(hostURL, portNum);
            }
            catch (Exception ex)
            {
                System.out.println("Receiver failure: " + ex.getMessage());
            }
        }
    }

    public void createReceiver (String hostURL, int portNum) throws IOException
    {
        Socket socket = null;
        BufferedReader receiverInput = null;
        PrintWriter receiverOutput = null;

        try
        {
            socket = new Socket(hostURL, portNum);
            receiverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            receiverOutput = new PrintWriter(socket.getOutputStream(), true);

            String content;
            String message = "";
            int packetReceived = 0;
            System.out.println("Waiting for sender connection...");

            //create packet
            packet p = new packet();
            while ((content = receiverInput.readLine()) != null)
            {
                //if receiver to be terminated
                if (content.equals("-1"))
                {
                    receiverOutput.println(-1);
                    //print out final message
                    System.out.println("Message: " + message);
                    break;
                }

                //split message at whitespaces to separate packet fields
                p.splitMessage(content);

                //add to final message with next word, increment total packets received
                message = message + p.word + " ";
                packetReceived++;
                
                System.out.println("Waiting " + p.seqNum + ", " + packetReceived + ", " + content + ", " + p.validateMessage());
                receiverOutput.println(p.validateMessage());
            }
        }
        catch (UnknownHostException e)
        {
            System.err.println("Could not find host: " + hostURL);
            System.exit(1);
        }
        catch (IOException ioex)
        {
            System.err.println("Couldn't perform operation(s) with the connection: " + ioex.getMessage());
        }

        receiverInput.close();
        receiverOutput.close();
        socket.close();
    }
}