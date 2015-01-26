(ns unless.core
  (:gen-class))

(defmacro unless 
  ([test true-clause]
    (list 'if (list 'not test) true-clause))
  ([test true-clause else-clause]
    (list 'if (list 'not test) true-clause else-clause)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
