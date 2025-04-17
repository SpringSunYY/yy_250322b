package com.lz.manage.model.dto.roomCommentInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.RoomCommentInfo;
/**
 * 房间评价Query对象 tb_room_comment_info
 *
 * @author YY
 * @date 2025-04-17
 */
@Data
public class RoomCommentInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 评论ID */
    private Long id;

    /** 房间 */
    private Long roomId;

    /** 评论用户 */
    private Long userId;

    /** 评分 */
    private Long score;

    /** 评论内容 */
    private String content;

    /** 评论图片 */
    private String imageUrls;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

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
     * @param roomCommentInfoQuery 查询对象
     * @return RoomCommentInfo
     */
    public static RoomCommentInfo queryToObj(RoomCommentInfoQuery roomCommentInfoQuery) {
        if (roomCommentInfoQuery == null) {
            return null;
        }
        RoomCommentInfo roomCommentInfo = new RoomCommentInfo();
        BeanUtils.copyProperties(roomCommentInfoQuery, roomCommentInfo);
        return roomCommentInfo;
    }
}
