package org.capnproto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.capnproto.StructList;
import org.capnproto.Text;

/* loaded from: classes3.dex */
public final class SlcPeek {
    public static final int SLC_STATE = 114;
    static final StructList.Factory<RBuilder, RReader> READINGS = new StructList.Factory<>(new RFactory());
    static final SFactory SLC = new SFactory();
    static final CFactory CURVE = new CFactory();
    static final LFactory LC = new LFactory();

    private SlcPeek() {
    }

    public static final class Reading {
        public final boolean enabled;
        public final String kind;
        public final String source;
        public final float speedMps;

        Reading(String source, boolean enabled, String kind, float speedMps) {
            this.source = source;
            this.enabled = enabled;
            this.kind = kind;
            this.speedMps = speedMps;
        }
    }

    public static final class Curve {
        public final boolean applied;
        public final float ceilingMps;
        public final boolean configured;
        public final boolean controlling;
        public final boolean glow;
        public final boolean hasCeiling;
        public final boolean hasRoadCurvature;
        public final float progress;
        public final float roadCurvature;
        public final boolean training;

        Curve(boolean configured, boolean hasCeiling, float ceilingMps, boolean applied, boolean controlling, boolean training, float progress, boolean glow, boolean hasRoadCurvature, float roadCurvature) {
            this.configured = configured;
            this.hasCeiling = hasCeiling;
            this.ceilingMps = ceilingMps;
            this.applied = applied;
            this.controlling = controlling;
            this.training = training;
            this.progress = progress;
            this.glow = glow;
            this.hasRoadCurvature = hasRoadCurvature;
            this.roadCurvature = roadCurvature;
        }
    }

    public static final class Slc {
        public final float acceptedMps;
        public final String acceptedSource;
        public boolean autoAdoptionPending;
        public Curve curve;
        public long decisionId;
        public boolean driverOverride;
        public final boolean enabled;
        public final boolean hasAccepted;
        public final String kind;
        public float offsetMps;
        public long presentationId;
        public final List<Reading> readings;
        public final String source;
        public final float speedMps;
        public String sessionId = "";
        public String status = "";
        public float capMps = Float.NaN;
        public float pendingMps = Float.NaN;
        public float adjustedMps = Float.NaN;

        Slc(boolean enabled, boolean hasAccepted, String kind, String source, String acceptedSource, float speedMps, float acceptedMps, List<Reading> readings) {
            this.enabled = enabled;
            this.hasAccepted = hasAccepted;
            this.kind = kind;
            this.source = source;
            this.acceptedSource = acceptedSource;
            this.speedMps = speedMps;
            this.acceptedMps = acceptedMps;
            this.readings = readings;
        }
    }

    static String text(StructReader r, int ptr) {
        return (ptr >= r.pointerCount || r._pointerFieldIsNull(ptr)) ? "" : ((Text.Reader) r._getPointerField(Text.factory, ptr)).toString();
    }

    static final class RReader extends StructReader {
        RReader(SegmentReader s, int d, int p, int ds, short pc, int nl) {
            super(s, d, p, ds, pc, nl);
        }

        Reading read() {
            return new Reading(SlcPeek.text(this, 0), _getBooleanField(0), SlcPeek.text(this, 1), _getFloatField(1));
        }
    }

    static final class RBuilder extends StructBuilder {
        RBuilder(SegmentBuilder s, int d, int p, int ds, short pc) {
            super(s, d, p, ds, pc);
        }
    }

    static final class RFactory extends StructFactory<RBuilder, RReader> {
        RFactory() {
        }

        @Override // org.capnproto.StructReader.Factory
        public final RReader constructReader(SegmentReader s, int d, int p, int ds, short pc, int nl) {
            return new RReader(s, d, p, ds, pc, nl);
        }

        @Override // org.capnproto.StructBuilder.Factory
        public final RBuilder constructBuilder(SegmentBuilder s, int d, int p, int ds, short pc) {
            return new RBuilder(s, d, p, ds, pc);
        }

        @Override // org.capnproto.StructBuilder.Factory
        public final StructSize structSize() {
            return new StructSize((short) 1, (short) 2);
        }

        @Override // org.capnproto.StructFactory
        public final RReader asReader(RBuilder b) {
            throw new UnsupportedOperationException();
        }
    }

    static final class SReader extends StructReader {
        SReader(SegmentReader s, int d, int p, int ds, short pc, int nl) {
            super(s, d, p, ds, pc, nl);
        }

        Slc read() {
            List<Reading> list = new ArrayList<>();
            if (18 < this.pointerCount && !_pointerFieldIsNull(18)) {
                Iterator it = ((StructList.Reader) _getPointerField(SlcPeek.READINGS, 18)).iterator();
                while (it.hasNext()) {
                    RReader r = (RReader) it.next();
                    list.add(r.read());
                }
            }
            Slc s = new Slc(_getBooleanField(128), _getBooleanField(130), SlcPeek.text(this, 6), SlcPeek.text(this, 7), SlcPeek.text(this, 16), _getFloatField(5), _getFloatField(6), list);
            s.sessionId = SlcPeek.text(this, 5);
            s.decisionId = _getLongField(5);
            s.presentationId = _getLongField(6);
            s.status = SlcPeek.text(this, 8);
            s.offsetMps = _getFloatField(7);
            if (_getBooleanField(131)) {
                s.capMps = _getFloatField(8);
            }
            if (_getBooleanField(132)) {
                s.pendingMps = _getFloatField(9);
            }
            if (_getBooleanField(138)) {
                s.adjustedMps = _getFloatField(26);
            }
            s.driverOverride = _getBooleanField(135);
            s.autoAdoptionPending = _getBooleanField(140);
            if (12 < this.pointerCount && !_pointerFieldIsNull(12)) {
                s.curve = ((CReader) _getPointerField(SlcPeek.CURVE, 12)).read();
            }
            return s;
        }
    }

