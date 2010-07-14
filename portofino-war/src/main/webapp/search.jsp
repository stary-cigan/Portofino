<%@ page contentType="text/html;charset=ISO-8859-1" language="java"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="mdes" uri="/manydesigns-elements-struts2" %>
<s:include value="/header.jsp"/>
<s:form method="post">
    <div class="buttons-bar">
        <s:submit method="create" value="Create" theme="simple"/>
    </div>
    <h1>Search: <s:property value="table.qualifiedName"/></h1>
    <mdes:write value="tableForm"/>
</s:form>
<s:include value="/footer.jsp"/>