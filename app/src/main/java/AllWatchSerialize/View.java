// automatically generated by the FlatBuffers compiler, do not modify

package AllWatchSerialize;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class View extends Table {
    public static View getRootAsView(ByteBuffer _bb) {
        return getRootAsView(_bb, new View());
    }

    public static View getRootAsView(ByteBuffer _bb, View obj) {
        _bb.order(ByteOrder.LITTLE_ENDIAN);
        return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
    }

    public static int createView(FlatBufferBuilder builder,
                                 int idOffset,
                                 int nameOffset,
                                 short datatype,
                                 boolean usesTemplate,
                                 int templateOffset,
                                 byte data_type,
                                 int dataOffset,
                                 int actionsOffset,
                                 short defaultStyle) {
        builder.startObject(9);
        View.addActions(builder, actionsOffset);
        View.addData(builder, dataOffset);
        View.addTemplate(builder, templateOffset);
        View.addName(builder, nameOffset);
        View.addId(builder, idOffset);
        View.addDefaultStyle(builder, defaultStyle);
        View.addDatatype(builder, datatype);
        View.addDataType(builder, data_type);
        View.addUsesTemplate(builder, usesTemplate);
        return View.endView(builder);
    }

    public static void startView(FlatBufferBuilder builder) {
        builder.startObject(9);
    }

    public static void addId(FlatBufferBuilder builder, int idOffset) {
        builder.addOffset(0, idOffset, 0);
    }

    public static void addName(FlatBufferBuilder builder, int nameOffset) {
        builder.addOffset(1, nameOffset, 0);
    }

    public static void addDatatype(FlatBufferBuilder builder, short datatype) {
        builder.addShort(2, datatype, 0);
    }

    public static void addUsesTemplate(FlatBufferBuilder builder, boolean usesTemplate) {
        builder.addBoolean(3, usesTemplate, false);
    }

    public static void addTemplate(FlatBufferBuilder builder, int templateOffset) {
        builder.addOffset(4, templateOffset, 0);
    }

    public static void addDataType(FlatBufferBuilder builder, byte dataType) {
        builder.addByte(5, dataType, 0);
    }

    public static void addData(FlatBufferBuilder builder, int dataOffset) {
        builder.addOffset(6, dataOffset, 0);
    }

    public static void addActions(FlatBufferBuilder builder, int actionsOffset) {
        builder.addOffset(7, actionsOffset, 0);
    }

    public static int createActionsVector(FlatBufferBuilder builder, int[] data) {
        builder.startVector(4, data.length, 4);
        for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]);
        return builder.endVector();
    }

    public static void startActionsVector(FlatBufferBuilder builder, int numElems) {
        builder.startVector(4, numElems, 4);
    }

    public static void addDefaultStyle(FlatBufferBuilder builder, short defaultStyle) {
        builder.addShort(8, defaultStyle, 0);
    }

    public static int endView(FlatBufferBuilder builder) {
        int o = builder.endObject();
        return o;
    }

    public void __init(int _i, ByteBuffer _bb) {
        bb_pos = _i;
        bb = _bb;
    }

    public View __assign(int _i, ByteBuffer _bb) {
        __init(_i, _bb);
        return this;
    }

    public Id id() {
        return id(new Id());
    }

    public Id id(Id obj) {
        int o = __offset(4);
        return o != 0 ? obj.__assign(__indirect(o + bb_pos), bb) : null;
    }

    public String name() {
        int o = __offset(6);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer nameAsByteBuffer() {
        return __vector_as_bytebuffer(6, 1);
    }

    public ByteBuffer nameInByteBuffer(ByteBuffer _bb) {
        return __vector_in_bytebuffer(_bb, 6, 1);
    }

    public short datatype() {
        int o = __offset(8);
        return o != 0 ? bb.getShort(o + bb_pos) : 0;
    }

    public boolean usesTemplate() {
        int o = __offset(10);
        return o != 0 && 0 != bb.get(o + bb_pos);
    }

    public TemplateRoot template() {
        return template(new TemplateRoot());
    }

    public TemplateRoot template(TemplateRoot obj) {
        int o = __offset(12);
        return o != 0 ? obj.__assign(__indirect(o + bb_pos), bb) : null;
    }

    public byte dataType() {
        int o = __offset(14);
        return o != 0 ? bb.get(o + bb_pos) : 0;
    }

    public Table data(Table obj) {
        int o = __offset(16);
        return o != 0 ? __union(obj, o) : null;
    }

    public Action actions(int j) {
        return actions(new Action(), j);
    }

    public Action actions(Action obj, int j) {
        int o = __offset(18);
        return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null;
    }

    public int actionsLength() {
        int o = __offset(18);
        return o != 0 ? __vector_len(o) : 0;
    }

    public short defaultStyle() {
        int o = __offset(20);
        return o != 0 ? bb.getShort(o + bb_pos) : 0;
    }
}

