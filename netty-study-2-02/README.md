# 前言介绍
在实际开发中，server端接收数据后我们希望他是一个字符串或者是一个对象类型，而不是字节码，那么； 

1.在netty中是否可以自动的把接收的Bytebuf数据转String，不需要我手动处理？ 答；有，可以在管道中添加一个StringDecoder。

2.在网络传输过程中有半包粘包的问题，netty能解决吗？ 答：能，netty提供了很丰富的解码器，在正确合理的使用下就能解决半包粘包问题。

3.常用的String字符串下有什么样的解码器呢？ 答：不仅在String下有处理半包粘包的解码器在处理其他的数据格式也有，其中谷歌的protobuf数据格式就是其中一个。 

对于String的有以下常用的三种：
- LineBasedFrameDecoder 基于换行
- DelimiterBasedFrameDecoder 基于指定字符串
- FixedLengthFrameDecoder 基于字符串长度


# 开发环境
jdk1.8【jdk1.7以下只能部分支持netty】
Netty4.1.72.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
telnet 测试【可以现在你的win7机器上测试这个命令，用于链接到服务端的测试命令】
