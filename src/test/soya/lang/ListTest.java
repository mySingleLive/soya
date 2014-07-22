package soya.lang;

import org.soya.runtime.VarScope;
import soya.util.SoyaBaseTestCase;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author: Gongjun
 */
public class ListTest extends SoyaBaseTestCase {

    public void testSize() {
        SoyaList list = new SoyaList();
        assertEquals(0, list.size());
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.size());
    }


/*
    public void testToString() {
        SoyaList list = new SoyaList();
        assertEquals("[]", list.toString());
        list.add("a");
        assertEquals("[\"a\"]", list.toString());
        list.add(new SoyaString("b"));
        assertEquals("[\"a\", \"b\"]", list.toString());
        List list2 = new ArrayList();
        list.add(list2);
        assertEquals("[\"a\", \"b\", []]", list.toString());
        list2.add(1);
        list2.add(2);
        assertEquals("[\"a\", \"b\", [1, 2]]", list.toString());
        list2.add("c");
        assertEquals("[\"a\", \"b\", [1, 2, \"c\"]]", list.toString());
        List list3 = new ArrayList();
        list3.add("hallo");
        list3.add("ok");
        list2.add(list3);
        assertEquals("[\"a\", \"b\", [1, 2, \"c\", [\"hallo\", \"ok\"]]]", list.toString());
        list.add(list);
        assertEquals("[\"a\", \"b\", [1, 2, \"c\", [\"hallo\", \"ok\"]], [...]]", list.toString());
        List list4 = new LinkedList();
        list4.add(list);
        list4.add(list2);
        list3.add(list4);
        assertEquals("[\"a\", \"b\", [1, 2, \"c\", [\"hallo\", \"ok\", [[...], [...]]]], [...]]", list.toString());
    }
*/

    public class ClosureVarScope extends VarScope {
        public Object __var_value;
    }

    public void testEquals() {
        SoyaList list1 = new SoyaList();
        list1.add(1);
        list1.add(2);
        list1.add("ok");
        assertTrue(list1.equals(list1));

        SoyaList list2 = new SoyaList();
        list2.add(1);
        list2.add(2);
        list2.add("ok");
        assertEquals(list1, list2);

        SoyaList list3 = new SoyaList();
        list3.add(1);
        list3.add("ok");
        assertFalse(list1.equals(list3));

        ArrayList alist = new ArrayList();
        alist.add(1);
        alist.add(2);
        alist.add("ok");
        SoyaList list4 = new SoyaList(alist);
        assertEquals(list1, list4);

        SoyaList list5 = new SoyaList(new Object[] {1, 2, "ok"});
        assertEquals(list1, list5);
    }

    public void testEach() throws Throwable {
        SoyaList list = new SoyaList();
        assertEquals(0, list.size());
        list.add(1);
        list.add("a");
        list.add(3);
        list.add(new SoyaDate(new Date()));
        SoyaList list2 = new SoyaList();
        list2.add(2);
        list2.add("b");
        list.add(list2);
        list.add("c");

        final int count[] = {0};
        list.each(new Closure() {
            public Object call(Object it) throws Exception {
                count[0]++;
                return null;
            }
        });

        assertEquals(list.size(), count[0]);
        count[0] = 0;

        final ClassPattern stringPattern = new ClassPattern(String.class, "value", new ClosureVarScope());
        list.each(stringPattern, new Closure() {
            public Object call(Object it) throws Exception {
                assertTrue(it.getClass().isAssignableFrom(String.class) || it.getClass().isAssignableFrom(SoyaString.class));
                assertEquals(it, stringPattern.getValue());
                count[0]++;
                return null;
            }
        });

        assertEquals(2, count[0]);
        count[0] = 0;

        final ClassPattern intPattern = new ClassPattern(int.class, "value", new ClosureVarScope());
        list.each(intPattern, new Closure() {
            public Object call(Object it) throws Exception {
                count[0]++;
                return null;
            }
        });

        assertEquals(2, count[0]);

    }
}
