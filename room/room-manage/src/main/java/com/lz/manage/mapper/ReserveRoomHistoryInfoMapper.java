package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.ReserveRoomHistoryInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 订房记录Mapper接口
 * 
 * @author YY
 * @date 2025-03-29
 */
public interface ReserveRoomHistoryInfoMapper extends BaseMapper<ReserveRoomHistoryInfo>
{
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
     * 删除订房记录
     * 
     * @param historyId 订房记录主键
     * @return 结果
     */
    public int deleteReserveRoomHistoryInfoByHistoryId(Long historyId);

    /**
     * 批量删除订房记录
     * 
     * @param historyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReserveRoomHistoryInfoByHistoryIds(Long[] historyIds);
}
