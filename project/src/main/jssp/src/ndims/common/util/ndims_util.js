load("ndims/common/util/ndims_json");

var ndimsUtil = {};

function init() {
	Procedure.define("NdimsJson", NdimsJson);
}

/**
 * 转换对象到Json字符串并进行Escape
 * 
 */
ndimsUtil.cnvJson = function(obj) {
			var strJson = ImJson.toJSONString(obj);
			// var strJson = Packages.org.json.simple.JSONObject.toJSONString(obj);
			if (strJson == null || strJson.length == 0) {
				return "{}";
			}
			// 转换undefined
			strJson = strJson.replace(/(:\s*[^"])undefined([^"])/g, "$1null$2" );
			
			// 转换newdate
			strJson = strJson.replace(/(:\s*[^"])newDate\((.*)\) ([^"])/g, "$1null$2" );
			
			return strJson;
		}
