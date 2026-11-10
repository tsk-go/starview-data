package com.starpilot.starview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.HashMap;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: NavModel.kt */
public final class NavIcons {
    public static final NavIcons INSTANCE = new NavIcons();
    private static final HashMap<String, Bitmap> cache = new HashMap<>();

    private NavIcons() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0042, code lost:
    
        if (r0.equals("new name") == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x005f, code lost:
    
        r0 = "turn";
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004b, code lost:
    
        if (r0.equals("notification") == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0054, code lost:
    
        if (r0.equals("continue") == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005d, code lost:
    
        if (r0.equals("depart") != false) goto L25;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0168 A[Catch: all -> 0x018c, TryCatch #0 {, blocks: (B:30:0x0117, B:32:0x0122, B:34:0x0133, B:38:0x0143, B:40:0x0168, B:43:0x017d, B:44:0x0186, B:49:0x0179), top: B:29:0x0117 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0179 A[Catch: all -> 0x018c, TryCatch #0 {, blocks: (B:30:0x0117, B:32:0x0122, B:34:0x0133, B:38:0x0143, B:40:0x0168, B:43:0x017d, B:44:0x0186, B:49:0x0179), top: B:29:0x0117 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bitmap icon(Context ctx, String type, String modifier) {
        String sb;
        Bitmap bitmap;
        Bitmap bitmap2;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        String str = type;
        if (str.length() == 0) {
            str = "turn";
        }
        String norm = str;
        if (Intrinsics.areEqual(norm, "rotary")) {
            norm = "roundabout";
        }
        switch (norm.hashCode()) {
            case -1335343116:
                break;
            case -567202649:
                break;
            case 595233003:
                break;
            case 1318962443:
                break;
        }
        String norm2 = StringsKt.replace(norm, " ", "_", false);
        String str2 = (String) MapsKt.mapOf(TuplesKt.to("left", "left"), TuplesKt.to("right", "right"), TuplesKt.to("straight", "straight"), TuplesKt.to("slightLeft", "slight_left"), TuplesKt.to("slightRight", "slight_right"), TuplesKt.to("sharpLeft", "sharp_left"), TuplesKt.to("sharpRight", "sharp_right")).get(modifier);
        if (str2 == null) {
            str2 = "";
        }
        String suffix = str2;
        if (Intrinsics.areEqual(modifier, "uturn")) {
            sb = "direction_uturn";
        } else {
            sb = (suffix.length() == 0 ? new StringBuilder().append("direction_").append(norm2) : new StringBuilder().append("direction_").append(norm2).append("_").append(suffix)).toString();
        }
        String name = sb;
        synchronized (cache) {
            Map $this$getOrPut$iv = cache;
            Bitmap bitmap3 = $this$getOrPut$iv.get(name);
            if (bitmap3 == null) {
                Resources r = ctx.getResources();
                int id = r.getIdentifier(name, "drawable", ctx.getPackageName());
                if (id == 0) {
                    if (suffix.length() > 0) {
                        id = r.getIdentifier("direction_turn_" + suffix, "drawable", ctx.getPackageName());
                        if (id == 0) {
                            id = r.getIdentifier("direction_turn_straight", "drawable", ctx.getPackageName());
                        }
                        bitmap = id != 0 ? null : BitmapFactory.decodeResource(r, id);
                        $this$getOrPut$iv.put(name, bitmap);
                    }
                }
                if (id == 0) {
                }
                if (id != 0) {
                }
                $this$getOrPut$iv.put(name, bitmap);
            } else {
                bitmap = bitmap3;
            }
            bitmap2 = bitmap;
        }
        return bitmap2;
    }
}
