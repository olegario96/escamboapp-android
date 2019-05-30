package com.getmore.olegario.capuccino.model;

import android.content.Context;

import java.io.IOException;
import java.io.OutputStreamWriter;

public final class CapuccinoTestWriter {
    private String[] defaultImports;
    private static final CapuccinoTestWriter INSTANCE = new CapuccinoTestWriter();

    private CapuccinoTestWriter() {
        this.defaultImports = new String[] {
            "import android.content.Context;",
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

        final String fileName = testConfiguration.getTestFileName();
        final String activityPackagePath = testConfiguration.getActivityPackagePath();
        final String activityClassName = testConfiguration.getActivityClassName();
        OutputStreamWriter outputStream = new OutputStreamWriter(   context.openFileOutput(fileName,
                                                                    Context.MODE_PRIVATE));

        this.writePackageAndDefaultImports( testConfiguration.getPackagePath(),
                                            activityPackagePath,
                                            activityClassName,
                                            outputStream);
    }

    public void writePackageAndDefaultImports(String packageName,
                                              String activityPackagePath,
                                              String activityClassName,
                                              OutputStreamWriter outputStream) throws IOException {

        outputStream.write(packagify(packageName));
        for (String stringImport: this.defaultImports) outputStream.write(stringImport);
        outputStream.write(importify(packageName, activityPackagePath, activityClassName));
    }

    private String importify(String packagePath, String activityPackagePath,
                             String activityClassName) {

        final String importPath = packagePath + "." + activityPackagePath + "." + activityClassName;
        return semicolonify("import " + semicolonify(importPath));
    }

    private String packagify(String packagePath) {
        return semicolonify("package " + semicolonify(packagePath));
    }

    private String semicolonify(String command) {
        return  command.substring(command.length() - 1).equals(";") ?
                command :
                command + ";";
    }

    public static CapuccinoTestWriter getInstance() {
        return INSTANCE;
    }
}
