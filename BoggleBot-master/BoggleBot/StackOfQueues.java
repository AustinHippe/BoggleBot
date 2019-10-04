public class StackOfQueues extends FixedSizeStack
{

	public StackOfQueues(int maxSize)
	{
		super(maxSize);
	}
	public StackOfQueues(int row, int column)
	{
		this(row*column);
	}

	public void push(ModifiedFixedQueue innerQueue)
	{
		super.push(innerQueue);
	}

	public ModifiedFixedQueue pop()
	{
		return (ModifiedFixedQueue)super.pop();
	}

	public String get(){
		ModifiedFixedQueue	hold;
		FixedSizeStack		otherStack;
		String				result;
		otherStack = new FixedSizeStack(super.size());
		result = "";
		while(!super.isEmpty()){//throw things in the other stack
			otherStack.push(super.pop());
		}
		while(!otherStack.isEmpty()){//grab the letters on the way back
			hold = (ModifiedFixedQueue)otherStack.pop();
			result = result+hold.peek();
			super.push(hold);
		}
		return result;
	}
}//class StackOfQueues