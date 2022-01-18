package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 王瑞玉
 * @since 2022/1/18 13:40
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        SocketChannel channel = (SocketChannel) ctx.channel();

        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端");
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "接收到消息：" + msg);
    }
}
