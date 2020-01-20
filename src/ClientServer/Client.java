package ClientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost", 0227);
        System.out.println("Successfully connected to "+client.getRemoteSocketAddress());

        DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        dataOutputStream.writeUTF(scanner.nextLine());

        DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
        String inputString = dataInputStream.readUTF();
        System.out.println("Sever said: "+inputString);

        client.close();
    }
}
