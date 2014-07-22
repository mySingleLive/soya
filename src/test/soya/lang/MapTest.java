package soya.lang;

import org.soya.runtime.VarScope;
import soya.util.SoyaBaseTestCase;

/**
 * @author: Jun Gong
 */
public class MapTest extends SoyaBaseTestCase {


    public void testSize() {
        SoyaMap map = new SoyaMap();
        assertEquals(0, map.size());
        map.put("a", 1);
        assertEquals(1, map.size());
        map.put("b", 2);
        map.put("c", 3);
        assertEquals(3, map.size());
        map.put("b", 4);
        map.put("c", 5);
        assertEquals(3, map.size());
    }

    public void testValue() {
        SoyaMap map = new SoyaMap();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        assertEquals(1, map.get("a"));
        assertEquals(2, map.get("b"));
        assertEquals(3, map.get("c"));

        map.put(1, "abc");
        map.put(2, "xyz");
        assertEquals("abc", map.get(1));
        assertEquals("xyz", map.get(2));

        map.set("name", "Peter");
        map.set("age", 23);
        assertEquals("Peter", map.get("name"));
        assertEquals(23, map.get("age"));

/*
        SoyaList results = (SoyaList) map.get(new Object[] {"a", "b", "c"});
        SoyaList list = new SoyaList();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(list, results);
*/

/*
        map.set(new Object[] {new ClassPattern(int.class)}, 0);
        assertEquals(0, map.get("a"));
        assertEquals(0, map.get("b"));
        assertEquals(0, map.get("c"));
        assertEquals(0, map.get("age"));
*/
    }


/*
    public void testToString() {
        SoyaMap map = new SoyaMap();
        assertEquals(map.toString(), "{}");
        map.put("a", 1);
        assertEquals(map.toString(), "{a: 1}");
    }
*/

    public class ClosureVarScope extends VarScope {
        public String __var_key;
        public int __var_value;
    }

    public void testEach() throws Throwable {
/*
        SoyaMap map = new SoyaMap();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        VarScope varScope = new ClosureVarScope();
        final ClassPattern keyPattern = new ClassPattern(Key.class, "key", varScope);
        final ClassPattern valPattern = new ClassPattern(Object.class, "value", varScope);

        final int[] count = {0};

        map.each(keyPattern, new Closure() {
            public Object call(Object it) throws Exception {
                assertEquals(it, keyPattern.getValue());
                count[0]++;
                return null;
            }
        });

        assertEquals(map.size(), count[0]);
        count[0] = 0;

        map.each(valPattern, new Closure() {
            public Object call(Object it) throws Exception {
                assertEquals(it, valPattern.getValue());
                count[0]++;
                return null;
            }
        });
        assertEquals(map.size(), count[0]);
*/
    }

}
