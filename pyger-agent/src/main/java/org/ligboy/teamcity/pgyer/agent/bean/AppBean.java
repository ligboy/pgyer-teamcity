package org.ligboy.teamcity.pgyer.agent.bean;

/**
 * @author ligboy
 */
public class AppBean {
    public String appKey;
    public String userKey;
    public int appType;
    public int appIsFirst;
    public int appIsLastest;
    public int appFileSize;
    public String appName;
    public String appVersion;
    public int appVersionNo;
    public int appBuildVersion;
    public String appIdentifier;
    public String appIcon;
    public String appDescription;
    public String appUpdateDescription;
    public String appScreenShots;
    public String appShortcutUrl;
    public String appQRCodeURL;
    public String appCreated;
    public String appUpdated;

    @Override
    public String toString() {
        return "AppBean{" +
                "appKey='" + appKey + '\'' +
                ", userKey='" + userKey + '\'' +
                ", appType=" + appType +
                ", appIsFirst=" + appIsFirst +
                ", appIsLastest=" + appIsLastest +
                ", appFileSize=" + appFileSize +
                ", appName='" + appName + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", appVersionNo=" + appVersionNo +
                ", appBuildVersion=" + appBuildVersion +
                ", appIdentifier=" + appIdentifier +
                ", appIcon='" + appIcon + '\'' +
                ", appDescription='" + appDescription + '\'' +
                ", appUpdateDescription='" + appUpdateDescription + '\'' +
                ", appScreenShots='" + appScreenShots + '\'' +
                ", appShortcutUrl='" + appShortcutUrl + '\'' +
                ", appQRCodeURL='" + appQRCodeURL + '\'' +
                ", appCreated='" + appCreated + '\'' +
                ", appUpdated='" + appUpdated + '\'' +
                '}';
    }
}
