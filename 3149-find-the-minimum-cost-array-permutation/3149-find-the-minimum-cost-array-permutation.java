class Solution {
    int n;
    int minsum = Integer.MAX_VALUE;
    int[] result;

    public int[] findPermutation(int[] nums) {
        n = nums.length;
        boolean[] visited = new boolean[n];
        int[] temp = new int[n];

        temp[0] = 0;     
        visited[0] = true;

        backtrack(nums, visited, temp, 1, 0);
        return result;       
    }

    private void backtrack(int[] nums, boolean[] visited, int[] temp, int idx, int sum) {

        if (sum >= minsum) return;

        if (idx == n) {
            sum += Math.abs(temp[n - 1] - nums[temp[0]]); 
            if (sum < minsum) {
                minsum = sum;
                result = temp.clone();
            }
            return;
        }

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                temp[idx] = i;

                int cost = Math.abs(temp[idx - 1] - nums[temp[idx]]);
                backtrack(nums, visited, temp, idx + 1, sum + cost);

                visited[i] = false;
            }
        }
    }
}