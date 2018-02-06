#有关职务管理画面的各种IF的使用说明Sample:
/src/main/webapp/ndims/ui_if/post/sample.html
Sample的URL：
http://localhost:8080/imart/ndims/ui_if/post/sample.html

#IF: 检索职务信息
Url：ndims/ui_if/master/maintenance/post/searchpost
默认参数Json模板：src/main/webapp/ndims/ui_if/post/json/searchpost.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.post.treeview",
        "baseDate": "2017/03/20",
        "localeId": "zh_CN",
        "companyCd": "comp_sample_01",
        "companyName": "样例公司",
        "isDisabled": true
    },
    "composite": {
        "departmentCd": "comp_sample_01",
        "departmentSetCd": "comp_sample_01",
        "companyCd": "comp_sample_01",
        "displayName": "样例公司",
        "description": "样例公司"
    },
    "start": 1,
    "count": 0,
    "sortTarget": "",
    "sortDirection": ""
}


#IF: 新增职务
Url：ndims/ui_if/master/maintenance/post/addpost
默认参数Json模板：src/main/webapp/ndims/ui_if/post/json/addpost.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.post.detail",
        "baseDate": "2017/03/20",
        "localeId": "zh_CN",
        "companyCd": "comp_sample_01",
        "companyName": "样例公司",
        "isDisabled": true
    },
    "model": {
        "jp.co.intra_mart.master.post.detail.main": {
            "defaultLocale": "zh_CN",
            "locale": "中文 (中国)",
            "postCd": "8ebr37v1iihd3cr",
            "companyCd": "comp_sample_01",
            "departmentSetCd": "comp_sample_01",
            "departmentDisplayName": "样例公司",
            "rank": "0",
            "sortKey": "0",
            "localedata": {
                "zh_CN": {
                    "postName": "test",
                    "notes": ""
                }
            },
            "isUpdated": "true"
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
        "parentInfo": {
            "departmentCd": "comp_sample_01",
            "departmentSetCd": "comp_sample_01",
            "companyCd": "comp_sample_01",
            "displayName": "样例公司",
            "description": "样例公司"
        },
        "recordInfo": null,
        "parameter": {
            "isEdit": "false"
        }
    }
}

#IF: 获得职务信息
Url：ndims/ui_if/master/maintenance/post/getpost
默认参数Json模板：src/main/webapp/ndims/ui_if/post/json/getpost.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.post.detail",
        "baseDate": "2017/03/20",
        "localeId": "zh_CN",
        "companyCd": "comp_sample_01",
        "companyName": "样例公司",
        "isDisabled": true
    },
    "model": {},
    "parameters": {
        "isEdit": true,
        "term": {
            "termCd": "8ebr381uzihdccr",
            "startDate": "1900/01/01 00:00:00",
            "endDate": "3000/01/01 00:00:00",
            "deleteFlag": false
        },
        "parentInfo": {
            "departmentCd": "comp_sample_01",
            "departmentSetCd": "comp_sample_01",
            "companyCd": "comp_sample_01",
            "displayName": "样例公司",
            "description": "样例公司"
        },
        "recordInfo": {
            "companyCd": "comp_sample_01",
            "departmentSetCd": "comp_sample_01",
            "postCd": "8ebr37v1iihd3cr",
            "rank": 0,
            "description": "test",
            "displayName": "test",
            "deleteFlag": false
        },
        "parameter": {
            "isEdit": "true"
        }
    }
}

#IF: 编辑职务信息
Url：ndims/ui_if/master/maintenance/post/setpost
默认参数Json模板：src/main/webapp/ndims/ui_if/post/json/setpost.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.post.detail",
        "baseDate": "2017/03/20",
        "localeId": "zh_CN",
        "companyCd": "comp_sample_01",
        "companyName": "样例公司",
        "isDisabled": true
    },
    "model": {
        "jp.co.intra_mart.master.post.detail.main": {
            "defaultLocale": "zh_CN",
            "locale": "中文 (中国)",
            "postCd": "8ebr37v1iihd3cr",
            "companyCd": "comp_sample_01",
            "departmentSetCd": "comp_sample_01",
            "departmentDisplayName": "样例公司",
            "rank": "0",
            "sortKey": "0",
            "localedata": {
                "zh_CN": {
                    "postName": "test",
                    "notes": ""
                }
            },
            "isUpdated": "false"
        }
    },
    "parameters": {
        "isEdit": true,
        "term": {
            "termCd": "8ebsf5nwulxxhcr",
            "startDate": "1900/01/01 00:00:00",
            "endDate": "3000/01/01 00:00:00",
            "deleteFlag": false
        },
        "parentInfo": {
            "departmentCd": "comp_sample_01",
            "departmentSetCd": "comp_sample_01",
            "companyCd": "comp_sample_01",
            "displayName": "样例公司",
            "description": "样例公司"
        },
        "recordInfo": {
            "companyCd": "comp_sample_01",
            "departmentSetCd": "comp_sample_01",
            "postCd": "8ebr37v1iihd3cr",
            "rank": 0,
            "description": "test",
            "displayName": "test",
            "deleteFlag": false
        },
        "parameter": {
            "isEdit": "true"
        }
    }
}

#IF: 删除职务信息
Url：ndims/ui_if/master/maintenance/post/removepost
默认参数Json模板：src/main/webapp/ndims/ui_if/post/json/removepost.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.post.detail",
        "baseDate": "2017/03/20",
        "localeId": "zh_CN",
        "companyCd": "comp_sample_01",
        "companyName": "样例公司",
        "isDisabled": true
    },
    "model": {
        "jp.co.intra_mart.master.post.detail.main": {
            "defaultLocale": "zh_CN",
            "locale": "中文 (中国)",
            "postCd": "8ebr37v1iihd3cr",
            "companyCd": "comp_sample_01",
            "departmentSetCd": "comp_sample_01",
            "departmentDisplayName": "样例公司",
            "rank": "0",
            "sortKey": "0",
            "localedata": {
                "zh_CN": {
                    "postName": "test",
                    "notes": ""
                }
            },
            "isUpdated": "false"
        }
    },
    "parameters": {
        "isEdit": true,
        "term": {
            "termCd": "8ebr381uzihdccr",
            "startDate": "1900/01/01 00:00:00",
            "endDate": "3000/01/01 00:00:00",
            "deleteFlag": false
        },
        "parentInfo": {
            "departmentCd": "comp_sample_01",
            "departmentSetCd": "comp_sample_01",
            "companyCd": "comp_sample_01",
            "displayName": "样例公司",
            "description": "样例公司"
        },
        "recordInfo": {
            "companyCd": "comp_sample_01",
            "departmentSetCd": "comp_sample_01",
            "postCd": "8ebr37v1iihd3cr",
            "rank": 0,
            "description": "test",
            "displayName": "test",
            "deleteFlag": false
        },
        "parameter": {
            "isEdit": "true"
        }
    }
}
