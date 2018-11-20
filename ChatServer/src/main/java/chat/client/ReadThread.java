package chat.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;


public class ReadThread extends Thread
{
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;


    public ReadThread(Socket socket, ChatClient client)
    {
        this.socket = socket;
        this.client = client;
    }


    public void run()
    {

        while (true)
        {
            try
            {
                InputStream input = socket.getInputStream();
                reader = new BufferedReader(new InputStreamReader(input));

                String response = reader.readLine();
                if (response != null)
                {
                    System.out.println("\n" + response);
                }

                if (client.getUserName() != null)
                {
                    System.out.print(client.getUserName() + ": ");
                }

            }
            catch (SocketException se)
            {
                System.out.println("Quitted.");
                break;
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                break;
            }

        }

    }
}
