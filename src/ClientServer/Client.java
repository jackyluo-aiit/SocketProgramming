package ClientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    static Socket client = null;
    static int status = 1;
    static String name = null;
    public static void main(String[] args) {
        try {
            client = new Socket("localhost", 9999);
            System.out.println("Successfully connected to " + client.getRemoteSocketAddress());
            System.out.print("Please input your name:");
            Scanner s = new Scanner(System.in);
            DataOutputStream username = new DataOutputStream(client.getOutputStream());
            name = s.nextLine();
            username.writeUTF(name);
            DataInputStream warmHello = new DataInputStream(client.getInputStream());
            String hello = warmHello.readUTF();
            System.out.println(hello);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Client c = new Client();
        Thread t = new Thread(c);  // 负责send data
        Read r = new Read(client);
        Thread rt = new Thread(r);  //负责read data

        t.start();
        rt.start();
//        System.out.println("Successfully connected to "+client.getRemoteSocketAddress());
//        System.out.print("Please input your name:");
//        Scanner s = new Scanner(System.in);
//        DataOutputStream username = new DataOutputStream(client.getOutputStream());
//        username.writeUTF(s.nextLine());
//        DataInputStream warmHello = new DataInputStream(client.getInputStream());
//        String hello = warmHello.readUTF();
//        System.out.println(hello);
//        while (true) {
//            System.out.print("Enter:");
//            Scanner scanner = new Scanner(System.in);
//            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
//            String send = scanner.nextLine();
//            dataOutputStream.writeUTF(send);
//            if(send.equals("Bye!")){
//                break;
//            }
//            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
//            String inputString = dataInputStream.readUTF();
//            System.out.println(inputString);
//        }
//        client.close();
    }

    @Override
    public void run() {
        try {
            while (true) {
//                System.out.println("Enter:");
                Scanner scanner = new Scanner(System.in);
                DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
                String send = scanner.nextLine();
                dataOutputStream.writeUTF(send);
                if (send.equals("Bye!")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("--- User "+name+" is logging out ---");
        } finally {
            try {
                if (client != null) {
                    client.close();
                    System.out.println("Client closed.");
                    System.exit(0);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

class Read implements Runnable {
    private Socket socket;

    public Read(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            while (true) {
                DataInputStream dataInputStream = new DataInputStream(this.socket.getInputStream());
                String inputString = dataInputStream.readUTF();
                System.out.println(inputString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
