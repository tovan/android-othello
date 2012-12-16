package neuburger.othello;

import java.util.ArrayList;
import java.util.Stack;

import android.graphics.Color;

public class GameBoard {

	private GamePiece[][] board;
	private BoardOperator boardOperator;
	private Stack<GamePiece> piecesMoved;
	private GamePiece lastMove;
	private ArrayList<GamePiece>piecesFlipped;
	
	public GameBoard() {
		board = new GamePiece[9][9];
		boardOperator = new BoardOperator(board);
		piecesFlipped = new ArrayList<GamePiece>();
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
		boolean madeMove = false;
		if (boardOperator.inBoard(x, y)) {
			if (!boardOperator.isPossibleMove(color, x, y)) {
				System.out.println("sorry but that is an invalid move!");
				madeMove = true;
			} else {
				piecesFlipped = new ArrayList<GamePiece>();
				captureLocation(color, x, y);
				GamePiece nextMove = board[x][y];
				flipPieces(color, nextMove, nextMove.getEdgePieces());
				lastMove = nextMove;
				madeMove = true;
			}
		}return madeMove;
		//will be false if user touched an invalid spot
	}

	private void captureLocation(int color, int x, int y) {
		board[x][y].setColor(color);
	}

	//will be used when I allow user to flip its own pieces
	private void toggleColor(int x, int y) {
		int newColor = getOppositeColor(board[x][y].getColor());
		board[x][y].setColor(newColor);
	}

	private int getOppositeColor(Integer colorofDesiredSpot) {
		return colorofDesiredSpot == Color.WHITE? Color.BLACK: Color.WHITE;
	}

	public void makeComputerMove(int computerColor) {
		ArrayList<GamePiece> potentialMoves = boardOperator.getPossibleMoves(computerColor);
		if (potentialMoves.isEmpty()) {
			System.out.println("no moves can be made");
		} 
		else {
			GamePiece nextMove = getStrategicPiece(potentialMoves);
			System.out.println("black moved on: "+nextMove.getX()+", "+ nextMove.getY());
			lastMove = nextMove;
			piecesFlipped = new ArrayList<GamePiece>();
			captureLocation(computerColor, nextMove.getX(),nextMove.getY());
			int piecesGained = computePiecesGained(nextMove);
			this.flipPieces(computerColor, nextMove, nextMove.getEdgePieces());
			System.out.println("you have just captured " + piecesGained + " piece(s)!");
		}
	}
	
	public GamePiece getLastMove() {
		return lastMove;
	}

	public GamePiece getStrategicPiece(ArrayList<GamePiece> potentialMoves) {
		for (GamePiece piece : potentialMoves) {
			if (piece.getX() == 1 && piece.getY() == 1 || piece.getX() == 1 && piece.getY() == 8) {
				return piece;
			} else if (piece.getX() == 8 && piece.getY() == 1 || piece.getX() == 8 && piece.getY() == 8) {
				return piece;
			}
		}
		return potentialMoves.get(potentialMoves.size() - 1);
	}

	public void flipPieces(int color, GamePiece nextMove, ArrayList<GamePiece> edgePieces) {
		// nextMove --> move just taken
		// has list of edgePieces at other end of pieces to be turned over
		for(GamePiece edgePiece: edgePieces){
			System.out.println((color == Color.BLACK? "black": "white") +" has E.P. of: "+edgePiece.getX()+", "+edgePiece.getY());
			int piecesGained = computePiecesGained(nextMove, edgePiece);
			if (nextMove.getX() == edgePiece.getX()) {
				flipHorizontally(color, nextMove, piecesGained, edgePiece);
			}
			else if (nextMove.getY() == edgePiece.getY()) {
				flipVertically(color, nextMove, piecesGained, edgePiece);
			}
			else{
				flipDiagonally(color, nextMove, piecesGained, edgePiece);
			}
		}
	}
	
