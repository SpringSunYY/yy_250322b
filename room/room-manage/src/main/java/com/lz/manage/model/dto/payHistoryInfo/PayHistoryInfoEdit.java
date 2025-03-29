package com.lz.manage.model.dto.payHistoryInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.PayHistoryInfo;
/**
 * 支付记录Vo对象 tb_pay_history_info
 *
 * @author YY
 * @date 2025-03-29
 */
@Data
public class PayHistoryInfoEdit implements Serializable
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

    /** 实际支付金额 */
    private BigDecimal payPrice;

    /** 支付凭证 */
    private String payVoucher;

    /** 审核状态（0待审核 1同意 2拒绝） */
    private Long auditStatus;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param payHistoryInfoEdit 编辑对象
     * @return PayHistoryInfo
     */
    public static PayHistoryInfo editToObj(PayHistoryInfoEdit payHistoryInfoEdit) {
        if (payHistoryInfoEdit == null) {
            return null;
        }
        PayHistoryInfo payHistoryInfo = new PayHistoryInfo();
        BeanUtils.copyProperties(payHistoryInfoEdit, payHistoryInfo);
        return payHistoryInfo;
    }
}
