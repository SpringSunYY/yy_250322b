package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.RoomCommentInfo;
import com.lz.manage.model.vo.roomCommentInfo.RoomCommentInfoVo;
import com.lz.manage.model.dto.roomCommentInfo.RoomCommentInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 房间评价Service接口
 * 
 * @author YY
 * @date 2025-04-17
 */
public interface IRoomCommentInfoService extends IService<RoomCommentInfo>
{
    //region mybatis代码
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
     * 批量删除房间评价
     * 
     * @param ids 需要删除的房间评价主键集合
     * @return 结果
     */
    public int deleteRoomCommentInfoByIds(Long[] ids);

    /**
     * 删除房间评价信息
     * 
     * @param id 房间评价主键
     * @return 结果
     */
    public int deleteRoomCommentInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param roomCommentInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<RoomCommentInfo> getQueryWrapper(RoomCommentInfoQuery roomCommentInfoQuery);

    /**
     * 转换vo
     *
     * @param roomCommentInfoList RoomCommentInfo集合
     * @return RoomCommentInfoVO集合
     */
    List<RoomCommentInfoVo> convertVoList(List<RoomCommentInfo> roomCommentInfoList);
}
