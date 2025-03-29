import request from '@/utils/request'

// 查询支付记录列表
export function listPayHistoryInfo(query) {
  return request({
    url: '/manage/payHistoryInfo/list',
    method: 'get',
    params: query
  })
}

// 查询支付记录详细
export function getPayHistoryInfo(historyId) {
  return request({
    url: '/manage/payHistoryInfo/' + historyId,
    method: 'get'
  })
}

// 新增支付记录
export function addPayHistoryInfo(data) {
  return request({
    url: '/manage/payHistoryInfo',
    method: 'post',
    data: data
  })
}

// 修改支付记录
export function updatePayHistoryInfo(data) {
  return request({
    url: '/manage/payHistoryInfo',
    method: 'put',
    data: data
  })
}

// 删除支付记录
export function delPayHistoryInfo(historyId) {
  return request({
    url: '/manage/payHistoryInfo/' + historyId,
    method: 'delete'
  })
}
