package DynamicProgram;


import java.util.ArrayList;
import java.util.regex.Pattern;

public class CoinAmount {
    public static void main(String[] args) {
//        int[] coins={1,2,5};
//        int amount=11;
//        coinChange(coins,amount);
        String content = "Ss33333";

        String pattern =
//                "^((?=^.*[A-Z].*[a-z].*$)|(?=^.*[a-z].*[A-Z].*$)).{5,17}$";
//                "^(?![0-9]+$)(?![a-z0-9]+$)(?![A-Z0-9]+$)[0-9A-Za-z~!@#$%^&*(){}?+,\\[\\]\\\\]{5,17}$";
                "^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*)[0-9A-Za-z~!@#$%^&*(){}?+,\\[\\]\\\\]{5,17}$";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println( isMatch);
    }


    // coins 中是可选硬币面值，amount 是目标金额
    static void  coinChange(int[] coins, int amount) {

        ArrayList<Integer> cost = new ArrayList<>();
//每次循环保存的差值  备忘录
        int needMoney = amount;
        do {
//            标记最小的coin
            int coinCur = 0;
            //        用来循环 判断哪个coin的差值 是最小的
            int curMoney = amount;
            for (int coin : coins) {
                int difference = needMoney  - coin;
                if (difference >= 0 && difference < curMoney) {
                    curMoney = difference;
                    coinCur = coin;
                }
            }
            needMoney=curMoney;
            cost.add(coinCur);
        } while (needMoney != 0);
        System.out.println(cost);
    }
}
