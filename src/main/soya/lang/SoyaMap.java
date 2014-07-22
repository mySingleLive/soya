package soya.lang;

import org.soya.runtime.MetaClassUtil;
import org.soya.runtime.PatternUtil;
import soya.util.pattern.Index;
import soya.util.pattern.Key;

import java.util.*;

/**
 * @author: Jun Gong
 */
public class SoyaMap extends EvalObject implements Map, Pattern, SoyaCollection {

    private Map map;

    public SoyaMap() {
        this(new HashMap<String, Object>());
    }

    public SoyaMap(Map map) {
        super(MetaClassUtil.Map);
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean containsKey(Object o) {
        return map.containsKey(o);
    }

    public boolean containsValue(Object o) {
        return map.containsValue(o);
    }

    public String getWithIndex(int i) {
        return (String) map.keySet().toArray()[i];
    }


/*
    public Object get(Object[] indexes) {
        if (indexes.length == 0) {
            return Null.NULL;
        }
        else if (indexes.length == 1) {
            Object result = map.get(indexes[0]);
            return result;
        }
        else {
            SoyaList results = new SoyaList();
            for (Object index : indexes) {
                results.add(get(index));
            }
            return results;
        }
    }
*/


    public void each(Object pattern1, Object pattern2, Closure closure) throws Throwable {
        ClassPattern keyPattern = null;
        ClassPattern indexPattern = null;
        ClassPattern entryPattern = null;
        Object pattern = null;

        if (pattern1 instanceof ClassPattern) {
            if (((ClassPattern) pattern1).getMetaClass().getClazz().equals(Key.class)) {
                keyPattern = (ClassPattern) pattern1;
            }
            else if (((ClassPattern) pattern1).getMetaClass().isKindOf(Index.class)) {
                indexPattern = (ClassPattern) pattern1;
            }
            else if (((ClassPattern) pattern1).getMetaClass().isKindOf(Entry.class)) {
                entryPattern = (ClassPattern) pattern1;
            }
            else {
                pattern = pattern1;
            }
        }

        if (pattern1 instanceof ClassPattern) {
            if (((ClassPattern) pattern2).getMetaClass().getClazz().equals(Key.class)) {
                if (keyPattern != null) {
                    throw new IllegalArgumentException();
                }
                keyPattern = (ClassPattern) pattern2;
            }
            else if (((ClassPattern) pattern2).getMetaClass().isKindOf(Index.class)) {
                if (indexPattern != null) {
                    throw new IllegalArgumentException();
                }
                indexPattern = (ClassPattern) pattern2;
            }
            else if (((ClassPattern) pattern2).getMetaClass().isKindOf(Entry.class)) {
                if (entryPattern != null) {
                    throw new IllegalArgumentException();
                }
                entryPattern = (ClassPattern) pattern2;
            }
            else {
                if (pattern != null) {
                    throw new IllegalArgumentException();
                }
                pattern = pattern2;
            }
        }

        Iterator iterator = map.entrySet().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            if (keyPattern != null) {
                keyPattern.setValue(entry.getKey());
            }
            else if (entryPattern != null) {
                entryPattern.setValue(entry);
            }
            else if (indexPattern != null) {
                indexPattern.setValue(count);
            }
            if (pattern != null && PatternUtil.isMatch(entry.getValue(), pattern)) {
                closure.call(entry.getValue());
            }
            count++;
        }
    }

