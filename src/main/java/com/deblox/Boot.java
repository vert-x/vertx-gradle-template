package com.deblox;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;

/**
 * Created by Kegan Holtzhausen on 29/05/14.
 *
 * This loads the config and then starts the main application
 *
 */
public class Boot extends AbstractVerticle {

  JsonObject config;

  private static final Logger logger = LoggerFactory.getLogger(Boot.class);

  // Allow running direct from IDE, loads resources/conf.json
  public static void main(String[] args) {
    DebloxRunner.runJava("src/main/java", Boot.class, false);
  }

  @Override
  public void start(final Future<Void> startedResult) {
    logger.info("\n" +
            "████████▄     ▄████████ ▀█████████▄   ▄█        ▄██████▄  ▀████    ▐████▀      ▀█████████▄   ▄██████▄   ▄██████▄      ███     \n" +
            "███   ▀███   ███    ███   ███    ███ ███       ███    ███   ███▌   ████▀         ███    ███ ███    ███ ███    ███ ▀█████████▄ \n" +
            "███    ███   ███    █▀    ███    ███ ███       ███    ███    ███  ▐███           ███    ███ ███    ███ ███    ███    ▀███▀▀██ \n" +
            "███    ███  ▄███▄▄▄      ▄███▄▄▄██▀  ███       ███    ███    ▀███▄███▀          ▄███▄▄▄██▀  ███    ███ ███    ███     ███   ▀ \n" +
            "███    ███ ▀▀███▀▀▀     ▀▀███▀▀▀██▄  ███       ███    ███    ████▀██▄          ▀▀███▀▀▀██▄  ███    ███ ███    ███     ███     \n" +
            "███    ███   ███    █▄    ███    ██▄ ███       ███    ███   ▐███  ▀███           ███    ██▄ ███    ███ ███    ███     ███     \n" +
            "███   ▄███   ███    ███   ███    ███ ███▌    ▄ ███    ███  ▄███     ███▄         ███    ███ ███    ███ ███    ███     ███     \n" +
            "████████▀    ██████████ ▄█████████▀  █████▄▄██  ▀██████▀  ████       ███▄      ▄█████████▀   ▀██████▀   ▀██████▀     ▄████▀   1.0\n" +
            "                                     ▀                                                                                        ");

    config = config();
    logger.info("config: " + config.toString());

    // Start each class mentioned in services
    for (final Object service : config.getJsonArray("services", new JsonArray())) {
      logger.info("deploying service: " + service);

      // get the config for the named service
      JsonObject serviceConfigJson = config.getJsonObject(service.toString(), new JsonObject());
      logger.info("serviceConfigJson: " + serviceConfigJson);

      // See DeploymentOptions.fromJson for all the possible configurables
      DeploymentOptions serviceConfig = new DeploymentOptions(serviceConfigJson);
      logger.info("service's config: " + serviceConfig.toJson().toString());

      vertx.deployVerticle(service.toString(), serviceConfig, res -> {

        if (res.succeeded()) {
          logger.info("successfully deployed service: " + service);
        } else {
          logger.error("failure while deploying service: " + service);
          res.cause().printStackTrace();
        }

      });
    }
  }
}


