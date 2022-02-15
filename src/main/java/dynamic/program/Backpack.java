package dynamic.program;

public class Backpack {

    public static void main(String[] args) {
//        back();
        coinAmount();
    }

    /**
     * 给你⼀个可装载重量为 W 的背包和 N 个物品，每个物品有重量和价值两个属性。其中第 i 个物品的重量为
     * wt[i]，价值为 val[i]，现在让你⽤这个背包装物品，最多能装的价值是多少？
     * 返回 6
     */
    private static void back(){
        int N=3;
        int W=4;
        int[] wt={2,1,3};
        int[] val={4,2,3};
        int[][] dp=new int[N+1][W+1];
        for (int i = 1; i <= N; i++) {
            for (int w = 1; w <= W; w++) {
                if (w - wt[i-1] < 0) {
                    // 这种情况下只能选择不装⼊背包
                    dp[i][w] = dp[i - 1][w];
                } else {
                    // 装⼊或者不装⼊背包，择优
//                   例如 当前装3kg后，剩余2kg，2kg价值加上当前3kg价值，小于，4kg总价值，那么取最大的 （4kg）
                    dp[i][w] = Math.max(dp[i - 1][w - wt[i-1]] + val[i-1],
                            dp[i - 1][w]);
                }
            }
        }

        System.out.println(dp[N][W]);
    }

    private static void coinAmount(){
        int amount=5;
        int[] coins={1,2,3};
            int n = coins.length;
            int[][] dp = new int[n + 1][amount + 1];
            // base case
            for (int i = 0; i <= n; i++) {
                dp[i][0] = 1;
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= amount; j++)
                    if (j - coins[i-1] >= 0) {
                        dp[i][j] = dp[i - 1][j]
                                + dp[i][j - coins[i - 1]];
                    }
                    else {
                        dp[i][j] = dp[i - 1][j];
                    }
            }
            System.out.println( dp[n][amount]);
    }
}
