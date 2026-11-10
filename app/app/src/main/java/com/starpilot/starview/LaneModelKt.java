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
    public static final String importLaneModel(Context ctx, Uri uri) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(uri, "uri");
        try {
            File dst = new File(ctx.getFilesDir(), "lane_count.lite");
            File tmp = new File(ctx.getFilesDir(), "lane_count.lite.part");
            InputStream in = ctx.getContentResolver().openInputStream(uri);
            Intrinsics.checkNotNull(in);
            Throwable inCause = null;
            try {
                FileOutputStream out = new FileOutputStream(tmp);
                Throwable outCause = null;
                try {
                    ByteStreamsKt.copyTo(in, out, 8192);
                } catch (Throwable th) {
                    outCause = th;
                    throw th;
                } finally {
                    CloseableKt.closeFinally(out, outCause);
                }
            } catch (Throwable th2) {
                inCause = th2;
                throw th2;
            } finally {
                CloseableKt.closeFinally(in, inCause);
            }
            byte[] head = new byte[8];
            FileInputStream fin = new FileInputStream(tmp);
            Throwable finCause = null;
            try {
                fin.read(head);
            } catch (Throwable th3) {
                finCause = th3;
                throw th3;
            } finally {
                CloseableKt.closeFinally(fin, finCause);
            }
            if (tmp.length() < 1000000 || !Intrinsics.areEqual(new String(head, 4, 4, Charsets.UTF_8), "TFL3")) {
                tmp.delete();
                return "That file isn't a lane model (.lite)";
            }
            byte[] bytes = FilesKt.readBytes(tmp);
            ByteBuffer buf = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder());
            buf.put(bytes);
            buf.rewind();
            boolean ok;
            try {
                Interpreter it = new Interpreter(buf);
                Throwable itCause = null;
                try {
                    ok = Arrays.equals(it.getInputTensor(0).shape(), new int[]{1, 400, 800, 3}) && Arrays.equals(it.getOutputTensor(0).shape(), new int[]{1, 8});
                } catch (Throwable th4) {
                    itCause = th4;
                    throw th4;
                } finally {
                    AutoCloseableKt.closeFinally(it, itCause);
                }
            } catch (Throwable th5) {
                ok = false;
            }
            if (!ok) {
                tmp.delete();
                return "That .lite file isn't the lane model (wrong input / output)";
            }
            tmp.renameTo(dst);
            return null;
        } catch (Exception e) {
            return "Import failed: " + e.getMessage();
        }
    }
}
