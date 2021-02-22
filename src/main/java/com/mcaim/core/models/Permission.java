package com.mcaim.core.models;

/**
 * Designed for permission checks
 */
public class Permission {
    private static final String PREFIX = "mcaim.";

    // No permission
    public static final String NONE = null;

    // Ranked permissions (generic)
    public static final String RANK_1 = PREFIX + "rank_1";
    public static final String RANK_2 = PREFIX + "rank_2";
    public static final String RANK_3 = PREFIX + "rank_3";
    public static final String RANK_4 = PREFIX + "rank_4";
    public static final String RANK_5 = PREFIX + "rank_5";

    // Staff permissions
    public static final String HELPER = PREFIX + "helper";
    public static final String MOD = PREFIX + "mod";
    public static final String SMOD = PREFIX + "smod";
    public static final String ADMIN = PREFIX + "admin";
    public static final String DEV = PREFIX + "dev";
    public static final String OWNER = PREFIX + "owner";

    // Operator permission
    public static final String OP = PREFIX + "op";
}
