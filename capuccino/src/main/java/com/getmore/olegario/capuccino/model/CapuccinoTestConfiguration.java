package com.getmore.olegario.capuccino.model;

public class CapuccinoTestConfiguration {
    private String packagePath;
    private String activityPackagePath;
    private String activityClassName;
    private String testFileName;
    private String expectedAssertion;

    public CapuccinoTestConfiguration(String packagePath, String activityClassName,
                                      String activityPackagePath, String testFileName,
                                      String expectedAssertion) {

        this.packagePath = packagePath;
        this.activityPackagePath = activityPackagePath;
        this.activityClassName = activityClassName;
        this.testFileName = testFileName;
        this.expectedAssertion = expectedAssertion;
    }

    public CapuccinoTestConfiguration() {}

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getActivityClassName() {
        return activityClassName;
    }

    public void setActivityClassName(String activityClassName) {
        this.activityClassName = activityClassName;
    }

    public String getActivityPackagePath() { return activityPackagePath; }

    public void setActivityPackagePath(String activityPackagePath) {
        this.activityPackagePath = activityPackagePath;
    }

    public String getTestFileName() {
        return testFileName;
    }

    public void setTestFileName(String testFileName) {
        this.testFileName = testFileName;
    }

    public String getExpectedAssertion() {
        return expectedAssertion;
    }

    public void setExpectedAssertion(String expectedAssertion) {
        this.expectedAssertion = expectedAssertion;
    }
}
