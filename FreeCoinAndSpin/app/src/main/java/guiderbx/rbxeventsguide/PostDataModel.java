package guiderbx.rbxeventsguide;

public class PostDataModel
{

    private String title;
    private String link;
    private String date;
    private String time;

    public PostDataModel() {
    }

    public PostDataModel(String title, String link, String date, String time) {
        this.title = title;
        this.link = link;
        this.date = date;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
