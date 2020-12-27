package ovsyanik.project.data;

import java.util.Date;

public class Notes {
    private int id;
    private String title;
    private String description;
    private String dateAction;
    private int userId;

    public Notes(int id, String title, String description, String dateAction, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateAction = dateAction;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateAction() {
        return dateAction;
    }

    public void setDateAction(String dateAction) {
        this.dateAction = dateAction;
    }

    public int getUser() {
        return userId;
    }

    public void setUser(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateAction='" + dateAction + '\'' +
                ", userId=" + userId +
                '}';
    }
}
