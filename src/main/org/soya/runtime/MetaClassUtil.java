package org.soya.runtime;

import soya.lang.*;

import java.util.HashMap;

/**
 * @author: Jun Gong
 */
public class MetaClassUtil {
    public static soya.lang.MetaClass PObject = createMetaClass(EvalObject.class, null);
    public static MetaClass Null = createMetaClass(soya.lang.Null.class, PObject);
    public static MetaClass MetaClass = createMetaClass(soya.lang.MetaClass.class, PObject);
    public static MetaClass Method = createMetaClass(SoyaMethod.class, PObject);
    public static MetaClass Boolean = createMetaClass(SoayBoolean.class, PObject);
    public static MetaClass Int = createMetaClass(soya.lang.Int.class, PObject);
    public static MetaClass Float = createMetaClass(soya.lang.Float.class, PObject);
    public static MetaClass String = createMetaClass(SoyaString.class, PObject);
    public static MetaClass List = createMetaClass(SoyaList.class, PObject);
    public static MetaClass Map = createMetaClass(SoyaMap.class, PObject);
    public static MetaClass MethodArray = createMetaClass(MethodArray.class, List);
    public static MetaClass Shell = createMetaClass(SoyaShell.class, PObject);
    public static MetaClass Range = createMetaClass(Range.class, PObject);
    public static MetaClass Pattern = createMetaClass(soya.lang.Pattern.class, PObject);
    public static MetaClass URL = createMetaClass(SoyaURL.class, PObject);
    public static MetaClass FILE = createMetaClass(SoyaFile.class, PObject);
    public static MetaClass ClassPattern = createMetaClass(soya.lang.ClassPattern.class, PObject);
    public static MetaClass StringLine = createMetaClass(StringLine.class, String);
    public static MetaClass Script = createMetaClass(soya.lang.Script.class, PObject);


    private static java.util.Map<String, MetaClass> metaClassCache;


    public static MetaClass getMetaClassFromCache(String className) {
    	if (metaClassCache == null) {
            metaClassCache = new HashMap<String, MetaClass>();
    		return null;
    	}
    	if (metaClassCache.containsKey(className)) {
    		return metaClassCache.get(className);
    	}
        return null;
    }

    public static void addMetaClassToCache(MetaClass cls) {
        addMetaClassToCache(cls.getName(), cls);
    }
    
    public static void addMetaClassToCache(String className, MetaClass cls) {
    	if (metaClassCache == null) {
            metaClassCache = new HashMap<String, MetaClass>();
    	}
        metaClassCache.put(className, cls);
    }

    public static MetaClass createMetaClass(Class jcls) {
        return createMetaClass(jcls, null);
    }

    public static MetaClass createMetaClass(Class jcls, MetaClass superClass) {
    	MetaClass cls = getMetaClassFromCache(jcls.getName());
    	if (cls != null) return cls;
        cls = new MetaClass(jcls, superClass);
        addMetaClassToCache(jcls.getName(), cls);
        return cls;
    }

    public static MetaClass createMetaClass(String className, SoyaClassLoader classLoader) throws ClassNotFoundException {
        MetaClass cls = getMetaClassFromCache(className);
        if (cls != null) return cls;
        Class jcls = classLoader.loadClass(className);
        cls = new MetaClass(jcls);
        addMetaClassToCache(className, cls);
        return cls;
    }
}
