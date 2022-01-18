package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

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

                            channel.pipeline()
//                                    // 基于换行符号
//                                    .addLast(new LineBasedFrameDecoder(1024))
//                                    // 基于指定字符串【换行符，这样功能等同于LineBasedFrameDecoder】
//                                    .addLast(new DelimiterBasedFrameDecoder(1024, false, Unpooled.wrappedBuffer(";".getBytes(StandardCharsets.UTF_8))))
//                                    // 基于最大长度
//                                    .addLast(new FixedLengthFrameDecoder(4))
                                    // 把消息转为字符串
                                    .addLast(new StringDecoder(CharsetUtil.UTF_8))
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
