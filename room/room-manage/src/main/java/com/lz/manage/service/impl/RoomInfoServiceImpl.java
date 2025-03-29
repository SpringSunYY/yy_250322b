package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.RoomInfoMapper;
import com.lz.manage.model.domain.RoomInfo;
import com.lz.manage.service.IRoomInfoService;
import com.lz.manage.model.dto.roomInfo.RoomInfoQuery;
import com.lz.manage.model.vo.roomInfo.RoomInfoVo;

/**
 * 酒店房间信息Service业务层处理
 *
 * @author YY
 * @date 2025-03-29
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo> implements IRoomInfoService {
    @Resource
    private RoomInfoMapper roomInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询酒店房间信息
     *
     * @param roomId 酒店房间信息主键
     * @return 酒店房间信息
     */
    @Override
    public RoomInfo selectRoomInfoByRoomId(Long roomId) {
        return roomInfoMapper.selectRoomInfoByRoomId(roomId);
    }

    /**
     * 查询酒店房间信息列表
     *
     * @param roomInfo 酒店房间信息
     * @return 酒店房间信息
     */
    @Override
    public List<RoomInfo> selectRoomInfoList(RoomInfo roomInfo) {
        List<RoomInfo> roomInfos = roomInfoMapper.selectRoomInfoList(roomInfo);
        for (RoomInfo info : roomInfos) {
            SysUser user = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
        }
        return roomInfos;
    }

    /**
     * 新增酒店房间信息
     *
     * @param roomInfo 酒店房间信息
     * @return 结果
     */
    @Override
    public int insertRoomInfo(RoomInfo roomInfo) {
        roomInfo.setUserId(SecurityUtils.getUserId());
        roomInfo.setCreateTime(DateUtils.getNowDate());
        return roomInfoMapper.insertRoomInfo(roomInfo);
    }

    /**
     * 修改酒店房间信息
     *
     * @param roomInfo 酒店房间信息
     * @return 结果
     */
    @Override
    public int updateRoomInfo(RoomInfo roomInfo) {
        roomInfo.setUpdateBy(SecurityUtils.getUsername());
        roomInfo.setUpdateTime(DateUtils.getNowDate());
        return roomInfoMapper.updateRoomInfo(roomInfo);
    }

    /**
     * 批量删除酒店房间信息
     *
     * @param roomIds 需要删除的酒店房间信息主键
     * @return 结果
     */
    @Override
    public int deleteRoomInfoByRoomIds(Long[] roomIds) {
        return roomInfoMapper.deleteRoomInfoByRoomIds(roomIds);
    }

    /**
     * 删除酒店房间信息信息
     *
     * @param roomId 酒店房间信息主键
     * @return 结果
     */
    @Override
    public int deleteRoomInfoByRoomId(Long roomId) {
        return roomInfoMapper.deleteRoomInfoByRoomId(roomId);
    }

    //endregion
    @Override
    public QueryWrapper<RoomInfo> getQueryWrapper(RoomInfoQuery roomInfoQuery) {
        QueryWrapper<RoomInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = roomInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long roomId = roomInfoQuery.getRoomId();
        queryWrapper.eq(StringUtils.isNotNull(roomId), "room_id", roomId);

        String roomName = roomInfoQuery.getRoomName();
        queryWrapper.like(StringUtils.isNotEmpty(roomName), "room_name", roomName);

        Long roomStatus = roomInfoQuery.getRoomStatus();
        queryWrapper.eq(StringUtils.isNotNull(roomStatus), "room_status", roomStatus);

        Long userId = roomInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = roomInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = roomInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = roomInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<RoomInfoVo> convertVoList(List<RoomInfo> roomInfoList) {
        if (StringUtils.isEmpty(roomInfoList)) {
            return Collections.emptyList();
        }
        return roomInfoList.stream().map(RoomInfoVo::objToVo).collect(Collectors.toList());
    }

}
