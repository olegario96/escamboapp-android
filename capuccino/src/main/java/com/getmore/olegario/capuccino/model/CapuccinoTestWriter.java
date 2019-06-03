package com.getmore.olegario.capuccino.model;

import android.content.Context;

import java.io.IOException;
import java.io.OutputStreamWriter;

public final class CapuccinoTestWriter {
    private String[] defaultImports;
    private final Stringify stringify = Stringify.getInstance();
    private final String TEST = "@Test";
    private final String PUBLIC = "public";
    private final String CLASS = "class";
    private final String ACTIVITY_TEST_RULE = "ActivityTestRule";
    private final CapuccinoEventTranslator translator = CapuccinoEventTranslator.getInstance();
    private static final CapuccinoTestWriter INSTANCE = new CapuccinoTestWriter();

    private CapuccinoTestWriter() {
        this.defaultImports = new String[] {
            "import android.content.Context;",
            "import android.support.test.espresso.Espresso;",
            "import android.support.test.espresso.assertion.ViewAssertions;",
            "import android.support.test.espresso.matcher.ViewMatchers;",
            "import android.support.test.InstrumentationRegistry;",
            "import android.support.test.rule.ActivityTestRule;",
            "import android.support.test.runner.AndroidJUnit4;",
            "import android.support.test.uiautomator.UiDevice;",
            "import android.support.test.uiautomator.UiObject;",
            "import android.support.test.uiautomator.UiObjectNotFoundException;",
            "import android.support.test.uiautomator.UiSelector;",
            "import org.junit.Rule;",
            "import org.junit.Test;",
            "import org.junit.runner.RunWith;"
        };
    }

    public void writeTest(CapuccinoTestConfiguration testConfiguration,
                          CapuccinoEventLogger eventLogger, Context context) throws IOException {
        eventLogger.print();
        final String fileName = this.stringify.concatenateWDot(testConfiguration.getTestFileName(), "java");
        final String activityPackagePath = testConfiguration.getActivityPackagePath();
        final String activityClassName = testConfiguration.getActivityClassName();
        OutputStreamWriter outputStream = new OutputStreamWriter(   context.openFileOutput(fileName,
                                                                    Context.MODE_PRIVATE));

        this.writePackageAndDefaultImports( testConfiguration.getPackagePath(),
                                            activityPackagePath,
                                            activityClassName,
                                            outputStream);

        this.writeTestClass(testConfiguration, outputStream);
        this.writeRule(outputStream, testConfiguration.getActivityClassName());
        this.writeTestSwitch(outputStream, testConfiguration.getExpectedAssertion(),eventLogger);
        outputStream.write(this.stringify.breaklinefy("}"));
        outputStream.close();
    }

    private void writePackageAndDefaultImports(String packageName,
                                              String activityPackagePath,
                                              String activityClassName,
                                              OutputStreamWriter outputStream) throws IOException {

        outputStream.write(this.stringify.packagify(packageName));
        for (String stringImport: this.defaultImports) outputStream.write(this.stringify.breaklinefy(stringImport));
        outputStream.write(this.stringify.importify(packageName, activityPackagePath, activityClassName));
    }

    private void writeTestClass(CapuccinoTestConfiguration testConfiguration,
                                OutputStreamWriter outputStream) throws IOException {

        final String RUN_WITH = "@RunWith(AndroidJUnit4.class)";
        final String className = testConfiguration.getTestFileName();
        outputStream.write(this.stringify.breaklinefy(RUN_WITH));
        this.writeClassDeclaration(className, outputStream);
    }

    private void writeClassDeclaration(String className,
                                       OutputStreamWriter outputStream) throws IOException {

        final String classDeclaration = this.stringify.concatenateWWhiteSpace(
                                            this.stringify.concatenateWWhiteSpace(
                                                this.stringify.concatenateWWhiteSpace(
                                                    this.PUBLIC, this.CLASS
                                                ), className
                                            ), "{"
                                        );

        outputStream.write(this.stringify.breaklinefy(classDeclaration));
    }

    private void writeRule(OutputStreamWriter outputStream, String className) throws IOException {
        final String RULE = "@Rule";
        final String activityRule = this.stringify.concatenateWWhiteSpace(this.PUBLIC, this.ACTIVITY_TEST_RULE);
        final String templateForClass = this.stringify.formatTemplate(className);
        final String activityRuleTyped = this.stringify.concatenateWWhiteSpace(activityRule, templateForClass);
        final String typedActivityVariableRule = this.stringify.concatenateWWhiteSpace(activityRuleTyped, "mActivityRule");
        final String variableRuleAssigned = this.stringify.concatenateWWhiteSpace(typedActivityVariableRule, "=");

        final String classType = this.stringify.concatenateWDot(className, this.CLASS);
        final String classParameter = this.stringify.formatParentheses(classType);
        final String newCommand = this.stringify.concatenateWWhiteSpace("new", this.ACTIVITY_TEST_RULE);
        final String newCommandType =   newCommand +
                                        this.stringify.formatTemplate("") +
                                        classParameter;

        outputStream.write(this.stringify.breaklinefy(RULE));
        outputStream.write(this.stringify.breaklinefy(variableRuleAssigned));
        outputStream.write(this.stringify.semicolonify(newCommandType));
    }

    private void writeTestSwitch(OutputStreamWriter outputStream,
                                 String expectedAssertion,
                                 CapuccinoEventLogger eventLogger) throws IOException {

        outputStream.write(this.stringify.breaklinefy(this.TEST));
        this.writeMethodDeclaration(outputStream);
        final String instanciateUiDevice = "UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());";
        outputStream.write(this.stringify.breaklinefy(instanciateUiDevice));
        String[] commands = this.translator.translateCapuccinoLogger(eventLogger);
        this.writeCommandsWithDelay(outputStream, commands);
        outputStream.write(this.stringify.breaklinefy(expectedAssertion));
        outputStream.write(this.stringify.breaklinefy("}"));
    }

    private void writeMethodDeclaration(OutputStreamWriter outputStream) throws IOException {
        final String methodTyped = this.stringify.concatenateWWhiteSpace(
                                        this.stringify.concatenateWWhiteSpace(
                                            this.PUBLIC, "void"
                                        ), "capuccinoAutomatedTest"
                                    );

        final String methodSigned = methodTyped + this.stringify.formatParentheses("");
        final String methodWithExceptions = this.stringify.concatenateWWhiteSpace(
                                                this.stringify.concatenateWWhiteSpace(
                                                    methodSigned,
                                                    "throws InterruptedException, UiObjectNotFoundException"
                                                ), "{"
                                            );

        outputStream.write(this.stringify.breaklinefy(methodWithExceptions));
    }

    private void writeCommandsWithDelay(OutputStreamWriter outputStream,
                                       String[] commands) throws IOException {

        for (String command: commands) {
            outputStream.write(this.stringify.breaklinefy(command));
            outputStream.write(this.stringify.breaklinefy("Thread.sleep(1000);"));
        }
    }

    public static CapuccinoTestWriter getInstance() {
        return INSTANCE;
    }
}
