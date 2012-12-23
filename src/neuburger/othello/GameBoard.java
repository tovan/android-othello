package neuburger.othello;

import java.util.ArrayList;
import java.util.Stack;

import android.graphics.Color;

public class GameBoard {

	private GamePiece[][] board;
	private CPU CPU;
	private BoardOperator boardOperator;
	private Stack<GamePiece> piecesMoved;
	private GamePiece lastMove;
	private GameBoard previousBoard;
	private int numPlayers;
	
	public GameBoard() {
		board = new GamePiece[9][9];
		boardOperator = new BoardOperator(board);
		CPU = new CPU(this, board, boardOperator);
		
		numPlayers = 1;
	}

	public GamePiece[][] getBoard() {
		return board;
	}

	public void setUpBoard() {
		board[4][4] = new GamePiece(4, 4, Color.BLACK);
		board[4][5] = new GamePiece(4, 5, Color.WHITE);
		board[5][4] = new GamePiece(5, 4, Color.WHITE);
		board[5][5] = new GamePiece(5, 5, Color.BLACK);

	}

	public boolean makeMove(int color, int x, int y) {
		cacheBoard();	//store current state to allow undo move
		boolean madeMove = false;
		if (boardOperator.inBoard(x, y)) {
			if (!boardOperator.isPossibleMove(color, x, y)) {
				System.out.println("sorry but that is an invalid move!");
			} else {
				CPU.captureLocation(color, x, y);
				GamePiece nextMove = board[x][y];
				CPU.flipPieces(color, nextMove, nextMove.getEdgePieces());
				setLastMove(nextMove);
				madeMove = true;
			}
		}return madeMove;
	}

	//will be used when I allow user to flip its own pieces
	private void toggleColor(int x, int y) {
		int newColor = getOppositeColor(board[x][y].getColor());
		board[x][y].setColor(newColor);
	}

	private int getOppositeColor(Integer colorofDesiredSpot) {
		return colorofDesiredSpot == Color.WHITE? Color.BLACK: Color.WHITE;
	}

	public GamePiece provideHint(int playersColor) {
		ArrayList<GamePiece> possibleMoves = boardOperator.getPossibleMoves(playersColor);
		//for now it returns the first possible move, must add AI to make it the ideal suggested move
		return possibleMoves.get(0);
	}
	public int numPiecesOfColor(int color) {
		int numPieces = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if(this.getPieceAt(i, j).isOccupied()){
					if (color == this.getPieceAt(i, j).getColor()) {
						numPieces++;
					}
				}
			}
		}
		return numPieces;
	}
	public void cacheBoard(){
		this.previousBoard = this;
	}
	public GameBoard getPreviousBoard(){
		return previousBoard;
	}
	
	public void setLastMove(GamePiece lastMove) {
		this.lastMove = lastMove;
	}

	public GamePiece getLastMove() {
		return lastMove;
	}
	public GamePiece getPieceAt(int i, int j) {
		return this.board[i][j];
	}
	public void setPieceAt(int i, int j, GamePiece gamePiece){
		this.board[i][j] = gamePiece;
	}
	public void setPieceAt(int i, int j, int color){
		this.board[i][j].setColor(color);
	}
	public BoardOperator getBoardOperator() {
		return boardOperator;
	}
	public CPU getCPU(){
		return this.CPU;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public void removeMove() {
		// TODO Auto-generated method stub
		//really must store prev. color
		GamePiece removedPiece = piecesMoved.pop();
		int x = removedPiece.getXLocation();
		int y = removedPiece.getYLocation();
		board[x][y] = new GamePiece(x, y);
	}
	public String toString() {
		StringBuffer str = new StringBuffer();
		for (int row = 1; row < board.length; row++) {
			for (int col = 1; col < board[row].length; col++) {
				Object currentBox = board[row][col];

				if (((GamePiece) currentBox).getColor() == null) {
					str.append("-");
				}

				else if (((GamePiece) currentBox).hasColor(Color.WHITE)) {
					str.append("W");
				} else if (((GamePiece) currentBox).hasColor(Color.BLACK)) {
					str.append("B");
				}
			}
			str.append("\n");
		}
		return str.toString();
	}
}