package com.starpilot.starview;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import com.starpilot.starview.Detector;
import com.starpilot.starview.LaneBrain;
import com.starpilot.starview.LaneModel;
import com.starpilot.starview.LaneShots;
import com.starpilot.starview.LaneSuggest;
import com.starpilot.starview.Nav;
import com.starpilot.starview.NudgeLogic;
import com.starpilot.starview.OsmRoads;
import com.starpilot.starview.SceneView;
import com.starpilot.starview.SharedPlace;
import com.starpilot.starview.TurnSignal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: MainActivity.kt */
public final class MainActivity$uiTick$1 implements Runnable {
    final /* synthetic */ MainActivity this$0;

    MainActivity$uiTick$1(MainActivity $receiver) {
        this.this$0 = $receiver;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Can't wrap try/catch for region: R(14:459|(3:460|461|(1:463))|465|(1:467)(1:1479)|468|(1:470)(2:1475|(1:1477)(1:1478))|471|(1:473)(1:(1:1473)(1:1474))|474|(3:476|(1:1468)(1:480)|(4:482|483|484|485))|(1:1470)(1:1471)|483|484|485) */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x02ca, code lost:
    
        if ((r0 % 40) == 5) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1297:0x0fe9, code lost:
    
        if (r46 != null) goto L835;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1404:0x11e3, code lost:
    
        if (r46 != null) goto L930;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1520:0x08f4, code lost:
    
        if (r7 != false) goto L479;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0382, code lost:
    
        if (r4.needsCamera(r5) != false) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0192, code lost:
    
        r0 = r118.this$0.tabletModePusher;
     */
    /* JADX WARN: Code restructure failed: missing block: B:558:0x0e80, code lost:
    
        if (r4.beside(-1) != false) goto L759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:565:0x0ea3, code lost:
    
        if (r4.beside(1) != false) goto L768;
     */
    /* JADX WARN: Code restructure failed: missing block: B:584:0x0f07, code lost:
    
        if (r4.getLogic().getState() != com.starpilot.starview.LaneSuggest.State.IDLE) goto L787;
     */
    /* JADX WARN: Code restructure failed: missing block: B:595:0x13d5, code lost:
    
        if (r0.getTurn().getHoldSide() != 0) goto L1003;
     */
    /* JADX WARN: Code restructure failed: missing block: B:641:0x149e, code lost:
    
        if (r4 == false) goto L1055;
     */
    /* JADX WARN: Code restructure failed: missing block: B:683:0x1578, code lost:
    
        if (r5 == false) goto L1099;
     */
    /* JADX WARN: Code restructure failed: missing block: B:887:0x1c36, code lost:
    
        if ((r0 % 1) == 0) goto L1370;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:1027:0x1f9b  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:1183:0x27dc  */
    /* JADX WARN: Removed duplicated region for block: B:1186:0x1c45  */
    /* JADX WARN: Removed duplicated region for block: B:1188:0x1c22  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:1219:0x1bef  */
    /* JADX WARN: Removed duplicated region for block: B:1234:0x193d  */
    /* JADX WARN: Removed duplicated region for block: B:1237:0x1949  */
    /* JADX WARN: Removed duplicated region for block: B:1239:0x194b  */
    /* JADX WARN: Removed duplicated region for block: B:1240:0x1866  */
    /* JADX WARN: Removed duplicated region for block: B:1241:0x184a  */
    /* JADX WARN: Removed duplicated region for block: B:1243:0x1809  */
    /* JADX WARN: Removed duplicated region for block: B:1247:0x16b8  */
    /* JADX WARN: Removed duplicated region for block: B:1251:0x15ef  */
    /* JADX WARN: Removed duplicated region for block: B:1252:0x15b1  */
    /* JADX WARN: Removed duplicated region for block: B:1253:0x14d9  */
    /* JADX WARN: Removed duplicated region for block: B:1259:0x13df  */
    /* JADX WARN: Removed duplicated region for block: B:1263:0x0f11  */
    /* JADX WARN: Removed duplicated region for block: B:1266:0x0f1b  */
    /* JADX WARN: Removed duplicated region for block: B:1318:0x139a  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x030f  */
    /* JADX WARN: Removed duplicated region for block: B:1368:0x1138  */
    /* JADX WARN: Removed duplicated region for block: B:1370:0x1143  */
    /* JADX WARN: Removed duplicated region for block: B:1382:0x1170  */
    /* JADX WARN: Removed duplicated region for block: B:1384:0x117a  */
    /* JADX WARN: Removed duplicated region for block: B:1391:0x11a1  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x031e  */
    /* JADX WARN: Removed duplicated region for block: B:1410:0x1202  */
    /* JADX WARN: Removed duplicated region for block: B:1413:0x1241  */
    /* JADX WARN: Removed duplicated region for block: B:1416:0x127a  */
    /* JADX WARN: Removed duplicated region for block: B:1421:0x12b3  */
    /* JADX WARN: Removed duplicated region for block: B:1424:0x12c4  */
    /* JADX WARN: Removed duplicated region for block: B:1429:0x12b6  */
    /* JADX WARN: Removed duplicated region for block: B:1431:0x121f  */
    /* JADX WARN: Removed duplicated region for block: B:1435:0x11f6  */
    /* JADX WARN: Removed duplicated region for block: B:1437:0x1175  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:1440:0x1148  */
    /* JADX WARN: Removed duplicated region for block: B:1441:0x113f  */
    /* JADX WARN: Removed duplicated region for block: B:1457:0x13a2  */
    /* JADX WARN: Removed duplicated region for block: B:1461:0x0e87  */
    /* JADX WARN: Removed duplicated region for block: B:1462:0x0e63  */
    /* JADX WARN: Removed duplicated region for block: B:1482:0x0c75  */
    /* JADX WARN: Removed duplicated region for block: B:1488:0x0b3d  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0344 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1491:0x0ad2  */
    /* JADX WARN: Removed duplicated region for block: B:1511:0x08d9  */
    /* JADX WARN: Removed duplicated region for block: B:1512:0x082b  */
    /* JADX WARN: Removed duplicated region for block: B:1515:0x08dd  */
    /* JADX WARN: Removed duplicated region for block: B:1531:0x091a  */
    /* JADX WARN: Removed duplicated region for block: B:1537:0x0933  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0354  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0360  */
    /* JADX WARN: Removed duplicated region for block: B:1601:0x07e9  */
    /* JADX WARN: Removed duplicated region for block: B:1602:0x07d2  */
    /* JADX WARN: Removed duplicated region for block: B:1603:0x07b5  */
    /* JADX WARN: Removed duplicated region for block: B:1607:0x069a  */
    /* JADX WARN: Removed duplicated region for block: B:1616:0x052f  */
    /* JADX WARN: Removed duplicated region for block: B:1622:0x050b  */
    /* JADX WARN: Removed duplicated region for block: B:1624:0x04f2  */
    /* JADX WARN: Removed duplicated region for block: B:1626:0x04ad  */
    /* JADX WARN: Removed duplicated region for block: B:1627:0x047b  */
    /* JADX WARN: Removed duplicated region for block: B:1629:0x0450  */
    /* JADX WARN: Removed duplicated region for block: B:1631:0x03fa  */
    /* JADX WARN: Removed duplicated region for block: B:1637:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:1638:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x03a9  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03b5  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x03c2  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x03e2  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x03f6  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0408  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0426  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0445  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x044d  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x045f  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0473  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0487  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0495  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x04a8  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x04b9  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x04d6  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x04de  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0501  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0509  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0520  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x052a  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0559  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0567  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x057c  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x0588  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x059e  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x05ac  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x05c1  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x05cd  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x05e3  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x05ef  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x0623  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x064a  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x0652  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0666  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x0690  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x0698  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x06b1  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x06bd  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x06d6  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x06e2  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x070b  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0717  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x0730  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x073c  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x075d  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x0769  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x07a5  */
    /* JADX WARN: Removed duplicated region for block: B:359:0x07b3  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x07c2  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x07d0  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x07e7  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x07ee  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x0823  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x0829  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x0832  */
    /* JADX WARN: Removed duplicated region for block: B:396:0x0a64  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x0a70  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x0a8e  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x0a9a  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x0ab1  */
    /* JADX WARN: Removed duplicated region for block: B:415:0x0abd  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x0acb  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x0adb  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x0af2  */
    /* JADX WARN: Removed duplicated region for block: B:427:0x0afe  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x0b10  */
    /* JADX WARN: Removed duplicated region for block: B:435:0x0b1f  */
    /* JADX WARN: Removed duplicated region for block: B:437:0x0b27  */
    /* JADX WARN: Removed duplicated region for block: B:441:0x0b39  */
    /* JADX WARN: Removed duplicated region for block: B:444:0x0b4a  */
    /* JADX WARN: Removed duplicated region for block: B:447:0x0b56  */
    /* JADX WARN: Removed duplicated region for block: B:451:0x0b66  */
    /* JADX WARN: Removed duplicated region for block: B:454:0x0b72  */
    /* JADX WARN: Removed duplicated region for block: B:457:0x0b7f  */
    /* JADX WARN: Removed duplicated region for block: B:488:0x0ca4  */
    /* JADX WARN: Removed duplicated region for block: B:496:0x0cca  */
    /* JADX WARN: Removed duplicated region for block: B:500:0x0cef  */
    /* JADX WARN: Removed duplicated region for block: B:509:0x0d59  */
    /* JADX WARN: Removed duplicated region for block: B:514:0x0d7b  */
    /* JADX WARN: Removed duplicated region for block: B:517:0x0d87  */
    /* JADX WARN: Removed duplicated region for block: B:522:0x0dae  */
    /* JADX WARN: Removed duplicated region for block: B:525:0x0dba  */
    /* JADX WARN: Removed duplicated region for block: B:530:0x0ddc  */
    /* JADX WARN: Removed duplicated region for block: B:533:0x0dea  */
    /* JADX WARN: Removed duplicated region for block: B:536:0x0df4  */
    /* JADX WARN: Removed duplicated region for block: B:541:0x0e0c  */
    /* JADX WARN: Removed duplicated region for block: B:544:0x0e1a  */
    /* JADX WARN: Removed duplicated region for block: B:547:0x0e24  */
    /* JADX WARN: Removed duplicated region for block: B:552:0x0e48  */
    /* JADX WARN: Removed duplicated region for block: B:555:0x0e6d  */
    /* JADX WARN: Removed duplicated region for block: B:562:0x0e90  */
    /* JADX WARN: Removed duplicated region for block: B:569:0x0ebb  */
    /* JADX WARN: Removed duplicated region for block: B:575:0x0edb  */
    /* JADX WARN: Removed duplicated region for block: B:588:0x13b5  */
    /* JADX WARN: Removed duplicated region for block: B:591:0x13c1  */
    /* JADX WARN: Removed duplicated region for block: B:598:0x13ee  */
    /* JADX WARN: Removed duplicated region for block: B:601:0x13fc  */
    /* JADX WARN: Removed duplicated region for block: B:604:0x1406  */
    /* JADX WARN: Removed duplicated region for block: B:609:0x141d  */
    /* JADX WARN: Removed duplicated region for block: B:612:0x142b  */
    /* JADX WARN: Removed duplicated region for block: B:615:0x1435  */
    /* JADX WARN: Removed duplicated region for block: B:619:0x1443  */
    /* JADX WARN: Removed duplicated region for block: B:626:0x1462  */
    /* JADX WARN: Removed duplicated region for block: B:629:0x1470  */
    /* JADX WARN: Removed duplicated region for block: B:632:0x147a  */
    /* JADX WARN: Removed duplicated region for block: B:645:0x14b1  */
    /* JADX WARN: Removed duplicated region for block: B:651:0x14d4  */
    /* JADX WARN: Removed duplicated region for block: B:654:0x14fc  */
    /* JADX WARN: Removed duplicated region for block: B:664:0x1527  */
    /* JADX WARN: Removed duplicated region for block: B:667:0x153a  */
    /* JADX WARN: Removed duplicated region for block: B:689:0x1588  */
    /* JADX WARN: Removed duplicated region for block: B:692:0x15a2  */
    /* JADX WARN: Removed duplicated region for block: B:695:0x15ae  */
    /* JADX WARN: Removed duplicated region for block: B:698:0x15bd  */
    /* JADX WARN: Removed duplicated region for block: B:704:0x15e6  */
    /* JADX WARN: Removed duplicated region for block: B:707:0x15f6  */
    /* JADX WARN: Removed duplicated region for block: B:721:0x1685  */
    /* JADX WARN: Removed duplicated region for block: B:724:0x1694  */
    /* JADX WARN: Removed duplicated region for block: B:727:0x16a7  */
    /* JADX WARN: Removed duplicated region for block: B:730:0x16b5  */
    /* JADX WARN: Removed duplicated region for block: B:733:0x16c8  */
    /* JADX WARN: Removed duplicated region for block: B:736:0x16d4  */
    /* JADX WARN: Removed duplicated region for block: B:743:0x16f1  */
    /* JADX WARN: Removed duplicated region for block: B:746:0x16fd  */
    /* JADX WARN: Removed duplicated region for block: B:753:0x177c  */
    /* JADX WARN: Removed duplicated region for block: B:756:0x1788  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:761:0x17f7  */
    /* JADX WARN: Removed duplicated region for block: B:766:0x1816  */
    /* JADX WARN: Removed duplicated region for block: B:773:0x183b  */
    /* JADX WARN: Removed duplicated region for block: B:776:0x1857  */
    /* JADX WARN: Removed duplicated region for block: B:779:0x1873  */
    /* JADX WARN: Removed duplicated region for block: B:798:0x1966  */
    /* JADX WARN: Removed duplicated region for block: B:808:0x1992  */
    /* JADX WARN: Removed duplicated region for block: B:811:0x19b5  */
    /* JADX WARN: Removed duplicated region for block: B:883:0x1c1d  */
    /* JADX WARN: Removed duplicated region for block: B:886:0x1c2e  */
    /* JADX WARN: Removed duplicated region for block: B:890:0x1c56  */
    /* JADX WARN: Removed duplicated region for block: B:893:0x1c67  */
    /* JADX WARN: Removed duplicated region for block: B:896:0x1c78  */
    /* JADX WARN: Removed duplicated region for block: B:899:0x1c89  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0253  */
    /* JADX WARN: Removed duplicated region for block: B:902:0x1c9a  */
    /* JADX WARN: Removed duplicated region for block: B:905:0x1cab  */
    /* JADX WARN: Removed duplicated region for block: B:908:0x1cb7  */
    /* JADX WARN: Removed duplicated region for block: B:916:0x1cda  */
    /* JADX WARN: Removed duplicated region for block: B:919:0x1ceb  */
    /* JADX WARN: Removed duplicated region for block: B:922:0x1cfc  */
    /* JADX WARN: Removed duplicated region for block: B:925:0x1d0d  */
    /* JADX WARN: Removed duplicated region for block: B:928:0x1d1e  */
    /* JADX WARN: Removed duplicated region for block: B:931:0x1d2f  */
    /* JADX WARN: Removed duplicated region for block: B:934:0x1d3b  */
    /* JADX WARN: Removed duplicated region for block: B:940:0x1d54  */
    /* JADX WARN: Removed duplicated region for block: B:943:0x1d65  */
    /* JADX WARN: Removed duplicated region for block: B:946:0x1d76  */
    /* JADX WARN: Removed duplicated region for block: B:949:0x1d87  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:952:0x1d93  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0289  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void run() {
        int i;
        final Telemetry t;
        DataClient dataClient;
        LinearLayout linearLayout;
        TextView stallBanner;
        TextView stallBanner2;
        int stall;
        TextView gpuBanner;
        TextView gpuBanner2;
        int i2;
        int i3;
        UiSettings uiSettings;
        TextView textView;
        boolean z;
        boolean z2;
        boolean wantHome;
        boolean z3;
        int i4;
        OffroadHome offroadHome;
        TextView textView2;
        boolean z4;
        boolean wantVideo;
        boolean z5;
        boolean z6;
        UiSettings uiSettings2;
        OverlayView overlayView;
        OverlayView overlayView2;
        long currentTimeMillis;
        long j;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        TextView textView7;
        boolean isSet;
        TextView textView8;
        String str;
        TextView textView9;
        TextView textView10;
        LinearLayout linearLayout2;
        SlcView slcView;
        WheelButton wheelButton;
        MiciDistanceView miciDistanceView;
        SlcActionsView slcActionsView;
        long j2;
        int i5;
        int i6;
        Pair pair;
        Pair cl;
        TextView textView11;
        TextView textView12;
        TextView textView13;
        TextView textView14;
        TextView textView15;
        TextView textView16;
        DevSidebarView devSidebarView;
        String str2;
        int i7;
        SideView sideView;
        SideView sideView2;
        String cnState;
        boolean usbgpu;
        boolean show;
        boolean z7;
        ImageView imageView;
        boolean z8;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        ImageView imageView5;
        ImageView imageView6;
        ImageView imageView7;
        ImageView imageView8;
        ImageView imageView9;
        boolean z9;
        ImageView imageView10;
        ImageView imageView11;
        boolean z10;
        TextView textView17;
        AetherGaugeView aetherGaugeView;
        SlcSourcesView slcSourcesView;
        int i8;
        NavCardView navCardView;
        int i9;
        NavCardView navCardView2;
        SlowReasonView slowReasonView;
        int i10;
        int i11;
        boolean z11;
        LaneBrain laneBrain;
        LaneAhead laneAhead;
        LaneMapLog laneMapLog;
        SceneView s;
        LaneCardView laneCardView;
        LaneShots laneShots;
        Detector detector;
        LaneCardView laneCardView2;
        BlinkerCardView blinkerCardView;
        DataClient dataClient2;
        GpsCardView gpsCardView;
        DataClient dataClient3;
        String cnState2;
        int i12;
        boolean z12;
        boolean z13;
        boolean active;
        boolean z14;
        DataClient dataClient4;
        int i13;
        int i14;
        LaneSuggestView laneSuggestView;
        LaneSuggestView laneSuggestView2;
        boolean connected;
        List emptyList;
        LaneBrain laneBrain2;
        LaneSuggestView laneSuggestView3;
        float f;
        LaneBrain laneBrain3;
        LaneBrain laneBrain4;
        boolean z15;
        int i15;
        LaneAhead laneAhead2;
        LaneBrain laneBrain5;
        UiSettings uiSettings3;
        LaneBrain laneBrain6;
        LaneBrain laneBrain7;
        LaneBrain laneBrain8;
        LaneSuggest.In in;
        LaneAhead laneAhead3;
        LaneBrain laneBrain9;
        LaneBrain laneBrain10;
        List<Nav.Lane> emptyList2;
        String key;
        String why;
        TurnSignal.In turnIn;
        LaneSuggestView laneSuggestView4;
        LaneSuggestView laneSuggestView5;
        LaneSuggestView laneSuggestView6;
        LaneBrain laneBrain11;
        Nav.Step first;
        ReplayBar replayBar;
        DataClient dataClient5;
        TextView textView18;
        DataClient dataClient6;
        int i16;
        TextView textView19;
        DataClient dataClient7;
        int i17;
        int i18;
        UiSettings uiSettings4;
        UiSettings uiSettings5;
        SharedPlace.Place place;
        int i19;
        int i20;
        TextView textView20;
        FrameLayout.LayoutParams lp;
        TextView textView21;
        int want;
        NudgeLogic.Result r;
        NudgeHintView nudgeHintView;
        NudgeHintView nudgeHintView2;
        NudgeHintView nudgeHintView3;
        WeatherView weatherView;
        TextView textView22;
        String str3;
        TextView textView23;
        long j3;
        long j4;
        float f2;
        long j5;
        float f3;
        TextView textView24;
        boolean z16;
        boolean showAlert;
        float f4;
        float f5;
        float f6;
        LinearLayout linearLayout3;
        View view;
        long mf;
        long j6;
        OverlayView overlayView3;
        WheelButton wheelButton2;
        DmView dmView;
        MiciDistanceView miciDistanceView2;
        SlcView slcView2;
        SlcActionsView slcActionsView2;
        DevSidebarView devSidebarView2;
        AetherGaugeView aetherGaugeView2;
        NavCardView navCardView3;
        SlowReasonView slowReasonView2;
        LaneCardView laneCardView3;
        BlinkerCardView blinkerCardView2;
        GpsCardView gpsCardView2;
        NudgeHintView nudgeHintView4;
        WeatherView weatherView2;
        SlcSourcesView slcSourcesView2;
        LinearLayout linearLayout4;
        int i21;
        Telemetry telemetry;
        Telemetry telemetry2;
        Handler handler;
        DataClient dataClient8;
        boolean z17;
        TextView textView25;
        String str4;
        String str5;
        VideoClient videoClient;
        VideoClient videoClient2;
        VideoClient videoClient3;
        VideoClient videoClient4;
        VideoClient videoClient5;
        Detector detector2;
        Telemetry t2;
        TextView textView26;
        StringBuilder sb;
        String str6;
        String str7;
        SceneView it;
        UiSettings uiSettings6;
        OverlayView overlayView4;
        String str8;
        SceneView sceneView;
        String str9;
        String str10;
        VideoClient videoClient6;
        VideoClient videoClient7;
        String str11;
        LayoutSeg layoutSeg;
        LayoutSeg layoutSeg2;
        String str12;
        String format;
        LayoutSeg layoutSeg3;
        LayoutSeg layoutSeg4;
        LayoutSeg layoutSeg5;
        LayoutSeg layoutSeg6;
        String str13;
        LayoutSeg layoutSeg7;
        LaneMarks laneMarks;
        int i22;
        String str14;
        int i23;
        int i24;
        int i25;
        int rgb;
        GpsCardView gpsCardView3;
        int i26;
        DevSidebarView devSidebarView3;
        int i27;
        LinearLayout linearLayout5;
        LinearLayout linearLayout6;
        float f7;
        TextView textView27;
        TextView textView28;
        LinearLayout linearLayout7;
        LinearLayout linearLayout8;
        int rgb2;
        View view2;
        LinearLayout linearLayout9;
        View view3;
        View view4;
        float f8;
        GradientDrawable gradientDrawable;
        GradientDrawable gradientDrawable2;
        LinearLayout linearLayout10;
        TextView textView29;
        TextView textView30;
        TextView textView31;
        TextView textView32;
        TextView textView33;
        TextView textView34;
        long j7;
        long j8;
        long j9;
        long j10;
        TextView textView35;
        DataClient dataClient9;
        boolean z18;
        HudEditor hudEditor;
        MaskEditor maskEditor;
        boolean z19;
        DataClient dataClient10;
        int i28;
        UiSettings uiSettings7;
        boolean mapOpen;
        HudEditor hudEditor2;
        MaskEditor maskEditor2;
        boolean z20;
        boolean mapOpen2;
        HudEditor hudEditor3;
        MaskEditor maskEditor3;
        LaneSuggestView laneSuggestView7;
        LaneSuggestView laneSuggestView8;
        LaneSuggestView laneSuggestView9;
        LaneSuggestView laneSuggestView10;
        LaneSuggestView laneSuggestView11;
        LaneSuggestView laneSuggestView12;
        CarMemory carMemory;
        CarMemory carMemory2;
        CarMemory carMemory3;
        LaneShots laneShots2;
        boolean z21;
        LaneShots laneShots3;
        LaneBrain laneBrain12;
        LaneBrain laneBrain13;
        LaneBrain laneBrain14;
        Detector detector3;
        LaneModel laneModel;
        LaneModel.Answer a;
        LaneBrain laneBrain15;
        LaneFeed laneFeed;
        LaneMarks laneMarks2;
        LaneBrain laneBrain16;
        double lat;
        double brg;
        LaneAhead laneAhead4;
        LaneBrain laneBrain17;
        LaneBrain laneBrain18;
        LaneMapLog laneMapLog2;
        LaneBrain laneBrain19;
        LaneBrain laneBrain20;
        SlowReasonView slowReasonView3;
        boolean mapOpen3;
        int i29;
        int i30;
        ImageView imageView12;
        ImageView imageView13;
        ImageView imageView14;
        ImageView imageView15;
        ImageView imageView16;
        ImageView imageView17;
        ImageView imageView18;
        TextView textView36;
        TextView textView37;
        TextView textView38;
        String str15;
        SlcActionsView slcActionsView3;
        UiSettings uiSettings8;
        boolean z22;
        MaskEditor maskEditor4;
        UiSettings uiSettings9;
        boolean z23;
        boolean z24;
        boolean z25;
        boolean z26;
        int i31;
        OffroadHome offroadHome2;
        int i32;
        int i33;
        OffroadHome offroadHome3;
        OffroadHome offroadHome4;
        LinearLayout linearLayout11;
        UiSettings uiSettings10;
        HudEditor hudEditor4;
        Function0 function0;
        long j11;
        long j12;
        TextView gpuBanner3;
        TextView gpuBanner4;
        TextView gpuBanner5;
        TextView stallBanner3;
        TextView stallBanner4;
        i = this.this$0.tick;
        this.this$0.tick = i + 1;
        t = this.this$0.telemetry;
        dataClient = this.this$0.data;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        boolean connected2 = dataClient.getConnected();
        this.this$0.saverTick(connected2);
        linearLayout = this.this$0.connectBar;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("connectBar");
            linearLayout = null;
        }
        linearLayout.setVisibility(connected2 ? 8 : 0);
        Object obj = t.getInfo().get("encStall");
        Number number = obj instanceof Number ? (Number) obj : null;
        int stall2 = number != null ? number.intValue() : 0;
        if (!connected2 || stall2 <= 0) {
            stallBanner = this.this$0.getStallBanner();
            if (stallBanner.getVisibility() != 8) {
                stallBanner2 = this.this$0.getStallBanner();
                stallBanner2.setVisibility(8);
            }
        } else {
            stallBanner3 = this.this$0.getStallBanner();
            stallBanner3.setText("Comma video encoder stopped " + stall2 + " s ago: no drive recording, no camera video.\nReboot the comma when parked.");
            stallBanner4 = this.this$0.getStallBanner();
            stallBanner4.setVisibility(0);
        }
        boolean quiet = connected2 && t.getStarted() && t.pBool("GpuQuiet", false);
        boolean loadingNow = connected2 && t.getStarted() && (quiet || Intrinsics.areEqual(Telemetry.pStr$default(t, "ChestnutState", null, 2, null), "loading"));
        if (loadingNow) {
            long nowMs = System.currentTimeMillis();
            j11 = this.this$0.gpuLoadSince;
            if (j11 == 0) {
                this.this$0.gpuLoadSince = nowMs;
            }
            j12 = this.this$0.gpuLoadSince;
            stall = stall2;
            long secs = (nowMs - j12) / 1000;
            gpuBanner3 = this.this$0.getGpuBanner();
            gpuBanner3.setText("⏳  Big model still loading… " + secs + " s" + (quiet ? "\ncamera video back when it's ready" : ""));
            gpuBanner4 = this.this$0.getGpuBanner();
            gpuBanner4.setAlpha((float) ((((Math.cos((nowMs / 1000.0d) * 4.0d) * 0.5d) + 0.5d) * 0.55d) + 0.45d));
            gpuBanner5 = this.this$0.getGpuBanner();
            gpuBanner5.setVisibility(0);
        } else {
            stall = stall2;
            this.this$0.gpuLoadSince = 0L;
            gpuBanner = this.this$0.getGpuBanner();
            if (gpuBanner.getVisibility() != 8) {
                gpuBanner2 = this.this$0.getGpuBanner();
                gpuBanner2.setVisibility(8);
            }
        }
        i2 = this.this$0.tick;
        if (i2 % 20 == 0 && function0 != null) {
            function0.invoke();
            Unit unit = Unit.INSTANCE;
        }
        i3 = this.this$0.tick;
        if (i3 % 100 == 50) {
            this.this$0.checkUsbReturn();
        }
        if (connected2) {
            this.this$0.navTick(System.currentTimeMillis());
        }
        MainActivity mainActivity = this.this$0;
        uiSettings = this.this$0.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        mainActivity.statsVisible = uiSettings.get("stats");
        textView = this.this$0.statsTv;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statsTv");
            textView = null;
        }
        z = this.this$0.statsVisible;
        textView.setVisibility(z ? 0 : 8);
        if (connected2 && !t.getStarted()) {
            uiSettings10 = this.this$0.settings;
            if (uiSettings10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("settings");
                uiSettings10 = null;
            }
            if (uiSettings10.get("offroad_home")) {
                hudEditor4 = this.this$0.hudEditor;
                if (hudEditor4 == null) {
                    z2 = true;
                    wantHome = z2;
                    z3 = this.this$0.homeShown;
                    if (wantHome != z3) {
                        this.this$0.homeShown = wantHome;
                        offroadHome4 = this.this$0.offroadHome;
                        if (offroadHome4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
                            offroadHome4 = null;
                        }
                        offroadHome4.setVisibility(wantHome ? 0 : 8);
                        linearLayout11 = this.this$0.sidebar;
                        if (linearLayout11 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
                            linearLayout11 = null;
                        }
                        linearLayout11.setVisibility(wantHome ? 0 : 8);
                    }
                    if (wantHome) {
                        i4 = 2;
                    } else {
                        offroadHome3 = this.this$0.offroadHome;
                        if (offroadHome3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
                            offroadHome3 = null;
                        }
                        i4 = 2;
                        OffroadHome.refresh$default(offroadHome3, t, false, 2, null);
                    }
                    if (wantHome) {
                        i33 = this.this$0.tick;
                        if (i33 % 600 == 30) {
                            KeepDrive.INSTANCE.applyPending(this.this$0);
                        }
                    }
                    StorageInfo storageInfo = StorageInfo.INSTANCE;
                    offroadHome = this.this$0.offroadHome;
                    if (offroadHome == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
                        offroadHome = null;
                    }
                    boolean copying = storageInfo.copying(offroadHome.getStorage()) == null;
                    if (wantHome) {
                        i31 = this.this$0.tick;
                        if (i31 % 300 != 5) {
                            offroadHome2 = this.this$0.offroadHome;
                            if (offroadHome2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
                                offroadHome2 = null;
                            }
                            if (offroadHome2.getStorage() == null || copying) {
                                i32 = this.this$0.tick;
                            }
                        }
                        this.this$0.fetchStorage();
                    }
                    textView2 = this.this$0.statusTv;
                    if (textView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("statusTv");
                        textView2 = null;
                    }
                    textView2.setVisibility(((connected2 || !t.getStarted()) && !wantHome) ? 0 : 8);
                    this.this$0.syncScene(!connected2 && t.getStarted());
                    if (connected2 && t.getStarted()) {
                        z25 = this.this$0.sceneOn;
                        if (!z25) {
                            z26 = this.this$0.visible;
                            if (z26) {
                                z4 = true;
                                wantVideo = z4;
                                if (wantVideo) {
                                    z24 = this.this$0.videoConnected;
                                    if (!z24) {
                                        this.this$0.startVideo();
                                    }
                                }
                                if (!wantVideo) {
                                    z23 = this.this$0.videoConnected;
                                    if (z23) {
                                        this.this$0.stopVideo();
                                    }
                                }
                                MainActivity mainActivity2 = this.this$0;
                                z5 = this.this$0.visible;
                                if (z5 && connected2 && t.getStarted()) {
                                    uiSettings8 = this.this$0.settings;
                                    if (uiSettings8 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                                        uiSettings8 = null;
                                    }
                                    if (!uiSettings8.get("detector_test")) {
                                        z22 = this.this$0.sceneOn;
                                        if (!z22) {
                                            maskEditor4 = this.this$0.maskEditor;
                                            if (maskEditor4 == null) {
                                                NewIdeas newIdeas = NewIdeas.INSTANCE;
                                                uiSettings9 = this.this$0.settings;
                                                if (uiSettings9 == null) {
                                                    Intrinsics.throwUninitializedPropertyAccessException("settings");
                                                    uiSettings9 = null;
                                                }
                                            }
                                        }
                                    }
                                    z6 = true;
                                    mainActivity2.syncDetector(z6);
                                    OsmRoads osmRoads = OsmRoads.INSTANCE;
                                    uiSettings2 = this.this$0.settings;
                                    if (uiSettings2 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                                        uiSettings2 = null;
                                    }
                                    osmRoads.setEnabled(uiSettings2.get("map_roads"));
                                    overlayView = this.this$0.overlay;
                                    if (overlayView == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("overlay");
                                        overlayView = null;
                                    }
                                    if (overlayView.updateCalibration()) {
                                        this.this$0.placeVideo();
                                    }
                                    overlayView2 = this.this$0.overlay;
                                    if (overlayView2 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("overlay");
                                        overlayView2 = null;
                                    }
                                    overlayView2.setUiStatus(UiStatus.Companion.from(t));
                                    currentTimeMillis = System.currentTimeMillis();
                                    j = this.this$0.lastThemeCheck;
                                    if (currentTimeMillis - j > 15000) {
                                        this.this$0.lastThemeCheck = System.currentTimeMillis();
                                        this.this$0.updateTheme();
                                    }
                                    boolean metric = t.isMetric();
                                    float conv = metric ? 3.6f : 2.23694f;
                                    boolean useWheel = t.tBool("use_wheel_speed", false);
                                    float v = (!useWheel || t.getVEgoCluster() <= 0.0f) ? t.getVEgo() : t.getVEgoCluster();
                                    textView3 = this.this$0.speedTv;
                                    if (textView3 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("speedTv");
                                        textView3 = null;
                                    }
                                    textView3.setText(String.valueOf(RangesKt.coerceAtLeast(MathKt.roundToInt(v * conv), 0)));
                                    textView4 = this.this$0.speedUnit;
                                    if (textView4 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
                                        textView4 = null;
                                    }
                                    textView4.setText(metric ? "km/h" : "mph");
                                    boolean hideSpeed = t.s("show_speed") || !t.getStarted();
                                    textView5 = this.this$0.speedTv;
                                    if (textView5 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("speedTv");
                                        textView5 = null;
                                    }
                                    textView5.setVisibility(hideSpeed ? 4 : 0);
                                    textView6 = this.this$0.speedUnit;
                                    if (textView6 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
                                        textView6 = null;
                                    }
                                    textView7 = this.this$0.speedTv;
                                    if (textView7 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("speedTv");
                                        textView7 = null;
                                    }
                                    textView6.setVisibility(textView7.getVisibility());
                                    float setMs = t.getVCruiseCluster() > 0.0f ? t.getVCruiseCluster() : t.getVCruise();
                                    isSet = !t.getCruiseEnabled() && setMs > 0.0f && setMs < 200.0f;
                                    float offset = t.tFloat("set_speed_offset", 0.0f);
                                    textView8 = this.this$0.setSpeedTv;
                                    if (textView8 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("setSpeedTv");
                                        textView8 = null;
                                    }
                                    if (isSet) {
                                        str = String.valueOf(MathKt.roundToInt((setMs + offset) * (metric ? 1.0f : 0.621371f)));
                                    } else {
                                        str = "–";
                                    }
                                    textView8.setText(str);
                                    textView9 = this.this$0.setSpeedTv;
                                    if (textView9 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("setSpeedTv");
                                        textView9 = null;
                                    }
                                    textView9.setTextColor(isSet ? -1 : Color.rgb(114, 114, 114));
                                    textView10 = this.this$0.maxLabel;
                                    if (textView10 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("maxLabel");
                                        textView10 = null;
                                    }
                                    textView10.setTextColor(!isSet ? Color.rgb(166, 166, 166) : (t.getEnabled() && t.getActive()) ? Color.rgb(128, 216, 166) : Color.rgb(145, 155, 149));
                                    linearLayout2 = this.this$0.maxCard;
                                    if (linearLayout2 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("maxCard");
                                        linearLayout2 = null;
                                    }
                                    linearLayout2.setVisibility((t.s("show_max") || !t.getStarted()) ? 4 : 0);
                                    slcView = this.this$0.slc;
                                    if (slcView == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("slc");
                                        slcView = null;
                                    }
                                    slcView.setVisibility((t.getStarted() || !t.s("speed_limit")) ? 4 : 0);
                                    wheelButton = this.this$0.wheel;
                                    if (wheelButton == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("wheel");
                                        wheelButton = null;
                                    }
                                    wheelButton.setVisibility((t.s("wheel_button") || !t.getStarted()) ? 4 : 0);
                                    miciDistanceView = this.this$0.personality;
                                    if (miciDistanceView == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("personality");
                                        miciDistanceView = null;
                                    }
                                    miciDistanceView.setVisibility((t.getStarted() || !t.s("personality_button")) ? 8 : 0);
                                    slcActionsView = this.this$0.slcActions;
                                    if (slcActionsView == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("slcActions");
                                        slcActionsView = null;
                                    }
                                    if (t.getStarted() || !t.s("speed_limit")) {
                                        j2 = 0;
                                    } else {
                                        slcActionsView3 = this.this$0.slcActions;
                                        if (slcActionsView3 == null) {
                                            Intrinsics.throwUninitializedPropertyAccessException("slcActions");
                                            slcActionsView3 = null;
                                        }
                                        j2 = 0;
                                        if (!SlcActionsView.controls$default(slcActionsView3, 0L, 1, null).isEmpty()) {
                                            i5 = 0;
                                            slcActionsView.setVisibility(i5);
                                            if (t.getStarted() || !t.s("csc_glow")) {
                                                i6 = i4;
                                                pair = null;
                                            } else {
                                                i6 = i4;
                                                pair = DrivingMode.curveLabel$default(DrivingMode.INSTANCE, t, 0L, 2, null);
                                            }
                                            cl = pair;
                                            textView11 = this.this$0.curveLabel;
                                            if (textView11 == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("curveLabel");
                                                textView11 = null;
                                            }
                                            textView11.setText((cl != null || (str15 = (String) cl.getFirst()) == null) ? "" : str15);
                                            if (cl != null) {
                                                textView38 = this.this$0.curveLabel;
                                                if (textView38 == null) {
                                                    Intrinsics.throwUninitializedPropertyAccessException("curveLabel");
                                                    textView38 = null;
                                                }
                                                textView38.setTextColor(((Number) cl.getSecond()).intValue());
                                                Unit unit2 = Unit.INSTANCE;
                                                Unit unit3 = Unit.INSTANCE;
                                            }
                                            textView12 = this.this$0.curveLabel;
                                            if (textView12 == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("curveLabel");
                                                textView12 = null;
                                            }
                                            textView12.setVisibility(cl == null ? 0 : 8);
                                            boolean noAlert = Intrinsics.areEqual(t.getAlertSize(), "none");
                                            textView13 = this.this$0.clockTv;
                                            if (textView13 == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("clockTv");
                                                textView13 = null;
                                            }
                                            textView13.setVisibility((!t.getStarted() && t.s("clock") && noAlert) ? 0 : 8);
                                            textView14 = this.this$0.clockTv;
                                            if (textView14 == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("clockTv");
                                                textView14 = null;
                                            }
                                            if (textView14.getVisibility() == 0) {
                                                textView37 = this.this$0.clockTv;
                                                if (textView37 == null) {
                                                    Intrinsics.throwUninitializedPropertyAccessException("clockTv");
                                                    textView37 = null;
                                                }
                                                textView37.setText(DrivingMode.clock$default(DrivingMode.INSTANCE, null, t.s("clock_24h"), 1, null));
                                            }
                                            textView15 = this.this$0.modeText;
                                            if (textView15 == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("modeText");
                                                textView15 = null;
                                            }
                                            textView15.setVisibility((!t.getStarted() && t.s("mode_text") && noAlert) ? 0 : 8);
                                            textView16 = this.this$0.modeText;
                                            if (textView16 == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("modeText");
                                                textView16 = null;
                                            }
                                            if (textView16.getVisibility() == 0) {
                                                textView36 = this.this$0.modeText;
                                                if (textView36 == null) {
                                                    Intrinsics.throwUninitializedPropertyAccessException("modeText");
                                                    textView36 = null;
                                                }
                                                textView36.setText(DrivingMode.INSTANCE.describe(t));
                                            }
                                            devSidebarView = this.this$0.devSidebar;
                                            if (devSidebarView == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("devSidebar");
                                                devSidebarView = null;
                                            }
                                            devSidebarView.setVisibility((t.getStarted() || !t.s("dev_sidebar")) ? 8 : 0);
                                            SideCam sideCam = SideCam.INSTANCE;
                                            str2 = this.this$0.host;
                                            i7 = this.this$0.port;
                                            int stall3 = 0;
                                            SideCam.tick$default(sideCam, t, str2, i7, 0L, 8, null);
                                            List sides = SideCam.INSTANCE.wanted(t);
                                            sideView = this.this$0.pipLeft;
                                            if (sideView == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("pipLeft");
                                                sideView = null;
                                            }
                                            sideView.setVisibility(!sides.contains("left") ? 0 : 8);
                                            sideView2 = this.this$0.pipRight;
                                            if (sideView2 == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("pipRight");
                                                sideView2 = null;
                                            }
                                            sideView2.setVisibility(!sides.contains("right") ? 0 : 8);
                                            cnState = Telemetry.pStr$default(t, "ChestnutState", null, i6, null);
                                            if (cnState.length() <= 0) {
                                                MainActivity mainActivity3 = this.this$0;
                                                if (!t.getChestnutPresent()) {
                                                    if (t.getStarted()) {
                                                        z10 = mainActivity3.egpuSeen;
                                                    }
                                                    usbgpu = false;
                                                    mainActivity3.egpuSeen = usbgpu;
                                                    show = !t.getStarted() && t.s("egpu_status") && usbgpu && t.pBool("UsbGpuCompiled", false);
                                                    if (show) {
                                                        imageView10 = mainActivity3.egpu;
                                                        if (imageView10 == null) {
                                                            Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                            imageView11 = null;
                                                        } else {
                                                            imageView11 = imageView10;
                                                        }
                                                        imageView11.setVisibility(8);
                                                        mainActivity3.egpuFallback = false;
                                                    } else {
                                                        boolean loading = t.pBool("UsbGpuLoading", false);
                                                        boolean active2 = t.pBool("UsbGpuActive", false);
                                                        boolean alive = t.getModelSeen() && System.nanoTime() - t.getLastModelRxNs() < 1000000000;
                                                        boolean bigFailed = !active2 || (t.getModelSeen() && !alive);
                                                        if (t.getEnabled()) {
                                                            z9 = mainActivity3.egpuWasEngaged;
                                                            if (!z9 && !loading && !active2 && t.getModelSeen()) {
                                                                mainActivity3.egpuFallback = true;
                                                            }
                                                        }
                                                        mainActivity3.egpuWasEngaged = t.getEnabled();
                                                        z7 = mainActivity3.egpuFallback;
                                                        mainActivity3.egpuFallback = z7 && bigFailed;
                                                        imageView = mainActivity3.egpu;
                                                        if (imageView == null) {
                                                            Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                            imageView = null;
                                                        }
                                                        imageView.setVisibility(0);
                                                        if (loading) {
                                                            imageView8 = mainActivity3.egpu;
                                                            if (imageView8 == null) {
                                                                Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                imageView8 = null;
                                                            }
                                                            imageView8.setImageResource(R.drawable.egpu_loading);
                                                            imageView9 = mainActivity3.egpu;
                                                            if (imageView9 == null) {
                                                                Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                imageView9 = null;
                                                            }
                                                            imageView9.setAlpha((float) (((0.5d - (Math.cos((System.nanoTime() / 1.0E9d) * 6.0d) * 0.5d)) * 0.65d) + 0.35d));
                                                        } else {
                                                            z8 = mainActivity3.egpuFallback;
                                                            if (z8) {
                                                                imageView6 = mainActivity3.egpu;
                                                                if (imageView6 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                    imageView6 = null;
                                                                }
                                                                imageView6.setImageResource(R.drawable.egpu_crossed);
                                                                imageView7 = mainActivity3.egpu;
                                                                if (imageView7 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                    imageView7 = null;
                                                                }
                                                                imageView7.setAlpha(0.65f);
                                                            } else if (bigFailed) {
                                                                imageView4 = mainActivity3.egpu;
                                                                if (imageView4 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                    imageView4 = null;
                                                                }
                                                                imageView4.setImageResource(R.drawable.egpu_orange);
                                                                imageView5 = mainActivity3.egpu;
                                                                if (imageView5 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                    imageView5 = null;
                                                                }
                                                                imageView5.setAlpha(1.0f);
                                                            } else {
                                                                imageView2 = mainActivity3.egpu;
                                                                if (imageView2 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                    imageView2 = null;
                                                                }
                                                                imageView2.setImageResource(R.drawable.egpu_green);
                                                                imageView3 = mainActivity3.egpu;
                                                                if (imageView3 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                    imageView3 = null;
                                                                }
                                                                imageView3.setAlpha(1.0f);
                                                            }
                                                        }
                                                    }
                                                    Unit unit4 = Unit.INSTANCE;
                                                }
                                                usbgpu = true;
                                                mainActivity3.egpuSeen = usbgpu;
                                                if (t.getStarted()) {
                                                }
                                                if (show) {
                                                }
                                                Unit unit42 = Unit.INSTANCE;
                                            } else {
                                                if (t.getStarted() && t.s("egpu_status")) {
                                                    String[] strArr = new String[3];
                                                    i29 = 0;
                                                    strArr[0] = "active";
                                                    strArr[1] = "loading";
                                                    strArr[i6] = "failed";
                                                    if (SetsKt.setOf((Object[]) strArr).contains(cnState)) {
                                                        i30 = 1;
                                                        imageView12 = this.this$0.egpu;
                                                        if (imageView12 == null) {
                                                            Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                            imageView12 = null;
                                                        }
                                                        imageView12.setVisibility(i30 == 0 ? i29 : 8);
                                                        if (i30 != 0) {
                                                            if (Intrinsics.areEqual(cnState, "loading")) {
                                                                imageView17 = this.this$0.egpu;
                                                                if (imageView17 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                    imageView17 = null;
                                                                }
                                                                imageView17.setImageResource(R.drawable.egpu_loading);
                                                                imageView18 = this.this$0.egpu;
                                                                if (imageView18 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                    imageView18 = null;
                                                                }
                                                                imageView18.setAlpha((float) (((0.5d - (Math.cos((System.nanoTime() / 1.0E9d) * 6.0d) * 0.5d)) * 0.65d) + 0.35d));
                                                            } else if (Intrinsics.areEqual(cnState, "failed")) {
                                                                imageView15 = this.this$0.egpu;
                                                                if (imageView15 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                    imageView15 = null;
                                                                }
                                                                imageView15.setImageResource(R.drawable.egpu_orange);
                                                                imageView16 = this.this$0.egpu;
                                                                if (imageView16 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                    imageView16 = null;
                                                                }
                                                                imageView16.setAlpha(1.0f);
                                                            } else {
                                                                imageView13 = this.this$0.egpu;
                                                                if (imageView13 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                    imageView13 = null;
                                                                }
                                                                imageView13.setImageResource(R.drawable.egpu_green);
                                                                imageView14 = this.this$0.egpu;
                                                                if (imageView14 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("egpu");
                                                                    imageView14 = null;
                                                                }
                                                                imageView14.setAlpha(1.0f);
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    i29 = 0;
                                                }
                                                i30 = i29;
                                                imageView12 = this.this$0.egpu;
                                                if (imageView12 == null) {
                                                }
                                                imageView12.setVisibility(i30 == 0 ? i29 : 8);
                                                if (i30 != 0) {
                                                }
                                            }
                                            textView17 = this.this$0.roadName;
                                            if (textView17 == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("roadName");
                                                textView17 = null;
                                            }
                                            textView17.setText((t.getStarted() || !t.s("road_name")) ? "" : t.getRoadName());
                                            aetherGaugeView = this.this$0.gauge;
                                            if (aetherGaugeView == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("gauge");
                                                aetherGaugeView = null;
                                            }
                                            aetherGaugeView.setVisibility((t.getStarted() || !t.s("aether_gauge")) ? 8 : 0);
                                            slcSourcesView = this.this$0.slcSources;
                                            if (slcSourcesView == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("slcSources");
                                                slcSourcesView = null;
                                            }
                                            if (t.getStarted() && t.s("speed_limit")) {
                                                if (!t.getSp7() ? t.s("slc_sources_sp7") : t.pBool("SpeedLimitSources", false)) {
                                                    i8 = 0;
                                                    slcSourcesView.setVisibility(i8);
                                                    Pair card = Telemetry.navCard$default(t, 0L, 1, null);
                                                    navCardView = this.this$0.navCard;
                                                    if (navCardView == null) {
                                                        Intrinsics.throwUninitializedPropertyAccessException("navCard");
                                                        navCardView = null;
                                                    }
                                                    if (t.getStarted() && t.s("nav_card") && card != null) {
                                                        mapOpen3 = this.this$0.getMapOpen();
                                                        if (!mapOpen3) {
                                                            i9 = 0;
                                                            navCardView.setVisibility(i9);
                                                            navCardView2 = this.this$0.navCard;
                                                            if (navCardView2 == null) {
                                                                Intrinsics.throwUninitializedPropertyAccessException("navCard");
                                                                navCardView2 = null;
                                                            }
                                                            navCardView2.setAlpha(!(card == null && ((Boolean) card.getSecond()).booleanValue()) ? 0.55f : 1.0f);
                                                            slowReasonView = this.this$0.slowCard;
                                                            if (slowReasonView == null) {
                                                                Intrinsics.throwUninitializedPropertyAccessException("slowCard");
                                                                slowReasonView = null;
                                                            }
                                                            if (t.getStarted() && t.s("slow_card")) {
                                                                slowReasonView3 = this.this$0.slowCard;
                                                                if (slowReasonView3 == null) {
                                                                    Intrinsics.throwUninitializedPropertyAccessException("slowCard");
                                                                    slowReasonView3 = null;
                                                                }
                                                                if (slowReasonView3.current() != null) {
                                                                    i10 = 0;
                                                                    slowReasonView.setVisibility(i10);
                                                                    if (t.getStarted()) {
                                                                        laneFeed = this.this$0.laneFeed;
                                                                        laneMarks2 = this.this$0.laneMarks;
                                                                        LaneBrain.Frame f9 = laneFeed.frame(t, laneMarks2, System.currentTimeMillis());
                                                                        if (f9 != null) {
                                                                            MainActivity mainActivity4 = this.this$0;
                                                                            laneBrain16 = mainActivity4.laneTracker;
                                                                            laneBrain16.update(f9);
                                                                            long nowL = f9.getNowMs();
                                                                            try {
                                                                                laneMapLog2 = mainActivity4.laneMapLog;
                                                                                if (laneMapLog2 != null) {
                                                                                    float vEgo = t.getVEgo();
                                                                                    laneBrain19 = mainActivity4.laneTracker;
                                                                                    laneBrain20 = mainActivity4.laneTracker;
                                                                                    laneMapLog2.frame(nowL, vEgo, f9, laneBrain19, laneBrain20.turning(nowL));
                                                                                    Unit unit5 = Unit.INSTANCE;
                                                                                }
                                                                            } catch (Exception e) {
                                                                            }
                                                                            double[] rp = RtkPosition.INSTANCE.fast(nowL) ? RtkPosition.INSTANCE.now(nowL, t.getVEgo()) : null;
                                                                            OsmRoads.Fix fx = OsmRoads.INSTANCE.getLastFix();
                                                                            if (rp != null) {
                                                                                i11 = 0;
                                                                                lat = rp[0];
                                                                            } else {
                                                                                i11 = 0;
                                                                                lat = fx != null ? fx.getLat() : Double.NaN;
                                                                            }
                                                                            double la = lat;
                                                                            double lo = rp != null ? rp[1] : fx != null ? fx.getLon() : Double.NaN;
                                                                            if (rp != null) {
                                                                                double heading = RtkPosition.INSTANCE.getHeading();
                                                                                if (((Double.isInfinite(heading) || Double.isNaN(heading)) ? i11 : 1) != 0) {
                                                                                    brg = RtkPosition.INSTANCE.getHeading();
                                                                                    laneAhead4 = mainActivity4.laneAhead;
                                                                                    laneBrain17 = mainActivity4.laneTracker;
                                                                                    double odo = laneBrain17.getOdo();
                                                                                    double radians = 1.5707963267948966d - Math.toRadians(brg);
                                                                                    float vEgo2 = t.getVEgo();
                                                                                    laneBrain18 = mainActivity4.laneTracker;
                                                                                    laneAhead4.update(nowL, odo, la, lo, radians, vEgo2, laneBrain18, f9);
                                                                                    Unit unit6 = Unit.INSTANCE;
                                                                                    Unit unit7 = Unit.INSTANCE;
                                                                                }
                                                                            }
                                                                            brg = fx != null ? fx.getBearingDeg() : Double.NaN;
                                                                            laneAhead4 = mainActivity4.laneAhead;
                                                                            laneBrain17 = mainActivity4.laneTracker;
                                                                            double odo2 = laneBrain17.getOdo();
                                                                            double radians2 = 1.5707963267948966d - Math.toRadians(brg);
                                                                            float vEgo22 = t.getVEgo();
                                                                            laneBrain18 = mainActivity4.laneTracker;
                                                                            laneAhead4.update(nowL, odo2, la, lo, radians2, vEgo22, laneBrain18, f9);
                                                                            Unit unit62 = Unit.INSTANCE;
                                                                            Unit unit72 = Unit.INSTANCE;
                                                                        } else {
                                                                            i11 = 0;
                                                                        }
                                                                    } else {
                                                                        i11 = 0;
                                                                        z11 = this.this$0.laneWasStarted;
                                                                        if (z11) {
                                                                            laneBrain = this.this$0.laneTracker;
                                                                            laneBrain.endDrive();
                                                                            laneAhead = this.this$0.laneAhead;
                                                                            laneAhead.reset();
                                                                            laneMapLog = this.this$0.laneMapLog;
                                                                            if (laneMapLog != null) {
                                                                                laneMapLog.close();
                                                                                Unit unit8 = Unit.INSTANCE;
                                                                            }
                                                                        }
                                                                    }
                                                                    if (t.getStarted() && detector3 != null && (laneModel = detector3.getLaneModel()) != null && (a = laneModel.getAnswer()) != null) {
                                                                        MainActivity mainActivity5 = this.this$0;
                                                                        if (System.currentTimeMillis() - a.getAtMs() < 5000) {
                                                                            laneBrain15 = mainActivity5.laneTracker;
                                                                            LaneBrain.modelSaw$default(laneBrain15, a.getPL(), a.getPR(), a.getAtMs(), 0L, 8, null);
                                                                        }
                                                                        Unit unit9 = Unit.INSTANCE;
                                                                        Unit unit10 = Unit.INSTANCE;
                                                                    }
                                                                    s = this.this$0.scene;
                                                                    if (s != null) {
                                                                        MainActivity mainActivity6 = this.this$0;
                                                                        laneBrain12 = mainActivity6.laneTracker;
                                                                        LaneBrain.Road r2 = laneBrain12.getRoad();
                                                                        laneBrain13 = mainActivity6.laneTracker;
                                                                        Pair<Integer, Double> laneFromLeft = laneBrain13.laneFromLeft();
                                                                        int n = laneFromLeft.component1().intValue();
                                                                        double q = laneFromLeft.component2().doubleValue();
                                                                        laneBrain14 = mainActivity6.laneTracker;
                                                                        int m = laneBrain14.laneCount().component1().intValue();
                                                                        s.setLaneHint((!t.getStarted() || r2 == null || n <= 0) ? null : new SceneView.LaneHint(r2.getId(), n - 1, m, q, Double.NaN, System.nanoTime()));
                                                                        Unit unit11 = Unit.INSTANCE;
                                                                        Unit unit12 = Unit.INSTANCE;
                                                                    }
                                                                    if (t.getStarted()) {
                                                                        z21 = this.this$0.laneWasStarted;
                                                                        if (!z21) {
                                                                            laneShots3 = this.this$0.getLaneShots();
                                                                            laneShots3.newDrive();
                                                                        }
                                                                    }
                                                                    this.this$0.laneWasStarted = t.getStarted();
                                                                    laneCardView = this.this$0.laneCard;
                                                                    if (laneCardView == null) {
                                                                        Intrinsics.throwUninitializedPropertyAccessException("laneCard");
                                                                        laneCardView = null;
                                                                    }
                                                                    laneCardView.setVisibility((t.getStarted() || !t.s("lane_card")) ? 8 : i11);
                                                                    laneShots = this.this$0.getLaneShots();
                                                                    detector = this.this$0.detectorRef;
                                                                    laneShots.setDetector(detector);
                                                                    laneCardView2 = this.this$0.laneCard;
                                                                    if (laneCardView2 == null) {
                                                                        Intrinsics.throwUninitializedPropertyAccessException("laneCard");
                                                                        laneCardView2 = null;
                                                                    }
                                                                    if (laneCardView2.getVisibility() == 0 && t.getVEgo() > 3.0f) {
                                                                        laneShots2 = this.this$0.getLaneShots();
                                                                        final MainActivity mainActivity7 = this.this$0;
                                                                        laneShots2.tick(new Function0() { // from class: com.starpilot.starview.MainActivity$uiTick$1$$ExternalSyntheticLambda0
                                                                            @Override // kotlin.jvm.functions.Function0
                                                                            public final Object invoke() {
                                                                                String run$lambda$5;
                                                                                run$lambda$5 = MainActivity$uiTick$1.run$lambda$5(mainActivity7, t);
                                                                                return run$lambda$5;
                                                                            }
                                                                        });
                                                                    }
                                                                    blinkerCardView = this.this$0.blinkCard;
                                                                    if (blinkerCardView == null) {
                                                                        Intrinsics.throwUninitializedPropertyAccessException("blinkCard");
                                                                        blinkerCardView = null;
                                                                    }
                                                                    dataClient2 = this.this$0.data;
                                                                    if (dataClient2 == null) {
                                                                        Intrinsics.throwUninitializedPropertyAccessException("data");
                                                                        dataClient2 = null;
                                                                    }
                                                                    blinkerCardView.setVisibility((dataClient2.getConnected() || !t.s("blink_card")) ? 8 : i11);
                                                                    gpsCardView = this.this$0.gpsCard;
                                                                    if (gpsCardView == null) {
                                                                        Intrinsics.throwUninitializedPropertyAccessException("gpsCard");
                                                                        gpsCardView = null;
                                                                    }
                                                                    dataClient3 = this.this$0.data;
                                                                    if (dataClient3 == null) {
                                                                        Intrinsics.throwUninitializedPropertyAccessException("data");
                                                                        dataClient3 = null;
                                                                    }
                                                                    gpsCardView.setVisibility((dataClient3.getConnected() || !t.s("gps_card")) ? 8 : i11);
                                                                    boolean laneOn = t.s("lane_suggest");
                                                                    boolean turnOn = t.s("turn_signals");
                                                                    if (t.s("car_memory")) {
                                                                        carMemory3 = this.this$0.carMemory;
                                                                        cnState2 = cnState;
                                                                        i12 = i11;
                                                                        carMemory3.update(t, System.currentTimeMillis(), t.getLeftBlindspot(), t.getRightBlindspot());
                                                                    } else {
                                                                        cnState2 = cnState;
                                                                        i12 = i11;
                                                                    }
                                                                    if (!t.getLeftBlindspot()) {
                                                                        if (t.s("car_memory")) {
                                                                            carMemory2 = this.this$0.carMemory;
                                                                        }
                                                                        z12 = i12;
                                                                        if (!t.getRightBlindspot()) {
                                                                            if (t.s("car_memory")) {
                                                                                carMemory = this.this$0.carMemory;
                                                                            }
                                                                            z13 = i12;
                                                                            active = Replay.INSTANCE.getActive();
                                                                            z14 = this.this$0.replayWas;
                                                                            if (active != z14) {
                                                                                this.this$0.replayWas = Replay.INSTANCE.getActive();
                                                                                laneSuggestView12 = this.this$0.laneSuggest;
                                                                                if (laneSuggestView12 == null) {
                                                                                    Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                                                                                    laneSuggestView12 = null;
                                                                                }
                                                                                laneSuggestView12.stop();
                                                                            }
                                                                            if (!t.getStarted()) {
                                                                                laneSuggestView10 = this.this$0.laneSuggest;
                                                                                if (laneSuggestView10 == null) {
                                                                                    Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                                                                                    laneSuggestView10 = null;
                                                                                }
                                                                                if (!laneSuggestView10.getLogic().getTest()) {
                                                                                    laneSuggestView11 = this.this$0.laneSuggest;
                                                                                    if (laneSuggestView11 == null) {
                                                                                        Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                                                                                        laneSuggestView11 = null;
                                                                                    }
                                                                                }
                                                                            }
                                                                            dataClient4 = this.this$0.data;
                                                                            if (dataClient4 == null) {
                                                                                Intrinsics.throwUninitializedPropertyAccessException("data");
                                                                                dataClient4 = null;
                                                                            }
                                                                            if (!dataClient4.getConnected()) {
                                                                                i13 = 1065353216;
                                                                                i14 = i12;
                                                                                laneSuggestView7 = this.this$0.laneSuggest;
                                                                                if (laneSuggestView7 == null) {
                                                                                }
                                                                                if (laneSuggestView7.getVisibility() == 8) {
                                                                                }
                                                                                laneSuggestView8 = this.this$0.laneSuggest;
                                                                                if (laneSuggestView8 == null) {
                                                                                }
                                                                                laneSuggestView8.stop();
                                                                                replayBar = this.this$0.replayBar;
                                                                                if (replayBar == null) {
                                                                                }
                                                                                dataClient5 = this.this$0.data;
                                                                                if (dataClient5 == null) {
                                                                                }
                                                                                replayBar.setVisibility((dataClient5.getConnected() || !Replay.INSTANCE.getActive()) ? 8 : i14);
                                                                                textView18 = this.this$0.whereBtn;
                                                                                if (textView18 == null) {
                                                                                }
                                                                                dataClient6 = this.this$0.data;
                                                                                if (dataClient6 == null) {
                                                                                }
                                                                                if (dataClient6.getConnected()) {
                                                                                }
                                                                                i16 = 8;
                                                                                textView18.setVisibility(i16);
                                                                                textView19 = this.this$0.mapBtn;
                                                                                if (textView19 == null) {
                                                                                }
                                                                                dataClient7 = this.this$0.data;
                                                                                if (dataClient7 == null) {
                                                                                }
                                                                                if (dataClient7.getConnected()) {
                                                                                }
                                                                                i17 = 8;
                                                                                textView19.setVisibility(i17);
                                                                                i18 = this.this$0.tick;
                                                                                if (i18 % 40 == 0) {
                                                                                }
                                                                                BlinkerTurn blinkerTurn = BlinkerTurn.INSTANCE;
                                                                                uiSettings4 = this.this$0.settings;
                                                                                if (uiSettings4 != null) {
                                                                                }
                                                                                int i34 = i14;
                                                                                BlinkerTurn.tick$default(blinkerTurn, t, uiSettings5, 0L, 4, null);
                                                                                this.this$0.ideasTick(t);
                                                                                DriveTrack.tick$default(DriveTrack.INSTANCE, t, 0L, 2, null);
                                                                                place = this.this$0.sharedPending;
                                                                                if (place != null) {
                                                                                }
                                                                                i19 = this.this$0.tick;
                                                                                if (i19 % 20 == 0) {
                                                                                }
                                                                                i20 = this.this$0.tick;
                                                                                if (i20 % 4 == 0) {
                                                                                }
                                                                                textView20 = this.this$0.mapBtn;
                                                                                if (textView20 == null) {
                                                                                }
                                                                                ViewGroup.LayoutParams layoutParams = textView20.getLayoutParams();
                                                                                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                                                                                lp = (FrameLayout.LayoutParams) layoutParams;
                                                                                MainActivity mainActivity8 = this.this$0;
                                                                                textView21 = mainActivity8.whereBtn;
                                                                                if (textView21 == null) {
                                                                                }
                                                                                want = mainActivity8.dp(textView21.getVisibility() != 0 ? 330 : 160);
                                                                                if (lp.getMarginStart() != want) {
                                                                                }
                                                                                Unit unit13 = Unit.INSTANCE;
                                                                                MainActivity mainActivity9 = this.this$0;
                                                                                if (t.getParams().get("NavLanePositioningAllowed") == null) {
                                                                                }
                                                                                if (!t.getStarted()) {
                                                                                }
                                                                                r = new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0d, 0.0d, null, 30, null);
                                                                                nudgeHintView = mainActivity9.nudgeHint;
                                                                                if (nudgeHintView == null) {
                                                                                }
                                                                                nudgeHintView.setResult(r);
                                                                                nudgeHintView2 = mainActivity9.nudgeHint;
                                                                                if (nudgeHintView2 == null) {
                                                                                }
                                                                                nudgeHintView2.setMetric(t.isMetric());
                                                                                nudgeHintView3 = mainActivity9.nudgeHint;
                                                                                if (nudgeHintView3 == null) {
                                                                                }
                                                                                nudgeHintView3.setVisibility(r.getState() != NudgeLogic.State.NONE ? 8 : 0);
                                                                                Unit unit14 = Unit.INSTANCE;
                                                                                weatherView = this.this$0.weather;
                                                                                if (weatherView == null) {
                                                                                }
                                                                                weatherView.setVisibility((t.getStarted() || !t.s("weather") || t.getWeatherId() <= 0) ? 8 : 0);
                                                                                textView22 = this.this$0.compass;
                                                                                if (textView22 == null) {
                                                                                }
                                                                                if (!t.getStarted()) {
                                                                                }
                                                                                textView22.setText(str3);
                                                                                textView23 = this.this$0.devMetrics;
                                                                                if (textView23 == null) {
                                                                                }
                                                                                textView23.setText((t.getStarted() || !t.s("dev_metrics")) ? "" : "CPU " + MathKt.roundToInt(t.getCpuUsage()) + "%  |  TEMP " + MathKt.roundToInt(t.getCpuTempC()) + "°C  |  RAM " + t.getMemUsage() + "%  |  DISK " + MathKt.roundToInt(t.getFreeSpacePct()) + "% free");
                                                                                long nowNs = System.nanoTime();
                                                                                if (t.getStarted()) {
                                                                                }
                                                                                if (t.getStarted()) {
                                                                                }
                                                                                this.this$0.standstillSinceNs = j3;
                                                                                j4 = this.this$0.standstillSinceNs;
                                                                                if (j4 == j3) {
                                                                                }
                                                                                float stoppedS = f2;
                                                                                j5 = this.this$0.onroadSinceNs;
                                                                                if (j5 == j3) {
                                                                                }
                                                                                float onroadS = f3;
                                                                                if (t.s("stopped_timer")) {
                                                                                }
                                                                                textView24 = this.this$0.speedTv;
                                                                                if (textView24 == null) {
                                                                                }
                                                                                z16 = this.this$0.night;
                                                                                textView24.setTextColor(!z16 ? -1 : Color.rgb(29, 29, 31));
                                                                                boolean hideNormal = !t.s("alerts");
                                                                                if (t.getStarted()) {
                                                                                }
                                                                                showAlert = false;
                                                                                MainActivity mainActivity10 = this.this$0;
                                                                                f4 = this.this$0.alertAlpha;
                                                                                if (showAlert) {
                                                                                }
                                                                                f5 = this.this$0.alertAlpha;
                                                                                mainActivity10.alertAlpha = f4 + ((stall3 - f5) * 0.35f);
                                                                                f6 = this.this$0.alertAlpha;
                                                                                if (f6 > 0.02f) {
                                                                                }
                                                                                ModelSnapshot model = t.getModel();
                                                                                if (model != null) {
                                                                                }
                                                                                j6 = this.this$0.lastModelFrame;
                                                                                if (mf == j6) {
                                                                                }
                                                                                this.this$0.lastModelFrame = mf;
                                                                                overlayView3 = this.this$0.overlay;
                                                                                if (overlayView3 == null) {
                                                                                }
                                                                                overlayView3.invalidate();
                                                                                wheelButton2 = this.this$0.wheel;
                                                                                if (wheelButton2 == null) {
                                                                                }
                                                                                wheelButton2.invalidate();
                                                                                dmView = this.this$0.dm;
                                                                                if (dmView == null) {
                                                                                }
                                                                                dmView.invalidate();
                                                                                miciDistanceView2 = this.this$0.personality;
                                                                                if (miciDistanceView2 == null) {
                                                                                }
                                                                                miciDistanceView2.invalidate();
                                                                                slcView2 = this.this$0.slc;
                                                                                if (slcView2 == null) {
                                                                                }
                                                                                slcView2.invalidate();
                                                                                slcActionsView2 = this.this$0.slcActions;
                                                                                if (slcActionsView2 == null) {
                                                                                }
                                                                                slcActionsView2.invalidate();
                                                                                devSidebarView2 = this.this$0.devSidebar;
                                                                                if (devSidebarView2 == null) {
                                                                                }
                                                                                if (devSidebarView2.getVisibility() == 0) {
                                                                                }
                                                                                aetherGaugeView2 = this.this$0.gauge;
                                                                                if (aetherGaugeView2 == null) {
                                                                                }
                                                                                aetherGaugeView2.invalidate();
                                                                                navCardView3 = this.this$0.navCard;
                                                                                if (navCardView3 == null) {
                                                                                }
                                                                                navCardView3.invalidate();
                                                                                slowReasonView2 = this.this$0.slowCard;
                                                                                if (slowReasonView2 == null) {
                                                                                }
                                                                                slowReasonView2.invalidate();
                                                                                laneCardView3 = this.this$0.laneCard;
                                                                                if (laneCardView3 == null) {
                                                                                }
                                                                                laneCardView3.invalidate();
                                                                                blinkerCardView2 = this.this$0.blinkCard;
                                                                                if (blinkerCardView2 == null) {
                                                                                }
                                                                                blinkerCardView2.invalidate();
                                                                                gpsCardView2 = this.this$0.gpsCard;
                                                                                if (gpsCardView2 == null) {
                                                                                }
                                                                                if (gpsCardView2.getVisibility() == 0) {
                                                                                }
                                                                                nudgeHintView4 = this.this$0.nudgeHint;
                                                                                if (nudgeHintView4 == null) {
                                                                                }
                                                                                nudgeHintView4.invalidate();
                                                                                weatherView2 = this.this$0.weather;
                                                                                if (weatherView2 == null) {
                                                                                }
                                                                                weatherView2.invalidate();
                                                                                slcSourcesView2 = this.this$0.slcSources;
                                                                                if (slcSourcesView2 == null) {
                                                                                }
                                                                                slcSourcesView2.invalidate();
                                                                                linearLayout4 = this.this$0.sidebar;
                                                                                if (linearLayout4 == null) {
                                                                                }
                                                                                if (linearLayout4.getVisibility() == 0) {
                                                                                }
                                                                                i21 = this.this$0.tick;
                                                                                if (i21 % 10 == 0) {
                                                                                }
                                                                                this.this$0.applyHud();
                                                                                GpsBridge gpsBridge = GpsBridge.INSTANCE;
                                                                                telemetry = this.this$0.telemetry;
                                                                                String pStr$default = Telemetry.pStr$default(telemetry, "LastGPSPosition", null, 2, null);
                                                                                telemetry2 = this.this$0.telemetry;
                                                                                gpsBridge.fallback(pStr$default, telemetry2.getVEgo());
                                                                                handler = this.this$0.ui;
                                                                                handler.postDelayed(this, 50L);
                                                                                return;
                                                                            }
                                                                            if (laneOn || turnOn) {
                                                                                ModelSnapshot md = t.getModel();
                                                                                long now = System.currentTimeMillis();
                                                                                JSONObject nav = t.navState();
                                                                                float minLc = t.tFloat("minimum_lane_change_speed", 8.9f);
                                                                                laneSuggestView = this.this$0.laneSuggest;
                                                                                if (laneSuggestView == null) {
                                                                                    Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                                                                                    laneSuggestView = null;
                                                                                }
                                                                                laneSuggestView.setMetric(t.isMetric());
                                                                                laneSuggestView2 = this.this$0.laneSuggest;
                                                                                if (laneSuggestView2 == null) {
                                                                                    Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                                                                                    laneSuggestView2 = null;
                                                                                }
                                                                                boolean turnBusy = laneSuggestView2.getTurn().getState() == TurnSignal.State.SIGNALING;
                                                                                if (nav != null) {
                                                                                    connected = connected2;
                                                                                    Double valueOf = Double.valueOf(nav.optDouble("maneuverDistance", Double.NaN));
                                                                                    double it2 = valueOf.doubleValue();
                                                                                    if (!((Double.isInfinite(it2) || Double.isNaN(it2)) ? false : true)) {
                                                                                        valueOf = null;
                                                                                    }
                                                                                    if (valueOf != null) {
                                                                                        double md2 = valueOf.doubleValue();
                                                                                        Pair it3 = Nav.INSTANCE.nextLanes();
                                                                                        if (it3 != null) {
                                                                                            if (!(Math.abs(it3.getSecond().doubleValue() - md2) < 200.0d)) {
                                                                                                it3 = null;
                                                                                            }
                                                                                            emptyList = (it3 == null || (first = it3.getFirst()) == null) ? null : first.getLanes();
                                                                                        }
                                                                                    }
                                                                                } else {
                                                                                    connected = connected2;
                                                                                }
                                                                                emptyList = CollectionsKt.emptyList();
                                                                                List navLanes = emptyList;
                                                                                if (laneOn) {
                                                                                    LaneSuggest.Companion companion = LaneSuggest.Companion;
                                                                                    laneBrain2 = this.this$0.laneTracker;
                                                                                    Pair<Integer, String> routeNeed = companion.routeNeed(nav, laneBrain2, t.isMetric(), navLanes);
                                                                                    int rDir = routeNeed.component1().intValue();
                                                                                    String rWhy = routeNeed.component2();
                                                                                    LaneSuggest.Companion.Exit ex = LaneSuggest.Companion.exitNeed(nav, t.isMetric());
                                                                                    boolean z27 = t.getEnabled() && t.getActive();
                                                                                    float vEgo3 = t.getVEgo();
                                                                                    float[] desirePred = md != null ? md.getDesirePred() : null;
                                                                                    boolean laneChanging = md != null ? md.getLaneChanging() : false;
                                                                                    int laneChangeDir = md != null ? md.getLaneChangeDir() : 0;
                                                                                    boolean leftBlinker = t.getLeftBlinker();
                                                                                    boolean rightBlinker = t.getRightBlinker();
                                                                                    boolean brakePressed = t.getBrakePressed();
                                                                                    boolean steeringPressed = t.getSteeringPressed();
                                                                                    float steeringTorque = t.getSteeringTorque();
                                                                                    boolean s2 = t.s("lane_nudge_confirm");
                                                                                    boolean s3 = t.s("lane_auto");
                                                                                    laneSuggestView3 = this.this$0.laneSuggest;
                                                                                    if (laneSuggestView3 == null) {
                                                                                        Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                                                                                        laneSuggestView3 = null;
                                                                                    }
                                                                                    boolean areEqual = Intrinsics.areEqual((Object) laneSuggestView3.getCommaConfirms(), (Object) true);
                                                                                    boolean s4 = t.s("lane_pass");
                                                                                    float it4 = t.getVCruiseCluster() > 0.0f ? t.getVCruiseCluster() : t.getVCruise();
                                                                                    if (t.getCruiseEnabled()) {
                                                                                        i13 = 1065353216;
                                                                                        if (1.0f <= it4 && it4 <= 200.0f) {
                                                                                            f = it4 / 3.6f;
                                                                                            boolean status = t.getLeadOne().getStatus();
                                                                                            float dRel = t.getLeadOne().getDRel();
                                                                                            float vLead = t.getLeadOne().getVLead();
                                                                                            laneBrain3 = this.this$0.laneTracker;
                                                                                            Pair<Integer, Double> sideLanes = laneBrain3.sideLanes(-1);
                                                                                            int n2 = sideLanes.component1().intValue();
                                                                                            double c = sideLanes.component2().doubleValue();
                                                                                            int i35 = (n2 >= 0 || c < 0.5d) ? -1 : n2;
                                                                                            int routeSoon = LaneSuggest.Companion.routeSoon(nav);
                                                                                            boolean isMetric = t.isMetric();
                                                                                            boolean s5 = t.s("exit_signals");
                                                                                            int dir = ex == null ? ex.getDir() : 0;
                                                                                            double dist = ex == null ? ex.getDist() : Double.NaN;
                                                                                            String str16 = (ex != null || (why = ex.getWhy()) == null) ? "" : why;
                                                                                            String str17 = (ex != null || (key = ex.getKey()) == null) ? "" : key;
                                                                                            laneBrain4 = this.this$0.laneTracker;
                                                                                            Pair<Integer, Double> sideLanes2 = laneBrain4.sideLanes((ex == null ? ex.getDir() : 0) >= 0 ? 1 : -1);
                                                                                            int n3 = sideLanes2.component1().intValue();
                                                                                            double c2 = sideLanes2.component2().doubleValue();
                                                                                            int i36 = (n3 >= 0 || c2 < 0.5d) ? -1 : n3;
                                                                                            if (ex == null) {
                                                                                                LaneSuggest.Companion companion2 = LaneSuggest.Companion;
                                                                                                laneBrain10 = this.this$0.laneTracker;
                                                                                                Pair it5 = Nav.INSTANCE.nextLanes();
                                                                                                if (it5 != null) {
                                                                                                    double doubleValue = it5.getSecond().doubleValue();
                                                                                                    if (!(0.0d <= doubleValue && doubleValue <= 800.0d)) {
                                                                                                        it5 = null;
                                                                                                    }
                                                                                                    if (it5 != null) {
                                                                                                        Nav.Step first2 = it5.getFirst();
                                                                                                        if (first2 != null) {
                                                                                                            emptyList2 = first2.getLanes();
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                                emptyList2 = CollectionsKt.emptyList();
                                                                                                if (companion2.inLaneFor(ex, laneBrain10, emptyList2)) {
                                                                                                    z15 = true;
                                                                                                    if (t.s("lane_ends_ask")) {
                                                                                                        laneAhead3 = this.this$0.laneAhead;
                                                                                                        laneBrain9 = this.this$0.laneTracker;
                                                                                                        i15 = laneAhead3.need(laneBrain9).getFirst().intValue();
                                                                                                    } else {
                                                                                                        i15 = 0;
                                                                                                    }
                                                                                                    laneAhead2 = this.this$0.laneAhead;
                                                                                                    laneBrain5 = this.this$0.laneTracker;
                                                                                                    String second = laneAhead2.need(laneBrain5).getSecond();
                                                                                                    uiSettings3 = this.this$0.settings;
                                                                                                    if (uiSettings3 == null) {
                                                                                                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                                                                                                        uiSettings3 = null;
                                                                                                    }
                                                                                                    float f10 = (uiSettings3.getFloat("nudge_card_pct", 60.0f) * 100.0f) / 100.0f;
                                                                                                    boolean s6 = t.s("lane_return");
                                                                                                    laneBrain6 = this.this$0.laneTracker;
                                                                                                    Pair<Integer, Double> sideLanes3 = laneBrain6.sideLanes(1);
                                                                                                    int n4 = sideLanes3.component1().intValue();
                                                                                                    double c3 = sideLanes3.component2().doubleValue();
                                                                                                    int i37 = (n4 >= 0 || c3 < 0.5d) ? -1 : n4;
                                                                                                    boolean status2 = t.getLeadRight().getStatus();
                                                                                                    float dRel2 = t.getLeadRight().getDRel();
                                                                                                    float vLead2 = t.getLeadRight().getVLead();
                                                                                                    laneBrain7 = this.this$0.laneTracker;
                                                                                                    int it6 = laneBrain7.laneCount().getFirst().intValue();
                                                                                                    int i38 = it6 > 0 ? it6 : -1;
                                                                                                    laneBrain8 = this.this$0.laneTracker;
                                                                                                    LaneBrain.Road road = laneBrain8.getRoad();
                                                                                                    in = new LaneSuggest.In(now, z27, vEgo3, minLc, desirePred, laneChanging, laneChangeDir, leftBlinker, rightBlinker, z12, z13, brakePressed, rDir, rWhy, steeringPressed, steeringTorque, s2, s3, areEqual, turnBusy, s4, f, status, dRel, vLead, i35, routeSoon, isMetric, s5, dir, dist, str16, str17, i36, z15, i15, second, f10, s6, i37, status2, dRel2, vLead2, i38, road == null && road.getHighway());
                                                                                                }
                                                                                            }
                                                                                            z15 = false;
                                                                                            if (t.s("lane_ends_ask")) {
                                                                                            }
                                                                                            laneAhead2 = this.this$0.laneAhead;
                                                                                            laneBrain5 = this.this$0.laneTracker;
                                                                                            String second2 = laneAhead2.need(laneBrain5).getSecond();
                                                                                            uiSettings3 = this.this$0.settings;
                                                                                            if (uiSettings3 == null) {
                                                                                            }
                                                                                            float f102 = (uiSettings3.getFloat("nudge_card_pct", 60.0f) * 100.0f) / 100.0f;
                                                                                            boolean s62 = t.s("lane_return");
                                                                                            laneBrain6 = this.this$0.laneTracker;
                                                                                            Pair<Integer, Double> sideLanes32 = laneBrain6.sideLanes(1);
                                                                                            int n42 = sideLanes32.component1().intValue();
                                                                                            double c32 = sideLanes32.component2().doubleValue();
                                                                                            if (n42 >= 0) {
                                                                                            }
                                                                                            boolean status22 = t.getLeadRight().getStatus();
                                                                                            float dRel22 = t.getLeadRight().getDRel();
                                                                                            float vLead22 = t.getLeadRight().getVLead();
                                                                                            laneBrain7 = this.this$0.laneTracker;
                                                                                            int it62 = laneBrain7.laneCount().getFirst().intValue();
                                                                                            if (it62 > 0) {
                                                                                            }
                                                                                            laneBrain8 = this.this$0.laneTracker;
                                                                                            LaneBrain.Road road2 = laneBrain8.getRoad();
                                                                                            in = new LaneSuggest.In(now, z27, vEgo3, minLc, desirePred, laneChanging, laneChangeDir, leftBlinker, rightBlinker, z12, z13, brakePressed, rDir, rWhy, steeringPressed, steeringTorque, s2, s3, areEqual, turnBusy, s4, f, status, dRel, vLead, i35, routeSoon, isMetric, s5, dir, dist, str16, str17, i36, z15, i15, second2, f102, s62, i37, status22, dRel22, vLead22, i38, road2 == null && road2.getHighway());
                                                                                        }
                                                                                    } else {
                                                                                        i13 = 1065353216;
                                                                                    }
                                                                                    f = 0.0f;
                                                                                    boolean status3 = t.getLeadOne().getStatus();
                                                                                    float dRel3 = t.getLeadOne().getDRel();
                                                                                    float vLead3 = t.getLeadOne().getVLead();
                                                                                    laneBrain3 = this.this$0.laneTracker;
                                                                                    Pair<Integer, Double> sideLanes4 = laneBrain3.sideLanes(-1);
                                                                                    int n22 = sideLanes4.component1().intValue();
                                                                                    double c4 = sideLanes4.component2().doubleValue();
                                                                                    if (n22 >= 0) {
                                                                                    }
                                                                                    int routeSoon2 = LaneSuggest.Companion.routeSoon(nav);
                                                                                    boolean isMetric2 = t.isMetric();
                                                                                    boolean s52 = t.s("exit_signals");
                                                                                    if (ex == null) {
                                                                                    }
                                                                                    double dist2 = ex == null ? ex.getDist() : Double.NaN;
                                                                                    if (ex != null) {
                                                                                    }
                                                                                    if (ex != null) {
                                                                                    }
                                                                                    laneBrain4 = this.this$0.laneTracker;
                                                                                    Pair<Integer, Double> sideLanes22 = laneBrain4.sideLanes((ex == null ? ex.getDir() : 0) >= 0 ? 1 : -1);
                                                                                    int n32 = sideLanes22.component1().intValue();
                                                                                    double c22 = sideLanes22.component2().doubleValue();
                                                                                    if (n32 >= 0) {
                                                                                    }
                                                                                    if (ex == null) {
                                                                                    }
                                                                                    z15 = false;
                                                                                    if (t.s("lane_ends_ask")) {
                                                                                    }
                                                                                    laneAhead2 = this.this$0.laneAhead;
                                                                                    laneBrain5 = this.this$0.laneTracker;
                                                                                    String second22 = laneAhead2.need(laneBrain5).getSecond();
                                                                                    uiSettings3 = this.this$0.settings;
                                                                                    if (uiSettings3 == null) {
                                                                                    }
                                                                                    float f1022 = (uiSettings3.getFloat("nudge_card_pct", 60.0f) * 100.0f) / 100.0f;
                                                                                    boolean s622 = t.s("lane_return");
                                                                                    laneBrain6 = this.this$0.laneTracker;
                                                                                    Pair<Integer, Double> sideLanes322 = laneBrain6.sideLanes(1);
                                                                                    int n422 = sideLanes322.component1().intValue();
                                                                                    double c322 = sideLanes322.component2().doubleValue();
                                                                                    if (n422 >= 0) {
                                                                                    }
                                                                                    boolean status222 = t.getLeadRight().getStatus();
                                                                                    float dRel222 = t.getLeadRight().getDRel();
                                                                                    float vLead222 = t.getLeadRight().getVLead();
                                                                                    laneBrain7 = this.this$0.laneTracker;
                                                                                    int it622 = laneBrain7.laneCount().getFirst().intValue();
                                                                                    if (it622 > 0) {
                                                                                    }
                                                                                    laneBrain8 = this.this$0.laneTracker;
                                                                                    LaneBrain.Road road22 = laneBrain8.getRoad();
                                                                                    in = new LaneSuggest.In(now, z27, vEgo3, minLc, desirePred, laneChanging, laneChangeDir, leftBlinker, rightBlinker, z12, z13, brakePressed, rDir, rWhy, steeringPressed, steeringTorque, s2, s3, areEqual, turnBusy, s4, f, status3, dRel3, vLead3, i35, routeSoon2, isMetric2, s52, dir, dist2, str16, str17, i36, z15, i15, second22, f1022, s622, i37, status222, dRel222, vLead222, i38, road22 == null && road22.getHighway());
                                                                                } else {
                                                                                    in = null;
                                                                                    i13 = 1065353216;
                                                                                }
                                                                                LaneSuggest.In lane = in;
                                                                                float poseYawZ = t.getPoseYawZ();
                                                                                float yawLeft = (!(!Float.isInfinite(poseYawZ) && !Float.isNaN(poseYawZ)) || System.nanoTime() - t.getPoseAtNs() >= 1000000000) ? t.getYawRate() : Odometry.INSTANCE.getGyroSign() * t.getPoseYawZ();
                                                                                if (!turnOn) {
                                                                                    i14 = 0;
                                                                                } else {
                                                                                    if (t.getStarted()) {
                                                                                        float vEgo4 = t.getVEgo();
                                                                                        boolean leftBlinker2 = t.getLeftBlinker();
                                                                                        boolean rightBlinker2 = t.getRightBlinker();
                                                                                        i14 = 0;
                                                                                        boolean tBool = t.tBool("use_turn_desires", false);
                                                                                        laneSuggestView5 = this.this$0.laneSuggest;
                                                                                        if (laneSuggestView5 == null) {
                                                                                            Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                                                                                            laneSuggestView5 = null;
                                                                                        }
                                                                                        double crossGapFor = laneSuggestView5.crossGapFor(nav, now);
                                                                                        laneSuggestView6 = this.this$0.laneSuggest;
                                                                                        if (laneSuggestView6 == null) {
                                                                                            Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                                                                                            laneSuggestView6 = null;
                                                                                        }
                                                                                        boolean z28 = laneSuggestView6.getLogic().getState() != LaneSuggest.State.IDLE;
                                                                                        boolean areEqual2 = Intrinsics.areEqual(t.getGear(), "park");
                                                                                        float steeringAngleDeg = t.getSteeringAngleDeg();
                                                                                        LaneSuggest.Companion companion3 = LaneSuggest.Companion;
                                                                                        laneBrain11 = this.this$0.laneTracker;
                                                                                        turnIn = new TurnSignal.In(now, nav, vEgo4, yawLeft, leftBlinker2, rightBlinker2, minLc, tBool, crossGapFor, z28, areEqual2, steeringAngleDeg, companion3.notTurnLane(nav, laneBrain11, Nav.INSTANCE.nextLanes()));
                                                                                        laneSuggestView4 = this.this$0.laneSuggest;
                                                                                        if (laneSuggestView4 == null) {
                                                                                            Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                                                                                            laneSuggestView4 = null;
                                                                                        }
                                                                                        laneSuggestView4.tick(lane, turnIn);
                                                                                        replayBar = this.this$0.replayBar;
                                                                                        if (replayBar == null) {
                                                                                            Intrinsics.throwUninitializedPropertyAccessException("replayBar");
                                                                                            replayBar = null;
                                                                                        }
                                                                                        dataClient5 = this.this$0.data;
                                                                                        if (dataClient5 == null) {
                                                                                            Intrinsics.throwUninitializedPropertyAccessException("data");
                                                                                            dataClient5 = null;
                                                                                        }
                                                                                        replayBar.setVisibility((dataClient5.getConnected() || !Replay.INSTANCE.getActive()) ? 8 : i14);
                                                                                        textView18 = this.this$0.whereBtn;
                                                                                        if (textView18 == null) {
                                                                                            Intrinsics.throwUninitializedPropertyAccessException("whereBtn");
                                                                                            textView18 = null;
                                                                                        }
                                                                                        dataClient6 = this.this$0.data;
                                                                                        if (dataClient6 == null) {
                                                                                            Intrinsics.throwUninitializedPropertyAccessException("data");
                                                                                            dataClient6 = null;
                                                                                        }
                                                                                        if (dataClient6.getConnected() && t.navState() == null) {
                                                                                            mapOpen2 = this.this$0.getMapOpen();
                                                                                            if (!mapOpen2) {
                                                                                                hudEditor3 = this.this$0.hudEditor;
                                                                                                if (hudEditor3 == null) {
                                                                                                    maskEditor3 = this.this$0.maskEditor;
                                                                                                    if (maskEditor3 == null) {
                                                                                                        i16 = i14;
                                                                                                        textView18.setVisibility(i16);
                                                                                                        textView19 = this.this$0.mapBtn;
                                                                                                        if (textView19 == null) {
                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("mapBtn");
                                                                                                            textView19 = null;
                                                                                                        }
                                                                                                        dataClient7 = this.this$0.data;
                                                                                                        if (dataClient7 == null) {
                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("data");
                                                                                                            dataClient7 = null;
                                                                                                        }
                                                                                                        if (dataClient7.getConnected()) {
                                                                                                            mapOpen = this.this$0.getMapOpen();
                                                                                                            if (!mapOpen) {
                                                                                                                hudEditor2 = this.this$0.hudEditor;
                                                                                                                if (hudEditor2 == null) {
                                                                                                                    maskEditor2 = this.this$0.maskEditor;
                                                                                                                    if (maskEditor2 == null) {
                                                                                                                        if (!t.getStarted()) {
                                                                                                                            z20 = this.this$0.homeShown;
                                                                                                                        }
                                                                                                                        i17 = i14;
                                                                                                                        textView19.setVisibility(i17);
                                                                                                                        i18 = this.this$0.tick;
                                                                                                                        if (i18 % 40 == 0) {
                                                                                                                            NudgeSync nudgeSync = NudgeSync.INSTANCE;
                                                                                                                            uiSettings7 = this.this$0.settings;
                                                                                                                            if (uiSettings7 == null) {
                                                                                                                                Intrinsics.throwUninitializedPropertyAccessException("settings");
                                                                                                                                uiSettings7 = null;
                                                                                                                            }
                                                                                                                            nudgeSync.tick(uiSettings7.getFloat("nudge_lc_pct", 100.0f));
                                                                                                                        }
                                                                                                                        BlinkerTurn blinkerTurn2 = BlinkerTurn.INSTANCE;
                                                                                                                        uiSettings4 = this.this$0.settings;
                                                                                                                        if (uiSettings4 != null) {
                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("settings");
                                                                                                                            uiSettings5 = null;
                                                                                                                        } else {
                                                                                                                            uiSettings5 = uiSettings4;
                                                                                                                        }
                                                                                                                        int i342 = i14;
                                                                                                                        BlinkerTurn.tick$default(blinkerTurn2, t, uiSettings5, 0L, 4, null);
                                                                                                                        this.this$0.ideasTick(t);
                                                                                                                        DriveTrack.tick$default(DriveTrack.INSTANCE, t, 0L, 2, null);
                                                                                                                        place = this.this$0.sharedPending;
                                                                                                                        if (place != null) {
                                                                                                                            dataClient10 = this.this$0.data;
                                                                                                                            if (dataClient10 == null) {
                                                                                                                                Intrinsics.throwUninitializedPropertyAccessException("data");
                                                                                                                                dataClient10 = null;
                                                                                                                            }
                                                                                                                            if (dataClient10.getConnected()) {
                                                                                                                                i28 = this.this$0.tick;
                                                                                                                                if (i28 % 20 == 0) {
                                                                                                                                    this.this$0.useShared();
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                        i19 = this.this$0.tick;
                                                                                                                        if (i19 % 20 == 0) {
                                                                                                                            Ntrip.INSTANCE.tick(this.this$0);
                                                                                                                        }
                                                                                                                        i20 = this.this$0.tick;
                                                                                                                        if (i20 % 4 == 0) {
                                                                                                                            MainActivity mainActivity11 = this.this$0;
                                                                                                                            dataClient9 = this.this$0.data;
                                                                                                                            if (dataClient9 == null) {
                                                                                                                                Intrinsics.throwUninitializedPropertyAccessException("data");
                                                                                                                                dataClient9 = null;
                                                                                                                            }
                                                                                                                            if (dataClient9.getConnected() && !Nav.INSTANCE.getActive() && t.navState() == null) {
                                                                                                                                hudEditor = this.this$0.hudEditor;
                                                                                                                                if (hudEditor == null) {
                                                                                                                                    maskEditor = this.this$0.maskEditor;
                                                                                                                                    if (maskEditor == null) {
                                                                                                                                        if (!t.getStarted()) {
                                                                                                                                            z19 = this.this$0.homeShown;
                                                                                                                                        }
                                                                                                                                        z18 = 1;
                                                                                                                                        mainActivity11.syncFavBar(z18);
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                            z18 = i342;
                                                                                                                            mainActivity11.syncFavBar(z18);
                                                                                                                        }
                                                                                                                        textView20 = this.this$0.mapBtn;
                                                                                                                        if (textView20 == null) {
                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("mapBtn");
                                                                                                                            textView20 = null;
                                                                                                                        }
                                                                                                                        ViewGroup.LayoutParams layoutParams2 = textView20.getLayoutParams();
                                                                                                                        Intrinsics.checkNotNull(layoutParams2, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                                                                                                                        lp = (FrameLayout.LayoutParams) layoutParams2;
                                                                                                                        MainActivity mainActivity82 = this.this$0;
                                                                                                                        textView21 = mainActivity82.whereBtn;
                                                                                                                        if (textView21 == null) {
                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("whereBtn");
                                                                                                                            textView21 = null;
                                                                                                                        }
                                                                                                                        want = mainActivity82.dp(textView21.getVisibility() != 0 ? 330 : 160);
                                                                                                                        if (lp.getMarginStart() != want) {
                                                                                                                            lp.setMarginStart(want);
                                                                                                                            textView35 = mainActivity82.mapBtn;
                                                                                                                            if (textView35 == null) {
                                                                                                                                Intrinsics.throwUninitializedPropertyAccessException("mapBtn");
                                                                                                                                textView35 = null;
                                                                                                                            }
                                                                                                                            textView35.setLayoutParams(lp);
                                                                                                                        }
                                                                                                                        Unit unit132 = Unit.INSTANCE;
                                                                                                                        MainActivity mainActivity92 = this.this$0;
                                                                                                                        boolean allowed = t.getParams().get("NavLanePositioningAllowed") == null ? t.pBool("NavLanePositioningAllowed", true) : true;
                                                                                                                        if (!t.getStarted() && t.getActive() && allowed && t.s("nudge_hint")) {
                                                                                                                            Double doubleOrNull = StringsKt.toDoubleOrNull(Telemetry.pStr$default(t, "LaneDetectionWidth", null, 2, null));
                                                                                                                            double ldw = doubleOrNull != null ? doubleOrNull.doubleValue() : 0.0d;
                                                                                                                            r = NudgeLogic.INSTANCE.evaluate(t.navState(), t.getVEgo(), t.getLeftBlinker(), t.getRightBlinker(), t.getLeftBlindspot(), t.getRightBlindspot(), t.getLaneWidthLeft(), t.getLaneWidthRight(), t.isMetric() ? ldw : 0.3048d * ldw);
                                                                                                                        } else {
                                                                                                                            r = new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0d, 0.0d, null, 30, null);
                                                                                                                        }
                                                                                                                        nudgeHintView = mainActivity92.nudgeHint;
                                                                                                                        if (nudgeHintView == null) {
                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("nudgeHint");
                                                                                                                            nudgeHintView = null;
                                                                                                                        }
                                                                                                                        nudgeHintView.setResult(r);
                                                                                                                        nudgeHintView2 = mainActivity92.nudgeHint;
                                                                                                                        if (nudgeHintView2 == null) {
                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("nudgeHint");
                                                                                                                            nudgeHintView2 = null;
                                                                                                                        }
                                                                                                                        nudgeHintView2.setMetric(t.isMetric());
                                                                                                                        nudgeHintView3 = mainActivity92.nudgeHint;
                                                                                                                        if (nudgeHintView3 == null) {
                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("nudgeHint");
                                                                                                                            nudgeHintView3 = null;
                                                                                                                        }
                                                                                                                        nudgeHintView3.setVisibility(r.getState() != NudgeLogic.State.NONE ? 8 : 0);
                                                                                                                        Unit unit142 = Unit.INSTANCE;
                                                                                                                        weatherView = this.this$0.weather;
                                                                                                                        if (weatherView == null) {
                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("weather");
                                                                                                                            weatherView = null;
                                                                                                                        }
                                                                                                                        weatherView.setVisibility((t.getStarted() || !t.s("weather") || t.getWeatherId() <= 0) ? 8 : 0);
                                                                                                                        textView22 = this.this$0.compass;
                                                                                                                        if (textView22 == null) {
                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("compass");
                                                                                                                            textView22 = null;
                                                                                                                        }
                                                                                                                        if (!t.getStarted() && t.s("compass") && !Float.isNaN(t.getBearingDeg())) {
                                                                                                                            String[] dirs = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
                                                                                                                            str3 = "- " + dirs[((int) (((((t.getBearingDeg() + 22.5f) % 360.0f) + 360.0f) % 360.0f) / 45.0f)) % 8] + " -";
                                                                                                                        }
                                                                                                                        textView22.setText(str3);
                                                                                                                        textView23 = this.this$0.devMetrics;
                                                                                                                        if (textView23 == null) {
                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("devMetrics");
                                                                                                                            textView23 = null;
                                                                                                                        }
                                                                                                                        textView23.setText((t.getStarted() || !t.s("dev_metrics")) ? "" : "CPU " + MathKt.roundToInt(t.getCpuUsage()) + "%  |  TEMP " + MathKt.roundToInt(t.getCpuTempC()) + "°C  |  RAM " + t.getMemUsage() + "%  |  DISK " + MathKt.roundToInt(t.getFreeSpacePct()) + "% free");
                                                                                                                        long nowNs2 = System.nanoTime();
                                                                                                                        if (t.getStarted()) {
                                                                                                                            j3 = 0;
                                                                                                                            this.this$0.onroadSinceNs = 0L;
                                                                                                                        } else {
                                                                                                                            j10 = this.this$0.onroadSinceNs;
                                                                                                                            j3 = 0;
                                                                                                                            if (j10 == 0) {
                                                                                                                                this.this$0.onroadSinceNs = nowNs2;
                                                                                                                            }
                                                                                                                        }
                                                                                                                        if (t.getStarted() || !t.getStandstill()) {
                                                                                                                            this.this$0.standstillSinceNs = j3;
                                                                                                                        } else {
                                                                                                                            j9 = this.this$0.standstillSinceNs;
                                                                                                                            if (j9 == j3) {
                                                                                                                                this.this$0.standstillSinceNs = nowNs2;
                                                                                                                            }
                                                                                                                        }
                                                                                                                        j4 = this.this$0.standstillSinceNs;
                                                                                                                        if (j4 == j3) {
                                                                                                                            j8 = this.this$0.standstillSinceNs;
                                                                                                                            f2 = (nowNs2 - j8) / 1.0E9f;
                                                                                                                        } else {
                                                                                                                            f2 = 0.0f;
                                                                                                                        }
                                                                                                                        float stoppedS2 = f2;
                                                                                                                        j5 = this.this$0.onroadSinceNs;
                                                                                                                        if (j5 == j3) {
                                                                                                                            j7 = this.this$0.onroadSinceNs;
                                                                                                                            f3 = (nowNs2 - j7) / 1.0E9f;
                                                                                                                        } else {
                                                                                                                            f3 = 0.0f;
                                                                                                                        }
                                                                                                                        float onroadS2 = f3;
                                                                                                                        if (t.s("stopped_timer") || stoppedS2 < 60.0f || onroadS2 < 60.0f) {
                                                                                                                            textView24 = this.this$0.speedTv;
                                                                                                                            if (textView24 == null) {
                                                                                                                                Intrinsics.throwUninitializedPropertyAccessException("speedTv");
                                                                                                                                textView24 = null;
                                                                                                                            }
                                                                                                                            z16 = this.this$0.night;
                                                                                                                            textView24.setTextColor(!z16 ? -1 : Color.rgb(29, 29, 31));
                                                                                                                        } else {
                                                                                                                            float f11 = 60;
                                                                                                                            int m2 = (int) (stoppedS2 / f11);
                                                                                                                            int sec = (int) (stoppedS2 % f11);
                                                                                                                            textView32 = this.this$0.speedTv;
                                                                                                                            if (textView32 == null) {
                                                                                                                                Intrinsics.throwUninitializedPropertyAccessException("speedTv");
                                                                                                                                textView32 = null;
                                                                                                                            }
                                                                                                                            String format2 = String.format("%d:%02d", Arrays.copyOf(new Object[]{Integer.valueOf(m2), Integer.valueOf(sec)}, 2));
                                                                                                                            Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
                                                                                                                            textView32.setText(format2);
                                                                                                                            textView33 = this.this$0.speedUnit;
                                                                                                                            if (textView33 == null) {
                                                                                                                                Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
                                                                                                                                textView33 = null;
                                                                                                                            }
                                                                                                                            textView33.setText("stopped");
                                                                                                                            int col = stoppedS2 < 150.0f ? ColorUtils.blendARGB(Color.rgb(22, 127, 64), Color.rgb(218, 111, 37), (stoppedS2 - 60.0f) / 90.0f) : stoppedS2 < 300.0f ? ColorUtils.blendARGB(Color.rgb(218, 111, 37), Color.rgb(201, 34, 49), (stoppedS2 - 150.0f) / 150.0f) : Color.rgb(201, 34, 49);
                                                                                                                            textView34 = this.this$0.speedTv;
                                                                                                                            if (textView34 == null) {
                                                                                                                                Intrinsics.throwUninitializedPropertyAccessException("speedTv");
                                                                                                                                textView34 = null;
                                                                                                                            }
                                                                                                                            textView34.setTextColor(col);
                                                                                                                        }
                                                                                                                        boolean hideNormal2 = !t.s("alerts");
                                                                                                                        if (t.getStarted()) {
                                                                                                                            if ((t.getAlertText1().length() > 0) && !Intrinsics.areEqual(t.getAlertSize(), "none") && !hideNormal2) {
                                                                                                                                showAlert = true;
                                                                                                                                MainActivity mainActivity102 = this.this$0;
                                                                                                                                f4 = this.this$0.alertAlpha;
                                                                                                                                if (showAlert) {
                                                                                                                                    stall3 = i13;
                                                                                                                                }
                                                                                                                                f5 = this.this$0.alertAlpha;
                                                                                                                                mainActivity102.alertAlpha = f4 + ((stall3 - f5) * 0.35f);
                                                                                                                                f6 = this.this$0.alertAlpha;
                                                                                                                                if (f6 > 0.02f) {
                                                                                                                                    linearLayout5 = this.this$0.alertBox;
                                                                                                                                    if (linearLayout5 == null) {
                                                                                                                                        Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                                                                                                                                        linearLayout5 = null;
                                                                                                                                    }
                                                                                                                                    linearLayout5.setVisibility(0);
                                                                                                                                    linearLayout6 = this.this$0.alertBox;
                                                                                                                                    if (linearLayout6 == null) {
                                                                                                                                        Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                                                                                                                                        linearLayout6 = null;
                                                                                                                                    }
                                                                                                                                    f7 = this.this$0.alertAlpha;
                                                                                                                                    linearLayout6.setAlpha(f7);
                                                                                                                                    if (showAlert) {
                                                                                                                                        textView29 = this.this$0.alert1;
                                                                                                                                        if (textView29 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("alert1");
                                                                                                                                            textView29 = null;
                                                                                                                                        }
                                                                                                                                        textView29.setText(t.getAlertText1());
                                                                                                                                        textView30 = this.this$0.alert2;
                                                                                                                                        if (textView30 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("alert2");
                                                                                                                                            textView30 = null;
                                                                                                                                        }
                                                                                                                                        textView30.setText(t.getAlertText2());
                                                                                                                                        textView31 = this.this$0.alert2;
                                                                                                                                        if (textView31 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("alert2");
                                                                                                                                            textView31 = null;
                                                                                                                                        }
                                                                                                                                        textView31.setVisibility(t.getAlertText2().length() == 0 ? 8 : 0);
                                                                                                                                    }
                                                                                                                                    boolean full = Intrinsics.areEqual(t.getAlertSize(), "full");
                                                                                                                                    textView27 = this.this$0.alert1;
                                                                                                                                    if (textView27 == null) {
                                                                                                                                        Intrinsics.throwUninitializedPropertyAccessException("alert1");
                                                                                                                                        textView27 = null;
                                                                                                                                    }
                                                                                                                                    String alertSize = t.getAlertSize();
                                                                                                                                    textView27.setTextSize(Intrinsics.areEqual(alertSize, "full") ? 52.0f : Intrinsics.areEqual(alertSize, "mid") ? 36.0f : 26.0f);
                                                                                                                                    textView28 = this.this$0.alert2;
                                                                                                                                    if (textView28 == null) {
                                                                                                                                        Intrinsics.throwUninitializedPropertyAccessException("alert2");
                                                                                                                                        textView28 = null;
                                                                                                                                    }
                                                                                                                                    textView28.setTextSize(full ? 24.0f : 20.0f);
                                                                                                                                    linearLayout7 = this.this$0.alertBox;
                                                                                                                                    if (linearLayout7 == null) {
                                                                                                                                        Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                                                                                                                                        linearLayout7 = null;
                                                                                                                                    }
                                                                                                                                    ViewGroup.LayoutParams layoutParams3 = linearLayout7.getLayoutParams();
                                                                                                                                    Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                                                                                                                                    FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) layoutParams3;
                                                                                                                                    lp2.height = full ? -1 : -2;
                                                                                                                                    linearLayout8 = this.this$0.alertBox;
                                                                                                                                    if (linearLayout8 == null) {
                                                                                                                                        Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                                                                                                                                        linearLayout8 = null;
                                                                                                                                    }
                                                                                                                                    linearLayout8.setLayoutParams(lp2);
                                                                                                                                    String alertStatus = t.getAlertStatus();
                                                                                                                                    switch (alertStatus.hashCode()) {
                                                                                                                                        case -2132360282:
                                                                                                                                            if (alertStatus.equals("starpilot")) {
                                                                                                                                                rgb2 = Color.rgb(23, 134, 68);
                                                                                                                                                break;
                                                                                                                                            }
                                                                                                                                            rgb2 = -16777216;
                                                                                                                                            break;
                                                                                                                                        case -1874525969:
                                                                                                                                            if (alertStatus.equals("userprompt")) {
                                                                                                                                                rgb2 = Color.rgb(255, 115, 0);
                                                                                                                                                break;
                                                                                                                                            }
                                                                                                                                            rgb2 = -16777216;
                                                                                                                                            break;
                                                                                                                                        case 1952151455:
                                                                                                                                            if (!alertStatus.equals("critical")) {
                                                                                                                                                rgb2 = -16777216;
                                                                                                                                                break;
                                                                                                                                            } else {
                                                                                                                                                rgb2 = Color.rgb(255, 0, 21);
                                                                                                                                                break;
                                                                                                                                            }
                                                                                                                                        default:
                                                                                                                                            rgb2 = -16777216;
                                                                                                                                            break;
                                                                                                                                    }
                                                                                                                                    int base = rgb2;
                                                                                                                                    boolean tint = t.s("alert_tint") && !Intrinsics.areEqual(t.getAlertStatus(), "normal");
                                                                                                                                    if (tint) {
                                                                                                                                        view3 = this.this$0.alertTint;
                                                                                                                                        if (view3 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("alertTint");
                                                                                                                                            view3 = null;
                                                                                                                                        }
                                                                                                                                        view3.setVisibility(0);
                                                                                                                                        view4 = this.this$0.alertTint;
                                                                                                                                        if (view4 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("alertTint");
                                                                                                                                            view4 = null;
                                                                                                                                        }
                                                                                                                                        f8 = this.this$0.alertAlpha;
                                                                                                                                        view4.setAlpha(f8);
                                                                                                                                        gradientDrawable = this.this$0.tintDrawable;
                                                                                                                                        gradientDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                                                                                                                                        gradientDrawable2 = this.this$0.tintDrawable;
                                                                                                                                        gradientDrawable2.setColors(full ? new int[]{OverlayView.Companion.withAlpha(base, 230), OverlayView.Companion.withAlpha(base, 230)} : new int[]{OverlayView.Companion.withAlpha(base, 0), OverlayView.Companion.withAlpha(base, 60), OverlayView.Companion.withAlpha(base, 230)});
                                                                                                                                        linearLayout10 = this.this$0.alertBox;
                                                                                                                                        if (linearLayout10 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                                                                                                                                            linearLayout10 = null;
                                                                                                                                        }
                                                                                                                                        linearLayout10.setBackgroundColor(full ? 0 : OverlayView.Companion.withAlpha(base, 120));
                                                                                                                                    } else {
                                                                                                                                        view2 = this.this$0.alertTint;
                                                                                                                                        if (view2 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("alertTint");
                                                                                                                                            view2 = null;
                                                                                                                                        }
                                                                                                                                        view2.setVisibility(8);
                                                                                                                                        linearLayout9 = this.this$0.alertBox;
                                                                                                                                        if (linearLayout9 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                                                                                                                                            linearLayout9 = null;
                                                                                                                                        }
                                                                                                                                        linearLayout9.setBackgroundColor(OverlayView.Companion.withAlpha(base, full ? 230 : 200));
                                                                                                                                    }
                                                                                                                                } else {
                                                                                                                                    linearLayout3 = this.this$0.alertBox;
                                                                                                                                    if (linearLayout3 == null) {
                                                                                                                                        Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                                                                                                                                        linearLayout3 = null;
                                                                                                                                    }
                                                                                                                                    linearLayout3.setVisibility(8);
                                                                                                                                    view = this.this$0.alertTint;
                                                                                                                                    if (view == null) {
                                                                                                                                        Intrinsics.throwUninitializedPropertyAccessException("alertTint");
                                                                                                                                        view = null;
                                                                                                                                    }
                                                                                                                                    view.setVisibility(8);
                                                                                                                                }
                                                                                                                                ModelSnapshot model2 = t.getModel();
                                                                                                                                mf = model2 != null ? model2.getFrameId() : -1L;
                                                                                                                                j6 = this.this$0.lastModelFrame;
                                                                                                                                if (mf == j6) {
                                                                                                                                    i27 = this.this$0.tick;
                                                                                                                                }
                                                                                                                                this.this$0.lastModelFrame = mf;
                                                                                                                                overlayView3 = this.this$0.overlay;
                                                                                                                                if (overlayView3 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("overlay");
                                                                                                                                    overlayView3 = null;
                                                                                                                                }
                                                                                                                                overlayView3.invalidate();
                                                                                                                                wheelButton2 = this.this$0.wheel;
                                                                                                                                if (wheelButton2 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("wheel");
                                                                                                                                    wheelButton2 = null;
                                                                                                                                }
                                                                                                                                wheelButton2.invalidate();
                                                                                                                                dmView = this.this$0.dm;
                                                                                                                                if (dmView == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("dm");
                                                                                                                                    dmView = null;
                                                                                                                                }
                                                                                                                                dmView.invalidate();
                                                                                                                                miciDistanceView2 = this.this$0.personality;
                                                                                                                                if (miciDistanceView2 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("personality");
                                                                                                                                    miciDistanceView2 = null;
                                                                                                                                }
                                                                                                                                miciDistanceView2.invalidate();
                                                                                                                                slcView2 = this.this$0.slc;
                                                                                                                                if (slcView2 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("slc");
                                                                                                                                    slcView2 = null;
                                                                                                                                }
                                                                                                                                slcView2.invalidate();
                                                                                                                                slcActionsView2 = this.this$0.slcActions;
                                                                                                                                if (slcActionsView2 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("slcActions");
                                                                                                                                    slcActionsView2 = null;
                                                                                                                                }
                                                                                                                                slcActionsView2.invalidate();
                                                                                                                                devSidebarView2 = this.this$0.devSidebar;
                                                                                                                                if (devSidebarView2 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("devSidebar");
                                                                                                                                    devSidebarView2 = null;
                                                                                                                                }
                                                                                                                                if (devSidebarView2.getVisibility() == 0) {
                                                                                                                                    i26 = this.this$0.tick;
                                                                                                                                    if (i26 % 4 == 0) {
                                                                                                                                        devSidebarView3 = this.this$0.devSidebar;
                                                                                                                                        if (devSidebarView3 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("devSidebar");
                                                                                                                                            devSidebarView3 = null;
                                                                                                                                        }
                                                                                                                                        devSidebarView3.invalidate();
                                                                                                                                    }
                                                                                                                                }
                                                                                                                                aetherGaugeView2 = this.this$0.gauge;
                                                                                                                                if (aetherGaugeView2 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("gauge");
                                                                                                                                    aetherGaugeView2 = null;
                                                                                                                                }
                                                                                                                                aetherGaugeView2.invalidate();
                                                                                                                                navCardView3 = this.this$0.navCard;
                                                                                                                                if (navCardView3 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("navCard");
                                                                                                                                    navCardView3 = null;
                                                                                                                                }
                                                                                                                                navCardView3.invalidate();
                                                                                                                                slowReasonView2 = this.this$0.slowCard;
                                                                                                                                if (slowReasonView2 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("slowCard");
                                                                                                                                    slowReasonView2 = null;
                                                                                                                                }
                                                                                                                                slowReasonView2.invalidate();
                                                                                                                                laneCardView3 = this.this$0.laneCard;
                                                                                                                                if (laneCardView3 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("laneCard");
                                                                                                                                    laneCardView3 = null;
                                                                                                                                }
                                                                                                                                laneCardView3.invalidate();
                                                                                                                                blinkerCardView2 = this.this$0.blinkCard;
                                                                                                                                if (blinkerCardView2 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("blinkCard");
                                                                                                                                    blinkerCardView2 = null;
                                                                                                                                }
                                                                                                                                blinkerCardView2.invalidate();
                                                                                                                                gpsCardView2 = this.this$0.gpsCard;
                                                                                                                                if (gpsCardView2 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("gpsCard");
                                                                                                                                    gpsCardView2 = null;
                                                                                                                                }
                                                                                                                                if (gpsCardView2.getVisibility() == 0) {
                                                                                                                                    gpsCardView3 = this.this$0.gpsCard;
                                                                                                                                    if (gpsCardView3 == null) {
                                                                                                                                        Intrinsics.throwUninitializedPropertyAccessException("gpsCard");
                                                                                                                                        gpsCardView3 = null;
                                                                                                                                    }
                                                                                                                                    gpsCardView3.invalidate();
                                                                                                                                }
                                                                                                                                nudgeHintView4 = this.this$0.nudgeHint;
                                                                                                                                if (nudgeHintView4 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("nudgeHint");
                                                                                                                                    nudgeHintView4 = null;
                                                                                                                                }
                                                                                                                                nudgeHintView4.invalidate();
                                                                                                                                weatherView2 = this.this$0.weather;
                                                                                                                                if (weatherView2 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("weather");
                                                                                                                                    weatherView2 = null;
                                                                                                                                }
                                                                                                                                weatherView2.invalidate();
                                                                                                                                slcSourcesView2 = this.this$0.slcSources;
                                                                                                                                if (slcSourcesView2 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("slcSources");
                                                                                                                                    slcSourcesView2 = null;
                                                                                                                                }
                                                                                                                                slcSourcesView2.invalidate();
                                                                                                                                linearLayout4 = this.this$0.sidebar;
                                                                                                                                if (linearLayout4 == null) {
                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("sidebar");
                                                                                                                                    linearLayout4 = null;
                                                                                                                                }
                                                                                                                                if (linearLayout4.getVisibility() == 0) {
                                                                                                                                    i22 = this.this$0.tick;
                                                                                                                                    if (i22 % 5 == 0) {
                                                                                                                                        TextView textView39 = (TextView) this.this$0.findViewById(R.id.sbName);
                                                                                                                                        String pStr$default2 = Telemetry.pStr$default(t, "GalaxyDeviceName", null, 2, null);
                                                                                                                                        if (pStr$default2.length() == 0) {
                                                                                                                                            pStr$default2 = t.pStr("DongleId", "comma");
                                                                                                                                        }
                                                                                                                                        textView39.setText(pStr$default2);
                                                                                                                                        String bars = StringsKt.repeat("●", RangesKt.coerceIn(t.getNetworkStrength(), 0, 4)) + StringsKt.repeat("○", 4 - RangesKt.coerceIn(t.getNetworkStrength(), 0, 4));
                                                                                                                                        TextView textView40 = (TextView) this.this$0.findViewById(R.id.sbNet);
                                                                                                                                        String networkType = t.getNetworkType();
                                                                                                                                        switch (networkType.hashCode()) {
                                                                                                                                            case -1419358249:
                                                                                                                                                if (networkType.equals("ethernet")) {
                                                                                                                                                    str14 = "ETH";
                                                                                                                                                    break;
                                                                                                                                                }
                                                                                                                                                str14 = "--";
                                                                                                                                                break;
                                                                                                                                            case -1364082857:
                                                                                                                                                if (networkType.equals("cell2g")) {
                                                                                                                                                    str14 = "2G";
                                                                                                                                                    break;
                                                                                                                                                }
                                                                                                                                                str14 = "--";
                                                                                                                                                break;
                                                                                                                                            case -1364082826:
                                                                                                                                                if (networkType.equals("cell3g")) {
                                                                                                                                                    str14 = "3G";
                                                                                                                                                    break;
                                                                                                                                                }
                                                                                                                                                str14 = "--";
                                                                                                                                                break;
                                                                                                                                            case -1364082795:
                                                                                                                                                if (networkType.equals("cell4g")) {
                                                                                                                                                    str14 = "LTE";
                                                                                                                                                    break;
                                                                                                                                                }
                                                                                                                                                str14 = "--";
                                                                                                                                                break;
                                                                                                                                            case -1364082764:
                                                                                                                                                if (networkType.equals("cell5g")) {
                                                                                                                                                    str14 = "5G";
                                                                                                                                                    break;
                                                                                                                                                }
                                                                                                                                                str14 = "--";
                                                                                                                                                break;
                                                                                                                                            case 3649301:
                                                                                                                                                if (networkType.equals("wifi")) {
                                                                                                                                                    str14 = "Wi-Fi";
                                                                                                                                                    break;
                                                                                                                                                }
                                                                                                                                                str14 = "--";
                                                                                                                                                break;
                                                                                                                                            default:
                                                                                                                                                str14 = "--";
                                                                                                                                                break;
                                                                                                                                        }
                                                                                                                                        textView40.setText(bars + "  " + str14);
                                                                                                                                        TextView $this$run_u24lambda_u2419 = (TextView) this.this$0.findViewById(R.id.sbTempV);
                                                                                                                                        $this$run_u24lambda_u2419.setText(MathKt.roundToInt(t.getMaxTempC()) + "°C");
                                                                                                                                        String thermalStatus = t.getThermalStatus();
                                                                                                                                        if (Intrinsics.areEqual(thermalStatus, "green")) {
                                                                                                                                            rgb = -1;
                                                                                                                                        } else {
                                                                                                                                            if (Intrinsics.areEqual(thermalStatus, "yellow")) {
                                                                                                                                                i23 = 202;
                                                                                                                                                i24 = 37;
                                                                                                                                                i25 = 218;
                                                                                                                                            } else {
                                                                                                                                                i23 = 34;
                                                                                                                                                i24 = 49;
                                                                                                                                                i25 = 201;
                                                                                                                                            }
                                                                                                                                            rgb = Color.rgb(i25, i23, i24);
                                                                                                                                        }
                                                                                                                                        $this$run_u24lambda_u2419.setTextColor(rgb);
                                                                                                                                        TextView $this$run_u24lambda_u2420 = (TextView) this.this$0.findViewById(R.id.sbVehV);
                                                                                                                                        $this$run_u24lambda_u2420.setText(t.getStarted() ? "ONLINE" : "OFFROAD");
                                                                                                                                        $this$run_u24lambda_u2420.setTextColor(t.getStarted() ? -1 : Color.rgb(166, 166, 166));
                                                                                                                                        boolean athenaOk = t.getLastAthenaPing() > 0 && (System.currentTimeMillis() / ((long) 1000)) - t.getLastAthenaPing() < 80;
                                                                                                                                        TextView $this$run_u24lambda_u2421 = (TextView) this.this$0.findViewById(R.id.sbConnV);
                                                                                                                                        $this$run_u24lambda_u2421.setText(athenaOk ? "ONLINE" : "OFFLINE");
                                                                                                                                        $this$run_u24lambda_u2421.setTextColor(athenaOk ? -1 : Color.rgb(201, 34, 49));
                                                                                                                                        boolean fon = DriveState.INSTANCE.forcedOnroad();
                                                                                                                                        boolean foff = DriveState.INSTANCE.forcedOffroad();
                                                                                                                                        ((Button) this.this$0.findViewById(R.id.dsDefault)).setAlpha((fon || foff) ? 1056964608 : i13);
                                                                                                                                        ((Button) this.this$0.findViewById(R.id.dsOnroad)).setAlpha(fon ? i13 : 1056964608);
                                                                                                                                        ((Button) this.this$0.findViewById(R.id.dsOffroad)).setAlpha(foff ? i13 : 1056964608);
                                                                                                                                    }
                                                                                                                                }
                                                                                                                                i21 = this.this$0.tick;
                                                                                                                                if (i21 % 10 == 0) {
                                                                                                                                    dataClient8 = this.this$0.data;
                                                                                                                                    if (dataClient8 == null) {
                                                                                                                                        Intrinsics.throwUninitializedPropertyAccessException("data");
                                                                                                                                        dataClient8 = null;
                                                                                                                                    }
                                                                                                                                    dataClient8.ping();
                                                                                                                                    z17 = this.this$0.statsVisible;
                                                                                                                                    if (z17) {
                                                                                                                                        Collection<Long> values = t.getByteCount().values();
                                                                                                                                        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                                                                                                                                        long kb = CollectionsKt.sumOfLong(values) / 1024;
                                                                                                                                        textView25 = this.this$0.statsTv;
                                                                                                                                        if (textView25 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("statsTv");
                                                                                                                                            textView25 = null;
                                                                                                                                        }
                                                                                                                                        MainActivity mainActivity12 = this.this$0;
                                                                                                                                        StringBuilder $this$run_u24lambda_u2427 = new StringBuilder();
                                                                                                                                        str4 = mainActivity12.linkIf;
                                                                                                                                        if (t.getRttMs() >= 0.0d) {
                                                                                                                                            str5 = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(t.getRttMs())}, 1));
                                                                                                                                            Intrinsics.checkNotNullExpressionValue(str5, "format(...)");
                                                                                                                                        } else {
                                                                                                                                            str5 = "–";
                                                                                                                                        }
                                                                                                                                        $this$run_u24lambda_u2427.append("link   " + str4 + "  rtt " + str5 + " ms  " + kb + " kB\n");
                                                                                                                                        $this$run_u24lambda_u2427.append("comma  cpu " + MathKt.roundToInt(t.getCpuUsage()) + "%  " + MathKt.roundToInt(t.getCpuTempC()) + "°C  " + t.getThermalStatus() + "  " + t.getDeviceType() + "/" + t.getSensor() + "\n");
                                                                                                                                        videoClient = mainActivity12.video;
                                                                                                                                        if (videoClient == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("video");
                                                                                                                                            videoClient = null;
                                                                                                                                        }
                                                                                                                                        String format3 = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(videoClient.getFps())}, 1));
                                                                                                                                        Intrinsics.checkNotNullExpressionValue(format3, "format(...)");
                                                                                                                                        videoClient2 = mainActivity12.video;
                                                                                                                                        if (videoClient2 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("video");
                                                                                                                                            videoClient2 = null;
                                                                                                                                        }
                                                                                                                                        int lastW = videoClient2.getLastW();
                                                                                                                                        videoClient3 = mainActivity12.video;
                                                                                                                                        if (videoClient3 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("video");
                                                                                                                                            videoClient3 = null;
                                                                                                                                        }
                                                                                                                                        int lastH = videoClient3.getLastH();
                                                                                                                                        videoClient4 = mainActivity12.video;
                                                                                                                                        if (videoClient4 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("video");
                                                                                                                                            videoClient4 = null;
                                                                                                                                        }
                                                                                                                                        String format4 = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(videoClient4.getDecodeMs())}, 1));
                                                                                                                                        Intrinsics.checkNotNullExpressionValue(format4, "format(...)");
                                                                                                                                        videoClient5 = mainActivity12.video;
                                                                                                                                        if (videoClient5 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("video");
                                                                                                                                            videoClient5 = null;
                                                                                                                                        }
                                                                                                                                        TextView textView41 = textView25;
                                                                                                                                        $this$run_u24lambda_u2427.append("video  " + format3 + " fps  " + lastW + "x" + lastH + "  dec " + format4 + " ms  drop " + videoClient5.getDropped() + "\n");
                                                                                                                                        detector2 = mainActivity12.detectorRef;
                                                                                                                                        if (detector2 == null || !detector2.getRunning()) {
                                                                                                                                            t2 = t;
                                                                                                                                            textView26 = textView41;
                                                                                                                                            sb = $this$run_u24lambda_u2427;
                                                                                                                                            str6 = "settings";
                                                                                                                                        } else {
                                                                                                                                            final Iterable $this$groupingBy$iv = detector2.getDets();
                                                                                                                                            Iterable $this$sortedByDescending$iv = GroupingKt.eachCount(new Grouping<Detector.Det, String>() { // from class: com.starpilot.starview.MainActivity$uiTick$1$run$lambda$27$$inlined$groupingBy$1
                                                                                                                                                @Override // kotlin.collections.Grouping
                                                                                                                                                public Iterator<Detector.Det> sourceIterator() {
                                                                                                                                                    return $this$groupingBy$iv.iterator();
                                                                                                                                                }

                                                                                                                                                @Override // kotlin.collections.Grouping
                                                                                                                                                public String keyOf(Detector.Det element) {
                                                                                                                                                    Detector.Det it7 = element;
                                                                                                                                                    return it7.getName();
                                                                                                                                                }
                                                                                                                                            }).entrySet();
                                                                                                                                            String counts = CollectionsKt.joinToString(CollectionsKt.sortedWith($this$sortedByDescending$iv, new Comparator() { // from class: com.starpilot.starview.MainActivity$uiTick$1$run$lambda$27$$inlined$sortedByDescending$1
                                                                                                                                                @Override // java.util.Comparator
                                                                                                                                                public final int compare(T t3, T t4) {
                                                                                                                                                    Map.Entry it7 = (Map.Entry) t4;
                                                                                                                                                    Integer num = (Integer) it7.getValue();
                                                                                                                                                    Map.Entry it8 = (Map.Entry) t3;
                                                                                                                                                    return ComparisonsKt.compareValues(num, (Integer) it8.getValue());
                                                                                                                                                }
                                                                                                                                            }), " ", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.MainActivity$uiTick$1$$ExternalSyntheticLambda1
                                                                                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                                                                                public final Object invoke(Object obj2) {
                                                                                                                                                    CharSequence run$lambda$27$lambda$24;
                                                                                                                                                    run$lambda$27$lambda$24 = MainActivity$uiTick$1.run$lambda$27$lambda$24((Map.Entry) obj2);
                                                                                                                                                    return run$lambda$27$lambda$24;
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                                            String status4 = detector2.getStatus();
                                                                                                                                            String accel = detector2.getAccel();
                                                                                                                                            String format5 = String.format("%.0f", Arrays.copyOf(new Object[]{Double.valueOf(detector2.getInferMs())}, 1));
                                                                                                                                            Intrinsics.checkNotNullExpressionValue(format5, "format(...)");
                                                                                                                                            String format6 = String.format("%.0f", Arrays.copyOf(new Object[]{Double.valueOf(detector2.getPrepMs())}, 1));
                                                                                                                                            Intrinsics.checkNotNullExpressionValue(format6, "format(...)");
                                                                                                                                            String format7 = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(detector2.getFps())}, 1));
                                                                                                                                            Intrinsics.checkNotNullExpressionValue(format7, "format(...)");
                                                                                                                                            textView26 = textView41;
                                                                                                                                            String format8 = String.format("%.0f", Arrays.copyOf(new Object[]{Double.valueOf(detector2.getRoadFps())}, 1));
                                                                                                                                            Intrinsics.checkNotNullExpressionValue(format8, "format(...)");
                                                                                                                                            sb = $this$run_u24lambda_u2427;
                                                                                                                                            String format9 = String.format("%.0f", Arrays.copyOf(new Object[]{Double.valueOf(detector2.getWideFps())}, 1));
                                                                                                                                            Intrinsics.checkNotNullExpressionValue(format9, "format(...)");
                                                                                                                                            videoClient6 = mainActivity12.detRoad;
                                                                                                                                            if (videoClient6 == null) {
                                                                                                                                                Intrinsics.throwUninitializedPropertyAccessException("detRoad");
                                                                                                                                                videoClient6 = null;
                                                                                                                                            }
                                                                                                                                            String str18 = videoClient6.getRunning() ? "road " : "";
                                                                                                                                            videoClient7 = mainActivity12.detWide;
                                                                                                                                            if (videoClient7 == null) {
                                                                                                                                                Intrinsics.throwUninitializedPropertyAccessException("detWide");
                                                                                                                                                videoClient7 = null;
                                                                                                                                            }
                                                                                                                                            if (videoClient7.getRunning()) {
                                                                                                                                                t2 = t;
                                                                                                                                                str11 = "wide";
                                                                                                                                            } else {
                                                                                                                                                t2 = t;
                                                                                                                                                str11 = "";
                                                                                                                                            }
                                                                                                                                            str6 = "settings";
                                                                                                                                            $this$run_u24lambda_u2427.append("detect " + status4 + "  " + accel + "  model " + format5 + " ms  prep " + format6 + " ms  " + format7 + " /s (road " + format8 + " wide " + format9 + ")  cams " + str18 + str11 + "\n");
                                                                                                                                            if (detector2.getLaneMarks() != null) {
                                                                                                                                                laneMarks = mainActivity12.laneMarks;
                                                                                                                                                $this$run_u24lambda_u2427.append("lines  " + laneMarks.getDebug() + "\n");
                                                                                                                                            }
                                                                                                                                            layoutSeg = mainActivity12.layoutSeg;
                                                                                                                                            if (layoutSeg == null) {
                                                                                                                                                Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                                                                                                                                layoutSeg = null;
                                                                                                                                            }
                                                                                                                                            if (layoutSeg.getEnabled()) {
                                                                                                                                                layoutSeg3 = mainActivity12.layoutSeg;
                                                                                                                                                if (layoutSeg3 == null) {
                                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                                                                                                                                    layoutSeg3 = null;
                                                                                                                                                }
                                                                                                                                                String status5 = layoutSeg3.getStatus();
                                                                                                                                                layoutSeg4 = mainActivity12.layoutSeg;
                                                                                                                                                if (layoutSeg4 == null) {
                                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                                                                                                                                    layoutSeg4 = null;
                                                                                                                                                }
                                                                                                                                                String accel2 = layoutSeg4.getAccel();
                                                                                                                                                layoutSeg5 = mainActivity12.layoutSeg;
                                                                                                                                                if (layoutSeg5 == null) {
                                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                                                                                                                                    layoutSeg5 = null;
                                                                                                                                                }
                                                                                                                                                String format10 = String.format("%.0f", Arrays.copyOf(new Object[]{Double.valueOf(layoutSeg5.getMs())}, 1));
                                                                                                                                                Intrinsics.checkNotNullExpressionValue(format10, "format(...)");
                                                                                                                                                layoutSeg6 = mainActivity12.layoutSeg;
                                                                                                                                                if (layoutSeg6 == null) {
                                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                                                                                                                                    layoutSeg6 = null;
                                                                                                                                                }
                                                                                                                                                if (layoutSeg6.getGpuError().length() > 0) {
                                                                                                                                                    layoutSeg7 = mainActivity12.layoutSeg;
                                                                                                                                                    if (layoutSeg7 == null) {
                                                                                                                                                        Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                                                                                                                                        layoutSeg7 = null;
                                                                                                                                                    }
                                                                                                                                                    str13 = "  gpu error: " + layoutSeg7.getGpuError();
                                                                                                                                                } else {
                                                                                                                                                    str13 = "";
                                                                                                                                                }
                                                                                                                                                str12 = status5 + " " + accel2 + " " + format10 + " ms" + str13;
                                                                                                                                            } else {
                                                                                                                                                layoutSeg2 = mainActivity12.layoutSeg;
                                                                                                                                                if (layoutSeg2 == null) {
                                                                                                                                                    Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                                                                                                                                    layoutSeg2 = null;
                                                                                                                                                }
                                                                                                                                                str12 = !layoutSeg2.getHasModel() ? "no model" : "off";
                                                                                                                                            }
                                                                                                                                            $this$run_u24lambda_u2427.append("layout " + str12 + "\n");
                                                                                                                                            if (detector2.getOrientError().length() > 0) {
                                                                                                                                                format = "ERROR " + StringsKt.take(detector2.getOrientError(), 60);
                                                                                                                                            } else {
                                                                                                                                                format = String.format("%.1f ms/car", Arrays.copyOf(new Object[]{Double.valueOf(detector2.getOrientMs())}, 1));
                                                                                                                                                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                                                                                                                                            }
                                                                                                                                            $this$run_u24lambda_u2427.append("orient " + format + "\n");
                                                                                                                                            double d = 1000;
                                                                                                                                            String format11 = String.format("%.0f", Arrays.copyOf(new Object[]{Double.valueOf(detector2.getLoadMs() / d)}, 1));
                                                                                                                                            Intrinsics.checkNotNullExpressionValue(format11, "format(...)");
                                                                                                                                            String format12 = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(detector2.getFirstRunMs() / d)}, 1));
                                                                                                                                            Intrinsics.checkNotNullExpressionValue(format12, "format(...)");
                                                                                                                                            String str19 = counts;
                                                                                                                                            if (str19.length() == 0) {
                                                                                                                                                str19 = "nothing";
                                                                                                                                            }
                                                                                                                                            $this$run_u24lambda_u2427.append("  load " + format11 + " s  first run " + format12 + " s  found: " + ((Object) str19) + (detector2.getGpuError().length() > 0 ? "   gpu: " + StringsKt.take(detector2.getGpuError(), 90) : "") + "\n");
                                                                                                                                        }
                                                                                                                                        str7 = mainActivity12.lastCrash;
                                                                                                                                        if (str7.length() > 0) {
                                                                                                                                            str10 = mainActivity12.lastCrash;
                                                                                                                                            $this$run_u24lambda_u2427.append("LAST CRASH  " + StringsKt.take(StringsKt.replace(str10, "\n", " | ", false), 300) + "\n");
                                                                                                                                        }
                                                                                                                                        it = mainActivity12.scene;
                                                                                                                                        if (it != null) {
                                                                                                                                            $this$run_u24lambda_u2427.append("scene  cars " + it.getShownCars() + "  " + (it.getFailure().length() > 0 ? "ERROR " + StringsKt.take(it.getFailure(), 90) : "ok") + "\n");
                                                                                                                                        }
                                                                                                                                        uiSettings6 = mainActivity12.settings;
                                                                                                                                        if (uiSettings6 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException(str6);
                                                                                                                                            uiSettings6 = null;
                                                                                                                                        }
                                                                                                                                        if (uiSettings6.get("map_roads")) {
                                                                                                                                            String tileStatus = OsmRoads.INSTANCE.getTileStatus();
                                                                                                                                            sceneView = mainActivity12.scene;
                                                                                                                                            if (sceneView == null || (str9 = sceneView.getMapStatus()) == null) {
                                                                                                                                                str9 = "";
                                                                                                                                            }
                                                                                                                                            $this$run_u24lambda_u2427.append("map    " + tileStatus + "  " + str9 + "\n");
                                                                                                                                        }
                                                                                                                                        String str20 = t2.getCalValid() ? "ok" : "NO";
                                                                                                                                        String format13 = String.format("%.3f %.3f %.3f", Arrays.copyOf(new Object[]{Float.valueOf(t2.getCalRpy()[0]), Float.valueOf(t2.getCalRpy()[1]), Float.valueOf(t2.getCalRpy()[2])}, 3));
                                                                                                                                        Intrinsics.checkNotNullExpressionValue(format13, "format(...)");
                                                                                                                                        String format14 = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(t2.getCalHeight())}, 1));
                                                                                                                                        Intrinsics.checkNotNullExpressionValue(format14, "format(...)");
                                                                                                                                        overlayView4 = mainActivity12.overlay;
                                                                                                                                        if (overlayView4 == null) {
                                                                                                                                            Intrinsics.throwUninitializedPropertyAccessException("overlay");
                                                                                                                                            overlayView4 = null;
                                                                                                                                        }
                                                                                                                                        String format15 = String.format("%.2f", Arrays.copyOf(new Object[]{Double.valueOf(overlayView4.getProj().getZoom())}, 1));
                                                                                                                                        Intrinsics.checkNotNullExpressionValue(format15, "format(...)");
                                                                                                                                        $this$run_u24lambda_u2427.append("calib  " + str20 + "  rpy " + format13 + "  h " + format14 + "  zoom " + format15 + "\n");
                                                                                                                                        Long l = t2.getMsgCount().get("modelV2");
                                                                                                                                        long longValue = l != null ? l.longValue() : 0L;
                                                                                                                                        Long l2 = t2.getMsgCount().get("carState");
                                                                                                                                        $this$run_u24lambda_u2427.append("model  " + longValue + "  cs " + (l2 != null ? l2.longValue() : 0L) + "  toggles " + t2.getToggles().size() + "  params " + t2.getParams().size() + "\n");
                                                                                                                                        if (t2.getLeadOne().getStatus()) {
                                                                                                                                            str8 = String.format("%.1f m  %.1f m/s", Arrays.copyOf(new Object[]{Float.valueOf(t2.getLeadOne().getDRel()), Float.valueOf(t2.getLeadOne().getVRel())}, 2));
                                                                                                                                            Intrinsics.checkNotNullExpressionValue(str8, "format(...)");
                                                                                                                                        } else {
                                                                                                                                            str8 = "none";
                                                                                                                                        }
                                                                                                                                        String format16 = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(t2.getSteeringAngleDeg())}, 1));
                                                                                                                                        Intrinsics.checkNotNullExpressionValue(format16, "format(...)");
                                                                                                                                        String format17 = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(t2.getOutTorque())}, 1));
                                                                                                                                        Intrinsics.checkNotNullExpressionValue(format17, "format(...)");
                                                                                                                                        $this$run_u24lambda_u2427.append("lead   " + str8 + "  steer " + format16 + "°  torque " + format17);
                                                                                                                                        Unit unit15 = Unit.INSTANCE;
                                                                                                                                        String sb2 = sb.toString();
                                                                                                                                        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
                                                                                                                                        textView26.setText(sb2);
                                                                                                                                    }
                                                                                                                                }
                                                                                                                                this.this$0.applyHud();
                                                                                                                                GpsBridge gpsBridge2 = GpsBridge.INSTANCE;
                                                                                                                                telemetry = this.this$0.telemetry;
                                                                                                                                String pStr$default3 = Telemetry.pStr$default(telemetry, "LastGPSPosition", null, 2, null);
                                                                                                                                telemetry2 = this.this$0.telemetry;
                                                                                                                                gpsBridge2.fallback(pStr$default3, telemetry2.getVEgo());
                                                                                                                                handler = this.this$0.ui;
                                                                                                                                handler.postDelayed(this, 50L);
                                                                                                                                return;
                                                                                                                            }
                                                                                                                        }
                                                                                                                        showAlert = false;
                                                                                                                        MainActivity mainActivity1022 = this.this$0;
                                                                                                                        f4 = this.this$0.alertAlpha;
                                                                                                                        if (showAlert) {
                                                                                                                        }
                                                                                                                        f5 = this.this$0.alertAlpha;
                                                                                                                        mainActivity1022.alertAlpha = f4 + ((stall3 - f5) * 0.35f);
                                                                                                                        f6 = this.this$0.alertAlpha;
                                                                                                                        if (f6 > 0.02f) {
                                                                                                                        }
                                                                                                                        ModelSnapshot model22 = t.getModel();
                                                                                                                        if (model22 != null) {
                                                                                                                        }
                                                                                                                        j6 = this.this$0.lastModelFrame;
                                                                                                                        if (mf == j6) {
                                                                                                                        }
                                                                                                                        this.this$0.lastModelFrame = mf;
                                                                                                                        overlayView3 = this.this$0.overlay;
                                                                                                                        if (overlayView3 == null) {
                                                                                                                        }
                                                                                                                        overlayView3.invalidate();
                                                                                                                        wheelButton2 = this.this$0.wheel;
                                                                                                                        if (wheelButton2 == null) {
                                                                                                                        }
                                                                                                                        wheelButton2.invalidate();
                                                                                                                        dmView = this.this$0.dm;
                                                                                                                        if (dmView == null) {
                                                                                                                        }
                                                                                                                        dmView.invalidate();
                                                                                                                        miciDistanceView2 = this.this$0.personality;
                                                                                                                        if (miciDistanceView2 == null) {
                                                                                                                        }
                                                                                                                        miciDistanceView2.invalidate();
                                                                                                                        slcView2 = this.this$0.slc;
                                                                                                                        if (slcView2 == null) {
                                                                                                                        }
                                                                                                                        slcView2.invalidate();
                                                                                                                        slcActionsView2 = this.this$0.slcActions;
                                                                                                                        if (slcActionsView2 == null) {
                                                                                                                        }
                                                                                                                        slcActionsView2.invalidate();
                                                                                                                        devSidebarView2 = this.this$0.devSidebar;
                                                                                                                        if (devSidebarView2 == null) {
                                                                                                                        }
                                                                                                                        if (devSidebarView2.getVisibility() == 0) {
                                                                                                                        }
                                                                                                                        aetherGaugeView2 = this.this$0.gauge;
                                                                                                                        if (aetherGaugeView2 == null) {
                                                                                                                        }
                                                                                                                        aetherGaugeView2.invalidate();
                                                                                                                        navCardView3 = this.this$0.navCard;
                                                                                                                        if (navCardView3 == null) {
                                                                                                                        }
                                                                                                                        navCardView3.invalidate();
                                                                                                                        slowReasonView2 = this.this$0.slowCard;
                                                                                                                        if (slowReasonView2 == null) {
                                                                                                                        }
                                                                                                                        slowReasonView2.invalidate();
                                                                                                                        laneCardView3 = this.this$0.laneCard;
                                                                                                                        if (laneCardView3 == null) {
                                                                                                                        }
                                                                                                                        laneCardView3.invalidate();
                                                                                                                        blinkerCardView2 = this.this$0.blinkCard;
                                                                                                                        if (blinkerCardView2 == null) {
                                                                                                                        }
                                                                                                                        blinkerCardView2.invalidate();
                                                                                                                        gpsCardView2 = this.this$0.gpsCard;
                                                                                                                        if (gpsCardView2 == null) {
                                                                                                                        }
                                                                                                                        if (gpsCardView2.getVisibility() == 0) {
                                                                                                                        }
                                                                                                                        nudgeHintView4 = this.this$0.nudgeHint;
                                                                                                                        if (nudgeHintView4 == null) {
                                                                                                                        }
                                                                                                                        nudgeHintView4.invalidate();
                                                                                                                        weatherView2 = this.this$0.weather;
                                                                                                                        if (weatherView2 == null) {
                                                                                                                        }
                                                                                                                        weatherView2.invalidate();
                                                                                                                        slcSourcesView2 = this.this$0.slcSources;
                                                                                                                        if (slcSourcesView2 == null) {
                                                                                                                        }
                                                                                                                        slcSourcesView2.invalidate();
                                                                                                                        linearLayout4 = this.this$0.sidebar;
                                                                                                                        if (linearLayout4 == null) {
                                                                                                                        }
                                                                                                                        if (linearLayout4.getVisibility() == 0) {
                                                                                                                        }
                                                                                                                        i21 = this.this$0.tick;
                                                                                                                        if (i21 % 10 == 0) {
                                                                                                                        }
                                                                                                                        this.this$0.applyHud();
                                                                                                                        GpsBridge gpsBridge22 = GpsBridge.INSTANCE;
                                                                                                                        telemetry = this.this$0.telemetry;
                                                                                                                        String pStr$default32 = Telemetry.pStr$default(telemetry, "LastGPSPosition", null, 2, null);
                                                                                                                        telemetry2 = this.this$0.telemetry;
                                                                                                                        gpsBridge22.fallback(pStr$default32, telemetry2.getVEgo());
                                                                                                                        handler = this.this$0.ui;
                                                                                                                        handler.postDelayed(this, 50L);
                                                                                                                        return;
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                        i17 = 8;
                                                                                                        textView19.setVisibility(i17);
                                                                                                        i18 = this.this$0.tick;
                                                                                                        if (i18 % 40 == 0) {
                                                                                                        }
                                                                                                        BlinkerTurn blinkerTurn22 = BlinkerTurn.INSTANCE;
                                                                                                        uiSettings4 = this.this$0.settings;
                                                                                                        if (uiSettings4 != null) {
                                                                                                        }
                                                                                                        int i3422 = i14;
                                                                                                        BlinkerTurn.tick$default(blinkerTurn22, t, uiSettings5, 0L, 4, null);
                                                                                                        this.this$0.ideasTick(t);
                                                                                                        DriveTrack.tick$default(DriveTrack.INSTANCE, t, 0L, 2, null);
                                                                                                        place = this.this$0.sharedPending;
                                                                                                        if (place != null) {
                                                                                                        }
                                                                                                        i19 = this.this$0.tick;
                                                                                                        if (i19 % 20 == 0) {
                                                                                                        }
                                                                                                        i20 = this.this$0.tick;
                                                                                                        if (i20 % 4 == 0) {
                                                                                                        }
                                                                                                        textView20 = this.this$0.mapBtn;
                                                                                                        if (textView20 == null) {
                                                                                                        }
                                                                                                        ViewGroup.LayoutParams layoutParams22 = textView20.getLayoutParams();
                                                                                                        Intrinsics.checkNotNull(layoutParams22, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                                                                                                        lp = (FrameLayout.LayoutParams) layoutParams22;
                                                                                                        MainActivity mainActivity822 = this.this$0;
                                                                                                        textView21 = mainActivity822.whereBtn;
                                                                                                        if (textView21 == null) {
                                                                                                        }
                                                                                                        want = mainActivity822.dp(textView21.getVisibility() != 0 ? 330 : 160);
                                                                                                        if (lp.getMarginStart() != want) {
                                                                                                        }
                                                                                                        Unit unit1322 = Unit.INSTANCE;
                                                                                                        MainActivity mainActivity922 = this.this$0;
                                                                                                        if (t.getParams().get("NavLanePositioningAllowed") == null) {
                                                                                                        }
                                                                                                        if (!t.getStarted()) {
                                                                                                        }
                                                                                                        r = new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0d, 0.0d, null, 30, null);
                                                                                                        nudgeHintView = mainActivity922.nudgeHint;
                                                                                                        if (nudgeHintView == null) {
                                                                                                        }
                                                                                                        nudgeHintView.setResult(r);
                                                                                                        nudgeHintView2 = mainActivity922.nudgeHint;
                                                                                                        if (nudgeHintView2 == null) {
                                                                                                        }
                                                                                                        nudgeHintView2.setMetric(t.isMetric());
                                                                                                        nudgeHintView3 = mainActivity922.nudgeHint;
                                                                                                        if (nudgeHintView3 == null) {
                                                                                                        }
                                                                                                        nudgeHintView3.setVisibility(r.getState() != NudgeLogic.State.NONE ? 8 : 0);
                                                                                                        Unit unit1422 = Unit.INSTANCE;
                                                                                                        weatherView = this.this$0.weather;
                                                                                                        if (weatherView == null) {
                                                                                                        }
                                                                                                        weatherView.setVisibility((t.getStarted() || !t.s("weather") || t.getWeatherId() <= 0) ? 8 : 0);
                                                                                                        textView22 = this.this$0.compass;
                                                                                                        if (textView22 == null) {
                                                                                                        }
                                                                                                        if (!t.getStarted()) {
                                                                                                        }
                                                                                                        textView22.setText(str3);
                                                                                                        textView23 = this.this$0.devMetrics;
                                                                                                        if (textView23 == null) {
                                                                                                        }
                                                                                                        textView23.setText((t.getStarted() || !t.s("dev_metrics")) ? "" : "CPU " + MathKt.roundToInt(t.getCpuUsage()) + "%  |  TEMP " + MathKt.roundToInt(t.getCpuTempC()) + "°C  |  RAM " + t.getMemUsage() + "%  |  DISK " + MathKt.roundToInt(t.getFreeSpacePct()) + "% free");
                                                                                                        long nowNs22 = System.nanoTime();
                                                                                                        if (t.getStarted()) {
                                                                                                        }
                                                                                                        if (t.getStarted()) {
                                                                                                        }
                                                                                                        this.this$0.standstillSinceNs = j3;
                                                                                                        j4 = this.this$0.standstillSinceNs;
                                                                                                        if (j4 == j3) {
                                                                                                        }
                                                                                                        float stoppedS22 = f2;
                                                                                                        j5 = this.this$0.onroadSinceNs;
                                                                                                        if (j5 == j3) {
                                                                                                        }
                                                                                                        float onroadS22 = f3;
                                                                                                        if (t.s("stopped_timer")) {
                                                                                                        }
                                                                                                        textView24 = this.this$0.speedTv;
                                                                                                        if (textView24 == null) {
                                                                                                        }
                                                                                                        z16 = this.this$0.night;
                                                                                                        textView24.setTextColor(!z16 ? -1 : Color.rgb(29, 29, 31));
                                                                                                        boolean hideNormal22 = !t.s("alerts");
                                                                                                        if (t.getStarted()) {
                                                                                                        }
                                                                                                        showAlert = false;
                                                                                                        MainActivity mainActivity10222 = this.this$0;
                                                                                                        f4 = this.this$0.alertAlpha;
                                                                                                        if (showAlert) {
                                                                                                        }
                                                                                                        f5 = this.this$0.alertAlpha;
                                                                                                        mainActivity10222.alertAlpha = f4 + ((stall3 - f5) * 0.35f);
                                                                                                        f6 = this.this$0.alertAlpha;
                                                                                                        if (f6 > 0.02f) {
                                                                                                        }
                                                                                                        ModelSnapshot model222 = t.getModel();
                                                                                                        if (model222 != null) {
                                                                                                        }
                                                                                                        j6 = this.this$0.lastModelFrame;
                                                                                                        if (mf == j6) {
                                                                                                        }
                                                                                                        this.this$0.lastModelFrame = mf;
                                                                                                        overlayView3 = this.this$0.overlay;
                                                                                                        if (overlayView3 == null) {
                                                                                                        }
                                                                                                        overlayView3.invalidate();
                                                                                                        wheelButton2 = this.this$0.wheel;
                                                                                                        if (wheelButton2 == null) {
                                                                                                        }
                                                                                                        wheelButton2.invalidate();
                                                                                                        dmView = this.this$0.dm;
                                                                                                        if (dmView == null) {
                                                                                                        }
                                                                                                        dmView.invalidate();
                                                                                                        miciDistanceView2 = this.this$0.personality;
                                                                                                        if (miciDistanceView2 == null) {
                                                                                                        }
                                                                                                        miciDistanceView2.invalidate();
                                                                                                        slcView2 = this.this$0.slc;
                                                                                                        if (slcView2 == null) {
                                                                                                        }
                                                                                                        slcView2.invalidate();
                                                                                                        slcActionsView2 = this.this$0.slcActions;
                                                                                                        if (slcActionsView2 == null) {
                                                                                                        }
                                                                                                        slcActionsView2.invalidate();
                                                                                                        devSidebarView2 = this.this$0.devSidebar;
                                                                                                        if (devSidebarView2 == null) {
                                                                                                        }
                                                                                                        if (devSidebarView2.getVisibility() == 0) {
                                                                                                        }
                                                                                                        aetherGaugeView2 = this.this$0.gauge;
                                                                                                        if (aetherGaugeView2 == null) {
                                                                                                        }
                                                                                                        aetherGaugeView2.invalidate();
                                                                                                        navCardView3 = this.this$0.navCard;
                                                                                                        if (navCardView3 == null) {
                                                                                                        }
                                                                                                        navCardView3.invalidate();
                                                                                                        slowReasonView2 = this.this$0.slowCard;
                                                                                                        if (slowReasonView2 == null) {
                                                                                                        }
                                                                                                        slowReasonView2.invalidate();
                                                                                                        laneCardView3 = this.this$0.laneCard;
                                                                                                        if (laneCardView3 == null) {
                                                                                                        }
                                                                                                        laneCardView3.invalidate();
                                                                                                        blinkerCardView2 = this.this$0.blinkCard;
                                                                                                        if (blinkerCardView2 == null) {
                                                                                                        }
                                                                                                        blinkerCardView2.invalidate();
                                                                                                        gpsCardView2 = this.this$0.gpsCard;
                                                                                                        if (gpsCardView2 == null) {
                                                                                                        }
                                                                                                        if (gpsCardView2.getVisibility() == 0) {
                                                                                                        }
                                                                                                        nudgeHintView4 = this.this$0.nudgeHint;
                                                                                                        if (nudgeHintView4 == null) {
                                                                                                        }
                                                                                                        nudgeHintView4.invalidate();
                                                                                                        weatherView2 = this.this$0.weather;
                                                                                                        if (weatherView2 == null) {
                                                                                                        }
                                                                                                        weatherView2.invalidate();
                                                                                                        slcSourcesView2 = this.this$0.slcSources;
                                                                                                        if (slcSourcesView2 == null) {
                                                                                                        }
                                                                                                        slcSourcesView2.invalidate();
                                                                                                        linearLayout4 = this.this$0.sidebar;
                                                                                                        if (linearLayout4 == null) {
                                                                                                        }
                                                                                                        if (linearLayout4.getVisibility() == 0) {
                                                                                                        }
                                                                                                        i21 = this.this$0.tick;
                                                                                                        if (i21 % 10 == 0) {
                                                                                                        }
                                                                                                        this.this$0.applyHud();
                                                                                                        GpsBridge gpsBridge222 = GpsBridge.INSTANCE;
                                                                                                        telemetry = this.this$0.telemetry;
                                                                                                        String pStr$default322 = Telemetry.pStr$default(telemetry, "LastGPSPosition", null, 2, null);
                                                                                                        telemetry2 = this.this$0.telemetry;
                                                                                                        gpsBridge222.fallback(pStr$default322, telemetry2.getVEgo());
                                                                                                        handler = this.this$0.ui;
                                                                                                        handler.postDelayed(this, 50L);
                                                                                                        return;
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                        i16 = 8;
                                                                                        textView18.setVisibility(i16);
                                                                                        textView19 = this.this$0.mapBtn;
                                                                                        if (textView19 == null) {
                                                                                        }
                                                                                        dataClient7 = this.this$0.data;
                                                                                        if (dataClient7 == null) {
                                                                                        }
                                                                                        if (dataClient7.getConnected()) {
                                                                                        }
                                                                                        i17 = 8;
                                                                                        textView19.setVisibility(i17);
                                                                                        i18 = this.this$0.tick;
                                                                                        if (i18 % 40 == 0) {
                                                                                        }
                                                                                        BlinkerTurn blinkerTurn222 = BlinkerTurn.INSTANCE;
                                                                                        uiSettings4 = this.this$0.settings;
                                                                                        if (uiSettings4 != null) {
                                                                                        }
                                                                                        int i34222 = i14;
                                                                                        BlinkerTurn.tick$default(blinkerTurn222, t, uiSettings5, 0L, 4, null);
                                                                                        this.this$0.ideasTick(t);
                                                                                        DriveTrack.tick$default(DriveTrack.INSTANCE, t, 0L, 2, null);
                                                                                        place = this.this$0.sharedPending;
                                                                                        if (place != null) {
                                                                                        }
                                                                                        i19 = this.this$0.tick;
                                                                                        if (i19 % 20 == 0) {
                                                                                        }
                                                                                        i20 = this.this$0.tick;
                                                                                        if (i20 % 4 == 0) {
                                                                                        }
                                                                                        textView20 = this.this$0.mapBtn;
                                                                                        if (textView20 == null) {
                                                                                        }
                                                                                        ViewGroup.LayoutParams layoutParams222 = textView20.getLayoutParams();
                                                                                        Intrinsics.checkNotNull(layoutParams222, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                                                                                        lp = (FrameLayout.LayoutParams) layoutParams222;
                                                                                        MainActivity mainActivity8222 = this.this$0;
                                                                                        textView21 = mainActivity8222.whereBtn;
                                                                                        if (textView21 == null) {
                                                                                        }
                                                                                        want = mainActivity8222.dp(textView21.getVisibility() != 0 ? 330 : 160);
                                                                                        if (lp.getMarginStart() != want) {
                                                                                        }
                                                                                        Unit unit13222 = Unit.INSTANCE;
                                                                                        MainActivity mainActivity9222 = this.this$0;
                                                                                        if (t.getParams().get("NavLanePositioningAllowed") == null) {
                                                                                        }
                                                                                        if (!t.getStarted()) {
                                                                                        }
                                                                                        r = new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0d, 0.0d, null, 30, null);
                                                                                        nudgeHintView = mainActivity9222.nudgeHint;
                                                                                        if (nudgeHintView == null) {
                                                                                        }
                                                                                        nudgeHintView.setResult(r);
                                                                                        nudgeHintView2 = mainActivity9222.nudgeHint;
                                                                                        if (nudgeHintView2 == null) {
                                                                                        }
                                                                                        nudgeHintView2.setMetric(t.isMetric());
                                                                                        nudgeHintView3 = mainActivity9222.nudgeHint;
                                                                                        if (nudgeHintView3 == null) {
                                                                                        }
                                                                                        nudgeHintView3.setVisibility(r.getState() != NudgeLogic.State.NONE ? 8 : 0);
                                                                                        Unit unit14222 = Unit.INSTANCE;
                                                                                        weatherView = this.this$0.weather;
                                                                                        if (weatherView == null) {
                                                                                        }
                                                                                        weatherView.setVisibility((t.getStarted() || !t.s("weather") || t.getWeatherId() <= 0) ? 8 : 0);
                                                                                        textView22 = this.this$0.compass;
                                                                                        if (textView22 == null) {
                                                                                        }
                                                                                        if (!t.getStarted()) {
                                                                                        }
                                                                                        textView22.setText(str3);
                                                                                        textView23 = this.this$0.devMetrics;
                                                                                        if (textView23 == null) {
                                                                                        }
                                                                                        textView23.setText((t.getStarted() || !t.s("dev_metrics")) ? "" : "CPU " + MathKt.roundToInt(t.getCpuUsage()) + "%  |  TEMP " + MathKt.roundToInt(t.getCpuTempC()) + "°C  |  RAM " + t.getMemUsage() + "%  |  DISK " + MathKt.roundToInt(t.getFreeSpacePct()) + "% free");
                                                                                        long nowNs222 = System.nanoTime();
                                                                                        if (t.getStarted()) {
                                                                                        }
                                                                                        if (t.getStarted()) {
                                                                                        }
                                                                                        this.this$0.standstillSinceNs = j3;
                                                                                        j4 = this.this$0.standstillSinceNs;
                                                                                        if (j4 == j3) {
                                                                                        }
                                                                                        float stoppedS222 = f2;
                                                                                        j5 = this.this$0.onroadSinceNs;
                                                                                        if (j5 == j3) {
                                                                                        }
                                                                                        float onroadS222 = f3;
                                                                                        if (t.s("stopped_timer")) {
                                                                                        }
                                                                                        textView24 = this.this$0.speedTv;
                                                                                        if (textView24 == null) {
                                                                                        }
                                                                                        z16 = this.this$0.night;
                                                                                        textView24.setTextColor(!z16 ? -1 : Color.rgb(29, 29, 31));
                                                                                        boolean hideNormal222 = !t.s("alerts");
                                                                                        if (t.getStarted()) {
                                                                                        }
                                                                                        showAlert = false;
                                                                                        MainActivity mainActivity102222 = this.this$0;
                                                                                        f4 = this.this$0.alertAlpha;
                                                                                        if (showAlert) {
                                                                                        }
                                                                                        f5 = this.this$0.alertAlpha;
                                                                                        mainActivity102222.alertAlpha = f4 + ((stall3 - f5) * 0.35f);
                                                                                        f6 = this.this$0.alertAlpha;
                                                                                        if (f6 > 0.02f) {
                                                                                        }
                                                                                        ModelSnapshot model2222 = t.getModel();
                                                                                        if (model2222 != null) {
                                                                                        }
                                                                                        j6 = this.this$0.lastModelFrame;
                                                                                        if (mf == j6) {
                                                                                        }
                                                                                        this.this$0.lastModelFrame = mf;
                                                                                        overlayView3 = this.this$0.overlay;
                                                                                        if (overlayView3 == null) {
                                                                                        }
                                                                                        overlayView3.invalidate();
                                                                                        wheelButton2 = this.this$0.wheel;
                                                                                        if (wheelButton2 == null) {
                                                                                        }
                                                                                        wheelButton2.invalidate();
                                                                                        dmView = this.this$0.dm;
                                                                                        if (dmView == null) {
                                                                                        }
                                                                                        dmView.invalidate();
                                                                                        miciDistanceView2 = this.this$0.personality;
                                                                                        if (miciDistanceView2 == null) {
                                                                                        }
                                                                                        miciDistanceView2.invalidate();
                                                                                        slcView2 = this.this$0.slc;
                                                                                        if (slcView2 == null) {
                                                                                        }
                                                                                        slcView2.invalidate();
                                                                                        slcActionsView2 = this.this$0.slcActions;
                                                                                        if (slcActionsView2 == null) {
                                                                                        }
                                                                                        slcActionsView2.invalidate();
                                                                                        devSidebarView2 = this.this$0.devSidebar;
                                                                                        if (devSidebarView2 == null) {
                                                                                        }
                                                                                        if (devSidebarView2.getVisibility() == 0) {
                                                                                        }
                                                                                        aetherGaugeView2 = this.this$0.gauge;
                                                                                        if (aetherGaugeView2 == null) {
                                                                                        }
                                                                                        aetherGaugeView2.invalidate();
                                                                                        navCardView3 = this.this$0.navCard;
                                                                                        if (navCardView3 == null) {
                                                                                        }
                                                                                        navCardView3.invalidate();
                                                                                        slowReasonView2 = this.this$0.slowCard;
                                                                                        if (slowReasonView2 == null) {
                                                                                        }
                                                                                        slowReasonView2.invalidate();
                                                                                        laneCardView3 = this.this$0.laneCard;
                                                                                        if (laneCardView3 == null) {
                                                                                        }
                                                                                        laneCardView3.invalidate();
                                                                                        blinkerCardView2 = this.this$0.blinkCard;
                                                                                        if (blinkerCardView2 == null) {
                                                                                        }
                                                                                        blinkerCardView2.invalidate();
                                                                                        gpsCardView2 = this.this$0.gpsCard;
                                                                                        if (gpsCardView2 == null) {
                                                                                        }
                                                                                        if (gpsCardView2.getVisibility() == 0) {
                                                                                        }
                                                                                        nudgeHintView4 = this.this$0.nudgeHint;
                                                                                        if (nudgeHintView4 == null) {
                                                                                        }
                                                                                        nudgeHintView4.invalidate();
                                                                                        weatherView2 = this.this$0.weather;
                                                                                        if (weatherView2 == null) {
                                                                                        }
                                                                                        weatherView2.invalidate();
                                                                                        slcSourcesView2 = this.this$0.slcSources;
                                                                                        if (slcSourcesView2 == null) {
                                                                                        }
                                                                                        slcSourcesView2.invalidate();
                                                                                        linearLayout4 = this.this$0.sidebar;
                                                                                        if (linearLayout4 == null) {
                                                                                        }
                                                                                        if (linearLayout4.getVisibility() == 0) {
                                                                                        }
                                                                                        i21 = this.this$0.tick;
                                                                                        if (i21 % 10 == 0) {
                                                                                        }
                                                                                        this.this$0.applyHud();
                                                                                        GpsBridge gpsBridge2222 = GpsBridge.INSTANCE;
                                                                                        telemetry = this.this$0.telemetry;
                                                                                        String pStr$default3222 = Telemetry.pStr$default(telemetry, "LastGPSPosition", null, 2, null);
                                                                                        telemetry2 = this.this$0.telemetry;
                                                                                        gpsBridge2222.fallback(pStr$default3222, telemetry2.getVEgo());
                                                                                        handler = this.this$0.ui;
                                                                                        handler.postDelayed(this, 50L);
                                                                                        return;
                                                                                    }
                                                                                    i14 = 0;
                                                                                }
                                                                                turnIn = null;
                                                                                laneSuggestView4 = this.this$0.laneSuggest;
                                                                                if (laneSuggestView4 == null) {
                                                                                }
                                                                                laneSuggestView4.tick(lane, turnIn);
                                                                                replayBar = this.this$0.replayBar;
                                                                                if (replayBar == null) {
                                                                                }
                                                                                dataClient5 = this.this$0.data;
                                                                                if (dataClient5 == null) {
                                                                                }
                                                                                replayBar.setVisibility((dataClient5.getConnected() || !Replay.INSTANCE.getActive()) ? 8 : i14);
                                                                                textView18 = this.this$0.whereBtn;
                                                                                if (textView18 == null) {
                                                                                }
                                                                                dataClient6 = this.this$0.data;
                                                                                if (dataClient6 == null) {
                                                                                }
                                                                                if (dataClient6.getConnected()) {
                                                                                    mapOpen2 = this.this$0.getMapOpen();
                                                                                    if (!mapOpen2) {
                                                                                    }
                                                                                }
                                                                                i16 = 8;
                                                                                textView18.setVisibility(i16);
                                                                                textView19 = this.this$0.mapBtn;
                                                                                if (textView19 == null) {
                                                                                }
                                                                                dataClient7 = this.this$0.data;
                                                                                if (dataClient7 == null) {
                                                                                }
                                                                                if (dataClient7.getConnected()) {
                                                                                }
                                                                                i17 = 8;
                                                                                textView19.setVisibility(i17);
                                                                                i18 = this.this$0.tick;
                                                                                if (i18 % 40 == 0) {
                                                                                }
                                                                                BlinkerTurn blinkerTurn2222 = BlinkerTurn.INSTANCE;
                                                                                uiSettings4 = this.this$0.settings;
                                                                                if (uiSettings4 != null) {
                                                                                }
                                                                                int i342222 = i14;
                                                                                BlinkerTurn.tick$default(blinkerTurn2222, t, uiSettings5, 0L, 4, null);
                                                                                this.this$0.ideasTick(t);
                                                                                DriveTrack.tick$default(DriveTrack.INSTANCE, t, 0L, 2, null);
                                                                                place = this.this$0.sharedPending;
                                                                                if (place != null) {
                                                                                }
                                                                                i19 = this.this$0.tick;
                                                                                if (i19 % 20 == 0) {
                                                                                }
                                                                                i20 = this.this$0.tick;
                                                                                if (i20 % 4 == 0) {
                                                                                }
                                                                                textView20 = this.this$0.mapBtn;
                                                                                if (textView20 == null) {
                                                                                }
                                                                                ViewGroup.LayoutParams layoutParams2222 = textView20.getLayoutParams();
                                                                                Intrinsics.checkNotNull(layoutParams2222, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                                                                                lp = (FrameLayout.LayoutParams) layoutParams2222;
                                                                                MainActivity mainActivity82222 = this.this$0;
                                                                                textView21 = mainActivity82222.whereBtn;
                                                                                if (textView21 == null) {
                                                                                }
                                                                                want = mainActivity82222.dp(textView21.getVisibility() != 0 ? 330 : 160);
                                                                                if (lp.getMarginStart() != want) {
                                                                                }
                                                                                Unit unit132222 = Unit.INSTANCE;
                                                                                MainActivity mainActivity92222 = this.this$0;
                                                                                if (t.getParams().get("NavLanePositioningAllowed") == null) {
                                                                                }
                                                                                if (!t.getStarted()) {
                                                                                }
                                                                                r = new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0d, 0.0d, null, 30, null);
                                                                                nudgeHintView = mainActivity92222.nudgeHint;
                                                                                if (nudgeHintView == null) {
                                                                                }
                                                                                nudgeHintView.setResult(r);
                                                                                nudgeHintView2 = mainActivity92222.nudgeHint;
                                                                                if (nudgeHintView2 == null) {
                                                                                }
                                                                                nudgeHintView2.setMetric(t.isMetric());
                                                                                nudgeHintView3 = mainActivity92222.nudgeHint;
                                                                                if (nudgeHintView3 == null) {
                                                                                }
                                                                                nudgeHintView3.setVisibility(r.getState() != NudgeLogic.State.NONE ? 8 : 0);
                                                                                Unit unit142222 = Unit.INSTANCE;
                                                                                weatherView = this.this$0.weather;
                                                                                if (weatherView == null) {
                                                                                }
                                                                                weatherView.setVisibility((t.getStarted() || !t.s("weather") || t.getWeatherId() <= 0) ? 8 : 0);
                                                                                textView22 = this.this$0.compass;
                                                                                if (textView22 == null) {
                                                                                }
                                                                                if (!t.getStarted()) {
                                                                                }
                                                                                textView22.setText(str3);
                                                                                textView23 = this.this$0.devMetrics;
                                                                                if (textView23 == null) {
                                                                                }
                                                                                textView23.setText((t.getStarted() || !t.s("dev_metrics")) ? "" : "CPU " + MathKt.roundToInt(t.getCpuUsage()) + "%  |  TEMP " + MathKt.roundToInt(t.getCpuTempC()) + "°C  |  RAM " + t.getMemUsage() + "%  |  DISK " + MathKt.roundToInt(t.getFreeSpacePct()) + "% free");
                                                                                long nowNs2222 = System.nanoTime();
                                                                                if (t.getStarted()) {
                                                                                }
                                                                                if (t.getStarted()) {
                                                                                }
                                                                                this.this$0.standstillSinceNs = j3;
                                                                                j4 = this.this$0.standstillSinceNs;
                                                                                if (j4 == j3) {
                                                                                }
                                                                                float stoppedS2222 = f2;
                                                                                j5 = this.this$0.onroadSinceNs;
                                                                                if (j5 == j3) {
                                                                                }
                                                                                float onroadS2222 = f3;
                                                                                if (t.s("stopped_timer")) {
                                                                                }
                                                                                textView24 = this.this$0.speedTv;
                                                                                if (textView24 == null) {
                                                                                }
                                                                                z16 = this.this$0.night;
                                                                                textView24.setTextColor(!z16 ? -1 : Color.rgb(29, 29, 31));
                                                                                boolean hideNormal2222 = !t.s("alerts");
                                                                                if (t.getStarted()) {
                                                                                }
                                                                                showAlert = false;
                                                                                MainActivity mainActivity1022222 = this.this$0;
                                                                                f4 = this.this$0.alertAlpha;
                                                                                if (showAlert) {
                                                                                }
                                                                                f5 = this.this$0.alertAlpha;
                                                                                mainActivity1022222.alertAlpha = f4 + ((stall3 - f5) * 0.35f);
                                                                                f6 = this.this$0.alertAlpha;
                                                                                if (f6 > 0.02f) {
                                                                                }
                                                                                ModelSnapshot model22222 = t.getModel();
                                                                                if (model22222 != null) {
                                                                                }
                                                                                j6 = this.this$0.lastModelFrame;
                                                                                if (mf == j6) {
                                                                                }
                                                                                this.this$0.lastModelFrame = mf;
                                                                                overlayView3 = this.this$0.overlay;
                                                                                if (overlayView3 == null) {
                                                                                }
                                                                                overlayView3.invalidate();
                                                                                wheelButton2 = this.this$0.wheel;
                                                                                if (wheelButton2 == null) {
                                                                                }
                                                                                wheelButton2.invalidate();
                                                                                dmView = this.this$0.dm;
                                                                                if (dmView == null) {
                                                                                }
                                                                                dmView.invalidate();
                                                                                miciDistanceView2 = this.this$0.personality;
                                                                                if (miciDistanceView2 == null) {
                                                                                }
                                                                                miciDistanceView2.invalidate();
                                                                                slcView2 = this.this$0.slc;
                                                                                if (slcView2 == null) {
                                                                                }
                                                                                slcView2.invalidate();
                                                                                slcActionsView2 = this.this$0.slcActions;
                                                                                if (slcActionsView2 == null) {
                                                                                }
                                                                                slcActionsView2.invalidate();
                                                                                devSidebarView2 = this.this$0.devSidebar;
                                                                                if (devSidebarView2 == null) {
                                                                                }
                                                                                if (devSidebarView2.getVisibility() == 0) {
                                                                                }
                                                                                aetherGaugeView2 = this.this$0.gauge;
                                                                                if (aetherGaugeView2 == null) {
                                                                                }
                                                                                aetherGaugeView2.invalidate();
                                                                                navCardView3 = this.this$0.navCard;
                                                                                if (navCardView3 == null) {
                                                                                }
                                                                                navCardView3.invalidate();
                                                                                slowReasonView2 = this.this$0.slowCard;
                                                                                if (slowReasonView2 == null) {
                                                                                }
                                                                                slowReasonView2.invalidate();
                                                                                laneCardView3 = this.this$0.laneCard;
                                                                                if (laneCardView3 == null) {
                                                                                }
                                                                                laneCardView3.invalidate();
                                                                                blinkerCardView2 = this.this$0.blinkCard;
                                                                                if (blinkerCardView2 == null) {
                                                                                }
                                                                                blinkerCardView2.invalidate();
                                                                                gpsCardView2 = this.this$0.gpsCard;
                                                                                if (gpsCardView2 == null) {
                                                                                }
                                                                                if (gpsCardView2.getVisibility() == 0) {
                                                                                }
                                                                                nudgeHintView4 = this.this$0.nudgeHint;
                                                                                if (nudgeHintView4 == null) {
                                                                                }
                                                                                nudgeHintView4.invalidate();
                                                                                weatherView2 = this.this$0.weather;
                                                                                if (weatherView2 == null) {
                                                                                }
                                                                                weatherView2.invalidate();
                                                                                slcSourcesView2 = this.this$0.slcSources;
                                                                                if (slcSourcesView2 == null) {
                                                                                }
                                                                                slcSourcesView2.invalidate();
                                                                                linearLayout4 = this.this$0.sidebar;
                                                                                if (linearLayout4 == null) {
                                                                                }
                                                                                if (linearLayout4.getVisibility() == 0) {
                                                                                }
                                                                                i21 = this.this$0.tick;
                                                                                if (i21 % 10 == 0) {
                                                                                }
                                                                                this.this$0.applyHud();
                                                                                GpsBridge gpsBridge22222 = GpsBridge.INSTANCE;
                                                                                telemetry = this.this$0.telemetry;
                                                                                String pStr$default32222 = Telemetry.pStr$default(telemetry, "LastGPSPosition", null, 2, null);
                                                                                telemetry2 = this.this$0.telemetry;
                                                                                gpsBridge22222.fallback(pStr$default32222, telemetry2.getVEgo());
                                                                                handler = this.this$0.ui;
                                                                                handler.postDelayed(this, 50L);
                                                                                return;
                                                                            }
                                                                            i13 = 1065353216;
                                                                            i14 = i12;
                                                                            laneSuggestView7 = this.this$0.laneSuggest;
                                                                            if (laneSuggestView7 == null) {
                                                                                Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                                                                                laneSuggestView7 = null;
                                                                            }
                                                                            if (laneSuggestView7.getVisibility() == 8) {
                                                                                laneSuggestView9 = this.this$0.laneSuggest;
                                                                                if (laneSuggestView9 == null) {
                                                                                    Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                                                                                    laneSuggestView9 = null;
                                                                                }
                                                                            }
                                                                            laneSuggestView8 = this.this$0.laneSuggest;
                                                                            if (laneSuggestView8 == null) {
                                                                                Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                                                                                laneSuggestView8 = null;
                                                                            }
                                                                            laneSuggestView8.stop();
                                                                            replayBar = this.this$0.replayBar;
                                                                            if (replayBar == null) {
                                                                            }
                                                                            dataClient5 = this.this$0.data;
                                                                            if (dataClient5 == null) {
                                                                            }
                                                                            replayBar.setVisibility((dataClient5.getConnected() || !Replay.INSTANCE.getActive()) ? 8 : i14);
                                                                            textView18 = this.this$0.whereBtn;
                                                                            if (textView18 == null) {
                                                                            }
                                                                            dataClient6 = this.this$0.data;
                                                                            if (dataClient6 == null) {
                                                                            }
                                                                            if (dataClient6.getConnected()) {
                                                                            }
                                                                            i16 = 8;
                                                                            textView18.setVisibility(i16);
                                                                            textView19 = this.this$0.mapBtn;
                                                                            if (textView19 == null) {
                                                                            }
                                                                            dataClient7 = this.this$0.data;
                                                                            if (dataClient7 == null) {
                                                                            }
                                                                            if (dataClient7.getConnected()) {
                                                                            }
                                                                            i17 = 8;
                                                                            textView19.setVisibility(i17);
                                                                            i18 = this.this$0.tick;
                                                                            if (i18 % 40 == 0) {
                                                                            }
                                                                            BlinkerTurn blinkerTurn22222 = BlinkerTurn.INSTANCE;
                                                                            uiSettings4 = this.this$0.settings;
                                                                            if (uiSettings4 != null) {
                                                                            }
                                                                            int i3422222 = i14;
                                                                            BlinkerTurn.tick$default(blinkerTurn22222, t, uiSettings5, 0L, 4, null);
                                                                            this.this$0.ideasTick(t);
                                                                            DriveTrack.tick$default(DriveTrack.INSTANCE, t, 0L, 2, null);
                                                                            place = this.this$0.sharedPending;
                                                                            if (place != null) {
                                                                            }
                                                                            i19 = this.this$0.tick;
                                                                            if (i19 % 20 == 0) {
                                                                            }
                                                                            i20 = this.this$0.tick;
                                                                            if (i20 % 4 == 0) {
                                                                            }
                                                                            textView20 = this.this$0.mapBtn;
                                                                            if (textView20 == null) {
                                                                            }
                                                                            ViewGroup.LayoutParams layoutParams22222 = textView20.getLayoutParams();
                                                                            Intrinsics.checkNotNull(layoutParams22222, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                                                                            lp = (FrameLayout.LayoutParams) layoutParams22222;
                                                                            MainActivity mainActivity822222 = this.this$0;
                                                                            textView21 = mainActivity822222.whereBtn;
                                                                            if (textView21 == null) {
                                                                            }
                                                                            want = mainActivity822222.dp(textView21.getVisibility() != 0 ? 330 : 160);
                                                                            if (lp.getMarginStart() != want) {
                                                                            }
                                                                            Unit unit1322222 = Unit.INSTANCE;
                                                                            MainActivity mainActivity922222 = this.this$0;
                                                                            if (t.getParams().get("NavLanePositioningAllowed") == null) {
                                                                            }
                                                                            if (!t.getStarted()) {
                                                                            }
                                                                            r = new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0d, 0.0d, null, 30, null);
                                                                            nudgeHintView = mainActivity922222.nudgeHint;
                                                                            if (nudgeHintView == null) {
                                                                            }
                                                                            nudgeHintView.setResult(r);
                                                                            nudgeHintView2 = mainActivity922222.nudgeHint;
                                                                            if (nudgeHintView2 == null) {
                                                                            }
                                                                            nudgeHintView2.setMetric(t.isMetric());
                                                                            nudgeHintView3 = mainActivity922222.nudgeHint;
                                                                            if (nudgeHintView3 == null) {
                                                                            }
                                                                            nudgeHintView3.setVisibility(r.getState() != NudgeLogic.State.NONE ? 8 : 0);
                                                                            Unit unit1422222 = Unit.INSTANCE;
                                                                            weatherView = this.this$0.weather;
                                                                            if (weatherView == null) {
                                                                            }
                                                                            weatherView.setVisibility((t.getStarted() || !t.s("weather") || t.getWeatherId() <= 0) ? 8 : 0);
                                                                            textView22 = this.this$0.compass;
                                                                            if (textView22 == null) {
                                                                            }
                                                                            if (!t.getStarted()) {
                                                                            }
                                                                            textView22.setText(str3);
                                                                            textView23 = this.this$0.devMetrics;
                                                                            if (textView23 == null) {
                                                                            }
                                                                            textView23.setText((t.getStarted() || !t.s("dev_metrics")) ? "" : "CPU " + MathKt.roundToInt(t.getCpuUsage()) + "%  |  TEMP " + MathKt.roundToInt(t.getCpuTempC()) + "°C  |  RAM " + t.getMemUsage() + "%  |  DISK " + MathKt.roundToInt(t.getFreeSpacePct()) + "% free");
                                                                            long nowNs22222 = System.nanoTime();
                                                                            if (t.getStarted()) {
                                                                            }
                                                                            if (t.getStarted()) {
                                                                            }
                                                                            this.this$0.standstillSinceNs = j3;
                                                                            j4 = this.this$0.standstillSinceNs;
                                                                            if (j4 == j3) {
                                                                            }
                                                                            float stoppedS22222 = f2;
                                                                            j5 = this.this$0.onroadSinceNs;
                                                                            if (j5 == j3) {
                                                                            }
                                                                            float onroadS22222 = f3;
                                                                            if (t.s("stopped_timer")) {
                                                                            }
                                                                            textView24 = this.this$0.speedTv;
                                                                            if (textView24 == null) {
                                                                            }
                                                                            z16 = this.this$0.night;
                                                                            textView24.setTextColor(!z16 ? -1 : Color.rgb(29, 29, 31));
                                                                            boolean hideNormal22222 = !t.s("alerts");
                                                                            if (t.getStarted()) {
                                                                            }
                                                                            showAlert = false;
                                                                            MainActivity mainActivity10222222 = this.this$0;
                                                                            f4 = this.this$0.alertAlpha;
                                                                            if (showAlert) {
                                                                            }
                                                                            f5 = this.this$0.alertAlpha;
                                                                            mainActivity10222222.alertAlpha = f4 + ((stall3 - f5) * 0.35f);
                                                                            f6 = this.this$0.alertAlpha;
                                                                            if (f6 > 0.02f) {
                                                                            }
                                                                            ModelSnapshot model222222 = t.getModel();
                                                                            if (model222222 != null) {
                                                                            }
                                                                            j6 = this.this$0.lastModelFrame;
                                                                            if (mf == j6) {
                                                                            }
                                                                            this.this$0.lastModelFrame = mf;
                                                                            overlayView3 = this.this$0.overlay;
                                                                            if (overlayView3 == null) {
                                                                            }
                                                                            overlayView3.invalidate();
                                                                            wheelButton2 = this.this$0.wheel;
                                                                            if (wheelButton2 == null) {
                                                                            }
                                                                            wheelButton2.invalidate();
                                                                            dmView = this.this$0.dm;
                                                                            if (dmView == null) {
                                                                            }
                                                                            dmView.invalidate();
                                                                            miciDistanceView2 = this.this$0.personality;
                                                                            if (miciDistanceView2 == null) {
                                                                            }
                                                                            miciDistanceView2.invalidate();
                                                                            slcView2 = this.this$0.slc;
                                                                            if (slcView2 == null) {
                                                                            }
                                                                            slcView2.invalidate();
                                                                            slcActionsView2 = this.this$0.slcActions;
                                                                            if (slcActionsView2 == null) {
                                                                            }
                                                                            slcActionsView2.invalidate();
                                                                            devSidebarView2 = this.this$0.devSidebar;
                                                                            if (devSidebarView2 == null) {
                                                                            }
                                                                            if (devSidebarView2.getVisibility() == 0) {
                                                                            }
                                                                            aetherGaugeView2 = this.this$0.gauge;
                                                                            if (aetherGaugeView2 == null) {
                                                                            }
                                                                            aetherGaugeView2.invalidate();
                                                                            navCardView3 = this.this$0.navCard;
                                                                            if (navCardView3 == null) {
                                                                            }
                                                                            navCardView3.invalidate();
                                                                            slowReasonView2 = this.this$0.slowCard;
                                                                            if (slowReasonView2 == null) {
                                                                            }
                                                                            slowReasonView2.invalidate();
                                                                            laneCardView3 = this.this$0.laneCard;
                                                                            if (laneCardView3 == null) {
                                                                            }
                                                                            laneCardView3.invalidate();
                                                                            blinkerCardView2 = this.this$0.blinkCard;
                                                                            if (blinkerCardView2 == null) {
                                                                            }
                                                                            blinkerCardView2.invalidate();
                                                                            gpsCardView2 = this.this$0.gpsCard;
                                                                            if (gpsCardView2 == null) {
                                                                            }
                                                                            if (gpsCardView2.getVisibility() == 0) {
                                                                            }
                                                                            nudgeHintView4 = this.this$0.nudgeHint;
                                                                            if (nudgeHintView4 == null) {
                                                                            }
                                                                            nudgeHintView4.invalidate();
                                                                            weatherView2 = this.this$0.weather;
                                                                            if (weatherView2 == null) {
                                                                            }
                                                                            weatherView2.invalidate();
                                                                            slcSourcesView2 = this.this$0.slcSources;
                                                                            if (slcSourcesView2 == null) {
                                                                            }
                                                                            slcSourcesView2.invalidate();
                                                                            linearLayout4 = this.this$0.sidebar;
                                                                            if (linearLayout4 == null) {
                                                                            }
                                                                            if (linearLayout4.getVisibility() == 0) {
                                                                            }
                                                                            i21 = this.this$0.tick;
                                                                            if (i21 % 10 == 0) {
                                                                            }
                                                                            this.this$0.applyHud();
                                                                            GpsBridge gpsBridge222222 = GpsBridge.INSTANCE;
                                                                            telemetry = this.this$0.telemetry;
                                                                            String pStr$default322222 = Telemetry.pStr$default(telemetry, "LastGPSPosition", null, 2, null);
                                                                            telemetry2 = this.this$0.telemetry;
                                                                            gpsBridge222222.fallback(pStr$default322222, telemetry2.getVEgo());
                                                                            handler = this.this$0.ui;
                                                                            handler.postDelayed(this, 50L);
                                                                            return;
                                                                        }
                                                                        z13 = 1;
                                                                        active = Replay.INSTANCE.getActive();
                                                                        z14 = this.this$0.replayWas;
                                                                        if (active != z14) {
                                                                        }
                                                                        if (!t.getStarted()) {
                                                                        }
                                                                        dataClient4 = this.this$0.data;
                                                                        if (dataClient4 == null) {
                                                                        }
                                                                        if (!dataClient4.getConnected()) {
                                                                        }
                                                                    }
                                                                    z12 = 1;
                                                                    if (!t.getRightBlindspot()) {
                                                                    }
                                                                    z13 = 1;
                                                                    active = Replay.INSTANCE.getActive();
                                                                    z14 = this.this$0.replayWas;
                                                                    if (active != z14) {
                                                                    }
                                                                    if (!t.getStarted()) {
                                                                    }
                                                                    dataClient4 = this.this$0.data;
                                                                    if (dataClient4 == null) {
                                                                    }
                                                                    if (!dataClient4.getConnected()) {
                                                                    }
                                                                }
                                                            }
                                                            i10 = 8;
                                                            slowReasonView.setVisibility(i10);
                                                            if (t.getStarted()) {
                                                            }
                                                            if (t.getStarted()) {
                                                                detector3 = this.this$0.detectorRef;
                                                                MainActivity mainActivity52 = this.this$0;
                                                                if (System.currentTimeMillis() - a.getAtMs() < 5000) {
                                                                }
                                                                Unit unit92 = Unit.INSTANCE;
                                                                Unit unit102 = Unit.INSTANCE;
                                                            }
                                                            s = this.this$0.scene;
                                                            if (s != null) {
                                                            }
                                                            if (t.getStarted()) {
                                                            }
                                                            this.this$0.laneWasStarted = t.getStarted();
                                                            laneCardView = this.this$0.laneCard;
                                                            if (laneCardView == null) {
                                                            }
                                                            laneCardView.setVisibility((t.getStarted() || !t.s("lane_card")) ? 8 : i11);
                                                            laneShots = this.this$0.getLaneShots();
                                                            detector = this.this$0.detectorRef;
                                                            laneShots.setDetector(detector);
                                                            laneCardView2 = this.this$0.laneCard;
                                                            if (laneCardView2 == null) {
                                                            }
                                                            if (laneCardView2.getVisibility() == 0) {
                                                                laneShots2 = this.this$0.getLaneShots();
                                                                final MainActivity mainActivity72 = this.this$0;
                                                                laneShots2.tick(new Function0() { // from class: com.starpilot.starview.MainActivity$uiTick$1$$ExternalSyntheticLambda0
                                                                    @Override // kotlin.jvm.functions.Function0
                                                                    public final Object invoke() {
                                                                        String run$lambda$5;
                                                                        run$lambda$5 = MainActivity$uiTick$1.run$lambda$5(mainActivity72, t);
                                                                        return run$lambda$5;
                                                                    }
                                                                });
                                                            }
                                                            blinkerCardView = this.this$0.blinkCard;
                                                            if (blinkerCardView == null) {
                                                            }
                                                            dataClient2 = this.this$0.data;
                                                            if (dataClient2 == null) {
                                                            }
                                                            blinkerCardView.setVisibility((dataClient2.getConnected() || !t.s("blink_card")) ? 8 : i11);
                                                            gpsCardView = this.this$0.gpsCard;
                                                            if (gpsCardView == null) {
                                                            }
                                                            dataClient3 = this.this$0.data;
                                                            if (dataClient3 == null) {
                                                            }
                                                            gpsCardView.setVisibility((dataClient3.getConnected() || !t.s("gps_card")) ? 8 : i11);
                                                            boolean laneOn2 = t.s("lane_suggest");
                                                            boolean turnOn2 = t.s("turn_signals");
                                                            if (t.s("car_memory")) {
                                                            }
                                                            if (!t.getLeftBlindspot()) {
                                                            }
                                                            z12 = 1;
                                                            if (!t.getRightBlindspot()) {
                                                            }
                                                            z13 = 1;
                                                            active = Replay.INSTANCE.getActive();
                                                            z14 = this.this$0.replayWas;
                                                            if (active != z14) {
                                                            }
                                                            if (!t.getStarted()) {
                                                            }
                                                            dataClient4 = this.this$0.data;
                                                            if (dataClient4 == null) {
                                                            }
                                                            if (!dataClient4.getConnected()) {
                                                            }
                                                        }
                                                    }
                                                    i9 = 8;
                                                    navCardView.setVisibility(i9);
                                                    navCardView2 = this.this$0.navCard;
                                                    if (navCardView2 == null) {
                                                    }
                                                    navCardView2.setAlpha(!(card == null && ((Boolean) card.getSecond()).booleanValue()) ? 0.55f : 1.0f);
                                                    slowReasonView = this.this$0.slowCard;
                                                    if (slowReasonView == null) {
                                                    }
                                                    if (t.getStarted()) {
                                                        slowReasonView3 = this.this$0.slowCard;
                                                        if (slowReasonView3 == null) {
                                                        }
                                                        if (slowReasonView3.current() != null) {
                                                        }
                                                    }
                                                    i10 = 8;
                                                    slowReasonView.setVisibility(i10);
                                                    if (t.getStarted()) {
                                                    }
                                                    if (t.getStarted()) {
                                                    }
                                                    s = this.this$0.scene;
                                                    if (s != null) {
                                                    }
                                                    if (t.getStarted()) {
                                                    }
                                                    this.this$0.laneWasStarted = t.getStarted();
                                                    laneCardView = this.this$0.laneCard;
                                                    if (laneCardView == null) {
                                                    }
                                                    laneCardView.setVisibility((t.getStarted() || !t.s("lane_card")) ? 8 : i11);
                                                    laneShots = this.this$0.getLaneShots();
                                                    detector = this.this$0.detectorRef;
                                                    laneShots.setDetector(detector);
                                                    laneCardView2 = this.this$0.laneCard;
                                                    if (laneCardView2 == null) {
                                                    }
                                                    if (laneCardView2.getVisibility() == 0) {
                                                    }
                                                    blinkerCardView = this.this$0.blinkCard;
                                                    if (blinkerCardView == null) {
                                                    }
                                                    dataClient2 = this.this$0.data;
                                                    if (dataClient2 == null) {
                                                    }
                                                    blinkerCardView.setVisibility((dataClient2.getConnected() || !t.s("blink_card")) ? 8 : i11);
                                                    gpsCardView = this.this$0.gpsCard;
                                                    if (gpsCardView == null) {
                                                    }
                                                    dataClient3 = this.this$0.data;
                                                    if (dataClient3 == null) {
                                                    }
                                                    gpsCardView.setVisibility((dataClient3.getConnected() || !t.s("gps_card")) ? 8 : i11);
                                                    boolean laneOn22 = t.s("lane_suggest");
                                                    boolean turnOn22 = t.s("turn_signals");
                                                    if (t.s("car_memory")) {
                                                    }
                                                    if (!t.getLeftBlindspot()) {
                                                    }
                                                    z12 = 1;
                                                    if (!t.getRightBlindspot()) {
                                                    }
                                                    z13 = 1;
                                                    active = Replay.INSTANCE.getActive();
                                                    z14 = this.this$0.replayWas;
                                                    if (active != z14) {
                                                    }
                                                    if (!t.getStarted()) {
                                                    }
                                                    dataClient4 = this.this$0.data;
                                                    if (dataClient4 == null) {
                                                    }
                                                    if (!dataClient4.getConnected()) {
                                                    }
                                                }
                                            }
                                            i8 = 8;
                                            slcSourcesView.setVisibility(i8);
                                            Pair card2 = Telemetry.navCard$default(t, 0L, 1, null);
                                            navCardView = this.this$0.navCard;
                                            if (navCardView == null) {
                                            }
                                            if (t.getStarted()) {
                                                mapOpen3 = this.this$0.getMapOpen();
                                                if (!mapOpen3) {
                                                }
                                            }
                                            i9 = 8;
                                            navCardView.setVisibility(i9);
                                            navCardView2 = this.this$0.navCard;
                                            if (navCardView2 == null) {
                                            }
                                            navCardView2.setAlpha(!(card2 == null && ((Boolean) card2.getSecond()).booleanValue()) ? 0.55f : 1.0f);
                                            slowReasonView = this.this$0.slowCard;
                                            if (slowReasonView == null) {
                                            }
                                            if (t.getStarted()) {
                                            }
                                            i10 = 8;
                                            slowReasonView.setVisibility(i10);
                                            if (t.getStarted()) {
                                            }
                                            if (t.getStarted()) {
                                            }
                                            s = this.this$0.scene;
                                            if (s != null) {
                                            }
                                            if (t.getStarted()) {
                                            }
                                            this.this$0.laneWasStarted = t.getStarted();
                                            laneCardView = this.this$0.laneCard;
                                            if (laneCardView == null) {
                                            }
                                            laneCardView.setVisibility((t.getStarted() || !t.s("lane_card")) ? 8 : i11);
                                            laneShots = this.this$0.getLaneShots();
                                            detector = this.this$0.detectorRef;
                                            laneShots.setDetector(detector);
                                            laneCardView2 = this.this$0.laneCard;
                                            if (laneCardView2 == null) {
                                            }
                                            if (laneCardView2.getVisibility() == 0) {
                                            }
                                            blinkerCardView = this.this$0.blinkCard;
                                            if (blinkerCardView == null) {
                                            }
                                            dataClient2 = this.this$0.data;
                                            if (dataClient2 == null) {
                                            }
                                            blinkerCardView.setVisibility((dataClient2.getConnected() || !t.s("blink_card")) ? 8 : i11);
                                            gpsCardView = this.this$0.gpsCard;
                                            if (gpsCardView == null) {
                                            }
                                            dataClient3 = this.this$0.data;
                                            if (dataClient3 == null) {
                                            }
                                            gpsCardView.setVisibility((dataClient3.getConnected() || !t.s("gps_card")) ? 8 : i11);
                                            boolean laneOn222 = t.s("lane_suggest");
                                            boolean turnOn222 = t.s("turn_signals");
                                            if (t.s("car_memory")) {
                                            }
                                            if (!t.getLeftBlindspot()) {
                                            }
                                            z12 = 1;
                                            if (!t.getRightBlindspot()) {
                                            }
                                            z13 = 1;
                                            active = Replay.INSTANCE.getActive();
                                            z14 = this.this$0.replayWas;
                                            if (active != z14) {
                                            }
                                            if (!t.getStarted()) {
                                            }
                                            dataClient4 = this.this$0.data;
                                            if (dataClient4 == null) {
                                            }
                                            if (!dataClient4.getConnected()) {
                                            }
                                        }
                                    }
                                    i5 = 8;
                                    slcActionsView.setVisibility(i5);
                                    if (t.getStarted()) {
                                    }
                                    i6 = i4;
                                    pair = null;
                                    cl = pair;
                                    textView11 = this.this$0.curveLabel;
                                    if (textView11 == null) {
                                    }
                                    textView11.setText((cl != null || (str15 = (String) cl.getFirst()) == null) ? "" : str15);
                                    if (cl != null) {
                                    }
                                    textView12 = this.this$0.curveLabel;
                                    if (textView12 == null) {
                                    }
                                    textView12.setVisibility(cl == null ? 0 : 8);
                                    boolean noAlert2 = Intrinsics.areEqual(t.getAlertSize(), "none");
                                    textView13 = this.this$0.clockTv;
                                    if (textView13 == null) {
                                    }
                                    textView13.setVisibility((!t.getStarted() && t.s("clock") && noAlert2) ? 0 : 8);
                                    textView14 = this.this$0.clockTv;
                                    if (textView14 == null) {
                                    }
                                    if (textView14.getVisibility() == 0) {
                                    }
                                    textView15 = this.this$0.modeText;
                                    if (textView15 == null) {
                                    }
                                    textView15.setVisibility((!t.getStarted() && t.s("mode_text") && noAlert2) ? 0 : 8);
                                    textView16 = this.this$0.modeText;
                                    if (textView16 == null) {
                                    }
                                    if (textView16.getVisibility() == 0) {
                                    }
                                    devSidebarView = this.this$0.devSidebar;
                                    if (devSidebarView == null) {
                                    }
                                    devSidebarView.setVisibility((t.getStarted() || !t.s("dev_sidebar")) ? 8 : 0);
                                    SideCam sideCam2 = SideCam.INSTANCE;
                                    str2 = this.this$0.host;
                                    i7 = this.this$0.port;
                                    int stall32 = 0;
                                    SideCam.tick$default(sideCam2, t, str2, i7, 0L, 8, null);
                                    List sides2 = SideCam.INSTANCE.wanted(t);
                                    sideView = this.this$0.pipLeft;
                                    if (sideView == null) {
                                    }
                                    sideView.setVisibility(!sides2.contains("left") ? 0 : 8);
                                    sideView2 = this.this$0.pipRight;
                                    if (sideView2 == null) {
                                    }
                                    sideView2.setVisibility(!sides2.contains("right") ? 0 : 8);
                                    cnState = Telemetry.pStr$default(t, "ChestnutState", null, i6, null);
                                    if (cnState.length() <= 0) {
                                    }
                                    textView17 = this.this$0.roadName;
                                    if (textView17 == null) {
                                    }
                                    textView17.setText((t.getStarted() || !t.s("road_name")) ? "" : t.getRoadName());
                                    aetherGaugeView = this.this$0.gauge;
                                    if (aetherGaugeView == null) {
                                    }
                                    aetherGaugeView.setVisibility((t.getStarted() || !t.s("aether_gauge")) ? 8 : 0);
                                    slcSourcesView = this.this$0.slcSources;
                                    if (slcSourcesView == null) {
                                    }
                                    if (t.getStarted()) {
                                        if (!t.getSp7() ? t.s("slc_sources_sp7") : t.pBool("SpeedLimitSources", false)) {
                                        }
                                    }
                                    i8 = 8;
                                    slcSourcesView.setVisibility(i8);
                                    Pair card22 = Telemetry.navCard$default(t, 0L, 1, null);
                                    navCardView = this.this$0.navCard;
                                    if (navCardView == null) {
                                    }
                                    if (t.getStarted()) {
                                    }
                                    i9 = 8;
                                    navCardView.setVisibility(i9);
                                    navCardView2 = this.this$0.navCard;
                                    if (navCardView2 == null) {
                                    }
                                    navCardView2.setAlpha(!(card22 == null && ((Boolean) card22.getSecond()).booleanValue()) ? 0.55f : 1.0f);
                                    slowReasonView = this.this$0.slowCard;
                                    if (slowReasonView == null) {
                                    }
                                    if (t.getStarted()) {
                                    }
                                    i10 = 8;
                                    slowReasonView.setVisibility(i10);
                                    if (t.getStarted()) {
                                    }
                                    if (t.getStarted()) {
                                    }
                                    s = this.this$0.scene;
                                    if (s != null) {
                                    }
                                    if (t.getStarted()) {
                                    }
                                    this.this$0.laneWasStarted = t.getStarted();
                                    laneCardView = this.this$0.laneCard;
                                    if (laneCardView == null) {
                                    }
                                    laneCardView.setVisibility((t.getStarted() || !t.s("lane_card")) ? 8 : i11);
                                    laneShots = this.this$0.getLaneShots();
                                    detector = this.this$0.detectorRef;
                                    laneShots.setDetector(detector);
                                    laneCardView2 = this.this$0.laneCard;
                                    if (laneCardView2 == null) {
                                    }
                                    if (laneCardView2.getVisibility() == 0) {
                                    }
                                    blinkerCardView = this.this$0.blinkCard;
                                    if (blinkerCardView == null) {
                                    }
                                    dataClient2 = this.this$0.data;
                                    if (dataClient2 == null) {
                                    }
                                    blinkerCardView.setVisibility((dataClient2.getConnected() || !t.s("blink_card")) ? 8 : i11);
                                    gpsCardView = this.this$0.gpsCard;
                                    if (gpsCardView == null) {
                                    }
                                    dataClient3 = this.this$0.data;
                                    if (dataClient3 == null) {
                                    }
                                    gpsCardView.setVisibility((dataClient3.getConnected() || !t.s("gps_card")) ? 8 : i11);
                                    boolean laneOn2222 = t.s("lane_suggest");
                                    boolean turnOn2222 = t.s("turn_signals");
                                    if (t.s("car_memory")) {
                                    }
                                    if (!t.getLeftBlindspot()) {
                                    }
                                    z12 = 1;
                                    if (!t.getRightBlindspot()) {
                                    }
                                    z13 = 1;
                                    active = Replay.INSTANCE.getActive();
                                    z14 = this.this$0.replayWas;
                                    if (active != z14) {
                                    }
                                    if (!t.getStarted()) {
                                    }
                                    dataClient4 = this.this$0.data;
                                    if (dataClient4 == null) {
                                    }
                                    if (!dataClient4.getConnected()) {
                                    }
                                }
                                z6 = false;
                                mainActivity2.syncDetector(z6);
                                OsmRoads osmRoads2 = OsmRoads.INSTANCE;
                                uiSettings2 = this.this$0.settings;
                                if (uiSettings2 == null) {
                                }
                                osmRoads2.setEnabled(uiSettings2.get("map_roads"));
                                overlayView = this.this$0.overlay;
                                if (overlayView == null) {
                                }
                                if (overlayView.updateCalibration()) {
                                }
                                overlayView2 = this.this$0.overlay;
                                if (overlayView2 == null) {
                                }
                                overlayView2.setUiStatus(UiStatus.Companion.from(t));
                                currentTimeMillis = System.currentTimeMillis();
                                j = this.this$0.lastThemeCheck;
                                if (currentTimeMillis - j > 15000) {
                                }
                                boolean metric2 = t.isMetric();
                                float conv2 = metric2 ? 3.6f : 2.23694f;
                                boolean useWheel2 = t.tBool("use_wheel_speed", false);
                                float v2 = (!useWheel2 || t.getVEgoCluster() <= 0.0f) ? t.getVEgo() : t.getVEgoCluster();
                                textView3 = this.this$0.speedTv;
                                if (textView3 == null) {
                                }
                                textView3.setText(String.valueOf(RangesKt.coerceAtLeast(MathKt.roundToInt(v2 * conv2), 0)));
                                textView4 = this.this$0.speedUnit;
                                if (textView4 == null) {
                                }
                                textView4.setText(metric2 ? "km/h" : "mph");
                                boolean hideSpeed2 = t.s("show_speed") || !t.getStarted();
                                textView5 = this.this$0.speedTv;
                                if (textView5 == null) {
                                }
                                textView5.setVisibility(hideSpeed2 ? 4 : 0);
                                textView6 = this.this$0.speedUnit;
                                if (textView6 == null) {
                                }
                                textView7 = this.this$0.speedTv;
                                if (textView7 == null) {
                                }
                                textView6.setVisibility(textView7.getVisibility());
                                float setMs2 = t.getVCruiseCluster() > 0.0f ? t.getVCruiseCluster() : t.getVCruise();
                                isSet = !t.getCruiseEnabled() && setMs2 > 0.0f && setMs2 < 200.0f;
                                float offset2 = t.tFloat("set_speed_offset", 0.0f);
                                textView8 = this.this$0.setSpeedTv;
                                if (textView8 == null) {
                                }
                                if (isSet) {
                                }
                                textView8.setText(str);
                                textView9 = this.this$0.setSpeedTv;
                                if (textView9 == null) {
                                }
                                textView9.setTextColor(isSet ? -1 : Color.rgb(114, 114, 114));
                                textView10 = this.this$0.maxLabel;
                                if (textView10 == null) {
                                }
                                textView10.setTextColor(!isSet ? Color.rgb(166, 166, 166) : (t.getEnabled() && t.getActive()) ? Color.rgb(128, 216, 166) : Color.rgb(145, 155, 149));
                                linearLayout2 = this.this$0.maxCard;
                                if (linearLayout2 == null) {
                                }
                                linearLayout2.setVisibility((t.s("show_max") || !t.getStarted()) ? 4 : 0);
                                slcView = this.this$0.slc;
                                if (slcView == null) {
                                }
                                slcView.setVisibility((t.getStarted() || !t.s("speed_limit")) ? 4 : 0);
                                wheelButton = this.this$0.wheel;
                                if (wheelButton == null) {
                                }
                                wheelButton.setVisibility((t.s("wheel_button") || !t.getStarted()) ? 4 : 0);
                                miciDistanceView = this.this$0.personality;
                                if (miciDistanceView == null) {
                                }
                                miciDistanceView.setVisibility((t.getStarted() || !t.s("personality_button")) ? 8 : 0);
                                slcActionsView = this.this$0.slcActions;
                                if (slcActionsView == null) {
                                }
                                if (t.getStarted()) {
                                }
                                j2 = 0;
                                i5 = 8;
                                slcActionsView.setVisibility(i5);
                                if (t.getStarted()) {
                                }
                                i6 = i4;
                                pair = null;
                                cl = pair;
                                textView11 = this.this$0.curveLabel;
                                if (textView11 == null) {
                                }
                                textView11.setText((cl != null || (str15 = (String) cl.getFirst()) == null) ? "" : str15);
                                if (cl != null) {
                                }
                                textView12 = this.this$0.curveLabel;
                                if (textView12 == null) {
                                }
                                textView12.setVisibility(cl == null ? 0 : 8);
                                boolean noAlert22 = Intrinsics.areEqual(t.getAlertSize(), "none");
                                textView13 = this.this$0.clockTv;
                                if (textView13 == null) {
                                }
                                textView13.setVisibility((!t.getStarted() && t.s("clock") && noAlert22) ? 0 : 8);
                                textView14 = this.this$0.clockTv;
                                if (textView14 == null) {
                                }
                                if (textView14.getVisibility() == 0) {
                                }
                                textView15 = this.this$0.modeText;
                                if (textView15 == null) {
                                }
                                textView15.setVisibility((!t.getStarted() && t.s("mode_text") && noAlert22) ? 0 : 8);
                                textView16 = this.this$0.modeText;
                                if (textView16 == null) {
                                }
                                if (textView16.getVisibility() == 0) {
                                }
                                devSidebarView = this.this$0.devSidebar;
                                if (devSidebarView == null) {
                                }
                                devSidebarView.setVisibility((t.getStarted() || !t.s("dev_sidebar")) ? 8 : 0);
                                SideCam sideCam22 = SideCam.INSTANCE;
                                str2 = this.this$0.host;
                                i7 = this.this$0.port;
                                int stall322 = 0;
                                SideCam.tick$default(sideCam22, t, str2, i7, 0L, 8, null);
                                List sides22 = SideCam.INSTANCE.wanted(t);
                                sideView = this.this$0.pipLeft;
                                if (sideView == null) {
                                }
                                sideView.setVisibility(!sides22.contains("left") ? 0 : 8);
                                sideView2 = this.this$0.pipRight;
                                if (sideView2 == null) {
                                }
                                sideView2.setVisibility(!sides22.contains("right") ? 0 : 8);
                                cnState = Telemetry.pStr$default(t, "ChestnutState", null, i6, null);
                                if (cnState.length() <= 0) {
                                }
                                textView17 = this.this$0.roadName;
                                if (textView17 == null) {
                                }
                                textView17.setText((t.getStarted() || !t.s("road_name")) ? "" : t.getRoadName());
                                aetherGaugeView = this.this$0.gauge;
                                if (aetherGaugeView == null) {
                                }
                                aetherGaugeView.setVisibility((t.getStarted() || !t.s("aether_gauge")) ? 8 : 0);
                                slcSourcesView = this.this$0.slcSources;
                                if (slcSourcesView == null) {
                                }
                                if (t.getStarted()) {
                                }
                                i8 = 8;
                                slcSourcesView.setVisibility(i8);
                                Pair card222 = Telemetry.navCard$default(t, 0L, 1, null);
                                navCardView = this.this$0.navCard;
                                if (navCardView == null) {
                                }
                                if (t.getStarted()) {
                                }
                                i9 = 8;
                                navCardView.setVisibility(i9);
                                navCardView2 = this.this$0.navCard;
                                if (navCardView2 == null) {
                                }
                                navCardView2.setAlpha(!(card222 == null && ((Boolean) card222.getSecond()).booleanValue()) ? 0.55f : 1.0f);
                                slowReasonView = this.this$0.slowCard;
                                if (slowReasonView == null) {
                                }
                                if (t.getStarted()) {
                                }
                                i10 = 8;
                                slowReasonView.setVisibility(i10);
                                if (t.getStarted()) {
                                }
                                if (t.getStarted()) {
                                }
                                s = this.this$0.scene;
                                if (s != null) {
                                }
                                if (t.getStarted()) {
                                }
                                this.this$0.laneWasStarted = t.getStarted();
                                laneCardView = this.this$0.laneCard;
                                if (laneCardView == null) {
                                }
                                laneCardView.setVisibility((t.getStarted() || !t.s("lane_card")) ? 8 : i11);
                                laneShots = this.this$0.getLaneShots();
                                detector = this.this$0.detectorRef;
                                laneShots.setDetector(detector);
                                laneCardView2 = this.this$0.laneCard;
                                if (laneCardView2 == null) {
                                }
                                if (laneCardView2.getVisibility() == 0) {
                                }
                                blinkerCardView = this.this$0.blinkCard;
                                if (blinkerCardView == null) {
                                }
                                dataClient2 = this.this$0.data;
                                if (dataClient2 == null) {
                                }
                                blinkerCardView.setVisibility((dataClient2.getConnected() || !t.s("blink_card")) ? 8 : i11);
                                gpsCardView = this.this$0.gpsCard;
                                if (gpsCardView == null) {
                                }
                                dataClient3 = this.this$0.data;
                                if (dataClient3 == null) {
                                }
                                gpsCardView.setVisibility((dataClient3.getConnected() || !t.s("gps_card")) ? 8 : i11);
                                boolean laneOn22222 = t.s("lane_suggest");
                                boolean turnOn22222 = t.s("turn_signals");
                                if (t.s("car_memory")) {
                                }
                                if (!t.getLeftBlindspot()) {
                                }
                                z12 = 1;
                                if (!t.getRightBlindspot()) {
                                }
                                z13 = 1;
                                active = Replay.INSTANCE.getActive();
                                z14 = this.this$0.replayWas;
                                if (active != z14) {
                                }
                                if (!t.getStarted()) {
                                }
                                dataClient4 = this.this$0.data;
                                if (dataClient4 == null) {
                                }
                                if (!dataClient4.getConnected()) {
                                }
                            }
                        }
                    }
                    z4 = false;
                    wantVideo = z4;
                    if (wantVideo) {
                    }
                    if (!wantVideo) {
                    }
                    MainActivity mainActivity22 = this.this$0;
                    z5 = this.this$0.visible;
                    if (z5) {
                        uiSettings8 = this.this$0.settings;
                        if (uiSettings8 == null) {
                        }
                        if (!uiSettings8.get("detector_test")) {
                        }
                        z6 = true;
                        mainActivity22.syncDetector(z6);
                        OsmRoads osmRoads22 = OsmRoads.INSTANCE;
                        uiSettings2 = this.this$0.settings;
                        if (uiSettings2 == null) {
                        }
                        osmRoads22.setEnabled(uiSettings2.get("map_roads"));
                        overlayView = this.this$0.overlay;
                        if (overlayView == null) {
                        }
                        if (overlayView.updateCalibration()) {
                        }
                        overlayView2 = this.this$0.overlay;
                        if (overlayView2 == null) {
                        }
                        overlayView2.setUiStatus(UiStatus.Companion.from(t));
                        currentTimeMillis = System.currentTimeMillis();
                        j = this.this$0.lastThemeCheck;
                        if (currentTimeMillis - j > 15000) {
                        }
                        boolean metric22 = t.isMetric();
                        float conv22 = metric22 ? 3.6f : 2.23694f;
                        boolean useWheel22 = t.tBool("use_wheel_speed", false);
                        float v22 = (!useWheel22 || t.getVEgoCluster() <= 0.0f) ? t.getVEgo() : t.getVEgoCluster();
                        textView3 = this.this$0.speedTv;
                        if (textView3 == null) {
                        }
                        textView3.setText(String.valueOf(RangesKt.coerceAtLeast(MathKt.roundToInt(v22 * conv22), 0)));
                        textView4 = this.this$0.speedUnit;
                        if (textView4 == null) {
                        }
                        textView4.setText(metric22 ? "km/h" : "mph");
                        boolean hideSpeed22 = t.s("show_speed") || !t.getStarted();
                        textView5 = this.this$0.speedTv;
                        if (textView5 == null) {
                        }
                        textView5.setVisibility(hideSpeed22 ? 4 : 0);
                        textView6 = this.this$0.speedUnit;
                        if (textView6 == null) {
                        }
                        textView7 = this.this$0.speedTv;
                        if (textView7 == null) {
                        }
                        textView6.setVisibility(textView7.getVisibility());
                        float setMs22 = t.getVCruiseCluster() > 0.0f ? t.getVCruiseCluster() : t.getVCruise();
                        isSet = !t.getCruiseEnabled() && setMs22 > 0.0f && setMs22 < 200.0f;
                        float offset22 = t.tFloat("set_speed_offset", 0.0f);
                        textView8 = this.this$0.setSpeedTv;
                        if (textView8 == null) {
                        }
                        if (isSet) {
                        }
                        textView8.setText(str);
                        textView9 = this.this$0.setSpeedTv;
                        if (textView9 == null) {
                        }
                        textView9.setTextColor(isSet ? -1 : Color.rgb(114, 114, 114));
                        textView10 = this.this$0.maxLabel;
                        if (textView10 == null) {
                        }
                        textView10.setTextColor(!isSet ? Color.rgb(166, 166, 166) : (t.getEnabled() && t.getActive()) ? Color.rgb(128, 216, 166) : Color.rgb(145, 155, 149));
                        linearLayout2 = this.this$0.maxCard;
                        if (linearLayout2 == null) {
                        }
                        linearLayout2.setVisibility((t.s("show_max") || !t.getStarted()) ? 4 : 0);
                        slcView = this.this$0.slc;
                        if (slcView == null) {
                        }
                        slcView.setVisibility((t.getStarted() || !t.s("speed_limit")) ? 4 : 0);
                        wheelButton = this.this$0.wheel;
                        if (wheelButton == null) {
                        }
                        wheelButton.setVisibility((t.s("wheel_button") || !t.getStarted()) ? 4 : 0);
                        miciDistanceView = this.this$0.personality;
                        if (miciDistanceView == null) {
                        }
                        miciDistanceView.setVisibility((t.getStarted() || !t.s("personality_button")) ? 8 : 0);
                        slcActionsView = this.this$0.slcActions;
                        if (slcActionsView == null) {
                        }
                        if (t.getStarted()) {
                        }
                        j2 = 0;
                        i5 = 8;
                        slcActionsView.setVisibility(i5);
                        if (t.getStarted()) {
                        }
                        i6 = i4;
                        pair = null;
                        cl = pair;
                        textView11 = this.this$0.curveLabel;
                        if (textView11 == null) {
                        }
                        textView11.setText((cl != null || (str15 = (String) cl.getFirst()) == null) ? "" : str15);
                        if (cl != null) {
                        }
                        textView12 = this.this$0.curveLabel;
                        if (textView12 == null) {
                        }
                        textView12.setVisibility(cl == null ? 0 : 8);
                        boolean noAlert222 = Intrinsics.areEqual(t.getAlertSize(), "none");
                        textView13 = this.this$0.clockTv;
                        if (textView13 == null) {
                        }
                        textView13.setVisibility((!t.getStarted() && t.s("clock") && noAlert222) ? 0 : 8);
                        textView14 = this.this$0.clockTv;
                        if (textView14 == null) {
                        }
                        if (textView14.getVisibility() == 0) {
                        }
                        textView15 = this.this$0.modeText;
                        if (textView15 == null) {
                        }
                        textView15.setVisibility((!t.getStarted() && t.s("mode_text") && noAlert222) ? 0 : 8);
                        textView16 = this.this$0.modeText;
                        if (textView16 == null) {
                        }
                        if (textView16.getVisibility() == 0) {
                        }
                        devSidebarView = this.this$0.devSidebar;
                        if (devSidebarView == null) {
                        }
                        devSidebarView.setVisibility((t.getStarted() || !t.s("dev_sidebar")) ? 8 : 0);
                        SideCam sideCam222 = SideCam.INSTANCE;
                        str2 = this.this$0.host;
                        i7 = this.this$0.port;
                        int stall3222 = 0;
                        SideCam.tick$default(sideCam222, t, str2, i7, 0L, 8, null);
                        List sides222 = SideCam.INSTANCE.wanted(t);
                        sideView = this.this$0.pipLeft;
                        if (sideView == null) {
                        }
                        sideView.setVisibility(!sides222.contains("left") ? 0 : 8);
                        sideView2 = this.this$0.pipRight;
                        if (sideView2 == null) {
                        }
                        sideView2.setVisibility(!sides222.contains("right") ? 0 : 8);
                        cnState = Telemetry.pStr$default(t, "ChestnutState", null, i6, null);
                        if (cnState.length() <= 0) {
                        }
                        textView17 = this.this$0.roadName;
                        if (textView17 == null) {
                        }
                        textView17.setText((t.getStarted() || !t.s("road_name")) ? "" : t.getRoadName());
                        aetherGaugeView = this.this$0.gauge;
                        if (aetherGaugeView == null) {
                        }
                        aetherGaugeView.setVisibility((t.getStarted() || !t.s("aether_gauge")) ? 8 : 0);
                        slcSourcesView = this.this$0.slcSources;
                        if (slcSourcesView == null) {
                        }
                        if (t.getStarted()) {
                        }
                        i8 = 8;
                        slcSourcesView.setVisibility(i8);
                        Pair card2222 = Telemetry.navCard$default(t, 0L, 1, null);
                        navCardView = this.this$0.navCard;
                        if (navCardView == null) {
                        }
                        if (t.getStarted()) {
                        }
                        i9 = 8;
                        navCardView.setVisibility(i9);
                        navCardView2 = this.this$0.navCard;
                        if (navCardView2 == null) {
                        }
                        navCardView2.setAlpha(!(card2222 == null && ((Boolean) card2222.getSecond()).booleanValue()) ? 0.55f : 1.0f);
                        slowReasonView = this.this$0.slowCard;
                        if (slowReasonView == null) {
                        }
                        if (t.getStarted()) {
                        }
                        i10 = 8;
                        slowReasonView.setVisibility(i10);
                        if (t.getStarted()) {
                        }
                        if (t.getStarted()) {
                        }
                        s = this.this$0.scene;
                        if (s != null) {
                        }
                        if (t.getStarted()) {
                        }
                        this.this$0.laneWasStarted = t.getStarted();
                        laneCardView = this.this$0.laneCard;
                        if (laneCardView == null) {
                        }
                        laneCardView.setVisibility((t.getStarted() || !t.s("lane_card")) ? 8 : i11);
                        laneShots = this.this$0.getLaneShots();
                        detector = this.this$0.detectorRef;
                        laneShots.setDetector(detector);
                        laneCardView2 = this.this$0.laneCard;
                        if (laneCardView2 == null) {
                        }
                        if (laneCardView2.getVisibility() == 0) {
                        }
                        blinkerCardView = this.this$0.blinkCard;
                        if (blinkerCardView == null) {
                        }
                        dataClient2 = this.this$0.data;
                        if (dataClient2 == null) {
                        }
                        blinkerCardView.setVisibility((dataClient2.getConnected() || !t.s("blink_card")) ? 8 : i11);
                        gpsCardView = this.this$0.gpsCard;
                        if (gpsCardView == null) {
                        }
                        dataClient3 = this.this$0.data;
                        if (dataClient3 == null) {
                        }
                        gpsCardView.setVisibility((dataClient3.getConnected() || !t.s("gps_card")) ? 8 : i11);
                        boolean laneOn222222 = t.s("lane_suggest");
                        boolean turnOn222222 = t.s("turn_signals");
                        if (t.s("car_memory")) {
                        }
                        if (!t.getLeftBlindspot()) {
                        }
                        z12 = 1;
                        if (!t.getRightBlindspot()) {
                        }
                        z13 = 1;
                        active = Replay.INSTANCE.getActive();
                        z14 = this.this$0.replayWas;
                        if (active != z14) {
                        }
                        if (!t.getStarted()) {
                        }
                        dataClient4 = this.this$0.data;
                        if (dataClient4 == null) {
                        }
                        if (!dataClient4.getConnected()) {
                        }
                    }
                    z6 = false;
                    mainActivity22.syncDetector(z6);
                    OsmRoads osmRoads222 = OsmRoads.INSTANCE;
                    uiSettings2 = this.this$0.settings;
                    if (uiSettings2 == null) {
                    }
                    osmRoads222.setEnabled(uiSettings2.get("map_roads"));
                    overlayView = this.this$0.overlay;
                    if (overlayView == null) {
                    }
                    if (overlayView.updateCalibration()) {
                    }
                    overlayView2 = this.this$0.overlay;
                    if (overlayView2 == null) {
                    }
                    overlayView2.setUiStatus(UiStatus.Companion.from(t));
                    currentTimeMillis = System.currentTimeMillis();
                    j = this.this$0.lastThemeCheck;
                    if (currentTimeMillis - j > 15000) {
                    }
                    boolean metric222 = t.isMetric();
                    float conv222 = metric222 ? 3.6f : 2.23694f;
                    boolean useWheel222 = t.tBool("use_wheel_speed", false);
                    float v222 = (!useWheel222 || t.getVEgoCluster() <= 0.0f) ? t.getVEgo() : t.getVEgoCluster();
                    textView3 = this.this$0.speedTv;
                    if (textView3 == null) {
                    }
                    textView3.setText(String.valueOf(RangesKt.coerceAtLeast(MathKt.roundToInt(v222 * conv222), 0)));
                    textView4 = this.this$0.speedUnit;
                    if (textView4 == null) {
                    }
                    textView4.setText(metric222 ? "km/h" : "mph");
                    boolean hideSpeed222 = t.s("show_speed") || !t.getStarted();
                    textView5 = this.this$0.speedTv;
                    if (textView5 == null) {
                    }
                    textView5.setVisibility(hideSpeed222 ? 4 : 0);
                    textView6 = this.this$0.speedUnit;
                    if (textView6 == null) {
                    }
                    textView7 = this.this$0.speedTv;
                    if (textView7 == null) {
                    }
                    textView6.setVisibility(textView7.getVisibility());
                    float setMs222 = t.getVCruiseCluster() > 0.0f ? t.getVCruiseCluster() : t.getVCruise();
                    isSet = !t.getCruiseEnabled() && setMs222 > 0.0f && setMs222 < 200.0f;
                    float offset222 = t.tFloat("set_speed_offset", 0.0f);
                    textView8 = this.this$0.setSpeedTv;
                    if (textView8 == null) {
                    }
                    if (isSet) {
                    }
                    textView8.setText(str);
                    textView9 = this.this$0.setSpeedTv;
                    if (textView9 == null) {
                    }
                    textView9.setTextColor(isSet ? -1 : Color.rgb(114, 114, 114));
                    textView10 = this.this$0.maxLabel;
                    if (textView10 == null) {
                    }
                    textView10.setTextColor(!isSet ? Color.rgb(166, 166, 166) : (t.getEnabled() && t.getActive()) ? Color.rgb(128, 216, 166) : Color.rgb(145, 155, 149));
                    linearLayout2 = this.this$0.maxCard;
                    if (linearLayout2 == null) {
                    }
                    linearLayout2.setVisibility((t.s("show_max") || !t.getStarted()) ? 4 : 0);
                    slcView = this.this$0.slc;
                    if (slcView == null) {
                    }
                    slcView.setVisibility((t.getStarted() || !t.s("speed_limit")) ? 4 : 0);
                    wheelButton = this.this$0.wheel;
                    if (wheelButton == null) {
                    }
                    wheelButton.setVisibility((t.s("wheel_button") || !t.getStarted()) ? 4 : 0);
                    miciDistanceView = this.this$0.personality;
                    if (miciDistanceView == null) {
                    }
                    miciDistanceView.setVisibility((t.getStarted() || !t.s("personality_button")) ? 8 : 0);
                    slcActionsView = this.this$0.slcActions;
                    if (slcActionsView == null) {
                    }
                    if (t.getStarted()) {
                    }
                    j2 = 0;
                    i5 = 8;
                    slcActionsView.setVisibility(i5);
                    if (t.getStarted()) {
                    }
                    i6 = i4;
                    pair = null;
                    cl = pair;
                    textView11 = this.this$0.curveLabel;
                    if (textView11 == null) {
                    }
                    textView11.setText((cl != null || (str15 = (String) cl.getFirst()) == null) ? "" : str15);
                    if (cl != null) {
                    }
                    textView12 = this.this$0.curveLabel;
                    if (textView12 == null) {
                    }
                    textView12.setVisibility(cl == null ? 0 : 8);
                    boolean noAlert2222 = Intrinsics.areEqual(t.getAlertSize(), "none");
                    textView13 = this.this$0.clockTv;
                    if (textView13 == null) {
                    }
                    textView13.setVisibility((!t.getStarted() && t.s("clock") && noAlert2222) ? 0 : 8);
                    textView14 = this.this$0.clockTv;
                    if (textView14 == null) {
                    }
                    if (textView14.getVisibility() == 0) {
                    }
                    textView15 = this.this$0.modeText;
                    if (textView15 == null) {
                    }
                    textView15.setVisibility((!t.getStarted() && t.s("mode_text") && noAlert2222) ? 0 : 8);
                    textView16 = this.this$0.modeText;
                    if (textView16 == null) {
                    }
                    if (textView16.getVisibility() == 0) {
                    }
                    devSidebarView = this.this$0.devSidebar;
                    if (devSidebarView == null) {
                    }
                    devSidebarView.setVisibility((t.getStarted() || !t.s("dev_sidebar")) ? 8 : 0);
                    SideCam sideCam2222 = SideCam.INSTANCE;
                    str2 = this.this$0.host;
                    i7 = this.this$0.port;
                    int stall32222 = 0;
                    SideCam.tick$default(sideCam2222, t, str2, i7, 0L, 8, null);
                    List sides2222 = SideCam.INSTANCE.wanted(t);
                    sideView = this.this$0.pipLeft;
                    if (sideView == null) {
                    }
                    sideView.setVisibility(!sides2222.contains("left") ? 0 : 8);
                    sideView2 = this.this$0.pipRight;
                    if (sideView2 == null) {
                    }
                    sideView2.setVisibility(!sides2222.contains("right") ? 0 : 8);
                    cnState = Telemetry.pStr$default(t, "ChestnutState", null, i6, null);
                    if (cnState.length() <= 0) {
                    }
                    textView17 = this.this$0.roadName;
                    if (textView17 == null) {
                    }
                    textView17.setText((t.getStarted() || !t.s("road_name")) ? "" : t.getRoadName());
                    aetherGaugeView = this.this$0.gauge;
                    if (aetherGaugeView == null) {
                    }
                    aetherGaugeView.setVisibility((t.getStarted() || !t.s("aether_gauge")) ? 8 : 0);
                    slcSourcesView = this.this$0.slcSources;
                    if (slcSourcesView == null) {
                    }
                    if (t.getStarted()) {
                    }
                    i8 = 8;
                    slcSourcesView.setVisibility(i8);
                    Pair card22222 = Telemetry.navCard$default(t, 0L, 1, null);
                    navCardView = this.this$0.navCard;
                    if (navCardView == null) {
                    }
                    if (t.getStarted()) {
                    }
                    i9 = 8;
                    navCardView.setVisibility(i9);
                    navCardView2 = this.this$0.navCard;
                    if (navCardView2 == null) {
                    }
                    navCardView2.setAlpha(!(card22222 == null && ((Boolean) card22222.getSecond()).booleanValue()) ? 0.55f : 1.0f);
                    slowReasonView = this.this$0.slowCard;
                    if (slowReasonView == null) {
                    }
                    if (t.getStarted()) {
                    }
                    i10 = 8;
                    slowReasonView.setVisibility(i10);
                    if (t.getStarted()) {
                    }
                    if (t.getStarted()) {
                    }
                    s = this.this$0.scene;
                    if (s != null) {
                    }
                    if (t.getStarted()) {
                    }
                    this.this$0.laneWasStarted = t.getStarted();
                    laneCardView = this.this$0.laneCard;
                    if (laneCardView == null) {
                    }
                    laneCardView.setVisibility((t.getStarted() || !t.s("lane_card")) ? 8 : i11);
                    laneShots = this.this$0.getLaneShots();
                    detector = this.this$0.detectorRef;
                    laneShots.setDetector(detector);
                    laneCardView2 = this.this$0.laneCard;
                    if (laneCardView2 == null) {
                    }
                    if (laneCardView2.getVisibility() == 0) {
                    }
                    blinkerCardView = this.this$0.blinkCard;
                    if (blinkerCardView == null) {
                    }
                    dataClient2 = this.this$0.data;
                    if (dataClient2 == null) {
                    }
                    blinkerCardView.setVisibility((dataClient2.getConnected() || !t.s("blink_card")) ? 8 : i11);
                    gpsCardView = this.this$0.gpsCard;
                    if (gpsCardView == null) {
                    }
                    dataClient3 = this.this$0.data;
                    if (dataClient3 == null) {
                    }
                    gpsCardView.setVisibility((dataClient3.getConnected() || !t.s("gps_card")) ? 8 : i11);
                    boolean laneOn2222222 = t.s("lane_suggest");
                    boolean turnOn2222222 = t.s("turn_signals");
                    if (t.s("car_memory")) {
                    }
                    if (!t.getLeftBlindspot()) {
                    }
                    z12 = 1;
                    if (!t.getRightBlindspot()) {
                    }
                    z13 = 1;
                    active = Replay.INSTANCE.getActive();
                    z14 = this.this$0.replayWas;
                    if (active != z14) {
                    }
                    if (!t.getStarted()) {
                    }
                    dataClient4 = this.this$0.data;
                    if (dataClient4 == null) {
                    }
                    if (!dataClient4.getConnected()) {
                    }
                }
            }
        }
        z2 = false;
        wantHome = z2;
        z3 = this.this$0.homeShown;
        if (wantHome != z3) {
        }
        if (wantHome) {
        }
        if (wantHome) {
        }
        StorageInfo storageInfo2 = StorageInfo.INSTANCE;
        offroadHome = this.this$0.offroadHome;
        if (offroadHome == null) {
        }
        boolean copying2 = storageInfo2.copying(offroadHome.getStorage()) == null;
        if (wantHome) {
        }
        textView2 = this.this$0.statusTv;
        if (textView2 == null) {
        }
        textView2.setVisibility(((connected2 || !t.getStarted()) && !wantHome) ? 0 : 8);
        this.this$0.syncScene(!connected2 && t.getStarted());
        if (connected2) {
            z25 = this.this$0.sceneOn;
            if (!z25) {
            }
        }
        z4 = false;
        wantVideo = z4;
        if (wantVideo) {
        }
        if (!wantVideo) {
        }
        MainActivity mainActivity222 = this.this$0;
        z5 = this.this$0.visible;
        if (z5) {
        }
        z6 = false;
        mainActivity222.syncDetector(z6);
        OsmRoads osmRoads2222 = OsmRoads.INSTANCE;
        uiSettings2 = this.this$0.settings;
        if (uiSettings2 == null) {
        }
        osmRoads2222.setEnabled(uiSettings2.get("map_roads"));
        overlayView = this.this$0.overlay;
        if (overlayView == null) {
        }
        if (overlayView.updateCalibration()) {
        }
        overlayView2 = this.this$0.overlay;
        if (overlayView2 == null) {
        }
        overlayView2.setUiStatus(UiStatus.Companion.from(t));
        currentTimeMillis = System.currentTimeMillis();
        j = this.this$0.lastThemeCheck;
        if (currentTimeMillis - j > 15000) {
        }
        boolean metric2222 = t.isMetric();
        float conv2222 = metric2222 ? 3.6f : 2.23694f;
        boolean useWheel2222 = t.tBool("use_wheel_speed", false);
        float v2222 = (!useWheel2222 || t.getVEgoCluster() <= 0.0f) ? t.getVEgo() : t.getVEgoCluster();
        textView3 = this.this$0.speedTv;
        if (textView3 == null) {
        }
        textView3.setText(String.valueOf(RangesKt.coerceAtLeast(MathKt.roundToInt(v2222 * conv2222), 0)));
        textView4 = this.this$0.speedUnit;
        if (textView4 == null) {
        }
        textView4.setText(metric2222 ? "km/h" : "mph");
        boolean hideSpeed2222 = t.s("show_speed") || !t.getStarted();
        textView5 = this.this$0.speedTv;
        if (textView5 == null) {
        }
        textView5.setVisibility(hideSpeed2222 ? 4 : 0);
        textView6 = this.this$0.speedUnit;
        if (textView6 == null) {
        }
        textView7 = this.this$0.speedTv;
        if (textView7 == null) {
        }
        textView6.setVisibility(textView7.getVisibility());
        float setMs2222 = t.getVCruiseCluster() > 0.0f ? t.getVCruiseCluster() : t.getVCruise();
        isSet = !t.getCruiseEnabled() && setMs2222 > 0.0f && setMs2222 < 200.0f;
        float offset2222 = t.tFloat("set_speed_offset", 0.0f);
        textView8 = this.this$0.setSpeedTv;
        if (textView8 == null) {
        }
        if (isSet) {
        }
        textView8.setText(str);
        textView9 = this.this$0.setSpeedTv;
        if (textView9 == null) {
        }
        textView9.setTextColor(isSet ? -1 : Color.rgb(114, 114, 114));
        textView10 = this.this$0.maxLabel;
        if (textView10 == null) {
        }
        textView10.setTextColor(!isSet ? Color.rgb(166, 166, 166) : (t.getEnabled() && t.getActive()) ? Color.rgb(128, 216, 166) : Color.rgb(145, 155, 149));
        linearLayout2 = this.this$0.maxCard;
        if (linearLayout2 == null) {
        }
        linearLayout2.setVisibility((t.s("show_max") || !t.getStarted()) ? 4 : 0);
        slcView = this.this$0.slc;
        if (slcView == null) {
        }
        slcView.setVisibility((t.getStarted() || !t.s("speed_limit")) ? 4 : 0);
        wheelButton = this.this$0.wheel;
        if (wheelButton == null) {
        }
        wheelButton.setVisibility((t.s("wheel_button") || !t.getStarted()) ? 4 : 0);
        miciDistanceView = this.this$0.personality;
        if (miciDistanceView == null) {
        }
        miciDistanceView.setVisibility((t.getStarted() || !t.s("personality_button")) ? 8 : 0);
        slcActionsView = this.this$0.slcActions;
        if (slcActionsView == null) {
        }
        if (t.getStarted()) {
        }
        j2 = 0;
        i5 = 8;
        slcActionsView.setVisibility(i5);
        if (t.getStarted()) {
        }
        i6 = i4;
        pair = null;
        cl = pair;
        textView11 = this.this$0.curveLabel;
        if (textView11 == null) {
        }
        textView11.setText((cl != null || (str15 = (String) cl.getFirst()) == null) ? "" : str15);
        if (cl != null) {
        }
        textView12 = this.this$0.curveLabel;
        if (textView12 == null) {
        }
        textView12.setVisibility(cl == null ? 0 : 8);
        boolean noAlert22222 = Intrinsics.areEqual(t.getAlertSize(), "none");
        textView13 = this.this$0.clockTv;
        if (textView13 == null) {
        }
        textView13.setVisibility((!t.getStarted() && t.s("clock") && noAlert22222) ? 0 : 8);
        textView14 = this.this$0.clockTv;
        if (textView14 == null) {
        }
        if (textView14.getVisibility() == 0) {
        }
        textView15 = this.this$0.modeText;
        if (textView15 == null) {
        }
        textView15.setVisibility((!t.getStarted() && t.s("mode_text") && noAlert22222) ? 0 : 8);
        textView16 = this.this$0.modeText;
        if (textView16 == null) {
        }
        if (textView16.getVisibility() == 0) {
        }
        devSidebarView = this.this$0.devSidebar;
        if (devSidebarView == null) {
        }
        devSidebarView.setVisibility((t.getStarted() || !t.s("dev_sidebar")) ? 8 : 0);
        SideCam sideCam22222 = SideCam.INSTANCE;
        str2 = this.this$0.host;
        i7 = this.this$0.port;
        int stall322222 = 0;
        SideCam.tick$default(sideCam22222, t, str2, i7, 0L, 8, null);
        List sides22222 = SideCam.INSTANCE.wanted(t);
        sideView = this.this$0.pipLeft;
        if (sideView == null) {
        }
        sideView.setVisibility(!sides22222.contains("left") ? 0 : 8);
        sideView2 = this.this$0.pipRight;
        if (sideView2 == null) {
        }
        sideView2.setVisibility(!sides22222.contains("right") ? 0 : 8);
        cnState = Telemetry.pStr$default(t, "ChestnutState", null, i6, null);
        if (cnState.length() <= 0) {
        }
        textView17 = this.this$0.roadName;
        if (textView17 == null) {
        }
        textView17.setText((t.getStarted() || !t.s("road_name")) ? "" : t.getRoadName());
        aetherGaugeView = this.this$0.gauge;
        if (aetherGaugeView == null) {
        }
        aetherGaugeView.setVisibility((t.getStarted() || !t.s("aether_gauge")) ? 8 : 0);
        slcSourcesView = this.this$0.slcSources;
        if (slcSourcesView == null) {
        }
        if (t.getStarted()) {
        }
        i8 = 8;
        slcSourcesView.setVisibility(i8);
        Pair card222222 = Telemetry.navCard$default(t, 0L, 1, null);
        navCardView = this.this$0.navCard;
        if (navCardView == null) {
        }
        if (t.getStarted()) {
        }
        i9 = 8;
        navCardView.setVisibility(i9);
        navCardView2 = this.this$0.navCard;
        if (navCardView2 == null) {
        }
        navCardView2.setAlpha(!(card222222 == null && ((Boolean) card222222.getSecond()).booleanValue()) ? 0.55f : 1.0f);
        slowReasonView = this.this$0.slowCard;
        if (slowReasonView == null) {
        }
        if (t.getStarted()) {
        }
        i10 = 8;
        slowReasonView.setVisibility(i10);
        if (t.getStarted()) {
        }
        if (t.getStarted()) {
        }
        s = this.this$0.scene;
        if (s != null) {
        }
        if (t.getStarted()) {
        }
        this.this$0.laneWasStarted = t.getStarted();
        laneCardView = this.this$0.laneCard;
        if (laneCardView == null) {
        }
        laneCardView.setVisibility((t.getStarted() || !t.s("lane_card")) ? 8 : i11);
        laneShots = this.this$0.getLaneShots();
        detector = this.this$0.detectorRef;
        laneShots.setDetector(detector);
        laneCardView2 = this.this$0.laneCard;
        if (laneCardView2 == null) {
        }
        if (laneCardView2.getVisibility() == 0) {
        }
        blinkerCardView = this.this$0.blinkCard;
        if (blinkerCardView == null) {
        }
        dataClient2 = this.this$0.data;
        if (dataClient2 == null) {
        }
        blinkerCardView.setVisibility((dataClient2.getConnected() || !t.s("blink_card")) ? 8 : i11);
        gpsCardView = this.this$0.gpsCard;
        if (gpsCardView == null) {
        }
        dataClient3 = this.this$0.data;
        if (dataClient3 == null) {
        }
        gpsCardView.setVisibility((dataClient3.getConnected() || !t.s("gps_card")) ? 8 : i11);
        boolean laneOn22222222 = t.s("lane_suggest");
        boolean turnOn22222222 = t.s("turn_signals");
        if (t.s("car_memory")) {
        }
        if (!t.getLeftBlindspot()) {
        }
        z12 = 1;
        if (!t.getRightBlindspot()) {
        }
        z13 = 1;
        active = Replay.INSTANCE.getActive();
        z14 = this.this$0.replayWas;
        if (active != z14) {
        }
        if (!t.getStarted()) {
        }
        dataClient4 = this.this$0.data;
        if (dataClient4 == null) {
        }
        if (!dataClient4.getConnected()) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String run$lambda$5(MainActivity this$0, Telemetry $t) {
        LaneBrain laneBrain;
        LaneShots.Companion companion = LaneShots.Companion;
        laneBrain = this$0.laneTracker;
        return companion.caption(laneBrain, "every 30 s", $t.getVEgo() * 2.23694f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence run$lambda$27$lambda$24(Map.Entry it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getValue() + " " + it.getKey();
    }
}
