package com.lz.manage.model.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 支付状态枚举
 */
public enum RPayStatus {
    PAY_STATUS_0("0", "待审核"),
    PAY_STATUS_1("1", "同意"),
    PAY_STATUS_2("2", "拒绝");

    private static final Map<String, RPayStatus> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (RPayStatus item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    RPayStatus(String value, String label) {
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
    public static Optional<RPayStatus> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
