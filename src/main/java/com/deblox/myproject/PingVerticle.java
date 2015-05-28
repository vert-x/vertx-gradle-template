/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 *
 */
package com.deblox.myproject;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;

/*
 * This is a simple Java verticle which receives `ping` messages on the event bus and sends back `pong` replies
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class PingVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(PingVerticle.class);
  EventBus eb;

  public void start(Future<Void> startFuture) throws Exception {
    logger.info("starting");
    logger.info("config: " + config().toString());

    eb = vertx.eventBus();

    eb.consumer("ping-address", message -> {
      message.reply("pong!");
    });

    // wait 2 seconds before completing startup
    vertx.setTimer(1000, tid -> {
      logger.info("startup complete");
      startFuture.complete();
    });

  }
}
