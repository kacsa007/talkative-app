# **Problem:**
Given a Player class - an instance of which can communicate with other Players.

#### **The requirements are as follows:**

1. Create 2 Player instances
2. One of the players should send a message to second player (let's call this player "initiator")
3. When a player receives a message, it should reply with a message that contains the received message concatenated with the value of a counter holding the number of messages this player already sent.
4. Finalize the program (gracefully) after the initiator sent 10 messages and received back 10 messages (stop condition)
5. Both players should run in the same java process (strong requirement)
6. Document for every class the responsibilities it has.
7. Additional challenge (nice to have) opposite to 5: have every player in a separate JAVA process.


## **Class Definitions:**

In the creation of this program I tried to use as much programming best practices as I could. Here are some of them.

#### **BaseApi -> The PLayer interface:**
 *  Used for threading --> using the queue approach is going to put or take messages out of it.
 * 
	
#### **Client:**
 * In case of threading: --> connection keepalive and messaging using queues
 * In case of socket --> socket connection and communication between two players.
 
 
#### **Player:**
 * Player interface used for defining players involved in chatting. Also this interface implementation methods are using generics.
 

 
#### **Declarations:**
 * Constants class for the constant values. You can change the port, give hostname/ip address and so on.
 

 
 
# **How to run?**

First of all create a package by running below command:

    - mvn clean install

Then run sh file. It will launch two instances of the application. First one will start the game in thread mode. 
The second one will be the socket where the game runs in two different JAVA process.

The game was tested both on windows and Mac OSX (latest version). You can change the port in the Declaration.JAVA class.
Development IDEA: Intellij Ultimate University Edition
    ./run-thread.sh  ->> for threading
    ./run-socket.sh  --> this is going to run the game together with the dependencies


#### **Blockers encountered during the programming**

Tried the logging approach which was a bad idea, so I kept some base classes and extended them.
I read numerous articles about this - first was the EventBus architecture.Didn't like that,
was complicated and didn't manage to get through in a relatively short period of time.
So, to not reinvent the wheel, I've found Java socket programming and concurrency together with 
threading. I've done this is python some months ago, so I had some understanding about it.

Learned some programming ideas using these two stackoverflow articles. One is about LOGGING and try to not print stacktrace 
related information e.printStackTrace() is a bad programming approach. 
The second one is about thread safetiness and some metodologies to use the lock.lock() in JAVA.

https://stackoverflow.com/questions/442564/avoid-synchronizedthis-in-java

https://stackoverflow.com/questions/585599/whats-causing-my-java-net-socketexception-connection-reset

