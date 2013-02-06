package neuberger.othello;

import java.util.ArrayList;

import junit.framework.TestCase;
import neuburger.othello.BoardController;
import neuburger.othello.ComputerPlayer;
import neuburger.othello.GameBoard;
import neuburger.othello.GamePiece;
import android.graphics.Color;

public class FlipPiecesTest extends TestCase {

	private GameBoard gameBoard;
	private ComputerPlayer CPU;
	
	public FlipPiecesTest() {
		this.gameBoard = new GameBoard();
		this.CPU = gameBoard.getCPU();
	}

	public void testFlipVertical() {
		gameBoard.setUpBoard();
		gameBoard.makeMove(Color.WHITE, 4, 3);

		assertTrue(Color.WHITE == gameBoard.getPieceAt(4, 4).getColor());
		assertEquals(numPiecesOccupied(), 5);
	}

	public void testComputeEdgePieces() {
		BoardController oper = gameBoard.getBoardController();
		// mock some moves being made
		gameBoard.setPieceAt(4, 3, Color.WHITE);
		gameBoard.setPieceAt(4, 4, Color.WHITE);
		gameBoard.setPieceAt(4, 5, Color.WHITE);
		gameBoard.setPieceAt(4, 6, Color.BLACK);
		gameBoard.getBoardController().setMyColor(Color.BLACK);

		oper.getSurroundingPieces(gameBoard.getPieceAt(4, 2));
		GamePiece edgePiece = oper.findExistingEdgePieces(
				Color.BLACK, gameBoard.getPieceAt(4, 3));
		oper.isPossibleMove(Color.BLACK, 4, 2);

		assertNotNull(edgePiece);
		assertTrue(edgePiece.getColor() == Color.BLACK);
		assertTrue(CPU.computePiecesGained(gameBoard.getPieceAt(4, 2)) == 3);
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
		
		gameBoard.makeMove(Color.WHITE, 5, 8);
		assertEquals(numPiecesOccupied(), 8);
		
		ArrayList<GamePiece>edgeList = gameBoard.getPieceAt(5, 8).getEdgePieces();
		assertEquals(edgeList.size(), 1);
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
	
}