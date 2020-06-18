package talkative.app.player;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Initiator extends Thread implements PlayerInterface {



    private AtomicInteger countDown;

    private BlockingQueue<Sender> queue;
    private BlockingQueue<Sender> playerConstructorQueue;
    private CountDownLatch latch; // TODO: 2020. 06. 18.  change this
    private int sentMessages = 1; // TODO: 2020. 06. 18. change this

//    private Object lock = new Object();

    private final ReentrantLock lock = new ReentrantLock();

    /**
     *
     */
    public Initiator(BlockingQueue<Sender> queue, AtomicInteger countDown, BlockingQueue<Sender> playerConstructorQueue, CountDownLatch latch)
    {
        this.queue = queue;
        this.countDown = countDown;
        this.playerConstructorQueue = playerConstructorQueue;
        this.latch = latch;
    }

    @Override
    public void run()
    {
        Thread.currentThread().setName(PlayerEnum.INITIATOR.getDescription());

        Sender dispatcher = new Sender();
        dispatcher.setPlayer(PlayerEnum.INITIATOR);

        System.out.println(PlayerEnum.INITIATOR.getDescription() + " player.test.thread running");

        while (countDown.get() > 0)
        {
            try
            {
                lock.lock();
                {

                    takeMessage();

                    countDown.decrementAndGet();
                    latch.countDown();

                    Thread.sleep(1000);

                    dispatcher.setMessage(Thread.currentThread().getName() + " is sending message n." + ++sentMessages);

                    putMessage(dispatcher);

                }
            }
            catch (InterruptedException e)
            {
            }
        }

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

    /**
     * @throws InterruptedException
     */
    @Override
    public void putMessage(Sender dispatcher) throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + " is sending a message");

        playerConstructorQueue.add(dispatcher);
    }

}

