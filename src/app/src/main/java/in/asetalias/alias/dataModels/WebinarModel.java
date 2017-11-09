package in.asetalias.alias.dataModels;

/**
 * Created by Jayant on 2017-11-03.
 */

public class WebinarModel {

    private String title;
    private String videoId;
    private String description;

    public WebinarModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


/*{
    "title": "Introduction to Linux",
    "videoId": "CTO-8BnzS_s",
    "description": "This webinar would be about basics of Linux and its terminologies. We'll be covering basics of command lines/principles/every 'Must Know' things about Linux.<br/><strong>Speakers:</strong> <br/><strong>Anuvrat Parashar:</strong> <a href = \"http://anuvrat.in/\">http://anuvrat.in/</a>"
  },*/