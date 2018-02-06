#有关用户管理画面的各种IF的使用说明Sample:
/src/main/webapp/ndims/ui_if/user/sample.html
Sample的URL：
http://localhost:8080/imart/ndims/ui_if/user/sample.html

#创建用户和账号的后台处理IF:
Url：ndims/ui_if/master/maintenance/user/setuser
默认参数Json模板：src/main/webapp/ndims/ui_if/user/json/adduser.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.user.detail",
        "baseDate": "2017/03/14",
        "localeId": "zh_CN"
    },
    "modelInfo": {
        "jp.co.intra_mart.master.user.detail.main": {
            "userCd": "test004",
            "isUpdated": "true"
        },
        "jp.co.intra_mart.master.user.detail.profileAttach": {
            "defaultLocale": "zh_CN",
            "locale": "中文 (中国)",
            "sex": "0",
            "sortKey": "0",
            "localedata": {
                "zh_CN": {
                    "userName": "test004",
                    "userSearchName": "",
                    "countryCd": "",
                    "zipCode": "",
                    "address1": "",
                    "address2": "",
                    "address3": "",
                    "telephoneNumber": "",
                    "extensionNumber": "",
                    "faxNumber": "",
                    "extensionFaxNumber": "",
                    "mobileNumber": "",
                    "emailAddress1": "",
                    "emailAddress2": "",
                    "mobileEmailAddress": "",
                    "url": "",
                    "notes": ""
                }
            },
            "isUpdated": "true"
        },
        "jp.co.intra_mart.master.user.detail.accountAttach": {
            "locale": "",
            "confirmPassword": "test004",
            "password": "test004",
            "license": true,
            "validStartDate": "2017/03/14",
            "validEndDate": "2999/12/31",
            "timezone": "",
            "firstDayOfWeek": "",
            "calendarId": "",
            "datetimeFormat": "",
            "dateFormatStandard": "",
            "dateFormatSimple": "",
            "timeFormatTimestamp": "",
            "timeFormatStandard": "",
            "datetimeLocale": "",
            "firstLogin": false,
            "accountLock": false,
            "loginFailureCount": "0",
            "pagePatternId": "",
            "notes": "",
            "imgUrl": "http://192.168.1.100:8080/imart/ui/theme/im_theme_dropdown_blue/images/thumbnail.png",
            "isUpdated": "true",
            "decimalFormat": ""
        },
        "jp.co.intra_mart.master.user.detail.roleAttach": {
            "roleTermsInfo": [
                {
                    "id": "NEW_1489477429053:0",
                    "category": "im_workflow",
                    "roleId": "im_workflow_user",
                    "roleName": "im_workflow_user",
                    "displayName": "IM-Workflow 用户",
                    "created": true,
                    "startDate": "2017/03/14",
                    "endDate": "2999/12/31"
                }
            ],
            "appTermsInfo": [
                
            ],
            "removedRoleParents": [
                
            ],
            "removedAppParents": [
                
            ]
        },
        "jp.co.intra_mart.master.user.detail.departmentAttach": {
            "termsInfo": [
                {
                    "id": "NEW_1489477435684:0",
                    "departmentSetCd": "comp_other_01",
                    "companyCd": "comp_other_01",
                    "departmentCd": "dept_other_10",
                    "displayName": "其他部门01",
                    "description": "其他部门01",
                    "deleteFlag": false,
                    "postName": "",
                    "post": undefined,
                    "departmentMain": "",
                    "created": true
                }
            ],
            "removedParents": [
                
            ]
        },
        "jp.co.intra_mart.master.user.detail.publicgroupAttach": {
            "termsInfo": [
                
            ],
            "removedParents": [
                
            ]
        },
        "jp.co.intra_mart.master.user.detail.categoryItemAttach": {
            "termsInfo": [
                
            ],
            "removedParents": [
                
            ]
        },
        "jp.co.intra_mart.master.user.detail.external_user": {
            "external_user": false,
            "isUpdated": "false"
        }
    },
    "parameters": {
        "isEdit": false,
        "term": {
            "termCd": "term",
            "startDate": "1900/01/01 00:00:00",
            "endDate": "3000/01/01 00:00:00",
            "deleteFlag": false
        },
        "parentInfo": null,
        "recordInfo": null,
        "parameter": {
            "isEdit": "false"
        }
    }
}


