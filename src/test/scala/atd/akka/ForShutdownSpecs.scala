package atd.akka

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import akka.testkit.TestKit
import akka.actor.Actor

/*
class ForShutdownSpecs extends WordSpec with ShouldMatchers with TestKit {

  
  "We should timeout and shutdown the actor after 1 seconds even after it received initial msgs" in {
    val forShutdown = Actor.actorOf( new ForShutdown(1000) ).start
    val parent = Actor.actorOf[Parent].start
    
    parent ! forShutdown
    forShutdown ! "nothing important"
    
    Thread.sleep(1500)
    
    forShutdown.isRunning should be (false)
    forShutdown.isShutdown should be (true)
  }
}
*/