class Solution {
        public int maximumLength(int[] A, int k) {
        Map<Integer, Integer>[] dp = new HashMap[k + 1];
        for (int i = 0; i <= k; i++) {
            dp[i] = new HashMap<>();
        }
        int[] res = new int[k + 1];
        for (int a : A) {
            for (int i = k; i >= 0; --i) {
                int v = dp[i].getOrDefault(a, 0);
                v = Math.max(v + 1, (i > 0 ? res[i - 1] + 1 : 0));
                dp[i].put(a, v);
                res[i] = Math.max(res[i], v);
            }
        }
        return res[k];
    }
}