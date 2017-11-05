# Finding Similar Items: Textually Similar Documents

## About

Implementation of a well-known data mining pipeline for finding similar documents in large datasets: Shingling + Minhashing + LSH.

## How to run

Easiest option is to use sbt:
``` scala
sbt compile //compile
sbt test //test
sbt run //run
sbt assembly //generate fat jar
```
We also include a pre-built jar in the project:

``` shell
java -jar target/scala-2.11/similar_items-assembly-0.1.0-SNAPSHOT.jar
```

## Authors

Kim Hammar, kimham@kth.se

Konstantin Sozinov, sozinov@kth.se
