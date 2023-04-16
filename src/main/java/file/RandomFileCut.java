package file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RandomFileCut {
    public static long size=524288L;
    public  int index;
    public  long begin;
    public  long end;

    @Override
    public String toString() {
        return "RandomFileCut{" +
                "index=" + index +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }

    public static void main(String[] args)  throws Exception{
        File sourceFile=new File("E:\\file\\PBC绩效赋能手册2023.pdf");
            cut(sourceFile);
    }

    private static void cut(File sourceFile) throws Exception{
        long length=sourceFile.length();
        System.out.println(length);
        int count=(int)Math.ceil((double) length / (double) size);
        System.out.println(count);
        long  offset=0L;
        List<RandomFileCut> randomFileCuts=new ArrayList<>();
        for (int i = 1; i < count; i++) {
            long end=offset+size;
            RandomFileCut fileCut=new RandomFileCut();
            fileCut.index=i;
            fileCut.begin=offset;
            fileCut.end=end;
            randomFileCuts.add(fileCut);
            offset=end;
        }
        long lastOffset=length-((count-1)*size);
        if (lastOffset>0){
            long end=lastOffset+offset;
            RandomFileCut fileCut=new RandomFileCut();
            fileCut.index=count;
            fileCut.begin=offset;
            fileCut.end=end;
            randomFileCuts.add(fileCut);
        }
        for (RandomFileCut randomFileCut : randomFileCuts) {
            System.out.println(randomFileCut);
            Random r=new Random();
            Long time=1000L*( r.nextInt(100)+1);
            System.out.println(time);
            Thread  thread=new Thread(){
                @Override
                public void run() {
                    try {
                        //Thread.sleep(time);
                        cutFile(sourceFile,randomFileCut.begin,randomFileCut.end,randomFileCut.index);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
    }

    private static void cutFile(File sourceFile,long begin,long end,int index) throws Exception{
        File outFile=new File("E:\\file\\"+index+".txt");
        FileOutputStream outputStream=new FileOutputStream(outFile);

        RandomAccessFile randomAccessFile=new RandomAccessFile(sourceFile,"r");
        randomAccessFile.seek(begin);

        int n=0;
        //byte[] buffer=new byte[1024];
        int a=0;
        while (randomAccessFile.getFilePointer()<=end&&(n=randomAccessFile.read())!=-1){
                outputStream.write(n);
                if (a<1000) {
                    Thread.sleep(10L);
                    a++;
                }
        }

        randomAccessFile.close();
        outputStream.close();
    }
}
