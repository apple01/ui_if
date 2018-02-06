/**
 * 角色_检索
 */
function getList(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		Debug.console(dataJson);
		var result = Content.executeFunction(
				"tenant/role/ajax/get_role_list", "getList",
				dataJson);
		
		// 整理不需要的内容
		if (result && result.data) {
			for (var i = 0; i < result.data.length; i++) {
				delete result.data[i].roleEdit;
				delete result.data[i].roleAccountList;
			}
		}
		
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 角色_新建
 */
function insert_role(request) {
	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	var result = Content.executeFunction("tenant/role/ajax/insert_role", "init", dataJson);
}

/**
 * 角色_更新
 */
function update_role(request) {
	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	var result = Content.executeFunction("tenant/role/ajax/update_role", "init", dataJson);
}

/**
 * 角色_删除
 */
function delete_role(request) {
	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	var result = Content.executeFunction("tenant/role/ajax/delete_role", "init", dataJson);
}

/**
 * 角色_详细
 */
function get_role(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = Procedure.NdimsJson.parseJSON(strData);
		var roleId = dataJson.roleId;
		var roleResult = new Object();
		var subRoleIds = [];
		var subRoleInfo;
		if (roleId != null) {
			// アカウントの情報を取得します
			var accountContext = Contexts.getAccountContext();
			var locale = accountContext.locale;
			// サブロール情報
			var roleInfoManager = new RoleInfoManager();
			var result = roleInfoManager.getSubRoleIds(roleId);

			if (result.error) {
			    var result = {
			    		error: true,
			    		successMessage: '',
			    		errorMessage: MessageManager.getMessage('MSG.E.IWP.TENANT.ROLE.GET.ROLE'),
			    		detailMessages: []
			    };
			    sendJSONString(result, result.errorMessage, result.detailMessages);
			}
			
			subRoleIds = result.data;
			if (subRoleIds.length != 0) {
				result = roleInfoManager.getRoleInfosByRoleIds(subRoleIds);
				if (result.error) {
				    var result = {
			    		error: true,
			    		successMessage: '',
			    		errorMessage: MessageManager.getMessage('MSG.E.IWP.TENANT.ROLE.GET.ROLE'),
			    		detailMessages: []
				    };
				    sendJSONString(result, result.errorMessage, result.detailMessages);
				}
				subRoleInfo = result.data;
			} else {
				subRoleInfo = [];
			}
			// 一覧データをセット.
			roleResult.subRoles = [];
			for (var i = 0; i < subRoleInfo.length; i++) {
				var roleObject = {
						id: subRoleInfo[i].roleId,
						roleDisplayName: subRoleInfo[i].displayName[locale],
						roleName: subRoleInfo[i].roleName
				}
				roleResult.subRoles.push(roleObject);
			}
	
			// ロール情報
			result = roleInfoManager.getRoleInfo(roleId);
			if (result.error || result.data == null) {
			    var result = {
		    		error: true,
		    		successMessage: '',
		    		errorMessage: MessageManager.getMessage('MSG.E.IWP.TENANT.ROLE.GET.ROLE'),
		    		detailMessages: []
			    };
			    sendJSONString(result, result.errorMessage, result.detailMessages);
			}

			roleResult.roleInfo = result.data;
		} else {
		    var result = {
	    		error: true,
	    		successMessage: '',
	    		errorMessage: MessageManager.getMessage('MSG.E.IWP.TENANT.ROLE.GET.ROLE'),
	    		detailMessages: []
		    };
		    sendJSONString(result, result.errorMessage, result.detailMessages);
		}
		
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(roleResult));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 角色_用户_追加
 */
function insert_role_account(request) {
	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	// 进行个别转换
	dataJson.userCds = Procedure.NdimsJson.toJSONString(dataJson.userCds);
	
	var result = Content.executeFunction("tenant/role/ajax/insert_role_account", "init", dataJson);
}

/**
 * 角色_用户_更新
 */
function update_role_account(request) {
	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	// 进行个别转换
	dataJson.userCds = Procedure.NdimsJson.toJSONString(dataJson.userCds);
	
	var result = Content.executeFunction("tenant/role/ajax/update_role_account", "init", dataJson);
}

/**
 * 角色_用户_删除
 */
function delete_role_account(request) {
	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	// 进行个别转换
	dataJson.userCds = Procedure.NdimsJson.toJSONString(dataJson.userCds);
	
	var result = Content.executeFunction("tenant/role/ajax/delete_role_account", "init", dataJson);
}

function sendJSONString(result, message, detailMessages) {
  message = (message == null) ? '' : message;
  detailMessages = (detailMessages == null) ? '' : detailMessages;

  var response = Web.getHTTPResponse();
  response.setContentType('application/json; charset=utf-8');
  response.sendMessageBodyString(Procedure.NdimsJson.toJSONString({
    error: result.error,
    successMessage: result.error ? '' : message,
    errorMessage: result.error ? message : '',
    detailMessages: result.error ? detailMessages : ''
  }));
}