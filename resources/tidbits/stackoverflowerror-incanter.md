Was getting a StackOverflowError when programmig in Clojure and couldn't figure out where it was coming from.  Eventually pinned it down to a reduce call.  Interestingly:

(reduce (fn [acc num] (plus acc [1 1 1]) [0 0 0] (range 10000)))

gives a StackOverflowError, but

(reduce (fn [acc num] (plus acc (matrix [1 1 1])) (matrix [0 0 0]) (range 10000)))

does not...