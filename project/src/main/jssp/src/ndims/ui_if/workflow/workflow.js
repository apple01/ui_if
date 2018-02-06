/**
 * 工作流_申请一览
 */
function apply_list(request) {

	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = ImJson.parseJSON(strData);
		Debug.console(dataJson);
		dataJson.extension.imwCallOriginalParams = URL.encode(ImJson.toJSONString(dataJson.extension.imwCallOriginalParams));
		var result = Content.executeFunction("im_workflow/user/apply/apply_list_data", "getList", dataJson);
		
		// 进行结果转换
		if (result) {
			for (var i = 0; i < result.data.length; i++) {
				var record = result.data[i];
				var listPageCol_Apply = record.listPageCol_Apply;
				listPageCol_Apply = listPageCol_Apply.replace(/<a.*>/g, "");
				listPageCol_Apply = listPageCol_Apply.replace(/<input type='hidden' value='/g, "");
				listPageCol_Apply = listPageCol_Apply.split("'>");
				var flowId = listPageCol_Apply[0];
				var nodeId = listPageCol_Apply[1];
				result.data[i].flowId = flowId;
				result.data[i].nodeId = nodeId;;
				result.data[i].flowName = result.data[i].listPageCol_FlowName;
				result.data[i].flowVersionNote = result.data[i].listPageCol_FlowVersionNote;
				
				delete result.data[i].listPageCol_Apply;
				delete result.data[i].listPageCol_FlowName;
				delete result.data[i].listPageCol_FlowVersionNote;
				delete result.data[i].listPageCol_Flow;
			}
		}
		
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 工作流_临时保存一览
 */
function temporary_save_list_data(request) {

	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = ImJson.parseJSON(strData);
		Debug.console(dataJson);
		dataJson.extension.imwCallOriginalParams = URL.encode(ImJson.toJSONString(dataJson.extension.imwCallOriginalParams));
		var result = Content.executeFunction("im_workflow/user/apply/temporary_save_list_data", "getList", dataJson);
		
		// 进行结果转换
		if (result) {
			for (var i = 0; i < result.data.length; i++) {
				var record = result.data[i];
				var listPageCol_Apply = record.listPageCol_Apply;
				listPageCol_Apply = listPageCol_Apply.replace(/<a.*>/g, "");
				listPageCol_Apply = listPageCol_Apply.replace(/<input type='hidden' value='/g, "");
				listPageCol_Apply = listPageCol_Apply.split("'>");
				record.flowId = listPageCol_Apply[0];
				record.nodeId = listPageCol_Apply[1];
				record.userDataId = listPageCol_Apply[2];
				record.actUserCode = listPageCol_Apply[3];
				record.applyBaseDate = listPageCol_Apply[4];
				
				var listPageCol_BaseDate = record.listPageCol_BaseDate;
				listPageCol_BaseDate = listPageCol_BaseDate.replace(/<a.*>/g, "");
				listPageCol_BaseDate = listPageCol_BaseDate.replace(/<input type='hidden' value='/g, "");
				listPageCol_BaseDate = listPageCol_BaseDate.split("'>");
				record.authUserCode = listPageCol_BaseDate[3];
				
				record.baseDate = record.listPageCol_BaseDate.match(/span>&nbsp;(.*)</i);
				record.baseDate = record.baseDate[1];
				
				record.applyUser = record.listPageCol_ApplyUser;
				record.flowName = record.listPageCol_FlowName;
				record.matterName = record.listPageCol_MatterName;
				record.temporarySaveComment = record.listPageCol_TemporarySaveComment;

				delete record.listPageCol_Apply;
				delete record.listPageCol_FlowName;
				delete record.listPageCol_BaseDate;
				delete record.listPageCol_ApplyUser;
				delete record.listPageCol_MatterName;
				delete record.listPageCol_TemporarySaveComment;
				delete record.listPageCol_Flow;
				delete record.listPageCol_MatterDelete;
			}
		}
		
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 工作流_未处理一览
 */
function process_list(request) {

	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = ImJson.parseJSON(strData);
		Debug.console(dataJson);
		dataJson.extension.imwCallOriginalParams = URL.encode(ImJson.toJSONString(dataJson.extension.imwCallOriginalParams));
		var result = Content.executeFunction("im_workflow/user/process/process_list_data", "getList", dataJson);
		
		
		// 进行结果转换
		if (result) {
			for (var i = 0; i < result.data.length; i++) {
				var record = result.data[i];
				var listPageCol_Proc = record.listPageCol_Proc;
				listPageCol_Proc = listPageCol_Proc.replace(/<a.*>/g, "");
				listPageCol_Proc = listPageCol_Proc.replace(/<input type='hidden' value='/g, "");
				listPageCol_Proc = listPageCol_Proc.split("'>");
				record.systemMatterId = listPageCol_Proc[0];
				record.userDataId = listPageCol_Proc[1];
				record.nodeId = listPageCol_Proc[2];
				record.pageType = listPageCol_Proc[3];

				record.priority = record.listPageCol_Priority.match(/title=(.*)></i);
				record.priority = record.priority[1];
				record.matterNumber = record.listPageCol_MatterNumber;
				record.matterName = record.listPageCol_MatterName;
				record.applyDate = record.listPageCol_ApplyDate;
				record.applyUser = record.listPageCol_ApplyUser;
				record.flowName = record.listPageCol_FlowName;
				record.nodeName = record.listPageCol_NodeName;
				record.baseDate = record.listPageCol_BaseDate;
				record.status =  record.listPageCol_Status.match(/title=(.*)></i);
				record.status = record.status[1];
				record.arriveDate = record.listPageCol_ArriveDate;
				record.procAuth =  record.listPageCol_ProcAuth.match(/title=(.*)></i);
				record.procAuth =  record.procAuth[1];
				record.procLimit = record.listPageCol_ProcLimit;
				
				delete record.listPageCol_Proc;
				delete record.listPageCol_Transfer;
				delete record.listPageCol_Priority;
				delete record.listPageCol_MatterNumber;
				delete record.listPageCol_MatterName;
				delete record.listPageCol_BaseDate;
				delete record.listPageCol_ApplyDate;
				delete record.listPageCol_ApplyUser;
				delete record.listPageCol_FlowName;
				delete record.listPageCol_NodeName;
				delete record.listPageCol_Status;
				delete record.listPageCol_ArriveDate;
				delete record.listPageCol_ProcLimit;
				delete record.listPageCol_ProcAuth;
				delete record.listPageCol_Detail;
				delete record.listPageCol_Flow;
				delete record.listPageCol_History;
				
			}
		}
		
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 工作流_未完了一览
 */
function actv_proc_list(request) {

	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = ImJson.parseJSON(strData);
		
		dataJson.extension.imwCallOriginalParams = URL.encode(ImJson.toJSONString(dataJson.extension.imwCallOriginalParams));
		Debug.console(dataJson);
		var result = Content.executeFunction("im_workflow/user/cpl_proc/actv_proc_list_data", "getList", dataJson);
		
		// 进行结果转换
		if (result) {
			for (var i = 0; i < result.data.length; i++) {
				var record = result.data[i];
				var listPageCol_PullBack = record.listPageCol_PullBack;
				listPageCol_PullBack = listPageCol_PullBack.replace(/<a.*>/g, "");
				listPageCol_PullBack = listPageCol_PullBack.replace(/<input type='hidden' value='/g, "");
				listPageCol_PullBack = listPageCol_PullBack.split("'>");
				record.systemMatterId = listPageCol_PullBack[0];
				record.userDataId = listPageCol_PullBack[1];
				record.flowId = listPageCol_PullBack[2];
				record.applyBaseDate = listPageCol_PullBack[3];

				record.priority = record.listPageCol_Priority.match(/title=(.*)></i);
				record.priority = record.priority[1];

                record.matterNumber = record.listPageCol_MatterNumber;
                record.matterName = record.listPageCol_MatterName;
                record.baseDate = record.listPageCol_BaseDate;
                record.applyDate = record.listPageCol_ApplyDate;
                record.applyUser = record.listPageCol_ApplyUser;
                record.flowName = record.listPageCol_FlowName;
                record.finalProcDate = record.listPageCol_FinalProcDate;

				delete record.listPageCol_PullBack;
				delete record.listPageCol_Priority;
				delete record.listPageCol_MatterNumber;
				delete record.listPageCol_MatterName;
				delete record.listPageCol_BaseDate;
				delete record.listPageCol_ApplyDate;
				delete record.listPageCol_ApplyUser;
				delete record.listPageCol_FlowName;
				delete record.listPageCol_FinalProcDate;
				delete record.listPageCol_Detail;
				delete record.listPageCol_Flow;
				delete record.listPageCol_History;
			}
		}
		
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}
/**
 * 工作流_完了一览
 */
function cpl_proc_list(request) {

	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = ImJson.parseJSON(strData);

		dataJson.extension.imwCallOriginalParams = URL.encode(ImJson.toJSONString(dataJson.extension.imwCallOriginalParams));
		Debug.console(dataJson);
		var result = Content.executeFunction("im_workflow/user/cpl_proc/cpl_proc_list_data", "getList", dataJson);
		
		// 进行结果转换
		if (result) {
			for (var i = 0; i < result.data.length; i++) {
				var record = result.data[i];
				
				record.priority = record.listPageCol_Priority.match(/title=(.*)></i);
				record.priority = record.priority[1];
				record.matterNumber = record.listPageCol_MatterNumber;
				record.matterName = record.listPageCol_MatterName;
				record.baseDate = record.listPageCol_BaseDate;
				record.applyDate = record.listPageCol_ApplyDate;
				record.applyUser = record.listPageCol_ApplyUser;
				record.flowName = record.listPageCol_FlowName;
				record.endDate = record.listPageCol_EndDate;
				record.endStatus = record.listPageCol_EndStatus.match(/title=(.*)></i);
				record.endStatus = record.endStatus[1];

				var listPageCol_Detail = record.listPageCol_Detail;
				listPageCol_Detail = listPageCol_Detail.replace(/<a.*>/g, "");
				listPageCol_Detail = listPageCol_Detail.replace(/<input type='hidden' value='/g, "");
				listPageCol_Detail = listPageCol_Detail.split("'>");
				record.systemMatterId = listPageCol_Detail[0];
				record.userDataId = listPageCol_Detail[1];

				delete record.listPageCol_Priority;
				delete record.listPageCol_MatterNumber;
				delete record.listPageCol_MatterName;
				delete record.listPageCol_BaseDate;
				delete record.listPageCol_ApplyDate;
				delete record.listPageCol_ApplyUser;
				delete record.listPageCol_FlowName;
				delete record.listPageCol_EndDate;
				delete record.listPageCol_EndStatus;
				delete record.listPageCol_Detail;
				delete record.listPageCol_Flow;
				delete record.listPageCol_History;
				
			}
		}
		
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
	
}

/**
 * 工作流_临时保存案件删除
 */
function tempDelete(request) {
	Debug.print("<tempDelete>");
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData);
	try {
		var dataJson = ImJson.parseJSON(strData);
		Debug.console(dataJson);
		var result = Content.executeFunction("im_workflow/user/apply/apply_list", "actionDelete", dataJson);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 工作流_申请
 */
function apply(request) {
	Debug.print("<apply>");
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();

	try {
		var dataJson = ImJson.parseJSON(strData);
		if (dataJson.applyParams.tempDirKey == "") {
			dataJson.applyParams.tempDirKey = getTempDirKey();
		}
	Debug.print(dataJson);
		Debug.console(dataJson);
		var result = Content.executeFunction("im_workflow/common/proc/exec/apply_jssp", "apply", dataJson);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 工作流_处理
 */
function proc(request) {
	Debug.print("<proc>");
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	// Debug.print(strData);
	try {
		var dataJson = ImJson.parseJSON(strData);
		if (dataJson.procParams.tempDirKey == "") {
			dataJson.procParams.tempDirKey = getTempDirKey();
		}
		// Debug.console(dataJson);
		dataJson.userParams.imwCallOriginalParams = URL.encode(ImJson.toJSONString(dataJson.userParams.imwCallOriginalParams));
		var result = Content.executeFunction("im_workflow/common/proc/exec/proc_jssp", "approve", dataJson);
		response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '"
				+ e + "'  }");
	}
}

/**
 * 工作流临时目录_获得
 */
function getTempDirKey() {
	    var sessionInfo = Contexts.getAccountContext();
	    var groupId = sessionInfo.loginGroupId;
	    var userCode = sessionInfo.userCd;
    
        // ログインユーザID 配下の全ての一時領域ディレクトリを削除
        var attachFileManager = new WorkflowAttachFileManager(groupId, userCode);

        // 新たな添付ファイル一時領域ディレクトリを作成
        var result = attachFileManager.createTempDirKey();
        if (!result.resultFlag) {
            // 作成失敗
            return null;
        }
        return result.data; // 添付ファイル一時領域ディレクトリキー
}
