//Java program for hosting a basic Server
import java.net.*;
import java.io.*;

public class server
{
    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private BufferedReader ServerInput = null;
    private BufferedWriter ServerOutput = null;
    private boolean isNum = true;
    private boolean terminated = false;
    private boolean byed = false;
    private String message = "Hello!";

    //port constructor
    public server (int port)
    {
        //begin server and await connection
        try
        {
            server = new ServerSocket(port);
            try
            {
                socket = server.accept();
                System.out.println("get connection from ... " + socket.getRemoteSocketAddress().toString());
                
                //take input from the client socket
                ServerInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ServerOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            }
            catch (IOException i)
            {
                System.out.print(i);
            }

            //read message from Client
            while (!terminated)
            {
                try
                {
                    try
                    {
                        ServerOutput.write(message); //Send initial output to connected client
                        ServerOutput.newLine();
                        ServerOutput.flush();
                    }
                    catch (IOException i)
                    {
                        System.out.println(i);
                    }

                while (!byed)
                {
                    try
                    {
                        String line = ServerInput.readLine();
                        String [] args = line.split(" ");
                        isNum = true;
                        
                        if (args[0].equals("bye")) //if client is exiting
                        {
                            System.out.println("get: " +line+ ", return -5");
                            ServerOutput.write("receive: exit.");
                            ServerOutput.newLine();
                            ServerOutput.flush();
                            byed = true;
                        }
    
                        else if (args[0].equals("terminate"))
                        {
                            System.out.println("get: " +line+ ", return -5");
                            terminated = true;
                        }
    
                        //add operand command
                        else if (args[0].equals("add"))
                        {
                            if (args.length < 3) //if -2 code
                            {
                                System.out.println("get: " +line+ ", return -2");
                                ServerOutput.write("receive: number of inputs is less than two.");
                                ServerOutput.newLine();
                                ServerOutput.flush();
                            }
                            else if (args.length > 5) //if -3 code
                            {
                                System.out.println("get: " +line+ ", return -3"); 
                                ServerOutput.write("receive: number of inputs is more than four.");
                                ServerOutput.newLine();
                                ServerOutput.flush();
                            }
                            else if (args.length >= 3 && args.length <= 5) //if correct number of inputs
                            {
                               for (int i = 1; i < args.length; i++) //parse length of arguments
                               {
                                   try
                                   {
                                       Integer.parseInt(args[i]); //check for any non-numbers
                                   }
                                   catch (NumberFormatException x)
                                   {
                                        isNum = false;
                                   }
                               }
    
                               if (isNum == false) //if -4 code
                               {
                                    System.out.println("get: " +line+ ", return -4");
                                    ServerOutput.write("receive: one or more of the inputs contain(s) non-number(s).");
                                    ServerOutput.newLine();
                                    ServerOutput.flush();   
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
                                ServerOutput.write("receive: " +sum);
                                ServerOutput.newLine();
                                ServerOutput.flush();
                            }
                        }
    
                        //subtract operand command
                        else if (args[0].equals("subtract"))
                        {
                            if (args.length < 3) //if -2 code
                            {
                                System.out.println("get: " +line+ ", return -2");
                                ServerOutput.write("receive: number of inputs is less than two.");
                                ServerOutput.newLine();
                                ServerOutput.flush();
                            }
                            else if (args.length > 5) //if -3 code
                            {
                                System.out.println("get: " +line+ ", return -3"); 
                                ServerOutput.write("receive: number of inputs is more than four.");
                                ServerOutput.newLine();
                                ServerOutput.flush();
                            }
                            else if (args.length >= 3 && args.length <= 5) //if correct number of inputs
                            {
                               for (int i = 1; i < args.length; i++) //parse length of arguments
                               {
                                   try
                                   {
                                       Integer.parseInt(args[i]); //check for any non-numbers
                                   }
                                   catch (NumberFormatException x)
                                   {
                                        isNum = false;
                                   }
                               }
    
                               if (isNum == false) //if -4 code
                               {
                                    System.out.println("get: " +line+ ", return -4");
                                    ServerOutput.write("receive: one or more of the inputs contain(s) non-number(s).");
                                    ServerOutput.newLine();
                                    ServerOutput.flush();   
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
                                ServerOutput.write("receive: " +difference);
                                ServerOutput.newLine();
                                ServerOutput.flush();
                            }
                        }
    
                        //multiply operand command
                        else if (args[0].equals("multiply"))
                        {
                            if (args.length < 3) //if -2 code
                            {
                                System.out.println("get: " +line+ ", return -2");
                                ServerOutput.write("receive: number of inputs is less than two.");
                                ServerOutput.newLine();
                                ServerOutput.flush();
                            }
                            else if (args.length > 5) //if -3 code
                            {
                                System.out.println("get: " +line+ ", return -3"); 
                                ServerOutput.write("receive: number of inputs is more than four.");
                                ServerOutput.newLine();
                                ServerOutput.flush();
                            }
                            else if (args.length >= 3 && args.length <= 5) //if correct number of inputs
                            {
                               for (int i = 1; i < args.length; i++) //parse length of arguments
                               {
                                   try
                                   {
                                       Integer.parseInt(args[i]); //check for any non-numbers
                                   }
                                   catch (NumberFormatException x)
                                   {
                                        isNum = false;
                                   }
                               }
    
                               if (isNum == false) //if -4 code
                               {
                                    System.out.println("get: " +line+ ", return -4");
                                    ServerOutput.write("receive: one or more of the inputs contain(s) non-number(s).");
                                    ServerOutput.newLine();
                                    ServerOutput.flush();   
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
                                ServerOutput.write("receive: " +product);
                                ServerOutput.newLine();
                                ServerOutput.flush();
                            }
                        }
    
                        //incorrect operand command
                        else
                        {
                            System.out.println("get: " +line + ", return -1");
                            ServerOutput.write("receive: incorrect operation command.");
                            ServerOutput.newLine();
                            ServerOutput.flush();
                        }
                    }
                    catch (IOException i)
                    {
                        System.out.println(i);
                    }
                }
            }
            catch(NullPointerException p)
            {
                try
                {
                     //close connection
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
                    socket = server.accept();
                    System.out.println("get connection from ... " + socket.getRemoteSocketAddress().toString());
                
                    //take input from the client socket
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
        if (args.length < 1 || args.length > 1)
        {
            System.out.println("Invalid arguments. Required format: 'java server [port_number]'");
        }
        else
        { 
            server serge = new server (Integer.valueOf(args[0]));
    
        }    
    }
}