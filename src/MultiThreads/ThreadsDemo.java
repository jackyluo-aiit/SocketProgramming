package MultiThreads;

public class ThreadsDemo implements Runnable{
    public static void main(String[] args) {
        System.out.println("Testing threads!!!");
    }
    @Override
    public void run() {
        new Thread(new ThreadsDemo()).start();
    }
}
