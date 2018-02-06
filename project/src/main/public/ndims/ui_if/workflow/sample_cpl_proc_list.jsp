<%-- 检索已处理（已办-总流程已完成）一览列表 --%>
<%@ page language="java" contentType="text/html;charset=utf-8"
  pageEncoding="utf-8"%>
  
<base href='http://localhost:8080/imart/' target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="ui/libs/jquery-1.7.2.js"></script>

<!-- 为了iAP工作流UI画面相关处理的实现，引入客户端处理模板 -->
<%@include file="/ndims/ui_if/workflow/proc_templete/cpl_proc_list.html" %>

<script type="text/javascript">
    var workflowParams = null;

    jQuery(document).ready(function() {

        // 检索已处理（已办-总流程未完成）一览列表
        $("#cpl_proc_list").click(function() {
            // 读入包含默认结构和参数的Json
            var jsondatapath = "ndims/ui_if/workflow/json/cpl_proc_list.json";
            $.getJSON(jsondatapath, function(obj) {
               
                var data = setInfo_cpl_proc_list(obj);
                
                // 存储参数
                workflowParams = data;
                
                $("#request").text(JSON.stringify(data, null, "  "));
                var url = "ndims/ui_if/workflow/cpl_proc_list";
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
    // 设定IF参数 : 检索已处理（已办-总流程已完成）一览列表
    function setInfo_cpl_proc_list(data) {
      var listParams = data.extension.imwCallOriginalParams.cplProcListParams;

      // 设定可进行检索的条件（条件为空的话检索全部）
      listParams.listPageCol_Priority = "";
      listParams.listPageCol_MatterNumber = "";
      listParams.listPageCol_MatterName = "";
      listParams.listPageCol_BaseDate = "";
      listParams.listPageCol_ApplyDate = "";
      listParams.listPageCol_ApplyUser = "";
      listParams.listPageCol_FlowName = "";
      listParams.listPageCol_EndDate = "";
      listParams.listPageCol_EndStatus = "";
      
      // 设定分页信息
      listParams.count = 15;            // 每页记录行数
      listParams.offset = 0;              // 跳转到第几页（第一页从0开始）
      listParams.currentPage = 1;    // 当前页
      
      // 设定排序信息
      var orderByList = listParams.orderByList;
      orderByList.columnName = "listPageCol_FlowName";    // 可以进行排序的字段名参考检索条件
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

      // 列：优先级
			td = $(document.createElement("td")).html(
					escapeHTML(listRow.priority), true);
			tr.append(td);

			// 列：案件号
			td = $(document.createElement("td")).html(
					escapeHTML(listRow.matterNumber), true);
			tr.append(td);
			
      // 列：案件名
      td = $(document.createElement("td")).html(
          escapeHTML(listRow.matterName), true);
      tr.append(td);
		      
			// 列：申请基准日
			td = $(document.createElement("td")).html(
					escapeHTML(listRow.baseDate), true);
			tr.append(td);

			// 列：申请开始日
			td = $(document.createElement("td")).html(
					escapeHTML(listRow.applyDate), true);
			tr.append(td);

			// 申请人
			td = $(document.createElement("td")).html(
					escapeHTML(listRow.applyUser), true);
			tr.append(td);

			// 列：流程名
			td = $(document.createElement("td")).html(
					escapeHTML(listRow.flowName), true);
			tr.append(td);

			// 列：结束日
			td = $(document.createElement("td")).html(
					escapeHTML(listRow.endDate), true);
			tr.append(td);

			// 列：完成状态
			td = $(document.createElement("td")).html(
					escapeHTML(listRow.endStatus), true);
			tr.append(td);

			// 列：详细
			html = "<a href=\"javascript:void(0);onClickDetail('"
					+ listRow.systemMatterId + "', '"
					+ listRow.userDataId
					+ "', workflowParams)\">详细</a>";
			var td = $(document.createElement("td")).html(html, true);
			tr.append(td);

			// 流程
			html = "<a href=\"javascript:void(0);onClickFlow('"
					+ listRow.systemMatterId 
					+ "', workflowParams)\">流程</a>";
			var td = $(document.createElement("td")).html(html, true);
			tr.append(td);

			// 履历
			html = "<a href=\"javascript:void(0);onClickHistory('"
					+ listRow.systemMatterId
					+ "', workflowParams)\">履历</a>";
			var td = $(document.createElement("td")).html(html, true);
			tr.append(td);

			$("#listTb").append(tr);
		}
	}
</script>

  <div>
    <button type="button" id="cpl_proc_list">检索已处理（已办-总流程已完成）一览列表</button>
  </div>
  
  <br>
  
    <div>
      <table id="listTb" border="1">
        <th>优先级</th>
        <th>案件号</th>
        <th>案件名</th>
        <th>申请基准日</th>
        <th>申请开始日</th>
        <th>申请人</th>
        <th>流程名</th>
        <th>结束日</th>
        <th>完成状态</th>
        <th>详细</th>
        <th>流程</th>
        <th>履历</th>
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
  
