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
		 //return a random move from the list of potential moves
		 if (potentialMoves.size() > 0){
			 Random generator = new Random();
			 bestPiece = potentialMoves.get(generator.nextInt(potentialMoves.size()));
		}
		return bestPiece;
	}

}
