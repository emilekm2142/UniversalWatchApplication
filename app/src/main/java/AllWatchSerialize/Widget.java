// automatically generated by the FlatBuffers compiler, do not modify

package AllWatchSerialize;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class Widget extends Table {
    public static Widget getRootAsWidget(ByteBuffer _bb) {
        return getRootAsWidget(_bb, new Widget());
    }

    public static Widget getRootAsWidget(ByteBuffer _bb, Widget obj) {
        _bb.order(ByteOrder.LITTLE_ENDIAN);
        return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
    }

    public static int createWidget(FlatBufferBuilder builder,
                                   long refresh,
                                   int templateOffset,
                                   int idOffset) {
        builder.startObject(3);
        Widget.addRefresh(builder, refresh);
        Widget.addId(builder, idOffset);
        Widget.addTemplate(builder, templateOffset);
        return Widget.endWidget(builder);
    }

    public static void startWidget(FlatBufferBuilder builder) {
        builder.startObject(3);
    }

    public static void addRefresh(FlatBufferBuilder builder, long refresh) {
        builder.addLong(0, refresh, 0L);
    }

    public static void addTemplate(FlatBufferBuilder builder, int templateOffset) {
        builder.addOffset(1, templateOffset, 0);
    }

    public static void addId(FlatBufferBuilder builder, int idOffset) {
        builder.addOffset(2, idOffset, 0);
    }

    public static int endWidget(FlatBufferBuilder builder) {
        int o = builder.endObject();
        return o;
    }

    public void __init(int _i, ByteBuffer _bb) {
        bb_pos = _i;
        bb = _bb;
    }

    public Widget __assign(int _i, ByteBuffer _bb) {
        __init(_i, _bb);
        return this;
    }

    public long refresh() {
        int o = __offset(4);
        return o != 0 ? bb.getLong(o + bb_pos) : 0L;
    }

    public TemplateRoot template() {
        return template(new TemplateRoot());
    }

    public TemplateRoot template(TemplateRoot obj) {
        int o = __offset(6);
        return o != 0 ? obj.__assign(__indirect(o + bb_pos), bb) : null;
    }

    public Id id() {
        return id(new Id());
    }

    public Id id(Id obj) {
        int o = __offset(8);
        return o != 0 ? obj.__assign(__indirect(o + bb_pos), bb) : null;
    }
}
