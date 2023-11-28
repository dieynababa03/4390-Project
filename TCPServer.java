import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Queue;
import java.util.LinkedList;
import java.io.FileWriter;

class TCPServer {

  public static void main(String argv[]) throws Exception {

    ServerSocket welcomeSocket = new ServerSocket(6789);
    // Array list to store clients
    List<ClientInfo> connectedClients = new ArrayList<>();
    Queue<ClientInfo> queue = new LinkedList<>();
    FileWriter txt = new FileWriter("output.txt");
    while (true) {

      Socket connectionSocket = welcomeSocket.accept();

      //Thread to handle multiple clients
      new Thread(() -> {
        try{


        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

        ClientInfo client = new ClientInfo(connectionSocket);

        // adding new client to array list
        connectedClients.add(client);

        // adding new client to queue
        queue.add(client);

        System.out.println("Received from addr: " + client.getAddress());

        //Write to file which user connected
        txt.write("User: " + client.getAddress() + " connected\n");

        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

        String clientMath = inFromClient.readLine();

        String math[] = clientMath.split(" ");

        int calculation = 0;
        // User wants to stop calculations
        if (math[0].equals("Exit")) {
          System.out.println("Client '" + client.getAddress() + "' requested to disconnect");
          queue.remove();
          return;
        } else if (math[1].equals("+")) {
          calculation = Integer.parseInt(math[0]) + Integer.parseInt(math[2]);

        } else if (math[1].equals("-")) {
          calculation = Integer.parseInt(math[0]) - Integer.parseInt(math[2]);

        } else if (math[1].equals("*")) {
          calculation = Integer.parseInt(math[0]) * Integer.parseInt(math[2]);

        } else {
          calculation = Integer.parseInt(math[0]) / Integer.parseInt(math[2]);
        }

        System.out.println(calculation);
        outToClient.writeInt(calculation);

        //Write to file that user disonnected and client received calculation
        txt.write("User: " + client.getAddress() + " disconnected and calculation sent\n");
        txt.close();
        inFromClient.close();
        outToClient.close();
        connectionSocket.close();

    }catch(IOException e){
      e.printStackTrace();
    }}).start();

    }

  }
}

// Class to create new clients as well as keep track of the clients info
class ClientInfo {
  private Socket client;
  private Date connectionDate;

  public ClientInfo(Socket client) {
    this.client = client;
    this.connectionDate = new Date();

  }

  public InetAddress getAddress() {
    return client.getInetAddress();
  }

  public Date getDate() {
    return connectionDate;
  }

  public Socket getClient() {
    return client;
  }

}