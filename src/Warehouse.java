public class Warehouse {
    private int id;
    private String number;
    private String nameWarehouse;
    private String profile;
    private String address;
    public void setNumber(String s)
    {
        number = s;
    }
    public void setAddress(String s)
    {
        address = s;
    }
    public String getNumber()
    {
        return number;
    }
    public String getAddress()
    {
        return address;
    }
    Warehouse(String n,String nam, String prof,String addr)
    {
        number = n;
        nameWarehouse = nam;
        profile = prof;
        address = addr;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getNameWarehouse() {
        return nameWarehouse;
    }

    public void setNameWarehouse(String nameWarehouse) {
        this.nameWarehouse = nameWarehouse;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}
