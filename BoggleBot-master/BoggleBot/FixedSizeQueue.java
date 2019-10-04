public class FixedSizeQueue implements QueueInterface
{
/*
Brandon Q Tran
Dr Jarvis
CISC231
February 28, 2019

This class holds the implementation for a fixed size queue

Instance Variables:
	array
		an Object array that contains the elements
		of our queue. This will be one greater than
		the input size to allow easy modulus
	front
		an integer that holds the current location
		of the front of our queue
	rear
		an integer that holds the current location
		of the rear of our queue.

Constructor:
	FixedSizeQueue(int maxQueueSize)
		creates a FixedSizeQueue object set with the
		max queue size. Front and rear are initialized to 0
Methods:
	getArray()
		Private accessor that returns the Object array
	getFront()
		Private accessor that returns integer holding front
	getRear()
		Private accessor that returns integer holding rear
	maxNumElements()
		Private accessor that returns integer holding the max number
		elements in this queue
	set(int loc, Object object)
		Private mutator that sets the element at loc in this.array
		to be the Object object

	isEmpty()
		Overriding method that returns boolean value answering if
		the queue is empty
	isFull()
		Overriding method that returns boolean value answering if
		the queue is is full
	peek()
		Overriding method that returns the Object at the front without
		changing state
		Throws Underflow exception if the queue is empty
	size()
		Overriding method that returns the integer holding the number
		of elements currently in the queue
	deQueue()
		Overriding method that returns the Object at the front of the
		queue and removes it from the queue by changing state
		Throws Underflow exception if the queue is empty
	enQueue(Object object)
		Overriding method that sets the an Object at the rear of the
		queue and pushes the rear back one
		Throws Overflow exception if the queue is full.
*/
	private Object[] array;
	private int front;
	private int rear;

	public FixedSizeQueue(int maxQueueSize)
	{
		if(maxQueueSize < 1)
		{
			throw new IllegalArgumentException
			(getClass().getName()+"constructor: maxQueueSize must be at least 1");
		}
		this.array = new Object[maxQueueSize+1];
		this.front = 0;
		this.rear = 0;
	}//FixedSizeQueue(int maxQueueSize)
	private Object[] getArray(){return this.array;}
	private int getFront(){return this.front;}
	private int getRear(){return this.rear;}
	private int maxNumElements(){return this.array.length - 1;}
	private void set(int loc, Object object){this.array[loc] = object;}

	@Override
	public boolean isEmpty(){return getFront() == getRear();}
	@Override
	public boolean isFull(){return isEmpty();}
	@Override
	public Object peek()
	{
		if(isEmpty())
		{
			throw new QueueUnderFlowException
			(getClass().getName()+"peek(): the Queue is empty so there is nothing to peek at.");
		}
		return getArray()[getFront()];
	}//peek()
	@Override
	public int size()
	{
/*
Need to consider two situations:
1) front <= rear, simply calculate the difference
2) front > rear, subtract the different from the array length
*/
		int size;
		if(getRear() >= getFront())
		{
			size = getRear() - getFront();
		}
		else
		{
			size = maxNumElements() + getRear() - getFront() + 1;
		}
		return size;
	}//size()
	@Override
	public Object deQueue()
	{
/*
Check to see if the queue is empty
Throw underflow if it is empty
Change front state factoring the modulus from wrap around
*/
		int hold;

		hold = getFront();
		if(isEmpty())
		{
			throw new QueueUnderFlowException
			(getClass().getName()+"deQueue(): the Queue is empty so no object can be dequeued");
		}
		this.front = (getFront() + 1) % (maxNumElements() + 1);
		return getArray()[hold];
	}//deQueue()
	@Override
	public void enQueue(Object object)
	{
/*
Attempt to change state
Check if it is full
If it is full, reset the rear
Otherwise set object in the array
(e.g. if front and rear are 0, we set array[0]
 so that front = 0 and rear = 1)
*/
		int hold;

		hold = getRear();
		this.rear = (getRear()+1) % (maxNumElements() + 1);
		if(isFull())
		{
			this.rear = hold;
			throw new QueueOverFlowException
			(getClass().getName()+"enQueue(): the Queue is full so no object can be enqueued "+ object);
		}
		set(hold, object);
	}//enQueue(Object object)
}//class FixedSizeQueue