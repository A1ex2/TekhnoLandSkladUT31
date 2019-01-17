package android.a1ex.com.sklad_tsd.Documents;

import android.a1ex.com.sklad_tsd.R;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Document implements Parcelable {
    public static final String TABLE_NAME = "Documents";

    public static final String COLUM_ID = "_id";
    public static final String COLUM_TYPE = "Type";
    public static final String COLUM_DATE_CREATE = "DateCreate";

    public long id;
    public String type;
    public Date dateCreate;

    public Document() {
        this.dateCreate = new Date();
        this.id = -1;
    }


    @Override
    public String toString() {
        return type + " " + id + " от " + dateToFormat();
    }

    public String dateToFormat() {
        String retval = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (dateCreate == null) {
            return retval;
        }
        retval = sdf.format(dateCreate);
        return retval;
    }

    public Document(long id, String type, Date dateCreate) {
        this.id = id;
        this.type = type;
        this.dateCreate = dateCreate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.type);
        dest.writeLong(this.dateCreate != null ? this.dateCreate.getTime() : -1);
    }

    protected Document(Parcel in) {
        this.id = in.readLong();
        this.type = in.readString();
        long tmpDateCreate = in.readLong();
        this.dateCreate = tmpDateCreate == -1 ? null : new Date(tmpDateCreate);
    }

    public static final Creator<Document> CREATOR = new Creator<Document>() {
        @Override
        public Document createFromParcel(Parcel source) {
            return new Document(source);
        }

        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };
}
