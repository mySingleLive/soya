package soya.lang;

import org.soya.runtime.InvokeUtil;
import org.soya.runtime.VarScope;

import java.io.Serializable;

/**
 * @author: Jun Gong
 */
public abstract class Closure extends EvalObject implements Caller, Serializable {
	public VarScope externalVariables;
    public boolean callOnAssign = false;
    private Object object;
    private int numberOfParameters = 0;
//	private Class[] parameterTypes;
	
	public Closure(Object object) {
		this(object, null, false);
	}
	
	public Closure(Object object, VarScope externalVariables, boolean callOnAssign) {
		this.object = object;
		this.externalVariables = externalVariables;
        this.callOnAssign = callOnAssign;
	}

    public Closure() {
    }

    @Override
    public boolean isMatch(Object obj) throws Throwable {
        if (numberOfParameters > 1) {
            return false;
        }
        Object ret = call(obj);
        if (ret instanceof Boolean) {
            return ((Boolean) ret).booleanValue();
        }
        return !InvokeUtil.isNull(ret);
    }

    public Object getObject() {
        return object;
    }

    public Object call() throws Throwable {
		return call(new Object[] {});
	}
	
	public Object call(Object it) throws Throwable {
        return this.invokeMethod("doCall", makeArgsArray(object, it, new Object[] {}));
	}
	
	public Object call(Object...args) throws Throwable {
        return this.invokeMethod("doCall", makeArgsArray(object, Null.NULL, args));
	}
	
	public Object apply(Object caller, Object...args) throws Throwable {
		try {
            return this.invokeMethod("doCall", makeArgsArray(caller, null, args));
		} catch (Exception e) {
			throw e;
		}
	}

    public Object[] makeArgsArray(Object outerObject, Object it, Object[] args) {
        Object[] newArgs = new Object[Math.max(args.length + 1, 2)];
        newArgs[0] = outerObject;
        if (args.length > 0) {
            newArgs[1] = args[0];
        }
        else {
            newArgs[1] = it;
        }
        for (int i = 1; i < args.length; i++) {
            newArgs[i + 1] = args[i];
        }
        return newArgs;
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }

    public void setNumberOfParameters(int numberOfParameters) {
        this.numberOfParameters = numberOfParameters;
    }
}
