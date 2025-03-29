package com.lz.manage.model.dto.roomInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.RoomInfo;
/**
 * 酒店房间信息Vo对象 tb_room_info
 *
 * @author YY
 * @date 2025-03-29
 */
@Data
public class RoomInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 房间编号 */
    private Long roomId;

    /** 房间名称 */
    private String roomName;

    /** 房间价格 */
    private BigDecimal roomPrice;

    /** 房间描述 */
    private String roomDesc;

    /** 房间图片 */
    private String roomImage;

    /** 房间状态（0空闲 1入住中 2下架） */
    private Long roomStatus;

    /** 订房次数 */
    private Long reserveNum;

    /** 创建人 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param roomInfoEdit 编辑对象
     * @return RoomInfo
     */
    public static RoomInfo editToObj(RoomInfoEdit roomInfoEdit) {
        if (roomInfoEdit == null) {
            return null;
        }
        RoomInfo roomInfo = new RoomInfo();
        BeanUtils.copyProperties(roomInfoEdit, roomInfo);
        return roomInfo;
    }
}
