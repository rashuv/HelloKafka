Apache Kafka 2.10 - 0.8.2.1 on Windows
======================================

To run Kafka you need a Linux shell under Windows. For that you should have cygwin tool, download it from here <https://cygwin.com/install.html>  
Check how to setup cygwin here <https://janschulte.wordpress.com/2013/10/13/apache-kafka-0-8-on-windows/>  
Once cygwin is setup do the following

1. Download kafka 2.10 - 0.8.2.1 binary 
https://www.apache.org/dyn/closer.cgi?path=/kafka/0.8.2.1/kafka_2.10-0.8.2.1.tgz

2. Extract and copy to C:\kafka_2.10-0.8.2.1

3. Install sbt msi installer from http://www.scala-sbt.org/release/tutorial/Installing-sbt-on-Windows.html

4. Go to your environment variables > path > 
it will show `;C:\Program Files (x86)\sbt\\bin`
just delete the extra '\' before bin so it should be 
`C:\Program Files (x86)\sbt\bin`

5. Go to kafka folder and run below commands
```
~/kafka_2.10-0.8.2.1$ sbt update
~/kafka_2.10-0.8.2.1$ sbt package
~/kafka_2.10-0.8.2.1$ sbt sbt-dependency
```
6. **Zookeeper** :
in kafka-run-class.sh at the end, replace $CLASSPATH with `cygpath -wp $CLASSPATH` and add `#JAVA=java`, see below updated code

   ```
# Launch mode
if [ "x$DAEMON_MODE" = "xtrue" ]; then
  #JAVA=java
  nohup $JAVA $KAFKA_HEAP_OPTS $KAFKA_JVM_PERFORMANCE_OPTS $KAFKA_GC_LOG_OPTS $KAFKA_JMX_OPTS $KAFKA_LOG4J_OPTS -cp `cygpath -wp $CLASSPATH` $KAFKA_OPTS "$@" > "$CONSOLE_OUTPUT_FILE" 2>&1 < /dev/null &
else
  #JAVA=java
  exec $JAVA $KAFKA_HEAP_OPTS $KAFKA_JVM_PERFORMANCE_OPTS $KAFKA_GC_LOG_OPTS $KAFKA_JMX_OPTS $KAFKA_LOG4J_OPTS -cp `cygpath -wp $CLASSPATH` $KAFKA_OPTS "$@"
fi
   ```
If you see some JAVA related error uncomment #JAVA=java from both places  
Now run
`~/kafka_2.10-0.8.2.1$ dos2unix bin/zookeeper-server-start.sh`  
Then run
`~/kafka_2.10-0.8.2.1$ bin/zookeeper-server-start.sh config/zookeeper.properties`

7. **Properties changes**  
a) in *zookeeper.properties*  
`dataDir=C://kafka_2.10-0.8.2.1/temp/zookeeper`  
b) in *server.properties*   
`log.dirs=C://kafka_2.10-0.8.2.1/logs`  
c) in *log4j.properties*  
`kafka.logs.dir=C://kafka_2.10-0.8.2.1/logs`  

8. time to run kafka, first run  
`~/kafka_2.10-0.8.2.1$ dos2unix bin/kafka-server-start.sh`  
then start kafka  
`~/kafka_2.10-0.8.2.1$ bin/kafka-server-start.sh config/server.properties`

9. Create topic "test"  
`~/kafka_2.10-0.8.2.1$ dos2unix bin/kafka-topics.sh`  
`~/kafka_2.10-0.8.2.1$ bin/kafka-topics.sh --zookeeper localhost:2181 --create --replication-factor 1 --partitions 1 --topic test`

10. to check all available topics  
`~/kafka_2.10-0.8.2.1$ bin/kafka-topics.sh --zookeeper localhost:2181 --list`

11. start producer  
`~/kafka_2.10-0.8.2.1$ dos2unix bin/kafka-console-producer.sh`  
`~/kafka_2.10-0.8.2.1$ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test`

11. start consumer  
`~/kafka_2.10-0.8.2.1$ dos2unix bin/kafka-console-consumer.sh`  
`~/kafka_2.10-0.8.2.1$ bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning`

12. Now type any string in producer shell and check if it appears in consumer  
Also if you run **HelloKafkaProducer.java** class the string *"Test message from java program"* should appear in consumer shell
