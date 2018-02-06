#有关公司管理画面的各种IF的使用说明Sample:
/src/main/webapp/ndims/ui_if/company/sample.html
Sample的URL：
http://localhost:8080/imart/ndims/ui_if/company/sample.html

#IF: 检索公司信息
Url：ndims/ui_if/master/maintenance/company/searchcompany
默认参数Json模板：src/main/webapp/ndims/ui_if/company/json/searchcompany.json
参数JsonSample：
{
    "extension" : {
        "searchCondition" : {
            "targetName" : true, 
            "targetKana" : false, 
            "targetCode" : true, 
            "keyword" : ""
        }
    }, 
    "sortIndex" : "companyCd", 
    "rowNum" : 10, 
    "sortOrder" : "asc", 
    "page" : 1
}

#IF: 新增公司
Url：ndims/ui_if/master/maintenance/company/addcompany
默认参数Json模板：src/main/webapp/ndims/ui_if/company/json/addcompany.json
参数JsonSample：
{
    "companyCd": "aaabbbccc",
    "sortKey": "0",
    "locales": {
        "zh_CN": {
            "departmentName": "test1111",
            "departmentShortName": "",
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
        },
        "en": {
            "departmentName": "",
            "departmentShortName": "",
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
        },
        "ja": {
            "departmentName": "",
            "departmentShortName": "",
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
    }
}

#IF: 获得公司信息
Url：ndims/ui_if/master/maintenance/company/getcompany
默认参数Json模板：src/main/webapp/ndims/ui_if/company/json/getcompany.json
参数JsonSample：
{
    "companyCd": "comp_sample_01"
}

#IF: 编辑公司信息
Url：ndims/ui_if/master/maintenance/company/setcompany
默认参数Json模板：src/main/webapp/ndims/ui_if/company/json/setcompany.json
参数JsonSample：
{
    "companyCd": "aaabbbccc",
    "sortKey": "0",
    "locales": {
        "zh_CN": {
            "departmentName": "testaaabbbccc",
            "departmentShortName": "testaaabbbccc",
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
        },
        "en": {
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
        },
        "ja": {
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
    }
}

#IF: 删除公司信息
Url：ndims/ui_if/master/maintenance/company/removecompany
默认参数Json模板：src/main/webapp/ndims/ui_if/company/json/removecompany.json
参数JsonSample：
[{"companyCd" : "aaabbbccc"}]
