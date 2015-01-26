(defn size2 [v]
  (loop [l v, c 0]
    (if (empty? l)
      c
      (recur (rest l) (inc c)))))

(size2 [1 2 3 4])
