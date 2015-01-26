(defn size "the size of a vector" [v]
  (if (empty? v)
    0
    (inc (size (rest v)))))

(defn size-recur "the size of a vector, using recur" [v]
  (loop [l v c 0]
    (if (empty? l)
      c
      (recur (rest l) (inc c)))))

(size [1 2 3])
(size-recur [1 2 3 4])