	private void flipHorizontally(int color, GamePiece nextMove, int piecesGained,	GamePiece edgePiece) {
		
		int totalY = Math.abs(nextMove.getY() - edgePiece.getY());
		int yPerMove = totalY / (piecesGained + 1);
		int minY = Math.min(nextMove.getY(), edgePiece.getY());
		int maxY = Math.max(nextMove.getY(), edgePiece.getY());
		int newY = minY + yPerMove;
		while ((newY != maxY)) {
			if(boardOperator.inBoard(edgePiece.getX(), newY)){
				board[edgePiece.getX()][newY].setColor(color);
				piecesFlipped.add(board[edgePiece.getX()][newY]);
				newY += yPerMove;
			}
		}
	}

	private void flipVertically(int color, GamePiece nextMove, int piecesGained,GamePiece edgePiece) {
		
		int totalX = Math.abs(nextMove.getX() - edgePiece.getX());
		int xPerMove = totalX / (piecesGained + 1);
		int minX = Math.min(nextMove.getX(), edgePiece.getX());
		int maxX = Math.max(nextMove.getX(), edgePiece.getX());
		int newX = minX + xPerMove;
		while ((newX != maxX)) {
			if(boardOperator.inBoard(newX, edgePiece.getY())){
				board[newX][edgePiece.getY()].setColor(color);
				piecesFlipped.add(board[newX][edgePiece.getY()]);
				newX += xPerMove;
				}
		}
	}

	private void flipDiagonally(int color, GamePiece nextMove, int piecesGained, GamePiece edgePiece) {
		int totalX = edgePiece.getX() - nextMove.getX();
		int xPerMove = totalX / (piecesGained + 1);
		int totalY = edgePiece.getY() - nextMove.getY();
		int yPerMove = totalY / (piecesGained + 1);
		int newX, newY;

		do {
			newY = nextMove.getY() + yPerMove;
			newX = nextMove.getX() + xPerMove;

			if (boardOperator.inBoard(newX, newY)) {
				board[newX][newY].setColor(color);
				piecesFlipped.add(board[newX][newY]);
				nextMove = board[newX][newY];
			} else {
				return;
			}
		} while (newX != edgePiece.getX() && newY != edgePiece.getY());
		//since it's a do while loop the edgePiece will get added even though it wasn't actually flipped
		piecesFlipped.remove(piecesFlipped.size() - 1);
	}

	public ArrayList<GamePiece> getPiecesFlipped() {
		return piecesFlipped;
	}

	public int computePiecesGained(GamePiece nextMove, GamePiece anEdgePiece){
		int distance = 0;
		// can be a distance of only x, y didn't change
		if (anEdgePiece.getLocation().y == nextMove.getY()) {
			distance = Math.abs(anEdgePiece.getLocation().x - nextMove.getX());
		}
		// can be a distance of only y, x didn't change
		else if (anEdgePiece.getLocation().x == nextMove.getX()) {
			distance = Math.abs(anEdgePiece.getLocation().y - nextMove.getY());
		}
		// can be diagonally apart
		else {
			//vertical and horizontal distances must be the same in a square grid
			distance = Math.abs(anEdgePiece.getLocation().y - nextMove.getY());
		}
		return distance - 1; 	// pieces captured are 1 less than distance
	}
	
	public int computePiecesGained(GamePiece piece) {
		//uses list of endPieces of received in piece
		int distance = 0;
		for (int i = 0; i < piece.getEdgePieces().size(); i++) {
			GamePiece currEdgePiece = piece.getEdgePieces().get(i);
			// can be a distance of only x, y didn't change
			if (currEdgePiece.getLocation().y == piece.getY()) {
				distance += Math.abs(currEdgePiece.getLocation().x - piece.getX());//must use += to have a cumulative sum over all edgePieces
			}
			// can be a distance of only y, x didn't change
			else if (currEdgePiece.getLocation().x == piece.getX()) {
				distance += Math.abs(currEdgePiece.getLocation().y - piece.getY());
			}
			// can be diagonally apart
			else {
				//vertical and horizontal distances must be the same in a square grid
				distance += Math.abs(currEdgePiece.getLocation().y - piece.getY());
			}
		}
		// pieces captured are 1 less than distance
		return distance - 1;
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
	
	public void removeMove() {
		// TODO Auto-generated method stub
		//really must store prev. color
		GamePiece removedPiece = piecesMoved.pop();
		int x = removedPiece.getX();
		int y = removedPiece.getY();
		board[x][y] = new GamePiece(x, y);
	}

}