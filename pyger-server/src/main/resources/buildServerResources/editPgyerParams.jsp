<%@ page import="org.ligboy.teamcity.pgyer.PgyerConstants" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="runnerConst" scope="request" class="org.ligboy.teamcity.pgyer.PgyerConstants"/>
<jsp:useBean id="buildForm" type="jetbrains.buildServer.controllers.admin.projects.BuildTypeForm" scope="request"/>

<l:settingsGroup title="Target Settings">
    <tr>
        <th><label for="org.ligboy.teamcity.pgyer.api_key">Api Key: <l:star/></label></th>
        <td><props:textProperty name="<%=PgyerConstants.PARAM_API_KEY%>" className="mediumField"
                                maxlength="32"/>
            <span class="smallNote">Enter the App ID, e.g. 10236b1dac2e823454775f63a8562385. 32 chars</span>
            <span class="error" id="error_org.ligboy.teamcity.pgyer.api_key"></span>
        </td>
    </tr>
    <tr>
        <th><label for="org.ligboy.teamcity.pgyer.user_key">User Key: <l:star/></label></th>
        <td><props:textProperty name="<%=PgyerConstants.PARAM_USER_KEY%>" className="mediumField"
                                maxlength="32"/>
            <span class="smallNote">Enter the User Key, e.g. 12346b1dac2e823457845f63a8562385. 32 chars</span>
            <span class="error" id="error_org.ligboy.teamcity.pgyer.user_key"></span>
        </td>
    </tr>

</l:settingsGroup>

<l:settingsGroup title="Uploaded File Source">
    <tr>
        <th><label for="org.ligboy.teamcity.pgyer.sourcePath">File path:  <l:star/></label></th>
        <td>
            <props:textProperty id="artifactPaths" name="<%=PgyerConstants.PARAM_SOURCE_PATH%>" className="longField"
                                maxlength="256" />
            <%--<bs:vcsTree fieldId="<%=PgyerConstants.PARAM_SOURCE_PATH%>"/>--%>
            <img class="handle vcsTreeHandle" src="<c:url value="/img/tree/popup-artifacts-tree.png"/>"
                 title="Select files from the latest build"
                 showdiscardchangesmessage="false" onclick="return BS.PgyerEditArtifacts.showPopup(this, '${buildForm.externalId}');"/>
            <span class="smallNote">Path to app package file to upload. Ant-style wildcards like app/**/*.apk.
            <bs:help file="Configuring+General+Settings" anchor="artifactPaths"/></span>

            <span class="error" id="error_org.ligboy.teamcity.pgyer.sourcePath"></span>
        </td>
    </tr>
</l:settingsGroup>

<l:settingsGroup title="Extra Setting">

    <tr>
        <th><label for="org.ligboy.teamcity.pgyer.update_description">Update instructions: </label></th>
        <td>
            <props:multilineProperty name="<%=PgyerConstants.PARAM_UPDATE_DESCRIPTION%>" className="longField"
                                     cols="30" rows="4" expanded="true" linkTitle="Please enter the version of the update instructions"/>
            <span class="smallNote">Optional. New line or comma separated paths to build artifacts. Ant-style wildcards like dir/**/*.zip and target directories like *.zip => winFiles,unix/distro.tgz => linuxFiles, where winFiles and linuxFiles are target directories are supported.
            <bs:help file="Configuring+General+Settings" anchor="artifactPaths"/></span>
            <span class="error" id="error_org.ligboy.teamcity.pgyer.update_description"></span>
        </td>
    </tr>

    <tr class="advancedSetting">
        <th><label for="org.ligboy.teamcity.pgyer.install_password">Install Password: </label></th>
        <td><props:passwordProperty name="<%=PgyerConstants.PARAM_INSTALL_PASSWORD%>" className="mediumField"
                                    maxlength="30"/>
            <span class="smallNote">Optional, user will be asked the password after you set your password. If you doesn't want password, please leave here by empty</span><span
                    class="error" id="error_org.ligboy.teamcity.pgyer.install_password"></span>
        </td>
    </tr>

    <tr class="advancedSetting">
        <th><label for="org.ligboy.teamcity.pgyer.publish_to_public">Push to Public:</label></th>
        <td><props:checkboxProperty name="<%=PgyerConstants.PARAM_PUBLISH_TO_PUBLIC%>"/><label
                for="org.ligboy.teamcity.pgyer.publish_to_public">Publish to public list?</label>
            <span class="smallNote">Optional. Default NOT publish to public list.</span>
            <span class="error" id="error_org.ligboy.teamcity.pgyer.publish_to_public"></span>
        </td>
    </tr>
</l:settingsGroup>
<script language="JavaScript">
    BS.PgyerEditArtifacts = {
        popup: null,

        showPopup: function(elem, buildTypeId) {
            this.popup = new BS.Popup("editArtifactsTreePopup", {
                hideOnMouseOut: false,
                hideOnMouseClickOutside: true,
                shift: {x: 20, y: 0},
                url: window["base_uri"] + "/editArtifactsTreePopup.html?buildTypeId=" + buildTypeId
            });
            this.popup.showPopupNearElement(elem);

            this.prepareSelection();

            return false;
        },

        prepareSelection: function() {
            var textarea = $j("#artifactPaths");
            $j(window).off("bs.agentFile bs.agentDir").on("bs.agentFile bs.agentDir", function(e, path) {
                var pathToAppend = e.namespace == "agentFile" ? path : path + " => " + path;
                textarea.val(pathToAppend);
                BS.VisibilityHandlers.updateVisibility('artifactPaths');
                BS.EditBuildTypeForm.setModified(true);   // In theory this can be wrong, but in 99% of cases the form is modified.
                this.popup.hidePopup(500, true);
            });
        }
    };
</script>