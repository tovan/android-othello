package neuburger.othello;

import java.util.ArrayList;
import java.util.Scanner;

import android.graphics.Color;

public class PlayGame {

	public PlayGame() {
	}

		public static void main(String[] args) {
			//bug found - black moves - computer moves are going into spots that are not legit. ex; 3, 4 and 3,5 when there
			//a white in 3, 2
			GameBoard board = new GameBoard();
			board.setUpBoard();
//			FindMoves findMoves = new FindMoves(board);
			BoardOperator boardOperator = board.getBoardOperator();
			String answer;
/*
			board.makeMove(Color.BLACK, 4,4);
			board.makeMove(Color.WHITE, 4,5);
			board.makeMove(Color.WHITE, 5,4);
			board.makeMove(Color.BLACK, 5,5);
			System.out.println(board);
			*/
			do{
				Scanner input = new Scanner(System.in);
				System.out.println("what action would you like to perform? "+
						"\nEnter l to list all possible moves,\nm to make a move "+
						"\np to print the board \nand q to quite playing ");
				
				answer = input.next();
				switch (answer.charAt(0)) {
				case 'l':
				case 'L':
					System.out.println(boardOperator.getPossibleMoves(Color.WHITE));
					break;
				case 'm':
				case 'M':
					int x = input.nextInt();
					int y = input.nextInt();
					boolean madeMove = board.makeMove(Color.WHITE, x, y );
					//every time person makes move, computer should too
//					ArrayList<Point> list = boardOperator.getCurrentBoxes(Color.BLACK);
//					for (Point l: list){
//						System.out.println(l.getLocation());
//					}
//					System.out.println("sorry computer moves are not enabled now");
					if(madeMove){
						board.getCPU().makeComputerMove(Color.BLACK);
					}
					break;
				case 'p':
				case 'P':
					System.out.println(board);
					break;
				case 'q':
				case 'Q':
					System.out.println(board);
					System.exit(0);
				case 't':
					board.makeMove(Color.WHITE, 1,1 );
					board.makeMove(Color.BLACK, 1, 2);
					System.out.println(board.toString());
//					board.makeMove(Color.WHITE, 5,7 );
//					board.makeMove(Color.BLACK, 2,2 );
//					board.makeMove(Color.BLACK, 2,3 );
//					board.makeMove(Color.BLACK, 4,7);
//
//					board.makeMove(Color.BLACK, 3,3);
					System.out.println("current locations:");
					ArrayList<GamePiece>possibleMoves = board.getBoardOperator().getPossibleMoves(Color.WHITE);
					for(GamePiece p: possibleMoves){
						System.out.println(p.getXLocation()+", "+p.getYLocation());
					}
					break;
				default:
					break;
				}
			}while(!(answer.charAt(0) =='q'));
			
	}	
}