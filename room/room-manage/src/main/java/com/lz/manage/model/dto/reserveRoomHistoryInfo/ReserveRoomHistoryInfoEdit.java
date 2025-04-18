package com.lz.manage.model.dto.reserveRoomHistoryInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ReserveRoomHistoryInfo;
/**
 * 订房记录Vo对象 tb_reserve_room_history_info
 *
 * @author YY
 * @date 2025-03-29
 */
@Data
public class ReserveRoomHistoryInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 记录编号 */
    private Long historyId;

    /** 房间编号 */
    private Long roomId;

    /** 用户 */
    private Long userId;

    /** 订房天数 */
    private Long dayNum;

    /** 订单总价 */
    private BigDecimal totalPrice;

    /** 状态（0待支付 1入住中 2已退房 3已结束） */
    private Long historyStatus;

    /** 订房时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reserveTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param reserveRoomHistoryInfoEdit 编辑对象
     * @return ReserveRoomHistoryInfo
     */
    public static ReserveRoomHistoryInfo editToObj(ReserveRoomHistoryInfoEdit reserveRoomHistoryInfoEdit) {
        if (reserveRoomHistoryInfoEdit == null) {
            return null;
        }
        ReserveRoomHistoryInfo reserveRoomHistoryInfo = new ReserveRoomHistoryInfo();
        BeanUtils.copyProperties(reserveRoomHistoryInfoEdit, reserveRoomHistoryInfo);
        return reserveRoomHistoryInfo;
    }
}
