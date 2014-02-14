@echo off
call mvn -B clean test-compile >outX.txt
call mvn -B -Dbenchmark.poolSize=100 -Dbenchmark.executorServiceType=jsr166e-forkjoin-pool test >>outX.txt
call mvn -B -Dbenchmark.poolSize=100 -Dbenchmark.executorServiceType=scala-forkjoin-pool test >>outX.txt
call mvn -B -Dbenchmark.poolSize=100 -Dbenchmark.executorServiceType=java-forkjoin-pool test >>outX.txt
call mvn -B -Dbenchmark.poolSize=100 -Dbenchmark.executorServiceType=thread-pool test >>outX.txt
