import java.io.*;
import java.util.ArrayList;
public class Boggle
{
/*
This class holds the implementation Boggle
Other class you may want to look at:
Letter
StackOfQueues
ModifiedFixedQueue


Instance Variables:
	board
		2D array of Letters
	row
		integer holding the number of rows
	col
		integer holding the number of columns
	dictionary
		ArrayList that holds all our valid words

Constructor:
	Boggle(int row, int col)
		creates a Boggle object
Methods:
	getBoard()
		Private accessor that returns the board
	getRow()
		Private accessor that returns this.row
	getCol()
		Private accessor that returns this.col
	setBoard(String board)
		Mutator that sets the board. Throws error if string length
		not equal to rows*cols

	playGame()
		Void method that will play the game
	search(int i, int j)
		Finds all the words in the board starting at (i,j)
	addNeighbor(ModifiedFixedQueue queue)
		this will add the neighbors of around the letter at (i,j) into the queue
	isPrefix(String string)
		boolean that check to see if the string is a prefix
	isWord(String string)
		boolean that check to see if the string is a word
*/
	private Letter[][] board;
	private int rows;
	private int cols;
	private ArrayList<String> dictionary;
//STAT	private int found;
//STAT	private int points;

	public Boggle(int row, int col)
	{
		File file;
		ArrayList<String> hold;
		BufferedReader input;
		String record;

		this.board = new Letter[row][col];
		this.rows = row;
		this.cols = col;
//STAT		this.found = 0;
//STAT		this.points = 0;
		//Create the dictionary
		hold = new ArrayList<String>();
		try
		{
			//file = new File("E:/CISC 231/BoggleBot-master/BranTran StackOfQueues/dictionary.txt");
			file = new File("word-dictionary.txt");
			input = new BufferedReader( new FileReader(file));
			record = input.readLine();
		}//try
		catch(IOException e) { throw new IllegalArgumentException("isWord: IOException caught on file priming"); }

		while( record != null )
		{
			hold.add(record);
			try
			{
				record = input.readLine();
			}//try
			catch(IOException e) { throw new IllegalArgumentException("isWord: IOException caught on file reading"); }
		}//while, puts our dictionary in an arraylist
		try
		{
			input.close();
		}//try
		catch(IOException e) { throw new IllegalArgumentException("isWord: IOException caught on file closing"); }
		this.dictionary = hold;
	}//Boggle()
	private Letter[][] getBoard(){return this.board;}
	private int getRow(){return this.rows;}
	private int getCol(){return this.cols;}
	private ArrayList<String> getDictionary(){return this.dictionary;}

/*************STATS*******************
	private void reset()
	{
		this.found = 0;
		this.points = 0;
	}
	private void addPoints(int points){this.points = this.points+points;}
	private void addFound(){this.found = this.found+1;}
//************************************/

	public void setBoard(String board)
	{
		if(board.length() != getRow()*getCol())
		{
			throw new IllegalArgumentException
			(getClass().getName()+"setBoard: input string "+board+" does not match required length: "+getRow()*getCol());
		}
		for(int i = 0; i < getRow();i++)
		{
			for(int j = 0; j < getCol();j++)
			{
				this.board[i][j] = new Letter(board.charAt(j+(getCol()*i)),i,j);//Puts string in row major order
			}
		}
	}

	public void playGame()
	{
//STAT		reset();//Reset the board before playing a game
		for(int i = getRow()-1; i >= 0; i--)
		{
			for(int j = getCol()-1; j >= 0; j--)
			{
//				System.out.println("Searching coordinates: "+i+", "+j);
				search(i,j);
			}
		}
	}

	public void search(int i, int j)
	{
		Letter[][]			board;
		boolean				hasNeighbor;
		ModifiedFixedQueue 	hold;
		ModifiedFixedQueue  next;
		Letter				nextLetter;
		StackOfQueues		stack;

		board = getBoard();
		stack = new StackOfQueues(getRow()*getCol());
		hold = new ModifiedFixedQueue((board[i][j]));

		//Priming the stack
		board[i][j].setUsed();
		addNeighbors(hold);
		stack.push(hold);
//		System.out.println("Start the stack with: "+stack.get());

		//Going through the stack
		while(!stack.isEmpty())
		{
			hold = stack.pop();
//			hold.neighbors(); COMMENT THIS OUT
			hasNeighbor = false;
			nextLetter = null;
			while(!hasNeighbor && !hold.isEmpty())
			{
				nextLetter = (Letter)hold.deQueue();
				if(nextLetter.used())//If what we are looking at has already been used
				{
//					System.out.println("We have a used neighbor: "+ nextLetter.get());
					if(hold.isEmpty())//If the last thing was something used
					{
						nextLetter = null;
					}
				}
				else//We have a good neighbor
				{
					hasNeighbor = true;
				}
			}
			if(hasNeighbor)//If we have another letter to add
			{
//				System.out.print("Initial stack size: "+stack.size());
				stack.push(hold);//bring the queue back
				nextLetter.setUsed();//make the letter used
				next = new ModifiedFixedQueue(nextLetter);
				addNeighbors(next);
				stack.push(next);//Push new queue in stack
//				System.out.println(", Size of stack after two pushes: "+stack.size());
				//Check the stack
//				System.out.println("Our stack has: "+stack.get());
				if(isPrefix(stack.get()))//It is a prefix
				{
//					System.out.println(", We found a prefix!");
					if(isWord(stack.get()))//Adding the neighbor produces a word
					{
						//SEND THE WORD
//						System.out.println(", We found a word!");
						System.out.println(stack.get());
//STAT					addFound();
//STAT					addPoints(getPoints(stack.get()));
					}
				}
				else//NOT a prefix
				{
//					System.out.println(", This is not a prefix");
					hold = stack.pop();//Pop the neighbor stack
					((Letter)hold.getFirst()).setUnused();//Toggle what was previously used
				}
			}
			else//We do not have a neighbor
			{
//				System.out.println("Queue is empty, remove");
				((Letter)hold.getFirst()).setUnused();//Toggle what was previously used
			}
		}//while stack not empty
	}//search

