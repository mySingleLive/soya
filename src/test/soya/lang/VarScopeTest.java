package soya.lang;

import org.soya.runtime.VarScope;
import soya.util.SoyaBaseTestCase;

/**
 * @author: Jun Gong
 */
public class VarScopeTest extends SoyaBaseTestCase {

    public class TestVarScope extends VarScope {
        public int __var_i;
        public String __var_s;
    }

    public class ChildVarScope extends VarScope {
        public int __var_x;
        public String __var_s;

        public ChildVarScope(VarScope parent) {
            super(parent);
        }
    }

    public void testVars() throws NoSuchFieldException, IllegalAccessException {
        VarScope varScope = new TestVarScope();
        Class varScopeClass = varScope.getClass();
        varScope.setVariable("i", 1);
        assertEquals(1, varScope.getVariable("i"));
        varScope.setVariable("s", "ok");
        assertEquals("ok", varScope.getVariable("s"));
    }

    public void testChildVarScope() {
        VarScope parent = new TestVarScope();
        VarScope child = new ChildVarScope(parent);

        assertEquals(parent, child.getParent());

        parent.setVariable("i", 1);
        parent.setVariable("s", "a");

        child.setVariable("x", 2);
        child.setVariable("s", "b");

        assertEquals(1, child.getVariable("i"));
        assertEquals(2, child.getVariable("x"));
        assertEquals("b", child.getVariable("s"));
    }

}
