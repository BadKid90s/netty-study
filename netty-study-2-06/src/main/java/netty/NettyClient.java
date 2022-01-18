package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author 王瑞玉
 * @since 2022/1/18 15:23
 */
public class NettyClient {
    public static void main(String[] args) {
        new NettyClient().bing("127.0.0.1", 9999);
    }

    private void bing(String address, int port) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.AUTO_READ, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            System.out.println("链接报告开始");
                            System.out.println("链接报告信息：本客户端链接到服务端。channelId：" + channel.id());
                            System.out.println("链接报告完毕");
                        }
                    });

            ChannelFuture future = null;

            future = b.connect(address, port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }
}
