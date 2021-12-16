package com.wry.bio.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wry
 */
public class SocketBio {

    public static void main(String[] args) throws Exception {
        buildServer(6666);
    }

    public static void buildServer(Integer port) throws Exception {
        // 创建socket
        ServerSocket serverSocket = new ServerSocket(port);
        // 创建线程池对象
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            final Socket socket = serverSocket.accept();
            executorService.execute(() -> handle(socket));
        }
    }

    private static void handle(final Socket socket)  {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];

            while (true) {
                int read = inputStream.read(bytes);
                if (read == -1) {
                    break;
                }
                System.out.println("接受到的消息：" + new String(bytes,0,read));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
