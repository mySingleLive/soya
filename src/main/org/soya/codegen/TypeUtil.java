package org.soya.codegen;

import org.objectweb.asm.Type;
import org.soya.ast.ClassNode;

/**
 * @author: Jun Gong
 */
public class TypeUtil {
    public static final Type OBJECT_TYPE = getType(ClassNode.OBJECT);
    public static final Type BOOLEAN_TYPE = getType(ClassNode.BOOLEAN);
    public static final Type LIST_TYPE = getType(ClassNode.LIST);
    public static final Type P_NULL_TYPE = getType(ClassNode.PNULL);
    public static final Type P_INT_TYPE = getType(ClassNode.PINT);
    public static final Type P_FLOAT_TYPE = getType(ClassNode.PFLOAT);
    public static final Type P_TUPLE = getType(ClassNode.PTUPLE);
    public static final Type P_LIST = getType(ClassNode.PLIST);
    public static final Type P_MAP_TYPE = getType(ClassNode.PMAP);
    public static final Type P_COLLECTION_TYPE = getType(ClassNode.COLLECTION);
    public static final Type P_RANGE_TYPE = getType(ClassNode.RANGE);
    public static final Type P_REGEX_PATTERN_TYPE = getType(ClassNode.REGEX_PATTERN);
    public static final Type P_OBJECT_PATTERN_TYPE = getType(ClassNode.POBJECT_PATTERN);
    public static final Type P_CLASS_PATTERN_TYPE = getType(ClassNode.PCLASS_PATTERN);
    public static final Type P_OR_PATTERN_TYPE = getType(ClassNode.POR_PATTERN);
    public static final Type P_AND_PATTERN_TYPE = getType(ClassNode.PAND_PATTERN);
    public static final Type P_NOT_PATTERN_TYPE = getType(ClassNode.PNOT_PATTERN);
    public static final Type P_DATE = getType(ClassNode.PDATE);
    public static final Type P_URL = getType(ClassNode.PURL);
    public static final Type P_FILE = getType(ClassNode.PFILE);
    public static final Type P_SHELL_TYPE = getType(ClassNode.SHELL);
    
    public static Type getType(ClassNode classNode) {
        return Type.getType(BytecodeUtil.getTypeDescription(classNode));
    }

    public static Type getType(Class cls) {
        return Type.getType(BytecodeUtil.getTypeDescription(cls));
    }

}
