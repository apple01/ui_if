/**
 * 集团_叶
 */
function getSubCompanyGroup(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		Debug.console(dataJson);
		var result = Content.executeFunction(
				"master/plugins/maintenance/company_group/treeview/frame/companygroup", "getSubCompanyGroup",
				dataJson.basicInfo, dataJson.composite);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}


	/**
 * 集团_所属公司
 */
function getLeaves(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		Debug.console(dataJson);
		var result = Content.executeFunction(
				"master/plugins/maintenance/company_group/treeview/frame/companygroup", "getLeaves",
				dataJson.basicInfo, dataJson.composite, dataJson.start, dataJson.count, dataJson.sortTarget, dataJson.sortDirection);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 集团_所属公司追加
 */
function joinCompanys(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		Debug.console(dataJson);
		var result = Content.executeFunction(
				"master/plugins/maintenance/company_group/treeview/frame/companygroup", "joinCompanys",
				dataJson.basicInfo, dataJson.parent, dataJson.children);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 集团_所属公司解除
 */
function detachCompany(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		Debug.console(dataJson);
		var result = Content.executeFunction(
				"master/maintenance/control/attach/attach_terms", "detach",
				dataJson.basicInfo, dataJson.recordInfo, dataJson.parentInfo, dataJson.parameter);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}
