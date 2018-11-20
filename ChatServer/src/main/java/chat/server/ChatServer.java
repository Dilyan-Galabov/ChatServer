package chat.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


public class ChatServer
{
    private int port;
    private static Set<String> userNames = new HashSet<>();
    private static Set<UserThread> userThreads = new HashSet<>();


    public ChatServer(int port)
    {
        this.port = port;
    }

    public void startServer()
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {

            System.out.println("Chat Server is listening on port " + port);

            while (true)
            {

                Socket socket = serverSocket.accept();

                UserThread user = new UserThread(socket, this);
                userThreads.add(user);
                user.start();

            }

        }
        catch (IOException ex)
        {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }



    public void deliverMessage(String message, UserThread excludeUser)
    {
        for (UserThread user : userThreads)
        {
            if (user != excludeUser)
            {
                user.sendMessage(message);
            }
        }
    }


    public void addUserName(String userName)
    {
        boolean isAdded = userNames.add(userName);
        if (isAdded)
        {
            userNames.add(userName);
            System.out.println("The user " + userName + " joins the chat room!");
        }

    }


    public void removeUser(String userName, UserThread aUser)
    {
        boolean isRemoved = userNames.remove(userName);
        if (isRemoved)
        {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }


    public Set<String> getUserNames()
    {
        return userNames;
    }


    public boolean hasUsers()
    {
        return !userNames.isEmpty();
    }


    public static void main(String[] args)
    {

        if (args.length < 1)
        {
            System.out.println("Syntax: java -jar Server <port-number>");
            System.exit(0);
        }

        try
        {
            int port = Integer.parseInt(args[0]);

            ChatServer server = new ChatServer(port);
            server.startServer();
        }
        catch (NumberFormatException ex)
        {
            System.out.println("Wrong input parameter !");
        }

    }

}
