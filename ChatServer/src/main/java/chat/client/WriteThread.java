package chat.client;


import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class WriteThread extends Thread
{
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;


    public WriteThread(Socket socket, ChatClient client)
    {
        this.socket = socket;
        this.client = client;
    }


    public void run()
    {

        try
        {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            Console console = System.console();
            String userName = console.readLine("\nEnter your username: ");
            client.setUserName(userName);
            writer.println(userName);

            String text = null;
            do
            {
                text = console.readLine(userName + ": ");
                writer.println(text);
            }
            while (!text.equals("quit"));

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                // NOOP
            }
        }
    }
}
