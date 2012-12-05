package neuburger.othello;

import neuburger.othello.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
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
        boardView.setOnTouchListener(this);
        translatePointToBox = new TranslatePointToBox();
    }

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
}
