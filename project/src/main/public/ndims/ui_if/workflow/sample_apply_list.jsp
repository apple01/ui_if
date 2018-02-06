<%-- 检索申请一览列表 --%>
<%@ page language="java" contentType="text/html;charset=utf-8"
  pageEncoding="utf-8"%>

<base href='http://localhost:8080/imart/' target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="ui/libs/jquery-1.7.2.js"></script>
    
<!-- 为了iAP工作流UI画面相关处理的实现，引入客户端处理模板 -->
<%@include file="/ndims/ui_if/workflow/proc_templete/apply_list.html" %>

<script type="text/javascript">
    var workflowParams = null;

    jQuery(document).ready(function() {

        // 检索申请一览列表
        $("#apply_list").click(function() {
            // 读入包含默认结构和参数的Json
            var jsondatapath = "ndims/ui_if/workflow/json/apply_list.json";
            $.getJSON(jsondatapath, function(obj) {
               
                var data = setInfo_apply_list(obj);
                
                // 存储参数
                workflowParams = data;
                
                $("#request").text(JSON.stringify(data, null, "  "));
                var url = "ndims/ui_if/workflow/apply_list";
                callAjax(url, data);
            });
        });

    });
</script>
<script type="text/javascript">
    function callAjax(url, data) {
        $.ajax({
            type : 'POST',
            url : url,
            contentType : 'application/json;charset=utf-8',
            headers : {
                'x-jp-co-intra-mart-ajax-request-from-imui-form-util' : 'true'
            },
            data : JSON.stringify(data),
            dataType : 'json',
            success : function(result) {
                if (!result.error) {
                    $("#result").text(JSON.stringify(result, null, "  "));
                    
                    setListData(result.data);
                } else {
                    alert("处理失败！");
                    $("#result").text(JSON.stringify(result, null, "  "));
                }
            },
            error : function(XMLHttpRequest, errorMessage, error) {
                alert("发生Ajax错误！:" + errorMessage);
                $("#result").text(XMLHttpRequest.responseText);
            }
        });
    }
    // 设定IF参数 : 检索申请一览列表
    function setInfo_apply_list(data) {
        var listParams = data.extension.imwCallOriginalParams.applyListParams;

        // 设定基本信息
        // listParams.applyUserCd = "omip";      // 申请人用户Cd(不设的话默认当前登录用户，如果希望用代理作为申请人的话设定代理的用户Cd)
        var now = new Date();
        var strNow = now.getFullYear() + "/"
                + ("0" + (now.getMonth() + 1)).slice(-2) + "/"
                + ("0" + now.getDate()).slice(-2);
        listParams.applyBaseDate = strNow; // 基准日（设置yyyy/mm/dd格式）

        // 设定可进行检索的条件（条件为空的话检索全部）
        listParams.listPageCol_FlowName = ""; // 支持流程名称检索
        listParams.listPageCol_FlowVersionNote = ""; // 支持流程版本检索

        // 设定分页信息
        listParams.count = 15; // 每页记录行数
        listParams.offset = 0; // 跳转到第几页（第一页从0开始）
        listParams.currentPage = 1; // 当前页
        
        // 设定排序信息
        var orderByList = listParams.orderByList;
        orderByList.columnName = "listPageCol_FlowName";
        orderByList.sortType = "ASC";
        
        return data;
    }
    

	function setListData(list) {

	// 移除标题行以外的内容
		$("#listTb tr:gt(0)").remove();

		for (var i = 0; i < list.length; i++) {

			var html = "";
			// 当前行数据取得
			var listRow = list[i];
			var tr = $(document.createElement("tr"));
			// 各列内容设定

			html = "<a href=\"javascript:void(0);onClickApplyFlow('"
					+ listRow.flowId + "', '" + listRow.nodeId
					+ "', workflowParams)\">申请</a>";
			var td = $(document.createElement("td")).html(html, true);
			tr.append(td);

			td = $(document.createElement("td")).html(
					escapeHTML(listRow.flowName), true);
			tr.append(td);

			td = $(document.createElement("td")).html(
					escapeHTML(listRow.flowVersionNote), true);
			tr.append(td);

			html = "<a href=\"javascript:void(0);onClickFlow('"
					+ listRow.flowId + "', workflowParams)\">查看</a>";
			td = $(document.createElement("td")).html(html, true);
			tr.append(td);

			$("#listTb").append(tr);
		}
	}
</script>

  <div>
    <button type="button" id="apply_list">检索申请一览列表</button>
  </div>
  
  <br>
  
    <div>
      <table id="listTb" border="1">
        <th>申请</th>
        <th>流程名</th>
        <th>备注</th>
        <th>流程</th>
      </table>
    </div>
    
    <br><br>
    
    <div>
    <table border="1">
      <tr>
        <td>Request参数：</td>
        <td><textarea id="request" cols="80" rows="20"></textarea></td>
      </tr>

      <tr>
        <td>运行结果：</td>
        <td><textarea id="result" cols="80" rows="20"></textarea></td>
      </tr>
    </table>
  </div>
  
