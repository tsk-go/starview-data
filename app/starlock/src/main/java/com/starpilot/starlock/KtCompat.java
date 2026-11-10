package com.starpilot.starlock;

import java.lang.reflect.Method;
import kotlin.Result;

/**
 * Recovered-source helper (Nov 10, 2026): the app was rebuilt from its own APK, and the decompiled Java calls
 * Kotlin's Result inline-class functions, whose JVM names ("constructor-impl", "isFailure-impl", ...) Java can't
 * write. A Result is just the value itself, or a Result.Failure holding the exception.
 */
public final class KtCompat {
  private KtCompat() {}

  public static Object resultOf(Object value) { return value; }

  public static boolean isFailure(Object result) { return result instanceof Result.Failure; }

  public static boolean isSuccess(Object result) { return !(result instanceof Result.Failure); }

  public static Throwable exceptionOrNull(Object result) {
    return result instanceof Result.Failure ? ((Result.Failure) result).exception : null;
  }

  public static Object getOrNull(Object result) { return result instanceof Result.Failure ? null : result; }

  private static Method boxImpl;

  /** a real kotlin.Result object, for code that hands one on (Continuation.resumeWith) */
  public static Object box(Object value) {
    try {
      if (boxImpl == null) boxImpl = Result.class.getMethod("box-impl", Object.class);
      return boxImpl.invoke(null, value);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  /** Kotlin has no checked exceptions: throws a checked one from code whose Java signature doesn't declare it */
  public static RuntimeException sneaky(Throwable t) {
    KtCompat.<RuntimeException>throw0(t);
    return null;
  }

  @SuppressWarnings("unchecked")
  private static <T extends Throwable> void throw0(Throwable t) throws T {
    throw (T) t;
  }

  /** lets Java catch a checked exception that Kotlin code throws without declaring it (a no-op) */
  public static <T extends Throwable> void mayThrow() throws T {}
}
