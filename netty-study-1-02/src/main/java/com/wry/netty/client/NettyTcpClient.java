package com.wry.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author 王瑞玉
 * @since 2021/12/23 16:46
 */
public class NettyTcpClient {
    public static void main(String[] args) throws InterruptedException {
        //workerGroup处理与客户端业务
        NioEventLoopGroup eventLoopGroup  = new NioEventLoopGroup();

        try {
            //创建服务器端启动对象，配置参数
            Bootstrap bootstrap = new Bootstrap();
            //使用链式编程
            bootstrap
                    //设置连个线程组
                    .group(eventLoopGroup )
                    // 说明客户端通道的实现类（便于 Netty 做反射处理）
                    .channel(NioSocketChannel.class)
                    //设置workerGroup中的EventLoop对应的管道处理器
                    .handler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline增加处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            channelPipeline.addLast(new NettyClientInHandler());
                            // 可以继续调用 socketChannel.pipeline().addLast()
                            // 添加更多 Handler
                        }
                    });
            System.out.println("客户端-->客户端准备好了！");

            //绑定一个端口，并且同步，生成一个ChannelFuture对象
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9999);

            //对关闭通道事件进行监听
            channelFuture
                    .channel()
                    .closeFuture()
                    .sync();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //发送异常时进行优雅的关闭
            eventLoopGroup .shutdownGracefully();
        }
    }
}
