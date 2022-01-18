package com.wry.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author 王瑞玉
 * @since 2022/1/18 13:31
 */
public class NettyServer {
    public static void main(String[] args) {
        new NettyServer().bing(9999);
    }

    private void bing(int port) {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {

                            System.out.println("链接报告开始");
                            System.out.println("链接报告信息：有一客户端链接到本服务端");
                            System.out.println("链接报告IP:" + channel.localAddress().getHostString());
                            System.out.println("链接报告Port:" + channel.localAddress().getPort());
                            System.out.println("链接报告完毕");

                            channel.pipeline()
                                    .addLast(new MyServerHandler());
                        }
                    });


            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
