Client.java:

This file implements a client program that connects to a server using a socket on port 6666. The client can send commands (show, item, or bid) with relevant arguments to the server. Based on the command, it sends specific data and waits to receive a response from the server, which it then prints out.
MyRunnable.java:

This file defines a Runnable class, MyRunnable, which handles individual client requests on the server side. It processes incoming commands (show, item, or bid) by reading data sent from the client and performing actions on a list of items. The show command displays all items, item adds new items, and bid processes bids. The class uses input and output streams for communication with the client.
Server.java:

This file sets up a multi-threaded server using a ServerSocket on port 6666. It creates a thread pool to manage client connections, allowing multiple clients to interact with the server concurrently. The server accepts connections and assigns each client to a new MyRunnable instance, passing along an items list for managing auction items and bids.
