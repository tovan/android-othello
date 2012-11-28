import android.graphics.Point;

import com.example.neuburger_othello.TranslatePointToBox;

import junit.framework.TestCase;

private TranslatePointToBox translate;

public class testTranslatePointToBox extends TestCase {
	translate = new TranslatePointToBox();
	translate.setScreenX(480);
	translate.setScreenY(480);
	
}
@Test
public void testTranslations{
	Point point = translate.translate(new Float(479), new Float(3));
	assertEquals(point.x, 7);
	assertEquals(point.y, 1);
	
	System.out.println("row, column numbers of poFloat 17, 3: "+point.x+" "+point.y);
	
	Point point = translate.translate(new Float(17), new Float(3));
	System.out.println("row, column numbers of poFloat 17, 3: "+point.x+" "+point.y);
	
	point = translate.translate( new Float(417),  new Float(39));
	System.out.println("row, column numbers of poFloat 417, 39: "+point.x+" "+point.y);
	
	point = translate.translate( new Float(217),  new Float(73));
	System.out.println("row, column numbers of poFloat 217, 73: "+point.x+" "+point.y);
	
	point = translate.translate( new Float(70),  new Float(123));
	System.out.println("row, column numbers of poFloat 70, 123: "+point.x+" "+point.y);
	
}