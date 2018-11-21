# ChatServer

Overview:

  Chat Server is application where two or more users can communicate each other. Based on Maven it is creating two jar's files - one for the c
  the server and one for the client. When server.jar file it is started every user can join the chat room on specific port given and IP - "localhost".


Guide:

For Server.jar: 
1.Open command prompt and type the following command: -java -jar "server.jar" -[port number].
3. Server started.

For Client.jar:
1. Open command prompt and type the following command: -java -jar client.jar -[IP] -[port number]. The IP must be for this example "localhost".
2, User must enter his name and it is connected to the server.


Functionality:
  
  1. Server.jar file - it is used for starting the server. Using "ServerSocket" which is listening for specific port. It is making a connection request, where clients are connectiong on the server based on local port and IP. It is
  starting a new thread for every single user which is connected to the server.
  
  2. Client.jar file - it is using a socket to connect the chat server and it have 2 Threads , one is for reading and the other is for writing
  in the server. When the user type: "quit" it's disconnecting from the server.
  
  
