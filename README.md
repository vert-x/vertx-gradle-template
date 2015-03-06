# Vert.x Gradle Template

Template project for creating a Vert.x module with a Gradle build.

Clone this and adapt it to easily develop Vert.x modules using Gradle as your build tool.

By default this module contains a simple Java verticle which listens on the event bus and responds to `ping!`
messages with `pong!`.

This template also shows you how to write tests in Java, Groovy, Ruby and Python

See the [build script](build.gradle) for the list of useful tasks

## Common Pitfalls

* Gradle command `runMod` fails to find classpath:
    * Check if you the `VERTX_MODS` environment variable set. If so, unset it by running gradle wrapper like so instead `env -u VERTX_MODS ./gradlew runMod`.

* Building JRuby verticle fails with `org.vertx.java.core.VertxException: org.jruby.embed.EvalFailedException: (Errno::ENOENT) <some/directory>`:
    * Manually create `<some/folder>` and run task again.

* Running JRuby verticle fails with `LoadError: no such file to load -- rubygems`:
    * Try setting `RUBYOPT` environment variable, e.g. `export RUBYOPT=rubygems && ./gradlew runMod` or [see discussion on StackOverflow](http://stackoverflow.com/questions/9848830/should-jruby-complete-jar-come-with-rubygems).