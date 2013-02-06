package neuburger.othello;

import java.util.ArrayList;
import android.graphics.Point;

public class GamePiece {
	private int xLocation;
	private int yLocation;
	private Integer color;
	private Point direction;

	/**
	 * pieces in other color immediately near a piece
	 */
	private ArrayList<GamePiece> surroundingPieces;
	/**
	 * pieces on opposite end of surrounding Piece
	 */
	private ArrayList<GamePiece> edgePieces;
	/**
	 * pieces gained by moving on this piece, can go negative 
	 */
	private int piecesGained;
	
	private int longTermGain;
	
	public GamePiece() {
		piecesGained = 0;
	}
	public GamePiece(int x, int y, Integer color) {
		this.xLocation = x;
		this.yLocation = y;

		this.color = color;
		edgePieces = new ArrayList<GamePiece>();
		piecesGained = 0;
	}
	public GamePiece(int x, int y){
		this.xLocation = x;
		this.yLocation = y;
		edgePieces = new ArrayList<GamePiece>();
		piecesGained = 0;
	}
	public Integer getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Point getDirection() {
		return direction;
	}

	public void setDirection(int x, int y) {
		this.direction = new Point(x, y);
	}

	public boolean hasColor(int c) {
		return getColor() == c;
	}

	public int getXLocation() {
		return this.xLocation;
	}

	public int getYLocation() {
		return this.yLocation;
	}

	public ArrayList<GamePiece> getEdgePieces() {
		return edgePieces;
	}

	public boolean isOccupied() {
		return color != null;
	}
	public void addEdgePiece(GamePiece edgePiece) {
		edgePieces.add(edgePiece);
	}
	public void clearEdgePieces() {
		edgePieces =  new ArrayList<GamePiece>();
	}
	public void setSurroundingPieces(ArrayList<GamePiece>surList){
		surroundingPieces = surList;
	}
	public ArrayList<GamePiece> getSurroundingPieces(){
		return surroundingPieces;
	}
	public int getPiecesGained() {
		return piecesGained;
	}
	public int getLongTermGain() {
		return longTermGain;
	}
	public void setPiecesGained(int piecesGained) {
		this.piecesGained = piecesGained;
		this.longTermGain = piecesGained;
	}
	public void switchPiecesGained(int number){
		this.longTermGain -= number;
	}
	public String toString(){
		return "Color: " + this.color + " Location: " + this.xLocation + ", " + this.yLocation;
	}
}
