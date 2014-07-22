package soya;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * @author: Jun Gong
 */
public class SoyaTestRunner extends BlockJUnit4ClassRunner {

	public SoyaTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

}
