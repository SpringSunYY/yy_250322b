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
 * 酒店房间信息对象 tb_room_info
 *
 * @author YY
 * @date 2025-03-29
 */
@TableName("tb_room_info")
@Data
public class RoomInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 房间编号 */
    @Excel(name = "房间编号")
    @TableId(value = "room_id", type = IdType.ASSIGN_ID)
    private Long roomId;

    /** 房间名称 */
    @Excel(name = "房间名称")
    private String roomName;

    /** 房间价格 */
    @Excel(name = "房间价格")
    private BigDecimal roomPrice;

    /** 房间描述 */
    @Excel(name = "房间描述")
    private String roomDesc;

    /** 房间图片 */
    @Excel(name = "房间图片")
    private String roomImage;

    /** 房间状态（0空闲 1入住中 2下架） */
    @Excel(name = "房间状态", readConverterExp = "0=空闲,1=入住中,2=下架")
    private Long roomStatus;

    /** 订房次数 */
    @Excel(name = "订房次数")
    private Long reserveNum;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long userId;

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
