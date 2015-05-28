# deBlox vert.x template

Modified version of [vertx-gradle-template](https://github.com/vert-x/vertx-gradle-template). 

## features

* Boot Class
* Logback

## building

Task *dist* will build a fatJar and roll in the logback libs.

```
./gradlew dist

```

## Running

When running as a fatJar, remember to specify the alternate logging implementation.


```

JAVA_OPTS="-Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.impl.SLF4JLogDelegateFactory"

java $JAVA_OPTS -jar my-module-1.0.0-final-fat.jar -cp /dir/with/logback/xml

```

