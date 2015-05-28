package com.deblox;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.io.File;
import java.util.function.Consumer;

/*
 * Runner for Running Tests and from within IDE
 */
public class DebloxRunner {


  public static void runJavaExample(String prefix, Class clazz, boolean clustered) {
    runJavaExample(prefix, clazz, new VertxOptions().setClustered(clustered));
  }

  public static void runJavaExample(String prefix, Class clazz, VertxOptions options) {
    String exampleDir = prefix + clazz.getPackage().getName().replace(".", "/");
    runExample(exampleDir, clazz.getName(), options);
  }

  public static void runScriptExample(String prefix, String scriptName, boolean clustered) {
    File file = new File(scriptName);
    String dirPart = file.getParent();
    String scriptDir = prefix + dirPart;
    DebloxRunner.runExample(scriptDir, scriptDir + "/" + file.getName(), clustered);
  }

  public static void runScriptExample(String prefix, String scriptName, VertxOptions options) {
    File file = new File(scriptName);
    String dirPart = file.getParent();
    String scriptDir = prefix + dirPart;
    DebloxRunner.runExample(scriptDir, scriptDir + "/" + file.getName(), options);
  }

  public static void runExample(String exampleDir, String verticleID, boolean clustered) {
    runExample(exampleDir, verticleID, new VertxOptions().setClustered(clustered));
  }

  public static void runExample(String exampleDir, String verticleID, VertxOptions options) {
    System.setProperty("vertx.cwd", exampleDir);
    Consumer<Vertx> runner = vertx -> {
      try {
        vertx.deployVerticle(verticleID);
      } catch (Throwable t) {
        t.printStackTrace();
      }
    };
    if (options.isClustered()) {
      Vertx.clusteredVertx(options, res -> {
        if (res.succeeded()) {
          Vertx vertx = res.result();
          runner.accept(vertx);
        } else {
          res.cause().printStackTrace();
        }
      });
    } else {
      Vertx vertx = Vertx.vertx(options);
      runner.accept(vertx);
    }
  }
}