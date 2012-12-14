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
			GamePiece lastMove = gameBoard.getLastMove();
			if(lastMove != null){
				paint.setColor(Color.RED);
				canvas.drawCircle(widthofBox * lastMove.getY() - widthofBox / 2,
						widthofBox * lastMove.getX() - widthofBox / 2,
						(widthofBox / 2), paint);
				int color = gameBoard.getPieceAt(lastMove.getX(), lastMove.getY()).getColor();
				paint.setColor(color);
				canvas.drawCircle(widthofBox * lastMove.getY() - widthofBox / 2,
						widthofBox * lastMove.getX() - widthofBox / 2,
						((widthofBox / 2)- 8), paint);
			}
			ArrayList<GamePiece>piecesFlipped = gameBoard.getPiecesFlipped();
			for(GamePiece flip: piecesFlipped){
				paint.setColor(Color.YELLOW);
				canvas.drawCircle(widthofBox * flip.getY() - widthofBox / 2,
						widthofBox * flip.getX() - widthofBox / 2,
						(widthofBox / 2), paint);

				int color = gameBoard.getPieceAt(flip.getX(), flip.getY()).getColor();
				paint.setColor(color);
				canvas.drawCircle(widthofBox * flip.getY() - widthofBox / 2,
						widthofBox * flip.getX() - widthofBox / 2,
						((widthofBox / 2)- 8), paint);
			}
			
			
			//reset paint to draw next board lines black
			paint.setColor(Color.BLACK);
		}

	}

	public GameBoard getGameBoard() {
		return this.gameBoard;
	}
}
