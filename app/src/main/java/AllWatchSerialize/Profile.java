// automatically generated by the FlatBuffers compiler, do not modify

package AllWatchSerialize;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class Profile extends Table {
    public static Profile getRootAsProfile(ByteBuffer _bb) {
        return getRootAsProfile(_bb, new Profile());
    }

    public static Profile getRootAsProfile(ByteBuffer _bb, Profile obj) {
        _bb.order(ByteOrder.LITTLE_ENDIAN);
        return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
    }

    public static int createProfile(FlatBufferBuilder builder,
                                    int nameOffset,
                                    int imageOffset) {
        builder.startObject(2);
        Profile.addImage(builder, imageOffset);
        Profile.addName(builder, nameOffset);
        return Profile.endProfile(builder);
    }

    public static void startProfile(FlatBufferBuilder builder) {
        builder.startObject(2);
    }

    public static void addName(FlatBufferBuilder builder, int nameOffset) {
        builder.addOffset(0, nameOffset, 0);
    }

    public static void addImage(FlatBufferBuilder builder, int imageOffset) {
        builder.addOffset(1, imageOffset, 0);
    }

    public static int createImageVector(FlatBufferBuilder builder, byte[] data) {
        builder.startVector(1, data.length, 1);
        for (int i = data.length - 1; i >= 0; i--) builder.addByte(data[i]);
        return builder.endVector();
    }

    public static void startImageVector(FlatBufferBuilder builder, int numElems) {
        builder.startVector(1, numElems, 1);
    }

    public static int endProfile(FlatBufferBuilder builder) {
        int o = builder.endObject();
        return o;
    }

    public void __init(int _i, ByteBuffer _bb) {
        bb_pos = _i;
        bb = _bb;
    }

    public Profile __assign(int _i, ByteBuffer _bb) {
        __init(_i, _bb);
        return this;
    }

    public String name() {
        int o = __offset(4);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer nameAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public ByteBuffer nameInByteBuffer(ByteBuffer _bb) {
        return __vector_in_bytebuffer(_bb, 4, 1);
    }

    public int image(int j) {
        int o = __offset(6);
        return o != 0 ? bb.get(__vector(o) + j * 1) & 0xFF : 0;
    }

    public int imageLength() {
        int o = __offset(6);
        return o != 0 ? __vector_len(o) : 0;
    }

    public ByteBuffer imageAsByteBuffer() {
        return __vector_as_bytebuffer(6, 1);
    }

    public ByteBuffer imageInByteBuffer(ByteBuffer _bb) {
        return __vector_in_bytebuffer(_bb, 6, 1);
    }
}

