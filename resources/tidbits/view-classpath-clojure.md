# Viewing the Java classpath from a Clojure REPL 

I've been struggling for a while trying to include some local jars as 
dependencies in a Clojure project using Leiningen (hopefully a solution for that 
is coming soon). In the course of this battle I needed to know what lein was 
actually putting on the classpath. Here the quickest way I found to print out 
the classpath in Clojure.

```clojure
(import [java.net URL URLClassLoader])

(clojure.pprint/pprint 
  (map (fn [x] (. x getFile)) 
    (.getURLs (ClassLoader/getSystemClassLoader))))
    
```
