package ClientServer;

import javax.naming.Name;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MultiClientServer implements Runnable{
    static Socket socket = null;
    static ServerSocket serverSocket = null;
    static ArrayList<Socket> socketList = new ArrayList();
    static HashMap<String, Socket> userList = new HashMap<>();


    public MultiClientServer(int port){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCurrentThreads(ExecutorService pool){
        return ((ThreadPoolExecutor)pool).getActiveCount();
    }

    public boolean logoutUser(String name){
        Socket s = userList.get(name);
        try{
            if(s != null){
                s.close();
                socketList.remove(s);
                userList.remove(name);
                System.out.println("Connection lost, client "+name+" socket has logout");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if (!socketList.contains(s)  && !userList.containsKey(name)){
            return true;
        }
        else return false;
    }

    @Override
    public void run() {
        String name = null;
        try {
            DataInputStream username = new DataInputStream(socket.getInputStream());
            name = username.readUTF();
            userList.put(name, socket);
            DataOutputStream warmHello = new DataOutputStream(socket.getOutputStream());
            warmHello.writeUTF("+++ Hello "+name+" +++");


            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

//            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//            dataOutputStream.writeUTF("Connection success!");

            while(true) {
                String received = dataInputStream.readUTF();
                if(received.equals("Bye!")){
                    break;
                }
                System.out.println("Client " + socket.getPort()+" "+name + " send: " + received);
                for (int i = 0; i < socketList.size(); i++) {
                    Socket s = socketList.get(i);
                    Socket targetSocket = userList.get(name);
                    DataOutputStream so = new DataOutputStream(s.getOutputStream());
                    if (s != targetSocket) { //检测当前连接的socket，如果不是则群发
                        so.writeUTF(received);
                    } else {
                        so.writeUTF("(You) " + received);
                    }
//                DataInputStream dIS = new DataInputStream(socket.getInputStream());
//                String received = dIS.readUTF();
//                System.out.println("Client from "+socket.getRemoteSocketAddress()+"send: "+received);
//                if(received.equals("Bye!")){
//                    break;
//                }
//                DataOutputStream dOS = new DataOutputStream(socket.getOutputStream());
//                Scanner scanner = new Scanner(System.in);
//                dOS.writeUTF(scanner.nextLine());
                }
            }
        } catch (Exception e) {
            System.out.println("--- User "+name+" is logging out ---");
        } finally {
            this.logoutUser(name);
        }
    }

    public static void main(String[] args) {
        System.out.println("************Server is running*************");
        ExecutorService pool = Executors.newFixedThreadPool(100);
        int port = 9999;
        int clientCount = 0;
        MultiClientServer server = new MultiClientServer(port);
        while (true) {
            System.out.println("...Port:"+port+" is waiting for client...");
            try {
                socket = serverSocket.accept();
                System.out.println("+++Client "+ ++clientCount+" connected+++\n+++Client address: "+socket.getRemoteSocketAddress()+"+++\n");
                socketList.add(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            pool.execute(server);
        }
    }
}
