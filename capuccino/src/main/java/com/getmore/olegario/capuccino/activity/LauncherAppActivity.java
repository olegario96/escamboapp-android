package com.getmore.olegario.capuccino.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.getmore.olegario.capuccino.R;
import com.getmore.olegario.capuccino.exception.ActivityPackageException;
import com.getmore.olegario.capuccino.exception.ExpectedAssertionException;
import com.getmore.olegario.capuccino.exception.TestFileNameException;
import com.getmore.olegario.capuccino.model.CapuccinoEventLogger;
import com.getmore.olegario.capuccino.model.CapuccinoTestConfiguration;
import com.getmore.olegario.capuccino.model.CapuccinoTestWriter;

public class LauncherAppActivity extends AppCompatActivity {
    private CapuccinoTestConfiguration capuccinoTestConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        final String packageName = this.capuccinoTestConfiguration.getPackageName();
        final String className = this.capuccinoTestConfiguration.getClassName();

        if (this.capuccinoTestConfiguration.getPackageName() == null ||
            this.capuccinoTestConfiguration.getClassName() == null) {

            final String errMsg = getString(R.string.activity_package_exception_message);
            throw new ActivityPackageException(errMsg);
        }

        if (this.capuccinoTestConfiguration.getTestFileName() == null) {
            final String errMsg = getString(R.string.test_file_name_exception_message);
            throw new TestFileNameException(errMsg);
        }

        if (this.capuccinoTestConfiguration.getExpectedAssertion() == null) {
            final String errMsg = getString(R.string.expected_assertion_exception_message);
            throw new ExpectedAssertionException(errMsg);
        }

        intent.setComponent(new ComponentName(packageName, className));
        startActivity(intent);
    }

    public void setPackageName(String packageName) {
        this.capuccinoTestConfiguration.setPackageName(packageName);
    }

    public void setClassName(String className) {
        this.capuccinoTestConfiguration.setClassName(className);
    }

    public void setTestFileName(String testFileName) {
        this.capuccinoTestConfiguration.setTestFileName(testFileName);
    }

    public void setExpectedAssertion(String expectedAssertion) {
        this.capuccinoTestConfiguration.setExpectedAssertion(expectedAssertion);
    }

    @Override
    public void finish() {
        CapuccinoEventLogger capuccinoEventLogger = CapuccinoEventLogger.getInstance();
        CapuccinoTestWriter.writeTest(this.capuccinoTestConfiguration, capuccinoEventLogger);
        super.finish();
    }
}
