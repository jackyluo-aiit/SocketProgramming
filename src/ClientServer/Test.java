package ClientServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ClientServer.MultiClientServer.serverSocket;

// TODO Auto-generated catch block
public class Test {
    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
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
            System.out.println("...Waiting for client...");
            Socket socket = serverSocket.accept();
            MultiClientServer multiClientServer = new MultiClientServer(0227);
            pool.execute(multiClientServer);
        }
    }
}
