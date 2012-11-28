package com.example.neuburger_othello;

import neuburger.othello.GameBoard;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
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

	// following method is called every time repaint is called from main
	// activity
	@SuppressLint({ "DrawAllocation", "DrawAllocation", "DrawAllocation",
			"DrawAllocation" })
	@Override
	public void onDraw(final Canvas canvas) {

		this.widthofBox = this.getWidth() / 8;
		this.heightofBox = this.widthofBox;
		// draw the grid
		int[] gridColors = { Color.GREEN, Color.BLUE };
		Log.d("on Draw ", "box sizes: width: " + widthofBox + " squareHeight: "
				+ heightofBox);
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				int startX = col * widthofBox;
				int startY = row * heightofBox;
				int endX = (col + 1) * widthofBox;
				int endY = (row + 1) * heightofBox;
				int colorIndex = (col + row) % 2;
				paint.setColor(gridColors[colorIndex]);
				canvas.drawRect(new Rect(startX, startY, endX, endY), paint);
				// draw pieces moved onto
				/*Integer color = gameBoard.getPieceAt(row, col).getColor();
				if (color != null) {
					paint.setColor(gameBoard.getPieceAt(row, col).getColor()
							.intValue());
					canvas.drawCircle((widthofBox * col - widthofBox / 2),
							(widthofBox * row - widthofBox / 2),
							(widthofBox / 2), paint);
				}*/
		}

			// draw pieces moved onto
			for (int c = 0; c < 9; c++) {
				for (int r = 0; r < 9; r++) {
					//get the color of the piece at row r and column c
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

		}

	}
}
