package com.lz.manage.model.vo.reserveRoomHistoryInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.ReserveRoomHistoryInfo;

/**
 * 订房记录Vo对象 tb_reserve_room_history_info
 *
 * @author YY
 * @date 2025-03-29
 */
@Data
public class ReserveRoomHistoryInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 记录编号
     */
    @Excel(name = "记录编号")
    private Long historyId;

    /**
     * 房间编号
     */
    @Excel(name = "房间编号")
    private Long roomId;
    private String roomName;

    /**
     * 用户
     */
    @Excel(name = "用户")
    private Long userId;
    private String userName;

    /**
     * 订房天数
     */
    @Excel(name = "订房天数")
    private Long dayNum;

    /**
     * 订单总价
     */
    @Excel(name = "订单总价")
    private BigDecimal totalPrice;


    /**
     * 当前余额
     */
    @Excel(name = "当前余额")
    private BigDecimal balance;

    /**
     * 状态（0待支付 1入住中 2已退房 3已结束）
     */
    @Excel(name = "状态", readConverterExp = "0=待支付,1=入住中,2=已退房,3=已结束")
    private Long historyStatus;

    /**
     * 订房时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订房时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reserveTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

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
     * 对象转封装类
     *
     * @param reserveRoomHistoryInfo ReserveRoomHistoryInfo实体对象
     * @return ReserveRoomHistoryInfoVo
     */
    public static ReserveRoomHistoryInfoVo objToVo(ReserveRoomHistoryInfo reserveRoomHistoryInfo) {
        if (reserveRoomHistoryInfo == null) {
            return null;
        }
        ReserveRoomHistoryInfoVo reserveRoomHistoryInfoVo = new ReserveRoomHistoryInfoVo();
        BeanUtils.copyProperties(reserveRoomHistoryInfo, reserveRoomHistoryInfoVo);
        return reserveRoomHistoryInfoVo;
    }
}
