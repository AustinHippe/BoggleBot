public class ModifiedFixedQueue extends FixedSizeQueue
{
	private Letter first;
	public ModifiedFixedQueue(Letter firstLetter)
	{
		super(8);//We are guarenteed that there are at most 8 neighbors in Two Dimensions
/*		if(!Character.isLetter(firstLetter))
		{
			throw new IllegalArgumentException
			(getClass().getName()+"constructor: character is not a letter");
		}
//*/
		this.first = firstLetter;
	}

	@Override
	public Object peek(){return this.first.get();}
	public Object getFirst(){return this.first;}
	public void neighbors()
	{
		Letter 	hold;
		String 	neighbors;
		int		size;
		neighbors = "";
		size = super.size();
		for(int i=0; i < size; i++)
		{
			hold = (Letter)super.deQueue();
/*			if(!hold.used())
			{
				neighbors = neighbors+" "+hold.get();
			}
//*/
			neighbors = neighbors+" "+hold.get();
			super.enQueue(hold);
		}
		System.out.println(((Letter)getFirst()).get()+" has the following neighbors: "+neighbors);
	}
}//class ModifiedFixedQueue