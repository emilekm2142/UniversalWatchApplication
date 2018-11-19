// automatically generated by the FlatBuffers compiler, do not modify

package AllWatchSerialize;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class Handshake extends Table {
    public static Handshake getRootAsHandshake(ByteBuffer _bb) {
        return getRootAsHandshake(_bb, new Handshake());
    }

    public static Handshake getRootAsHandshake(ByteBuffer _bb, Handshake obj) {
        _bb.order(ByteOrder.LITTLE_ENDIAN);
        return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
    }

    public static int createHandshake(FlatBufferBuilder builder,
                                      int manufacturerOffset,
                                      int modelNameOffset,
                                      int extraModelInfoOffset,
                                      int firmwareVersionOffset,
                                      int bufferKbSize,
                                      boolean usesEncryption,
                                      int requirementsOffset) {
        builder.startObject(7);
        Handshake.addRequirements(builder, requirementsOffset);
        Handshake.addFirmwareVersion(builder, firmwareVersionOffset);
        Handshake.addExtraModelInfo(builder, extraModelInfoOffset);
        Handshake.addModelName(builder, modelNameOffset);
        Handshake.addManufacturer(builder, manufacturerOffset);
        Handshake.addBufferKbSize(builder, bufferKbSize);
        Handshake.addUsesEncryption(builder, usesEncryption);
        return Handshake.endHandshake(builder);
    }

    public static void startHandshake(FlatBufferBuilder builder) {
        builder.startObject(7);
    }

    public static void addManufacturer(FlatBufferBuilder builder, int manufacturerOffset) {
        builder.addOffset(0, manufacturerOffset, 0);
    }

    public static void addModelName(FlatBufferBuilder builder, int modelNameOffset) {
        builder.addOffset(1, modelNameOffset, 0);
    }

    public static void addExtraModelInfo(FlatBufferBuilder builder, int extraModelInfoOffset) {
        builder.addOffset(2, extraModelInfoOffset, 0);
    }

    public static void addFirmwareVersion(FlatBufferBuilder builder, int firmwareVersionOffset) {
        builder.addOffset(3, firmwareVersionOffset, 0);
    }

    public static void addBufferKbSize(FlatBufferBuilder builder, int bufferKbSize) {
        builder.addShort(4, (short) bufferKbSize, (short) 0);
    }

    public static void addUsesEncryption(FlatBufferBuilder builder, boolean usesEncryption) {
        builder.addBoolean(5, usesEncryption, false);
    }

    public static void addRequirements(FlatBufferBuilder builder, int requirementsOffset) {
        builder.addOffset(6, requirementsOffset, 0);
    }

    public static int createRequirementsVector(FlatBufferBuilder builder, short[] data) {
        builder.startVector(2, data.length, 2);
        for (int i = data.length - 1; i >= 0; i--) builder.addShort(data[i]);
        return builder.endVector();
    }

    public static void startRequirementsVector(FlatBufferBuilder builder, int numElems) {
        builder.startVector(2, numElems, 2);
    }

    public static int endHandshake(FlatBufferBuilder builder) {
        int o = builder.endObject();
        return o;
    }

    public void __init(int _i, ByteBuffer _bb) {
        bb_pos = _i;
        bb = _bb;
    }

    public Handshake __assign(int _i, ByteBuffer _bb) {
        __init(_i, _bb);
        return this;
    }

    public String manufacturer() {
        int o = __offset(4);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer manufacturerAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public ByteBuffer manufacturerInByteBuffer(ByteBuffer _bb) {
        return __vector_in_bytebuffer(_bb, 4, 1);
    }

    public String modelName() {
        int o = __offset(6);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer modelNameAsByteBuffer() {
        return __vector_as_bytebuffer(6, 1);
    }

    public ByteBuffer modelNameInByteBuffer(ByteBuffer _bb) {
        return __vector_in_bytebuffer(_bb, 6, 1);
    }

    public String extraModelInfo() {
        int o = __offset(8);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer extraModelInfoAsByteBuffer() {
        return __vector_as_bytebuffer(8, 1);
    }

    public ByteBuffer extraModelInfoInByteBuffer(ByteBuffer _bb) {
        return __vector_in_bytebuffer(_bb, 8, 1);
    }

    public String firmwareVersion() {
        int o = __offset(10);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer firmwareVersionAsByteBuffer() {
        return __vector_as_bytebuffer(10, 1);
    }

    public ByteBuffer firmwareVersionInByteBuffer(ByteBuffer _bb) {
        return __vector_in_bytebuffer(_bb, 10, 1);
    }

    public int bufferKbSize() {
        int o = __offset(12);
        return o != 0 ? bb.getShort(o + bb_pos) & 0xFFFF : 0;
    }

    public boolean usesEncryption() {
        int o = __offset(14);
        return o != 0 && 0 != bb.get(o + bb_pos);
    }

    public short requirements(int j) {
        int o = __offset(16);
        return o != 0 ? bb.getShort(__vector(o) + j * 2) : 0;
    }

    public int requirementsLength() {
        int o = __offset(16);
        return o != 0 ? __vector_len(o) : 0;
    }

    public ByteBuffer requirementsAsByteBuffer() {
        return __vector_as_bytebuffer(16, 2);
    }

    public ByteBuffer requirementsInByteBuffer(ByteBuffer _bb) {
        return __vector_in_bytebuffer(_bb, 16, 2);
    }
}
