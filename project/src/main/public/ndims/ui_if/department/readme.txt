#有关部门管理画面的各种IF的使用说明Sample:
/src/main/webapp/ndims/ui_if/department/sample.html
Sample的URL：
http://localhost:8080/imart/ndims/ui_if/department/sample.html

#IF: 检索公司直属部门信息
Url：ndims/ui_if/master/maintenance/department/searchcompanydep
默认参数Json模板：src/main/webapp/ndims/ui_if/department/json/searchcompanydep.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.company.treeview",
        "baseDate": "2017/03/20",
        "localeId": "zh_CN",
        "companyCd": "comp_sample_01",
        "companyName": "样例公司",
        "isDisabled": true
    },
    "route": [

    ]
}


#IF: 检索部门下属子部门信息
Url：ndims/ui_if/master/maintenance/department/searchsubdep
默认参数Json模板：src/main/webapp/ndims/ui_if/department/json/searchsubdep.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.company.treeview",
        "baseDate": "2017/03/20",
        "localeId": "zh_CN",
        "companyCd": "comp_sample_01",
        "companyName": "样例公司",
        "isDisabled": true
    },
    "composite": {
        "companyCd": "comp_sample_01",
        "departmentCd": "dept_sample_10",
        "departmentSetCd": "comp_sample_01",
        "displayName": "样例部门01",
        "description": "样例部门01"
    }
}

#IF: 新建部门信息
Url：ndims/ui_if/master/maintenance/department/adddep
默认参数Json模板：src/main/webapp/ndims/ui_if/department/json/adddep.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.company.detail",
        "baseDate": "2017/03/20",
        "localeId": "zh_CN",
        "companyCd": "comp_sample_01",
        "companyName": "样例公司",
        "isDisabled": true
    },
    "model": {
        "jp.co.intra_mart.master.company.detail.main": {
            "defaultLocale": "zh_CN",
            "locale": "中文 (中国)",
            "companyName": "样例公司",
            "departmentCd": "8ebr2ffj6ignacr",
            "departmentSetCd": "",
            "companyCd": "comp_sample_01",
            "sortKey": "0",
            "localedata": {
                "zh_CN": {
                    "departmentName": "testY",
                    "departmentShortName": "testY",
                    "departmentSearchName": "",
                    "countryCd": "",
                    "zipCode": "",
                    "address1": "",
                    "address2": "",
                    "address3": "",
                    "telephoneNumber": "",
                    "extensionNumber": "",
                    "faxNumber": "",
                    "extensionFaxNumber": "",
                    "emailAddress1": "",
                    "emailAddress2": "",
                    "url": "",
                    "notes": ""
                }
            },
            "isUpdated": "true"
        },
        "jp.co.intra_mart.master.company.detail.attach": {
            "termsInfo": [],
            "removedParents": []
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
            "companyCd": "comp_sample_01",
            "departmentCd": "dept_sample_10",
            "departmentSetCd": "comp_sample_01",
            "displayName": "样例部门01",
            "description": "样例部门01"
        },
        "recordInfo": null,
        "parameter": {
            "isEdit": "false"
        }
    }
}

#IF: 获得部门详细信息
Url：ndims/ui_if/master/maintenance/department/getdep
默认参数Json模板：src/main/webapp/ndims/ui_if/department/json/getdep.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.company.detail",
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
            "termCd": "8ebr2fjqmignw",
            "startDate": "1900/01/01 00:00:00",
            "endDate": "3000/01/01 00:00:00",
            "deleteFlag": false
        },
        "parentInfo": null,
        "recordInfo": {
            "companyCd": "comp_sample_01",
            "departmentCd": "8ebr2ffj6ignacr",
            "departmentSetCd": "comp_sample_01",
            "displayName": "test",
            "description": "test"
        },
        "parameter": {
            "isEdit": "true"
        }
    }
}

#IF: 更新部门信息
Url：ndims/ui_if/master/maintenance/department/setdep
默认参数Json模板：src/main/webapp/ndims/ui_if/department/json/setdep.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.company.detail",
        "baseDate": "2017/03/21",
        "localeId": "zh_CN",
        "companyCd": "comp_sample_01",
        "companyName": "样例公司",
        "isDisabled": true
    },
    "model": {
        "jp.co.intra_mart.master.company.detail.main": {
            "defaultLocale": "zh_CN",
            "locale": "中文 (中国)",
            "companyName": "样例公司",
            "departmentCd": "8ebr2ffj6ignacr",
            "departmentSetCd": "comp_sample_01",
            "companyCd": "comp_sample_01",
            "sortKey": "0",
            "localedata": {
                "zh_CN": {
                    "departmentName": "testY",
                    "departmentShortName": "testY",
                    "departmentSearchName": "",
                    "countryCd": "",
                    "zipCode": "",
                    "address1": "",
                    "address2": "",
                    "address3": "",
                    "telephoneNumber": "",
                    "extensionNumber": "",
                    "faxNumber": "",
                    "extensionFaxNumber": "",
                    "emailAddress1": "",
                    "emailAddress2": "",
                    "url": "",
                    "notes": ""
                }
            },
            "isUpdated": "false"
        },
        "jp.co.intra_mart.master.company.detail.attach": {
            "termsInfo": [
                
            ],
            "removedParents": [
                
            ]
        }
    },
    "parameters": {
        "isEdit": true,
        "term": {
            "termCd": "8ebsbh2niyuu6",
            "startDate": "1900/01/01 00:00:00",
            "endDate": "3000/01/01 00:00:00",
            "deleteFlag": false
        },
        "parentInfo": null,
        "recordInfo": {
            "companyCd": "comp_sample_01",
            "departmentCd": "8ebr2ffj6ignacr",
            "departmentSetCd": "comp_sample_01",
            "displayName": "testY",
            "description": "testY"
        },
        "parameter": {
            "isEdit": "true"
        }
    }
}


