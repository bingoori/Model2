<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%
   String mainurl = request.getContextPath();
   response.sendRedirect(mainurl + "/MemberLogin.me");
%>