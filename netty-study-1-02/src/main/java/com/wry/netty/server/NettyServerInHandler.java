package com.wry.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义一个 Handler，需要继承 Netty 规定好的某个 HandlerAdapter（规范）
 * InboundHandler 用于处理数据流入本端（客户端）的 IO 事件
 * OutboundHandler 用于处理数据流出本端（客户端）的 IO 事件
 *
 * @author 王瑞玉
 * @since 2021/12/23 17:15
 */
public class NettyServerInHandler extends ChannelInboundHandlerAdapter {
    /**
     * 通道就绪时执行
     *
     * @param ctx 上下文对象
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器-->通道准备完毕！");
    }

    /**
     * 读取数据事件（可以读取客户端发送的消息）
     *
     * @param ctx 上下文对象，包含管道（pipeline），通道（channel）,地址(连接地址)
     * @param msg 客户端发送的消息，默认Object类型，需要进行转换
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器-->客户端发送的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器-->客户端地址：" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕后执行
     *
     * @param ctx 上下文对象
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器-->数据读取完毕！");
        // 发送响应给客户端
        // Unpooled 类是 Netty 提供的专门操作缓冲区的工具类，copiedBuffer 方法返回的 ByteBuf 对象类似于NIO 中的 ByteBuffer，但性能更高
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello client! i have got your data.", CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf);
        System.out.println("服务器-->写入数据完毕！");
    }


    /**
     * 发送异常时
     *
     * @param ctx   上下文对象
     * @param cause 异常对象
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("服务器-->发送异常！！");
        ctx.channel().close();
    }
}
