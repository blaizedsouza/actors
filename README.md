```sh
  ☆ノノハ
  从*’w’)
(つactorsと)
```

Evaluation of API and performance of different actor libraries written on Scala:
[Akka](https://github.com/akka/akka/blob/master/akka-actor/src/main/scala/akka/actor/Actor.scala) vs.
[Lift](https://github.com/lift/framework/blob/master/core/actor/src/main/scala/net/liftweb/actor/LiftActor.scala) vs.
[Scala](https://github.com/scala/scala/blob/master/src/actors/scala/actors/Actor.scala) vs.
[Scalaz](https://github.com/scalaz/scalaz/blob/master/core/src/main/scala/scalaz/concurrent/Actor.scala)

[![Build Status](https://secure.travis-ci.org/plokhotnyuk/actors.png)](http://travis-ci.org/plokhotnyuk/actors)

## Benchmarks and their goals
* `Enqueueing` - memory footprint of internal actor queue per submitted message and submition throughput in a single thread
* `Dequeueing` - message handling throughput in a single thread
* `Initiation` - memory footprint of minimal actors and initiation time in a single thread
* `Single-producer sending` - throughput of message sending from external thread to an actor
* `Multi-producer sending` - throughput of message sending from several external threads to an actor
* `Max throughput` - throughput of message sending from several external threads to several actors
* `Ping latency` - average latency of sending of a ping message between two actors
* `Ping throughput 10K` - throughput of executor service by sending of a ping message between 10K pairs of actors

## Hardware required
- CPU: 2 cores or more
- RAM: 6Gb or greater

## Software installed required
- JDK: 1.7.0_x or newer
- Maven: 3.x or sbt: 0.12.x

## Building & running benchmarks
Use following command-line instructions to build from sources and run benchmarks with Scala's ForkJoinPool in FIFO mode:
```sh
mvn -B clean test >outX.txt
```
or
```sh
sbt clean test >outX.txt
```

To run benchmarks for all available types of executor service use `mvnAll.sh` or `sbtAll.sh` scripts (for Windows: `mvnAll.bat` or `sbtAll.bat`).

Recommended values of JVM options which can be set for MAVEN_OPTS and SBT_OPTS system variables:

```sh
-server -Xms1g -Xmx1g -Xss1m -XX:NewSize=512m -XX:PermSize=256m -XX:MaxPermSize=256m -XX:+TieredCompilation -XX:+UseG1GC -XX:+UseNUMA -XX:+UseCondCardMark -XX:-UseBiasedLocking -XX:+AlwaysPreTouch
```

## Test result descriptions
Results of running mvnAll.bat or mvnAll.sh scripts on different environments with
pool size set to default (number of available processors), 1, 10 or 100 values accordingly:

#### out0*.txt
Intel(R) Core(TM) i7-2640M CPU @ 2.80GHz (max 3.50GHz), RAM 12Gb DDR3-1333, Windows 7 sp1, Oracle JDK 1.8.0_20-ea-b11 64-bit

#### out1*.txt
Intel(R) Core(TM) i7-2640M CPU @ 2.80GHz (max 3.50GHz), RAM 12Gb DDR3-1333, Windows 7 sp1, Oracle JDK 1.7.0_60-ea-b15 64-bit

#### out2*.txt
Intel(R) Core(TM) i7-3740QM CPU @ 2.70GHz (max 3.70GHz), RAM 16Gb DDR3-1600, Mac OS X 10.9.2, JDK 1.8.0_05-b13 64-bit

#### out3*.txt
Intel(R) Core(TM) i7-3770 CPU @ 3.40GHz (max 3.90GHz) overclocked to 4.30GHz, RAM 32Gb DDR3-1666, Linux Mint 14, Oracle JDK 1.7.0_55-b13 64-bit