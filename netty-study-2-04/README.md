# 前言介绍
netty通信就向一个流水channel管道，我们可以在管道的中间插入一些‘挡板’为我们服务。

比如字符串的编码解码，在前面我们使用new StringDecoder(CharsetUtil.UTF_8)进行字符串解码，
这样我们在收取数据就不需要手动处理字节码。

那么本章节我们使用与之对应的new StringEncoderCharsetUtil.UTF_8)进行进行字符串编码，用以实现服务端在发送数据的时候只需要传输字符串内容即可。
# 开发环境
jdk1.8【jdk1.7以下只能部分支持netty】
Netty4.1.72.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
telnet 测试【可以现在你的win7机器上测试这个命令，用于链接到服务端的测试命令】
