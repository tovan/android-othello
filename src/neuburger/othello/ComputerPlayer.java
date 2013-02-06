package neuburger.othello;

import java.util.ArrayList;
import android.graphics.Color;

public class ComputerPlayer {
	
	private GameBoard gameBoard;
	private GamePiece[][] board;
	private BoardController boardController;
	private ArrayList<GamePiece>piecesFlipped;
	private GamePiece lastMove;
	private Strategy strategy;
	public enum StrategyEnum {easy, medium, hard};
	
	public ComputerPlayer(GameBoard gb, GamePiece[][] b, BoardController oper){
		piecesFlipped = new ArrayList<GamePiece>();
		this.board = b;
		this.boardController = oper;
		this.gameBoard = gb;
		this.strategy = new HardStrategy(this);
	}
	public void setStrategy(StrategyEnum s){
		switch(s){
		case easy:
			strategy = new EasyStrategy(this);
			break;
		case medium:
			strategy = new MediumStrategy(this);
			break;
		case hard:
			strategy = new HardStrategy(this);
			break;
		}
	}
	public boolean makeComputerMove(int computerColor) {
		ArrayList<GamePiece> potentialMoves = boardController.getPossibleMoves(computerColor);
		if (potentialMoves.isEmpty()) {
			System.out.println("no moves can be made");
		} 
		else {
			GamePiece nextMove = strategy.getNextMove(potentialMoves, computerColor);
			this.gameBoard.setLastMove(nextMove);
			piecesFlipped = new ArrayList<GamePiece>();
			
			if (nextMove != null){	//check that there is a possible move 
				boardController.cacheBoard();
				captureLocation(computerColor, nextMove.getXLocation(),nextMove.getYLocation());
				this.flipPieces(computerColor, nextMove, nextMove.getEdgePieces());
				return true;
			}
		}
		return false;
	}

	protected void captureLocation(int color, int x, int y) {
		board[x][y].setColor(color);
	}
	
	protected void resetPiecesGained(ArrayList<GamePiece> potentialMoves, GamePiece originalPiece){
		
		for(GamePiece piece: potentialMoves){
			 int potentialPiecesGained = computePiecesGained(piece);
			 originalPiece.switchPiecesGained(potentialPiecesGained);
		}
	}
	protected GamePiece getPieceWithMaxGain(ArrayList<GamePiece> potentialMoves){
		int maxPiecesGained = computePiecesGained(potentialMoves.get(0));	//set max to gain of first piece
		GamePiece pieceWithMaxGain =  potentialMoves.get(0);;
		for(int ctr = 1; ctr < potentialMoves.size(); ctr++){
			int potentialPiecesGained = computePiecesGained(potentialMoves.get(ctr));
			if(potentialPiecesGained > maxPiecesGained){
				maxPiecesGained =  potentialPiecesGained;
				pieceWithMaxGain = potentialMoves.get(ctr);
			}
		}
		return pieceWithMaxGain;
	}
	public void flipPieces(int color, GamePiece nextMove, ArrayList<GamePiece> edgePieces) {
		// nextMove --> move just taken, has list of edgePieces at other end of pieces to be turned over
		for(GamePiece edgePiece: edgePieces){
			if (nextMove.getXLocation() == edgePiece.getXLocation()) {
				flipHorizontally(color, nextMove, edgePiece);
			}
			else if (nextMove.getYLocation() == edgePiece.getYLocation()) {
				flipVertically(color, nextMove,  edgePiece);
			}
			else{
				flipDiagonally(color, nextMove, edgePiece);
			}
		}
	}
	
	private void flipHorizontally(int color, GamePiece nextMove, GamePiece edgePiece) {
		int piecesGained = Math.abs(edgePiece.getYLocation() - nextMove.getYLocation());	
		int totalY = Math.abs(nextMove.getYLocation() - edgePiece.getYLocation());
		int yPerMove = totalY / piecesGained;

		int minY = Math.min(nextMove.getYLocation(), edgePiece.getYLocation());
		int maxY = Math.max(nextMove.getYLocation(), edgePiece.getYLocation());
		int newY = minY + yPerMove;
		while ((newY != maxY)) {
			if(boardController.inBoard(edgePiece.getXLocation(), newY)){
				board[edgePiece.getXLocation()][newY].setColor(color);
				piecesFlipped.add(board[edgePiece.getXLocation()][newY]);
				newY += yPerMove;
			}
		}
	}

