/**
 * 部门_所属职位
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
				"master/plugins/maintenance/company_post/treeview/frame/post", "getLeaves",
				dataJson.basicInfo, dataJson.composite, dataJson.start, dataJson.count, dataJson.sortTarget, dataJson.sortDirection);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 部门_职位新建更新
 */
function setDetail(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		Debug.console(dataJson);
		var result = Content.executeFunction(
				"master/maintenance/control/detail/detail", "setDetail",
				dataJson.basicInfo, dataJson.model, dataJson.parameters);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 部门_职位详细
 */
function getDetail(request) {
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
 * 部门_职位删除
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
				dataJson.basicInfo, dataJson.model, dataJson.parameters);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}
