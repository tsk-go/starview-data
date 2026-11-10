package com.starpilot.starview;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: ForkFixes.kt */
public final class ForkFixes {
    public static final ForkFixes INSTANCE = new ForkFixes();

    private ForkFixes() {
    }

    public final List<String> parse(String md) {
        Intrinsics.checkNotNullParameter(md, "md");
        int start = StringsKt.indexOf$default((CharSequence) md, "# Fork fixes to drop", 0, false, 6, (Object) null);
        if (start < 0) {
            return CollectionsKt.emptyList();
        }
        int it = StringsKt.indexOf$default((CharSequence) md, "\n# ", start + 1, false, 4, (Object) null);
        if (it < 0) {
            it = md.length();
        }
        Regex regex = new Regex("(?m)^\\d+\\. \\*\\*(.+?)\\*\\*");
        String substring = md.substring(start, it);
        Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
        return SequencesKt.toList(SequencesKt.map(Regex.findAll$default(regex, substring, 0, 2, null), new Function1() { // from class: com.starpilot.starview.ForkFixes$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String parse$lambda$1;
                parse$lambda$1 = ForkFixes.parse$lambda$1((MatchResult) obj);
                return parse$lambda$1;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String parse$lambda$1(MatchResult it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return StringsKt.trim((CharSequence) it.getGroupValues().get(1)).toString();
    }
}
