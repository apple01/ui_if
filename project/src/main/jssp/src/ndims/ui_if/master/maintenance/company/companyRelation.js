var isAvailable 			= Procedure.imAppCom.isAvailable;
/**
 * 新建公司
*/
function newDepartment(request) {
//	Debug.console(request);
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);

		var result = Content.executeFunction(
				"im_master/maintenance/company/company_new", "setDepartment",
				dataJson);
		if (result.error) {
			response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));	
		}

		var accountContext = Contexts.getAccountContext();
		var defaultLocaleId = Content.executeFunction("im_master/maintenance/company/parts/get_locales", "getDefaultLocaleId");
		var appCommonManager = new AppCommonManager();
//		var companyGroup = {
//			defaultLocale   : defaultLocaleId,
//			companyGroupSetCd        : dataJson.companyCd,
//			companyGroupCd  : dataJson.companyCd,
//			termCd          : null,
//			startDate       : appCommonManager.getSystemStartDate().data,
//			endDate         : appCommonManager.getSystemEndDate().data,
//			deleteFlag      : "0",
//			sortKey         : Module.number.toInt(dataJson.sortKey),
//			recordUserCd    : accountContext.userCd,
//			recordDate      : new DateTime().getDate().data,
//			locales         : {}
//		};

		var companyGroup = {
			locales         : {}
		};
		for (let x in dataJson.locales) {
			companyGroup.locales[x] = {
				companyGroupName        : isAvailable(dataJson.locales[x].departmentName) ? dataJson.locales[x].departmentName : dataJson.locales[defaultLocaleId].departmentName,
				companyGroupShortName   : dataJson.locales[x].departmentShortName,
				companyGroupSearchName  : dataJson.locales[x].departmentSearchName,
				notes                : dataJson.locales[x].notes
			};
		};

		var cgm = new IMMCompanyGroupManager();
		var companyGroupSetCd = dataJson.companyCd;
		if (dataJson.parameters.parentInfo) {
//			cgm.searchCompanyGroupWithCompany(bizKey, condition, date, locale, start, count, isDisable);
			var db     = new TenantDatabase();
			result = db.select("SELECT distinct company_group_set_cd FROM imm_company_grp where company_group_cd = '" + dataJson.parameters.parentInfo.companyGroupCd + "'");
			 if (!result.error && result.data.length > 0) {
			     result.data.forEach(function(e) {
			    	 companyGroupSetCd = e.company_group_set_cd;
			    	 dataJson.parameters.parentInfo.companyGroupSetCd = e.company_group_set_cd;
			     });
			 } else {
					response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));	 
			 }
		}
		
		var group =	{
			    "basicInfo": {
			        "extensionPoint": "jp.co.intra_mart.foundation.master.setting.companygroup.detail",
			        "baseDate": new Date(),
			        "localeId": defaultLocaleId,
			        "companyCd": null,
			        "isDisabled": true
			    },
			    "model": {
			        "jp.co.intra_mart.master.companygroup.detail.main": {
			            "defaultLocale": defaultLocaleId,
			            "locale": "",
			            "companyGroupCd": dataJson.companyCd,
			            "companyGroupSetCd": companyGroupSetCd,
			            "sortKey": "0",
			            "localedata": companyGroup.locales,
			            "isUpdated": "true"
			        }
			    },
			    "parameters": dataJson.parameters
			};

		result = Content.executeFunction(
				"master/maintenance/control/detail/detail", "setDetail",
				group.basicInfo, group.model, group.parameters);
		
		if (result.error) {
			response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));	
		}

		var companyGroupBizKey ={};
		var companyBizKey ={};
		companyGroupBizKey.companyGroupCd = dataJson.companyCd;
		companyGroupBizKey.companyGroupSetCd = companyGroupSetCd;
		companyBizKey.companyCd = dataJson.companyCd;
		var termInfo = {};
		termInfo.deleteFlag = false;
		termInfo.startDate = appCommonManager.getSystemStartDate().data;
		termInfo.endDate = appCommonManager.getSystemEndDate().data;
		termInfo.sortKey = Module.number.toInt(dataJson.sortKey);
		cgm.setCompanyAttach(companyGroupBizKey, companyBizKey, termInfo);

		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}
