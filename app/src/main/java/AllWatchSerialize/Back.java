// automatically generated by the FlatBuffers compiler, do not modify

package AllWatchSerialize;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class Back extends Table {
    public static Back getRootAsBack(ByteBuffer _bb) {
        return getRootAsBack(_bb, new Back());
    }

    public static Back getRootAsBack(ByteBuffer _bb, Back obj) {
        _bb.order(ByteOrder.LITTLE_ENDIAN);
        return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
    }

    public static int createBack(FlatBufferBuilder builder,
                                 int extrasOffset) {
        builder.startObject(1);
        Back.addExtras(builder, extrasOffset);
        return Back.endBack(builder);
    }

    public static void startBack(FlatBufferBuilder builder) {
        builder.startObject(1);
    }

    public static void addExtras(FlatBufferBuilder builder, int extrasOffset) {
        builder.addOffset(0, extrasOffset, 0);
    }

    public static int createExtrasVector(FlatBufferBuilder builder, int[] data) {
        builder.startVector(4, data.length, 4);
        for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]);
        return builder.endVector();
    }

    public static void startExtrasVector(FlatBufferBuilder builder, int numElems) {
        builder.startVector(4, numElems, 4);
    }

    public static int endBack(FlatBufferBuilder builder) {
        int o = builder.endObject();
        return o;
    }

    public void __init(int _i, ByteBuffer _bb) {
        bb_pos = _i;
        bb = _bb;
    }

    public Back __assign(int _i, ByteBuffer _bb) {
        __init(_i, _bb);
        return this;
    }

    public Extras extras(int j) {
        return extras(new Extras(), j);
    }

    public Extras extras(Extras obj, int j) {
        int o = __offset(4);
        return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null;
    }

    public int extrasLength() {
        int o = __offset(4);
        return o != 0 ? __vector_len(o) : 0;
    }
}

