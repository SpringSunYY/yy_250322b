package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.RoomInfo;
import com.lz.manage.model.vo.roomInfo.RoomInfoVo;
import com.lz.manage.model.dto.roomInfo.RoomInfoQuery;
import com.lz.manage.model.dto.roomInfo.RoomInfoInsert;
import com.lz.manage.model.dto.roomInfo.RoomInfoEdit;
import com.lz.manage.service.IRoomInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 酒店房间信息Controller
 *
 * @author YY
 * @date 2025-03-29
 */
@RestController
@RequestMapping("/manage/roomInfo")
public class RoomInfoController extends BaseController
{
    @Resource
    private IRoomInfoService roomInfoService;

    /**
     * 查询酒店房间信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:roomInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(RoomInfoQuery roomInfoQuery)
    {
        RoomInfo roomInfo = RoomInfoQuery.queryToObj(roomInfoQuery);
        startPage();
        List<RoomInfo> list = roomInfoService.selectRoomInfoList(roomInfo);
        List<RoomInfoVo> listVo= list.stream().map(RoomInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出酒店房间信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:roomInfo:export')")
    @Log(title = "酒店房间信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RoomInfoQuery roomInfoQuery)
    {
        RoomInfo roomInfo = RoomInfoQuery.queryToObj(roomInfoQuery);
        List<RoomInfo> list = roomInfoService.selectRoomInfoList(roomInfo);
        ExcelUtil<RoomInfo> util = new ExcelUtil<RoomInfo>(RoomInfo.class);
        util.exportExcel(response, list, "酒店房间信息数据");
    }

    /**
     * 获取酒店房间信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:roomInfo:query')")
    @GetMapping(value = "/{roomId}")
    public AjaxResult getInfo(@PathVariable("roomId") Long roomId)
    {
        RoomInfo roomInfo = roomInfoService.selectRoomInfoByRoomId(roomId);
        return success(RoomInfoVo.objToVo(roomInfo));
    }

    /**
     * 新增酒店房间信息
     */
    @PreAuthorize("@ss.hasPermi('manage:roomInfo:add')")
    @Log(title = "酒店房间信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RoomInfoInsert roomInfoInsert)
    {
        RoomInfo roomInfo = RoomInfoInsert.insertToObj(roomInfoInsert);
        return toAjax(roomInfoService.insertRoomInfo(roomInfo));
    }

    /**
     * 修改酒店房间信息
     */
    @PreAuthorize("@ss.hasPermi('manage:roomInfo:edit')")
    @Log(title = "酒店房间信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RoomInfoEdit roomInfoEdit)
    {
        RoomInfo roomInfo = RoomInfoEdit.editToObj(roomInfoEdit);
        return toAjax(roomInfoService.updateRoomInfo(roomInfo));
    }

    /**
     * 删除酒店房间信息
     */
    @PreAuthorize("@ss.hasPermi('manage:roomInfo:remove')")
    @Log(title = "酒店房间信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{roomIds}")
    public AjaxResult remove(@PathVariable Long[] roomIds)
    {
        return toAjax(roomInfoService.deleteRoomInfoByRoomIds(roomIds));
    }
}
