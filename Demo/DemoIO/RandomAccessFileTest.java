package DemoIO;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;


/*
    RandomAccessFile 随机存储文件流
    1.RandomAccessFile直接继承于java.lang.Object类，实现了DataInput与DataOutput接口
    2.RandomAccessFile既可以作为一个输入流，又可以作为一个输出流
    3.RandomAccessFile作为输出流时，写出到的文件如果不存在，则在执行过程中自动创建
                    如果写出到的文件存在，则会对原有文件内容进行覆盖（默认从头覆盖）
    4.可以通过相关的操作，实现RandomAccessFile ”插入“数据的效果
* */
public class RandomAccessFileTest {

    @Test
    public void test(){

        RandomAccessFile raf1 = null;
        RandomAccessFile raf2 = null;
        try {
            raf1 = new RandomAccessFile("hello.txt","r");
            raf2 = new RandomAccessFile("hello.txt","rw");

            byte[] buffer = new byte[1021];
            int len;
            while ((len = raf1.read(buffer)) != -1){
                raf2.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(raf1 != null){
                try {
                    raf1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(raf2 != null){
                try {
                    raf2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test2() throws IOException {

        RandomAccessFile raf1 = new RandomAccessFile("hello.txt","rw");

        raf1.write(3);//将指针调到角标为3的位置
        raf1.write("xyz".getBytes());

        raf1.close();
    }

    /*
    * 使用RandomAccessFile实现数据的插入效果
    * */
    @Test
    public void test3() throws IOException {

        RandomAccessFile raf = new RandomAccessFile("hello.txt","rw");

        raf.seek(3);//将指针调到角标为3的位置
        //保存指针3后面的所有数据到StringBuilder中
        StringBuilder builder = new StringBuilder((int) new File("hello.txt").length());
        byte[] buffer = new byte[20];
        int len;
        while ((len = raf.read(buffer)) !=  -1){
            builder.append(new String(buffer,0,len));
        }

        //调回指针，写入"xyz"
        raf.seek(3);
        raf.write("xyz".getBytes());//将一个字符串转化为一个字节数组byte[]

        //将StringBuilder中的数据写入到文件中
        raf.write(builder.toString().getBytes());

        raf.close();

    }
}