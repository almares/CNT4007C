//Java program for hosting a basic Server
import java.net.*;
import java.io.*;

public class server
{
    //initialize socket and input, output streams
    private Socket socket = null;
    private ServerSocket server = null;
    private BufferedReader ServerInput = null;
    private BufferedWriter ServerOutput = null;
    private boolean isNum = true;
    private boolean terminated = false;
    private String message = "Hello!";

    //port constructor
    public server (int port)
    {
        //begin server and await connection
        try
        {
            server = new ServerSocket(port); //deploy server on port_number
            try
            {
                socket = server.accept(); //accept client request
                System.out.println("get connection from ... " + socket.getRemoteSocketAddress().toString());
                
                //take input from the client socket
                ServerInput = new BufferedReader(new InputStreamReader(socket.getInputStream())); //to read the client's initial output
                ServerOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); //to respond to the client's request
            }
            catch (IOException i)
            {
                System.out.print(i);
            }

            //read messages in from client as until 'terminated'
            while (!terminated)
            {
                try
                {
                    try
                    {
                        ServerOutput.write(message); //send initial output to connected client
                        ServerOutput.newLine();
                        ServerOutput.flush();
                    }
                    catch (IOException i)
                    {
                        System.out.println(i);
                    }
                
                    try
                    {
                        String line = ServerInput.readLine(); //read in client's command line arguments
                        String [] args = line.split(" "); //chunk them into individual operators and values
                        isNum = true;
                        
                        if (args[0].equals("bye")) //if client is exiting
                        {
                            System.out.println("get: " +line+ ", return -5");
                            message = "receive: -5";   
                        }
    
                        else if (args[0].equals("terminate")) //if client is terminating the server
                        {
                            System.out.println("get: " +line+ ", return -5");
                            message = "receive: -5";
                            terminated = true;
                        }
    
                        //add operand command case
                        else if (args[0].equals("add"))
                        {
                            if (args.length < 3) //if -2 code
                            {
                                System.out.println("get: " +line+ ", return -2");
                                message = "receive: -2";
                            }
                            else if (args.length > 5) //if -3 code
                            {
                                System.out.println("get: " +line+ ", return -3"); 
                                message = "receive: -3";
                            }
                            else if (args.length >= 3 && args.length <= 5) //if correct number of inputs
                            {
                               for (int i = 1; i < args.length; i++) //parse length of arguments
                               {
                                   try
                                   {
                                       Integer.parseInt(args[i]); //check for any non-numbers
                                   }
                                   catch (NumberFormatException x) //mark boolean value if invalid arguments
                                   {
                                        isNum = false;
                                   }
                               }
    
                               if (isNum == false) //if -4 code
                               {
                                    System.out.println("get: " +line+ ", return -4"); 
                                    message = "receive: -4";
                               }
                            }
                            if (isNum == true && args.length >= 3 && args.length <= 5) //do addition
                            {
                                int sum = 0;
                                for (int i = 1; i < args.length; i++)
                                {
                                    sum += Integer.parseInt(args[i]);
                                }
                                System.out.println("get: " +line+ ", return: " +sum);
                                message = "receive: " +sum;
                            }
                        }
    
                        //subtract operand command case
                        else if (args[0].equals("subtract"))
                        {
                            if (args.length < 3) //if -2 code
                            {
                                System.out.println("get: " +line+ ", return -2");
                                message = "receive: -2";
                            }
                            else if (args.length > 5) //if -3 code
                            {
                                System.out.println("get: " +line+ ", return -3"); 
                                message = "receive: -3";
                            }
                            else if (args.length >= 3 && args.length <= 5) //if correct number of inputs
                            {
                               for (int i = 1; i < args.length; i++) //parse length of arguments
                               {
                                   try
                                   {
                                       Integer.parseInt(args[i]); //check for any non-numbers
                                   }
                                   catch (NumberFormatException x) //mark boolean value if invalid arguments
                                   {
                                        isNum = false;
                                   }
                               }
    
                               if (isNum == false) //if -4 code
                               {
                                    System.out.println("get: " +line+ ", return -4"); 
                                    message = "receive: -4";
                               }
                            }
                            if (isNum == true && args.length >= 3 && args.length <= 5) //do subtraction
                            {
                                int difference = Integer.parseInt(args[1]);
                                for (int i = 2; i < args.length; i++)
                                {
                                    difference -= Integer.parseInt(args[i]);
                                }
                                System.out.println("get: " +line+ ", return: " +difference);
                                message = "receive: " +difference;
                            }
                        }
    
                        //multiply operand command case
                        else if (args[0].equals("multiply"))
                        {
                            if (args.length < 3) //if -2 code
                            {
                                System.out.println("get: " +line+ ", return -2");
                                message = "receive: -2";
                            }
                            else if (args.length > 5) //if -3 code
                            {
                                System.out.println("get: " +line+ ", return -3"); 
                                message = "receive: -3";
                            }
                            else if (args.length >= 3 && args.length <= 5) //if correct number of inputs
                            {
                               for (int i = 1; i < args.length; i++) //parse length of arguments
                               {
                                   try
                                   {
                                       Integer.parseInt(args[i]); //check for any non-numbers
                                   }
                                   catch (NumberFormatException x) //mark boolean value if invalid arguments
                                   {
                                        isNum = false;
                                   }
                               }
    
                               if (isNum == false) //if -4 code
                               {
                                    System.out.println("get: " +line+ ", return -4");
                                    message = "receive: -4";
                               }
                            }
                            if (isNum == true && args.length >= 3 && args.length <= 5) //do multiplication
                            {
                                int product = Integer.parseInt(args[1]);
                                for (int i = 2; i < args.length; i++)
                                {
                                    product *= Integer.parseInt(args[i]);
                                }
                                System.out.println("get: " +line+ ", return: " +product);
                                message = "receive: " +product;
                            }
                        }
    
                        //incorrect operand(s) command case (-1)
                        else
                        {
                            System.out.println("get: " +line + ", return -1");
                            message = "receive: -1";
                        }
                    }
                    catch (IOException i)
                    {
                        System.out.println(i);
                    }
                
                }
                catch(NullPointerException p)
                {
                    try
                    {
                        //close connections
                        socket.close();
                        ServerInput.close();
                        ServerOutput.close();
                    }
                    catch (IOException i)
                    {
                        System.out.println(i);
                    }
                    try
                    {
                        socket = server.accept(); //accept next prospective client request
                        System.out.println("get connection from ... " + socket.getRemoteSocketAddress().toString());
                
                        //establish reader and writer for client socket communication once again
                        ServerInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        ServerOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    }
                    catch (IOException i)
                    {
                        System.out.print(i);
                    }
                    message = "Hello!";
                }
            }
            System.out.println("Connection closed");
        }
        catch (IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        if (args.length < 1 || args.length > 1) //ensure valid character arguments when starting the server
        {
            System.out.println("Invalid arguments. Required format: 'java server [port_number]'");
        }
        else
        { 
            server serge = new server (Integer.valueOf(args[0])); //create the server
    
        }    
    }
}
