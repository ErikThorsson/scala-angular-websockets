package services

import akka.actor.{Actor, ActorRef, PoisonPill, Props}

object ChatServiceActor {
  def props(out: ActorRef) = Props(new ChatServiceActor(out))
}

class ChatServiceActor(out: ActorRef) extends Actor {
  override def receive: Receive = {
    case msg: String if msg.contains("close") =>
      out ! s"Closing the connection as requested"
      self ! PoisonPill
    case msg: String =>
      out ! s"Echo, Received the message: ${msg}"
  }

    // override def postStop() = {
    //     println("closing websocket connection.")
    //     someResource.close()
    // }

}