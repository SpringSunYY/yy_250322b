package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.RoomInfo;
import com.lz.manage.model.enums.RReverveStatus;
import com.lz.manage.model.enums.RRoomStatus;
import com.lz.manage.service.IRoomInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.ReserveRoomHistoryInfoMapper;
import com.lz.manage.model.domain.ReserveRoomHistoryInfo;
import com.lz.manage.service.IReserveRoomHistoryInfoService;
import com.lz.manage.model.dto.reserveRoomHistoryInfo.ReserveRoomHistoryInfoQuery;
import com.lz.manage.model.vo.reserveRoomHistoryInfo.ReserveRoomHistoryInfoVo;

import static com.lz.common.utils.DateUtils.HH_MM_SS;
import static com.lz.common.utils.DateUtils.YYYY_MM_DD;

/**
 * 订房记录Service业务层处理
 *
 * @author YY
 * @date 2025-03-29
 */
@Service
public class ReserveRoomHistoryInfoServiceImpl extends ServiceImpl<ReserveRoomHistoryInfoMapper, ReserveRoomHistoryInfo> implements IReserveRoomHistoryInfoService {
    @Resource
    private ReserveRoomHistoryInfoMapper reserveRoomHistoryInfoMapper;

    @Resource
    private IRoomInfoService roomInfoService;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询订房记录
     *
     * @param historyId 订房记录主键
     * @return 订房记录
     */
    @Override
    public ReserveRoomHistoryInfo selectReserveRoomHistoryInfoByHistoryId(Long historyId) {
        return reserveRoomHistoryInfoMapper.selectReserveRoomHistoryInfoByHistoryId(historyId);
    }

    /**
     * 查询订房记录列表
     *
     * @param reserveRoomHistoryInfo 订房记录
     * @return 订房记录
     */
    @Override
    @DataScope(deptAlias = "tb_reserve_room_history_info", userAlias = "tb_reserve_room_history_info")
    public List<ReserveRoomHistoryInfo> selectReserveRoomHistoryInfoList(ReserveRoomHistoryInfo reserveRoomHistoryInfo) {
        List<ReserveRoomHistoryInfo> reserveRoomHistoryInfos = reserveRoomHistoryInfoMapper.selectReserveRoomHistoryInfoList(reserveRoomHistoryInfo);
        for (ReserveRoomHistoryInfo info : reserveRoomHistoryInfos) {
            SysUser user = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            RoomInfo roomInfo = roomInfoService.selectRoomInfoByRoomId(info.getRoomId());
            if (StringUtils.isNotNull(roomInfo)) {
                info.setRoomName(roomInfo.getRoomName());
            }
        }
        return reserveRoomHistoryInfos;
    }

