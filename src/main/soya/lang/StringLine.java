package soya.lang;

import org.soya.runtime.MetaClassUtil;

public class StringLine extends SoyaString {

	public StringLine(String value) {
		super(value);
		this.setMetaClass(MetaClassUtil.StringLine);
	}

}
