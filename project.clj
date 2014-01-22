(defproject troyastorino "0.1.0-SNAPSHOT"
            :description "My personal website."
            :dependencies [[org.clojure/clojure "1.5.1"]
                           [compojure "1.1.6"]
                           [lib-noir "0.7.9"]
                           [ring-server "0.3.0"]
                           [enlive "1.1.5"]
                           [markdown-clj "0.9.41"]]
            :plugins [[lein-ring "0.8.10"]]
            :ring {:handler troyastorino.server/app}
            :main troyastorino.server
            :profiles {:dev {:dependencies [[ring-mock "0.1.5"]
                                            [ring/ring-devel "1.1.0"]]
                             :debug True}}
            :min-lein-version "2.0.0")
