// iAP用户、账号主数据管理画面后台处理UI

/**
 * 新增和保存用户、账号信息 （包含用户的账号，给用户设定所属组织、身份等）
 */
function setUser(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');

	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		Debug.console(dataJson);
		var userCd = dataJson.modelInfo["jp.co.intra_mart.master.user.detail.main"].userCd;
		var strBaseDate = dataJson.basicInfo.baseDate;
		var baseDate = new Date();
		if (strBaseDate != null && strBaseDate.trim().length > 0) {
			baseDate = DateTimeFormatter.parseToDate("yyyy/MM/dd", strBaseDate);
		}
		var deptermsInfo = dataJson.modelInfo["jp.co.intra_mart.master.user.detail.departmentAttach"].termsInfo;
		var departmentAttach = [];
		for (var i = 0; i < deptermsInfo.length; i++) {
			var depterm = deptermsInfo[i];
			var info = {}
			info.companyCd = depterm.companyCd;
			info.departmentSetCd = depterm.departmentSetCd;
			info.departmentCd = depterm.departmentCd;
			info.departmentMain = depterm.departmentMain;
			info.post = depterm.post;
			departmentAttach.push(info);
		}
		
		var result = Content.executeFunction(
				"master/maintenance/control/detail/detail", "setDetail",
				dataJson.basicInfo, dataJson.modelInfo, dataJson.parameters);
		
		Debug.console(departmentAttach);
		
		if (result.error === false) {
			for (var i = 0; i < departmentAttach.length; i++) {
				var info = departmentAttach[i]; 
				var ret = setUserMainDepPost(userCd, 
					info.companyCd, 
					info.departmentSetCd, 
					info.departmentCd,
					baseDate,
					info.departmentMain,
					info.post);
				if (ret.error) {
					throw "用户信息保存成功，但是用户主所属、职务信息保存时发生错误。[" + ret.message + "]";
				}
			}
		}
		
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}

}

/**
 * 获得用户信息 （包含用户的账号，给用户设定所属组织、身份等）
 */
function getUser(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		Debug.console(dataJson);
		var result = Content.executeFunction(
				"master/maintenance/control/detail/detail", "getDetail",
				dataJson.basicInfo, dataJson.model, dataJson.parameters);

		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 删除用户信息
 * 
 */
function remove(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		Debug.console(dataJson);
		var result = Content.executeFunction(
				"master/maintenance/control/detail/detail", "remove",
				dataJson.basicInfo, dataJson.modelInfo, dataJson.parameters);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 检索用户信息
 * 
 */
function search(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		Debug.console(dataJson);
		var result = Content.executeFunction(
				"master/maintenance/control/search/search", "search",
				dataJson.extensionPoint, dataJson.pluginId, dataJson.basicInfo,
				dataJson.model, dataJson.start, dataJson.count,
				dataJson.sortTarget, dataJson.sortDirection);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 设定用户的主所属和所属职务信息
 * 
 */
function setUserMainDepPost(userCd, companyCd, departmentSetCd, departmentCd, baseDate, isMain, post) {

	Debug.print("userCd:" + userCd);
	Debug.print("companyCd:" + companyCd);
	Debug.print("departmentSetCd:" + departmentSetCd);
	Debug.print("departmentCd:" + departmentCd);
	Debug.print("isMain:" + isMain);
	Debug.print("post:" + post);

	var result = null;
	var companyManager = new IMMCompanyManager();
	var userBizKey = new Object();
	userBizKey.userCd = userCd;
	var departmentBizKey = new Object();
	departmentBizKey.companyCd = companyCd;
	departmentBizKey.departmentSetCd = departmentSetCd;
	departmentBizKey.departmentCd = departmentCd;
	result = companyManager.getUserAttachTerm(departmentBizKey, userBizKey, baseDate);
	if (result.error === true) {
		return result;
	}
	var termInfo = result.data;
	if (isMain) {
		// 设定用户主所属信息
		var departmentDatas = companyManager.getDepartmentSetAll();
		result = companyManager.setUserAttach(departmentBizKey, userBizKey,
				termInfo, isMain);
	}
	
	// 设定用户职务信息
	if (post === undefined || post=== null) {
		return result;
	}
	var postCds = post.split(',');
	var postCd = "";
	for (var i = 0; i < postCds.length; i++) {
		postCd = postCds[i].trim();
		var postBizKey = {};
		postBizKey.companyCd = companyCd;
		postBizKey.departmentSetCd = departmentSetCd;
		postBizKey.departmentCd = departmentCd;
		postBizKey.postCd = postCd;
		result = companyManager.setCompanyPostAttach(departmentBizKey,	userBizKey, postBizKey, termInfo.termCd);
	}

	return result;
}

