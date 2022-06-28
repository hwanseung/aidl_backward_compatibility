package compatibility.backward.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public final class Info implements Parcelable {
    public String title;
    public List<SubInfo> infos;

    public Info() {
        infos = new ArrayList<>();
    }

    private Info(Parcel in) {
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Info> CREATOR = new Parcelable.Creator<Info>() {
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeParcelableList(infos, flags);
    }

    public void readFromParcel(Parcel in) {
        title = in.readString();
        infos = new ArrayList<SubInfo>();
        in.readParcelableList(infos, SubInfo.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }
}