	private void flipVertically(int color, GamePiece nextMove, GamePiece edgePiece) {
		int piecesGained = Math.abs(edgePiece.getXLocation() - nextMove.getXLocation());
		int totalX = Math.abs(nextMove.getXLocation() - edgePiece.getXLocation());
		int xPerMove = totalX / piecesGained ;

		int minX = Math.min(nextMove.getXLocation(), edgePiece.getXLocation());
		int maxX = Math.max(nextMove.getXLocation(), edgePiece.getXLocation());
		int newX = minX + xPerMove;
		while ((newX != maxX)) {
			if(boardController.inBoard(newX, edgePiece.getYLocation())){
				board[newX][edgePiece.getYLocation()].setColor(color);
				piecesFlipped.add(board[newX][edgePiece.getYLocation()]);
				newX += xPerMove;
				}
		}
	}

	private void flipDiagonally(int color, GamePiece nextMove, GamePiece edgePiece) {
		int piecesGained = Math.abs(edgePiece.getYLocation() - nextMove.getYLocation());
		int totalX = edgePiece.getXLocation() - nextMove.getXLocation();
		int xPerMove = totalX / (piecesGained);
		int totalY = edgePiece.getYLocation() - nextMove.getYLocation();
		int yPerMove = totalY / (piecesGained);

		int newX, newY;
		do {
			newY = nextMove.getYLocation() + yPerMove;
			newX = nextMove.getXLocation() + xPerMove;

			if (boardController.inBoard(newX, newY)) {
				board[newX][newY].setColor(color);
				piecesFlipped.add(board[newX][newY]);
				nextMove = board[newX][newY];
			} else {
				return;
			}
		} while (newX != edgePiece.getXLocation() && newY != edgePiece.getYLocation());
		//since it's a do while loop the edgePiece will get added even though it wasn't actually flipped
		piecesFlipped.remove(piecesFlipped.size() - 1);
	}

	public ArrayList<GamePiece> getPiecesFlipped() {
		return piecesFlipped;
	}
	public int computePiecesGained(GamePiece piece) {
		int distance = 0;
		for (int i = 0; i < piece.getEdgePieces().size(); i++) {
			GamePiece currEdgePiece = piece.getEdgePieces().get(i);
			// vertical change
			if (currEdgePiece.getYLocation() == piece.getYLocation()) {
				distance += ((Math.abs(currEdgePiece.getXLocation() - piece.getXLocation()))-1);
			}
			// horizontal or diagonal change
			// in diagonal change vertical and horizontal distances must be the same in a square grid
			else{
				distance += ((Math.abs(currEdgePiece.getYLocation() - piece.getYLocation()))-1);
			}
		}
		return distance;  
	}
	
	
	protected boolean isCorner(GamePiece piece){
		if(piece.getXLocation() == 1 && piece.getYLocation() == 1 || piece.getXLocation() == 8 && piece.getYLocation() == 8){
			return true;
		}
		if(piece.getXLocation() == 1 && piece.getYLocation() == 8 || piece.getXLocation() == 8 && piece.getYLocation() == 1){
			return true;
		}
		return false;
	}
	public boolean isEdge(GamePiece piece){
		if(piece.getXLocation() == 1 || piece.getXLocation() == 8 ||
				piece.getYLocation() == 1 || piece.getYLocation() == 8 ){
			return true;
		}
		return false;
	}
	protected int getOppositeColor(Integer colorofDesiredSpot) {
		return colorofDesiredSpot == Color.WHITE? Color.BLACK: Color.WHITE;
	}

	public GamePiece getLastMove() {
		return lastMove;
	}

	public BoardController getBoardController() {
		return boardController;
	}
	
}
