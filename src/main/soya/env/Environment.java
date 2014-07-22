package soya.env;

/**
 * @author: Jun Gong
 */
public interface Environment  {

    public String getName();

    public Environment getParent();

    public void start();

    public void end();
}
