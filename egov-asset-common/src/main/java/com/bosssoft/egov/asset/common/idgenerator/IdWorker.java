package com.bosssoft.egov.asset.common.idgenerator;

import java.security.SecureRandom;

/** 
*
* @ClassName   类名：IdWorkerBySnowflake 
* @Description 功能说明：
* /** 
 * tweeter的snowflake 移植到Java: 
 *
 * 链接：http://mp.weixin.qq.com/s?__biz=MjM5ODYxMDA5OQ==&mid=403814007&idx=1&sn=c7d1b64fc013b8e07bbd1088d00ab692&mpshare=1&scene=23&srcid=1106PINc30l6r5H1eXU7Mj5t#rd
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月6日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月6日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class IdWorker {
	// 基准时间
//	private long twepoch = 1478653524904L; //Wed Nov 09 09:05:24 CST 2016
    private long twepoch = 1487491317310L; //Sun Feb 19 16:01:57 CST 2017
    // 区域标志位数
    private final static long regionIdBits = 5L;
    // 机器标识位数
    private final static long workerIdBits = 5L;
    // 序列号识位数
    private final static long sequenceBits = 10L;

    // 区域标志ID最大值
    private final static long maxRegionId = -1L ^ (-1L << regionIdBits);
    // 机器ID最大值
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 序列号ID最大值
    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

    // 机器ID偏左移位
    private final static long workerIdShift = sequenceBits;
    // 业务ID偏左移位
    private final static long regionIdShift = sequenceBits + workerIdBits;
    // 时间毫秒左移位
    private final static long timestampLeftShift = sequenceBits + workerIdBits + regionIdBits;

    private static long lastTimestamp = -1L;

    private long sequence = 0L;
    private final long workerId;
    private final long regionId;

    public IdWorker(long workerId, long regionId) {

        // 如果超出范围就抛出异常
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0");
        }
        if (regionId > maxRegionId || regionId < 0) {
            throw new IllegalArgumentException("datacenter Id can't be greater than %d or less than 0");
        }

        this.workerId = workerId;
        this.regionId = regionId;
    }

    public IdWorker(long workerId) {
        // 如果超出范围就抛出异常
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0");
        }
        this.workerId = workerId;
        this.regionId = 0;
    }

    public long generate() {
        return this.nextId(false, 0);
    }

    
    /**
     * 实际产生代码的
     *
     * @param isPadding
     * @param busId
     * @return
     */
    private synchronized long nextId(boolean isPadding, long busId) {

        long timestamp = timeGen();
        long paddingnum = regionId;

        if (isPadding) {
            paddingnum = busId;
        }

        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp) {
            //sequence自增，因为sequence只有10bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & sequenceMask;
            //判断是否溢出,也就是每毫秒内超过1024，当为1024时，与sequenceMask相与，sequence就等于0
            if (sequence == 0) {
                //自旋等待到下一毫秒
                timestamp = tailNextMillis(lastTimestamp);
            }
        } else {
            // 如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加,
            // 为了保证尾数随机性更大一些,最后一位设置一个随机数
            sequence = new SecureRandom().nextInt(10);
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) | (paddingnum << regionIdShift) | (workerId << workerIdShift) | sequence;
    }

    // 防止产生的时间比之前的时间还要小（由于NTP回拨等问题）,保持增量的趋势.
    private long tailNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    // 获取当前的时间戳
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
		IdWorker a = new IdWorker(23L,23L);
        int i=0;
		long start = System.currentTimeMillis();
        while(i < 10){
			System.out.println(a.generate());
			i++;
        }
		//long test = a.generate();
		start = System.currentTimeMillis();
        long[] b = ComponetIdGen.newWKID(100000);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(b[9999]);
        System.out.println(b[5678]);
        System.out.println(b[99999]);
        System.out.println(b[99998]);
	}  
 }
