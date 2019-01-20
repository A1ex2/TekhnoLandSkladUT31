package android.a1ex.com.sklad_tsd.Directories;

import android.os.Parcel;
import android.os.Parcelable;

public class Cell implements Parcelable {
    public static final String TABLE_NAME = "Cells";

    public static final String COLUM_ID = "_id";
    public static final String COLUM_NAME = "Name";
    public static final String COLUM_ADDRESS = "Address";


    public long id;
    public String address;
    public String name;

    public Cell() {
    }

    public Cell(String name, String address) {
        this.address = address;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name +"(" + address + ")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.address);
        dest.writeString(this.name);
    }

    protected Cell(Parcel in) {
        this.id = in.readLong();
        this.address = in.readString();
        this.name = in.readString();
    }

    public static final Creator<Cell> CREATOR = new Creator<Cell>() {
        @Override
        public Cell createFromParcel(Parcel source) {
            return new Cell(source);
        }

        @Override
        public Cell[] newArray(int size) {
            return new Cell[size];
        }
    };
}
