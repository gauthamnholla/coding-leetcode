class Solution:
    def maxTotalReward(self, rewardValues: List[int]) -> int:
        @cache
        def smmax(x):  # max sum smaller than x
            p = bisect_left(val, x) - 1
            if p < 0:
                return 0
            ans = -1
            while p >= 0:
                if (vp:=val[p]) <= x // 2:
                    ans = max(ans, vp + smmax(vp))
                    break
                ans = max(ans, vp + smmax(x - vp))
                if ans == x - 1:
                    break
                p -= 1
            return ans
        val = sorted(set(rewardValues))
        return val[-1] + smmax(val[-1])