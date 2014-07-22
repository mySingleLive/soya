package soya.lang;

import org.soya.runtime.MetaClassUtil;
import org.soya.runtime.PatternUtil;
import org.soya.runtime.VarScope;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class ObjectPattern extends EvalObject implements Pattern {

    protected Object object;
    protected String alias;
    protected VarScope varScope;
    protected List<Object> conditions = new LinkedList<Object>();


    public ObjectPattern(MetaClass mclass) {
        this(mclass, null);
    }

    public ObjectPattern(MetaClass mclass, Object object) {
        this(mclass, object, null, null);
    }

    public ObjectPattern(MetaClass mclass, Object object, String alias, VarScope varScope) {
        super(mclass);
        this.object = object;
        this.alias = alias;
        this.varScope = varScope;
    }

    public ObjectPattern(Object object, String alias, VarScope varScope) {
        this(MetaClassUtil.createMetaClass(Object.class), object, alias, varScope);
    }


    public boolean isMatch(Object obj) throws Throwable {
        boolean ret = isCase(obj);
        if (ret) {
            setValue(obj);
        }
        return ret;
    }

    public boolean isCase(Object obj) throws Throwable {
        return PatternUtil.isMatch(obj, object);
    }

    public void setValue(Object value) {
        if (alias != null && !alias.isEmpty() && varScope != null) {
            varScope.setVariable(alias, value);
        }
    }

    public Object getValue() {
        if (alias != null && !alias.isEmpty() && varScope != null) {
            return varScope.getVariable(alias);
        }
        return null;
    }

    public Object get(Object[] patterns) {
        ObjectPattern classPattern = clone();
        for (Object p : patterns) {
            classPattern.addCondition(p);
        }
        return classPattern;
    }

    public Object get(Object pattern) {
        ObjectPattern classPattern = clone();
        classPattern.addCondition(pattern);
        return classPattern;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectPattern) {
            return ((ObjectPattern) obj).getObject().equals(object);
        }
        return false;
    }


    public List<Object> getConditions() {
        return conditions;
    }

    public void setConditions(List<Object> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(Object condition) {
        conditions.add(condition);
    }

    public String getAlias() {
        return alias;
    }

    public VarScope getVarScope() {
        return varScope;
    }

    public Object getObject() {
        return object;
    }

    public ObjectPattern clone() {
        ObjectPattern classPattern = new ObjectPattern(metaClass, object, alias, varScope);
        classPattern.setConditions(conditions);
        return classPattern;
    }


}
