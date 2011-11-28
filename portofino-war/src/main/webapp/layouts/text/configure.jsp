<%@ page contentType="text/html;charset=ISO-8859-1" language="java"
         pageEncoding="ISO-8859-1"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes-dynattr.tld"
%><%@taglib prefix="mde" uri="/manydesigns-elements"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><%@ taglib tagdir="/WEB-INF/tags" prefix="portofino"
%><stripes:layout-render name="/skins/${skin}/modal-page.jsp">
    <stripes:layout-component name="customScripts">
        <script type="text/javascript" src="<stripes:url value="/ckeditor/ckeditor.js"/>"></script>
        <script type="text/javascript" src="<stripes:url value="/ckeditor/adapters/jquery.js"/>"></script>
    </stripes:layout-component>
    <jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.actions.TextAction"/>
    <stripes:layout-component name="contentHeader">
        <portofino:buttons list="configuration" cssClass="contentButton" />
        <div class="breadcrumbs">
            <div class="inner">
                <mde:write name="breadcrumbs"/>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="portletHeader">
        <%@include file="../portlet-common-configuration.jsp" %>
    </stripes:layout-component>
    <stripes:layout-component name="portletBody">
        <fieldset class="mde-form-fieldset" style="padding-top: 1em;">
            <legend><fmt:message key="layouts.text.configure.content"/></legend>
            <stripes:textarea class="editor" name="content" value="${actionBean.content}"/>
        </fieldset>
        <div class="horizontalSeparator"></div>
        <fmt:message key="layouts.text.configure.this_document_is_saved"/> <c:out value="${actionBean.textFile.name}"/>
        <input type="hidden" name="cancelReturnUrl" value="<c:out value="${actionBean.cancelReturnUrl}"/>"/>
    </stripes:layout-component>
    <stripes:layout-component name="portletFooter">
        <script type="text/javascript">
            $('textarea.editor').ckeditor({
                toolbar: 'Full',
                toolbarCanCollapse: false,
                filebrowserWindowWidth : '640',
                filebrowserWindowHeight : '480',
                filebrowserBrowseUrl : '<c:out value="${dispatch.absoluteOriginalPath}"/>?browse=',
                filebrowserUploadUrl : '<c:out value="${dispatch.absoluteOriginalPath}"/>?uploadAttachmentFromCKEditor='
            });
        </script>
    </stripes:layout-component>
    <stripes:layout-component name="contentFooter">
        <portofino:buttons list="configuration" cssClass="contentButton" />
    </stripes:layout-component>
</stripes:layout-render>