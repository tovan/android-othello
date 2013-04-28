package neuburger.othello;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;


public class MainActivity extends Activity implements OnTouchListener  {

	private BoardView boardView;
	private TranslatePointToBox translatePointToBox;
	private GameBoard gameBoard;
	private ComputerPlayer CPU;
	private Button finishButton;
	private Button hintForWhiteButton;
	private Button hintForBlackButton;
	private TextView counter;
	private int playersColor;
	private int computerColor;
	private int currColor;
	private Menu optionsMenu;
	private int newNumPlayers;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		optionsMenu = (Menu)this.findViewById(R.menu.activity_main);
		return true;
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Toast toast;
		
		switch(item.getItemId()){
		case R.id.newGame:
			onCreate(null);
			break;
		
		case R.id.onePlayer:
			if(item.isChecked()){
				item.setChecked(false);
			}
			else if (gameInProgress()){
				newNumPlayers = 1;
				stopPlayer();
				}
			else{
				gameBoard.setNumPlayers(1);
				item.setCheckable(true);
				item.setChecked(true);
			}
			break;
			
		case R.id.twoPlayers:
			if(item.isChecked()){
				item.setChecked(false);
			}
			else if (gameInProgress()){
				newNumPlayers = 2;
				stopPlayer();
			}
			else{
				gameBoard.setNumPlayers(2);
				currColor = Color.BLACK;
				item.setCheckable(true);
				item.setChecked(true);	
			}
			break;
			
		case R.id.userIsBlack:
			if (gameInProgress()){
				newNumPlayers = 1;
				stopPlayer();
			}
			else{
				playersColor = Color.BLACK;
				computerColor = Color.WHITE;
			}
			break;
		
		case R.id.easy:
			toast = Toast.makeText(getBaseContext(), "Easy Mode",  Toast.LENGTH_LONG);
			toast.show();
			gameBoard.setStrategy(ComputerPlayer.StrategyEnum.easy);
			item.setCheckable(true);
			item.setChecked(true);	
			break;
	
		case R.id.medium:
			toast = Toast.makeText(getBaseContext(),"Medium Mode", Toast.LENGTH_LONG);
			toast.show();
			gameBoard.setStrategy(ComputerPlayer.StrategyEnum.medium);
			item.setCheckable(true);
			item.setChecked(true);	
			break;
			
		case R.id.hard:
			toast = Toast.makeText(getBaseContext(),"Hard Mode", Toast.LENGTH_LONG);
			toast.show();
			gameBoard.setStrategy(ComputerPlayer.StrategyEnum.hard);
			item.setCheckable(true);
			item.setChecked(true);	
			break;
		}
		return true;
	}
	public boolean gameInProgress() {
		return gameBoard.numPiecesOfColor(Color.BLACK)>2 || gameBoard.numPiecesOfColor(Color.WHITE) >2;
	}
	public void stopPlayer(){
		AlertDialog.Builder popUpAlert  = new AlertDialog.Builder(this);

		popUpAlert.setMessage("Switching the number of players in middle of a game will restart your game. " +
				"\nAre you sure you would like to continue? Press cancel to continue the game as is");
		popUpAlert.setTitle("Reset Number of Players");
		popUpAlert.setPositiveButton("Ok",
			    new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int btnClicked) {
			        	onCreate(null);
			        	gameBoard.setNumPlayers(newNumPlayers);
						currColor = Color.BLACK;
						
			        }
			    });
		popUpAlert.setNegativeButton("Cancel", 
				new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int btnClicked) {
						//nothing happens
					}
		});
		popUpAlert.setCancelable(true);
		popUpAlert.create().show();
	}
	
	
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        finishButton = (Button) this.findViewById(R.id.finishButton);
        hintForWhiteButton = (Button) this.findViewById(R.id.hintForWhiteButton);
        hintForBlackButton = (Button) this.findViewById(R.id.hintForBlackButton);
        boardView = (BoardView)this.findViewById(R.id.boardView);
        
        gameBoard = boardView.getGameBoard();
        CPU = gameBoard.getComputerPlayer();
        
    	boardView.setOnTouchListener(this);
        translatePointToBox = new TranslatePointToBox();
        
        counter = (TextView)this.findViewById(R.id.counter);
        //by default one player should play against computer, this can be changed through menu 
        gameBoard.setNumPlayers(1);
        //by default user is white and computer is black, this can be changed through menu
        playersColor = Color.WHITE;
        computerColor = Color.BLACK;
        currColor = playersColor == Color.BLACK? Color.BLACK: Color.WHITE;	//if 2 players are playing, first move is white
    
    }

    public void onFinishButtonClick(View view){
    	Log.d("onFinishClick", "button clicked");
    	makeComputerMove(computerColor);
    	
    }
    public void onHintForWhiteButtonClick(View view){
    	provideHint(Color.WHITE);
    }
    public void onHintForBlackButtonClick(View view){
    	provideHint(Color.BLACK);
    }
    public void provideHint(int color){
    	Log.d("onHintClick", "trying to give a hint");
    	GamePiece suggestedMove = gameBoard.provideHint(color);
    	//only if a move can be made should the toast be visible
    	if(suggestedMove != null){
	    	boardView.setSuggestedMove(suggestedMove);
	    	Toast toast = Toast.makeText(getBaseContext(), ""+suggestedMove.getXLocation()+", "+
	    									suggestedMove.getYLocation(), Toast.LENGTH_LONG);
	    	toast.show();
    	}
    	boardView.invalidate();
    	
    }
    public void onUndoButtonClick(View view){
    	GamePiece[][]oldBoard = this.gameBoard.getBoardController().getPreviosBoard(); //oldBoard has pieces on it
    	this.gameBoard.setBoard(oldBoard);
    	boardView.setGameBoard(gameBoard);
    	boardView.invalidate();
    }
	public void makeComputerMove(int computerColor) {
		boolean movePossible = CPU.makeComputerMove(computerColor);
		if (movePossible == false){
			Toast toast = Toast.makeText(this, "No moves possible!", Toast.LENGTH_LONG);
			toast.show();
		}
		else{
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
	    	counter.setText("white count: "+ whiteTotal +"\nblack count: "+ blackTotal+"\nlead color: " +
	    			winingColor);
	    	counter.showContextMenu();
		}
	}
	
	public GameBoard getGameBoard() {
		return gameBoard;
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

		if(gameBoard.getNumPlayers() == 2){
			madeMove = gameBoard.makeMove(currColor, box.y+1, box.x+1);
		}
		madeMove = gameBoard.makeMove(playersColor, box.y+1, box.x+1);
		boardView.invalidate();
		if(madeMove && gameBoard.getNumPlayers() == 1 ){
			makeComputerMove(computerColor);
			boardView.invalidate();
		}
		currColor = Color.BLACK;//if 2 players are playing, switch color after each move
		return false;
	}
	
}
