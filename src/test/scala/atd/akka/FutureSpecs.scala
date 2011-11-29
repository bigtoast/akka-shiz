package atd.akka

import akka.util.duration._
import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import akka.actor.Actor
import Actor._
import akka.dispatch.Future


class ExplicitFutureSpec extends WordSpec with MustMatchers {


  "Futures like they are described in documentation" should {
    "behave dammit" in {
      case class Count(int :Int)
      class Counter extends Actor {
        var int = 0

        def receive = {
          case "next" =>
            int = int +1
            self.channel ! Count( int )
        }
      }

      val counter = actorOf[Counter].start

      val futures :List[Future[Count]] = List.fill(10) { counter ? "next" }

      val results = Future.sequence( futures ).get

      results.size must be ( 10 )
    }
  }

}
