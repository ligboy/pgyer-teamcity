<%@ page import="org.ligboy.teamcity.pgyer.PgyerConstants" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>

<div class="parameter">
    Target App ID: <strong><props:displayValue name="<%=PgyerConstants.PARAM_API_KEY%>"
                                              emptyValue="default"/></strong>
</div>

<div class="parameter">
    File Source: <strong><props:displayValue name="<%=PgyerConstants.PARAM_SOURCE_PATH%>" emptyValue="empty"/></strong>
</div>

<div class="parameter">
    Update instructions: <strong><props:displayValue name="<%=PgyerConstants.PARAM_UPDATE_DESCRIPTION%>"
                                          emptyValue="none"/></strong>
</div>

<div class="parameter">
    Install Password: <strong><props:displayValue name="<%=PgyerConstants.PARAM_INSTALL_PASSWORD%>"
                                        emptyValue="none"/></strong>
</div>