    /**
     * 新增订房记录
     *
     * @param reserveRoomHistoryInfo 订房记录
     * @return 结果
     */
    @Override
    public int insertReserveRoomHistoryInfo(ReserveRoomHistoryInfo reserveRoomHistoryInfo) {
        //判断是否有房间
        RoomInfo roomInfo = roomInfoService.selectRoomInfoByRoomId(reserveRoomHistoryInfo.getRoomId());
        if (StringUtils.isNull(roomInfo)) {
            throw new ServiceException("房间不存在！！！");
        }
        //判断房间是否是已经订房
        if (!roomInfo.getRoomStatus().equals(Long.parseLong(RRoomStatus.ROOM_STATUS_0.getValue()))) {
            throw new ServiceException("房间当前状态不可订房！！！");
        }
        //根据订房时间+天数判断获取结束时间
        if (StringUtils.isNull(reserveRoomHistoryInfo.getReserveTime())) {
            reserveRoomHistoryInfo.setReserveTime(DateUtils.getNowDate());
        } else {
            //后台传过来的是年月日，要加上当前的时分秒
            //先强制格式化时间为年月日
            String dateToStr = DateUtils.parseDateToStr(YYYY_MM_DD, reserveRoomHistoryInfo.getReserveTime());
            //加上当前的时分秒
            String hhmmss = DateUtils.parseDateToStr(HH_MM_SS, new Date());
            reserveRoomHistoryInfo.setReserveTime(DateUtils.parseDate(dateToStr + " " + hhmmss));
        }
        //结束时间 订房时间+天数
        reserveRoomHistoryInfo.setEndTime(DateUtils.addDays(reserveRoomHistoryInfo.getReserveTime(), reserveRoomHistoryInfo.getDayNum().intValue()));
        //判断此时间段是否有订房记录
        List<ReserveRoomHistoryInfo> list = reserveRoomHistoryInfoMapper.selectList(new LambdaQueryWrapper<>(ReserveRoomHistoryInfo.class)
                .ge(ReserveRoomHistoryInfo::getReserveTime, reserveRoomHistoryInfo.getReserveTime())
                .le(ReserveRoomHistoryInfo::getEndTime, reserveRoomHistoryInfo.getEndTime())
                .eq(ReserveRoomHistoryInfo::getRoomId, reserveRoomHistoryInfo.getRoomId())
                .in(ReserveRoomHistoryInfo::getHistoryStatus, (Object) new Long[]{Long.parseLong(RReverveStatus.REVERVE_STATUS_0.getValue()), Long.parseLong(RReverveStatus.REVERVE_STATUS_1.getValue())})
        );
        if (StringUtils.isNotEmpty(list)) {
            throw new ServiceException("房间当前时间段已经订房，不可再订房！！！");
        }

        //更新房间为已定房
//        roomInfo.setRoomStatus(Long.parseLong(RRoomStatus.ROOM_STATUS_1.getValue()));
        roomInfo.setReserveNum(roomInfo.getReserveNum() + 1);
        roomInfoService.updateById(roomInfo);
        //计算价格
        reserveRoomHistoryInfo.setTotalPrice(roomInfo.getRoomPrice().multiply(new BigDecimal(reserveRoomHistoryInfo.getDayNum())));
        reserveRoomHistoryInfo.setUserId(SecurityUtils.getUserId());
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
    public int updateReserveRoomHistoryInfo(ReserveRoomHistoryInfo reserveRoomHistoryInfo) {
        //判断是否有房间
        RoomInfo roomInfo = roomInfoService.selectRoomInfoByRoomId(reserveRoomHistoryInfo.getRoomId());
        if (StringUtils.isNull(roomInfo)) {
            throw new ServiceException("房间不存在！！！");
        }
        //如果传过来是退房
        if (reserveRoomHistoryInfo.getHistoryStatus().equals(Long.parseLong(RReverveStatus.REVERVE_STATUS_2.getValue()))) {
            roomInfo.setRoomStatus(Long.parseLong(RRoomStatus.ROOM_STATUS_0.getValue()));
            roomInfoService.updateById(roomInfo);
        }
        reserveRoomHistoryInfo.setUpdateBy(SecurityUtils.getUsername());
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
    public int deleteReserveRoomHistoryInfoByHistoryIds(Long[] historyIds) {
        return reserveRoomHistoryInfoMapper.deleteReserveRoomHistoryInfoByHistoryIds(historyIds);
    }

    /**
     * 删除订房记录信息
     *
     * @param historyId 订房记录主键
     * @return 结果
     */
    @Override
    public int deleteReserveRoomHistoryInfoByHistoryId(Long historyId) {
        return reserveRoomHistoryInfoMapper.deleteReserveRoomHistoryInfoByHistoryId(historyId);
    }

    //endregion
    @Override
    public QueryWrapper<ReserveRoomHistoryInfo> getQueryWrapper(ReserveRoomHistoryInfoQuery reserveRoomHistoryInfoQuery) {
        QueryWrapper<ReserveRoomHistoryInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = reserveRoomHistoryInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long historyId = reserveRoomHistoryInfoQuery.getHistoryId();
        queryWrapper.eq(StringUtils.isNotNull(historyId), "history_id", historyId);

        Long roomId = reserveRoomHistoryInfoQuery.getRoomId();
        queryWrapper.eq(StringUtils.isNotNull(roomId), "room_id", roomId);

        Long userId = reserveRoomHistoryInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long historyStatus = reserveRoomHistoryInfoQuery.getHistoryStatus();
        queryWrapper.eq(StringUtils.isNotNull(historyStatus), "history_status", historyStatus);

        Date reserveTime = reserveRoomHistoryInfoQuery.getReserveTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginReserveTime")) && StringUtils.isNotNull(params.get("endReserveTime")), "reserve_time", params.get("beginReserveTime"), params.get("endReserveTime"));

        Date createTime = reserveRoomHistoryInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = reserveRoomHistoryInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = reserveRoomHistoryInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<ReserveRoomHistoryInfoVo> convertVoList(List<ReserveRoomHistoryInfo> reserveRoomHistoryInfoList) {
        if (StringUtils.isEmpty(reserveRoomHistoryInfoList)) {
            return Collections.emptyList();
        }
        return reserveRoomHistoryInfoList.stream().map(ReserveRoomHistoryInfoVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public void autoUpdateReserve() {
        //查到所有的待支付、预定中的记录
        LambdaQueryWrapper<ReserveRoomHistoryInfo> queryWrapper = new LambdaQueryWrapper<>(ReserveRoomHistoryInfo.class);
        queryWrapper.in(ReserveRoomHistoryInfo::getHistoryStatus, Long.parseLong(RReverveStatus.REVERVE_STATUS_0.getValue()), Long.parseLong(RReverveStatus.REVERVE_STATUS_1.getValue()));
        List<ReserveRoomHistoryInfo> list = this.list(queryWrapper);
        long currentedTimeMillis = System.currentTimeMillis();
        for (ReserveRoomHistoryInfo info : list) {
            //获取订房时间 判断订房时间+天数是否大于当前时间
            Date reserveTime = info.getReserveTime();
            if (reserveTime.getTime() + info.getDayNum() * 24 * 60 * 60 * 1000 < currentedTimeMillis) {
                //如果大于当前时间，修改状态为已退房
                info.setHistoryStatus(Long.parseLong(RReverveStatus.REVERVE_STATUS_3.getValue()));
                this.updateById(info);
                //更新房间状态为空闲
//                RoomInfo roomInfo = roomInfoService.selectRoomInfoByRoomId(info.getRoomId());
//                roomInfo.setRoomStatus(Long.parseLong(RRoomStatus.ROOM_STATUS_0.getValue()));
//                roomInfoService.updateById(roomInfo);
            }
            //判断创建时间是否是当前时间的十五分钟后后，如果是且状态还是待支付则直接修改状态为已结束
            if (info.getCreateTime().getTime() + 15 * 60 * 1000 < currentedTimeMillis && info.getHistoryStatus().equals(Long.parseLong(RReverveStatus.REVERVE_STATUS_0.getValue()))) {
                info.setHistoryStatus(Long.parseLong(RReverveStatus.REVERVE_STATUS_4.getValue()));
                this.updateById(info);
                //更新房间预定数
                RoomInfo roomInfo = roomInfoService.selectRoomInfoByRoomId(info.getRoomId());
                roomInfo.setReserveNum(roomInfo.getReserveNum() - 1);
                roomInfoService.updateById(roomInfo);
            }
        }
    }

}
