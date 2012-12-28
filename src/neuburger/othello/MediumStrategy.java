package neuburger.othello;

import java.util.ArrayList;

public class MediumStrategy implements Strategy{

	private ComputerPlayer computerPlayer;
	
	public MediumStrategy(ComputerPlayer computerPlayer){
		this.computerPlayer = computerPlayer;
	}
	
	@Override
	public GamePiece getNextMove(ArrayList<GamePiece> potentialMoves, int computerColor) {
		int playersColor = computerPlayer.getOppositeColor(computerColor);
		GamePiece bestPiece = null;
		for (GamePiece piece : potentialMoves) {
			 if( computerPlayer.isCorner(piece) ){
				 bestPiece = piece;
			 }
			 else{
				 //computes 2 tiers of 'predicting'
				 int potentialPiecesGained = computerPlayer.computePiecesGained(piece);
				 piece.setPiecesGained(potentialPiecesGained);
				 ArrayList<GamePiece>potentialPlayerMoves = computerPlayer.getBoardOperator().getPossibleMoves(playersColor);
				 computerPlayer.resetPiecesGained(potentialPlayerMoves, piece);
				 bestPiece = computerPlayer.getPieceWithMaxGain(potentialMoves);
			 }
		}
		return bestPiece;
		
	}

}
