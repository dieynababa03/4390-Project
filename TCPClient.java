import java.io.*;
import java.net.*;

class TCPClient {

  public static void main(String argv[]) throws Exception {
    String sentence;
    int modifiedSentence;

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    Socket clientSocket = new Socket("127.0.0.1", 6789);

    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

    DataInputStream inFromServer = new DataInputStream((clientSocket.getInputStream()));

    sentence = inFromUser.readLine();

    
    outToServer.writeBytes(sentence + '\n');

    //Prints equation result
    modifiedSentence = inFromServer.readInt();

    System.out.println("FROM SERVER: " + modifiedSentence);

    clientSocket.close();

  }
}