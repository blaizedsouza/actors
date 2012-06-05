package com.github.plokhotnyuk.actors

import annotation.tailrec
import java.util.concurrent.atomic.{AtomicInteger, AtomicReference}

/**
 * Based on non-intrusive MPSC node-based queue, described by Dmitriy Vyukov:
 * http://www.1024cores.net/home/lock-free-algorithms/queues/non-intrusive-mpsc-node-based-queue
 */
final class ThreadBasedActor[A](e: A => Unit, onError: Throwable => Unit = throw (_)) {
  private[this] val doRun = new AtomicInteger(1)
  private[this] var spin = 0
  private[this] var anyA: A = _ // Don't know how to simplify this
  private[this] var tail = new Node[A](anyA)
  private[this] val head = new AtomicReference[Node[A]](tail)

  start()

  def !(a: A) {
    val n = new Node[A](a)
    head.getAndSet(n).lazySet(n)
  }

  def exit() {
    doRun.set(0)
  }

  private[this] def start() {
    new Thread() {
      override def run() {
        handleMessages()
      }
    }.start()
  }

  private[this] def handleMessages() {
    while (doRun.get != 0) {
      tail = batchHandle(tail, 1024)
    }
  }

  @tailrec
  private[this] def batchHandle(n: Node[A], i: Int): Node[A] = {
    val next = n.get
    if (next ne null) {
      handle(next.a)
      if (i > 0) {
        batchHandle(next, i - 1)
      } else {
        next
      }
    } else {
      backOff()
      n
    }
  }

  private[this] def backOff() {
    spin = if (spin == 1024) spin + 1 else { Thread.`yield`(); 0 }
  }

  private[this] def handle(a: A) {
    try {
      e(a)
    } catch {
      case ex => onError(ex)
    }
  }
}