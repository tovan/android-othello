package com.example.neuburger_othello;

import neuburger.othello.GameBoard;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnTouchListener  {

	private BoardView boardView;
	private TranslatePointToBox translatePointToBox;
	private GameBoard gameBoard;
	private Button finishButton;
	
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finishButton = (Button) this.findViewById(R.id.finishButton);
        Display display = getWindowManager().getDefaultDisplay();
    	Point size = new Point();
    	Point smallSize = new Point();
    	Point largeSize = new Point();
    	display.getCurrentSizeRange(smallSize, largeSize);
    	display.getSize(size);
        
    	gameBoard = new GameBoard();
        boardView = new BoardView(this, gameBoard);
        
        setContentView(boardView);
//        setContentView(finishButton);
        boardView.setOnTouchListener(this);
//        boardView.setOnLongClickListener(this);
//        boardView.setOnDragListener(this);
        //must be created after drawView, to have width and height
        translatePointToBox = new TranslatePointToBox();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @SuppressLint("NewApi")
	public void pickImage( View view ) {
    	Log.d("Othello", "Button clicked!");
    	buttonView.setBackgroundColor(200);
    	buttonView.setText("Button has been clicked");

    }
*/
    public void onButtonClick(View view){
    	Log.d("Othello main", "button clicked");
    	finishButton.setText("button was clicked");
    }
	@Override
	public boolean onTouch(View drawView, MotionEvent pointPressed) {
		float xLocation = pointPressed.getX();
		float yLocation = pointPressed.getY();
		int boxWidth = drawView.getWidth()/8;
		int boxHeight = drawView.getWidth()/8;
		Point box = new Point();
		box =  translatePointToBox.translate(xLocation, yLocation, boxWidth, boxHeight);
		Log.d("onTouch", xLocation +" "+yLocation);
		Log.d("onTouch", box.y+1 +", "+(box.x+1));
		gameBoard.makeMove(Color.WHITE, box.y+1, box.x+1);
		drawView.invalidate();
		return false;
	}
	public GameBoard getGameBoard(){
		return this.gameBoard;
	}
	
	/*@Override
	public boolean onLongClick(View v) {
		Log.d("onLongClick", "testing");
		return false;
	}*/
}
