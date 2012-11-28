package com.example.neuburger_othello;
import android.graphics.Point;

public class TranslatePointToBox {

	
	public TranslatePointToBox(){
	}
	public Point translate(float touchX, float touchY, int boxWidth, int boxHeight){
		int row = (int)touchX/boxWidth;
		int column = (int)touchY/boxHeight;
		return new Point(row, column);
	}
}
