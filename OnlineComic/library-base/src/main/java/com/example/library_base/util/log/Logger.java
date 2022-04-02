package com.example.library_base.util.log;

public final class Logger extends LoggerBase {

    private static final String Version = "COMIC";

    public Logger() {
        super("");
    }

    public Logger(Class<?> cls) {
        super(cls);
    }

    @Override
    protected String getTag() {
        return Version;
    }
}
