# Adding local jar dependencies using Leiningen 2

_Note: Solutions works with Leiningen 2.0.0-preview10_

I've been going crazy trying to get lein 2 to add a local jar to the classpath for Clojure project I've been working on. The right way to do this is to create a local maven repository and stick your local jar in there. There is even a lein plugin that helps you do this: [lein-localrepo](https://github.com/kumarshantanu/lein-localrepo).

Unfortunately, I can't do this for my project. The Clojure component I'm building sits inside of a large existing codebase with components written in many different languages. The system uses a message passing tool communicate between these different components, and as part of the build process to interact with Java, it creates jars to holding the message type classes. If I was still using lein 1, I could point to these jars using the `:extra-classpath-dirs` key in my `project.clj`, but that was discontinued in lein 2 for [repeatability](https://github.com/technomancy/leiningen/wiki/Repeatability).
 
Luckily, I found a hack that still lets you add dependencies for jars by their relative location in the filesystem.  By using `:resource-paths`, you can add the jars you need to the classpath. I put the jars I needed into a "lcm-jars" directory relative to my classpath, and added the following line to my `project.clj` file:

```clojure
  :resource-paths ["lcm-jars/*"]
  
```

The wildcard character `*` adds all the jars inside the folder to the classpath. Other folders or jars could be added to the resource-paths vector, as long as all the paths are relative to the project root directory.
