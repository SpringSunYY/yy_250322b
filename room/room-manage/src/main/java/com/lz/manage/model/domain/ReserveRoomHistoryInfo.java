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
 * 订房记录对象 tb_reserve_room_history_info
 *
 * @author YY
 * @date 2025-03-29
 */
@TableName("tb_reserve_room_history_info")
@Data
public class ReserveRoomHistoryInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 记录编号 */
    @Excel(name = "记录编号")
    @TableId(value = "history_id", type = IdType.ASSIGN_ID)
    private Long historyId;

    /** 房间编号 */
    @Excel(name = "房间编号")
    @TableField(exist = false)
    private String roomName;
    private Long roomId;

    /** 用户 */
    @Excel(name = "用户")
    @TableField(exist = false)
    private String userName;
    private Long userId;

    /** 订房天数 */
    @Excel(name = "订房天数")
    private Long dayNum;

    /** 订单总价 */
    @Excel(name = "订单总价")
    private BigDecimal totalPrice;

    /** 状态（0待支付 1入住中 2已退房 3已结束） */
    @Excel(name = "状态", readConverterExp = "0=待支付,1=入住中,2=已退房,3=已结束")
    private Long historyStatus;

    /** 订房时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订房时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reserveTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
