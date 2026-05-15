class Solution:
    def minimumCost(self, target: str, words: List[str], costs: List[int]) -> int:        
        dic = defaultdict(dict)

        for w, c in zip(words, costs):
            if w not in dic[len(w)]:
                dic[len(w)][w] = c
            elif c < dic[len(w)][w]: 
                dic[len(w)][w] = c
        
        # only sqrt(n) unique length
        lengths = sorted(dic.keys())

        n = len(target)
        
        dp = [inf] * (n + 1)
        dp[0] = 0

        for i in range(n):
            # at each location i, check all possible length j
            # i.e., if target[i : i + j] in the hash table 
            for j in lengths:
                if i + j > n: 
                    break
                else:
                    try:
                        cost = dic[j][target[i : i + j]]
                        if dp[i + j] > dp[i] + cost:
                            dp[i + j] = dp[i] + cost
                    except KeyError:
                        pass

        return dp[-1] if dp[-1] < inf else -1