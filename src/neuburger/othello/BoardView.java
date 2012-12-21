package neuburger.othello;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint({ "DrawAllocation", "DrawAllocation", "DrawAllocation",
		"DrawAllocation" })
public class BoardView extends View {
	private Paint paint;
	private int widthofBox;
	private int heightofBox;
	private GameBoard gameBoard;
	private GamePiece suggestedMove;

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setBackgroundColor(Color.WHITE);
		this.paint = new Paint();
		this.gameBoard = new GameBoard();
		gameBoard.setUpBoard();

	}

	@SuppressLint({ "DrawAllocation", "DrawAllocation", "DrawAllocation",
			"DrawAllocation" })
	@Override
	public void onDraw(final Canvas canvas) {
		this.widthofBox = this.getWidth() / 8;
		this.heightofBox = this.widthofBox;

		this.paint = new Paint();// why not called from constructor?

		// draw the background
		paint.setColor(Color.rgb(0, 80, 0));
		canvas.drawRect(0, 0, getWidth(), getWidth(), paint);
		// draw the lines
		paint.setColor(Color.BLACK);
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 8; col++) {
				int startX = col * widthofBox;
				int startY = row * heightofBox;
				int endX = (col + 1) * widthofBox;
				canvas.drawLine(startX, startY, endX, startY, paint);// draws
																		// rows
				canvas.drawLine(startY, startX, startY, endX, paint);// draws
																		// columns
				if (suggestedMove != null) {
					paint.setColor(Color.LTGRAY);
					float startXLoc = (suggestedMove.getYLocation()-1) * widthofBox;
					float startYLoc = (suggestedMove.getXLocation()-1)* heightofBox;
					canvas.drawRect(startXLoc, startYLoc, startXLoc + widthofBox, startYLoc + heightofBox, paint);
					paint.setColor(Color.BLACK);
				}
				//reset sugggestedMove so that it is not redrawn until user requests it again
				suggestedMove = null;
			}
			// draw pieces moved onto
			for (int c = 0; c < 9; c++) {
				for (int r = 0; r < 9; r++) {
					Integer color = gameBoard.getPieceAt(r, c).getColor();
					if (color != null) {
						paint.setColor(gameBoard.getPieceAt(r, c).getColor()
								.intValue());
						canvas.drawCircle((widthofBox * c - widthofBox / 2),
								(widthofBox * r - widthofBox / 2),
								(widthofBox / 2), paint);

					}
				}
			}
			//draw visual cues of last move
			GamePiece lastMove = gameBoard.getLastMove();
			if(lastMove != null){
				paint.setColor(Color.RED);
				canvas.drawCircle(widthofBox * lastMove.getYLocation() - widthofBox / 2,
						widthofBox * lastMove.getXLocation() - widthofBox / 2,
						(widthofBox / 2), paint);
				int color = gameBoard.getPieceAt(lastMove.getXLocation(), lastMove.getYLocation()).getColor();
				paint.setColor(color);
				canvas.drawCircle(widthofBox * lastMove.getYLocation() - widthofBox / 2,
						widthofBox * lastMove.getXLocation() - widthofBox / 2,
						((widthofBox / 2)- 8), paint);
			}
			ArrayList<GamePiece>piecesFlipped = gameBoard.getCPU().getPiecesFlipped();
			for(GamePiece flip: piecesFlipped){
				paint.setColor(Color.YELLOW);
				canvas.drawCircle(widthofBox * flip.getYLocation() - widthofBox / 2,
						widthofBox * flip.getXLocation() - widthofBox / 2,
						(widthofBox / 2), paint);

				int color = gameBoard.getPieceAt(flip.getXLocation(), flip.getYLocation()).getColor();
				paint.setColor(color);
				canvas.drawCircle(widthofBox * flip.getYLocation() - widthofBox / 2,
						widthofBox * flip.getXLocation() - widthofBox / 2,
						((widthofBox / 2)- 8), paint);
			}
			
			
			//reset paint to draw next board lines black
			paint.setColor(Color.BLACK);
		}

	}

	public GameBoard getGameBoard() {
		return this.gameBoard;
	}
	public void setGameBoard(GameBoard newBoard){
		this.gameBoard = newBoard;
	}

	public void setSuggestedMove(GamePiece suggestedMove) {
		this.suggestedMove = suggestedMove;
	}
}
