import java.util.*;

class Solution {

    int n, m;
    int[] nums;
    int[] change;
public boolean check(int mid){
//Works as min-Heap(Ordered Set)
    TreeSet<int[]> pq = new TreeSet<>(
        (a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        }
    );
  //marking first occureneces in changeIndices array for every ele
    HashMap<Integer, Integer> first = new HashMap<>();

    for (int i = 0; i <= mid; i++) {
        if (!first.containsKey(change[i] - 1)) {
            first.put(change[i] - 1, i);
        }
    }
    //it stores the indices of nums array at index = it s first occ in changeindices array.
    int[] arr = new int[mid + 1];
    Arrays.fill(arr, -1); // -1 denotes spaces which will be used to mark the indices which has been already made 0 after 2nd operation.

    for (Map.Entry<Integer, Integer> e : first.entrySet()) {
        arr[e.getValue()] = e.getKey();
    }

    int c = 0;
    for (int i = mid; i >= 0; i--) {
        //Spaces and 0-valued nums element.
        if (arr[i] == -1 || nums[arr[i]] == 0) {
            c++;
            continue;
        }
         // can be marked 0.
        if (c > 0) {
            c--;
            pq.add(new int[]{nums[arr[i]], arr[i]});
        }
        //no spaces in front to mark 0;
         else {
            // check if we should replace the already marked one with the curr index, to form most optimal set of elements in pq.
            if (!pq.isEmpty()) {
                if (nums[arr[i]] <= pq.first()[0]) {
                    c++;
                } else {
                    c++;
                    pq.pollFirst();
                    pq.add(new int[]{nums[arr[i]], arr[i]});
                }
            } else {
                c++;
            }
        }
    }
 // the indices which are marked, through 2nd op.
    boolean[] used = new boolean[n];
    for (int[] p : pq) {
        used[p[1]] = true;
    }
  // all 2nd op one req 2 op to get marked.
    long val = (long) pq.size() * 2;
    for (int i = 0; i < n; i++) {
        if (!used[i]) {
            val += nums[i] + 1;
        }
    }
   // if req cost is <= total places or seconds.
    return val <= mid + 1;
}

    public int earliestSecondToMarkIndices(int[] nums, int[] changeIndices) {
        this.n = nums.length;
        this.m = changeIndices.length;
        this.nums = nums;
        this.change = changeIndices;

        int L = 0;
        int R = m - 1;
        int ans = -1;

        while (L <= R) {
            int mid = (L + R) >>> 1;
            if (check(mid)) {
                ans = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        if(ans == -1)
        return ans;
        return ans+1;
    }

}