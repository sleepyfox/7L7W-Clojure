(rest [1 2 3])
(every? number? [1 2])
(every? number? [1 2 :three])
(some nil? [1 2 nil])
(not-every? odd? [1 3 5])
(not-any? number? [:one :two :three])
(def words ["Luke" "Chewie" "Han" "Lando"])
(filter (fn [word] (> (count word) 4)) words)
(map (fn [x] (* x x)) [1 1 2 3 5 8])
(def colours ["red", "blue"])
(def toys ["block" "car"])
(for [x colours] (str "I like " x))
(for [x colours, y toys] (str "I like " x " " y "s"))
(defn small-word [word] (< (count word) 4))
(for [x colours, y toys, :when (small-word y)] (str "I like " x " " y "s"))
(reduce + [1 2 3 4 5])
(reduce * [1 2 3 4 5])
(sort [3 1 4 2])
(defn abs [x] (if (< x 0) (- x) x))
(sort-by abs [-1 -4 3 2])
(range 1 10)
(range 1 10 3)
(range 10)
(take 3 (repeat "Use the Force, Luke"))
(take 5 (cycle [:lather :rinse :repeat]))
(take 5 (drop 2 (cycle [:lather :rinse :repeat])))
(->> [:lather :rinse :repeat] cycle (drop 2) (take 5))
(take 5 (interpose :and [:lather :rinse :repeat]))
(take 20 (interleave (cycle (range 2)) (cycle (range 3))))
(take 5 (iterate inc 1))
(take 5 (iterate dec 0))
(defn fib-pair [[a b]] [b (+ a b)])
(take 5 (map first (iterate fib-pair [1 1])))
(nth (map first (iterate fib-pair [1 1])) 50)
(defn factorial [n] (apply * (take n (iterate inc 1))))
(factorial 5)
(defmacro unless [test body] (list 'if (list 'not test) body))
(macroexpand '(unless condition body))
(unless true (println "No more danger, Will."))
(unless false (println "No more danger, Will."))
