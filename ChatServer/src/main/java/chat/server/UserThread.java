package chat.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;


public class UserThread extends Thread
{
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

    public UserThread(Socket socket, ChatServer server)
    {
        this.socket = socket;
        this.server = server;
    }

    public void run()
    {
        try
        {

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            showAllUsers();

            String userName = reader.readLine();
            if (userName != null)
            {
                server.addUserName(userName);
            }

            String serverMessage = "New user connected: " + userName;
            server.deliverMessage(serverMessage, this);

            String clientMessage;
            do
            {
                clientMessage = reader.readLine();
                serverMessage = userName + " : " + clientMessage;
                server.deliverMessage(serverMessage, this);
            }
            while (!clientMessage.equals("quit"));

            server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " has quitted.";
            server.deliverMessage(serverMessage, this);

        }
        catch(SocketException se )
        {
            System.out.println("User disconnected ");
        }
        catch (IOException ex)
        {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void showAllUsers()
    {
        if (server.hasUsers())
        {
            writer.println("Connected users: " + server.getUserNames());
        }
        else
        {
            writer.println("No other users connected");
        }
    }


    public void sendMessage(String message)
    {
        writer.println(message);
    }

}
