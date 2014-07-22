package soya.lang;

import soya.util.SoyaBaseTestCase;

/**
 * @author: Jun Gong
 */
public class RangeTest extends SoyaBaseTestCase {

    public void testSize() {
        ObjectRange range1 = new ObjectRange(0, 20);
        assertEquals(21, range1.size());

        ObjectRange range2 = new ObjectRange(-10, 10);
        assertEquals(21, range2.size());

/*
        ObjectRange range3 = new ObjectRange(0, 100, false, true);
        assertEquals(100, range3.size());

        ObjectRange range4 = new ObjectRange(0, 100, false, false);
        assertEquals(99, range4.size());

        ObjectRange range5 = new ObjectRange(0, 100, true, false);
        assertEquals(100, range5.size());
*/
    }

    public void testToString() {
        ObjectRange range1 = new ObjectRange(0, 100);
        assertEquals("0..100", range1.toString());

        ObjectRange range2 = new ObjectRange(-1, 10, false, true);
        assertEquals("-1>..10", range2.toString());

        ObjectRange range3 = new ObjectRange(0, 20, false, false);
        assertEquals("0>..<20", range3.toString());

        ObjectRange range4 = new ObjectRange(0, 30, true, false);
        assertEquals("0..<30", range4.toString());
    }

}
