package com.wry.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author 王瑞玉
 * @since 2021/12/14 10:36
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel)  {
        System.out.println("连接报告开始");
        System.out.println("连接报告信息：有一客户端连接到本服务端");
        System.out.println("连接报告IP:" + channel.localAddress().getHostString());
        System.out.println("连接报告Port:" + channel.localAddress().getPort());
        System.out.println("连接报告完毕");
    }

}
