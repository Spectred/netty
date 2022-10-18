package pers.swd.netty.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws IOException {
        // 1. 创建线程池，有客户端连接成功就创建一个线程
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 2. 创建Server Socket
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务端已启动");
        while (true) {
            // 3. 监听客户端
            final Socket socket = serverSocket.accept();
            // 4. 处理消息
            executorService.execute(() -> handle(socket));
        }
    }

    private static void handle(Socket socket) {
        try {
            System.out.println(Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
            // 接收消息: 从连接中取出输入流
            InputStream is = socket.getInputStream();
            byte[] b = new byte[1 << 10];
            int read = is.read(b);
            System.out.println("客户端:" + new String(b, 0, read));

            // 响应消息: 取出输出流
            OutputStream os = socket.getOutputStream();
            os.write("World".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
