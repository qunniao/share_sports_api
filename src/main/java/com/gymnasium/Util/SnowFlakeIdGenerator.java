package com.gymnasium.Util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static java.lang.System.currentTimeMillis;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/10 11:49
 * @Description:
 */
public class SnowFlakeIdGenerator {
    //机器启动时的时间戳,需要从机器启动时确定
    private final static long twepoch = currentTimeMillis();

    // 机器标识位数
    private final static long workerIdBits = 5L;
    // 数据中心标识位数
    private final static long datacenterIdBits = 5L;

    // 毫秒内自增位数
    private final static long sequenceBits = 12L;
    // 机器ID偏左移12位
    private final static long workerIdShift = sequenceBits;
    // 数据中心ID左移17位
    private final static long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    //sequence掩码，确保sequnce不会超出上限
    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
    //上次时间戳
    private static long lastTimestamp = -1L;
    //序列
    private long sequence = 0L;
    //服务器ID
    private long workerId = 1L;

    /**
     * long workerIdBits = 5L;
     * -1L 的二进制: 1111111111111111111111111111111111111111111111111111111111111111
     * -1L<<workerIdBits = -32 ,二进制: 1111111111111111111111111111111111111111111111111111111111100000
     *  workerMask= -1L ^ -32 = 31, 二进制: 11111
     */
    private static long workerMask= -1L ^ (-1L << workerIdBits);
    //进程编码
    private long processId = 1L;
    private static long processMask=-1L ^ (-1L << datacenterIdBits);
    private static SnowFlakeIdGenerator idGenerator = null;

    static{
        idGenerator=new SnowFlakeIdGenerator();
    }
    public static synchronized long nextId(){
        return idGenerator.getNextId();
    }

    /**
     * 隐藏构造方法,单例
     */
    private SnowFlakeIdGenerator() {
        System.out.println("实例化了SnowFlakeIdGenerator");
        //获取机器编码
        this.workerId=this.getMachineNum();
        //获取进程编码
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        this.processId=Long.valueOf(runtimeMXBean.getName().split("@")[0]).longValue();

        //避免编码超出最大值
        /**
         * 如果workerId=489181L,二进制是1110111011011011101, workerMask上面已经得知是31, 二进制: 11111, 机器码的位数是5位,workerIdBits = 5L;
         * workerId & workerMask = 29,二进制 11101,所以二进制都是控制在5位
         * processId同理可以控制在二进制5位
         */
        this.workerId=workerId & workerMask;
        this.processId=processId & processMask;
    }

    public synchronized long getNextId() {
        //获取时间戳
        long timestamp = timeGen();
        //如果时间戳小于上次时间戳则报错
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //如果时间戳与上次时间戳相同
        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1，与sequenceMask确保sequence不会超出上限
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        //将时间戳的二进制向左移22位,进程id的二进制向左移12位,机器id的二进制左移5位,序号的二进制左移5位
        //再用||连接起来,所以形成了一个64位的long类型:
        //[xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxxx xx] [xx xxxx xxxx xx] [xx xxx][x xxxx]  从左到右分别是: 时间戳, 进程id,机器id,序号
        long nextId = ((timestamp - twepoch) << timestampLeftShift) | (processId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
        return nextId;
    }

    /**
     * 再次获取时间戳直到获取的时间戳与现有的不同
     * @param lastTimestamp
     * @return 下一个时间戳
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return currentTimeMillis();
    }

    /**
     * 获取机器编码
     * @return
     */
    private long getMachineNum(){
        long machinePiece;
        StringBuilder sb = new StringBuilder();
        Enumeration<NetworkInterface> e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
        while (e.hasMoreElements()) {
            NetworkInterface ni = e.nextElement();
            sb.append(ni.toString());
        }
        machinePiece = sb.toString().hashCode();
        return machinePiece;
    }

    public static void main(String[] args) {

        System.out.println(nextId());


    }
}
