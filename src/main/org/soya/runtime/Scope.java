package org.soya.runtime;

/**
 * @author: Jun Gong
 */
public interface Scope {

    Scope getParent();

    boolean contains(String name);

    Object getVariable(String name);

    void setVariable(String name, Object value);
}
