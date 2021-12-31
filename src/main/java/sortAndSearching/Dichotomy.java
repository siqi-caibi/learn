package sortAndSearching;

public class Dichotomy {
    public static void main(String[] args) {

        System.out.println(firstBadVersion(8));
    }

//    二分法找 最后一个错误版本
//            【 1，1，1，0，0，0，0，0】 第3个变成false
    private static  int firstBadVersion(int n){
        int left=1;
        int right=n;
        while (right>left){
            int mid=left+(right-left)/2;
//            如果  中间值 满足条件 那么就缩小右边的范围
//            不满独条件就  扩大左边的值
            if (isTrue(mid)){
                right=mid;
            }else {
                left=mid+1;
            }
        }
        return left;
    }
    private static boolean isTrue(int n){
//        1,2,3都是false
        return n > 3;
    }
}
