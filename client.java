//Java program for Client
import java.net.*;
import java.io.*;

public class client
{
    //initialize socket and input, output streams
    private Socket socket = null;
    private BufferedReader ServerOutput = null;
    private BufferedReader ClientInput = null;
    private BufferedWriter ClientOutput = null;
    private boolean terminated = false;
    private String output = null;

    //constructor for server URL and port number parameters
    public client(String address, int port)
    {
        //establish connection
        try
        {
            socket = new Socket(address, port); //deploy client request to appropriate server URL and port number

            ClientInput = new BufferedReader(new InputStreamReader(System.in)); //to read the user's command line inputs
            ClientOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); //to write the requests to the server
            ServerOutput = new BufferedReader(new InputStreamReader(socket.getInputStream())); //to read the response from the server
            
            try
            {
                output = ServerOutput.readLine(); //read in the server's response, interpreting the error code or printing out the successful operation
                if (output.contains("-1"))
                    System.out.println("receive: incorrect operation command.");
                else if (output.contains("-2"))
                    System.out.println("receive: number of inputs is less than two.");
                else if (output.contains("-3"))
                    System.out.println("receive: number of inputs is more than four.");
                else if (output.contains("-4"))
                    System.out.println("receive: one or more of the inputs contain(s) non-number(s).");
                else if (output.contains("-5"))
                {
                    System.out.println("receive: exit.");
                    terminated = true;
                }
                else System.out.println(output);
            }
            catch (IOException i)
            {
                System.out.println(i);
            }
            
            while (!terminated)
            {
                try
                {
                    //send initial input to server
                    String line = "";
                    try
                    {
                        line = ClientInput.readLine(); //String to take input from user
                        ClientOutput.write(line);
                        ClientOutput.newLine();
                        ClientOutput.flush();
                    }
                    catch (IOException i)
                    {
                        System.out.println(i);
                    }

                    //interpret server responses in the same process
                    try
                    {
                        output = ServerOutput.readLine();
                        if (output.contains("-1"))
                            System.out.println("receive: incorrect operation command.");
                        else if (output.contains("-2"))
                            System.out.println("receive: number of inputs is less than two.");
                        else if (output.contains("-3"))
                            System.out.println("receive: number of inputs is more than four.");
                        else if (output.contains("-4"))
                            System.out.println("receive: one or more of the inputs contain(s) non-number(s).");
                        else if (output.contains("-5"))
                        {
                            System.out.println("receive: exit.");
                            terminated = true;
                        }
                        else System.out.println(output);
                    }
                    catch (IOException i)
                    {
                        System.out.println(i);
                    }
                }
                catch (NullPointerException p)
                {
                    break;
                }
            }
            //close connections
            ClientInput.close();
            ClientOutput.close();
            socket.close();
        }
        catch (UnknownHostException u)
        {
            System.out.println(u);
        }
        catch (IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main (String args [])
    {
        if (args.length < 2 || args.length > 2) //ensure valid character arguments when starting the client
        {
            System.out.println("Invalid arguments. Required format: 'java client [serverURL] [port_number]'");
        }
        else 
        {
            client clyde = new client (args[0], Integer.valueOf(args[1])); //create the client
        }
    }
}