#检索用户列表的的后台处理IF:
Url：ndims/ui_if/master/maintenance/user/search
默认参数Json模板：src/main/webapp/ndims/ui_if/user/json/search.json
参数JsonSample：
{
    "extensionPoint": "jp.co.intra_mart.foundation.master.setting.user.search.criteria",
    "pluginId": "jp.co.intra_mart.master.user.criteria.main",
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.user.search",
        "baseDate": "2017/03/13",
        "localeId": "zh_CN"
    },
    "model": {
        "target": "department",
        "attachmentName": "",
        "attachmentNameCd": "",
        "attachmentNameSetCd": "",
        "companyCd": "",
        "searchbelow": true,
        "keyword": "test003",
        "code": true,
        "userName": true,
        "userSearchName": false,
        "ignore": true
    },
    "start": 1,
    "count": 0,
    "sortTarget": "",
    "sortDirection": ""
}

#获得某用户信息的的后台处理IF:
Url：ndims/ui_if/master/maintenance/user/getuser
默认参数Json模板：src/main/webapp/ndims/ui_if/user/json/getuser.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.user.detail",
        "baseDate": "2017/03/13",
        "localeId": "zh_CN"
    },
    "model": {},
    "parameters": {
        "isEdit": true,
        "term": {
            "termCd": "8eb02rmrb03prcr",
            "startDate": "1900/01/01 00:00:00",
            "endDate": "3000/01/01 00:00:00",
            "deleteFlag": false
        },
        "parentInfo": null,
        "recordInfo": {
            "userCd": "test",
            "description": "样例用户01API",
            "displayName": "样例用户01API",
            "deleteFlag": false,
            "code": "test"
        },
        "parameter": {
            "isEdit": "true"
        }
    }
}

#编辑信息的的后台处理IF:
Url：ndims/ui_if/master/maintenance/user/setuser
默认参数Json模板：src/main/webapp/ndims/ui_if/user/json/setuser.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.user.detail",
        "baseDate": "2017/03/08",
        "localeId": "zh_CN"
    },
    "modelInfo": {
        "jp.co.intra_mart.master.user.detail.main": {
            "userCd": "test",
            "isUpdated": "false"
        },
        "jp.co.intra_mart.master.user.detail.profileAttach": {
            "defaultLocale": "zh_CN",
            "locale": "中文(中国)",
            "sex": "0",
            "sortKey": "0",
            "localedata": {
                "zh_CN": {
                    "userName": "WANGJIAN_02",
                    "userSearchName": "WANGJIAN_02",
                    "countryCd": "2",
                    "zipCode": "3",
                    "address1": "4",
                    "address2": "5",
                    "address3": "6",
                    "telephoneNumber": "",
                    "extensionNumber": "",
                    "faxNumber": "",
                    "extensionFaxNumber": "",
                    "mobileNumber": "",
                    "emailAddress1": "",
                    "emailAddress2": "",
                    "mobileEmailAddress": "",
                    "url": "",
                    "notes": ""
                }
            },
            "isUpdated": "false"
        },
        "jp.co.intra_mart.master.user.detail.accountAttach": {
            "locale": "",
            "license": true,
            "validStartDate": "2018/03/07",
            "validEndDate": "2999/12/31",
            "timezone": "",
            "firstDayOfWeek": "-1",
            "calendarId": "",
            "datetimeFormat": "",
            "dateFormatStandard": "",
            "dateFormatSimple": "",
            "timeFormatTimestamp": "",
            "timeFormatStandard": "",
            "datetimeLocale": "undefined",
            "firstLogin": false,
            "accountLock": false,
            "loginFailureCount": "0",
            "pagePatternId": "",
            "notes": "",
            "imgUrl": "http: //localhost: 8080/imart/ui/theme/im_theme_dropdown_blue/images/thumbnail.png",
            "isUpdated": "false",
            "decimalFormat": ""
        },
        "jp.co.intra_mart.master.user.detail.roleAttach": {
            "roleTermsInfo": [
                {
                    "roleId": "im_workflow_user",
                    "roleName": "im_workflow_user",
                    "category": "im_workflow",
                    "displayName": "IM-Workflow用户",
                    "startDate": "2017/03/07",
                    "endDate": "2999/12/31"
                }
            ],
            "appTermsInfo": [],
            "removedRoleParents": [],
            "removedAppParents": []
        },
        "jp.co.intra_mart.master.user.detail.departmentAttach": {
            "termsInfo": [
                {
                    "departmentSetCd": "comp_other_01",
                    "companyCd": "comp_other_01",
                    "departmentCd": "dept_other_10",
                    "description": "其他部门01",
                    "displayName": "其他部门01",
                    "deleteFlag": false,
                    "shortName": "其他部门01",
                    "postName": "",
                    "departmentMain": ""
                }
            ],
            "removedParents": []
        },
        "jp.co.intra_mart.master.user.detail.publicgroupAttach": {
            "termsInfo": [],
            "removedParents": []
        },
        "jp.co.intra_mart.master.user.detail.categoryItemAttach": {
            "termsInfo": [],
            "removedParents": []
        },
        "jp.co.intra_mart.master.user.detail.external_user": {
            "external_user": false,
            "isUpdated": "false"
        }
    },
    "parameters": {
        "isEdit": true,
        "term": {
            "termCd": "8eb8p5lyrnlhhcr",
            "startDate": "1900/01/01 00:00:00",
            "endDate": "3000/01/01 00:00:00",
            "deleteFlag": false
        },
        "parentInfo": null,
        "recordInfo": {
            "userCd": "test",
            "description": "test",
            "displayName": "test",
            "deleteFlag": false,
            "code": "test"
        },
        "parameter": {
            "isEdit": "true"
        }
    }
}