#IF: 删除部门信息
Url：ndims/ui_if/master/maintenance/department/removedep
默认参数Json模板：src/main/webapp/ndims/ui_if/department/json/removedep.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.company.detail",
        "baseDate": "2017/03/20",
        "localeId": "zh_CN",
        "companyCd": "comp_sample_01",
        "companyName": "样例公司",
        "isDisabled": true
    },
    "model": {
        "jp.co.intra_mart.master.company.detail.main": {
            "defaultLocale": "zh_CN",
            "locale": "中文 (中国)",
            "companyName": "样例公司",
            "departmentCd": "8ebr2ffj6ignacr",
            "departmentSetCd": "comp_sample_01",
            "companyCd": "comp_sample_01",
            "sortKey": "0",
            "localedata": {
                "zh_CN": {
                    "departmentName": "test",
                    "departmentShortName": "test",
                    "departmentSearchName": "",
                    "countryCd": "",
                    "zipCode": "",
                    "address1": "",
                    "address2": "",
                    "address3": "",
                    "telephoneNumber": "",
                    "extensionNumber": "",
                    "faxNumber": "",
                    "extensionFaxNumber": "",
                    "emailAddress1": "",
                    "emailAddress2": "",
                    "url": "",
                    "notes": ""
                }
            },
            "isUpdated": "false"
        },
        "jp.co.intra_mart.master.company.detail.attach": {
            "termsInfo": [],
            "removedParents": []
        }
    },
    "parameters": {
        "isEdit": true,
        "term": {
            "termCd": "8ebr2fjqmignw",
            "startDate": "1900/01/01 00:00:00",
            "endDate": "3000/01/01 00:00:00",
            "deleteFlag": false
        },
        "parentInfo": null,
        "recordInfo": {
            "companyCd": "comp_sample_01",
            "departmentCd": "8ebr2ffj6ignacr",
            "departmentSetCd": "comp_sample_01",
            "displayName": "test",
            "description": "test"
        },
        "parameter": {
            "isEdit": "true"
        }
    }
}


#IF: 检索部门所属用户信息
Url：ndims/ui_if/master/maintenance/department/searchdepuser
默认参数Json模板：src/main/webapp/ndims/ui_if/department/json/searchdepuser.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.company.treeview",
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


#IF: 在部门中追加员工
Url：ndims/ui_if/master/maintenance/department/joinusers
默认参数Json模板：src/main/webapp/ndims/ui_if/department/json/joinusers.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.company.treeview",
        "baseDate": "2017/03/20",
        "localeId": "zh_CN",
        "companyCd": "comp_sample_01",
        "companyName": "样例公司",
        "isDisabled": true
    },
    "parent": {
        "companyCd": "comp_sample_01",
        "departmentCd": "dept_sample_10",
        "departmentSetCd": "comp_sample_01",
        "displayName": "样例部门01",
        "description": "样例部门01"
    },
    "children": [
        {
            "userCd": "kezhang01",
            "displayName": "科长01"
        }
    ]
}


#IF: 在部门中去除员工
Url：ndims/ui_if/master/maintenance/department/detachuser
默认参数Json模板：src/main/webapp/ndims/ui_if/department/json/detachuser.json
参数JsonSample：
{
    "basicInfo": {
        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.company.attachterms.serverside",
        "baseDate": "2017/03/20",
        "localeId": "zh_CN",
        "companyCd": "comp_sample_01",
        "companyName": "样例公司",
        "isDisabled": true
    },
    "recordInfo": {
        "userCd": "buzhang01",
        "departmentMain": false,
        "description": "部长01",
        "displayName": "部长01",
        "deleteFlag": false,
        "companyCd": "comp_sample_01",
        "departmentSetCd": "comp_sample_01",
        "departmentCd": "dept_sample_10"
    },
    "parentInfo": {
        "companyCd": "comp_sample_01",
        "departmentCd": "dept_sample_10",
        "departmentSetCd": "comp_sample_01",
        "displayName": "样例部门01",
        "description": "样例部门01"
    },
    "parameter": {
        "termsInfo": []
    }
}