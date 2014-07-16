package com.github.plokhotnyuk.actors

import com.typesafe.config.ConfigFactory._
import com.typesafe.config.Config

class AkkaNonBalanceableActorSpec extends AkkaActorSpec {
  override def config: Config = load(parseString(
    """
      akka {
        log-dead-letters = 0
        log-dead-letters-during-shutdown = off
        deployment {
          default {
            dispatcher = "akka.actor.default-dispatcher"
          }
        }
        actor {
          unstarted-push-timeout = 100s
          default-dispatcher {
            executor = "com.github.plokhotnyuk.actors.CustomExecutorServiceConfigurator"
            throughput = 1024
            mailbox-type = "akka.dispatch.SingleConsumerOnlyUnboundedMailbox"
          }
        }
      }
    """))
}
