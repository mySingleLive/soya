package org.soya.codegen;

import org.objectweb.asm.commons.Method;
import org.soya.ast.ClassNode;
import org.soya.ast.ParameterNode;

/**
 * @author: Jun Gong
 */
public class MethodUtil {
	
	protected static final Method TO_STRING_COMMAND = MethodUtil.getMethod("java.lang.String toString ()");
	protected static final Method STRING_BUFFER_APPEND_COMMAND = MethodUtil.getMethod("java.lang.StringBuffer append (java.lang.Object)");

    protected static final Method PLUS_COMMAND = getMethod("java.lang.Object plus (java.lang.Object, java.lang.Object)");
    protected static final Method MINUS_COMMAND = getMethod("java.lang.Object minus (java.lang.Object, java.lang.Object)");
    protected static final Method MULTI_COMMAND = getMethod("java.lang.Object multi (java.lang.Object, java.lang.Object)");
    protected static final Method DIV_COMMAND = getMethod("java.lang.Object div (java.lang.Object, java.lang.Object)");
    protected static final Method MOD_COMMAND = getMethod("java.lang.Object mod (java.lang.Object, java.lang.Object)");
    protected static final Method POW_COMMAND = getMethod("java.lang.Object pow (java.lang.Object, java.lang.Object)");

    protected static final Method NEGAVITE = getMethod("java.lang.Object negative (java.lang.Object)");
    protected static final Method POSITIVE = getMethod("java.lang.Object positive (java.lang.Object)");
    protected static final Method NOT_COMMAND = getMethod("boolean not (java.lang.Object)");
    protected static final Method AND_COMMAND = getMethod("boolean and (java.lang.Object, java.lang.Object)");
    protected static final Method OR_COMMAND = getMethod("boolean or (java.lang.Object, java.lang.Object)");

    protected static final Method BIT_AND_COMMAND = getMethod("java.lang.Object bitAnd (java.lang.Object, java.lang.Object)");
    protected static final Method BIT_OR_COMMAND = getMethod("java.lang.Object bitOr (java.lang.Object, java.lang.Object)");
    protected static final Method BIT_SL_COMMAND = getMethod("java.lang.Object bitShiftLeft (java.lang.Object, java.lang.Object)");
    protected static final Method BIT_SR_COMMAND = getMethod("java.lang.Object bitShiftRight (java.lang.Object, java.lang.Object)");

    protected static final Method XOR_COMMAND = getMethod("java.lang.Object xor (java.lang.Object, java.lang.Object)");

    protected static final Method GT_COMMAND = getMethod("boolean greateThen (java.lang.Object, java.lang.Object)");
    protected static final Method LT_COMMAND = getMethod("boolean lessThen (java.lang.Object, java.lang.Object)");
    protected static final Method GE_COMMAND = getMethod("boolean greateEqual (java.lang.Object, java.lang.Object)");
    protected static final Method LE_COMMAND = getMethod("boolean lessEqual (java.lang.Object, java.lang.Object)");
    protected static final Method EQ_COMMAND = getMethod("boolean equal (java.lang.Object, java.lang.Object)");
    protected static final Method NE_COMMAND = getMethod("boolean notEqual (java.lang.Object, java.lang.Object)");
    protected static final Method SR_COMMAND = getMethod("java.lang.Object shiftRight (java.lang.Object, java.lang.Object)");
    protected static final Method SL_COMMAND = getMethod("java.lang.Object shiftLeft (java.lang.Object, java.lang.Object)");
    protected static final Method INDEX_COMMAND = getMethod("java.lang.Object index (java.lang.Object, java.lang.Object[])");
    protected static final Method SET_INDEX_COMMAND = getMethod("java.lang.Object setIndexValue (java.lang.Object, java.lang.Object[], java.lang.Object)");
    protected static final Method THROW_COMMAND = getMethod("void throwException (java.lang.Object)");

    protected static final Method ASSERT_TRUE = getMethod("void assertTrue (java.lang.String, java.lang.Object, java.lang.Object)");
    protected static final Method ASSERT_OPERATOR = getMethod("void assertOperatorExpression (java.lang.String, java.lang.Object, java.lang.Object, int, java.lang.Object, java.lang.String, java.lang.String)");
    protected static final Method ASSERT_OPERATOR_WITHOUT_RIGHT = getMethod("void assertOperatorExpression (java.lang.String, java.lang.Object, java.lang.Object, int, java.lang.Object, java.lang.String)");

    protected static final Method LOGGER = getMethod("java.util.logging.Logger logger (java.lang.String)");
    protected static final Method REGEX_PATTERN = getMethod("soya.lang.RegexPattern regexPattern (java.lang.String, int)");

    protected static final Method GET_OBJECT_CLASS = getMethod("java.lang.Class getObjectClazz (java.lang.Object)");
    protected static final Method CHOOSE_CONSTRUCOTR = getMethod("int chooseContructor (java.lang.String, java.lang.Object[])");

