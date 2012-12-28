package neuburger.othello;

import java.util.ArrayList;

public interface Strategy {
	public GamePiece getNextMove(ArrayList<GamePiece> potentialMoves, int computerColor);
}
