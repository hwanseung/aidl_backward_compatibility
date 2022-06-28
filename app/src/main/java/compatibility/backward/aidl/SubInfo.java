package compatibility.backward.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public final class SubInfo implements Parcelable {
    public String title;

    public SubInfo() {
    }

    private SubInfo(Parcel in) {
        readFromParcel(in);
    }

    public static final Parcelable.Creator<SubInfo> CREATOR = new Parcelable.Creator<SubInfo>() {
        public SubInfo createFromParcel(Parcel in) {
            return new SubInfo(in);
        }

        public SubInfo[] newArray(int size) {
            return new SubInfo[size];
        }
    };

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
    }

    public void readFromParcel(Parcel in) {
        title = in.readString();
    }

    public int describeContents() {
        return 0;
    }
}
