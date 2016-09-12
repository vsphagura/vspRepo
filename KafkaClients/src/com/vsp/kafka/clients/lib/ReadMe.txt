Producer:
This is straight forward and can be used as standalone producer for partitioned or non-partitioned 
topics.

Consumer:
This consumer can be also used for partitioned and non-partitioned Topics. For partitioned topics
there are two thread pools one is for the threads to read from each partition and the other 
thread pool for messages so that processing of messages does not effect the rate of reading the 
messages.