import request from '@/utils/request'

// 查询订房记录列表
export function listReserveRoomHistoryInfo(query) {
  return request({
    url: '/manage/reserveRoomHistoryInfo/list',
    method: 'get',
    params: query
  })
}

// 查询订房记录详细
export function getReserveRoomHistoryInfo(historyId) {
  return request({
    url: '/manage/reserveRoomHistoryInfo/' + historyId,
    method: 'get'
  })
}

// 新增订房记录
export function addReserveRoomHistoryInfo(data) {
  return request({
    url: '/manage/reserveRoomHistoryInfo',
    method: 'post',
    data: data
  })
}

// 修改订房记录
export function updateReserveRoomHistoryInfo(data) {
  return request({
    url: '/manage/reserveRoomHistoryInfo',
    method: 'put',
    data: data
  })
}

// 删除订房记录
export function delReserveRoomHistoryInfo(historyId) {
  return request({
    url: '/manage/reserveRoomHistoryInfo/' + historyId,
    method: 'delete'
  })
}
