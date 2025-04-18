package com.lz.manage.model.vo.payHistoryInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.PayHistoryInfo;
/**
 * 支付记录Vo对象 tb_pay_history_info
 *
 * @author YY
 * @date 2025-03-29
 */
@Data
public class PayHistoryInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 支付记录编号 */
    @Excel(name = "支付记录编号")
    private Long historyId;

    /** 预定编号 */
    @Excel(name = "预定编号")
    private Long reserveId;

    /** 房间编号 */
    @Excel(name = "房间编号")
    private String roomName;
    private Long roomId;

    /** 用户 */
    @Excel(name = "用户")
    private String userName;
    private Long userId;

    /** 实际支付金额 */
    @Excel(name = "实际支付金额")
    private BigDecimal payPrice;

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


     /**
     * 对象转封装类
     *
     * @param payHistoryInfo PayHistoryInfo实体对象
     * @return PayHistoryInfoVo
     */
    public static PayHistoryInfoVo objToVo(PayHistoryInfo payHistoryInfo) {
        if (payHistoryInfo == null) {
            return null;
        }
        PayHistoryInfoVo payHistoryInfoVo = new PayHistoryInfoVo();
        BeanUtils.copyProperties(payHistoryInfo, payHistoryInfoVo);
        return payHistoryInfoVo;
    }
}
