package com.starpilot.starview;

import android.content.Context;
import android.net.Uri;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.tensorflow.lite.Interpreter;

/* compiled from: LaneModel.kt */
public final class LaneModelKt {
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00da, code lost:
    
        if (java.util.Arrays.equals(r0.getOutputTensor(0).shape(), new int[]{1, 8}) != false) goto L27;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v18 */
    /* JADX WARN: Type inference failed for: r7v19 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final String importLaneModel(Context ctx, Uri uri) {
        Object obj;
        Throwable th;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(uri, "uri");
        try {
            File file = new File(ctx.getFilesDir(), "lane_count.lite");
            File file2 = new File(ctx.getFilesDir(), "lane_count.lite.part");
            InputStream openInputStream = ctx.getContentResolver().openInputStream(uri);
            Intrinsics.checkNotNull(openInputStream);
            InputStream inputStream = openInputStream;
            try {
                InputStream inputStream2 = inputStream;
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                try {
                    boolean z = false;
                    ByteStreamsKt.copyTo(inputStream2, fileOutputStream, 8192);
                    CloseableKt.closeFinally(fileOutputStream, null);
                    CloseableKt.closeFinally(inputStream, null);
                    FileInputStream fileInputStream = new FileInputStream(file2);
                    try {
                        String str = 8;
                        byte[] bArr = new byte[8];
                        fileInputStream.read(bArr);
                        CloseableKt.closeFinally(fileInputStream, null);
                        if (file2.length() >= 1000000 && Intrinsics.areEqual(new String(bArr, 4, 4, Charsets.UTF_8), "TFL3")) {
                            byte[] readBytes = FilesKt.readBytes(file2);
                            ByteBuffer order = ByteBuffer.allocateDirect(readBytes.length).order(ByteOrder.nativeOrder());
                            order.put(readBytes);
                            order.rewind();
                            try {
                                try {
                                    Interpreter interpreter = new Interpreter(order);
                                    try {
                                        Interpreter interpreter2 = interpreter;
                                        boolean z2 = true;
                                        try {
                                            if (Arrays.equals(interpreter2.getInputTensor(0).shape(), new int[]{1, 400, 800, 3})) {
                                                try {
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    str = 0;
                                                    try {
                                                        throw th;
                                                    } catch (Throwable th3) {
                                                        AutoCloseableKt.closeFinally(interpreter, th);
                                                        throw th3;
                                                    }
                                                }
                                            }
                                            z2 = false;
                                            str = 0;
                                            AutoCloseableKt.closeFinally(interpreter, null);
                                            z = z2;
                                        } catch (Throwable th4) {
                                            th = th4;
                                            obj = null;
                                            th = th;
                                            str = obj;
                                            throw th;
                                        }
                                    } catch (Throwable th5) {
                                        th = th5;
                                        obj = null;
                                    }
                                } catch (Throwable th6) {
                                }
                            } catch (Throwable th7) {
                                str = 0;
                            }
                            if (z) {
                                file2.renameTo(file);
                                return str;
                            }
                            file2.delete();
                            return "That .lite file isn't the lane model (wrong input / output)";
                        }
                        file2.delete();
                        return "That file isn't a lane model (.lite)";
                    } finally {
                        try {
                            throw th;
                        } finally {
                        }
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            return "Import failed: " + e.getMessage();
        }
    }
}
