package org.soya.runtime;

import org.apache.log4j.Logger;
import org.soya.antlr.util.DateTimeUtil;
import org.soya.ast.BlockNode;
import org.soya.ast.ClassNode;
import org.soya.ast.CompilationUnit;
import org.soya.tools.ErrorList;
import org.soya.util.NameUtil;
import soya.lang.*;
import soya.util.SoyaAssertionOperator;

import java.io.File;
import java.io.PrintStream;
import java.lang.Float;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: Jun Gong
 */
public class SoyaShell {

    public SoyaShell() {
	}

	private static SoyaShell shellInstance = new SoyaShell();

	private CompilationUnit unit;
    private Map<String, MetaClass> declaredClasses = new HashMap<String, MetaClass>();
    private SoyaObjectWrapper objectWrapper = new SoyaObjectWrapper();

    public static SoyaShell getSharedShell() {
        return SoyaShell.shellInstance;
    }


    public Object eval(CompilationUnit unit) throws Exception {
    	
    	this.unit = unit;

        Thread thread = Thread.currentThread();

        thread.setContextClassLoader(unit.getClassLoader());

        BlockNode block = unit.getBlock();
        ErrorList errorList = unit.getSourceCode().getErrorList();
        if (errorList.hasErrors()) {
            errorList.interruptIfHasErrors();
            return Null.NULL;
        }
        ClassNode scriptNode = unit.getScriptClassNode();
        if (scriptNode != null) {
            return evalScript(scriptNode);
        }
        ClassNode mainNode = unit.getMainClassNode();
        if (mainNode != null) {
            return evalMain(mainNode);
        }
        return Null.NULL;
    }