    static final class SBuilder extends StructBuilder {
        SBuilder(SegmentBuilder s, int d, int p, int ds, short pc) {
            super(s, d, p, ds, pc);
        }
    }

    static final class SFactory extends StructFactory<SBuilder, SReader> {
        SFactory() {
        }

        @Override // org.capnproto.StructReader.Factory
        public final SReader constructReader(SegmentReader s, int d, int p, int ds, short pc, int nl) {
            return new SReader(s, d, p, ds, pc, nl);
        }

        @Override // org.capnproto.StructBuilder.Factory
        public final SBuilder constructBuilder(SegmentBuilder s, int d, int p, int ds, short pc) {
            return new SBuilder(s, d, p, ds, pc);
        }

        @Override // org.capnproto.StructBuilder.Factory
        public final StructSize structSize() {
            return new StructSize((short) 17, (short) 21);
        }

        @Override // org.capnproto.StructFactory
        public final SReader asReader(SBuilder b) {
            throw new UnsupportedOperationException();
        }
    }

    static final class CReader extends StructReader {
        CReader(SegmentReader s, int d, int p, int ds, short pc, int nl) {
            super(s, d, p, ds, pc, nl);
        }

        Curve read() {
            return new Curve(_getBooleanField(16), _getBooleanField(19), _getFloatField(10), _getBooleanField(20), _getBooleanField(21), _getBooleanField(22), _getFloatField(11), _getBooleanField(23), _getBooleanField(25), _getFloatField(14));
        }
    }

    static final class CBuilder extends StructBuilder {
        CBuilder(SegmentBuilder s, int d, int p, int ds, short pc) {
            super(s, d, p, ds, pc);
        }
    }

    static final class CFactory extends StructFactory<CBuilder, CReader> {
        CFactory() {
        }

        @Override // org.capnproto.StructReader.Factory
        public final CReader constructReader(SegmentReader s, int d, int p, int ds, short pc, int nl) {
            return new CReader(s, d, p, ds, pc, nl);
        }

        @Override // org.capnproto.StructBuilder.Factory
        public final CBuilder constructBuilder(SegmentBuilder s, int d, int p, int ds, short pc) {
            return new CBuilder(s, d, p, ds, pc);
        }

        @Override // org.capnproto.StructBuilder.Factory
        public final StructSize structSize() {
            return new StructSize((short) 8, (short) 4);
        }

        @Override // org.capnproto.StructFactory
        public final CReader asReader(CBuilder b) {
            throw new UnsupportedOperationException();
        }
    }

    public static final class LaneCentering {
        public final float applied;
        public final boolean lateralActive;
        public final String reason;
        public final float requested;
        public final int version;

        LaneCentering(int version, boolean lateralActive, float requested, float applied, String reason) {
            this.version = version;
            this.lateralActive = lateralActive;
            this.requested = requested;
            this.applied = applied;
            this.reason = reason;
        }
    }

    static final class LReader extends StructReader {
        LReader(SegmentReader s, int d, int p, int ds, short pc, int nl) {
            super(s, d, p, ds, pc, nl);
        }

        LaneCentering read() {
            return new LaneCentering(_getShortField(0) & 65535, _getBooleanField(16), _getFloatField(1), _getFloatField(6), SlcPeek.text(this, 0));
        }
    }

    static final class LBuilder extends StructBuilder {
        LBuilder(SegmentBuilder s, int d, int p, int ds, short pc) {
            super(s, d, p, ds, pc);
        }
    }

    static final class LFactory extends StructFactory<LBuilder, LReader> {
        LFactory() {
        }

        @Override // org.capnproto.StructReader.Factory
        public final LReader constructReader(SegmentReader s, int d, int p, int ds, short pc, int nl) {
            return new LReader(s, d, p, ds, pc, nl);
        }

        @Override // org.capnproto.StructBuilder.Factory
        public final LBuilder constructBuilder(SegmentBuilder s, int d, int p, int ds, short pc) {
            return new LBuilder(s, d, p, ds, pc);
        }

        @Override // org.capnproto.StructBuilder.Factory
        public final StructSize structSize() {
            return new StructSize((short) 4, (short) 1);
        }

        @Override // org.capnproto.StructFactory
        public final LReader asReader(LBuilder b) {
            throw new UnsupportedOperationException();
        }
    }

    public static LaneCentering laneCentering(StructReader lateralState) {
        if (lateralState.pointerCount < 1 || lateralState._pointerFieldIsNull(0)) {
            return null;
        }
        return ((LReader) lateralState._getPointerField(LC, 0)).read();
    }

    public static Slc slcState(StructReader event) {
        if ((event._getShortField(4) & 65535) != 114 || event.pointerCount < 1 || event._pointerFieldIsNull(0)) {
            return null;
        }
        return ((SReader) event._getPointerField(SLC, 0)).read();
    }
}
