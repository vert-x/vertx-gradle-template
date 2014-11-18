import org.vertx.scala.core.eventbus.Message

val log = container.logger
vertx.eventBus.registerHandler("ping-address", {message:Message[String] =>
	message.reply("pong!")
	log info "Sent back pong scala!"
})