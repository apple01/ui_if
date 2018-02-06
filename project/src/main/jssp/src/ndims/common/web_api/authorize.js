function authorize(uri, action) {
	Debug.print("<authorize>");
	try {
		var client = Packages.jp.co.intra_mart.foundation.authz.client.AuthorizationClientFactory.getInstance()
				.getAuthorizationClient();
		var result = client.authorize(uri, action);
		Debug.console(result);
		if (result == Packages.AuthorizeResult.Permit) {
			return true;
		} else {
			return false;
		}
	} catch (e) {
		Debug.console(e);
		return false;
	}
}

function webApiAuthorize(request) {
	var response = Web.getHTTPResponse();
	response.setContentType('application/json; charset=utf-8');
	var strData = request.getMessageBodyAsString();
	try {
		var dataJson = ImJson.parseJSON(strData);
		var result = authorize(dataJson.uri, dataJson.action);
		response.sendMessageBodyString("{ 'error' : false, 'authorize' : " + result + " }");
	} catch (e) {
		response.sendMessageBodyString("{ 'error' : true, 'errorMessage' : '" + e + "'  'authorize' : false}");
	}
}
