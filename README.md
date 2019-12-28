# PallaServer

With the help of a server, local or not, u can pass a little colorfull ball through a multitude of remote screens.

## How to start the hole thing

**First u have to start the server:**
1. Find the file in `PallaServer/src/Server/Main.java`
2. Execute the file with whatever u have
3. At this point the server will be listening to any clients that wants to connect to the port 6000

**Now u have to connect the clients to the server:**
1. Find the file in `PallaServer/src/Client/ThreadClient.java`
2. Execute the file with whatever u have (u can execute how many clients u want)
3. Than input the ip of the server (localhost is fine too) and its port and press "Connetti al server" (yes i'm ITALIANO)

Whenever u're ready, on the server frame, press "Lancia la pallina" to launch the ball (the button will get disabled the first time used).
The ball wil start from the first client u connected to the second one to the third one and so on.

# Features

1. U can disconnect the client whenever u want (if the clients that got disconnected has the ball in it than the ball gets transferedto the next client)
2. U can connect new clients even if the ball is already running
3. The server displays all the client that are currently connected with itself
4. If the ball has been launched and for whatever reason u disconnected all the clients from the server than the button to launch the ball gets re-enabled so that u can restart again if u want to
