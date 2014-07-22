package soya.lang;

/**
 * @author: Jun Gong
 */
public class Property {

    private String name;
    private MetaClass type;
    private SoyaMethod setter;
    private SoyaMethod getter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MetaClass getType() {
        return type;
    }
}
