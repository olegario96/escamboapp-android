package com.getmore.olegario.capuccino.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.getmore.olegario.capuccino.R;
import com.getmore.olegario.capuccino.exception.ActivityPackageException;
import com.getmore.olegario.capuccino.exception.ExpectedAssertionException;
import com.getmore.olegario.capuccino.exception.TestFileNameException;
import com.getmore.olegario.capuccino.model.CapuccinoEventLogger;
import com.getmore.olegario.capuccino.model.CapuccinoTestConfiguration;
import com.getmore.olegario.capuccino.model.CapuccinoTestWriter;

import java.io.IOException;

public class LauncherAppActivity extends AppCompatActivity {
    protected final CapuccinoTestConfiguration capuccinoTestConfiguration = new CapuccinoTestConfiguration();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        final String packageName = this.capuccinoTestConfiguration.getPackagePath();
        final String activityPackagePath = this.capuccinoTestConfiguration.getActivityPackagePath();
        final String className = this.capuccinoTestConfiguration.getActivityClassName();

        if (this.capuccinoTestConfiguration.getPackagePath() == null ||
            this.capuccinoTestConfiguration.getActivityClassName() == null) {

            final String errMsg = getString(R.string.root_package_exception_message);
            throw new ActivityPackageException(errMsg);
        }

        if (this.capuccinoTestConfiguration.getActivityPackagePath() == null) {
            final String errMsg = getString(R.string.package_for_activity_exception_message);
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

        final String componentPath = packageName + "." + activityPackagePath + "." + className;
        intent.setComponent(new ComponentName(packageName, componentPath));
        startActivity(intent);
    }

    public void setPackagePath(String packagePath) {
        this.capuccinoTestConfiguration.setPackagePath(packagePath);
    }

    public void setActiviyPackagePath(String activityPackagePath) {
        this.capuccinoTestConfiguration.setActivityPackagePath(activityPackagePath);
    }

    public void setClassName(String className) {
        this.capuccinoTestConfiguration.setActivityClassName(className);
    }

    public void setTestFileName(String testFileName) {
        this.capuccinoTestConfiguration.setTestFileName(testFileName);
    }

    public void setExpectedAssertion(String expectedAssertion) {
        this.capuccinoTestConfiguration.setExpectedAssertion(expectedAssertion);
    }

    @Override
    protected void onDestroy() {
        CapuccinoEventLogger capuccinoEventLogger = CapuccinoEventLogger.getInstance();
        CapuccinoTestWriter capuccinoTestWriter = CapuccinoTestWriter.getInstance();
        try {
            capuccinoTestWriter.writeTest(this.capuccinoTestConfiguration, capuccinoEventLogger, getApplicationContext());
        } catch (IOException e) {
            Log.e(">>>>CAPUCCINO ERROR", getString(R.string.error_while_writing_test_file));
            Log.e(">>>>CAPUCCINO ERROR", getString(R.string.printing_stack_trace));
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
