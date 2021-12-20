package com.wry.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MapperByteBuffer 可让文件直接在内存修改（堆外内存），操作系统不需要拷贝
 */
public class MapperByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");

        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 可以直接修改的下标范围是0-4
         *@param mode       MapMode.READ_WRITE 指定FileChannel的模式
         *@param position   0 可以直接修改的起始位置
         *@param size       5 是映射到内存的大小，即1.txt的多少个字节映射到内存
         * 实际类型是：DirectByteBuffer
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(4, (byte) '9');

        randomAccessFile.close();
    }

}
