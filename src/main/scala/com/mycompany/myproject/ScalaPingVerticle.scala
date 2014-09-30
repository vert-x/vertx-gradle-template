package com.mycompany.myproject

import org.vertx.scala.platform.Verticle
import org.vertx.scala.core.eventbus.Message

/**
 * ScalaPing Verticle
 * Octavio Luna
 *
 */

class ScalaPingVerticle extends Verticle{
  override def start(){
    val log = container.logger
    vertx.eventBus.registerHandler("ping-address", {message:Message[String] =>
	    message.reply("pong!")
	    log info "Sent back pong scala!"
    })
  }
}