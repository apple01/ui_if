/* 
 * JsAPI转换为WebAPI的定义声明
 * URI:ndims/js/webapi/{apiname}
 * 
 * 定义方说明
 * 
 "API对象名" : {			// 如果是GlobalFunction，则API对象名请指定global
		isStatic : true, // 必须参数：是否是静态对象
		methods : {	// 必须参数：该API对象名下的API函数
			"API对象下的方法名（实际的API名）" : {
				params : {	// 可选参数：API参数，参数并不需要强制指定，但指定参数可以更好的验证传递的参数是否正确。
					"a" : "参数默认值", 支持函数定义
					...
				},
				rtn : { // 可选参数：返回值形式 , 不定义的话则默认采用封装在result.data中的形式进行返回
					isDirectRtn : true	// API的返回值是否封装在reuslt.data中进行返回（false时），还是直接返回(true)
				},
				authz : { // 可选参数：认可资源权限信息：当指定的认可资源是认可的时候才允许访问（不指定则不进行许可校验）
					uri : "",
					action : ""
				},
				info : { // 可选参数：信息，例如参照Doc的URL等（仅作参考用）
					uri : "ndims/js/webapi/API对象名.API对象下的方法名",
					ref : "https://www.intra-mart.jp/apidoc/iap/apilist-ssjs/doc/platform/Contexts/index.html#method-getAccountContext_0"
				}
			}
		},
		construct : { // 可选参数：构造信息 ，不设定则没有构造信息
			params : {	// 可选参数：构造参数，参数并不需要强制指定，但指定参数可以更好的验证传递的参数是否正确。
					"a" : "参数默认值", 支持函数定义
					...
			},
		}
	}
  */

 /* 配合该定义的调用方参数说明
 * 
 *
 {
	construct : { // 构造信息 ，不设定则没有构造信息
		params : {	// 构造参数，参数并不需要强制指定
				"a" : "参数默认值",
				... 
		},
	},
	params : {	// API参数，参数并不需要强制指定
		"a" : "参数默认值",
		... 
	}
 }
 */

var ndimsJsWebApiConf = {
	"global" : { // 全局函数
		isStatic : true, // 是否是静态对象
		methods : {
			"getMessageDigest" : {
				params : {
					"algorithm" : "",
					"value" : ""
				},
				rtn : {
					isDirectRtn : true
				},
				authz : null,
				info : { // 信息，例如参照Doc的URL等
					uri : "ndims/js/webapi/global.getMessageDigest",
					ref : "https://www.intra-mart.jp/apidoc/iap/apilist-ssjs/doc/platform/GlobalFunction/index.html#method-getMessageDigest_24"
				}
			}
		}
	},
	"Contexts" : {
		isStatic : true, // 是否是静态对象
		methods : {
			"getAccountContext" : {
				params : null, // API参数
				rtn : {
					isDirectRtn : false
				// API的返回值是否封装在reust.data中进行返回（false时），还是直接返回(true)
				},
				authz : { // 权限信息
					uri : "",
					action : ""
				},
				info : { // 信息，例如参照Doc的URL等
					uri : "ndims/js/webapi/Contexts.getAccountContext",
					ref : "https://www.intra-mart.jp/apidoc/iap/apilist-ssjs/doc/platform/Contexts/index.html#method-getAccountContext_0"
				}
			}
		},
		construct : {
			params : null
		// 构造参数
		},
		authz : { // 权限信息
			uri : "",
			action : ""
		},
		info : { // 信息，例如参照Doc的URL等
			ref : "https://www.intra-mart.jp/apidoc/iap/apilist-ssjs/doc/platform/Contexts/index.html"
		}
	}, // -----------------------------------------------------------------
	"IMMCompanyManager" : { /* 公司组织的Manager */
		isStatic : false,
		methods : {
			"getDepartments" : {
				params : {
					"bizKey" : {},
					"termDate" : function() {
						return new Date();
					}
				}, // API参数
				rtn : {
					isDirectRtn : true
				// API的返回值是否封装在reust.data中进行返回（false时），还是直接返回(true)
				},
				authz : { // 权限信息
					uri : "",
					action : ""
				},
				info : { // 信息，例如参照Doc的URL等
					uri : "ndims/js/webapi/Contexts.getAccountContext",
					ref : "https://www.intra-mart.jp/apidoc/iap/apilist-ssjs/doc/platform/Contexts/index.html#method-getAccountContext_0"
				}
			}
		},
		construct : {
			params : {
				"updateUserCd" : function() {
					var accountContext = Contexts.getAccountContext();
					return accountContext.userCd;
				},
				"defaultLocale" : function() {
					var accountContext = Contexts.getAccountContext();
					return accountContext.locale;
				}
			}
		// 构造参数
		},
		authz : { // 权限信息
			uri : "",
			action : ""
		},
		info : { // 信息，例如参照Doc的URL等
			ref : "https://www.intra-mart.jp/apidoc/iap/apilist-ssjs/doc/platform/Contexts/index.html"
		}
	}, // -----------------------------------------------------------------
	"ApplyManager" : { /* 工作流的ApplyManager */
		isStatic : false,
		methods : {
			"apply" : {
				params : {
					"applyParam" : null,
					"userParam" : null
				},
				rtn : {
					isDirectRtn : true // API的返回值是否封装在reust.data中进行返回（false时），还是直接返回(true)
				},
				info : { // 信息，例如参照Doc的URL等
					uri : "ndims/js/webapi/ApplyManager.apply",
					ref : "https://www.intra-mart.jp/apidoc/iap/apilist-ssjs/doc/im_workflow/ApplyManager/index.html#method-apply_0"
				}
			},
			construct : {
				params : null
			},
			info : { // 信息，例如参照Doc的URL等
				ref : "https://www.intra-mart.jp/apidoc/iap/apilist-ssjs/doc/im_workflow/ApplyManager/index.html"
			}
		}
	}

};

function getJsWebApiConf() {
	return ndimsJsWebApiConf;
}
