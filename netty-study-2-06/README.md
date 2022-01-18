# 前言介绍
在前边的案例中使用socket模拟器链接我们的NettyServer，进行通信测试。  
本章节我们写一个helloworld版的NettyClient客户端，与我们的socket模拟器进行通信。  
在netty中客户端与服务端的写法基本类似，注意一些细节即可，这也是netty的强大之处，它把nio流与sokcet封装的相当简单易用。


# 开发环境
jdk1.8【jdk1.7以下只能部分支持netty】
Netty4.1.72.Final【netty3.x 4.x 5每次的变化较大，接口类名也随着变化】
telnet 测试【可以现在你的win7机器上测试这个命令，用于链接到服务端的测试命令】
