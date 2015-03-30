package io.github.gdg_bucharest.gdg_feedly_client.feedly;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pndl on 3/29/15.
 */
public class Summary implements Parcelable {

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

    public Summary() {
    }

    private Summary(Parcel in) {
        this.content = in.readString();
        this.direction = in.readString();
    }

    public static final Parcelable.Creator<Summary> CREATOR = new Parcelable.Creator<Summary>() {
        public Summary createFromParcel(Parcel source) {
            return new Summary(source);
        }

        public Summary[] newArray(int size) {
            return new Summary[size];
        }
    };
}
