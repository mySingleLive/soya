package soya.lang;



import org.soya.runtime.InvokeUtil;
import org.soya.runtime.MetaClassUtil;
import org.soya.runtime.PatternUtil;

import java.util.*;

/**
 * @author: Jun Gong
 */
public class SoyaList extends EvalObject implements List, Pattern, SoyaCollection {

    protected final List list;

    public SoyaList() {
        this(new ArrayList());
    }

    public SoyaList(List list) {
        super(MetaClassUtil.List);
        this.list = list;
    }

    public SoyaList(Object[] array) {
        super(MetaClassUtil.List);
        int len = array.length;
        list = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            list.add(array[i]);
        }
    }

    public int size() {
        return list.size();
    }

    @Override
    public Object getWithIndex(int index) {
        if (index < 0) {
            index = size() + index;
        }
        return list.get(index);
    }


    public int length() {
        return size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean contains(Object o) {
        return list.contains(o);
    }

    public Iterator iterator() {
        return list.iterator();
    }

    public Object[] toArray() {
        return list.toArray();
    }

    public boolean add(Object o) {
        return list.add(o);
    }

    public boolean containsAll(Collection objects) {
        return list.containsAll(objects);
    }

    public boolean addAll(Collection collection) {
        return list.addAll(collection);
    }

    public boolean addAll(int i, Collection collection) {
        return list.addAll(collection);
    }

    public boolean retainAll(Collection objects) {
        return list.retainAll(objects);
    }

    public boolean removeAll(Collection objects) {
        return list.removeAll(objects);
    }

    public void clear() {
        list.clear();
    }

    public Object get(int index1, int index2) throws Throwable {
        List ret = new SoyaList();
        if (index1 < 0) {
            index1 = size() + index1;
        }
        if (index2 < 0) {
            index2 = size() + index2;
        }
        if (index2 > index1) {
            index2 = Math.min(index2, list.size() - 1);
            for (int i = index1; i <= index2; i++) {
                ret.add(list.get(i));
            }
        }
        else {
            ret.add(list.get(index1));
        }
        return ret;
    }

    public Object get(int index, Pattern pattern) throws Throwable {
        List ret = new SoyaList();
        int len = size();
        if (index < 0) {
            index = len + index;
        }
        for (int i = index; i < len; i++) {
            Object o = get(i);
            if (PatternUtil.isMatch(o, pattern)) {
                ret.add(o);
            }
        }
        return ret;
    }

    public Object get(ClassPattern pattern, int index) throws Throwable {
        List ret = new SoyaList();
        int len = size();
        if (index < 0) {
            index = len + index;
        }
        index = Math.min(index + 1, len);
        for (int i = 0; i < index; i++) {
            Object o = get(i);
            if (PatternUtil.isMatch(o, pattern)) {
                ret.add(o);
            }
        }
        return ret;
    }


    public Object get(Object o) throws Throwable {
        if (o instanceof Int) {
            int index = ((Int) o).getValue();
            if (index < 0) {
                index = size() + index;
            }
            return get(index);
        }
        else if (o instanceof Integer) {
            int index = ((Integer) o).intValue();
            if (index < 0) {
                index = size() + index;
            }
        	return get(index);
        }
        else {
        	return find(o);
        }
    }

    public Object get(int i) {
        return getWithIndex(i);
    }

    public void set(Object[] indexes, Object o) throws Throwable {
        if (indexes.length == 0) {
            return;
        }
        else if (indexes.length == 1) {
            set(indexes[0], o);
        }
        else if (indexes.length == 2) {
            Object o1 = indexes[0];
            Object o2 = indexes[1];
            Integer i1 = (Integer) InvokeUtil.transformToJavaObject(Integer.class, o1);
            Integer i2 = (Integer) InvokeUtil.transformToJavaObject(Integer.class, o2);
            int index1 = i1.intValue();
            int index2 = i2.intValue();
            if (index2 > index1) {
                index2 = Math.min(index2, list.size() - 1);
                for (int i = index1; i <= index2; i++) {
                    list.set(i, o);
                }
            }
            else {
                list.set(index1, o);
            }

        }
        else {
            throw new IllegalArgumentException("wrong number of arguments");
        }
    }

    public void set(Object pattern, Object o) throws Throwable {
        if (pattern instanceof Int) {
            set(((Int) pattern).getValue(), o);
        }
        else if (pattern instanceof Integer) {
            set(((Integer) pattern).intValue(), o);
        }
        else {
            boolean hasIndex = false;
            if (PatternUtil.hasIndexPattern(pattern)) {
                hasIndex = true;
            }
            int len = size();
            for (int i = 0; i < len; i++) {
                if (hasIndex) {
                    Int index = new Int(i);
                    if (PatternUtil.isMatch(index, pattern)) {
                        ((ObjectPattern) pattern).setValue(index);
                        set(i, o);
                    }
                }
                else {
                    Object obj = get(i);
                    if (PatternUtil.isMatch(obj, pattern)) {
                        set(i, o);
                    }
                }
            }
        }
    }

    public Object set(int i, Object o) {
        if (o instanceof Closure && ((Closure) o).callOnAssign) {
            try {
                o = ((Closure) o).call(list.get(i));
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        if (list.size() == i) {
            return list.add(o);
        }
        else {
            return list.set(i, o);
        }
    }

    public void add(int i, Object o) {
        list.add(i, o);
    }

    public List compact() {
        int len = size();
        List ret = new SoyaList();
        for (int i = 0; i < len; i++) {
            Object o = list.get(i);
            if (!InvokeUtil.isNull(o)) {
                ret.add(o);
            }
        }
        return ret;
    }

    public List uniq() {
        int len = size();
        List ret = new SoyaList();
        for (int i = 0; i < len; i++) {
            Object o = list.get(i);
            if (!ret.contains(o)) {
                ret.add(o);
            }
        }
        return ret;
    }

    public List sort() {
        Collections.sort(list);
        return this;
    }

    public List sort(final Closure closure) throws Throwable {
        Object[] array = toArray();
        final Throwable[] throwable = {null};
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                try {
                    Boolean ret = (Boolean) closure.call(o1, o2);
                    return ret.booleanValue() ? -1 : 1;
                } catch (Throwable t) {
                    throwable[0] = t;
                }
                return 0;
            }
        });
        if (throwable[0] != null) {
            throw throwable[0];
        }
        return this;
    }

    public List reverse() {
        List ret = new SoyaList(list.toArray());
        Collections.reverse(ret);
        return ret;
    }

    public Object pop() {
        return removeAt(size() - 1);
    }

    @Override
    public Object remove(int index) {
        return remove(new Int(index));
    }

    public Object removeAt(int i) {
        return list.remove(i);
    }

    public boolean remove(Object o) {
        return list.remove(o);
    }

    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    public ListIterator listIterator() {
        return list.listIterator();
    }

    public ListIterator listIterator(int i) {
        return list.listIterator(i);
    }

    public List subList(int i, int i2) {
        return list.subList(i, i2);
    }

    public Object[] toArray(Object[] objects) {
        return list.toArray(objects);
    }

    public boolean equals(Object o) {
        if (this == o || list == o) {
            return true;
        }
        if (o instanceof List) {
            List ls2 = (List) o;
            if (list.size() != ls2.size()) {
                return false;
            }
            for (int i = 0; i < list.size(); i++) {
                Object item1 = InvokeUtil.wrapObject(list.get(i));
                Object item2 = InvokeUtil.wrapObject(ls2.get(i));
                if (!item1.equals(item2)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public List find(Object pattern) throws Throwable {
        int len = size();
        List results = new SoyaList();
        for (int i = 0; i < len; i++) {
            Object obj = get(i);
            if (obj != null) {
                if (PatternUtil.isMatch(obj, pattern)) {
                    results.add(obj);
                }
            }
        }
        return results;
    }

    public Object last() {
        return get(size() - 1);
    }

    public Object last(Object pattern) throws Throwable {
        int len = size();
        Object ret = null;
        for (int i = 0; i < len; i++) {
            Object obj = get(i);
            if (PatternUtil.isMatch(obj, pattern)) {
                ret = obj;
            }
        }
        return ret;
    }

    public void setLast(Object value) {
        set(size() - 1, value);
    }

    public void setLast(Object pattern, Object value) throws Throwable {
        int len = size();
        int lastIndex = -1;
        for (int i = 0; i < len; i++) {
            Object obj = get(i);
            if (PatternUtil.isMatch(obj, pattern)) {
                lastIndex = i;
            }
        }
        if (lastIndex != -1) {
            set(lastIndex, value);
        }
    }

    public Object first() {
        return get(0);
    }

    public Object first(Object pattern) throws Throwable {
        int len = size();
        for (int i = 0; i < len; i++) {
            Object obj = get(i);
            if (PatternUtil.isMatch(obj, pattern)) {
                return obj;
            }
        }
        return null;
    }

    public List tail() {
        List ret = new SoyaList();
        int len = size();
        for (int i = 1; i < len; i++) {
            ret.add(list.get(i));
        }
        return ret;
    }

    public void setFirst(Object pattern, Object value) throws Throwable {
        int len = size();
        for (int i = 0; i < len; i++) {
            Object obj = get(i);
            if (PatternUtil.isMatch(obj, pattern)) {
                set(i, value);
                return;
            }
        }
    }

    public void setFirst(Object value) {
        set(0, value);
    }

    public List collect(Closure closure) throws Throwable {
        int len = size();
        List items = new SoyaList();
        for (int i = 0; i < len; i++) {
            Object o = list.get(i);
            Object ret = closure.call(o);
            items.add(ret);
        }
        return items;
    }

/*
    public void setEach(ObjectPattern indexPattern, Object pattern, Object value) throws Throwable {
        int len = size();
        boolean hasIndex = false;
        if (PatternUtil.hasIndexPattern(indexPattern)) {
            hasIndex = true;
        }
        for (int i = 0; i < len; i++) {
            if (hasIndex) {
                if (PatternUtil.isMatch(InvokeUtil.wrapObject(i), indexPattern)) {
                    indexPattern.setValue(new Int(i));
                }
                else {
                    continue;
                }
            }
            Object obj = get(i);
            if (PatternUtil.isMatch(obj, pattern)) {
                set(i, value);
            }
        }
    }
*/

    public void setEach(Object pattern, ObjectPattern indexPattern, Object value) throws Throwable {
        int len = size();
        boolean hasIndex = false;
        if (PatternUtil.hasIndexPattern(indexPattern)) {
            hasIndex = true;
        }
        for (int i = 0; i < len; i++) {
            if (hasIndex) {
                if (PatternUtil.isMatch(InvokeUtil.wrapObject(i), indexPattern)) {
                    indexPattern.setValue(new Int(i));
                }
                else {
                    continue;
                }
            }
            Object obj = get(i);
            if (PatternUtil.isMatch(obj, pattern)) {
                set(i, value);
            }
        }
    }


    public void setEach(Object pattern, Object value) throws Throwable {
        int len = size();
        boolean hasIndex = false;
        if (PatternUtil.hasIndexPattern(pattern)) {
            hasIndex = true;
        }
        for (int i = 0; i < len; i++) {
            Object obj = get(i);
            if (hasIndex) {
                if (PatternUtil.isMatch(InvokeUtil.wrapObject(i), pattern)) {
                    ((ObjectPattern) pattern).setValue(i);
                    set(i, value);
                }
            }
            else if (PatternUtil.isMatch(obj, pattern)) {
                set(i, value);
            }
        }
    }

    public void setEach(Object value) {
        int len = size();
        for (int i = 0; i < len; i++) {
            set(i, value);
        }
    }


    public void each(Object pattern1, Object pattern2, Closure closure) throws Throwable {
        int len = size();
        ClassPattern indexPattern = null;
        Object pattern = null;
        if (PatternUtil.hasIndexPattern(pattern1)) {
            indexPattern = (ClassPattern) pattern1;
            pattern = pattern2;
        }
        else if (PatternUtil.hasIndexPattern(pattern2)) {
            indexPattern = (ClassPattern) pattern2;
            pattern = pattern1;
        }
        else {
            pattern = pattern1;
        }

        for (int i = 0; i < len; i++) {
            if (indexPattern != null) {
                indexPattern.setValue(new Int(i));
            }
            Object obj = get(i);
            if (PatternUtil.isMatch(obj, pattern)) {
                closure.call(InvokeUtil.wrapObject(obj));
            }
        }
    }

    public void each(Object pattern, Closure closure) throws Throwable {
        int len = size();
        ClassPattern indexPattern = null;
        if (PatternUtil.hasIndexPattern(pattern)) {
            indexPattern = (ClassPattern) pattern;
        }
        for (int i = 0; i < len; i++) {
            Object obj = get(i);
            if (indexPattern != null) {
                indexPattern.setValue(new Int(i));
                closure.call(InvokeUtil.wrapObject(obj));
            }
            else if (PatternUtil.isMatch(obj, pattern)) {
                closure.call(InvokeUtil.wrapObject(obj));
            }
        }
    }

    public String join(String s) {
        int len = list.size();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < len; i++) {
            buffer.append(list.get(i));
            if (i < len - 1) {
                buffer.append(s);
            }
        }
        return buffer.toString();
    }

    public String join() {
        return join("");
    }

    public List plus(List arg) {
        SoyaList ret = new SoyaList(list.toArray());
        for (Object o : arg) {
            ret.add(o);
        }
        return ret;
    }

    public List multi(int n) {
        SoyaList ret = this;
        for (int i = 0; i < n - 1; i++) {
            ret = (SoyaList) ret.plus(this);
        }
        return ret;
    }

    public String multi(String s) {
        return this.join(s);
    }

    public void each(Closure closure) throws Throwable {
        int len = size();
        for (int i = 0; i < len; i++) {
            Object obj = get(i);
            closure.call(obj);
        }
    }

    @Override
    public boolean isMatch(Object obj) throws Throwable {
        Iterator objIt = null;

        if (!(obj instanceof Collection)) {
            return false;
        }

        if (list.size() == 0) {
            if (((Collection) obj).size() == 0) {
                return true;
            }
            return false;
        }

        objIt = ((Collection) obj).iterator();

        for (Object pattern : list) {
            Object o = obj;
            if (!objIt.hasNext()) {
                return false;
            }
            o = objIt.next();
            boolean ret = PatternUtil.isMatch(o, pattern);
            if (!ret) {
                return false;
            }
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List sortBy(final String name) {
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Object r1 = null;
                Object r2 = null;
                try {
                    r1 = InvokeUtil.invokeJavaObjectMethod(o1, name, new Object[0]);
                } catch (Throwable throwable) {
                }
                try {
                    r2 = InvokeUtil.invokeJavaObjectMethod(o2, name, new Object[0]);
                } catch (Throwable throwable) {
                }

                if ((r1 instanceof Comparable) && !(r2 instanceof Comparable)) {
                    return -1;
                }
                else if (!(r1 instanceof Comparable) && (r2 instanceof Comparable)) {
                    return 1;
                }
                else if (!(r1 instanceof Comparable) && !(r2 instanceof Comparable)) {
                    return 0;
                }
                return ((Comparable) r1).compareTo(r2);
            }
        });
        return this;
    }


    public List shiftLeft(Object o) {
        this.add(o);
        return this;
    }

    private class ListLink {
        public List list;
        public ListLink parent;
    }

    private String formatListString(ListLink link) {
        List list = link.list;
        StringBuffer buffer = new StringBuffer('[');
        buffer.append('[');
        int len = list.size();
        for (int i = 0; i < len; i++) {
            Object value = list.get(i);
            if (value instanceof String || value instanceof SoyaString) {
                buffer.append("\"");
                buffer.append(value.toString().replace("\"", "\\\""));
                buffer.append("\"");
            }
            else if (value instanceof List) {
                boolean isRecursion = false;
                if (value == list) {
                    isRecursion = true;
                }
                else if (link != null) {
                    ListLink p = link;
                    while (p != null) {
                        if (value == p.list) {
                            isRecursion = true;
                            break;
                        }
                        else {
                            p = p.parent;
                        }
                    }
                }
                if (isRecursion) {
                    buffer.append("[...]");
                }
                else {
                    ListLink p = new ListLink();
                    p.list = (List) value;
                    p.parent = link;
                    buffer.append(formatListString(p));
                }
            }
            else {
                buffer.append(value);
            }
            if (i < len - 1) {
                buffer.append(", ");
            }
        }
        buffer.append(']');
        return buffer.toString();
    }

    public String toString() {
        ListLink link = new ListLink();
        link.list = this;
        link.parent = null;
        return formatListString(link);
    }


}
