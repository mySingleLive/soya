package soya.lang;

import java.util.List;

/**
 * @author: Jun Gong
 */
public interface Range extends List, Pattern, SoyaCollection {

    Comparable getFrom();

    Comparable getTo();

    Object getStep();

    boolean isIncludeFrom();

    boolean isIncludeTo();
    
}
