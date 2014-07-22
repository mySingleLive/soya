package soya.lang;

import soya.util.SoyaBaseTestCase;

/**
 * @author: Jun Gong
 */
public class TupleTest extends SoyaBaseTestCase {

    public void testToString() {
        Tuple tuple1 = new Tuple();
        assertEquals("()", tuple1.toString());

        Tuple tuple2 = new Tuple(new Object[] {1, 3, "a"});
        assertEquals("(1, 3, \"a\")", tuple2.toString());

        Tuple tuple3 = new Tuple(new Object[] {20, 40}, true);
        assertEquals("20x40", tuple3.toString());

        Tuple tuple4 = new Tuple(new Object[] {1, 2, "a"}, true);
        assertEquals("1x2xa", tuple4.toString());
    }

}
