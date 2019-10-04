public class Letter
{
	private char letter;
	private boolean status;
	private int		i;
	private int		j;

	public Letter(char letter, int row, int col)
	{
		this.letter = letter;
		this.status = false;
		this.i = row;
		this.j = col;
	}
	public boolean used(){return this.status;}
	public char	get(){return this.letter;}
	public void setUsed(){this.status = true;}
	public void setUnused(){this.status = false;}
	public int getI(){return this.i;}
	public int getJ(){return this.j;}
}//class Letter