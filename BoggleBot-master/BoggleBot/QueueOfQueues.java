public class QueueOfQueues
{
	private FixedSizeQueue queue;

	public QueueOfQueues(int maxSize)
	{
		this.queue = new FixedSizeQueue(maxSize);
	}
	public QueueOfQueues(int row, int column)
	{
		this(row*column);
	}

	private FixedSizeQueue getQueue(){return this.queue;}

	public void enQueue(ModifiedFixedQueue innerQueue)
	{
		getQueue().enQueue(innerQueue);
	}

	public ModifiedFixedQueue deQueue()
	{
		return (ModifiedFixedQueue)getQueue().deQueue();
	}
}//class QueueOfQueues