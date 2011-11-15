<%@ page contentType="text/html;charset=ISO-8859-1" language="java"
         pageEncoding="ISO-8859-1"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"
%><%@ taglib prefix="mde" uri="/manydesigns-elements"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="portofino" uri="/manydesigns-portofino" %>
<stripes:layout-render name="/skins/default/admin-page.jsp">
    <jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.actions.admin.ConnectionProvidersAction"/>
    <stripes:layout-component name="pageTitle">
        <fmt:message key="commons.create"/> connection provider
    </stripes:layout-component>
    <stripes:layout-component name="contentHeader">
        <portofino:buttons list="connectionProviders-create" bean="${actionBean}" cssClass="contentButton" />
    </stripes:layout-component>
    <stripes:layout-component name="portletTitle">
        <fmt:message key="commons.create"/> connection provider
    </stripes:layout-component>
    <stripes:layout-component name="portletBody">
        <mde:write name="actionBean" property="form"/>
        <c:if test="${actionBean.detectedValuesForm != null}">
            <div class="horizontalSeparator"></div>
        </c:if>
        <stripes:hidden name="connectionType" />
    </stripes:layout-component>
    <stripes:layout-component name="contentFooter">
        <portofino:buttons list="connectionProviders-create" bean="${actionBean}" cssClass="contentButton" />
    </stripes:layout-component>
</stripes:layout-render>