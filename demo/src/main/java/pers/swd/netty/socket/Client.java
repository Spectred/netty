package pers.swd.netty.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        while (true) {
            // 1. 创建Socket
            Socket socket = new Socket("127.0.0.1", 9999);
            // 2. 连接中取出输出流并发送消息
            OutputStream os = socket.getOutputStream();
            String msg = scan();
            os.write(msg.getBytes());
            // 3. 连接中取出输入流并接收消息
            InputStream is = socket.getInputStream();
            byte[] b = new byte[1 << 10];
            int read = is.read(b);
            System.out.println("服务端:" + new String(b, 0, read));
            // 4. 关闭socket
            socket.close();
        }
    }

    private static String scan() {
        System.out.println("输入:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
