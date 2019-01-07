package android.a1ex.com.tekhnolandskladut31.Directories;

public class Cell {
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
}
