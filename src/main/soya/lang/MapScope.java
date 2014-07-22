package soya.lang;

import org.soya.runtime.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jun Gong
 */
public class MapScope extends AbstractObject implements Scope {

    private Scope parent;
    private Map<String, Object> variables = new HashMap<String, Object>();

    public MapScope() {
    }

    public MapScope(MapScope parent) {
        this.parent = parent;
    }

    public Object getVariable(String name) throws NoSuchVariableException {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        else if (parent != null) {
            return parent.getVariable(name);
        }
        throw new NoSuchVariableException(name);
    }

    public void setVariable(String name, Object value) throws NoSuchVariableException {
        if (variables.containsKey(name)) {
            variables.put(name, value);
        }
        else if (parent != null) {
            parent.setVariable(name, value);
        }
        else {
            throw new NoSuchVariableException(name);
        }
    }

    public Scope getParent() {
        return parent;
    }

    @Override
    public boolean contains(String name) {
        if (variables.containsKey(name)) {
            return true;
        }
        if (parent != null) {
            return parent.contains(name);
        }
        return false;
    }

    public void setParent(Scope parent) {
        this.parent = parent;
    }

    public boolean hasVariable(String name) {
        return variables != null && variables.containsKey(name);
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

}
