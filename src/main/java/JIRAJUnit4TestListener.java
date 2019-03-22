import com.epam.jira.core.TestResultProcessor;
import constant.FileConstants;
import constant.JIRATestResult;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import utils.JIRAAttachment;

import java.io.File;

import static java.util.concurrent.TimeUnit.*;

public class JIRAJUnit4TestListener extends RunListener {
    private long testStartTime;

    @Override
    public void testRunFinished(Result result) throws Exception {
        super.testRunFinished(result);
        TestResultProcessor.saveResults();
    }

    @Override
    public void testStarted(Description description) throws Exception {
        super.testStarted(description);
        init(description);
    }

    @Override
    public void testFinished(Description description) throws Exception {
        super.testFinished(description);
        addAttachment(description);
        TestResultProcessor.setTime(getTestDuration());
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        super.testFailure(failure);
        TestResultProcessor.setStatus(JIRATestResult.FAILED.toString());
        TestResultProcessor.addException(failure.getException());
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        super.testIgnored(description);
        init(description);
        TestResultProcessor.setStatus(JIRATestResult.UNTESTED.toString());
        TestResultProcessor.addToSummary("Canononical test method name: " + description.getMethodName());
    }

    private String getJIRATestKeyFromFileName(File file) {
        return file.getName().substring(file.getName().lastIndexOf(FileConstants.JIRA_KEY_PREFIX.toString()) + 1,
                file.getName().lastIndexOf(FileConstants.FILE_EXTENSION_SEPARATOR.toString()));
    }

    private void init(Description description) {
        if (hasEnabledJIRATestKeyAnnotation(description)) {
            testStartTime = System.currentTimeMillis();
            TestResultProcessor.startJiraAnnotatedTest(description.getAnnotation(JIRATestKey.class).key());
            TestResultProcessor.setStatus(JIRATestResult.PASSED.toString());
        }
    }

    private String getTestDuration() {
        long duration = System.currentTimeMillis() - testStartTime;
        return MINUTES.convert(duration, MICROSECONDS) +
                "m " +
                SECONDS.convert(duration, MICROSECONDS) +
                "." +
                MILLISECONDS.convert(duration, MICROSECONDS) +
                "s ";
    }

    private void addAttachment(Description description) {
        for (File attachment : JIRAAttachment.getAttachmentList()) {
            String JIRATestKey = getJIRATestKeyFromFileName(attachment);
            if (hasEnabledJIRATestKeyAnnotation(description) && description.getAnnotation(JIRATestKey.class).key().equals(JIRATestKey)) {
                TestResultProcessor.addAttachment(attachment);
            }
        }
    }

    private boolean hasEnabledJIRATestKeyAnnotation(Description description) {
        return description.getAnnotations().toString().contains(JIRATestKey.class.getCanonicalName()) &&
                !description.getAnnotation(JIRATestKey.class).disabled();
    }
}