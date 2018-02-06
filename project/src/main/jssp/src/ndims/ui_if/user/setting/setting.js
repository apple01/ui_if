/**
 * 密码变更
 */
function passwordUpdate(request) {

	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	Debug.console(dataJson);
	var result = Content.executeFunction("user/settings/password/ajax/update_password", "init", dataJson);
}

/**
 * 日历变更
 */
function calendarUpdate(request) {

	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	var result = Content.executeFunction("user/settings/calendar/ajax/update_calendar", "init", dataJson);
}

/**
 * 区域变更
 */
function localeUpdate(request) {

	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	Debug.console(dataJson);
	var result = Content.executeFunction("user/settings/locale/ajax/update_locale", "init", dataJson);
}

/**
 * 格式变更
 */
function formatSetUpdate(request) {

	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	Debug.console(dataJson);
	var result = Content.executeFunction("user/settings/date_and_time_display_format/ajax/update_format_set", "init", dataJson);
}

/**
 * 档案变更
 */
function profileUpdate(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	Debug.print(strData); 
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	// Debug.console(dataJson);
	var result = Content.executeFunction("user/settings/profile/profile_edit", "setUserSelfProfile", dataJson);
	response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
}

/**
 * 菜单变更
 */
function menuDisplayUpdate(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	// Debug.console(dataJson);
	var result = Content.executeFunction("user/settings/menu_display/ajax/update_menu_display", "init", dataJson);
}

/**
 * 数值形式变更
 */
function numberFormatUpdate(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	var result = Content.executeFunction("user/settings/number_format/ajax/update_number_format", "init", dataJson);
}

/**
 * 日历变更初始化
 */
function calendarInit(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var content = new Packages.cn.com.intra_mart.system.display.Content("user/settings/calendar/views/calendar_update");
	var result = content.getAllBindData(request);
	response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
}

/**
 * 区域变更初始化
 */
function localeInit(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var content = new Packages.cn.com.intra_mart.system.display.Content("user/settings/locale/views/locale_update");
	var result = content.getAllBindData(request);
	response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
}

/**
 * 日期与时刻的形式变更初始化
 */
function formatSetInit(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var content = new Packages.cn.com.intra_mart.system.display.Content("user/settings/date_and_time_display_format/views/date_and_time_display_format_update");
	var result = content.getAllBindData(request);
	response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
}

/**
 * 档案变更初始化
 */
function profileInit(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var content = new Packages.cn.com.intra_mart.system.display.Content("user/settings/profile/profile_edit");
	var result = content.getAllBindData(request);
	response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
}

/**
 * 菜单显示变更初始化
 */
function menuDisplayInit(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var content = new Packages.cn.com.intra_mart.system.display.Content("user/settings/menu_display/views/menu_display_update");
	var result = content.getAllBindData(request);
	response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
}

/**
 * 数值形式变更初始化
 */
function numberFormatInit(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var content = new Packages.cn.com.intra_mart.system.display.Content("user/settings/number_format/views/number_format_update");
	var result = content.getAllBindData(request);
	response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
}

/**
 * 密码重置发送
 */
function pwdResetSend(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	// Debug.console(dataJson);
	var result = Content.executeFunction("user/reminder/ajax/send_mail", "init", dataJson);
	response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
}

/**
 * 密码重置
 */
function pwdReset(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	var dataJson = Procedure.NdimsJson.parseJSON(strData);
	// Debug.console(dataJson);
	var result = Content.executeFunction("user/reminder/ajax/reset_password", "init", dataJson);
	response.sendMessageBodyString(Procedure.NdimsJson.toJSONString(result));
}
