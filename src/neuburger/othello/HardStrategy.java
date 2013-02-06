package neuburger.othello;

import java.util.ArrayList;

public class HardStrategy implements Strategy{

	private ComputerPlayer computerPlayer;
	private BoardController boardController;
	
	public HardStrategy(ComputerPlayer computerPlayer){
		this.computerPlayer = computerPlayer;
		this.boardController = computerPlayer.getBoardController();
	}
	
	@Override
	public GamePiece getNextMove(ArrayList<GamePiece> potentialMoves, int computerColor) {
		int playersColor = computerPlayer.getOppositeColor(computerColor);
		GamePiece bestPiece = null;
		for (GamePiece piece : potentialMoves) {
			 //hard strategy does 4 tiers of 'predicting'
			 int potentialPiecesGained = computerPlayer.computePiecesGained(piece);
			 piece.setPiecesGained(potentialPiecesGained);
			 if (potentialMoves.size() > 0){	//check that a possible move exists
				 ArrayList<GamePiece>potentialPlayerMoves = boardController.getPossibleMoves(playersColor);
				 computerPlayer.resetPiecesGained(potentialPlayerMoves, piece);
				
				 potentialPlayerMoves = boardController.getPossibleMoves(playersColor);
				 computerPlayer.resetPiecesGained(potentialPlayerMoves, piece);
				 
				 potentialPlayerMoves = boardController.getPossibleMoves(playersColor);
				 computerPlayer.resetPiecesGained(potentialPlayerMoves, piece);
				 
				 bestPiece = computerPlayer.getPieceWithMaxGain(potentialMoves);
			 }
		}
		return bestPiece;
		
	}

}
