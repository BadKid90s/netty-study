# 前言介绍
在微信或者QQ的聊天中我们经常会用到一些群聊，把你的信息发送给所有用户。

那么为了实现群发消息，在netty中我们可以使用ChannelGroup方式进行群发消息。

如果为了扩展验证比如你实际聊天有不同的群，那么可以定义ConcurrentHashMap结构来存放ChannelGroup。

ChannelGroup中提供了一些基础的方法；添加、异常、查找、清空、发放消息、关闭等。

# 开发环境
jdk1.8【jdk1.7以下只能部分支持netty】
Netty4.1.72.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
telnet 测试【可以现在你的win7机器上测试这个命令，用于链接到服务端的测试命令】
