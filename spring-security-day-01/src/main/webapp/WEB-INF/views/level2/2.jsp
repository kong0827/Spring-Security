<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("PATH", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>系统</title>
<link rel="stylesheet" href="${PATH }/layui/css/layui.css">
</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<!-- 顶部导航 -->
		<%@include file="/WEB-INF/include/navbar.jsp"%>

		<!-- 侧边栏 -->
		<%@include file="/WEB-INF/include/sidebar.jsp"%>


		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div style="padding: 15px;">

				<a href="${PATH }/main.html">返回</a>
				<h1>七伤拳</h1>
				<p>练这拳的人全都死了</p>
			</div>
		</div>
		<div class="layui-footer"></div>
	</div>
	<script src="${PATH }/layui/layui.js"></script>
	<script src="${PATH }/layui/jquery.min.js"></script>
	<script src="${PATH }/layui/highlight.js"></script>
	<script>
		//JavaScript代码区域
		layui.use('element', function() {
			new HighLight({hrefContent:'/level2/2'})
			var element = layui.element;

		});
	</script>
</body>
</html>



