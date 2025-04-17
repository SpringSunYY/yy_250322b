import request from '@/utils/request'

// 查询房间评价列表
export function listRoomCommentInfo(query) {
  return request({
    url: '/manage/roomCommentInfo/list',
    method: 'get',
    params: query
  })
}

// 查询房间评价详细
export function getRoomCommentInfo(id) {
  return request({
    url: '/manage/roomCommentInfo/' + id,
    method: 'get'
  })
}

// 新增房间评价
export function addRoomCommentInfo(data) {
  return request({
    url: '/manage/roomCommentInfo',
    method: 'post',
    data: data
  })
}

// 修改房间评价
export function updateRoomCommentInfo(data) {
  return request({
    url: '/manage/roomCommentInfo',
    method: 'put',
    data: data
  })
}

// 删除房间评价
export function delRoomCommentInfo(id) {
  return request({
    url: '/manage/roomCommentInfo/' + id,
    method: 'delete'
  })
}
