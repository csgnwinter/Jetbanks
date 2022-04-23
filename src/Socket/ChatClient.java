package Socket;

import java.io.*;
import java.net.Socket;
import Mechanisms.Screen;

public class ChatClient {
    public static void runClient() {
        Socket clientsocket = null;
        DataInputStream input = null;
        DataOutputStream output = null;
        BufferedReader space = null;

        try {
            /*
             * Creates a stream socket and connects it to the
             * specified port number at the specified IP address.
             */
            clientsocket = new Socket("localhost", 6666);
            input = new DataInputStream(clientsocket.getInputStream());

            /*
             * returns the OutputStream attached with this socket.
             */
            OutputStream outputStream = clientsocket.getOutputStream();
            output = new DataOutputStream(outputStream);

            space = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(Screen.COLOR_BLUE + "You have been connected to the live chat." + Screen.COLOR_RESET);
            System.out.println(Screen.COLOR_BLUE + "You  can now start chatting." + Screen.COLOR_RESET);
            System.out.println(Screen.COLOR_RED + "Type end to end the chat." + Screen.COLOR_RESET);

            String strFromServer = "", strToServer = "";
            // Infinite loop
            while (true) {
                System.out.print("You: ");
                // Read user's input to send to server
                strToServer = space.readLine();
                // End the infinite loop if user enters 'end'
                if (strToServer.equals("end")) {
                    break;
                }
                // Writes the user's input to the server
                output.writeUTF(strToServer);
                output.flush();
                // Get reply message from the server
                strFromServer = input.readUTF();
                // Print reply message to the user
                System.out.println(Screen.COLOR_GREEN + "Banker says: " + strFromServer + Screen.COLOR_RESET);
            }

            // Closes all the I/O stream and socket
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (clientsocket != null) {
                clientsocket.close();
            }

        } catch (Exception exe) {
            exe.printStackTrace();
        }
    }
}