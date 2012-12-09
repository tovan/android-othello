package neuberger.othello;

import java.util.ArrayList;

import junit.framework.TestCase;
import neuburger.othello.BoardOperator;
import neuburger.othello.GameBoard;
import neuburger.othello.GamePiece;
import android.graphics.Color;

public class FlipPiecesTest extends TestCase {

	private GameBoard gameBoard;

	public FlipPiecesTest() {
		this.gameBoard = new GameBoard();

	}

	public void testFlipVertical() {
		gameBoard.setUpBoard();
		gameBoard.makeMove(Color.WHITE, 4, 3);

		assertTrue(Color.WHITE == gameBoard.getPieceAt(4, 4).getColor());
		assertEquals(numPiecesOccupied(), 5);
	}

	public void testComputeEdgePieces() {
		BoardOperator oper = gameBoard.getBoardOperator();
		// mock some moves being made
		gameBoard.setPieceAt(4, 3, Color.WHITE);
		gameBoard.setPieceAt(4, 4, Color.WHITE);
		gameBoard.setPieceAt(4, 5, Color.WHITE);
		gameBoard.setPieceAt(4, 6, Color.BLACK);
		gameBoard.getBoardOperator().setMyColor(Color.BLACK);

		oper.getSurroundingPieces(gameBoard.getPieceAt(4, 2));
		GamePiece edgePiece = oper.findExistingEdgePieces(
				Color.BLACK, gameBoard.getPieceAt(4, 3));
		oper.isPossibleMove(Color.BLACK, 4, 2);

		assertNotNull(edgePiece);
		assertTrue(edgePiece.getColor() == Color.BLACK);
		assertTrue(gameBoard.computePiecesGained(gameBoard.getPieceAt(4, 2)) == 3);
	}


	public int numPiecesOccupied() {
		int numPieces = 0;
		GamePiece[][] gameBoard = this.gameBoard.getBoard();
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard[i].length; j++) {
				if (gameBoard[i][j].isOccupied()) {
					numPieces++;
				}
			}
		}
		return numPieces;
	}
	public void testGetEdgePieces(){
		gameBoard.getPieceAt(5,5).setColor(Color.WHITE);
		gameBoard.getPieceAt(5,6).setColor(Color.BLACK);
		gameBoard.getPieceAt(5,7).setColor(Color.BLACK);
		gameBoard.getPieceAt(7,4).setColor(Color.BLACK);
		gameBoard.getPieceAt(6,3).setColor(Color.BLACK);
		gameBoard.getPieceAt(5,2).setColor(Color.BLACK);
		gameBoard.getPieceAt(4,1).setColor(Color.WHITE);
		
		gameBoard.makeMove(5,8,Color.WHITE);
		assertEquals(numPiecesOccupied(), 7);
		gameBoard.getBoardOperator().setMyColor(Color.WHITE);
		ArrayList<GamePiece>surrList = this.gameBoard.getBoardOperator().getSurroundingPieces(gameBoard.getPieceAt(5, 8));
		ArrayList<GamePiece>edgeList = new ArrayList<GamePiece>();
		for(GamePiece piece : surrList){
			edgeList.add(this.gameBoard.getBoardOperator().findExistingEdgePieces(Color.WHITE, piece));
		}
		assertEquals(edgeList.size(), 2);
		System.out.println(edgeList.size());
	}
	public void mockStrangeFlipping(){
		gameBoard.getPieceAt(4,1).setColor(Color.WHITE);
		gameBoard.getPieceAt(4,2).setColor(Color.WHITE);
		gameBoard.getPieceAt(4,3).setColor(Color.WHITE);
		gameBoard.getPieceAt(4,4).setColor(Color.WHITE);
		gameBoard.getPieceAt(4,5).setColor(Color.WHITE);
		
		gameBoard.getPieceAt(5,1).setColor(Color.WHITE);
		gameBoard.getPieceAt(5,2).setColor(Color.WHITE);
		gameBoard.getPieceAt(5,3).setColor(Color.WHITE);
		gameBoard.getPieceAt(5,4).setColor(Color.WHITE);
		gameBoard.getPieceAt(5,5).setColor(Color.BLACK);
		
		gameBoard.getPieceAt(6,1).setColor(Color.WHITE);
		gameBoard.getPieceAt(6,2).setColor(Color.WHITE);
		gameBoard.getPieceAt(6,3).setColor(Color.WHITE);
		gameBoard.getPieceAt(6,4).setColor(Color.WHITE);

		gameBoard.getPieceAt(7,1).setColor(Color.WHITE);
		gameBoard.getPieceAt(7,2).setColor(Color.BLACK);
		gameBoard.getPieceAt(7,3).setColor(Color.BLACK);
		gameBoard.getPieceAt(7,4).setColor(Color.BLACK);
		gameBoard.getPieceAt(7,5).setColor(Color.BLACK);
		gameBoard.getPieceAt(7,6).setColor(Color.BLACK);
		
		gameBoard.getPieceAt(8,1).setColor(Color.WHITE);
		gameBoard.getPieceAt(8,2).setColor(Color.WHITE);
		gameBoard.getPieceAt(8,3).setColor(Color.WHITE);
		gameBoard.getPieceAt(8,4).setColor(Color.WHITE);
		gameBoard.getPieceAt(8,5).setColor(Color.WHITE);
		gameBoard.getPieceAt(8,6).setColor(Color.WHITE);
		
		boolean moveSuccessful = gameBoard.makeMove(Color.WHITE, 7,7);
		assertEquals(moveSuccessful, true);
	}
	public void testNotPreciseFlipping(){
		gameBoard.getPieceAt(4,4).setColor(Color.WHITE);
		gameBoard.getPieceAt(4,5).setColor(Color.WHITE);
		
		gameBoard.getPieceAt(5,1).setColor(Color.WHITE);
		gameBoard.getPieceAt(5,2).setColor(Color.BLACK);
		gameBoard.getPieceAt(5,3).setColor(Color.WHITE);
		gameBoard.getPieceAt(5,4).setColor(Color.WHITE);
		gameBoard.getPieceAt(5,5).setColor(Color.WHITE);
		
		gameBoard.getPieceAt(6,2).setColor(Color.BLACK);
		gameBoard.getPieceAt(6,4).setColor(Color.WHITE);
		gameBoard.getPieceAt(6,5).setColor(Color.WHITE);
		
		gameBoard.getPieceAt(7,2).setColor(Color.BLACK);
		gameBoard.getPieceAt(7,3).setColor(Color.BLACK);
		gameBoard.getPieceAt(7,4).setColor(Color.BLACK);
		gameBoard.getPieceAt(7,5).setColor(Color.BLACK);
		gameBoard.getPieceAt(7,6).setColor(Color.BLACK);
		gameBoard.getPieceAt(8,3).setColor(Color.WHITE);
		
		//white should move on 5,1 and only 5,2 should get flipped to black
		ArrayList<GamePiece>edgePiece = new ArrayList<GamePiece>();
		edgePiece.add(gameBoard.getPieceAt(5,2));
		gameBoard.flipPieces(Color.WHITE, gameBoard.getPieceAt(5,1), edgePiece);
		
//		assertEquals(gameBoard.computePiecesGained(gameBoard.getPieceAt(5,1), gameBoard.getPieceAt(5,2)), 1);
		assertEquals(gameBoard.computePiecesGained(gameBoard.getPieceAt(5,1)), 1);
	}
}