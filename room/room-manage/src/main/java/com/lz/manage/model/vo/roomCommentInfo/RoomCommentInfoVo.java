package com.lz.manage.model.vo.roomCommentInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.RoomCommentInfo;
/**
 * 房间评价Vo对象 tb_room_comment_info
 *
 * @author YY
 * @date 2025-04-17
 */
@Data
public class RoomCommentInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 评论ID */
    @Excel(name = "评论ID")
    private Long id;

    /** 房间 */
    @Excel(name = "房间")
    private Long roomId;

    /** 评论用户 */
    @Excel(name = "评论用户")
    private Long userId;

    /** 评分 */
    @Excel(name = "评分")
    private Long score;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String content;

    /** 评论图片 */
    @Excel(name = "评论图片")
    private String imageUrls;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param roomCommentInfo RoomCommentInfo实体对象
     * @return RoomCommentInfoVo
     */
    public static RoomCommentInfoVo objToVo(RoomCommentInfo roomCommentInfo) {
        if (roomCommentInfo == null) {
            return null;
        }
        RoomCommentInfoVo roomCommentInfoVo = new RoomCommentInfoVo();
        BeanUtils.copyProperties(roomCommentInfo, roomCommentInfoVo);
        return roomCommentInfoVo;
    }
}
