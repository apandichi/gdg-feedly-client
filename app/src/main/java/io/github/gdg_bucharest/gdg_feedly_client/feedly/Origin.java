package io.github.gdg_bucharest.gdg_feedly_client.feedly;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pndl on 5/9/15.
 */
public class Origin implements Parcelable {

    private String href;
    private String type;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
        dest.writeString(this.type);
    }

    public Origin() {
    }

    private Origin(Parcel in) {
        this.href = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Origin> CREATOR = new Parcelable.Creator<Origin>() {
        public Origin createFromParcel(Parcel source) {
            return new Origin(source);
        }

        public Origin[] newArray(int size) {
            return new Origin[size];
        }
    };
}
