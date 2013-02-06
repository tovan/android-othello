package neuburger.othello;

import java.util.ArrayList;

public class MediumStrategy implements Strategy {

	private ComputerPlayer computerPlayer;
	private BoardController boardController;
	
	public MediumStrategy(ComputerPlayer computerPlayer) {
		this.computerPlayer = computerPlayer;
		this.boardController = computerPlayer.getBoardController();
	}

	@Override
	public GamePiece getNextMove(ArrayList<GamePiece> potentialMoves,
			int computerColor) {
		int playersColor = computerPlayer.getOppositeColor(computerColor);
		GamePiece bestPiece = null;
		for (GamePiece piece : potentialMoves) {
			// computes 2 tiers of 'predicting'
			int potentialPiecesGained = computerPlayer.computePiecesGained(piece);
			piece.setPiecesGained(potentialPiecesGained);
			ArrayList<GamePiece> potentialPlayerMoves = boardController.getPossibleMoves(playersColor);
			computerPlayer.resetPiecesGained(potentialPlayerMoves, piece);
			if (potentialMoves.size() > 0) { //check that a possible move exists
				bestPiece = computerPlayer.getPieceWithMaxGain(potentialMoves);
			}
		}
		return bestPiece;

	}

}
