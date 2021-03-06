// automatically generated by the FlatBuffers compiler, do not modify

package AllWatchSerialize;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class Action extends Table {
    public static Action getRootAsAction(ByteBuffer _bb) {
        return getRootAsAction(_bb, new Action());
    }

    public static Action getRootAsAction(ByteBuffer _bb, Action obj) {
        _bb.order(ByteOrder.LITTLE_ENDIAN);
        return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
    }

    public static int createAction(FlatBufferBuilder builder,
                                   short temp,
                                   short a,
                                   int actionNameOffset,
                                   int callbackOffset,
                                   int extrasOffset) {
        builder.startObject(5);
        Action.addExtras(builder, extrasOffset);
        Action.addCallback(builder, callbackOffset);
        Action.addActionName(builder, actionNameOffset);
        Action.addA(builder, a);
        Action.addTemp(builder, temp);
        return Action.endAction(builder);
    }

    public static void startAction(FlatBufferBuilder builder) {
        builder.startObject(5);
    }

    public static void addTemp(FlatBufferBuilder builder, short temp) {
        builder.addShort(0, temp, 0);
    }

    public static void addA(FlatBufferBuilder builder, short a) {
        builder.addShort(1, a, 0);
    }

    public static void addActionName(FlatBufferBuilder builder, int actionNameOffset) {
        builder.addOffset(2, actionNameOffset, 0);
    }

    public static void addCallback(FlatBufferBuilder builder, int callbackOffset) {
        builder.addOffset(3, callbackOffset, 0);
    }

    public static void addExtras(FlatBufferBuilder builder, int extrasOffset) {
        builder.addOffset(4, extrasOffset, 0);
    }

    public static int createExtrasVector(FlatBufferBuilder builder, int[] data) {
        builder.startVector(4, data.length, 4);
        for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]);
        return builder.endVector();
    }

    public static void startExtrasVector(FlatBufferBuilder builder, int numElems) {
        builder.startVector(4, numElems, 4);
    }

    public static int endAction(FlatBufferBuilder builder) {
        int o = builder.endObject();
        return o;
    }

    public void __init(int _i, ByteBuffer _bb) {
        bb_pos = _i;
        bb = _bb;
    }

    public Action __assign(int _i, ByteBuffer _bb) {
        __init(_i, _bb);
        return this;
    }

    public short temp() {
        int o = __offset(4);
        return o != 0 ? bb.getShort(o + bb_pos) : 0;
    }

    public short a() {
        int o = __offset(6);
        return o != 0 ? bb.getShort(o + bb_pos) : 0;
    }

    public String actionName() {
        int o = __offset(8);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer actionNameAsByteBuffer() {
        return __vector_as_bytebuffer(8, 1);
    }

    public ByteBuffer actionNameInByteBuffer(ByteBuffer _bb) {
        return __vector_in_bytebuffer(_bb, 8, 1);
    }

    public String callback() {
        int o = __offset(10);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer callbackAsByteBuffer() {
        return __vector_as_bytebuffer(10, 1);
    }

    public ByteBuffer callbackInByteBuffer(ByteBuffer _bb) {
        return __vector_in_bytebuffer(_bb, 10, 1);
    }

    public Extras extras(int j) {
        return extras(new Extras(), j);
    }

    public Extras extras(Extras obj, int j) {
        int o = __offset(12);
        return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null;
    }

    public int extrasLength() {
        int o = __offset(12);
        return o != 0 ? __vector_len(o) : 0;
    }
}

