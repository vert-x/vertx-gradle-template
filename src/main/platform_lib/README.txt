If you want override the default langs.properties, cluster.xml or any other config for the Vert.x platform (i.e.
not for the module!) then you can add them in here and they will be added to the platform classpath when running
your module using Gradle.

Also, if you are assembling your module into a fatjar, you can move the platform_lib directory into your
src/main/resources directory - this means the directory will end up in your module and the fatjar starter knows to add
that directory (and any jars/zips inside it) to the Vert.x platform classpath when executing your module as a fatjar.