    public Object evalMain(ClassNode mainClassNode) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Class clazz = unit.getClassLoader().defineClass(mainClassNode, unit);
        Method mainMethod = InvokeUtil.findJavaMethod(clazz, clazz.getMethods(), "main", new Object[] {}, true);
        if (mainMethod != null && mainMethod.getModifiers() == (Modifier.PUBLIC | Modifier.STATIC)) {
            mainMethod.invoke(clazz, new Object[0]);
        }
        return Null.NULL;
    }

    public Object evalScript(ClassNode scriptClassNode) throws Exception {
        Class clazz = unit.getClassLoader().defineClass(scriptClassNode, unit);
        Method method = clazz.getMethod("main", new Class[] {String[].class});
        method.invoke(clazz, new Object[] {unit.getSourceCode().getConfiguration().getArgs()});
        return null;
/*
        if (!scriptClassNode.isScript()) {
            return Null.NULL;
        }
        Constructor cons = clazz.getConstructor();
        Script script = (Script) cons.newInstance();
        script.setMetaClass(MetaClassUtil.createMetaClass(clazz));
        Object result = script.run(new Object[0]);
        return result;
*/
    }


    public static void doScriptMain(Class clazz, Object[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor cons = clazz.getConstructor();
        Script script = (Script) cons.newInstance();
        script.setMetaClass(MetaClassUtil.createMetaClass(clazz));
        Object result = script.run(args);
    }


    public Throwable unwrapException(Throwable th) {
/*
        if (th instanceof NoSuchPropertyException) {
            NoSuchPropertyException nspe = (NoSuchPropertyException) th;
            return new NoSuchPropertyException(nspe.getClazz(), nspe.getPropertyName());
        }
*/
        Throwable t = th;
        if (t.getCause() != null && t.getCause() != th) {
            t = t.getCause();
        }
        if (t != th && t instanceof SoyaRuntimeException) {
            return unwrapException(t);
        }
        return t;
    }

    public void throwAssertError(String snippet, Object message) {
        StringBuffer buffer = new StringBuffer();
        if (message != null && !message.toString().isEmpty()) {
            buffer.append(message.toString());
        }
        buffer.append('\n');
        buffer.append("Expression: ");
        buffer.append(snippet);
        throw new AssertionError(buffer.toString());
    }

    public void throwAssertError(String snippet, Object message, Object leftValue, Object rightValue, String leftSnippt, String rightSnippet) {
        StringBuffer buffer = new StringBuffer();
        if (message != null && !message.toString().isEmpty()) {
            buffer.append(message.toString());
        }
        buffer.append('\n');
        buffer.append('\n');
        buffer.append("Expression: ");
        buffer.append(snippet);

        boolean hasLeftSnippet = leftSnippt != null && !leftSnippt.isEmpty();
        boolean hasRightSnippet = rightSnippet != null && !rightSnippet.isEmpty();

        if (hasLeftSnippet || hasRightSnippet) {
            buffer.append("\n\nActual: ");
        }

        if (hasLeftSnippet) {
            buffer.append(' ');
            buffer.append(leftSnippt);
            buffer.append(" = ");
            buffer.append(leftValue);
        }
        if (hasRightSnippet) {
            if (hasLeftSnippet) {
                buffer.append(", ");
            }
            else {
                buffer.append(' ');
            }

            buffer.append(rightSnippet);
            buffer.append(" = ");
            buffer.append(rightValue);
        }
        buffer.append('\n');
        throw new AssertionError(buffer.toString());
    }


    public void assertTrue(String snippet, Object ret, Object message) {
        if (!(ret instanceof Boolean)) {
            return;
        }
        if (!((Boolean) ret).booleanValue()) {
            throwAssertError(snippet, message);
        }
    }

    public void assertOperatorExpression(String snippet, Object left, Object right,
                                         int operator, Object message,
                                         String leftSnippet) throws Throwable {
        assertOperatorExpression(snippet, left, right, operator, message, leftSnippet, null);
    }

    public void assertOperatorExpression(String snippet, Object left, Object right,
                                         int operator, Object message,
                                         String leftSnippet, String rightSnippet) throws Throwable {
        switch (operator) {
            case SoyaAssertionOperator.ASRT_GT:
                assertGreateThen(snippet, left, right, message, leftSnippet, rightSnippet);
                return;
            case SoyaAssertionOperator.ASRT_LT:
                assertLessThen(snippet, left, right, message, leftSnippet, rightSnippet);
                return;
            case SoyaAssertionOperator.ASRT_GE:
                assertGreateEqual(snippet, left, right, message, leftSnippet, rightSnippet);
                return;
            case SoyaAssertionOperator.ASRT_LE:
                assertLessEqual(snippet, left, right, message, leftSnippet, rightSnippet);
                return;
            case SoyaAssertionOperator.ASRT_EQ:
                assertEqual(snippet, left, right, message, leftSnippet, rightSnippet);
                return;
            case SoyaAssertionOperator.ASRT_NE:
                assertNotEqual(snippet, left, right, message, leftSnippet, rightSnippet);
                return;
            case SoyaAssertionOperator.ASRT_IS:
                assertIs(snippet, left, right, message, leftSnippet, rightSnippet);
                return;
            case SoyaAssertionOperator.ASRT_RM:
                assertRegexMatch(snippet, left, right, message, leftSnippet, rightSnippet);
                return;
            case SoyaAssertionOperator.ASRT_NR:
                assertRegexNotMatch(snippet, left, right, message, leftSnippet, rightSnippet);
                return;
            default:
                return;
        }
    }

    public void assertGreateThen(String snippet, Object left, Object right, Object message,
                               String leftSnippet, String rightSnippet) throws Throwable {
        if (!greateThen(left, right)) {
            throwAssertError(snippet, message, left, right, leftSnippet, rightSnippet);
        }
    }

    public void assertLessThen(String snippet, Object left, Object right, Object message,
                               String leftSnippet, String rightSnippet) throws Throwable {
        if (!lessThen(left, right)) {
            throwAssertError(snippet, message, left, right, leftSnippet, rightSnippet);
        }

    }

    public void assertGreateEqual(String snippet, Object left, Object right, Object message,
                               String leftSnippet, String rightSnippet) throws Throwable {
        if (!greateEqual(left, right)) {
            throwAssertError(snippet, message, left, right, leftSnippet, rightSnippet);
        }

    }

    public void assertLessEqual(String snippet, Object left, Object right, Object message,
                               String leftSnippet, String rightSnippet) throws Throwable {
        if (!lessEqual(left, right)) {
            throwAssertError(snippet, message, left, right, leftSnippet, rightSnippet);
        }

    }

    public void assertEqual(String snippet, Object left, Object right, Object message,
                               String leftSnippet, String rightSnippet) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (!equal(left, right)) {
            throwAssertError(snippet, message, left, right, leftSnippet, rightSnippet);
        }
    }

    public void assertNotEqual(String snippet, Object left, Object right, Object message,
                            String leftSnippet, String rightSnippet) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (equal(left, right)) {
            throwAssertError(snippet, message, left, right, leftSnippet, rightSnippet);
        }
    }


    public void assertIs(String snippet, Object left, Object right, Object message,
                               String leftSnippet, String rightSnippet) throws Throwable {
        if (!PatternUtil.isMatch(left, right)) {
            throwAssertError(snippet, message, left, right, leftSnippet, rightSnippet);
        }
    }

    public void assertRegexMatch(String snippet, Object left, Object right, Object message,
                         String leftSnippet, String rightSnippet) throws Throwable {
        if (!matchRegex(left, right)) {
            throwAssertError(snippet, message, left, right, leftSnippet, rightSnippet);
        }
    }

    public void assertRegexNotMatch(String snippet, Object left, Object right, Object message,
                                 String leftSnippet, String rightSnippet) throws Throwable {
        if (!notMatchRegex(left, right)) {
            throwAssertError(snippet, message, left, right, leftSnippet, rightSnippet);
        }
    }



    public void throwException(Object exception) throws Throwable {
        throw (Throwable) exception;
    }

