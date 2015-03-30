package io.github.gdg_bucharest.gdg_feedly_client.feedly;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pndl on 3/19/15.
 */
public class Entry implements Parcelable {

    private String author;
    private String title;
    private Summary summary;
    private Summary content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Summary getContent() {
        return content;
    }

    public void setContent(Summary content) {
        this.content = content;
    }

    public Entry() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.title);
        dest.writeParcelable(this.summary, 0);
        dest.writeParcelable(this.content, 0);
    }

    private Entry(Parcel in) {
        this.author = in.readString();
        this.title = in.readString();
        this.summary = in.readParcelable(Summary.class.getClassLoader());
        this.content = in.readParcelable(Summary.class.getClassLoader());
    }

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        public Entry createFromParcel(Parcel source) {
            return new Entry(source);
        }

        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };
}
