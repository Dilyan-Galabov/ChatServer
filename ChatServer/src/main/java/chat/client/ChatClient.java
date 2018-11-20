package chat.client;


import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;


public class ChatClient
{
    private String hostname;
    private int port;
    private String userName;


    public ChatClient(String hostname, int port)
    {
        this.hostname = hostname;
        this.port = port;
    }

    public void startClient()
    {
        try
        {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");

            ReadThread readThread = new ReadThread(socket, this);
            readThread.start();

            WriteThread writeThread = new WriteThread(socket, this);
            writeThread.start();

        }
        catch (UnknownHostException ex)
        {
            System.out.println("Server not found: " + ex.getMessage());
        }
        catch(ConnectException ex)
        {
            System.out.println("Can not connect to the server!");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }


    public void setUserName(String userName)
    {
        this.userName = userName;
    }


    public String getUserName()
    {
        return this.userName;
    }


    public static void main(String[] args)
    {
        if (args.length < 2)
        {
            return;
        }


            String hostname = args[0];
            int port = Integer.parseInt(args[1]);

            ChatClient client = new ChatClient(hostname, port);
            client.startClient();

    }
}
