package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

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


                            channel.pipeline()
                                    //添加解码处理器
                                    .addLast(new StringDecoder(CharsetUtil.UTF_8))
                                    //添加编码处理器
                                    .addLast(new StringEncoder(CharsetUtil.UTF_8))
                                    .addLast(new MyClientHandler());
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
