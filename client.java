//Java program for Client
import java.net.*;
import java.io.*;

public class client
{
    //initialize oscket and input output streams
    private Socket socket = null;
    private BufferedReader ServerOutput = null;
    private BufferedReader ClientInput = null;
    private BufferedWriter ClientOutput = null;
    private boolean terminated = false;

    //constructor to put IP address and port number
    public client(String address, int port)
    {
        //establish connection
        try
        {
            socket = new Socket(address, port);

            ClientInput = new BufferedReader(new InputStreamReader(System.in));
            ClientOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ServerOutput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String output = "";
            try
            {
                output = ServerOutput.readLine();
                if (output.equals("-1"))
                    System.out.println("receive: incorrect operation command.");
                else if (output.equals("-2"))
                    System.out.println("receive: number of inputs is less than two.");
                else if (output.equals("-3"))
                    System.out.println("receive: number of inputs is more than four.");
                else if (output.equals("-4"))
                    System.out.println("receive: one or more of the inputs contain(s) non-number(s).");
                else if (output.equals("-5"))
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
            
            //keep reading
            while (!terminated)
            {
                try
                {
                    String line = "";
                    //send initial input to server
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

                    //interpret server responses
                    try
                    {
                        output = ServerOutput.readLine();
                        if (output.equals("-1"))
                            System.out.println("receive: incorrect operation command.");
                        else if (output.equals("-2"))
                            System.out.println("receive: number of inputs is less than two.");
                        else if (output.equals("-3"))
                            System.out.println("receive: number of inputs is more than four.");
                        else if (output.equals("-4"))
                            System.out.println("receive: one or more of the inputs contain(s) non-number(s).");
                        else if (output.equals("-5"))
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
                    System.out.println(p);
                    break;
                }
            }
            //close connection
            ClientInput.close();
            ClientOutput.close();
            ServerOutput.close();
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
        if (args.length < 2 || args.length > 2)
        {
            System.out.println("Invalid arguments. Required format: 'java client [serverURL] [port_number]'");
        }
        else 
        {
            client clyde = new client (args[0], Integer.valueOf(args[1]));
        }
    }
}