package com.example.library_base.util.log;

import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

public abstract class LoggerBase {
    private static String mTag = "[COMIC]";
    private final String mPrefix;
    private static boolean isLogEnable = true;

    public LoggerBase(Class<?> cls) {
        this(cls.getSimpleName());
    }

    public LoggerBase(String prefix) {
        mTag = getTag();
        if (TextUtils.isEmpty(mTag)) {
            throw new IllegalStateException("Tag must be not null or empty");
        }
        mPrefix = "[" + prefix + "] ";
    }

    /**
     * Gets the tag that will be used in all logging calls.
     */
    @NonNull
    protected abstract String getTag();

    /**
     * Logs a {@link Log#VERBOSE} log message. Will only be logged if {@link Log#VERBOSE} is
     * loggable. This is a wrapper around {@link Log#v(String, String)}.
     *
     * @param message The message you would like logged.
     */
    public static void v(String message) {
        if (isV() && isLogEnable) {
            Log.v(mTag, getLogInfo().concat(message));
        }
    }

    /**
     * Logs a {@link Log#DEBUG} log message. Will only be logged if {@link Log#DEBUG} is
     * loggable. This is a wrapper around {@link Log#d(String, String)}.
     *
     * @param message The message you would like logged.
     */
    public static void d(String message) {
        if (isD() && isLogEnable) {
            Log.d(mTag, getLogInfo().concat(message));
        }
    }

    /**
     * Logs a {@link Log#INFO} log message. Will only be logged if {@link Log#INFO} is loggable.
     * This is a wrapper around {@link Log#i(String, String)}.
     *
     * @param message The message you would like logged.
     */
    public static void i(String message) {
        if (isI() && isLogEnable) {
            Log.i(mTag, getLogInfo().concat(message));
        }
    }

    /**
     * Logs a {@link Log#WARN} log message. This is a wrapper around {@link Log#w(String, String)}.
     *
     * @param message The message you would like logged.
     */
    public static void w(String message) {
        if(isLogEnable)
            Log.w(mTag, getLogInfo().concat(message));
    }

    /**
     * Logs a {@link Log#ERROR} log message. This is a wrapper around {@link Log#e(String, String)}.
     *
     * @param message The message you would like logged.
     */
    public static void e(String message) {
        if(isLogEnable)
            Log.e(mTag, getLogInfo().concat(message));
    }

    private static boolean isV() {
        return Log.isLoggable(mTag, Log.VERBOSE);
    }

    private static boolean isD() {
        return Log.isLoggable(mTag, Log.DEBUG);
    }

    private static boolean isI() {
        return Log.isLoggable(mTag, Log.INFO);
    }

    private static boolean isLog() {
        return isLogEnable;
    }

    private static String getLogInfo() {
        String[] info = new String[] { "", ""};
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length < 5) {
            Log.e("Logger", "Stack is too shallow!!!");
        } else {
            info[0] = elements[4].getClassName().substring(
                    elements[4].getClassName().lastIndexOf(".") + 1);
            info[1] = elements[4].getMethodName() + "()";
        }
        return "["+info[0]+"_"+info[1]+"] ";
    }
}
