var $apiConf = Content.executeFunction("ndims/common/web_api/webapiConf", "getJsWebApiConf");

/**
 * 
 */
function callApi(request) {
	Debug.print("<call js web api>");
	
	// 默认返回值
	var result = {
		"error" : false , "message" : "", "data" : null
	};

	// 接受参数
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print("getMessageBodyAsString:" + strData);
	
	var apiObjName = "";	// API对象名称
	var apiFunName = "";	// API函数名称
	
	var resultData = null; // API返回值
	
	try {
		// 获得调用的API全称
		var apiName = request.apiname;

		// 校验(调用对象的API名称是否指定)
		if (!apiName || apiName == null || apiName.length == 0) {
			throw ("未设定API名称");
		}
		
		// 从API全称中分解API对象名和API函数名
		if (apiName.indexOf(".") >= 0) {
			apiName = apiName.split(".");
			apiObjName = apiName[0];
			apiFunName = apiName[1];
			Debug.print("apiObjName:" + apiObjName);
			Debug.print("apiFunName:" + apiFunName);
		} else {
			throw ("API名称信息指定不正确");
		}
		// 校验
		if (!apiObjName || apiObjName == null || apiObjName.length == 0) {
			throw ("API名称信息指定不正确");
		} else {
			if ($apiConf[apiObjName] == null) {
				throw ("指定的API不存在");
			}
		}
		var apiObjDef = $apiConf[apiObjName];
		Debug.console(apiObjDef);
		
		if (!apiFunName || apiFunName == null || apiFunName.length == 0) {
			throw ("API名称信息指定不正确");
		} else {
			if (apiObjDef.methods[apiFunName] == null) {
				throw ("指定名称的API不存在");
			}
		}
		
		var apiFunDef = apiObjDef.methods[apiFunName];
		Debug.console(apiFunDef);
		
		// 权限校验
		if (apiFunDef.authz != null && 
				apiFunDef.authz.uri != null && apiFunDef.authz.uri > 0 && 
				apiFunDef.authz.action != null && apiFunDef.authz.action > 0) {
			// 如果在API函数级别设定的权限认定，则以函数级别的权限为优先校验
			var authorize = Content.executeFunction("ndims/common/web_api/authorize", "authorize", apiFunDef.authz.uri, apiFunDef.authz.action);
			if (!authorize) {
				throw ("没有指定API的调用权限");
			}
		} else {
			// 个别API函数级别权限认定未定义时，则以API对象级别的权限定义进行校验
			if (apiObjDef.authz != null && 
				apiObjDef.authz.uri != null && apiObjDef.authz.uri > 0 && 
				apiObjDef.authz.action != null && apiObjDef.authz.action > 0) {
				var authorize = Content.executeFunction("ndims/common/web_api/authorize", "authorize", apiObjDef.authz.uri, apiObjDef.authz.action);
				if (!authorize) {
					throw ("没有指定API的调用权限");
				}
			}
		}
		
		// 解析参数
		var dataJson = {};
		if (strData != undefined && strData != null && strData.length > 0) {
			dataJson = ImJson.parseJSON(strData);
		}
		Debug.print("客户端提交的参数");
		Debug.console(dataJson);
		
		// 解析API对象的construct参数
		var apiObjParams = {};
		if (apiObjDef.construct != null && apiObjDef.construct.params != null) {
			// 定义默认参数
			for (let param in apiObjDef.construct.params) {
				if (typeof(apiObjDef.construct.params[param]) === "function") {
					apiObjParams[param] = apiObjDef.construct.params[param]();
				} else {
					apiObjParams[param] = apiObjDef.construct.params[param];
				}
			}
		}
		if (dataJson.construct != null && dataJson.construct.params != null) {
			// 指定调用参数时，指定调用的参数
			for (let param in dataJson.construct.params) {
				apiObjParams[param] = dataJson.construct.params[param];
			}
		}
		Debug.print("生成API对象用的参数");
		Debug.console(apiObjParams);
		// 创建API对象构造参数用表单式生成用数组
		var apiObjParamsExp = [];
		for (let param in apiObjParams) {
			apiObjParamsExp.push("apiObjParams['" + param +"']");
		}

		// 解析API函数的参数
		var apiFunParams = {};
		if (apiFunDef.params != null && apiFunDef.params != null) {
			// 定义默认参数
			for (let param in apiFunDef.params) {
				if (typeof(apiFunDef.params[param]) === "function") {
					apiFunParams[param] = apiFunDef.params[param]();
				} else {
					apiFunParams[param] = apiFunDef.params[param];
				}
			}
		}
		if (dataJson.params != null && dataJson.params != null) {
			// 指定调用参数时，指定调用的参数
			for (let param in dataJson.params) {
				apiFunParams[param] = dataJson.params[param];
			}
		}
		Debug.print("生成API函数用的参数");
		Debug.console(apiFunParams);
		// 创建API对象构造参数用表单式生成用数组
		var apiFunParamsExp = [];
		for (let param in apiFunParams) {
			apiFunParamsExp.push("apiFunParams['" + param +"']");
		}

		if (apiObjName === "global") {
			// 全局API时
			let exp = apiFunName + "(" + apiFunParamsExp.join(", ") + ")";
			Debug.print("call global apiObjFunExp:" + exp);
			resultData = eval(exp);
		} else {
			// 非全局API时
			// 如果API对象为非静态对象时，创建其实例
			if (apiObjDef.isStatic != true) {
				// 调用非静态API
				let apiObj = null;
				let exp = "new " +  apiObjName + "(" + apiObjParamsExp.join(", ") + ")";
				Debug.print("new apiObjExp:" + exp);
				// 创建API对象实例
				apiObj = eval(exp);
				if (apiObj == null) {
					throw ("API调用失败，不能创建API对象");
				}
				// 基于API对象实例调用API函数
				exp = "apiObj." + apiFunName + "(" + apiFunParamsExp.join(", ") + ")";
				Debug.print("call apiObjFunExp:" + exp);
				resultData = eval(exp);
			} else {
				// 基于API对象调用静态API函数
				let exp = "";
				if (apiObjParamsExp.length < 1) {
					exp =  apiObjName + "." + apiFunName + "(" + apiFunParamsExp.join(", ") + ")";
				} else {
					exp =  apiObjName + "(" + apiObjParamsExp.join(", ") + ")." + apiFunName + "(" + apiFunParamsExp.join(", ") + ")";
				}
				Debug.print("call static apiObjFunExp:" + exp);
				resultData = eval(exp);
			}
		}
		
		// 设定返回
		if (apiFunDef.rtn != null  && apiFunDef.rtn.isDirectRtn === true) {
			// 返回值直接返回
			if (resultData == null) {
				throw ("API调用失败，返回值为null");
			}
			response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(resultData));
		} else {
			// 返回值包含在result.data中进行返回
			result.data =  resultData;
			response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
		}
		
	} catch (e) {
		Debug.console(e);
		response.sendMessageBodyString("{'error' : true, 'message' : '" + e + "'}");
	}
	
}

