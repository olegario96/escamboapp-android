package com.getmore.olegario.capuccino.model;

public class CapuccinoTestConfiguration {
    private String packageName;
    private String className;
    private String testFileName;
    private String expectedAssertion;

    public CapuccinoTestConfiguration(String packageName, String className, String testFileName, String expectedAssertion) {
        this.packageName = packageName;
        this.className = className;
        this.testFileName = testFileName;
        this.expectedAssertion = expectedAssertion;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
