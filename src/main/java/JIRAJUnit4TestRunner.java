import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class JIRAJUnit4TestRunner extends BlockJUnit4ClassRunner {

    public JIRAJUnit4TestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        notifier.addListener(new JIRAJUnit4TestListener());
        notifier.fireTestRunStarted(getDescription());
        super.run(notifier);
    }
}