#删除用户和账号的后台处理IF:
Url：ndims/ui_if/master/maintenance/user/remove
默认参数Json模板：src/main/webapp/ndims/ui_if/user/json/remove.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.user.detail",
        "baseDate": "2017/03/13",
        "localeId": "zh_CN"
    },
    "modelInfo": {
        "jp.co.intra_mart.master.user.detail.main": {
            "userCd": "test001",
            "isUpdated": "false"
        },
        "jp.co.intra_mart.master.user.detail.profileAttach": {
            "defaultLocale": "zh_CN",
            "locale": "中文 (中国)",
            "sex": "0",
            "sortKey": "0",
            "localedata": {
                "zh_CN": {
                    "userName": "test",
                    "userSearchName": "",
                    "countryCd": "",
                    "zipCode": "",
                    "address1": "",
                    "address2": "",
                    "address3": "",
                    "telephoneNumber": "",
                    "extensionNumber": "",
                    "faxNumber": "",
                    "extensionFaxNumber": "",
                    "mobileNumber": "",
                    "emailAddress1": "",
                    "emailAddress2": "",
                    "mobileEmailAddress": "",
                    "url": "",
                    "notes": ""
                }
            },
            "isUpdated": "false"
        },
        "jp.co.intra_mart.master.user.detail.accountAttach": {
            "locale": "",
            "license": false,
            "validStartDate": "2017/03/13",
            "validEndDate": "2999/12/31",
            "timezone": "",
            "firstDayOfWeek": "-1",
            "calendarId": "",
            "datetimeFormat": "",
            "dateFormatStandard": "",
            "dateFormatSimple": "",
            "timeFormatTimestamp": "",
            "timeFormatStandard": "",
            "datetimeLocale": "undefined",
            "firstLogin": false,
            "accountLock": false,
            "loginFailureCount": "0",
            "pagePatternId": "",
            "notes": "",
            "imgUrl": "http://192.168.1.100:8080/imart/ui/theme/im_theme_dropdown_blue/images/thumbnail.png",
            "isUpdated": "false",
            "decimalFormat": ""
        },
        "jp.co.intra_mart.master.user.detail.roleAttach": {
            "roleTermsInfo": [],
            "appTermsInfo": [],
            "removedRoleParents": [],
            "removedAppParents": []
        },
        "jp.co.intra_mart.master.user.detail.departmentAttach": {
            "termsInfo": [],
            "removedParents": []
        },
        "jp.co.intra_mart.master.user.detail.publicgroupAttach": {
            "termsInfo": [],
            "removedParents": []
        },
        "jp.co.intra_mart.master.user.detail.categoryItemAttach": {
            "termsInfo": [],
            "removedParents": []
        },
        "jp.co.intra_mart.master.user.detail.external_user": {
            "external_user": false,
            "isUpdated": "false"
        }
    },
    "parameters": {
        "isEdit": true,
        "term": {
            "termCd": "8ebh74hgsyu9ucr",
            "startDate": "1900/01/01 00:00:00",
            "endDate": "3000/01/01 00:00:00",
            "deleteFlag": false
        },
        "parentInfo": null,
        "recordInfo": {
            "userCd": "test001",
            "description": "test",
            "displayName": "test",
            "deleteFlag": false,
            "code": "test001"
        },
        "parameter": {
            "isEdit": "true"
        }
    }
}