/*
    public ObjectRange range(Object from, Object to, boolean includeFrom, boolean includeTo) {
        ObjectRange ret = new ObjectRange((Comparable) from, (Comparable) to, includeFrom, includeTo);
        return ret;
    }
*/

    public static Logger getLogger(Class clazz) {
        return Logger.getLogger(clazz);
    }

    public RegexPattern regexPattern(String regex, int flag) {
        return RegexPatternUtil.internRegexPattern(regex, flag);
    }

    public Object newInstance(Class clazz, Object[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        SoyaClassLoader classLoader = unit.getClassLoader();
        MetaClass metaClass = MetaClassUtil.createMetaClass(clazz);
        Object obj = metaClass.newInstance(args);
        return InvokeUtil.wrapObject(obj);
    }

    public SoyaDate newDateTime(int year, int month, int day, int hour, int minute, int second, String timeZone) {
        Date date = DateTimeUtil.createDateTime(year, month, day, hour, minute, second, timeZone);
        SoyaDate pdate = new SoyaDate(date, timeZone);
        return pdate;
    }

    public MetaClass getMetaClass(String className) throws ClassNotFoundException {
        MetaClass metaClass = MetaClassUtil.createMetaClass(className, unit.getClassLoader());
        return metaClass;
    }


    public int chooseContructor(Class clazz, Object[] args) throws ClassNotFoundException {
        Constructor[] constructors = clazz.getConstructors();
        Constructor constructor = InvokeUtil.findJavaConstructor(clazz, constructors, args);
        List constructorList = new ArrayList();
        for (Constructor cotr : constructors) {
            if (cotr.getParameterTypes().length == args.length || cotr.isVarArgs()) {
                constructorList.add(cotr);
            }
        }
        Comparator comp = new Comparator() {
            public int compare(Object arg0, Object arg1) {
                Constructor c1 = (Constructor) arg0;
                Constructor c2 = (Constructor) arg1;
                String paramTypesStr1 = Arrays.toString(c1.getParameterTypes());
                String paramTypesStr2 = Arrays.toString(c2.getParameterTypes());
                return paramTypesStr1.compareTo(paramTypesStr2);
            }
        };
        Collections.sort(constructorList, comp);

        int p = -1;
        for (int i = 0; i < constructorList.size(); i++) {
            if (constructor == constructorList.get(i)) {
                p = i;
                break;
            }
        }
        return p;
    }


    public Object invokePoJoMethod(String invokerClassName, Object obj, String methodName, Object[] argObjs) throws Throwable {
        try {
/*
            if (obj instanceof SoyaObject) {
                return InvokeUtil.invokePObjectMethod((SoyaObject) obj, methodName, argObjs);
            }
            else {
                return InvokeUtil.invokeJavaObjectMethod(obj, methodName, argObjs);
            }
*/
            return InvokeUtil.invokeJavaObjectMethod(obj, methodName, argObjs);
        } catch (Throwable t) {
            throw unwrapException(t);
        }
    }

    public Object invokeStaticDefaultInvokerMethod(String invokerClassName, String methodName, Object[] argObjs) throws Throwable {
        try {
            SoyaClassLoader classLoader = unit.getClassLoader();
            Class invokerClz = classLoader.loadClass(invokerClassName);
            return InvokeUtil.invokeStaticDefaultInvokerMethod(invokerClz, methodName, argObjs);
        } catch (Throwable t) {
            throw unwrapException(t);
        }
    }

    public Object invokeDefaultInvokerMethod(String invokerClassName, Object thisObject, String methodName, Object[] argObjs) throws Throwable {
        try {
            SoyaClassLoader classLoader = unit.getClassLoader();
            Class invokerClz = classLoader.loadClass(invokerClassName);
            return InvokeUtil.invokeDefaultInvokerMethod(invokerClz, thisObject, methodName, argObjs);
        } catch (Throwable t) {
            throw unwrapException(t);
        }
    }

    public Object invokeStaticMethod(String invokerClassName, String className, String methodName, Object[] argObjs) throws Throwable {
        SoyaClassLoader classLoader = unit.getClassLoader();
        Class invokerClz = classLoader.loadClass(invokerClassName);
        Class clz = classLoader.loadClass(className);
        return InvokeUtil.invokeStaticMethod(invokerClz, clz, methodName, argObjs);
    }

    public Object invokeSystemEvnMethod(String className, String methodName, Object[] argObjs) throws Throwable {
        try {
            SoyaClassLoader classLoader = unit.getClassLoader();
            Class clz = classLoader.loadClass(className);
            return InvokeUtil.invokeSystemEvnMethod(clz, methodName, argObjs);
        } catch (Throwable t) {
            throw unwrapException(t);
        }
    }

    public Class getObjectClazz(Object obj) {
        if (obj instanceof SoyaObject) {
            return ((SoyaObject) obj).getObjectClass();
        }
        return obj.getClass();
    }

    public Object starDot(String invokerClassName, Object obj, String methodName, Object[] args) throws Throwable {
        if (obj instanceof List) {
            List rets = new SoyaList();
            int len = ((List) obj).size();
            for (int i = 0; i < len; i++) {
                Object item = ((List) obj).get(i);
                if (item != null) {
                    Object ret = InvokeUtil.invokeJavaObjectMethod(item, methodName, args);
                    rets.add(ret);
                }
            }
            return rets;
        }
        return InvokeUtil.invokeJavaObjectMethod(obj, "starDot", new Object[] {methodName, args});
    }
    
    public Object getPropertyValue(String invokerClassName, Object obj, String propertyName) throws Throwable {
        try {
            if (obj instanceof EvalObject) {
                return ((EvalObject) obj).getPropertyValue(propertyName);
            }
            else {
                SoyaClassLoader classLoader = unit.getClassLoader();
                Class invokerClz = classLoader.loadClass(invokerClassName);
                return InvokeUtil.getJavaObjectPropertyValue(invokerClz, obj, propertyName);
            }
        } catch (Throwable th) {
            throw unwrapException(th);
        }
    }
    
    public void setPropertyValue(Object obj, String propertyName, Object[] args) throws Throwable {
        synchronized (obj) {
            if (obj instanceof EvalObject) {
                ((EvalObject) obj).setPropertyValue(propertyName, args);
            }
            else {
                InvokeUtil.setJavaObjectPropertyValue(obj, propertyName, args);
            }
        }
    }
    
    public Object getStaticValue(String invokerClassName, String className, String fieldName) throws Throwable {
        SoyaClassLoader classLoader = unit.getClassLoader();
        Class invokerClz = classLoader.loadClass(invokerClassName);
        Class clz = classLoader.loadClass(className);
        Field field = null;

    	try {
			field = clz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
		}
    	if (field != null) {
            if (clz.isAssignableFrom(invokerClz)) {
                field.setAccessible(true);
            }
    		Object value = field.get(null);
    		return InvokeUtil.wrapObject(value);
    	}
        else {
            try {
                return invokeStaticMethod(invokerClassName, className, fieldName, new Object[0]);
            } catch (Throwable t) {
                if (t instanceof NoSuchMethodException) {
                    try {
                        return invokeStaticMethod(invokerClassName, className, NameUtil.toGetterName(fieldName), new Object[0]);
                    } catch (Throwable t2) {
                        throw t2;
                    }
                }
                throw t;
            }
        }
    }

    public Object getVariable(VarScope varScope, String varFieldName, int level) throws NoSuchFieldException, IllegalAccessException {
        while (level-- > 0) {
            varScope = varScope.parent;
        }
        return varScope.getClass().getField(varFieldName).get(varScope);
    }

    public Object getScriptVariable(Script script, String varName) throws Exception {
        VarScope bundle = script.getBundle();
        if (bundle.contains(varName)) {
            return bundle.getVariable(varName);
        }
        return InvokeUtil.getJavaObjectPropertyValue(script.getClass(), script, varName);
    }

    public void setScriptVariable(Script script, String varName, Object value) throws Exception {
        VarScope bundle = script.getBundle();
        if (bundle.contains(varName)) {
            bundle.setVariable(varName, value);
        }
        else {
            InvokeUtil.setJavaObjectPropertyValue(script, varName, new Object[] {value});
        }
    }


    public Object positive(Object obj) throws Exception {
        if (obj instanceof Integer) {
            return obj;
        }
        else if (obj instanceof Int) {
            return obj;
        }
        else if (obj instanceof Float) {
            return obj;
        }
        else if (obj instanceof soya.lang.Float) {
            return obj;
        }
        else if (obj instanceof Double) {
            return obj;
        }
        else if (obj instanceof BigDecimal) {
            return obj;
        }
        throw new Exception("Positive operator cannot be applied to " + obj.getClass().getSimpleName());
    }

    public Object negative(Object obj) throws Exception {
        if (obj instanceof Integer) {
            return new Integer(-((Integer) obj).intValue());
        }
        else if (obj instanceof Int) {
            return new Int(-((Int) obj).getValue());
        }
        else if (obj instanceof Float) {
            return new Float(-((Float) obj).floatValue());
        }
        else if (obj instanceof soya.lang.Float) {
            return new soya.lang.Float(-((soya.lang.Float) obj).getValue());
        }
        else if (obj instanceof Double) {
            return new Double(-((Double) obj).doubleValue());
        }
        else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).multiply(new BigDecimal(-1));
        }
        throw new Exception("Nagative operator cannot be applied to " + obj.getClass().getSimpleName());
    }

    public boolean not(Object obj) throws Exception {
        if (obj instanceof Boolean) {
            return !((Boolean) obj).booleanValue();
        }
        return obj == null || obj == Null.NULL;
    }


    public boolean greateThen(Object left, Object right) throws Throwable {
        if (right instanceof SoyaFile) {
            ((SoyaFile) right).write(left, false);
        }
        else if (right instanceof File) {
            new SoyaFile((File) right).write(left, false);
        }
        return ((Boolean) InvokeUtil.invokeJavaObjectMethod(left, "greaterThan", new Object[] {right})).booleanValue();
    }

    public boolean lessThen(Object left, Object right) throws Throwable {
        if (left instanceof File) {
            new SoyaFile((File) left).write(right, false);
        }
        return ((Boolean) InvokeUtil.invokeJavaObjectMethod(left, "lessThan", new Object[] {right})).booleanValue();
    }

    public boolean equal(Object left, Object right) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (left == null || left == Null.NULL) {
            if (right == null || right == Null.NULL) {
                return true;
            }
            return false;
        }

        if (right == null || right == Null.NULL) {
            if (left == null || left == Null.NULL) {
                return true;
            }
            return false;
        }

        if (left == right) {
            return true;
        }

        left = InvokeUtil.wrapObject(left);
        right = InvokeUtil.wrapObject(right);
        return left.equals(right);
    }

    public boolean notEqual(Object left, Object right) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return !equal(left, right);
    }

    public boolean and(Object left, Object right) {
        if (InvokeUtil.isNull(left) || (left instanceof Boolean && !((Boolean) left).booleanValue())) {
            return false;
        }
        if (InvokeUtil.isNull(right) || (left instanceof Boolean && !((Boolean) right).booleanValue())) {
            return false;
        }
        return true;
    }

    public boolean or(Object left, Object right) {
        boolean leftRet = true, rightRet = true;
        if (InvokeUtil.isNull(left) || (left instanceof Boolean && !((Boolean) left).booleanValue())) {
            leftRet = false;
        }
        if (InvokeUtil.isNull(right) || (left instanceof Boolean && !((Boolean) right).booleanValue())) {
            rightRet =  false;
        }
        return leftRet || rightRet;
    }

    public Object xor(Object left, Object right) throws Throwable {
        if (left instanceof Boolean) {
            if (right instanceof Boolean) {
                return !left.equals(right);
            }
            return !((Boolean) left).booleanValue();
        }

        return InvokeUtil.invokeJavaObjectMethod(left, "xor", new Object[] {right});
    }

    public Object bitAnd(Object left, Object right) throws Throwable {
        return InvokeUtil.invokeJavaObjectMethod(left, "bitAnd", new Object[] {right});
    }

    public Object bitOr(Object left, Object right) throws Throwable {
        return InvokeUtil.invokeJavaObjectMethod(left, "bitOr", new Object[] {right});
    }

    public Object bitShiftLeft(Object left, Object right) throws Throwable {
        return InvokeUtil.invokeJavaObjectMethod(left, "bitShiftLeft", new Object[] {right});
    }

    public Object bitShiftRight(Object left, Object right) throws Throwable {
        return InvokeUtil.invokeJavaObjectMethod(left, "bitShiftRight", new Object[] {right});
    }


    public boolean greateEqual(Object left, Object right) throws Throwable {
        return ((Boolean) InvokeUtil.invokeJavaObjectMethod(left, "greaterEquals", new Object[] {right})).booleanValue();

    }

    public boolean lessEqual(Object left, Object right) throws Throwable {
        return ((Boolean) InvokeUtil.invokeJavaObjectMethod(left, "lessEquals", new Object[] {right})).booleanValue();
    }

    public boolean testCondition(Object obj) {
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        return obj != null && obj != Null.NULL;
    }

    public Object index(Object obj, Object[] args) throws Throwable {
        if (obj == null) {
            return null;
        }
        if (obj.getClass().isArray() && args.length >= 1) {
            return Array.get(obj, ((Int) args[0]).getValue());
        }
        return InvokeUtil.invokeJavaObjectMethod(obj, "get", args);
    }

    public Object setIndexValue(Object obj, Object[] args, Object value) throws Throwable {
        if (obj instanceof SoyaObject) {
            ((SoyaObject) obj).set(args, value);
        }
        else if (obj instanceof Object[]) {
//            new SoyaList((Object[]) obj).set(args, value);
        	for (Object index : args) {
        		if (index instanceof Int) {
        			((Object[]) obj)[((Int) index).getValue()] = value;
        		}
        		else if (index instanceof Integer) {
        			((Object[]) obj)[((Integer) index).intValue()] = value;
        		}
        	}
        }
        return value;
    }

    public Object shiftRight(Object left, Object right) throws Throwable {
        Object obj = left;
        if (right instanceof Writable) {
            ((Writable) right).write(obj);
            return obj;
        }
        else if (right instanceof String || right instanceof SoyaString) {
            StringBuffer buffer = new StringBuffer(right.toString());
            buffer.append(obj);
            return buffer.toString();
        }
        else if (right instanceof PrintStream) {
            ((PrintStream) right).print(obj);
            return obj;
        }
        /*
        else if (right instanceof List) {
            ((List) right).add(obj);
        }
        else if (right instanceof )
        else {
            Method method = right.getClass().getMethod("append", new Class[] {Object.class});
            if (method != null) {
                method.invoke(right, new Object[] {obj});
            }
            else {
                method = right.getClass().getMethod("append", new Class[] {String.class});
                if (method != null) {
                    method.invoke(right, new Object[] {obj.toString()});
                }
            }
        }
*/
        return InvokeUtil.invokeJavaObjectMethod(right, "shiftLeft", new Object[]{left});

    }

    public Object shiftLeft(Object left, Object right) throws Throwable {
        Object obj = InvokeUtil.wrapObject(right);
        if (left instanceof Writable) {
            ((Writable) left).write(obj);
            return obj;
        }
        else if (left instanceof String || left instanceof SoyaString) {
            StringBuffer buffer = new StringBuffer(left.toString());
            buffer.append(obj);
            return buffer.toString();
        }
        else if (left instanceof PrintStream) {
            ((PrintStream) left).print(obj);
            return obj;
        }
/*
        else {
            Method method = left.getClass().getMethod("append", new Class[] {Object.class});
            if (method != null) {
                method.invoke(left, new Object[] {obj});
            }
            else {
                method = left.getClass().getMethod("append", new Class[] {String.class});
                if (method != null) {
                    method.invoke(left, new Object[] {obj.toString()});
                }
            }
        }
*/
        return InvokeUtil.invokeJavaObjectMethod(left, "shiftLeft", new Object[] {right});
    }

    public Object reference(String invokerClassName, Object obj, Object ref) {
        if (ref instanceof String || ref instanceof SoyaString) {
            return getReference(obj, ref.toString());
        }
        return null;
    }

    public Object newAliasPattern(Object obj, String alias, VarScope varScope) {
        return new ObjectPattern(obj, alias, varScope);
    }

    public Object getReference(Object obj, String name)  {
        MetaClass metaClass = MetaClassUtil.createMetaClass(obj.getClass());
        MethodArray methodArray = metaClass.findMethod(name);
        methodArray.setObject(obj);
        return methodArray;
    }


    public boolean isMatch(Object obj, Object pattern) throws Throwable {
        if (obj == pattern) {
            return true;
        }
        if (pattern.getClass().isAssignableFrom(obj.getClass()) &&
                ((Boolean) equal(obj, pattern)).booleanValue()) {
            return true;
        }
        if (!(obj.getClass().isAssignableFrom(boolean.class) || obj instanceof Boolean) &&
                (pattern.getClass().isAssignableFrom(boolean.class) || pattern instanceof Boolean)) {
            return ((Boolean) pattern).booleanValue();
        }
        if (pattern instanceof Pattern) {
            return ((Pattern) pattern).isMatch(obj);
        }
        else {
            return pattern.equals(obj);
        }
    }

    public boolean matchRegex(Object source, Object regexPattern) {
        String str = ((CharSequence) source).toString();
        if (regexPattern instanceof RegexPattern) {
            return ((RegexPattern) regexPattern).test(str);
        }
        if (regexPattern instanceof CharSequence) {
            return RegexPatternUtil.internRegexPattern(regexPattern.toString(), 0).test(str);
        }
        return false;
    }

    public boolean notMatchRegex(Object source, Object regexPattern) {
        return !matchRegex(source, regexPattern);
    }

    public boolean in(Object obj, Object range) {
        if (obj instanceof Comparable && range instanceof Range) {
            return in((Comparable) obj, (Range) range);
        }
        return false;
    }

    public boolean in(Comparable obj, Range range) {
        return in(obj, range.getFrom(), range.getTo(), range.isIncludeFrom(), range.isIncludeTo());
    }

    public boolean in(Object obj, Object from, Object to, boolean includeFrom, boolean includeTo) {
        if (obj instanceof Comparable && from instanceof Comparable && to instanceof Comparable) {
            return in((Comparable) obj, (Comparable) from, (Comparable) to, includeFrom, includeTo);
        }
        return false;
    }

    public boolean in(Comparable obj, Comparable from, Comparable to, boolean includeFrom, boolean includeTo) {
        int fromResult = obj.compareTo(from);
        int toResult = obj.compareTo(to);
        if (includeFrom && includeTo) {
            return fromResult >= 0 && toResult <= 0;
        }
        else if (includeFrom && !includeTo) {
            return fromResult >= 0 && toResult < 0;
        }
        else if (!includeFrom && includeTo) {
            return fromResult > 0 && toResult <= 0;
        }
        else if (!includeFrom && !includeTo) {
            return fromResult > 0 && toResult < 0;
        }
        return false;
    }

    public boolean instanceOf(Object left, Object right) throws Throwable {
        if (right instanceof ClassPattern) {
            return ((ClassPattern) right).isCase(left);
        }
        if (right instanceof Class) {
            return new ClassPattern((Class) right).isCase(left);
        }
        if (right instanceof List) {
            for (Object item : (List) right) {
                if (instanceOf(left, item)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public Object plus(Object left, Object right) throws Throwable {
        if (left instanceof String || left instanceof SoyaString) {
            return left.toString() + right.toString();
        }
        left = InvokeUtil.wrapObject(left);
        right = InvokeUtil.wrapObject(right);
        return InvokeUtil.invokeJavaObjectMethod(left, "plus", new Object[] {right});
    }

    public Object minus(Object left, Object right) throws Throwable {
        left = InvokeUtil.wrapObject(left);
        right = InvokeUtil.wrapObject(right);
        return InvokeUtil.invokeJavaObjectMethod(left, "minus", new Object[] {right});
    }

    public Object multi(Object left, Object right) throws Throwable {
        left = InvokeUtil.wrapObject(left);
        right = InvokeUtil.wrapObject(right);
        if ((left instanceof Int) && right instanceof Int) {
            Int ret = new Int(((Int) left).getValue() * ((Int) right).getValue());
            return ret;
        }
        return InvokeUtil.invokeJavaObjectMethod(left, "multi", new Object[] {right});
    }

    public Object div(Object left, Object right) throws Throwable {
        left = InvokeUtil.wrapObject(left);
        right = InvokeUtil.wrapObject(right);
        return InvokeUtil.invokeJavaObjectMethod(left, "div", new Object[] {right});
    }
    
    public Object mod(Object left, Object right) {
        left = InvokeUtil.wrapObject(left);
        right = InvokeUtil.wrapObject(right);
        if (left instanceof Int && right instanceof Int) {
            return new Int(((Int) left).getValue() % ((Int) right).getValue());
        }
    	return Null.NULL;
    }

    public Object pow(Object left, Object right) throws Throwable {
        left = InvokeUtil.wrapObject(left);
        right = InvokeUtil.wrapObject(right);
        return InvokeUtil.invokeJavaObjectMethod(left, "pow", new Object[] {right});
    }

}
