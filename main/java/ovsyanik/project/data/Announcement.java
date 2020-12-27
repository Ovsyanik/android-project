package ovsyanik.project.data;

import android.os.Parcel;
import android.os.Parcelable;


public class Announcement implements Parcelable {
    private int id;
    private String title;
    private String image;
    private byte[] mImage;
    private String description;
    private String dateStart;
    private String dateEnd;

    public Announcement( String mTitle, String mImage, String mDescription, String dateStart, String dateEnd) {
        this.title = mTitle;
        this.image = mImage;
        this.description = mDescription;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Announcement(int mId, String mTitle, String mImage, String mDescription, String dateStart, String dateEnd) {
        this.id = mId;
        this.title = mTitle;
        this.image = mImage;
        this.description = mDescription;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Announcement(String mTitle, byte[] mImage, String mDescription, String dateStart, String dateEnd) {
        this.title = mTitle;
        this.mImage = mImage;
        this.description = mDescription;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Announcement(int mId, String mTitle, byte[] mImage, String mDescription, String dateStart, String dateEnd) {
        this.id = mId;
        this.title = mTitle;
        this.mImage = mImage;
        this.description = mDescription;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    protected Announcement(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Announcement> CREATOR = new Creator<Announcement>() {
        @Override
        public Announcement createFromParcel(Parcel in) {
            return new Announcement(in);
        }

        @Override
        public Announcement[] newArray(int size) {
            return new Announcement[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        this.id = mId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String mImage) {
        this.image = mImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String mDescription) {
        this.description = mDescription;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                '}';
    }

    public byte[] getmImage() {
        return mImage;
    }

    public void setmImage(byte[] mImage) {
        this.mImage = mImage;
    }
}
