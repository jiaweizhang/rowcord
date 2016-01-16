package hello;

public class Test {

    private final long id;
    private final String fName;
    private final String lName;

    public Test
    (long id, String fName, String lName) {
        this.id = id;
        this.fName = fName;
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
