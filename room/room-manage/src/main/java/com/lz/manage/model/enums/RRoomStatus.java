package com.lz.manage.model.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 房间状态枚举
 */
public enum RRoomStatus {
    ROOM_STATUS_0("0", "空闲"),
    ROOM_STATUS_1("1", "入住中"),
    ROOM_STATUS_2("2", "下架");

    private static final Map<String, RRoomStatus> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (RRoomStatus item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    RRoomStatus(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    /**
     * 根据 value 获取对应的枚举
     *
     * @param value 枚举的值
     * @return 对应的枚举对象，如果没有找到则返回 Optional.empty()
     */
    public static Optional<RRoomStatus> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
