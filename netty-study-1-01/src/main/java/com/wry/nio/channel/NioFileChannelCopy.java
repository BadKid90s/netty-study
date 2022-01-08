package com.wry.nio.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannelCopy {
    public static void main(String[] args) throws IOException {

        copy("/Users/wry/file.txt","/Users/wry/file2.txt");
    }

    public static void copy(String path1, String path2) throws IOException {
        FileInputStream inputStream = new FileInputStream(path1);
        FileOutputStream outputStream = new FileOutputStream(path2);

        FileChannel channel1 = inputStream.getChannel();
        FileChannel channel2 = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        while (true) {
            byteBuffer.clear();
            int read = channel1.read(byteBuffer);
            if (read == -1) {
                break;
            }
            byteBuffer.flip();
            channel2.write(byteBuffer);
        }
        inputStream.close();
        outputStream.close();
    }
}
