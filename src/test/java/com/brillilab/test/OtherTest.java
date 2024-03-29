package com.brillilab.test;
import java.util.Date;

import com.brillilab.alioss.AliSts;
import com.brillilab.blockingQueue.Consumer;
import com.brillilab.blockingQueue.Producer;
import com.brillilab.utils.QRCodeEncoderUtils;
import com.brillilab.utils.QuartzUtils;
import com.brillilab.utils.SecureUtils;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OtherTest {

    private int num=0;

    //并不会报异常
    @Test
    public void test01(){
        while (true){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        increace();
                        System.out.println("Thread:"+Thread.currentThread().getName()+"  start!   num="+num);
                    }
                }
            }).start();
        }
    }

    public synchronized void increace(){
        num++;
    }

    @Test
    public void test02(){
        List<String> list=new ArrayList<>();
        int i=0;
        while (true){
            list.add(String.valueOf(i++).intern());
        }
    }

    @Test
    public void test03(){
        System.out.println(Integer.toBinaryString(10));
        System.out.println(Integer.toBinaryString(8));
        System.out.println(10&8);
    }

    @Test
    public void test04() throws Exception {
        String url="https://www.baidu.com/";
        QRCodeEncoderUtils.encoder(url);
    }

    @Test
    public void test05(){
        double a=0.1*0.1;

        BigDecimal b = new BigDecimal(a);
        b.setScale(2,RoundingMode.HALF_UP);

        DoubleAccumulator da=new DoubleAccumulator(((l,r)->{
            return l*r;
        }),0.1);
        da.accumulate(0.1);
        double v = da.doubleValue();


        System.out.println(a);
        System.out.println(b.floatValue());
        System.out.println(v);
    }

    @Test
    public void test06(){
        System.out.println(1 & 2);
    }


    @Test
    public void test07(){
        Assert.assertEquals(Md5Crypt.md5Crypt("123456".getBytes()),Md5Crypt.md5Crypt("123456".getBytes()));
    }

    @Test
    public void test08() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Assert.assertEquals(SecureUtils.MD5Encrypt("123456"),SecureUtils.MD5Encrypt("123456"));
    }

    @Test
    public void test09(){
        System.out.println(1000 << 2);
    }

    @Test
    public void test10(){
        Integer iv = 100;
        long lv = iv;
        System.out.println(lv);
    }
    
    @Test
    public void test11(){
        BigDecimal v1=new BigDecimal(1);
        BigDecimal v2=new BigDecimal(-5);
        BigDecimal add=v1.add(v2);
        System.out.println(add);
        System.out.println(v2.compareTo(new BigDecimal(0)));
    }

    @Test
    public void test12(){
        HashSet<String> strings=new HashSet<>();
        strings.add("1");
        strings.add("1");
        strings.add("55555");
        strings.add("55555");
        System.out.println(strings);
        System.out.println(strings.toString().replace("[","").replace("]",""));
    }

    @Test
    public void test13(){
        String regEx="[`~!@#$%^&*()\\-+={}':;,\\[\\].<>/?￥%…（）_+|【】‘；：”“’。，、？\\s]";
        String value = "ssde-s12121";
        System.out.println(value.replaceAll(regEx,"?"));
    }

    @Test
    public void test14(){
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal tree = BigDecimal.valueOf(3);
        BigDecimal oneByTree = BigDecimal.valueOf(1.0/3.0).setScale(2,RoundingMode.HALF_UP);
        BigDecimal divide=one.divide(tree,2,RoundingMode.HALF_UP);
        System.out.println(divide.scale());
        System.out.println(divide);
        System.out.println(divide.doubleValue());
        System.out.println(one);
        System.out.println(tree);
        System.out.println(oneByTree.doubleValue());
    }

    @Test
    public void test15() throws InterruptedException {
        AtomicInteger zero=new AtomicInteger(0);

        for (int i=0; i < 10; i++) {
            new Thread(() ->{
                int value;
                synchronized (this){
                    value = zero.incrementAndGet();
                    System.out.printf("%s %s \n",value,Thread.currentThread().getName());
                }
            },"thread-"+i).start();
        }

        while (zero.intValue()<10);

    }

    @Test
    public void test16(){
        Double d = 1.0;
        Integer i = d.intValue();

        double dd = 1.0;
        int ii = (int)dd;

        System.out.println(i);
        System.out.println(ii);
    }

    /**
     * 测试synchronized
     */
    private AtomicInteger times;

    @Test
    public void synchronizedUse() throws InterruptedException {
        new Thread(this::printHello).start();
        new Thread(this::printHello).start();
        Thread.sleep(5000L);
    }

    private synchronized void printHello(){

        Thread thread=Thread.currentThread();

        if(times==null){
            times=new AtomicInteger(0);
        }else {
            if(times.getAndIncrement()>=4){
                times=null;
                return;
            }
        }

        System.out.println("hello"+times.get()+"  Thread:"+thread.getName());

        printHello();
    }

    @Test
    public void testGetCronExpr() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=simpleDateFormat.parse("2019-03-31 23:10:00");
        Date alert=new Date(date.getTime()-10*60*1000);
        String cronExpr=QuartzUtils.getCronExpr(alert);
        System.out.println(cronExpr);
    }

    @Test
    public void hash() throws UnsupportedEncodingException {
        //HashMap技术Hash值
        String str = "无人生还";
        char[] chars=str.toCharArray();
        int h = 0;
        int hash = 0;
        for (int i=0 ; i<chars.length ;i++) {
            h=chars[i];
            h ^= (h >>> 20) ^ (h >>> 12);
            hash = hash ^ (h >>> 7) ^ (h >>> 4);
        }
        System.out.println(h);
        System.out.println(Integer.toBinaryString(h));

        int[] arr = new int[9];
        //HashMap计算索引
        int index = h & (arr.length-1);
        System.out.println(index);
    }

    @Test
    public void charValue(){
        System.out.println((int)'A');
        System.out.println('A'+1);
    }

    @Test
    public void emptyMapGet(){
        HashMap<Object, Object> objectObjectHashMap=new HashMap<>();
        System.out.println(objectObjectHashMap.get(0));
    }

    @Test
    public void useIndexWithStream(){
        List<Integer> integers=Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        IntStream.range(0,integers.size()).forEach(i -> System.out.printf("index : %d ,value : %d",i ,integers.get(i)));
    }

    @Test
    public void getContent(){
        String str = "2.从培养物中吸出培养基#${<2a08e811375c4944ad5efe60d767d333>}$# #${<26b53604cf61465c91e3746c43f8b1c3>}$#；用 1 * PBS 洗涤细胞#${<f60b2cb3804a4edea95fabd3721209e6>}$#；吸出；";
        String s=str.replaceAll("[#][$][{][<]([0-9a-zA-Z]{32})[>][}][$][#]","").trim();
//        String s=str.replaceAll("[0-9a-zA-Z]+","");
        System.out.println(s);
    }

    @Test
    public void getSome(){
        String str = "2.从培养物中吸出培养基#${[reagent]<2a08e811375c4944ad5efe60d767d333>}$# #${[reagent]<26b53604cf61465c91e3746c43f8b1c3>}$#；用 1 * PBS 洗涤细胞#${[reagent]<f60b2cb3804a4edea95fabd3721209e6>}$#；吸出；";
        System.out.println(getReagentUuids(str));
    }

    public List<String> getReagentUuids(String content){
        Pattern pattern = Pattern.compile("([0-9a-zA-Z]{32})");
        Matcher matcher=pattern.matcher(content);
        List<String> uuids = new ArrayList<>();
        while (matcher.find()){
            String group=matcher.group();
            uuids.add(group);
        }
        return uuids;
    }

    @Test
    public void sort(){
        Integer[] all = {20238,20302,20318,20414,20430,20398,20382,20334,20286,20254,20270,20350,20366,20446,20462,20478,20494,20510,20526,20542,20558,20574,20590,20606,20558,20574,20590,20606,20238,20302,20318,20414,20430,20398,20382,20334,20286,20254,20270,20350,20366,20446,20462,20478,20494,20510,20526,20542,20239,20255,20271,20287,20303,20319,20335,20351,20367,20383,20399,20415,20431,20447,20463,20479,20495,20511,20527,20543,20559,20575,20591,20607};
        Arrays.sort(all);
        for (int one:all) {
            System.out.println(one);
        }
        System.out.println(all.length);

        List<Integer> integers=Arrays.asList(all).stream().distinct().collect(Collectors.toList());
        System.out.println(integers.size());
    }

    @Test
    public void  blockingQueue() throws InterruptedException {

        LinkedBlockingQueue<Integer> queue=new LinkedBlockingQueue<>(10);
        ArrayList<Integer> list = new ArrayList<>(1000);
        for (int i = 0; i < 500000; i++){
            list.add(i);
        }
        Producer producer = new Producer(list,queue);
        Consumer consumer=new Consumer(queue);

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(producer);
        service.execute(consumer);

        service.awaitTermination(10, TimeUnit.SECONDS);

    }

    @Test
    public void encoding() throws UnsupportedEncodingException {
        String s=new String("æ¨çæéå·²è¢«ä¿®æ¹ï¼è¯·å·æ°ç¨æ·ä¿¡æ¯ï¼".getBytes("ISO8859-1"),"UTF-8");
        System.out.println(s);
    }

    @Test
    public void test20(){
        System.out.println(System.currentTimeMillis());
        System.out.println((6307200000000L-1570502090449L)/(1000*3600*24));
    }

    //base64
    @Test
    public void test21() throws UnsupportedEncodingException {
        byte[] decode = Base64.getDecoder().decode("eyJjb2RlIjoxMDAwLCJtZXNzYWdlIjoi5pyq55+l6ZSZ6K+vISJ9");
        System.out.println(new String(decode,"utf-8"));
    }

    //查询mac地址
    @Test
    public void getRemoteMac() throws UnknownHostException, SocketException {
        InetAddress inetAddress = InetAddress.getByName("39.156.69.79");
        byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        String macAddress = sb.toString().trim().toUpperCase();
        System.out.println(macAddress);
    }

    //reentrantLock测试
    @Test
    public void reentrantLock() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        new Thread(() ->{
            try {
                //获取锁
                reentrantLock.lock();
                //线程等待 当前线程只有获取了锁才能调用该方法，调用都当前线程将释放锁
//            condition.await();
                condition.awaitUninterruptibly();

                System.out.printf("锁队列剩余: %s, 该线程被唤醒，继续执行! \t\n",reentrantLock.getQueueLength());

            }finally {
                reentrantLock.unlock();
            }

        }).start();

        Thread.sleep(100);

        new Thread(() ->{
            try {
                reentrantLock.lock();
                //随机唤醒一个该condition下的线程获得锁继续执行
                condition.signal();
                //唤醒该condition下面的所有线程进行锁竞争，获取锁的线程继续操作（公平）
                condition.signalAll();

                System.out.printf("锁队列剩余: %s, 开始唤醒! \t\n",reentrantLock.getQueueLength());

            } finally {
                reentrantLock.unlock();
            }

        }).start();

        System.out.printf("锁队列剩余: %s! \t\n",reentrantLock.getQueueLength());
    }

    @Test
    public void localDateTimeAdd(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveHourBefore = now.plusHours(-5L);
        System.out.println(fiveHourBefore);
    }

    @Test
    public void substring(){
        String str =  "123456";
        System.out.println(str.substring(4,5));
    }

    @Test
    public void endOfDayTest(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.withHour(23).withMinute(59).withSecond(59).withNano(0);
        System.out.println(end);
    }

    @Test
    public void calender(){
        LocalDateTime now = LocalDateTime.now();
        int monthValue = now.getMonthValue();
        int dayOfMonth = now.getDayOfMonth();
        int year = now.getYear();
        System.out.printf("year:%d,month:%d,day:%d \t\n",year,monthValue,dayOfMonth);
    }

    @Test
    public void suffix(){
        String name = "jjj.pdf";
        System.out.println(name.substring(name.lastIndexOf(".")));
    }

    // 亦或操作 ^
    @Test
    public void test22(){

        int a = Integer.valueOf("100101100111101111111100001",2);
        int b = Integer.valueOf("000000000000000010010110011",2);
        int c = a ^ b;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println("100101100111101111111100001");
        System.out.println("000000000000000010010110011");
        System.out.println(Integer.toBinaryString(c));
    }

    /**
     * ArrayList序列化
     *
     */
    @Test
    public void test23(){
        ArrayList<Integer> integers = new ArrayList<>();
        for(int i=0;i<10;i++){
            integers.add(i);
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("/Users/menghaowu/Documents/temp/Object/test.temp"))){
            outputStream.writeObject(integers);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("/Users/menghaowu/Documents/temp/Object/test.temp"))){
            Object o = inputStream.readObject();
            System.out.println(o);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 引用传递与值传递
    @Test
    public void test24(){
        int a = 1;
        int b = 2;
        change(a,b);
        System.out.printf("a:%d,b:%d \n",a,b);
    }

    private void change(int a,int b){
        int i=a;
        a = b;
        b = i;
        System.out.printf("a:%d,b:%d \n",a,b);
    }

    // 引用传递与值传递
    @Test
    public void test25(){
        AliSts aliSts = new AliSts();
        aliSts.setAccessKeyId("aaaaaa");
        aliSts.setAccessKeySecret("aaaaa");
        aliSts.setSecurityToken("aaaaa");
        aliSts.setExpireTime(new Date());
        modify(aliSts);
        System.out.println("aliSts:"+aliSts);
    }

    private void modify(AliSts aliSts){
        aliSts = null;
        System.out.println("aliSts:"+aliSts);
    }

    // VirtualMachine Attach API
    @Test
    public void test26() throws IOException, AttachNotSupportedException {
        for (VirtualMachineDescriptor descriptor : VirtualMachine.list()){

            System.out.println(descriptor.id()+" "+descriptor.displayName());
        }

        //property
        VirtualMachine attach = VirtualMachine.attach("69205");
        Properties systemProperties = attach.getSystemProperties();
        systemProperties.entrySet().forEach(e->{
            System.out.println(e.getKey()+ " : "+ e.getValue());
        });

    }
}
