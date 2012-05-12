package atd.akka

import akka.actor.Actor

case class Count(int :Int)
class Counter extends Actor {
  var int = 0

  def receive = {
    case "next" =>
      int = int +1
      sender ! Count( int )
  }
}

