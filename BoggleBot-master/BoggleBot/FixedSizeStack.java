public class FixedSizeStack implements StackInterface
{
/*
Brandon Q Tran
Dr Jarvis
CISC231
February 28, 2019

This class holds the implementation for a fixed size stack

Instance Variables:
	array
		an Object array that contains the elements
		of our stack. This will be one greater than
		the input size to allow easy modulus
	top
		integer holding the height of the stack

Constructor:
	FixedSizeStack(int maxStackSize)
		creates a FixedSizeStack object set with the
		max stack size. Top is initialized to the
		max value
Methods:
	getArray()
		Private accessor that returns the Object array
	getTop()
		Private accessor that returns the this.top
	set(int loc, Object object)
		Private mutator that sets the element at loc in this.array
		to be the Object object

	isEmpty()
		Overriding method that returns boolean value answering if
		the stack is empty
	isFull()
		Overriding method that returns boolean value answering if
		the stack is is full
	peek()
		Overriding method that returns the Object at the top of the
		stack without changing the stack
		Throws Underflow exception if the stack is empty
	size()
		Overriding method that returns the integer holding the number
		of elements currently in the stack
	pop()
		Overriding method that returns the Object at the top of the
		stack and lowers the top
		Throws Underflow exception if the stack is empty
	push(Object object)
		Overriding method that increases the stack by adding object
		to the top of the stack
		Throws Overflow exception if the stack is full.
*/
	private Object[] array;
	private int top;

	public FixedSizeStack(int maxStackSize)
	{
		if(maxStackSize < 1)
		{
			throw new IllegalArgumentException
			(getClass().getName()+"maxStackSize must be at least 1\n");
		}
		this.array = new Object[maxStackSize];
		this.top = maxStackSize;
	}//FixedSizeStack()
	private Object[] getArray(){return this.array;}
	private int getTop(){return this.top;}
	private void set(int loc, Object object){this.array[loc] = object;}

	@Override
	public boolean isEmpty(){return getTop() == (getArray().length);}
	@Override
	public boolean isFull(){return getTop() == 0;}
	@Override
	public Object peek()
	{
		if(isEmpty())
		{
			throw new StackUnderFlowException
			(getClass().getName()+"peek(): The stack is empty so there is nothing to peek");
		}
		return getArray()[getTop()];
	}//peek()
	@Override
	public int size(){return getArray().length - top;}
	@Override
	public Object pop()
	{
/*
Check to see if the stack is empty
Otherwise increase top by one
and return the array element before it
(e.g. max Stack of 3 with one element in it
top was 2 and now is 3, return array[2];
*/
		if(isEmpty())
		{
			throw new StackUnderFlowException
			(getClass().getName()+"pop(): The stack is empty so there is nothing to pop");
		}
		this.top = getTop() + 1;
		return getArray()[getTop()-1];
	}//pop()
	@Override
	public void push(Object object)
	{
/*
Check to see if the stack is full
Otherwise decrease top by one
and return the array element before it
(e.g. max Stack of 3 with one element in it
top was 2 and now is 1, set array[1]=object;
*/
		if(isFull())
		{
			throw new StackOverFlowException
			(getClass().getName()+"push(): The stack is full so we cannot push: "+ object);
		}
		this.top = getTop()-1;
		set(getTop(),object);
	}//push(Object object)
}//class FixedSizeStack