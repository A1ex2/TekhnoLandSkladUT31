package android.a1ex.com.tekhnolandskladut31.Documents;

import android.icu.util.LocaleData;

import java.time.LocalDateTime;
import java.util.Date;

public class Document {
    public static final String TABLE_NAME = "Documents";

    public static final String COLUM_ID = "_id";
    public static final String COLUM_TYPE = "Type";
    public static final String COLUM_LAST_DATE_CREATE = "DateCreate";

    public long id;
    public String type;
    public Date dateCreate;

    public Document() {
        this.dateCreate = new Date();
    }

    public Document(long id, String type, Date dateCreate) {
        this.id = id;
        this.type = type;
        this.dateCreate = dateCreate;
    }

    public enum docType {Residue, Recount}

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
}
