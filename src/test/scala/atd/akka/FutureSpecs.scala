package atd.akka


import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import akka.actor.Actor
import Actor._
import akka.dispatch.Future

/*
class ExplicitFutureSpec extends WordSpec with MustMatchers {


  "Futures like they are described in documentation" should {
    "behave dammit" in {
      val counter = actorOf[Counter].start

      val futures :List[Future[Count]] = List.fill(10) { (counter ? "next").mapTo[Count]  }

      val results = Future.sequence( futures ).get

      results.size must be ( 10 )
    }
  }

}
*/
