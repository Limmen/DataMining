# Mining Data Streams - Triest Reservoir Sampling

## About

Implementation of the Triest-Impr Reservoir sampling technique for estimating triangle counts on the E-road dataset.

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
java -jar target/scala-2.11/frequent_itemsets-assembly-0.1.0-SNAPSHOT.jar
```

## Authors

Kim Hammar, kimham@kth.se

Konstantin Sozinov, sozinov@kth.se
