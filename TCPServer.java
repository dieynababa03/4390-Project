import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

class TCPServer {

  public static void main(String argv[]) throws Exception {
    String clientMath;

    String math[];

    ServerSocket welcomeSocket = new ServerSocket(6789);
    // Array list to store clients
    List<ClientInfo> connectedClients = new ArrayList<>();
    while (true) {

      Socket connectionSocket = welcomeSocket.accept();

      BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

      ClientInfo client = new ClientInfo(connectionSocket);

      // adding new client to array list
      connectedClients.add(client);

      System.out.println("Received from addr: " + client.getAddress());

      DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

      clientMath = inFromClient.readLine();

      math = clientMath.split(" ");

      int calculation = 0;
      if (math[1].equals("+")) {
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