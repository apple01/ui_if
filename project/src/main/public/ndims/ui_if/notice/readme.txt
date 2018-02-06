#有关iAP的MessageHub通知信息相关的各种IF的使用说明Sample:
/src/main/webapp/ndims/ui_if/notice/sample.html
Sample的URL：
http://localhost:8080/imart/ndims/ui_if/notice/sample.html

#IF: 检索当前登录用户的通知信息列表
Url：ndims/ui_if/notice/json/getNoticeHistoryResult
默认参数Json模板：src/main/webapp/ndims/ui_if/notice/json/getNoticeHistoryResult.json
参数JsonSample：
{
    "start": 0,
    "length": 0
}

#IF: 检索当前登录用户的通知信息总件数
Url：ndims/ui_if/notice/json/countUnreadNoticeHistory
默认参数Json模板：src/main/webapp/ndims/ui_if/notice/json/countUnreadNoticeHistory.json
参数JsonSample：
{
}

#IF: 更新当前登录用户的指定通知信息已读状态
Url：ndims/ui_if/notice/json/updateNoticeHasReadFlag
默认参数Json模板：src/main/webapp/ndims/ui_if/notice/json/updateNoticeHasReadFlag.json
{
    "noticeIds": [
    ]
}

#IF: 删除当前登录用户的指定通知信息已读状态
Url：ndims/ui_if/notice/json/deleteNoticeHistory
默认参数Json模板：src/main/webapp/ndims/ui_if/notice/json/deleteNoticeHistory.json
{
    "noticeIds": [
    ]
}
