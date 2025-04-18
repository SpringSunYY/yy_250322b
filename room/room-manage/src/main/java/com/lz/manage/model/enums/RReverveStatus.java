package com.lz.manage.model.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 预约状态枚举
 */
public enum RReverveStatus {
    REVERVE_STATUS_0("0", "待支付"),
    REVERVE_STATUS_1("1", "入住中"),
    REVERVE_STATUS_2("2", "已退房"),
    REVERVE_STATUS_3("3", "已结束"),
    REVERVE_STATUS_4("4", "未支付过期");

    private static final Map<String, RReverveStatus> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (RReverveStatus item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    RReverveStatus(String value, String label) {
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
    public static Optional<RReverveStatus> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
