(defproject cluelog "0.1.0-SNAPSHOT"
  :description "cluelog -> A very basic logging tool written in Clojure."
  :url "https://github.com/siebenmarck/cluelog"
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [org.clojure/data.json "0.2.4"]
                 [com.ashafa/clutch "0.4.0"]
                 ]
  :plugins [[lein-ring "0.8.3"]]
  :min-lein-version "2.0.0"
  :ring {:handler cluelog.handler/app}
  )


