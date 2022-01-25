package array;

public class LeapArray {

//        每秒 100，分4块，每秒25
    private static final int sizeOfWindow=2000;
    private static final int amountOfWindow=5;
    private static final WindowWrap[] sourceArray= new WindowWrap[amountOfWindow];

    static class WindowWrap {
        private final long startTime;
        private int dataAmount;

        public WindowWrap(long startTime, int dataAmount) {
            this.startTime = startTime;
            this.dataAmount = dataAmount;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            int test=leapWindow();
            Thread.sleep(200);
//            if (test<2){
//                Thread.sleep(100);
//            }
        }
    }


    private static int leapWindow(){
        int test=0;
        long curTime=System.currentTimeMillis();
        long startTime=curTime-curTime%sizeOfWindow;
        int indexOfWindow=(int) ((curTime/sizeOfWindow)%amountOfWindow);

//        根据索引idx，在采样窗口数组中取得一个时间窗口old
        System.out.println(indexOfWindow+"this time "+curTime);
        WindowWrap oldWindowWrap=sourceArray[indexOfWindow];
        //如果old为空，说明该时间窗口还没有创建、则创建一个时间窗口，并将它插入到array的第idx个位置
        if (oldWindowWrap==null){
            /*
             *     B0       B1      B2    NULL      B4
             * ||_______|_______|_______|_______|_______||___
             * 200     400     600     800     1000    1200  timestamp
             *                             ^
             *                          time=888
             *   23432432
             *            bucket is empty, so create new and update
             *
             * If the old bucket is absent, then we create a new bucket at {@code windowStart},
             * then try to update circular array via a CAS operation. Only one thread can
             * succeed to update, while other threads yield its time slice.
             */
            sourceArray[indexOfWindow]=new WindowWrap(startTime,0);
            System.out.println("创建一个时间窗口，开始时间"+startTime+",数据为请求数量，此时请求量为 0");
        }else {
            oldWindowWrap.dataAmount=oldWindowWrap.dataAmount+1;
            test=oldWindowWrap.dataAmount;
            //如果当前窗口的开始时间time与old的开始时间相等，那么说明old就是当前时间窗口，直接返回old
            if (oldWindowWrap.startTime == startTime){
                /*
                 *     B0       B1      B2     B3      B4
                 * ||_______|_______|_______|_______|_______||___
                 * 200     400     600     800     1000    1200  timestamp
                 *                             ^
                 *                          time=888
                 *            startTime of Bucket 3: 800, so it's up-to-date
                 *
                 * If current {@code windowStart} is equal to the start timestamp of old bucket,
                 * that means the time is within the bucket, so directly return the bucket.
                 */
                System.out.println("处于当前时间段内，开始时间"+startTime+",数据为请求数量，此时请求量为 "+oldWindowWrap.dataAmount);
            }else if(oldWindowWrap.startTime < startTime) {
                /*
                 *   (old)
                 *             B0       B1      B2    NULL      B4
                 * |_______||_______|_______|_______|_______|_______||___
                 * ...    1200     1400    1600    1800    2000    2200  timestamp
                 *                              ^
                 *                           time=1676
                 *          startTime of Bucket 2: 400, deprecated, should be reset
                 *
                 * If the start timestamp of old bucket is behind provided time, that means
                 * the bucket is deprecated. We have to reset the bucket to current {@code windowStart}.
                 * Note that the reset and clean-up operations are hard to be atomic,
                 * so we need a update lock to guarantee the correctness of bucket update.
                 *
                 * The update lock is conditional (tiny scope) and will take effect only when
                 * bucket is deprecated, so in most cases it won't lead to performance loss.
                 */
                System.out.println("上一个时间段内的处理已完成，开始时间"+oldWindowWrap.startTime+",数据为请求数量，上一个区间请求量为 "+oldWindowWrap.dataAmount);
    // 如果当前窗口的开始时间time大于old的开始时间，则说明old窗口已经过时了，将old的开始时间更新为最新值:time，
                sourceArray[indexOfWindow]=new WindowWrap(startTime,0);
//                我的idea版本此处ui报错，提示 永远true，实际为 永远false
            }else if(oldWindowWrap.startTime > startTime) {
                throw new RuntimeException("如果不手动修改操作系统的时间，这个情况永远不会出现");
            }
        }
        return test;
    }
}
