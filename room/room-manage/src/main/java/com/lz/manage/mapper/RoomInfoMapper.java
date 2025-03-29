package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.RoomInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 酒店房间信息Mapper接口
 * 
 * @author YY
 * @date 2025-03-29
 */
public interface RoomInfoMapper extends BaseMapper<RoomInfo>
{
    /**
     * 查询酒店房间信息
     * 
     * @param roomId 酒店房间信息主键
     * @return 酒店房间信息
     */
    public RoomInfo selectRoomInfoByRoomId(Long roomId);

    /**
     * 查询酒店房间信息列表
     * 
     * @param roomInfo 酒店房间信息
     * @return 酒店房间信息集合
     */
    public List<RoomInfo> selectRoomInfoList(RoomInfo roomInfo);

    /**
     * 新增酒店房间信息
     * 
     * @param roomInfo 酒店房间信息
     * @return 结果
     */
    public int insertRoomInfo(RoomInfo roomInfo);

    /**
     * 修改酒店房间信息
     * 
     * @param roomInfo 酒店房间信息
     * @return 结果
     */
    public int updateRoomInfo(RoomInfo roomInfo);

    /**
     * 删除酒店房间信息
     * 
     * @param roomId 酒店房间信息主键
     * @return 结果
     */
    public int deleteRoomInfoByRoomId(Long roomId);

    /**
     * 批量删除酒店房间信息
     * 
     * @param roomIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRoomInfoByRoomIds(Long[] roomIds);
}
