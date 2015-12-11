package com.ai.actor.akka

import akka.actor.SupervisorStrategy.Resume
import akka.actor.{OneForOneStrategy, SupervisorStrategy, SupervisorStrategyConfigurator}

/**
 * @author liujignag@biostime.com
 * @since 1.6
 */
class UserGuardianStrategyConfigurator
  extends SupervisorStrategyConfigurator{
  def create(): SupervisorStrategy = {
    OneForOneStrategy() {
      case _ => Resume
    }
  }
}
