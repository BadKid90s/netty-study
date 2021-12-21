package com.wry.nio.selected;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 王瑞玉
 * @since 2021/12/21 17:51
 */
public class ServerSocketTest {
    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress(6666);
        //绑定端口
        serverSocketChannel.bind(socketAddress);
        //设置为非阻塞,不设置抛 java.nio.channels.IllegalBlockingModeException 异常
        serverSocketChannel.configureBlocking(false);

        //创建Selector
        Selector selector = Selector.open();
        //注册channel 到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            if (selector.select(1000) < 0) {
                System.out.println("等待了1s!");
            }

            //获取有事件的SelectionKey
            Set<SelectionKey> keys = selector.selectedKeys();

            //循环遍历
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                //判断是否是连接事件
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    // 创建客户端channel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置为非阻塞,不设置抛 java.nio.channels.IllegalBlockingModeException 异常
                    socketChannel.configureBlocking(false);
                    //注册channel 到selector
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("from 客户端：" + new String(byteBuffer.array()));
                    //清空buffer
                    byteBuffer.clear();
                }

                //移除掉当前的SelectionKey
                iterator.remove();
            }
        }
    }
}
