package soya.lang;

/**
 * @author: Jun Gong
 */
public class Long extends EvalObject implements Comparable, Pattern {

    private long value;


    @Override
    public int compareTo(Object o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isMatch(Object obj) throws Throwable {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
