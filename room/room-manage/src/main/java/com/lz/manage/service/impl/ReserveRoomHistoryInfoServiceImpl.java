package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.ReserveRoomHistoryInfoMapper;
import com.lz.manage.model.domain.ReserveRoomHistoryInfo;
import com.lz.manage.service.IReserveRoomHistoryInfoService;
import com.lz.manage.model.dto.reserveRoomHistoryInfo.ReserveRoomHistoryInfoQuery;
import com.lz.manage.model.vo.reserveRoomHistoryInfo.ReserveRoomHistoryInfoVo;

/**
 * 订房记录Service业务层处理
 * 
 * @author YY
 * @date 2025-03-29
 */
@Service
public class ReserveRoomHistoryInfoServiceImpl extends ServiceImpl<ReserveRoomHistoryInfoMapper, ReserveRoomHistoryInfo> implements IReserveRoomHistoryInfoService
{
    @Resource
    private ReserveRoomHistoryInfoMapper reserveRoomHistoryInfoMapper;

    //region mybatis代码
    /**
     * 查询订房记录
     * 
     * @param historyId 订房记录主键
     * @return 订房记录
     */
    @Override
    public ReserveRoomHistoryInfo selectReserveRoomHistoryInfoByHistoryId(Long historyId)
    {
        return reserveRoomHistoryInfoMapper.selectReserveRoomHistoryInfoByHistoryId(historyId);
    }

    /**
     * 查询订房记录列表
     * 
     * @param reserveRoomHistoryInfo 订房记录
     * @return 订房记录
     */
    @Override
    public List<ReserveRoomHistoryInfo> selectReserveRoomHistoryInfoList(ReserveRoomHistoryInfo reserveRoomHistoryInfo)
    {
        return reserveRoomHistoryInfoMapper.selectReserveRoomHistoryInfoList(reserveRoomHistoryInfo);
    }

    /**
     * 新增订房记录
     * 
     * @param reserveRoomHistoryInfo 订房记录
     * @return 结果
     */
    @Override
    public int insertReserveRoomHistoryInfo(ReserveRoomHistoryInfo reserveRoomHistoryInfo)
    {
        reserveRoomHistoryInfo.setCreateTime(DateUtils.getNowDate());
        return reserveRoomHistoryInfoMapper.insertReserveRoomHistoryInfo(reserveRoomHistoryInfo);
    }

    /**
     * 修改订房记录
     * 
     * @param reserveRoomHistoryInfo 订房记录
     * @return 结果
     */
    @Override
    public int updateReserveRoomHistoryInfo(ReserveRoomHistoryInfo reserveRoomHistoryInfo)
    {
        reserveRoomHistoryInfo.setUpdateTime(DateUtils.getNowDate());
        return reserveRoomHistoryInfoMapper.updateReserveRoomHistoryInfo(reserveRoomHistoryInfo);
    }

    /**
     * 批量删除订房记录
     * 
     * @param historyIds 需要删除的订房记录主键
     * @return 结果
     */
    @Override
    public int deleteReserveRoomHistoryInfoByHistoryIds(Long[] historyIds)
    {
        return reserveRoomHistoryInfoMapper.deleteReserveRoomHistoryInfoByHistoryIds(historyIds);
    }

    /**
     * 删除订房记录信息
     * 
     * @param historyId 订房记录主键
     * @return 结果
     */
    @Override
    public int deleteReserveRoomHistoryInfoByHistoryId(Long historyId)
    {
        return reserveRoomHistoryInfoMapper.deleteReserveRoomHistoryInfoByHistoryId(historyId);
    }
    //endregion
    @Override
    public QueryWrapper<ReserveRoomHistoryInfo> getQueryWrapper(ReserveRoomHistoryInfoQuery reserveRoomHistoryInfoQuery){
        QueryWrapper<ReserveRoomHistoryInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = reserveRoomHistoryInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long historyId = reserveRoomHistoryInfoQuery.getHistoryId();
        queryWrapper.eq( StringUtils.isNotNull(historyId),"history_id",historyId);

        Long roomId = reserveRoomHistoryInfoQuery.getRoomId();
        queryWrapper.eq( StringUtils.isNotNull(roomId),"room_id",roomId);

        Long userId = reserveRoomHistoryInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Long historyStatus = reserveRoomHistoryInfoQuery.getHistoryStatus();
        queryWrapper.eq( StringUtils.isNotNull(historyStatus),"history_status",historyStatus);

        Date reserveTime = reserveRoomHistoryInfoQuery.getReserveTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginReserveTime"))&&StringUtils.isNotNull(params.get("endReserveTime")),"reserve_time",params.get("beginReserveTime"),params.get("endReserveTime"));

        Date createTime = reserveRoomHistoryInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String updateBy = reserveRoomHistoryInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy) ,"update_by",updateBy);

        Date updateTime = reserveRoomHistoryInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<ReserveRoomHistoryInfoVo> convertVoList(List<ReserveRoomHistoryInfo> reserveRoomHistoryInfoList) {
        if (StringUtils.isEmpty(reserveRoomHistoryInfoList)) {
            return Collections.emptyList();
        }
        return reserveRoomHistoryInfoList.stream().map(ReserveRoomHistoryInfoVo::objToVo).collect(Collectors.toList());
    }

}
