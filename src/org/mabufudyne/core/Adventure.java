package org.mabufudyne.core;

public class Adventure {

    private static String DEFAULT_NAME = "New Adventure";

    private String name;

    public Adventure() {
        this.name = DEFAULT_NAME;
    }

    public Adventure(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static String getDefaultName() {
        return DEFAULT_NAME;
    }

}
