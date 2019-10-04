public class BoggleDriver
{

	public static void main ( String[] args )
	{
		/****************************
		*		TESTING
		*****************************/
/*		Boggle x;
		ModifiedFixedQueue q;
		char b;
		Letter a;

		b = "z".charAt(0);
		a = new Letter( b,3,3);
		x = new Boggle( 4, 4 );
		q = new ModifiedFixedQueue(a);
///*
		x.setBoard("mapoeterdenildhc");

		x.printBoard();

		x.addNeighbors(q);
		while( q.isEmpty() == false )
		{
			System.out.print(((Letter)q.deQueue()).get() + " ");
		}

		System.out.println();
		System.out.println("al is a prefix: "+x.isPrefix("al"));
		System.out.println("al is a word: "+x.isWord("al"));

		// testing letter class
/*


		System.out.println(a.used());
		System.out.println(a.get());
		a.toggleStatus();
		System.out.println(a.used());
//*/

		//Testing search
/*
		System.out.println("Begin search of the board");
		x.search(3,3);

//*/

		//Testing game
/*
		System.out.println("Begin search of the board");
		x.playGame();

//*/

		/****************************
		*          ACTUAL GAME
		****************************/
		Boggle 	board;
		int 	col;
		String	input;
		int 	row;

		input = "epfbiwozusyakasxnbpkbxqru";
		col = 5;
		row = col;//ONLY FOR SQUARE

		board = new Boggle(row, col);
		board.setBoard(input);
//		board.printBoard();
//		System.out.println();
		board.playGame();
//		board.printStats();
	} //main
} //class