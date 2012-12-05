package neuburger.othello;

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

	public BoardView(Context context){
		super(context);
	}
	public BoardView(Context context, AttributeSet attrs){
		super(context);
	}
	public BoardView(Context context, GameBoard gb) {
		super(context);
		this.setBackgroundColor(Color.WHITE);
		this.paint = new Paint();
		this.gameBoard = gb;
		gameBoard.setUpBoard();

	}
	@SuppressLint({ "DrawAllocation", "DrawAllocation", "DrawAllocation",
			"DrawAllocation" })
	@Override
	public void onDraw(final Canvas canvas) {
		this.widthofBox = this.getWidth() / 8;
		this.heightofBox = this.widthofBox;
		
		// draw the background
		paint.setColor(Color.rgb(0,80,0));
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		//draw the lines
		paint.setColor(Color.BLACK);
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 8; col++) {
				int startX = col * widthofBox;
				int startY = row * heightofBox;
				int endX = (col + 1) * widthofBox;
				canvas.drawLine(startX, startY, endX, startY, paint);//draws rows
				canvas.drawLine(startY, startX, startY, endX, paint);//draws columns
		}
			// draw pieces moved onto
			for (int c = 0; c < 9; c++) {
				for (int r = 0; r < 9; r++) {
					Integer color = gameBoard.getPieceAt(r, c).getColor();
					if (color != null) {
						paint.setColor(gameBoard.getPieceAt(r, c).getColor().intValue());
						canvas.drawCircle((widthofBox * c - widthofBox / 2),
								(widthofBox * r - widthofBox / 2),(widthofBox / 2), paint);
						paint.setColor(Color.BLACK);//reset color to always draw grid-lines black
						
					}
				}
			}

		}

	}
}
