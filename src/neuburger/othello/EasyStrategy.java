package neuburger.othello;

import java.util.ArrayList;
import java.util.Random;

public class EasyStrategy implements Strategy{

	private ComputerPlayer computerPlayer;
	
	public EasyStrategy(ComputerPlayer computerPlayer){
		this.computerPlayer = computerPlayer;
		
	}
	
	@Override
	public GamePiece getNextMove(ArrayList<GamePiece> potentialMoves, int computerColor) {
		GamePiece bestPiece = null;
		for (GamePiece piece : potentialMoves) {
			 if( computerPlayer.isCorner(piece) ){
				return piece;
			 }
			 else{
				 //return a random move from the list of potential moves
				 Random generator = new Random(potentialMoves.size());
				 bestPiece = potentialMoves.get(generator.nextInt());
			 }
		}
		return bestPiece;
	}

}
