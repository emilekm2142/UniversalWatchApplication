// automatically generated by the FlatBuffers compiler, do not modify

package AllWatchSerialize;

public final class CommandType {
    public static final byte NONE = 0;
    public static final byte Handshake = 1;
    public static final byte Application = 2;
    public static final byte View = 3;
    public static final byte Action = 4;
    public static final byte Open = 5;
    public static final byte Back = 6;
    public static final byte Update = 7;
    public static final String[] names = {"NONE", "Handshake", "Application", "View", "Action", "Open", "Back", "Update",};

    private CommandType() {
    }

    public static String name(int e) {
        return names[e];
    }
}

