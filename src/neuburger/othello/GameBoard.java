package neuburger.othello;

import java.util.ArrayList;

import android.graphics.Color;

public class GameBoard {

	private GamePiece[][] board;
	private ComputerPlayer computerPlayer;
	private BoardController boardController;
	
	/**
	 * Used to paint visual cues on board 
	 */
	private GamePiece lastMove;
	private int numPlayers;

	public GameBoard() {
		board = new GamePiece[9][9];
		boardController = new BoardController(board);
		computerPlayer = new ComputerPlayer(this, board, boardController);
		
		numPlayers = 1;
	}
	public GamePiece[][] getBoard() {
		return board;
	}
	
	public void setUpBoard() {
		board[4][4] = new GamePiece(4, 4, Color.BLACK);
		board[4][5] = new GamePiece(4, 5, Color.WHITE);
		board[5][4] = new GamePiece(5, 4, Color.WHITE);
		board[5][5] = new GamePiece(5, 5, Color.BLACK);
	}

	public boolean makeMove(int color, int x, int y) {
		boolean madeMove = false;
		if (boardController.inBoard(x, y)) {
			if (!boardController.isPossibleMove(color, x, y)) {
				System.out.println("sorry but that is an invalid move!");
			} else {
				boardController.cacheBoard();
				computerPlayer.captureLocation(color, x, y);
				GamePiece nextMove = board[x][y];
				computerPlayer.flipPieces(color, nextMove, nextMove.getEdgePieces());
				setLastMove(nextMove);
				madeMove = true;
			}
		}
		return madeMove;
	}

	/**
	 * Returns the first possible move, can add AI to make it suggest the ideal move
	 * @param playersColor
	 * @return
	 */
	public GamePiece provideHint(int playersColor) {
		ArrayList<GamePiece> possibleMoves = boardController
				.getPossibleMoves(playersColor);
		return possibleMoves.get(0);
	}

	public int numPiecesOfColor(int color) {
		int numPieces = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (this.getPieceAt(i, j).isOccupied()) {
					if (color == this.getPieceAt(i, j).getColor()) {
						numPieces++;
					}
				}
			}
		}
		return numPieces;
	}
	public void setStrategy(ComputerPlayer.StrategyEnum s){
		this.computerPlayer.setStrategy(s);
	}

	public void setLastMove(GamePiece lastMove) {
		this.lastMove = lastMove;
	}

	public GamePiece getLastMove() {
		return lastMove;
	}

	public GamePiece getPieceAt(int i, int j) {
		return this.board[i][j];
	}

	public void setPieceAt(int i, int j, GamePiece gamePiece) {
		this.board[i][j] = gamePiece;
	}

	public void setPieceAt(int i, int j, int color) {
		this.board[i][j].setColor(color);
	}

	public BoardController getBoardController() {
		return boardController;
	}

	public ComputerPlayer getComputerPlayer() {
		return this.computerPlayer;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	public void setBoard(GamePiece[][] board) {
		this.board = board;
	}
	public String toString() {
		StringBuffer str = new StringBuffer();
		for (int row = 1; row < board.length; row++) {
			for (int col = 1; col < board[row].length; col++) {
				Object currentBox = board[row][col];

				if (((GamePiece) currentBox).getColor() == null) {
					str.append("-");
				}

				else if (((GamePiece) currentBox).hasColor(Color.WHITE)) {
					str.append("W");
				} else if (((GamePiece) currentBox).hasColor(Color.BLACK)) {
					str.append("B");
				}
			}
			str.append("\n");
		}
		return str.toString();
	}
}