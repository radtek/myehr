<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String contextPath = request.getContextPath();
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" href="${pageContext.request.contextPath }/common/css/commomcss.css?v0.0.2" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath }/common/css/font-awesome-4.7.0/css/font-awesome.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath }/common/js/assets/bootstrap/css/bootstrap.css" type="text/css"></link>
<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/jsp/orgpicture/dabengOrgChart/myorg/img/logo.ico">
<script type="text/javascript" src="${pageContext.request.contextPath }/common/js/commonjs.js?v0.0.2"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/common/js/date-format.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/common/js/md5.js?v1.0.0.1"></script>
