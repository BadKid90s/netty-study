package com.wry.nio.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 将"hello word! " 写入 file.txt
 */
public class NioFileChannel {

    public static void main(String[] args) throws IOException {
        String hello = "hello word! ";
        String path = "/Users/wry/file.txt";

        writer(hello, path);

        read(path);
    }

    public static void read(String path) throws IOException {
        //创建文件
        File file = new File(path);
        //创建一个输入流 -> channel
        FileInputStream fileInputStream = new FileInputStream(file);
        //获取channel
        FileChannel channel = fileInputStream.getChannel();
        //创建byteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //channel数据转byteBuffer
        channel.read(byteBuffer);

        //输出
        System.out.println(new String(byteBuffer.array(), StandardCharsets.UTF_8));

        channel.close();
        fileInputStream.close();
    }

    public static void writer(String hello, String path) throws IOException {
        //创建一个输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        // 通过fileOutputStream 获取对应的 FileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个byteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //往buffer里写入数据
        byteBuffer.put(hello.getBytes(StandardCharsets.UTF_8));

        //反转buffer
        byteBuffer.flip();
        //把byteBuffer 中的数据写入到 fileChannel中
        fileChannel.write(byteBuffer);

        //关闭
        fileOutputStream.close();
        fileChannel.close();
    }
}
