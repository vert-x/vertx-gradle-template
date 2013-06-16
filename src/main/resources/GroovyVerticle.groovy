

vertx.eventBus.registerHandler("ping-address") { message ->
  message.reply("pong!")
  container.logger.info("Sent back pong groovy!")
}