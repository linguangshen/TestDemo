<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/11/24
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>地址</th>
        <th>电话号码</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${users}" var="obj">
            <tr>
                <th>${obj.id}</th>
                <th>${obj.name}</th>
                <th>${obj.city}</th>
                <th>${obj.age}</th>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
