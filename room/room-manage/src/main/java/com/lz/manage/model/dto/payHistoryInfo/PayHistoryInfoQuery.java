package com.lz.manage.model.dto.payHistoryInfo;

import java.util.Map;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.PayHistoryInfo;
/**
 * 支付记录Query对象 tb_pay_history_info
 *
 * @author YY
 * @date 2025-03-29
 */
@Data
public class PayHistoryInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 支付记录编号 */
    private Long historyId;

    /** 预定编号 */
    private Long reserveId;

    /** 房间编号 */
    private Long roomId;

    /** 用户 */
    private Long userId;

    /** 审核状态（0待审核 1同意 2拒绝） */
    private Long auditStatus;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param payHistoryInfoQuery 查询对象
     * @return PayHistoryInfo
     */
    public static PayHistoryInfo queryToObj(PayHistoryInfoQuery payHistoryInfoQuery) {
        if (payHistoryInfoQuery == null) {
            return null;
        }
        PayHistoryInfo payHistoryInfo = new PayHistoryInfo();
        BeanUtils.copyProperties(payHistoryInfoQuery, payHistoryInfo);
        return payHistoryInfo;
    }
}
