
import junit.framework.TestCase;
import neuburger.othello.BoardOperator;
import neuburger.othello.GameBoard;
import neuburger.othello.GamePiece;
import android.graphics.Color;

public class testFlipPieces extends TestCase {

	private GameBoard gameBoard;

	public testFlipPieces() {
		this.gameBoard = new GameBoard();

	}

	public void testFlipVertical() {
		gameBoard.setUpBoard();
		gameBoard.makeMove(Color.WHITE, 4, 3);

		assertTrue(Color.WHITE == gameBoard.getPieceAt(4, 4).getColor());
		assertEquals(numPiecesOccupied(), 6);
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
}