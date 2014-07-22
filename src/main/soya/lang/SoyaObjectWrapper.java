package soya.lang;

import java.lang.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jun Gong
 */
public class SoyaObjectWrapper {

    private static Map<Class, WrapperCommand> wrapCommandMap = new HashMap<Class, WrapperCommand>();
    static {
        wrapCommandMap.put(Boolean.class, new WrapperCommand<Boolean, SoayBoolean>() {
            public SoayBoolean wrap(Boolean aBoolean) {
                return new SoayBoolean(aBoolean);
            }
        });
        wrapCommandMap.put(int.class, new WrapperCommand<Integer, Int>() {
            public Int wrap(Integer integer) {
                return new Int(integer);
            }
        });
        wrapCommandMap.put(Integer.class, new WrapperCommand<Integer, Int>() {
            public Int wrap(Integer integer) {
                return new Int(integer);
            }
        });
        wrapCommandMap.put(java.lang.Float.class, new WrapperCommand<java.lang.Float, Float>() {
            public Float wrap(java.lang.Float aFloat) {
                return new Float(aFloat);
            }
        });
        wrapCommandMap.put(String.class, new WrapperCommand<String, SoyaString>() {
            public SoyaString wrap(String s) {
                return new SoyaString(s);
            }
        });
    }

    public SoyaObject wrap(Object obj) {
        if (obj == null) {
            return Null.NULL;
        }
        if (obj instanceof SoyaObject) {
            return (SoyaObject) obj;
        }
        if (wrapCommandMap.containsKey(obj.getClass())) {
            WrapperCommand commond = wrapCommandMap.get(obj.getClass());
            return (SoyaObject) commond.wrap(obj);
        }
        return Null.NULL;
    }

    public SoayBoolean wrap(boolean value) {
        return new SoayBoolean(value);
    }

    public Int wrap(int value) {
        return new Int(value);
    }
}
