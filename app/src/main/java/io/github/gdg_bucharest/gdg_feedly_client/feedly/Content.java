package io.github.gdg_bucharest.gdg_feedly_client.feedly;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pndl on 3/29/15.
 */
public class Content implements Parcelable {

    private String content;
    private String direction;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.direction);
    }

    public Content() {
    }

    private Content(Parcel in) {
        this.content = in.readString();
        this.direction = in.readString();
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        public Content createFromParcel(Parcel source) {
            return new Content(source);
        }

        public Content[] newArray(int size) {
            return new Content[size];
        }
    };
}
