package com.getmore.olegario.capuccino;

import com.getmore.olegario.capuccino.model.CapuccinoTestConfiguration;

import org.junit.Test;

public class CapuccinoTestConfigurationTest {
    private final CapuccinoTestConfiguration testConfiguration = new CapuccinoTestConfiguration(
            "com.example.olegario.escamboapp",
            ".activity.HomeWithoutAuthentication",
            "CapuccinoTest",
            "withParent(withId(R.id.toolbar)"
    );

    @Test
    public void getPackageName() {
        final boolean result = testConfiguration.getPackagePath().equals("com.example.olegario.escamboapp");
        assert result;
    }

    @Test
    public void getClassName() {
        final boolean result = testConfiguration.getActivityClassName().equals(".activity.HomeWithoutAuthentication");
        assert result;
    }

    @Test
    public void getTestFileName() {
        final boolean result = testConfiguration.getTestFileName().equals("CapuccinoTest");
        assert result;
    }

    @Test
    public void getExpectedAssertion() {
        final boolean result = testConfiguration.getExpectedAssertion().equals("withParent(withId(R.id.toolbar)");
        assert result;
    }

    @Test
    public void setPackageName() {
        testConfiguration.setPackagePath("com.getmore.olegario.escamboapp");
        final boolean result = testConfiguration.getPackagePath().equals("com.getmore.olegario.escamboapp");
        assert result;
    }

    @Test
    public void setClassName() {
        testConfiguration.setActivityClassName(".activity.MainActivity");
        final boolean result = testConfiguration.getActivityClassName().equals(".activity.MainActivity");
        assert result;
    }

    @Test
    public void setTestFileName() {
        testConfiguration.setTestFileName("EspressoTest");
        final boolean result = testConfiguration.getTestFileName().equals("EspressoTest");        assert result;
        assert result;
    }

    @Test
    public void setExpectedAssertion() {
        testConfiguration.setExpectedAssertion("withParent(withId(R.id.toolbarAuth)");
        final boolean result = testConfiguration.getExpectedAssertion().equals("withParent(withId(R.id.toolbarAuth)");
        assert result;
    }
}
