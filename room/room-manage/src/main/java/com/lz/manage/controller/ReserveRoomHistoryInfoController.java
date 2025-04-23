package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.lz.manage.model.domain.UserBalanceInfo;
import com.lz.manage.service.IUserBalanceInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
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
import com.lz.manage.model.domain.ReserveRoomHistoryInfo;
import com.lz.manage.model.vo.reserveRoomHistoryInfo.ReserveRoomHistoryInfoVo;
import com.lz.manage.model.dto.reserveRoomHistoryInfo.ReserveRoomHistoryInfoQuery;
import com.lz.manage.model.dto.reserveRoomHistoryInfo.ReserveRoomHistoryInfoInsert;
import com.lz.manage.model.dto.reserveRoomHistoryInfo.ReserveRoomHistoryInfoEdit;
import com.lz.manage.service.IReserveRoomHistoryInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 订房记录Controller
 *
 * @author YY
 * @date 2025-03-29
 */
@RestController
@RequestMapping("/manage/reserveRoomHistoryInfo")
public class ReserveRoomHistoryInfoController extends BaseController
{
    @Resource
    private IReserveRoomHistoryInfoService reserveRoomHistoryInfoService;

    @Resource
    private IUserBalanceInfoService userBalanceInfoService;

    /**
     * 查询订房记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:reserveRoomHistoryInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ReserveRoomHistoryInfoQuery reserveRoomHistoryInfoQuery)
    {
        ReserveRoomHistoryInfo reserveRoomHistoryInfo = ReserveRoomHistoryInfoQuery.queryToObj(reserveRoomHistoryInfoQuery);
        startPage();
        List<ReserveRoomHistoryInfo> list = reserveRoomHistoryInfoService.selectReserveRoomHistoryInfoList(reserveRoomHistoryInfo);
        List<ReserveRoomHistoryInfoVo> listVo= list.stream().map(ReserveRoomHistoryInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出订房记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:reserveRoomHistoryInfo:export')")
    @Log(title = "订房记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReserveRoomHistoryInfoQuery reserveRoomHistoryInfoQuery)
    {
        ReserveRoomHistoryInfo reserveRoomHistoryInfo = ReserveRoomHistoryInfoQuery.queryToObj(reserveRoomHistoryInfoQuery);
        List<ReserveRoomHistoryInfo> list = reserveRoomHistoryInfoService.selectReserveRoomHistoryInfoList(reserveRoomHistoryInfo);
        ExcelUtil<ReserveRoomHistoryInfo> util = new ExcelUtil<ReserveRoomHistoryInfo>(ReserveRoomHistoryInfo.class);
        util.exportExcel(response, list, "订房记录数据");
    }

    /**
     * 获取订房记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:reserveRoomHistoryInfo:query')")
    @GetMapping(value = "/{historyId}")
    public AjaxResult getInfo(@PathVariable("historyId") Long historyId)
    {
        ReserveRoomHistoryInfo reserveRoomHistoryInfo = reserveRoomHistoryInfoService.selectReserveRoomHistoryInfoByHistoryId(historyId);
        ReserveRoomHistoryInfoVo roomHistoryInfoVo = ReserveRoomHistoryInfoVo.objToVo(reserveRoomHistoryInfo);
        UserBalanceInfo userBalanceInfo = userBalanceInfoService.selectUserBalanceInfoByUserId(reserveRoomHistoryInfo.getUserId());
        roomHistoryInfoVo.setBalance(userBalanceInfo.getBalance());
        return success(roomHistoryInfoVo);
    }

    /**
     * 新增订房记录
     */
    @PreAuthorize("@ss.hasPermi('manage:reserveRoomHistoryInfo:add')")
    @Log(title = "订房记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated ReserveRoomHistoryInfoInsert reserveRoomHistoryInfoInsert)
    {
        ReserveRoomHistoryInfo reserveRoomHistoryInfo = ReserveRoomHistoryInfoInsert.insertToObj(reserveRoomHistoryInfoInsert);
        return toAjax(reserveRoomHistoryInfoService.insertReserveRoomHistoryInfo(reserveRoomHistoryInfo));
    }

    /**
     * 修改订房记录
     */
    @PreAuthorize("@ss.hasPermi('manage:reserveRoomHistoryInfo:edit')")
    @Log(title = "订房记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ReserveRoomHistoryInfoEdit reserveRoomHistoryInfoEdit)
    {
        ReserveRoomHistoryInfo reserveRoomHistoryInfo = ReserveRoomHistoryInfoEdit.editToObj(reserveRoomHistoryInfoEdit);
        return toAjax(reserveRoomHistoryInfoService.updateReserveRoomHistoryInfo(reserveRoomHistoryInfo));
    }

    /**
     * 删除订房记录
     */
    @PreAuthorize("@ss.hasPermi('manage:reserveRoomHistoryInfo:remove')")
    @Log(title = "订房记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{historyIds}")
    public AjaxResult remove(@PathVariable Long[] historyIds)
    {
        return toAjax(reserveRoomHistoryInfoService.deleteReserveRoomHistoryInfoByHistoryIds(historyIds));
    }
}
