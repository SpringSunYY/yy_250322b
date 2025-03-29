import request from '@/utils/request'

// 查询酒店房间信息列表
export function listRoomInfo(query) {
  return request({
    url: '/manage/roomInfo/list',
    method: 'get',
    params: query
  })
}

// 查询酒店房间信息详细
export function getRoomInfo(roomId) {
  return request({
    url: '/manage/roomInfo/' + roomId,
    method: 'get'
  })
}

// 新增酒店房间信息
export function addRoomInfo(data) {
  return request({
    url: '/manage/roomInfo',
    method: 'post',
    data: data
  })
}

// 修改酒店房间信息
export function updateRoomInfo(data) {
  return request({
    url: '/manage/roomInfo',
    method: 'put',
    data: data
  })
}

// 删除酒店房间信息
export function delRoomInfo(roomId) {
  return request({
    url: '/manage/roomInfo/' + roomId,
    method: 'delete'
  })
}
