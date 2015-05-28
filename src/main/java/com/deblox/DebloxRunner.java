package com.deblox;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;

import java.io.File;
import java.util.function.Consumer;

/*
 * Runner for Running Tests and from within IDE
 */
public class DebloxRunner {

  private static final Logger logger = LoggerFactory.getLogger(DebloxRunner.class);

  public static void runJava(String prefix, Class clazz, boolean clustered) {
    runJava(prefix, clazz, new VertxOptions().setClustered(clustered));
  }

  public static void runJava(String prefix, Class clazz, VertxOptions options) {
    String runDir = prefix + clazz.getPackage().getName().replace(".", "/");
    run(runDir, clazz.getName(), options);
  }

  public static void runScript(String prefix, String scriptName, boolean clustered) {
    File file = new File(scriptName);
    String dirPart = file.getParent();
    String scriptDir = prefix + dirPart;
    DebloxRunner.run(scriptDir, scriptDir + "/" + file.getName(), clustered);
  }

  public static void runScript(String prefix, String scriptName, VertxOptions options) {
    File file = new File(scriptName);
    String dirPart = file.getParent();
    String scriptDir = prefix + dirPart;
    DebloxRunner.run(scriptDir, scriptDir + "/" + file.getName(), options);
  }

  public static void run(String runDir, String verticleID, boolean clustered) {
    run(runDir, verticleID, new VertxOptions().setClustered(clustered));
  }

  public static void run(String runDir, String verticleID, VertxOptions options) {
    logger.info("booting");
    System.setProperty("vertx.cwd", runDir);
    Consumer<Vertx> runner = vertx -> {
      try {
        JsonObject config = Util.loadConfig("/conf.json");
        // put config inside a config tag
        DeploymentOptions deploymentOptions = new DeploymentOptions(config);
        vertx.deployVerticle(verticleID, deploymentOptions);
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