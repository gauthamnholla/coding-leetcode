class Solution {
    public class TreeSegment{
        long[][] tree;
        TreeSegment(int n){
            tree = new long[n*4][4];
        }

        public void build(int ind, int lft, int rgt, int[] arr){
            if(lft == rgt){
                tree[ind][0] = 0;
                tree[ind][1] = Long.MIN_VALUE;
                tree[ind][2] = Long.MIN_VALUE;
                tree[ind][3] = arr[lft];
                return;
            }

            int mid = (lft+rgt)/2;
            build(2*ind+1, lft, mid, arr);
            build(2*ind+2, mid+1, rgt, arr);
            merge(ind);
        }

        public void merge(int ind){
            int lft = 2*ind+1;
            int rgt = 2*ind+2;

            for(int i = 0;i < 4;i++) tree[ind][i] = Long.MIN_VALUE;

            for(int lf = 0;lf < 2;lf++){
                for(int ll = 0;ll < 2;ll++){
                    int lftSeg = lf*2+ll;
                    if(tree[lft][lftSeg] == Long.MIN_VALUE) continue;

                    for(int rf = 0;rf < 2;rf++){
                        for(int rl = 0;rl < 2;rl++){
                            int rgtSeg = rf*2+rl;
                            if(tree[rgt][rgtSeg] == Long.MIN_VALUE) continue;

                            if(ll == 1 && rf == 1) continue;

                            int newLft = lf;
                            int newRgt = rl;
                            int newInd = newLft*2 + newRgt;
                            tree[ind][newInd] = Math.max(tree[ind][newInd],
                                    tree[lft][lftSeg] + tree[rgt][rgtSeg]);
                        }
                    }
                }
            }
        }

        public void queryPos(int ind, int tarInd, int lft, int rgt, int value){
            if(lft == rgt){
                tree[ind][3] = value;
                return;
            }

            int mid = (lft+rgt)/2;

            if(tarInd <= mid){
                queryPos(2*ind+1, tarInd, lft, mid, value);
            }
            else queryPos(2*ind+2, tarInd, mid+1, rgt, value);
            merge(ind);
        }
    }

    public int maximumSumSubsequence(int[] nums, int[][] queries) {
        int len = nums.length;
        TreeSegment tree = new TreeSegment(len);
        int MOD = (int)1e9+7;
        long ans = 0;

        tree.build(0, 0, len-1, nums);
        for(int[] query : queries){
            tree.queryPos(0, query[0], 0, len-1, query[1]);
            long max = Long.MIN_VALUE;
            for(long ele: tree.tree[0]) max = Math.max(max, ele);
            ans = (ans + max) % MOD;
        }

        return (int) ans;
    }
}