    public void each(Object obj, Closure closure) throws Throwable {
        ClassPattern keyPattern = null;
        ClassPattern indexPattern = null;
        ClassPattern entryPattern = null;
        Object pattern = null;

        if (obj instanceof ClassPattern) {
            ClassPattern classPattern = (ClassPattern) obj;
            if (classPattern.getMetaClass().isKindOf(Key.class)) {
                keyPattern = classPattern;
            }
            else if (classPattern.getMetaClass().isKindOf(Index.class)) {
                indexPattern = classPattern;
            }
            else if (classPattern.getMetaClass().isKindOf(Entry.class)) {
                entryPattern = classPattern;
            }
            else {
                pattern = classPattern;
            }
        }
        else {
            pattern = obj;
        }
        Iterator iterator = map.entrySet().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            if (keyPattern != null) {
                keyPattern.setValue(entry.getKey());
                closure.call(entry.getValue());
            }
            else if (entryPattern != null) {
                entryPattern.setValue(entry);
                closure.call(entry);
            }
            else if (pattern != null && PatternUtil.isMatch(entry.getValue(), pattern)) {
                closure.call(entry.getValue());
            }
            else if (indexPattern != null) {
                indexPattern.setValue(count);
                closure.call(entry);
            }
            count++;
        }
    }

    public Object getUndefinedProperty(String name) {
        return get(name);
    }

    public void setUndefinedProperty(String name, Object value) {
        set(name, value);
    }

    public SoyaMap plus(Map obj) {
        SoyaMap newMap = new SoyaMap(map);
        for (Object key : obj.keySet()) {
            newMap.put(key, obj.get(key));
        }
        return newMap;
    }

    public void set(Object[] indexes, Object o) {
        if (indexes.length == 0) {
            return;
        }

        for (Object key : indexes) {
            set(key, o);
        }
    }

    public void set(Object index, Object o) {
        put(index, o);
    }

    public Object get(Object o) {
        if (o instanceof SoyaString) {
            o = o.toString();
        }
        return map.get(o);
    }

    public Object put(Object o, Object o2) {
        if (o instanceof SoyaString) {
            o = o.toString();
        }
        return map.put(o, o2);
    }

    public Object remove(Object o) {
        return map.remove(o);
    }

    public void putAll(Map map) {
        this.map.putAll(map);
    }

    public void clear() {
        map.clear();
    }

    public Set keySet() {
        return map.keySet();
    }

    public Collection values() {
        return map.values();
    }

    public Set<Entry> entrySet() {
        return map.entrySet();
    }

    private String format(Map obj) {
        StringBuffer buffer = new StringBuffer();
        buffer.append('{');
        int count = 0;
        for (Iterator keyIt = obj.keySet().iterator(); keyIt.hasNext(); ) {
            Object key = keyIt.next();
            if (key instanceof CharSequence) {
                buffer.append('"');
                buffer.append(key.toString().replace("\"", "\\\""));
                buffer.append('"');
            }
            else {
                buffer.append(key);
            }
            buffer.append(": ");
            Object val = obj.get(key);
            if (val != null) {
                String valStr;
                if (val instanceof Map) {
                    valStr = format((Map) val);
                }
                else {
                    valStr = val.toString();
                }
                buffer.append(valStr);
                if (count < obj.size() - 1) {
                    buffer.append(", ");
                }
                count++;
            }
        }
        buffer.append('}');
        return buffer.toString();
    }

    public String toString() {
        return format(this);
    }

    @Override
    public boolean isMatch(Object obj) throws Throwable {
        if (!(obj instanceof Map)) {
            return false;
        }
        Map objMap = (Map) obj;
        for (Object key : map.keySet()) {
            if (!objMap.containsKey(key)) {
                return false;
            }
            Object value = map.get(key);
            Object objValue = objMap.get(key);
            if (!PatternUtil.isMatch(objValue, value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object getObject() {
        return map.getClass();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Map) {
            Map m = (Map) obj;
            if (map.size() != m.size()) {
                return false;
            }
            for (Object key : map.keySet()) {
                if (!m.containsKey(key)) {
                    return false;
                }
                if (!m.get(key).equals(map.get(key))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public Map shiftLeft(Map right) {
        for (Iterator iterator = right.keySet().iterator(); iterator.hasNext(); ) {
            Object key = iterator.next();
            Object value = right.get(key);
            map.put(key, value);
        }
        return this;
    }
}
