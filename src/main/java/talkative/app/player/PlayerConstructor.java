package talkative.app.player;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *
 *         Generic player of the game communicating with {@link Initiator} through player.test.thread safe {@link BlockingQueue} that waits until a queue has
 *         messages to process
 */
public class PlayerConstructor extends Thread implements PlayerInterface
{

    private BlockingQueue<Sender> queue;
    private BlockingQueue<Sender> othersQueue;
    private int sentMessages = 0;

//    private Object lock = new Object();
    private final ReentrantLock lock = new ReentrantLock();

    /**
     *
     */
    public PlayerConstructor(BlockingQueue<Sender> queue, BlockingQueue<Sender> othersQueue)
    {
        this.queue = queue;
        this.othersQueue = othersQueue;
    }

    @Override
    public void run()
    {
        Thread.currentThread().setName(PlayerEnum.PLAYERX.getDescription());

        Sender dispatcher = new Sender();
        dispatcher.setPlayer(PlayerEnum.PLAYERX);

        System.out.println(PlayerEnum.PLAYERX.getDescription() + " player.test.thread is runnning");

        while (true)
        {

            try

            {
                lock.lock();

                {
                    takeMessage();

                    dispatcher.setMessage(Thread.currentThread().getName() + " is sending message n. " + ++sentMessages);

                    putMessage(dispatcher);

                    Thread.sleep(1000);

                }
            }
            catch (InterruptedException e)
            {
            }
        }

    }

    /**
     * @throws InterruptedException
     *
     */
    @Override
    public void putMessage(Sender dispatcher) throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + " is sending a message");

        othersQueue.put(dispatcher);
    }



    /**
     * @throws InterruptedException
     */
    @Override
    public void takeMessage() throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + " is waiting for messages");
        Sender dispatcher = queue.take();
        System.out.println(Thread.currentThread().getName() + " has found a new message : " + dispatcher.getMessage());
    }

}
