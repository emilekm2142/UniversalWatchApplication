// automatically generated by the FlatBuffers compiler, do not modify

package AllWatchSerialize;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class Update extends Table {
    public static Update getRootAsUpdate(ByteBuffer _bb) {
        return getRootAsUpdate(_bb, new Update());
    }

    public static Update getRootAsUpdate(ByteBuffer _bb, Update obj) {
        _bb.order(ByteOrder.LITTLE_ENDIAN);
        return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
    }

    public static int createUpdate(FlatBufferBuilder builder,
                                   int viewOffset) {
        builder.startObject(1);
        Update.addView(builder, viewOffset);
        return Update.endUpdate(builder);
    }

    public static void startUpdate(FlatBufferBuilder builder) {
        builder.startObject(1);
    }

    public static void addView(FlatBufferBuilder builder, int viewOffset) {
        builder.addOffset(0, viewOffset, 0);
    }

    public static int endUpdate(FlatBufferBuilder builder) {
        int o = builder.endObject();
        return o;
    }

    public void __init(int _i, ByteBuffer _bb) {
        bb_pos = _i;
        bb = _bb;
    }

    public Update __assign(int _i, ByteBuffer _bb) {
        __init(_i, _bb);
        return this;
    }

    public View view() {
        return view(new View());
    }

    public View view(View obj) {
        int o = __offset(4);
        return o != 0 ? obj.__assign(__indirect(o + bb_pos), bb) : null;
    }
}

