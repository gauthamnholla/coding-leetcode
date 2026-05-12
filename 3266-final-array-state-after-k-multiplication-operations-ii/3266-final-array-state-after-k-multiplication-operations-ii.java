class Solution {
    int MOD = 1_000_000_007;

    // this calculates x to the power y in log y time
    long fastPower(long x, long y) {
        long res = 1;
        while (y > 0) {
            if ((y & 1) == 1)
                res = (res * x) % MOD;
            x = (x * x) % MOD;
            y /= 2;
        }
        return res;
    }

    public int[] getFinalState(int[] nums, int k, int m) {
        // if multiplier is 1, nums always stays same so return it
        if (m == 1)
            return nums;
        int n = nums.length;
        
        // minHeap to get min element in O(1) time
        PriorityQueue<int[]> minHeap = new PriorityQueue<int[]>(
                (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        
        // find the max value of the array and fill the minHeap at the same time
        long max = 0;
        for (int i = 0; i < nums.length; i++) {
            minHeap.add(new int[] { nums[i], i });
            max = Math.max(max, nums[i]);
        }

        // find the iterating point
        while (k > 0 && max / minHeap.peek()[0] >= m) {
            int[] min = minHeap.poll();
            min[0] *= m;
            minHeap.add(min);
            --k;
        }

        // find how many times we need to multiply each num in nums
        int times = k / n;
        // find remaining k
        int rem = k % n;

        // find what would be the multiplier value 
        long first = fastPower(m, times);
        // this second one is for those numbers in num where k remains and it needs one additional multiplication
        long second = fastPower(m, times + 1);

        // calculate the result and store it in nums itself
        while (!minHeap.isEmpty()) {
            int[] min = minHeap.poll();
            long mul = rem-- > 0 ? second : first;
            long val = (mul * min[0]) % MOD;
            nums[min[1]] = (int)val;
        }
        return nums;
    }
}