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

    public final Bitmap icon(Context ctx, String type, String modifier) {
        Bitmap bitmap;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        String norm = type;
        if (norm.length() == 0) {
            norm = "turn";
        }
        if (Intrinsics.areEqual(norm, "rotary")) {
            norm = "roundabout";
        }
        switch (norm) {
            case "new name":
            case "notification":
            case "continue":
            case "depart":
                norm = "turn";
                break;
        }
        String norm2 = StringsKt.replace(norm, " ", "_", false);
        String suffix = (String) MapsKt.mapOf(TuplesKt.to("left", "left"), TuplesKt.to("right", "right"), TuplesKt.to("straight", "straight"), TuplesKt.to("slightLeft", "slight_left"), TuplesKt.to("slightRight", "slight_right"), TuplesKt.to("sharpLeft", "sharp_left"), TuplesKt.to("sharpRight", "sharp_right")).get(modifier);
        if (suffix == null) {
            suffix = "";
        }
        String name;
        if (Intrinsics.areEqual(modifier, "uturn")) {
            name = "direction_uturn";
        } else {
            name = (suffix.length() == 0 ? new StringBuilder().append("direction_").append(norm2) : new StringBuilder().append("direction_").append(norm2).append("_").append(suffix)).toString();
        }
        synchronized (cache) {
            Map $this$getOrPut$iv = cache;
            Object hit = $this$getOrPut$iv.get(name);
            if (hit == null) {
                Resources r = ctx.getResources();
                int id = r.getIdentifier(name, "drawable", ctx.getPackageName());
                if (id == 0 && suffix.length() > 0) {
                    id = r.getIdentifier("direction_turn_" + suffix, "drawable", ctx.getPackageName());
                }
                if (id == 0) {
                    id = r.getIdentifier("direction_turn_straight", "drawable", ctx.getPackageName());
                }
                Bitmap made = id == 0 ? null : BitmapFactory.decodeResource(r, id);
                $this$getOrPut$iv.put(name, made);
                hit = made;
            }
            bitmap = (Bitmap) hit;
        }
        return bitmap;
    }
}
