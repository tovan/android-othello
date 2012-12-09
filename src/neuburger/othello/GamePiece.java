package neuburger.othello;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Point;


public class GamePiece {
	private int xLocation;
	private int yLocation;
	private Integer color;
	private Point direction;

	private ArrayList<GamePiece> potentialMoves;
	private ArrayList<GamePiece> edgePieces;
	private Point location;

	public GamePiece() {

	}
	public static void main(String[] args) {
		System.out.println(Color.BLACK);
	}
	public GamePiece(int x, int y, Integer color) {
		this.xLocation = x;
		this.yLocation = y;
		this.location = new Point(xLocation, yLocation);

		this.color = color;
		potentialMoves = new ArrayList<GamePiece>();
		edgePieces = new ArrayList<GamePiece>();
	}
	public GamePiece(int x, int y){
		this.xLocation = x;
		this.yLocation = y;
		this.location = new Point(xLocation, yLocation);
		potentialMoves = new ArrayList<GamePiece>();
		edgePieces = new ArrayList<GamePiece>();

	}
	public Integer getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
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

	public int getX() {
		return this.xLocation;
	}

	public int getY() {
		return this.yLocation;
	}

	public void addToPotentialMovesList(GamePiece nextPiece) {
		potentialMoves.add(nextPiece);
	}

	public ArrayList<GamePiece> getEdgePieces() {
		return edgePieces;
	}

	public void setEdgePieces(ArrayList<GamePiece> edgePieces) {
		this.edgePieces = edgePieces;
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
}
