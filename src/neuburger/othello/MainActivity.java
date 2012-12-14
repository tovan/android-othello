package neuburger.othello;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements OnTouchListener  {

	private BoardView boardView;
	private TranslatePointToBox translatePointToBox;
	private GameBoard gameBoard;
	private Button finishButton;
	private TextView counter;
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    Menu optionsMenu = (Menu) this.findViewById(R.id.menu);
	    inflater.inflate(R.id.menu, menu);
	    return true;
	}
	*/
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Menu optionsMenu = (Menu) this.findViewById(R.id.menu);
        finishButton = (Button) this.findViewById(R.id.finishButton);
        boardView = (BoardView)this.findViewById(R.id.boardView);
        gameBoard = boardView.getGameBoard();
        
    	boardView.setOnTouchListener(this);
        translatePointToBox = new TranslatePointToBox();
        
        counter = (TextView)this.findViewById(R.id.counter);
    }

    public void onButtonClick(View view){
    	Log.d("Othello main", "button clicked");
    	makeComputerMove();
    	
    }

	public void makeComputerMove() {
		gameBoard.makeComputerMove();
    	boardView.invalidate();
    	int blackTotal = this.gameBoard.numPiecesOfColor(Color.BLACK);
    	int whiteTotal = this.gameBoard.numPiecesOfColor(Color.WHITE);
    	String winingColor = null;
    	if(blackTotal > whiteTotal){
    		winingColor = "black";
    	}
    	else if(whiteTotal > blackTotal){
    		winingColor = "white";
    	}
    	else{
    		winingColor = "tie";
    	}
    	counter.setText("white count: "+ whiteTotal +"\nblack count: "+ blackTotal+"\nlead color: "+winingColor);
    	counter.showContextMenu();
	}
	@Override
	public boolean onTouch(View boardView, MotionEvent pointPressed) {
		float xLocation = pointPressed.getX();
		float yLocation = pointPressed.getY();
		int boxWidth = boardView.getWidth()/8;
		int boxHeight = boardView.getWidth()/8;
		Point box = new Point();
		box =  translatePointToBox.translate(xLocation, yLocation, boxWidth, boxHeight);
		Log.d("onTouch", xLocation +" "+yLocation);
		Log.d("onTouch", box.y+1 +", "+(box.x+1));
		boolean madeMove = gameBoard.makeMove(Color.WHITE, box.y+1, box.x+1);
		boardView.invalidate();
		if(madeMove){
			makeComputerMove();
			boardView.invalidate();
		}
		return false;
	}
	
}
