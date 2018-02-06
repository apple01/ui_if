
/**
 * 新建公司
*/
function newDepartment(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		Debug.console(dataJson);
		var result = Content.executeFunction(
				"im_master/maintenance/company/company_new", "setDepartment",
				dataJson);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 更新公司
 */
function editDepartment(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		Debug.console(dataJson);
		var result = Content.executeFunction(
				"im_master/maintenance/company/company_edit", "setDepartment",
				dataJson);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 删除公司
 */
function removeCompany(request) {
	Debug.console(request);
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		var result = Content.executeFunction(
				"im_master/maintenance/company/company_edit", "removeCompany",
				dataJson);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 获取公司信息
 */
function getCompany(request) {
	Debug.console(request);
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		var result = Content.executeFunction("im_master/maintenance/company/parts/get_department","getDepartment", dataJson.companyCd);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 获取公司信息
 */
function getCompany(request) {
	Debug.console(request);
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		var result = Content.executeFunction("im_master/maintenance/company/parts/get_department","getDepartment", dataJson.companyCd);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 检索公司信息
 */
function searchCompany(request) {
	Debug.console(request);
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		var result = Content.executeFunction("im_master/maintenance/company/company_list_data","getList", dataJson);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}
