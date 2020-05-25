package com.brillilab.test;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * NIO的测试
 *
 * @author wmh
 */
public class NIOTest {

    /**
     * 测试NIO文件读取
     *
     * @throws IOException
     */
    @Test
    public void readFileByNIO() throws IOException {
        //获取输入流
        FileInputStream inputStream = new FileInputStream("D:\\data\\files\\nio.txt");
        //获取Channel
        FileChannel channel = inputStream.getChannel();
        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取数据到缓冲区
        int read = channel.read(buffer);
        //重设buffer
        buffer.flip();

        //从buffer中取出数据
        byte[] bytes = new byte[1024];
        int i = 0;
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            bytes[i] = b;
            i++;
        }
        String s = new String(bytes, "utf-8");
        System.out.print(s);

        //关流释放资源
        inputStream.close();
    }

    /**
     * 测试NIO文件传输
     *
     * @throws IOException
     */
    @Test
    public void transferFileByNIO() throws IOException {

        long begian = System.currentTimeMillis();

        //创建流
        FileInputStream inputStream = new FileInputStream("D:\\data\\files\\windows_7_ultimate_x64_2018.iso");
        FileOutputStream outputStream = new FileOutputStream("D:\\data\\files\\windows_7_ultimate_x64_2018-copy.iso");

        //获取Channel
        FileChannel channelIn = inputStream.getChannel();
        FileChannel channelOut = outputStream.getChannel();

        //创建Buffer 1M
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

        //读入
        long i = 0;
        while (channelIn.read(buffer) != -1) {
            //重设Buffer
            buffer.flip();
            //写出
            i += channelOut.write(buffer);
            //清空Buffer
            buffer.clear();
        }


        inputStream.close();
        outputStream.close();

        long end = System.currentTimeMillis();

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.CHINA);
        System.out.println("一共传送数据：" + nf.format(i / Math.pow(1024, 2)) + "MB");
        System.out.println("传输文件所用时间：" + (end - begian) / 1000 + "s");
    }

    /**
     * 测试普通文件传输
     *
     * @throws IOException
     */
    @Test
    public void transferFileNormal() throws IOException {

        long begian = System.currentTimeMillis();

        FileInputStream inputStream = new FileInputStream("D:\\data\\files\\ideaIU-2018.1.2.exe");
        FileOutputStream outputStream = new FileOutputStream("D:\\data\\files\\ideaIU-2018.1.2-copy.exe");

        byte[] baffer = new byte[1024];
        int i = 0;
        while (inputStream.read(baffer) != -1) {
            outputStream.write(baffer);
        }

        inputStream.close();
        outputStream.close();

        long end = System.currentTimeMillis();

        System.out.println("传输文件所用时间：" + (end - begian) + "sm");

    }

    /**
     * nio + pipe zip
     */
    @Test
    public void zipFileChannel() throws IOException {
        //prepare file
        String sourceFile = "D:/data/files/ideaIU-2018.1.2-copy.exe";
        String zipFile = "D:/data/files/zip/ideaIU-2018.1.2-copy.zip";
        String fileName = "ideaIU-2018.1.2-copy.exe";
        //nioFile
        long begin = System.currentTimeMillis();
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            try (FileChannel fileChannel = new FileInputStream(sourceFile).getChannel()) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                fileChannel.transferTo(0, fileChannel.size(), writableByteChannel);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.printf("Use Time : %s s", (end - begin) / 1000);
    }

    @Test
    public void zipFileChannelPipe() {
        //prepare file
        String sourceFile = "D:/data/files/ideaIU-2018.1.2-copy.exe";
        String zipFile = "D:/data/files/zip/ideaIU-2018.1.2-copy.zip";
        String fileName = "ideaIU-2018.1.2-copy.exe";
        //nioFile
        long begin = System.currentTimeMillis();
        try (WritableByteChannel out = Channels.newChannel(new FileOutputStream(zipFile))) {
            Pipe pipe = Pipe.open();
            //异步任务

            CompletableFuture.runAsync(() -> renderFileZip(pipe, sourceFile, fileName));
            //获取读通道
            ReadableByteChannel readChannel = pipe.source();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (readChannel.read(buffer) >= 0) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.printf("Use Time : %s s", (end - begin) / 1000);
    }

    private void renderFileZip(Pipe pipe, String sourceFile, String entityName) {
        try (ZipOutputStream zos = new ZipOutputStream(Channels.newOutputStream(pipe.sink()));
             WritableByteChannel out = Channels.newChannel(zos)) {
            System.out.println("Task Begin");
            System.out.printf("Thread name : %s ,Thread ID: %d \n",
                    Thread.currentThread().getName(), Thread.currentThread().getId());
            zos.putNextEntry(new ZipEntry(entityName));
            FileChannel channel = new FileInputStream(new File(sourceFile)).getChannel();
            channel.transferTo(0, channel.size(), out);
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * RandomAccessFile 使用多线程分配复制并合并
     */
    @Test
    public void randomAccessFileUse() throws ExecutionException, InterruptedException {
        String sourceFile = "D:/data/files/ideaIU-2018.1.2-copy.exe";
        String distFileDir = "D:/data/files/copy/";

        int sheets;
        long sheetSize = 1, fileSize;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        File inFile = new File(sourceFile);
        File outDir = new File(distFileDir);
        if (!inFile.exists()) {
            System.out.println("source file do not exist !");
        }

        if (!outDir.exists()) {
            boolean rs = outDir.mkdir();
            if (!rs) {
                System.out.println("make dir failed !");
            }
        }

        fileSize = inFile.length();
        if (fileSize > 1024 * 1024 * 1024 * 5L) {
            System.out.println("file is bigger than 5Gb !");
        }

        if (fileSize > 1024 * 1024 * 100L) {
            sheetSize = 1024 * 1024 * 50L;
        }

        if (fileSize > 1024 * 1024 * 1024L) {
            sheetSize = 1024 * 1024 * 200L;
        }

        sheets = (fileSize % sheetSize) > 0 ? (int) (fileSize / sheetSize) : (int) (fileSize / sheetSize - 1);
        long loopTime = sheetSize / 1024;
        //监听
        ObservableFileList tempFiles= new ObservableFileList(sourceFile.substring(sourceFile.lastIndexOf('/')));
        FileListMergeObserver merger = new FileListMergeObserver(tempFiles, sheets + 1);
        //拷贝
        for (int i = 0; i <= sheets; i++) {
            int num = i;
            long thisSheetSize = sheetSize;

            Future<File> result = executor.submit(new Callable<File>() {
                @Override
                public File call() throws Exception {
                    File tempFile = new File(distFileDir + inFile.getName() + '.' + num + ".temp");
                    RandomAccessFile outRaf = new RandomAccessFile(tempFile, "rw");
                    RandomAccessFile inRaf = new RandomAccessFile(inFile, "r");

                    long start = num * thisSheetSize;
                    inRaf.seek(start);
                    byte[] buff = new byte[1024];

                    for (long pose = start, times = 0; times<loopTime; pose += 1024, times++) {
                        long pointer = inRaf.getFilePointer();
//                        System.out.printf("ThreadName:%s , ThreadId:%d , times:%d , loopTime:%d , filePointer:%d \n",
//                                Thread.currentThread().getName(), Thread.currentThread().getId(), times, loopTime, pointer);
                        // 文件最后读取完并退出循环
                        if (fileSize - pointer <= 1024){
                            buff = new byte[1024];
                            inRaf.read(buff);
                            outRaf.write(buff,0 ,(int)(fileSize - pointer) );
                            break;
                        }else {
                            inRaf.read(buff);
                            outRaf.write(buff);
                        }
                    }
                    inRaf.close();
                    outRaf.close();
                    return tempFile;
                }
            });
            tempFiles.add(result.get());
        }
        while (merger.flag){
            executor.shutdown();
            if (executor.isTerminated()){
                System.out.println("end!");
                break;
            }
        }
//        executor.awaitTermination(100, TimeUnit.SECONDS);
//        System.out.println("end!");
    }

    /**
     * FileList观察者
     */
    class FileListMergeObserver implements Observer{

        int total;
        public volatile boolean flag;

        public FileListMergeObserver(ObservableFileList observable, int total) {
            observable.addObserver(this);
            this.total = total;
        }

        @Override
        public void update(Observable o, Object arg) {
            if (o instanceof ObservableFileList){
                ObservableFileList fileList = (ObservableFileList) o;
                if (fileList.size() == total){
                    List<File> files = fileList.getData();
                    String dir = files.get(0).getParent();
                    String mergeName = fileList.getOriginName();
                    try {
                        RandomAccessFile out = new RandomAccessFile(dir + mergeName, "rw");
                        for(int index=0; index<total; index++){
                            File file = files.get(index);
                            try (FileInputStream in = new FileInputStream(file)){
                                byte[] buff = new byte[1024];
                                for ( int bytes = in.read(buff);bytes >0;bytes = in.read(buff)){
                                    out.write(buff,0,bytes);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        flag=true;
                    }
                }
            }
        }
    }

    /**
     * 可监听FileList
     */
    class ObservableFileList extends Observable{
        private String originName;
        private List<File> data;

        public ObservableFileList(String originName) {
            this.originName = originName;
            this.data = new ArrayList<File>(15);
        }
        
        public boolean add(File file){
            if (data == null){
                System.err.println("file list has not initialed!");
            }
            boolean rs = data.add(file);
            if (rs){
                setChanged();
                notifyObservers();
            }
            return rs;
        }
        
        public long size(){
            if (data == null){
                System.err.println("file list has not initialed!");
            }
            return data.size();
        }

        public List<File> getData() {
            return data;
        }

        public String getOriginName() {
            return originName;
        }
    }
}