    protected static final Method INSTANCEOF_COMMAND = getMethod("boolean instanceOf (java.lang.Object, java.lang.Object)");

    protected static final Method IS_MATCH_COMMAND = getMethod("boolean isMatch (java.lang.Object, java.lang.Object)");
    protected static final Method MATCH_COMMAND = getMethod("java.lang.Object match (java.lang.Object, java.lang.Object[])");
    protected static final Method MATCH_REGEX_COMMAND = getMethod("boolean matchRegex (java.lang.Object, java.lang.Object)");
    protected static final Method NOT_MATCH_REGEX_COMMAND = getMethod("boolean notMatchRegex (java.lang.Object, java.lang.Object)");

    protected static final Method GET_METACLASS = getMethod("soya.lang.MetaClass getMetaClass (java.lang.String)");

    protected static final Method CREATE_TIME_COMMAND = getMethod("soya.lang.TimeDuration createTime (int, int, int)");
    protected static final Method CREATE_DATE_TIME_COMMAND =  getMethod("java.util.Date createDateTime (int, int, int, int, int, int, java.lang.String)");
    protected static final Method NEW_DATE_TIME_COMMAND =  getMethod("soya.lang.SoyaDate newDateTime (int, int, int, int, int, int, java.lang.String)");

    protected static final Method TEST_CONDITION_COMMAND = getMethod("boolean testCondition (java.lang.Object)");
    protected static final Method NEW_INSTANCE_COMMAND = getMethod("java.lang.Object newInstance (java.lang.Class, java.lang.Object[])");
    protected static final Method INVOKE_METHOD_COMMAND = getMethod("java.lang.Object invokePoJoMethod (java.lang.String, java.lang.Object, java.lang.String, java.lang.Object[])");
    protected static final Method INVOKE_STATIC_DEFAULT_INVOKER_METHOD_COMMAND = getMethod("java.lang.Object invokeStaticDefaultInvokerMethod (java.lang.String, java.lang.String, java.lang.Object[])");
    protected static final Method INVOKE_DEFAULT_INVOKER_METHOD_COMMAND = getMethod("java.lang.Object invokeDefaultInvokerMethod (java.lang.String, java.lang.Object, java.lang.String, java.lang.Object[])");
    protected static final Method INVOKE_STATIC_METHOD_COMMAND = getMethod("java.lang.Object invokeStaticMethod (java.lang.String, java.lang.String, java.lang.String, java.lang.Object[])");
    protected static final Method INVOKE_SYS_EVN_METHOD_COMMAND = getMethod("java.lang.Object invokeSystemEvnMethod (java.lang.String, java.lang.String, java.lang.Object[])");

    protected static final Method STAR_DOT = getMethod("java.lang.Object starDot (java.lang.String, java.lang.Object, java.lang.String, java.lang.Object[])");
    protected static final Method GET_PROPERTY_VALUE = getMethod("java.lang.Object getPropertyValue (java.lang.String, java.lang.Object, java.lang.String)");
    protected static final Method SET_PROPERTY_VALUE = getMethod("void setPropertyValue (java.lang.Object, java.lang.String, java.lang.Object[])");
    protected static final Method GET_STATIC_VALUE = getMethod("java.lang.Object getStaticValue (java.lang.String, java.lang.String, java.lang.String)");
    protected static final Method GET_REFERENCE = getMethod("java.lang.Object reference (java.lang.String, java.lang.Object, java.lang.Object)");

    protected static final Method MAP_PUT_COMMAND = getMethod("java.lang.Object put (java.lang.Object, java.lang.Object)");

    
    public static Method getMethod(String methodName) {
        return Method.getMethod(methodName);
    }

    public static Method getStaticInitializerMethod() {
        return Method.getMethod(BytecodeUtil.getStaticInitializerName());
    }

    public static Method getConstructorMethod() {
        return Method.getMethod(BytecodeUtil.getConstructorName());
    }

    public static Method getConstructorMethod(ClassNode paramType) {
        return Method.getMethod(BytecodeUtil.getConstructorName(paramType));
    }

    public static Method getConstructorMethod(ClassNode[] paramTypes) {
        return Method.getMethod(BytecodeUtil.getConstructorName(paramTypes));
    }

    public static Method getConstructorMethod(Class paramType) {
        return Method.getMethod(BytecodeUtil.getConstructorName(paramType));
    }

    public static Method getConstructorMethod(Class[] paramTypes) {
        return Method.getMethod(BytecodeUtil.getConstructorName(paramTypes));
    }

    public static Method getConstructorMethod(ParameterNode param) {
        return Method.getMethod(BytecodeUtil.getConstructorName(param.getType()));
    }

    public static Method getConstructorMethod(ParameterNode[] params) {
        return Method.getMethod(BytecodeUtil.getConstructorName(params));
    }
}
