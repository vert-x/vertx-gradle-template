# Vert.x Gradle Template

## running a fatJar

```

java -Dorg.vertx.logger-delegate-factory-class-name=org.vertx.java.core.logging.impl.SLF4JLogDelegateFactory -jar build/libs/my-module-1.0.0-final-fat.jar  -cp ./build/deps/slf4j-api-1.7.7.jar:./build/deps/slf4j-simple-1.7.7.jar:./src/main/resources/logback.xml

```

