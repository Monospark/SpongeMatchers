package org.monospark.spongematchers.testutil;

import org.junit.Assert;

public class ExceptionChecker {

    public static void check(Class<? extends Exception> exceptionClass, Runnable... runnables) {
        for (Runnable r : runnables) {
            try {
                r.run();
                Assert.fail("No exception was thrown. expected: " + exceptionClass.getName());
            } catch (Exception e) {
                if (!exceptionClass.isInstance(e)) {
                    throw new AssertionError("Wrong exception was thrown. expected: " + exceptionClass.getSimpleName()
                            + ", got: " + e.getClass().getName(), e);
                }

                if (e.getMessage() == null) {
                    throw new AssertionError("A custom exception message should have been attached", e);
                }
            }
        }
    }
}
