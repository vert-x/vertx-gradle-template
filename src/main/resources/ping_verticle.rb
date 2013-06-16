require "vertx"
include Vertx

EventBus.register_handler('ping-address') do |msg|
  msg.reply 'pong!'
  puts 'sent back pong ruby'
end
