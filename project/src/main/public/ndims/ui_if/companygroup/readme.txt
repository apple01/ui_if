#有关集团管理画面的各种IF的使用说明Sample:
/src/main/webapp/ndims/ui_if/companygroup/sample.html
Sample的URL：
http://localhost:8080/imart/ndims/ui_if/companygroup/sample.html

#IF: 检索最上层集团信息
Url：ndims/ui_if/master/maintenance/companygroup/searchcompanygroup
默认参数Json模板：src/main/webapp/ndims/ui_if/companygroup/json/searchcompanygroup.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.companygroup.treeview",
        "baseDate": "",
        "localeId": "zh_CN",
        "isDisabled": true
    },
    "route": []
}

#IF: 检索集团下属子集团信息
Url：ndims/ui_if/master/maintenance/companygroup/searchsubcompanygroup
默认参数Json模板：src/main/webapp/ndims/ui_if/companygroup/json/searchsubcompanygroup.json
参数JsonSample：
{
    "basicInfo" : {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.companygroup.treeview",
        "baseDate": "",
        "localeId": "zh_CN",
        "companyCd" : "",
        "isDisabled": true
    },
    "composite" : {
        "companyGroupCd": "",
        "companyGroupSetCd": "",
        "displayName": "",
        "description": ""
    }
}

#IF: 新建集团信息
Url：ndims/ui_if/master/maintenance/companygroup/addcompanygroup
默认参数Json模板：src/main/webapp/ndims/ui_if/companygroup/json/addcompanygroup.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.companygroup.detail",
        "baseDate": "",
        "localeId": "zh_CN",
        "companyCd": null,
        "isDisabled": true
    },
    "model": {
        "jp.co.intra_mart.master.companygroup.detail.main": {
            "defaultLocale": "zh_CN",
            "locale": "中文 (中国)",
            "companyGroupCd": "",
            "companyGroupSetCd": "",
            "sortKey": "0",
            "localedata": {
                "zh_CN": {
                    "companyGroupName": "",
                    "companyGroupShortName": "",
                    "companyGroupSearchName": "",
                    "notes": ""
                }
            },
            "isUpdated": "true"
        }
    },
    "parameters": {
        "isEdit": false,
        "term": {
            "termCd": "",
            "startDate": "1900/01/01 00:00:00",
            "endDate": "3000/01/01 00:00:00",
            "deleteFlag": false
        },
        "parentInfo": {
            "companyGroupCd": "",
            "companyGroupSetCd": "",
            "displayName": "",
            "description": ""
        },
        "recordInfo": null,
        "parameter": {
            "isEdit": "false"
        }
    }
}

#IF: 获得集团详细信息
Url：ndims/ui_if/master/maintenance/companygroup/getcompanygroup
默认参数Json模板：src/main/webapp/ndims/ui_if/companygroup/json/getcompanygroup.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.companygroup.detail",
        "baseDate": "",
        "localeId": "zh_CN",
        "companyCd": null,
        "isDisabled": true
    },
    "model": {},
    "parameters": {
        "isEdit": true,
        "term": {
            "termCd": "",
            "startDate": "",
            "endDate": "",
            "deleteFlag": false
        },
        "parentInfo": null,
        "recordInfo": {
            "companyGroupCd": "",
            "companyGroupSetCd": "",
            "displayName": "",
            "description": ""
        },
        "parameter": {
            "isEdit": "true"
        }
    }
}

