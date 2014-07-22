package org.soya.runtime;

import java.lang.reflect.Field;

/**
 * @author: Jun Gong
 */
public abstract class VarScope implements Scope {

    public final VarScope parent;

    public VarScope() {
        this(null);
    }

    public VarScope(VarScope parent) {
        this.parent = parent;
    }

    public VarScope getParent() {
        return parent;
    }

    private String toFieldName(String name) {
        return "__var_" + name;
    }

    @Override
    public boolean contains(String name) {
        Field field = null;
        try {
            field = this.getClass().getField(toFieldName(name));
        } catch (Exception e) {
            if (parent != null) {
                return parent.contains(name);
            }
        }
        return field != null;
    }

    public Object getVariable(String name) {
        try {
            return this.getClass().getField(toFieldName(name)).get(this);
        } catch (Exception e) {
            if (parent != null) {
                return parent.getVariable(name);
            }
            else {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return null;
    }

    public void setVariable(String name, Object value) {
        try {
            this.getClass().getField(toFieldName(name)).set(this, value);
        } catch (Exception e) {
            if (parent != null) {
                parent.setVariable(name, value);
            }
            else {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }
}
