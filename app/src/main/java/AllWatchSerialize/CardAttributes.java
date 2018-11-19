// automatically generated by the FlatBuffers compiler, do not modify

package AllWatchSerialize;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class CardAttributes extends Table {
    public static CardAttributes getRootAsCardAttributes(ByteBuffer _bb) {
        return getRootAsCardAttributes(_bb, new CardAttributes());
    }

    public static CardAttributes getRootAsCardAttributes(ByteBuffer _bb, CardAttributes obj) {
        _bb.order(ByteOrder.LITTLE_ENDIAN);
        return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
    }

    public static int createCardAttributes(FlatBufferBuilder builder,
                                           int titleOffset,
                                           int imageOffset,
                                           int avatarOffset,
                                           int actionsOffset) {
        builder.startObject(4);
        CardAttributes.addActions(builder, actionsOffset);
        CardAttributes.addAvatar(builder, avatarOffset);
        CardAttributes.addImage(builder, imageOffset);
        CardAttributes.addTitle(builder, titleOffset);
        return CardAttributes.endCardAttributes(builder);
    }

    public static void startCardAttributes(FlatBufferBuilder builder) {
        builder.startObject(4);
    }

    public static void addTitle(FlatBufferBuilder builder, int titleOffset) {
        builder.addOffset(0, titleOffset, 0);
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

    public static void addAvatar(FlatBufferBuilder builder, int avatarOffset) {
        builder.addOffset(2, avatarOffset, 0);
    }

    public static int createAvatarVector(FlatBufferBuilder builder, byte[] data) {
        builder.startVector(1, data.length, 1);
        for (int i = data.length - 1; i >= 0; i--) builder.addByte(data[i]);
        return builder.endVector();
    }

    public static void startAvatarVector(FlatBufferBuilder builder, int numElems) {
        builder.startVector(1, numElems, 1);
    }

    public static void addActions(FlatBufferBuilder builder, int actionsOffset) {
        builder.addOffset(3, actionsOffset, 0);
    }

    public static int createActionsVector(FlatBufferBuilder builder, int[] data) {
        builder.startVector(4, data.length, 4);
        for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]);
        return builder.endVector();
    }

    public static void startActionsVector(FlatBufferBuilder builder, int numElems) {
        builder.startVector(4, numElems, 4);
    }

    public static int endCardAttributes(FlatBufferBuilder builder) {
        int o = builder.endObject();
        return o;
    }

    public void __init(int _i, ByteBuffer _bb) {
        bb_pos = _i;
        bb = _bb;
    }

    public CardAttributes __assign(int _i, ByteBuffer _bb) {
        __init(_i, _bb);
        return this;
    }

    public String title() {
        int o = __offset(4);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer titleAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public ByteBuffer titleInByteBuffer(ByteBuffer _bb) {
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

    public int avatar(int j) {
        int o = __offset(8);
        return o != 0 ? bb.get(__vector(o) + j * 1) & 0xFF : 0;
    }

    public int avatarLength() {
        int o = __offset(8);
        return o != 0 ? __vector_len(o) : 0;
    }

    public ByteBuffer avatarAsByteBuffer() {
        return __vector_as_bytebuffer(8, 1);
    }

    public ByteBuffer avatarInByteBuffer(ByteBuffer _bb) {
        return __vector_in_bytebuffer(_bb, 8, 1);
    }

    public Action actions(int j) {
        return actions(new Action(), j);
    }

    public Action actions(Action obj, int j) {
        int o = __offset(10);
        return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null;
    }

    public int actionsLength() {
        int o = __offset(10);
        return o != 0 ? __vector_len(o) : 0;
    }
}

