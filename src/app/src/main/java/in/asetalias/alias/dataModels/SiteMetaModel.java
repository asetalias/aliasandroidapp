package in.asetalias.alias.dataModels;

/**
 * Created by Jayant on 2017-11-03.
 */

public class SiteMetaModel {

    private String icon;
    private String url;
    private String title;
    private String iconColor;

    public SiteMetaModel() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }
}


/*   {
        "icon": "envelope",
        "url": "mailto:contactasetalias@gmail.com",
        "title": "Email",
        "iconColor": "green"
      },*/