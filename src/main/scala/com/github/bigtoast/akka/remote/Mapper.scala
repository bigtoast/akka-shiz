package com.github.bigtoast.akka.remote

import akka.actor._
import akka.pattern.ask
import com.typesafe.config.ConfigFactory
import com.eaio.uuid.UUID
import scala.util.Random
import akka.util.Timeout
import akka.util.duration._

case class Remove[K]( key :K )
case class Put[K,V]( key :K, value :V )
case class Get[K]( key :K )
case class Value[V]( value :V )

class Mapper[K,V] extends Actor {

  var map = Map[K,V]()
  
  def receive = {
    
    case Get( key :K ) =>
     val response = map.get( key ) match {
        case Some( value ) => Some(Value(value))
        case None => None
      }
     sender ! response
     println("Returning %s " format response )
      
    case Put( key : K, value :V ) =>
      map = map + ( key -> value )
      sender ! "Put key %s and value %s".format(key, value)
      
    case Remove( key :K ) =>
      map = map - key
  
  }
}

object Mapper extends App {
  
  val system = ActorSystem("MapperSystem", ConfigFactory.load.getConfig("mapper"))
  val mapper = system.actorOf(Props[Mapper[String,String]], "mapper" )
  
  println("Started Mapper")
}

class MapperClient extends Actor {
  
  def receive = {
    case None =>
      println( "received none")
      
    case Some( value ) =>
      println(" received %s" format value)
  }
  
}

object MapperClient extends App {
  val system = ActorSystem("MapperClientSystem", ConfigFactory.load.getConfig("mapperclient"))
  val client = system.actorOf(Props[MapperClient], "mapperclient")
  val remoteMapper = system.actorFor("akka://MapperSystem@127.0.0.1:5555/user/mapper")
  
  implicit val t = Timeout( 5 second )
  
  while( true ) {
    val v = new UUID()
    val k = Random.nextInt
    val msg = Put(k,v)
    ask(remoteMapper, Put(k, v)) onSuccess { 
      case rep => println("send %s, got back %s" format( msg, rep))
    } onFailure {
      case rep => println("send %s, got back %s" format( msg, rep))
    }
    Thread.sleep(500)
  }
  
}
