package vn.fjobs.base.fragments.toolbox;

import android.os.Parcel;
import android.os.Parcelable;

public class NavigationState implements Parcelable {

    public final int placeholder;
    public final String backStackName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(placeholder);
        parcel.writeString(backStackName);
    }

    public NavigationState(Parcel parcel) {
        placeholder = parcel.readInt();
        backStackName = parcel.readString();
    }

    public NavigationState(int type, String backStackName) {
        this.placeholder = type;
        this.backStackName = backStackName;
    }

    public NavigationState(int type) {
        this(type, Integer.toString((int) (Integer.MAX_VALUE * Math.random())));
    }

    public static final Creator<NavigationState> CREATOR = new Creator<NavigationState>() {
        @Override
        public NavigationState createFromParcel(Parcel in) {
            return new NavigationState(in);
        }

        @Override
        public NavigationState[] newArray(int size) {
            return new NavigationState[size];
        }
    };
}
