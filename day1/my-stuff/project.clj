(defproject my-stuff "0.1.0-SNAPSHOT"
  :description "day1 code from 7L7W:Clojure"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :main ^:skip-aot my-stuff.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
