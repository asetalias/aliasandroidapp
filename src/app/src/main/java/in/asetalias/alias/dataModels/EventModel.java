package in.asetalias.alias.dataModels;

/**
 * Created by Jayant on 2017-10-29.
 */

public class EventModel {

    private String title;
    private String cta;
    private String ctaText;
    private String date;
    private String startTime;
    private String endTime;
    private String location;
    private String description;
    private String poster;

    public EventModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCta() {
        return cta;
    }

    public void setCta(String cta) {
        this.cta = cta;
    }

    public String getCtaText() {
        return ctaText;
    }

    public void setCtaText(String ctaText) {
        this.ctaText = ctaText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}


/*{
    "title": "No Upcoming Event",
    "cta": "http://bit.ly/aliaseventsuggest",
    "ctaText": "Suggest an Event",
    "date": "NA",
    "poster": "http://asetalias.in/images/flashbox/default.jpg",
    "startTime": "NA",
    "endTime": "NA",
    "location": "NA",
    "eventOD": 0,
    "description": "No Upcoming Event, Stay Updated!",
    "cta2enable": 0,
    "cta2": "#",
    "cta2Text": "View"
  }*/