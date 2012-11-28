package neuburger.othello;

import android.graphics.Point;


public class TranslatePointToBox {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TranslatePointToBox translate = new TranslatePointToBox();
		translate.setScreenX(480);
		translate.setScreenY(480);
		
		Point point = translate.translate(new Float(479), Float.valueOf(3));
		System.out.println("row, column numbers of point 479, 3: "+point.x+" "+point.y);
		
		point = translate.translate(new Float(420), new Float(3));
		System.out.println("row, column numbers of point 420, 3: "+point.x+" "+point.y);
		
		point = translate.translate(new Float(489), new Float(3));
		System.out.println("row, column numbers of point 489, 3: "+point.x+" "+point.y);
		
		/*Point point = translate.translate(new Float(17), new Float(3));
		System.out.println("row, column numbers of point 17, 3: "+point.x+" "+point.y);
		
		point = translate.translate( new Float(417),  new Float(39));
		System.out.println("row, column numbers of point 417, 39: "+point.x+" "+point.y);
		
		point = translate.translate( new Float(217),  new Float(73));
		System.out.println("row, column numbers of point 217, 73: "+point.x+" "+point.y);
		
		point = translate.translate( new Float(70),  new Float(123));
		System.out.println("row, column numbers of point 70, 123: "+point.x+" "+point.y);
		*/
	}
	private int screenX;
	private int screenY;
	
	public TranslatePointToBox(){
	}
	public void setScreenX(int x){
		this.screenX = x;
	}
	public void setScreenY(int y){
		this.screenY = y;
	}
	/*
	public Float translate(Float x, Float y, Float begX, Float endX){
		Float row = 0;
		if(x <= endX && x >= (endX - 60)){
			long remainder = screenX % x;
			long boundary  = x+remainder;
			return (Float) ((Float) this.screenX/boundary);
//			return this.screenX/endX;
		}
		else if(x < (endX/4))//poFloat is in 1st 1/2 of screen
			row =  translate(x, y, 0, (endX/4));
		else if(x > (endX/4)){
			row =  translate(x, y, (endX/4), endX);
		}
		
		return row;
	}
	*/
	public Point translate(Float x, Float y){
		Float row = FindRowNumber(x);
		Float column = FindColumNumber(y);
		return new Point(row.intValue(), column.intValue());
	}
	
	private Float FindRowNumber(Float x) {
		for(int i = 0, boundary = 0; i < 9; i++, boundary+=screenY/8){
			if(x <= (boundary + screenY/8) && x >= boundary){
				return new Float(i+1);
			}
		}	
			return null;//never reaches here, unless received an invalid poFloat, which it can't in runtime
	}
	private Float FindColumNumber(Float y) {
		for(int i = 0, boundary = 0; i < 9; i++, boundary+=screenX/8){
			if(y <= (boundary + screenX/8) && y >= boundary){
				return new Float(i+1);
			}
		}	
			return null;//never reaches here, unless received an invalid poFloat, which it can't in runtime
	}
}
