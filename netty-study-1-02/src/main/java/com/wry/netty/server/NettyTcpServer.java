package com.wry.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author 王瑞玉
 * @since 2021/12/23 16:46
 */
public class NettyTcpServer {
    public static void main(String[] args) throws InterruptedException {
        //创建boosGroup 和workerGroup
        //boosGroup只处理连接请求
        //workerGroup处理与客户端业务
        //两者都是无限循环
        //boosGroup和workerGroup 默认是CPU核数 * 2
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程
            bootstrap
                    //设置连个线程组
                    .group(boosGroup, workerGroup)
                    //设置NioServerSocketChannel作为服务器通道的实现
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到的连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //设置workerGroup中的EventLoop对应的管道处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline增加处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            channelPipeline.addLast(new NettyServerInHandler());
                            // 可以继续调用 socketChannel.pipeline().addLast()
                            // 添加更多 Handler
                        }

                    });
            System.out.println("服务器-->服务器准备好了！");

            //绑定一个端口，并且同步，生成一个ChannelFuture对象
            ChannelFuture channelFuture = bootstrap.bind(9999);

            //对关闭通道事件进行监听
            channelFuture
                    .channel()
                    .closeFuture()
                    .sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //发送异常时进行优雅的关闭
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
