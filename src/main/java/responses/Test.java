package responses;

import java.io.Serializable;

public class Test implements Serializable {

    private long id;
    private String fName;
    private String lName;

    public Test(long id, String fName, String lName) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
    }

    public Test() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public long getId() {
        return id;
    }

    public String getFName() {
        return fName;
    }

    public String getLName() {
        return lName;
    }
}
