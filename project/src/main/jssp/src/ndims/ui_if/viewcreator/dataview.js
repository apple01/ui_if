/**
 * ViewCreator : 数据参照列表
 */
function list(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var content = new Packages.cn.com.intra_mart.system.display.Content("viewcreator/views/dataview/list");
	var result = content.getAllBindData(request);
	response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
}