	public void addNeighbors(ModifiedFixedQueue queue)
	{
		int i;
		int j;

		i = ((Letter)queue.getFirst()).getI();
		j = ((Letter)queue.getFirst()).getJ();
		if(i == 0 && j == 0)//Upper left
		{
			queue.enQueue(getBoard()[i][j+1]);
			queue.enQueue(getBoard()[i+1][j]);
			queue.enQueue(getBoard()[i+1][j+1]);
		}
		else if(i == 0 && j == getCol()-1)//Upper right
		{
			queue.enQueue(getBoard()[i][j-1]);
			queue.enQueue(getBoard()[i+1][j]);
			queue.enQueue(getBoard()[i+1][j-1]);
		}
		else if(i == getRow()-1 && j == 0)//Lower left
		{
			queue.enQueue(getBoard()[i][j+1]);
			queue.enQueue(getBoard()[i-1][j]);
			queue.enQueue(getBoard()[i-1][j+1]);
		}
		else if(i == getRow()-1 && j == getCol()-1)//Lower right
		{
			queue.enQueue(getBoard()[i][j-1]);
			queue.enQueue(getBoard()[i-1][j]);
			queue.enQueue(getBoard()[i-1][j-1]);
		}
		else if(i == 0 && j > 0 && j < getCol()-1)//Upper Edge
		{
			queue.enQueue(getBoard()[i][j+1]);
			queue.enQueue(getBoard()[i][j-1]);
			queue.enQueue(getBoard()[i+1][j]);
			queue.enQueue(getBoard()[i+1][j+1]);
			queue.enQueue(getBoard()[i+1][j-1]);
		}
		else if(i == getRow()-1 && j > 0 && j < getCol()-1)//Lower Edge
		{
			queue.enQueue(getBoard()[i][j+1]);
			queue.enQueue(getBoard()[i][j-1]);
			queue.enQueue(getBoard()[i-1][j]);
			queue.enQueue(getBoard()[i-1][j+1]);
			queue.enQueue(getBoard()[i-1][j-1]);
		}
		else if(j == 0 && i > 0 && i < getRow()-1)//Right Edge
		{
			queue.enQueue(getBoard()[i+1][j]);
			queue.enQueue(getBoard()[i-1][j]);
			queue.enQueue(getBoard()[i][j+1]);
			queue.enQueue(getBoard()[i+1][j+1]);
			queue.enQueue(getBoard()[i-1][j+1]);
		}
		else if(j == getCol()-1 && i > 0 && i < getRow()-1)//Left Edge
		{
			queue.enQueue(getBoard()[i+1][j]);
			queue.enQueue(getBoard()[i-1][j]);
			queue.enQueue(getBoard()[i][j-1]);
			queue.enQueue(getBoard()[i+1][j-1]);
			queue.enQueue(getBoard()[i-1][j-1]);
		}
		else if(j > 0 && j < getCol()-1 && i > 0 && i < getCol()-1)//Inside piece
		{
			queue.enQueue(getBoard()[i-1][j-1]);
			queue.enQueue(getBoard()[i][j-1]);
			queue.enQueue(getBoard()[i+1][j-1]);
			queue.enQueue(getBoard()[i-1][j]);
			queue.enQueue(getBoard()[i+1][j]);
			queue.enQueue(getBoard()[i-1][j+1]);
			queue.enQueue(getBoard()[i][j+1]);
			queue.enQueue(getBoard()[i+1][j+1]);
		}
	}//addNeighbors
	public boolean isPrefix(String string)
	{
		ArrayList<String> 	hold;
		int 				i;
		boolean 			prefixFound;

		hold = getDictionary();
		prefixFound = false;
		i = 0;

		while( i < hold.size() && prefixFound == false )
		{
			if( hold.get(i).indexOf(string) == 0 )// This sees if string is at the front of dictionary word
			{
//				System.out.println("Dictionary word: "+ hold.get(i)+", prefix: "+string);
				prefixFound = true;
			}//if it is a prefix
			else
			{
				i = i + 1;
			}//if it is not
		}//while, goes through our dictionary to see if string is a prefix

		return prefixFound;
	}
	public boolean isWord(String string)
	{
		ArrayList<String> 	hold;
		int		i;
		boolean wordFound;

		hold = getDictionary();
		wordFound = false;
		i = 0;
		while( i < hold.size() && wordFound == false && string.length() >= 3)
		{
			if( hold.get(i).compareTo(string) == 0 )
			{
//				System.out.println("Dictionary word: "+ hold.get(i)+", word: "+string);
				wordFound = true;
			}//if it is a word
			else
			{
				i = i + 1;
			}//if it is not
		}//while, goes through our dictionary to see if string is a valid word

		return wordFound;
	}
	public void printBoard()
	{
		for(int i = 0; i < getRow();i++)
		{
			for(int j = 0; j < getCol();j++)
			{
				System.out.print(getBoard()[i][j].get()+"");
			}
			System.out.println();
		}
	}
/****************	STATS  *****************
	private int getPoints(String word)
	{
		int length;
		if(word.length() > 8)
		{
			length = 8;
		}
		else
		{
			length = word.length();
		}
		return length - 2;
	}

	public void printStats()
	{
		System.out.println("We found "+this.found+" words.");
		System.out.println("We have a total of "+this.points+" points.");
	}
//****************************************/
}//class Boggle