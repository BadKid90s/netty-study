package com.wry.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannelCopy2 {
    public static void main(String[] args) throws IOException {

        copy("/Users/wry/file.txt", "/Users/wry/file3.txt");
    }

    public static void copy(String path1, String path2) throws IOException {
        FileInputStream inputStream = new FileInputStream(path1);
        FileOutputStream outputStream = new FileOutputStream(path2);

        FileChannel channel1 = inputStream.getChannel();
        FileChannel channel2 = outputStream.getChannel();

        channel2.transferFrom(channel1, 0, channel1.size());
        inputStream.close();
        outputStream.close();
    }
}
