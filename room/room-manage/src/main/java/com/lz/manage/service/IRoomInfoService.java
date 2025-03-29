package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.RoomInfo;
import com.lz.manage.model.vo.roomInfo.RoomInfoVo;
import com.lz.manage.model.dto.roomInfo.RoomInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 酒店房间信息Service接口
 * 
 * @author YY
 * @date 2025-03-29
 */
public interface IRoomInfoService extends IService<RoomInfo>
{
    //region mybatis代码
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
     * 批量删除酒店房间信息
     * 
     * @param roomIds 需要删除的酒店房间信息主键集合
     * @return 结果
     */
    public int deleteRoomInfoByRoomIds(Long[] roomIds);

    /**
     * 删除酒店房间信息信息
     * 
     * @param roomId 酒店房间信息主键
     * @return 结果
     */
    public int deleteRoomInfoByRoomId(Long roomId);
    //endregion
    /**
     * 获取查询条件
     *
     * @param roomInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<RoomInfo> getQueryWrapper(RoomInfoQuery roomInfoQuery);

    /**
     * 转换vo
     *
     * @param roomInfoList RoomInfo集合
     * @return RoomInfoVO集合
     */
    List<RoomInfoVo> convertVoList(List<RoomInfo> roomInfoList);
}
