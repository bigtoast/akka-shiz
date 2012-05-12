package atd.akka

import akka.actor.Actor
import akka.actor.ReceiveTimeout
import akka.actor.ActorRef
import akka.util.duration._

class ForShutdown( stopIn :Long) extends Actor {

  context.setReceiveTimeout( stopIn seconds )
  
  def receive :Receive = {
    
    case ReceiveTimeout =>
      context.stop( self )
      
    case str :String =>
      sender ! str
  }
}

class Parent extends Actor {
  
  def receive :Receive = {
    
    case actr :ActorRef =>
      context.watch( actr )
 
      println("linked with %s" format actr)
      
    //case msg :Death =>
      //println("%s died" format msg)
      
    case msg =>
      println("UNHANDLED MSG %s" format msg)
      
  }
}