package in.asetalias.alias.dataModels;

/**
 * Created by Jayant on 2017-11-03.
 */

public class CommunityModel {

    private String title;
    private String tag;
    private String desc;
    private String logo;
    private String website;
    private String meetup;
    private String telegram;

    public CommunityModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMeetup() {
        return meetup;
    }

    public void setMeetup(String meetup) {
        this.meetup = meetup;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }
}