package com.deblox;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Future;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

/**
 * Created by Kegan Holtzhausen on 29/05/14.
 *
 * This loads the config and then starts the main application
 *
 */
public class Boot extends Verticle {

    JsonObject config;
    private Logger logger;

    @Override
    public void start(final Future<Void> startedResult) {
        logger = container.logger();
        logger.info("starting");

        config = this.getContainer().config();

        logger.info("config: " + config.toString());

        // Start each class mentined in services
        for (final Object service: config.getArray("services", new JsonArray())) {
            logger.info("deploying service: " + service);

            JsonObject serviceConfig = config.getObject(service.toString(), new JsonObject());
            logger.info("service's config: " + serviceConfig);

            container.deployVerticle(service.toString(), serviceConfig, serviceConfig.getInteger("instances", 1), new AsyncResultHandler<String>() {

                public void handle(AsyncResult<String> deployResult) {
                    if (deployResult.succeeded()) {
                        logger.info("successfully deployed service: " + service);
                    } else {
                        logger.error("failure while deploying service: " + service + ". reason, " + deployResult.cause());
                    }
                }

            });
        }

        startedResult.setResult(null);


        }


}

