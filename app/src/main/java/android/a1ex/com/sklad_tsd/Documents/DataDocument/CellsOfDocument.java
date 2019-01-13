package android.a1ex.com.sklad_tsd.Documents.DataDocument;

public class CellsOfDocument {
    public static final String TABLE_NAME = "CellsOfDocument";

    public static final String COLUM_ID = "_id";
    public static final String COLUM_ID_DOCUMENT = "_idDocument";

    public long id;
    public long idDocument;

    public CellsOfDocument() {
    }

    public CellsOfDocument(long id, long idDocuments) {
        this.id = id;
        this.idDocument = idDocument;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(long idDocument) {
        this.idDocument = idDocument;
    }
}
