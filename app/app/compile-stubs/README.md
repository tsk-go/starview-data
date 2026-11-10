# Compile-time exception stubs

This app's Java (and StarLock's, `../../starlock`) was recovered from Kotlin bytecode (StarView 2.84.1, StarLock 2.19). Kotlin has no checked exceptions, so the
recovered code calls `JSONObject.put`, stream reads and writes, `Thread.sleep` and so on without `try` or `throws`.

So that javac accepts it as Kotlin did, these few exception classes are compiled as `RuntimeException`s and patched over the
real ones **for compiling only** (`--patch-module java.base=...` in `build.gradle.kts`; Android's `org.json` is part of
`java.base` in the system image the build compiles against). They are never packaged: on the tablet the real classes are used. Bytecode is unchanged by this -
a checked exception exists only in javac's checks, and `catch` blocks name the class the same way either way.

Stubbed: `IOException`, `InterruptedException`, `ExecutionException`, `GeneralSecurityException`, `JSONException`,
`ReflectiveOperationException` (so `ClassNotFoundException`, `NoSuchMethodException`, `IllegalAccessException`...).
