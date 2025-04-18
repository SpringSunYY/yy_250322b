package com.lz.manage.model.dto.reserveRoomHistoryInfo;

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
import com.lz.manage.model.domain.ReserveRoomHistoryInfo;
/**
 * 订房记录Query对象 tb_reserve_room_history_info
 *
 * @author YY
 * @date 2025-03-29
 */
@Data
public class ReserveRoomHistoryInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 记录编号 */
    private Long historyId;

    /** 房间编号 */
    private Long roomId;

    /** 用户 */
    private Long userId;

    /** 状态（0待支付 1入住中 2已退房 3已结束） */
    private Long historyStatus;

    /** 订房时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reserveTime;
    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

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
     * @param reserveRoomHistoryInfoQuery 查询对象
     * @return ReserveRoomHistoryInfo
     */
    public static ReserveRoomHistoryInfo queryToObj(ReserveRoomHistoryInfoQuery reserveRoomHistoryInfoQuery) {
        if (reserveRoomHistoryInfoQuery == null) {
            return null;
        }
        ReserveRoomHistoryInfo reserveRoomHistoryInfo = new ReserveRoomHistoryInfo();
        BeanUtils.copyProperties(reserveRoomHistoryInfoQuery, reserveRoomHistoryInfo);
        return reserveRoomHistoryInfo;
    }
}
