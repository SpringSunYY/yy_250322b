package com.lz.manage.model.dto.roomInfo;

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
import com.lz.manage.model.domain.RoomInfo;
/**
 * 酒店房间信息Query对象 tb_room_info
 *
 * @author YY
 * @date 2025-03-29
 */
@Data
public class RoomInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 房间编号 */
    private Long roomId;

    /** 房间名称 */
    private String roomName;

    /** 房间状态（0空闲 1入住中 2下架） */
    private Long roomStatus;

    /** 创建人 */
    private Long userId;

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
     * @param roomInfoQuery 查询对象
     * @return RoomInfo
     */
    public static RoomInfo queryToObj(RoomInfoQuery roomInfoQuery) {
        if (roomInfoQuery == null) {
            return null;
        }
        RoomInfo roomInfo = new RoomInfo();
        BeanUtils.copyProperties(roomInfoQuery, roomInfo);
        return roomInfo;
    }
}
