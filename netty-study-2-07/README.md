# 前言介绍
Netty开发中，客户端与服务端需要保持同样的；半包粘包处理，编码解码处理、收发数据方式，这样才能保证数据通信正常。   
在前面NettyServer的章节中我们也同样处理了；半包粘包、编码解码等，为此在本章节我们可以把这些知识模块开发到NettyClient中。  
本章节涉及到的知识点有；LineBasedFrameDecoder、StringDecoder、StringEncoder、ChannelInboundHandlerAdapter等

# 开发环境
jdk1.8【jdk1.7以下只能部分支持netty】
Netty4.1.72.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
telnet 测试【可以现在你的win7机器上测试这个命令，用于链接到服务端的测试命令】
