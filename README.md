# Vert.x Gradle Template

Modified to support logback, see gradle/vertx.gradle for tasks.

## Build

To build a fatJar with logback libs

```

./gradlew dist

```

## Running


```

java -Dorg.vertx.logger-delegate-factory-class-name=org.vertx.java.core.logging.impl.SLF4JLogDelegateFactory -jar build/libs/my-module-1.0.0-final-fat.jar -cp /dir/with/logback/xml

```

