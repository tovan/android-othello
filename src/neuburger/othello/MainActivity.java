package neuburger.othello;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData.Item;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
	private int numPlayers;
	private int playersColor;
	private int computerColor;
	private int currColor;
	private Menu optionsMenu;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		optionsMenu = (Menu)this.findViewById(R.menu.activity_main);
		return true;
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		case R.id.newGame:
			GameBoard newBoard = new GameBoard();
			newBoard.setUpBoard();
			this.boardView.setGameBoard(newBoard);
			this.gameBoard = newBoard;
			boardView.invalidate();
			break;
		
		case R.id.onePlayer:
			numPlayers = 1;
			item.setCheckable(true);
			item.setChecked(true);
			break;
		case R.id.twoPlayers:
			numPlayers = 2;
			item.setCheckable(true);
			item.setChecked(true);
//			Item onePlayerItem = (Item)optionsMenu.findItem(R.id.onePlayer);
			break;
		case R.id.userIsBlack:
			numPlayers = 1;
			playersColor = Color.BLACK;
			computerColor = Color.WHITE;
		}
		return true;
	}
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finishButton = (Button) this.findViewById(R.id.finishButton);
        boardView = (BoardView)this.findViewById(R.id.boardView);
        gameBoard = boardView.getGameBoard();
        
    	boardView.setOnTouchListener(this);
        translatePointToBox = new TranslatePointToBox();
        
        counter = (TextView)this.findViewById(R.id.counter);
        //by default one player should play against computer, this can be changed through menu 
        numPlayers = 1;
        //by default user is white and computer is black, this can be changed through menu
        playersColor = Color.WHITE;
        computerColor = Color.BLACK;
        currColor = Color.WHITE;	//if 2 players are playing, first move is white
    }

    public void onButtonClick(View view){
    	Log.d("Othello main", "button clicked");
    	makeComputerMove(computerColor);
    	
    }

	public void makeComputerMove(int computerColor) {
		gameBoard.makeComputerMove(computerColor);
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
		boolean madeMove;
		Point box = new Point();
		box =  translatePointToBox.translate(xLocation, yLocation, boxWidth, boxHeight);
		Log.d("onTouch", xLocation +" "+yLocation);
		Log.d("onTouch", box.y+1 +", "+(box.x+1));
		if(numPlayers == 2){
			madeMove = gameBoard.makeMove(currColor, box.y+1, box.x+1);
		}
		madeMove = gameBoard.makeMove(playersColor, box.y+1, box.x+1);
		boardView.invalidate();
		if(madeMove && numPlayers == 1 ){
			makeComputerMove(computerColor);
			boardView.invalidate();
		}
		currColor = Color.BLACK;//if 2 players are playing, switch color after each move
		return false;
	}
	
}