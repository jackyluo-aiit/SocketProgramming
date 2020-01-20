package ClientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyThread implements Runnable{
    private Socket socket = null;

    public MyThread(Socket serverSocket){
        this.socket = serverSocket;
    }
    @Override
    public void run() {
        try {
            System.out.println("... Client "+socket.getRemoteSocketAddress()+" connected! ...");

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.println("Client from "+socket.getRemoteSocketAddress()+"send: "+dataInputStream.readUTF());

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Connection success!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(socket == null){
                    socket.close();
                    System.out.println("Server socket has close!");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
