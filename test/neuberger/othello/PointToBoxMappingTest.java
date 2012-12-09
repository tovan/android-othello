package neuberger.othello;

import neuburger.othello.TranslatePointToBox;

import junit.framework.TestCase;
import android.graphics.Point;

public class PointToBoxMappingTest extends TestCase {

	public void testTranslations() {

		TranslatePointToBox translatePointToBox = new TranslatePointToBox();
		Point point = translatePointToBox.translate(Float.valueOf(479),
				Float.valueOf(3), 60, 60);
		System.out.println(point.x + ", " + point.y);
		assertEquals(point.x, 7);
		assertEquals(point.y, 0);

		point = translatePointToBox.translate(Float.valueOf(17),
				Float.valueOf(3), 60, 60);
		assertEquals(point.x, 0);
		assertEquals(point.y, 0);

		point = translatePointToBox.translate(Float.valueOf(417),
				Float.valueOf(39), 60, 60);
		assertEquals(point.x, 6);
		assertEquals(point.y, 0);

		point = translatePointToBox.translate(Float.valueOf(217),
				Float.valueOf(73), 60, 60);
		assertEquals(point.x, 3);
		assertEquals(point.y, 1);

		point = translatePointToBox.translate(Float.valueOf(70),
				Float.valueOf(123), 60, 60);
		assertEquals(point.x, 1);
		assertEquals(point.y, 2);

	}

}
