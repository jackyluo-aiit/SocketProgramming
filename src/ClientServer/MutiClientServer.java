package ClientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MutiClientServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(0227);
        ExecutorService pool = Executors.newFixedThreadPool(2);
        while (true) {
//            Socket server = serverSocket.accept();
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Socket server = serverSocket.accept();
//                        System.out.println("received from "+server.getRemoteSocketAddress());
//                        DataInputStream dataInputStream = new DataInputStream(server.getInputStream());
//                        System.out.println(dataInputStream.readUTF());
//
//                        DataOutputStream dataOutputStream = new DataOutputStream(server.getOutputStream());
//                        dataOutputStream.writeUTF("Thank you! "+server.getRemoteSocketAddress());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
            Socket socket = serverSocket.accept();
            MyThread myThread = new MyThread(socket);
            pool.execute(myThread);
        }
    }
}
