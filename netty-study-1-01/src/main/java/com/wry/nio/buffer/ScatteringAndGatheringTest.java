package com.wry.nio.buffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering: 将数据写入到buffer时，可以采用buffer数组，依次写入  [分散]
 * Gathering : 将数据读取到buffer时，可以采用buffer数组，依次读取  [聚合]
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        //创建serverSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();


        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        //绑定端口到socket 并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建byteBuffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        //假定接受的消息最长8个字节
        int messagesLength = 8;
        while (true) {
            int byteRead = 0;
            while (byteRead < messagesLength) {
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                System.out.println("目前读取的字节数：" + byteRead);
                // 使用流打印，查看当前buffer的position和limit
                Arrays.asList(byteBuffers).stream()
                        .map(byteBuffer -> "position" + byteBuffer.position() + ", limit:" + byteBuffer.limit())
                        .forEach(System.out::println);
            }

            //将所有的buffer进行反转
            Arrays.asList(byteBuffers).stream().forEach(Buffer::flip);

            //将数据读出显示到客户端
            long byteWirte = 0;
            while (byteWirte < messagesLength) {
                long write = socketChannel.write(byteBuffers);
                byteWirte += write;
                System.out.println("目前写入的字节数：" + byteWirte);
                // 使用流打印，查看当前buffer的position和limit
                Arrays.asList(byteBuffers).stream()
                        .map(byteBuffer -> "position" + byteBuffer.position() + ", limit:" + byteBuffer.limit())
                        .forEach(System.out::println);
            }

            //将所有的buffer进行clear
            Arrays.asList(byteBuffers).stream().forEach(Buffer::clear);

            System.out.println("读取字节数：" + byteRead + ",写入字节数：" + byteWirte + ",文件大小：" + messagesLength);
        }

    }
}
