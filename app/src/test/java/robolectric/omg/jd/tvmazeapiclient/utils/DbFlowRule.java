package robolectric.omg.jd.tvmazeapiclient.utils;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.robolectric.RuntimeEnvironment;

public class DbFlowRule implements TestRule {

    public static DbFlowRule create() {
        return new DbFlowRule();
    }

    private DbFlowRule() {
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                FlowManager.init(RuntimeEnvironment.application);
                try {
                    base.evaluate();
                } finally {
                    FlowManager.destroy();
                }
            }
        };
    }
}