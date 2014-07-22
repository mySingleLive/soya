package soya.lang;

import org.soya.runtime.MetaClassUtil;
import org.soya.runtime.VarScope;

/**
 * @author: Jun Gong
 */
public abstract class Script extends EvalObject {

    protected VarScope bundle;

    public VarScope getBundle() {
        return bundle;
    }

    public Script() {
        super(MetaClassUtil.Script);
    }

    public Script(MetaClass metaClass) {
        super(metaClass);
    }

	public abstract Object run(Object[] args);

}

