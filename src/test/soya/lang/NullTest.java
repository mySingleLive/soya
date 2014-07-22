package soya.lang;

import soya.util.SoyaBaseTestCase;

/**
 * @author: Jun Gongjun
 */
public class NullTest extends SoyaBaseTestCase {

    private Null aNull = Null.NULL;

    public void testToString() {
        assertEquals("null", aNull.toString());
    }

    public void testIsEmpty() {
        assertEquals(true, aNull.isEmpty());
    }

    public void testIsNull() {
        assertEquals(true, aNull.isNull());
    }

    public void testEquals() {
        assertTrue(aNull.equals(null));
        assertTrue(aNull.equals(Null.NULL));
    }
}
