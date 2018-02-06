#有关身份管理画面的各种IF的使用说明Sample:
/src/main/webapp/ndims/ui_if/role/sample.html
Sample的URL：
http://localhost:8080/imart/ndims/ui_if/role/sample.html

#IF: 检索身份信息
Url：ndims/ui_if/master/maintenance/role/searchrole
默认参数Json模板：src/main/webapp/ndims/ui_if/role/json/searchrole.json
参数JsonSample：
{
    "extension": {
        "searchRoleName": "",
        "searchRoleCategory": ""
    },
    "page": 1,
    "rowNum": 10,
    "sortOrder": "asc",
    "sortIndex": "roleName"
}


#IF: 新增身份
Url：ndims/ui_if/master/maintenance/role/addrole
默认参数Json模板：src/main/webapp/ndims/ui_if/role/json/addrole.json
参数JsonSample：
{
    "roleId": "",
    "roleName": "",
    "roleDisplayName_zh_CN": "",
    "roleDisplayNames": ",,",
    "roleCategory": "",
    "subRoleIds": ""
}

#IF: 获得身份信息
Url：ndims/ui_if/master/maintenance/role/getrole
默认参数Json模板：src/main/webapp/ndims/ui_if/role/json/getrole.json
参数JsonSample：
{
    "roleId": ""
}

#IF: 编辑身份信息
Url：ndims/ui_if/master/maintenance/role/setrole
默认参数Json模板：src/main/webapp/ndims/ui_if/role/json/setrole.json
参数JsonSample：
{
    "roleId": "",
    "roleName": "",
    "roleDisplayName_zh_CN": "",
    "roleDisplayNames": ",,",
    "roleCategory": "",
    "subRoleIds": ""
}

#IF: 删除身份信息
Url：ndims/ui_if/master/maintenance/role/removerole
默认参数Json模板：src/main/webapp/ndims/ui_if/role/json/removerole.json
参数JsonSample：
{
    "roleId": ""
}

#IF: 向身份中追加所属用户
Url：ndims/ui_if/master/maintenance/role/addroleaccount
默认参数Json模板：src/main/webapp/ndims/ui_if/role/json/addroleaccount.json
参数JsonSample：
{
    "roleId": "",
    "userCds": [
    ]
}

#IF: 向身份中编辑所属用户（身份期间）
Url：ndims/ui_if/master/maintenance/role/setroleaccount
默认参数Json模板：src/main/webapp/ndims/ui_if/role/json/setroleaccount.json
参数JsonSample：
{
    "limitDate": "",
    "roleId": "",
    "startDate": "",
    "userCds": [
    ]
}

#IF: 向身份中编辑所属用户（身份期间）
Url：ndims/ui_if/master/maintenance/role/removeroleaccount
默认参数Json模板：src/main/webapp/ndims/ui_if/role/json/removeroleaccount.json
参数JsonSample：
{
    "roleId": "",
    "userCds": [
    ]
}


