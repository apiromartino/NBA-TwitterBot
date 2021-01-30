package twitterBot

import akka.actor.{Actor, ActorRef}
import com.danielasfregola.twitter4s.entities.Tweet
import com.danielasfregola.twitter4s.TwitterStreamingClient

case class ListenMentions()

class TwitterBot(HashtagController: ActorRef) extends Actor {

  val id = 1348657069847666688L

  val streamingClient = TwitterStreamingClient() //is the client to support stream
  // connections offered by the Twitter Streaming Api.

  val NBAAccount = Seq("NBAInformation6")

  def receive = {

    case ListenMentions => {
      streamingClient.filterStatuses(tracks=NBAAccount) {
        case tweet: Tweet => {
          if(id != tweet.user.get.id) HashtagController ! ManageTweet(tweet)
        }
      }
    }
    case _ => println("Se recibio otra cosa")
  }
}



