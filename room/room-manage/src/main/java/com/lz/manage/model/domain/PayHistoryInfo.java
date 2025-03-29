package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 支付记录对象 tb_pay_history_info
 *
 * @author YY
 * @date 2025-03-29
 */
@TableName("tb_pay_history_info")
@Data
public class PayHistoryInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付记录编号
     */
    @Excel(name = "支付记录编号")
    @TableId(value = "history_id", type = IdType.ASSIGN_ID)
    private Long historyId;

    /**
     * 预定编号
     */
    @Excel(name = "预定编号")
    private Long reserveId;

    /**
     * 房间编号
     */
    @Excel(name = "房间编号")
    @TableField(exist = false)
    private String roomName;
    private Long roomId;

    /**
     * 用户
     */
    @Excel(name = "用户")
    @TableField(exist = false)
    private String userName;
    private Long userId;

    /**
     * 实际支付金额
     */
    @Excel(name = "实际支付金额")
    private BigDecimal payPrice;

    /**
     * 支付凭证
     */
    @Excel(name = "支付凭证")
    private String payVoucher;

    /**
     * 审核状态（0待审核 1同意 2拒绝）
     */
    @Excel(name = "审核状态", readConverterExp = "0=待审核,1=同意,2=拒绝")
    private Long auditStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