#IF: 更新集团信息
Url：ndims/ui_if/master/maintenance/companygroup/setcompanygroup
默认参数Json模板：src/main/webapp/ndims/ui_if/companygroup/json/setcompanygroup.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.companygroup.detail",
        "baseDate": "",
        "localeId": "zh_CN",
        "companyCd": null,
        "isDisabled": true
    },
    "model": {
        "jp.co.intra_mart.master.companygroup.detail.main": {
            "defaultLocale": "zh_CN",
            "locale": "中文 (中国)",
            "companyGroupCd": "",
            "companyGroupSetCd": "",
            "sortKey": "0",
            "localedata": {
                "zh_CN": {
                    "companyGroupName": "",
                    "companyGroupShortName": "",
                    "companyGroupSearchName": "",
                    "notes": ""
                }
            },
            "isUpdated": "false"
        }
    },
    "parameters": {
        "isEdit": true,
        "term": {
            "termCd": "",
            "startDate": "",
            "endDate": "",
            "deleteFlag": false
        },
        "parentInfo": null,
        "recordInfo": {
            "companyGroupCd": "",
            "companyGroupSetCd": "",
            "displayName": "",
            "description": ""
        },
        "parameter": {
            "isEdit": "true"
        }
    }
}

#IF: 删除集团信息
Url：ndims/ui_if/master/maintenance/companygroup/removecompanygroup
默认参数Json模板：src/main/webapp/ndims/ui_if/companygroup/json/removecompanygroup.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.companygroup.detail",
        "baseDate": "",
        "localeId": "zh_CN",
        "companyCd": null,
        "isDisabled": true
    },
    "model": {
        "jp.co.intra_mart.master.companygroup.detail.main": {
            "defaultLocale": "zh_CN",
            "locale": "中文 (中国)",
            "companyGroupCd": "",
            "companyGroupSetCd": "",
            "sortKey": "0",
            "localedata": {
                "zh_CN": {
                    "companyGroupName": "",
                    "companyGroupShortName": "",
                    "companyGroupSearchName": "",
                    "notes": ""
                }
            },
            "isUpdated": "false"
        }
    },
    "parameters": {
        "isEdit": true,
        "term": {
            "termCd": "",
            "startDate": "",
            "endDate": "",
            "deleteFlag": false
        },
        "parentInfo": null,
        "recordInfo": {
            "companyGroupCd": "",
            "companyGroupSetCd": "",
            "displayName": "",
            "description": ""
        },
        "parameter": {
            "isEdit": "true"
        }
    }
}


#IF: 检索集团所属公司信息
Url：ndims/ui_if/master/maintenance/companygroup/searchcompany
默认参数Json模板：src/main/webapp/ndims/ui_if/companygroup/json/searchcompany.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.companygroup.treeview",
        "baseDate": "",
        "localeId": "zh_CN",
        "companyCd": null,
        "isDisabled": true
    },
    "composite": {
        "companyGroupCd": "",
        "companyGroupSetCd": "",
        "displayName": "",
        "description": ""
    },
    "start": 1,
    "count": 0,
    "sortTarget": "",
    "sortDirection": ""
}


#IF: 在集团中追加所属公司
Url：ndims/ui_if/master/maintenance/companygroup/joincompanys
默认参数Json模板：src/main/webapp/ndims/ui_if/companygroup/json/joincompanys.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.companygroup.treeview",
        "baseDate": "",
        "localeId": "zh_CN",
        "companyCd": null,
        "isDisabled": true
    },
    "parent": {
        "companyGroupCd": "",
        "companyGroupSetCd": "",
        "displayName": "",
        "description": ""
    },
    "children": [
    ]
}

#IF: 在集团中移除所属公司
Url：ndims/ui_if/master/maintenance/companygroup/detachcompany
默认参数Json模板：src/main/webapp/ndims/ui_if/companygroup/json/detachcompany.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.companygroup.attachterms.serverside",
        "baseDate": "",
        "localeId": "zh_CN",
        "companyCd": null,
        "isDisabled": true
    },
    "recordInfo": {
        "companyCd": "",
        "description": "",
        "displayName": "",
        "deleteFlag": false,
        "shortName": ""
    },
    "parentInfo": {
        "companyGroupCd": "",
        "companyGroupSetCd": "",
        "displayName": "",
        "description": ""
    },
    "parameter": {
        "termsInfo": []
    }
}
