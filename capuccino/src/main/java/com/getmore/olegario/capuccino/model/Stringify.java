package com.getmore.olegario.capuccino.model;

public final class Stringify {
    private final String IMPORT = "import";
    private final String PACKAGE = "package";
    private final String SEMICOLON = ";";
    private final String LINE_BREAKER = System.lineSeparator();
    private static final Stringify INSTANCE = new Stringify();

    private Stringify(){}

    public String semicolonify(String command) {
        return  command.substring(command.length() - 2).equals(";") ?
                breaklinefy(command) :
                breaklinefy(command + this.SEMICOLON);
    }

    public String breaklinefy(String command) {
        return command.substring(command.length() - 1).equals(this.LINE_BREAKER) ?
                command :
                command + this.LINE_BREAKER;
    }

    public String importify(String packagePath, String activityPackagePath,
                             String activityClassName) {

        final String importPath = this.concatenateWDot(
                                    this.concatenateWDot(packagePath, activityPackagePath),
                                    activityClassName);

        return  semicolonify(this.concatenateWWhiteSpace(this.IMPORT, importPath));
    }

    public String packagify(String packagePath) {
        return semicolonify(this.concatenateWWhiteSpace(this.PACKAGE, packagePath));
    }

    public String concatenateWWhiteSpace(String s1, String s2) {
        return s1 + " " + s2;
    }

    public String concatenateWDot(String s1, String s2) {
        return s1 + "." + s2;
    }

    public String formatTemplate(String s1) {
        return "<" + s1 + ">";
    }

    public String formatParentheses(String s1) {
        return "(" + s1 + ")";
    }

    public static Stringify getInstance() {
        return INSTANCE;
    }
}
