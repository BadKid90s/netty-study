package netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * 自定义解码器
 *
 * @author 王瑞玉
 * @since 2022/1/18 15:43
 */
public class MyDecoder extends ByteToMessageDecoder {
    //数据包基础长度
    private final int BASE_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List out) throws Exception {

        //基础长度不足，我们设定基础长度为4
        if (in.readableBytes() < BASE_LENGTH) {
            return;
        }

        while (true) {
            byte b = in.readByte();
            //判断是否是起始
            if (b == 48) {
                // 标记包头开始的index
                in.markReaderIndex();
                break;
            }
        }
        ByteBuf byteBuf = Unpooled.buffer();
        //读取内容
        while (true) {
            byte b = in.readByte();
            //判断是否是结束
            if (b == 48) {
                // 标记包头开始的index
                in.markReaderIndex();
                in.resetReaderIndex();
                break;
            }
            //读取1字节
            byteBuf.writeByte(b);


        }
        String msgLengthStr = byteBuf.toString(CharsetUtil.UTF_8);
        out.add(msgLengthStr);
    }
}
