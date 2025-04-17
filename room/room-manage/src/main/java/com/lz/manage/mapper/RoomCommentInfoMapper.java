package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.RoomCommentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 房间评价Mapper接口
 * 
 * @author YY
 * @date 2025-04-17
 */
public interface RoomCommentInfoMapper extends BaseMapper<RoomCommentInfo>
{
    /**
     * 查询房间评价
     * 
     * @param id 房间评价主键
     * @return 房间评价
     */
    public RoomCommentInfo selectRoomCommentInfoById(Long id);

    /**
     * 查询房间评价列表
     * 
     * @param roomCommentInfo 房间评价
     * @return 房间评价集合
     */
    public List<RoomCommentInfo> selectRoomCommentInfoList(RoomCommentInfo roomCommentInfo);

    /**
     * 新增房间评价
     * 
     * @param roomCommentInfo 房间评价
     * @return 结果
     */
    public int insertRoomCommentInfo(RoomCommentInfo roomCommentInfo);

    /**
     * 修改房间评价
     * 
     * @param roomCommentInfo 房间评价
     * @return 结果
     */
    public int updateRoomCommentInfo(RoomCommentInfo roomCommentInfo);

    /**
     * 删除房间评价
     * 
     * @param id 房间评价主键
     * @return 结果
     */
    public int deleteRoomCommentInfoById(Long id);

    /**
     * 批量删除房间评价
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRoomCommentInfoByIds(Long[] ids);
}
