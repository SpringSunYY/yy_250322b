package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.ReserveRoomHistoryInfo;
import com.lz.manage.model.vo.reserveRoomHistoryInfo.ReserveRoomHistoryInfoVo;
import com.lz.manage.model.dto.reserveRoomHistoryInfo.ReserveRoomHistoryInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 订房记录Service接口
 * 
 * @author YY
 * @date 2025-03-29
 */
public interface IReserveRoomHistoryInfoService extends IService<ReserveRoomHistoryInfo>
{
    //region mybatis代码
    /**
     * 查询订房记录
     * 
     * @param historyId 订房记录主键
     * @return 订房记录
     */
    public ReserveRoomHistoryInfo selectReserveRoomHistoryInfoByHistoryId(Long historyId);

    /**
     * 查询订房记录列表
     * 
     * @param reserveRoomHistoryInfo 订房记录
     * @return 订房记录集合
     */
    public List<ReserveRoomHistoryInfo> selectReserveRoomHistoryInfoList(ReserveRoomHistoryInfo reserveRoomHistoryInfo);

    /**
     * 新增订房记录
     * 
     * @param reserveRoomHistoryInfo 订房记录
     * @return 结果
     */
    public int insertReserveRoomHistoryInfo(ReserveRoomHistoryInfo reserveRoomHistoryInfo);

    /**
     * 修改订房记录
     * 
     * @param reserveRoomHistoryInfo 订房记录
     * @return 结果
     */
    public int updateReserveRoomHistoryInfo(ReserveRoomHistoryInfo reserveRoomHistoryInfo);

    /**
     * 批量删除订房记录
     * 
     * @param historyIds 需要删除的订房记录主键集合
     * @return 结果
     */
    public int deleteReserveRoomHistoryInfoByHistoryIds(Long[] historyIds);

    /**
     * 删除订房记录信息
     * 
     * @param historyId 订房记录主键
     * @return 结果
     */
    public int deleteReserveRoomHistoryInfoByHistoryId(Long historyId);
    //endregion
    /**
     * 获取查询条件
     *
     * @param reserveRoomHistoryInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<ReserveRoomHistoryInfo> getQueryWrapper(ReserveRoomHistoryInfoQuery reserveRoomHistoryInfoQuery);

    /**
     * 转换vo
     *
     * @param reserveRoomHistoryInfoList ReserveRoomHistoryInfo集合
     * @return ReserveRoomHistoryInfoVO集合
     */
    List<ReserveRoomHistoryInfoVo> convertVoList(List<ReserveRoomHistoryInfo> reserveRoomHistoryInfoList);
}
