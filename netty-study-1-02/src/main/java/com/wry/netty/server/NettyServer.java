package com.wry.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author 王瑞玉
 * @since 2021/12/14 10:30
 */
public class NettyServer {
    public static void main(String[] args) {
        new NettyServer().bing(7397);
    }

    private void bing(Integer port) {
        //配置服务端NIO线程组
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(parentGroup, childGroup)
                //非阻塞模式
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childHandler(new MyChannelInitializer())
                .childHandler(new MyServerHandler());

        try {
            ChannelFuture f = b.bind(port).sync();
                f.channel()
                    .closeFuture()
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }

    }
}
