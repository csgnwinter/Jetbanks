package Socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {
        DataInputStream input = null;
        ServerSocket mainSocket = null;
        DataOutputStream output = null;
        BufferedReader space = null;

        try {
            /*
             * Creates a server socket, bound to the specified port.
             */
            mainSocket = new ServerSocket(6666);

            // Infinite loop to wait for client
            while (true) {
                System.out.println("Banker waiting for client ... ");

                /*
                 * Listens for a connection to be made to this socket and
                 * accepts it. The method blocks until a connection is
                 * made.
                 */
                Socket socket = mainSocket.accept();
                input = new DataInputStream(socket.getInputStream());

                OutputStream outputStream = socket.getOutputStream();
                output = new DataOutputStream(outputStream);

                space = new BufferedReader(new InputStreamReader(System.in));

                System.out.println("Client has been connected");

                String msgFromClient = "", msgToClient = "";
                // Loop till client is disconnected
                while (true) {
                    try {
                        // Read message from client
                        msgFromClient = input.readUTF();
                    }
                    // Breaks the loop is client is disconnected
                    catch (EOFException eof) {
                        System.out.println("Client have been disconnected.");
                        break;
                    }
                    // Print out message from client
                    System.out.println("Client: " + msgFromClient);
                    // Gets input from banker to send to client
                    System.out.print("You: ");
                    msgToClient = space.readLine();
                    // Writes the banker's input to the client
                    output.writeUTF(msgToClient);
                    output.flush();
                }
            }
        } catch (Exception i) {
            i.printStackTrace();
        }
    }

}