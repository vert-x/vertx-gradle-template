# Vert.x Gradle Template

## build fatJar with logback libs embedded

./gradlew dist

## running

```

java -Dorg.vertx.logger-delegate-factory-class-name=org.vertx.java.core.logging.impl.SLF4JLogDelegateFactory -jar build/libs/my-module-1.0.0-final-fat.jar -cp /somepath/logback.xml

```

