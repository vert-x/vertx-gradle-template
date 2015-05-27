package com.deblox;


import io.vertx.core.*;
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

  DeploymentOptions config;
  private static final Logger logger = LoggerFactory.getLogger(Boot.class);

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

    // get the conf.json file
//        config = this.getContainer().config();
    config = new DeploymentOptions();

    logger.info("config: " + config.toString());

    // Start each class mentioned in services
    for (final Object service : config.getConfig().getJsonArray("services", new JsonArray())) {
      logger.info("deploying service: " + service);

      DeploymentOptions serviceConfig = new DeploymentOptions(config.getConfig().getJsonObject(service.toString(), new JsonObject()));
      logger.info("service's config: " + serviceConfig);

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


