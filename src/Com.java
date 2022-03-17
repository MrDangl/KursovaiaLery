public class Com {
    private int id;
    private String numberWarehouse;
    private String email;
    private String wareProfile;
    private String fioWare;
    private String wareAdress;
    private Integer[] warehouse;
    Com(String numberWarehouse,String email,String wareProfile,String fioWare,String address)
    {
        this.numberWarehouse = numberWarehouse;
        this.email = email;
        this.wareProfile = wareProfile;
        this.fioWare = fioWare;
        this.wareAdress = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumberWarehouse(String numberPharmacy) {
        this.numberWarehouse = numberWarehouse;
    }

    public void setFioWare(String fioWare) {
        this.fioWare = fioWare;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPharmacy(Integer[] pharmacy) {
        this.warehouse = pharmacy;
    }

    public void setWareProfile(String wareProfile) {
        this.wareProfile = wareProfile;
    }

    public int getId() {
        return id;
    }

    public Integer[] getPharmacy() {
        return warehouse;
    }

    public String getEmail() {
        return email;
    }

    public String getFioWare() {
        return fioWare;
    }

    public String getNumberWarehouse() {
        return numberWarehouse;
    }

    public String getWareProfile() {
        return wareProfile;
    }

    public void setWarehouse(Integer[] warehouse) {
        this.warehouse = warehouse;
    }

    public Integer[] getWarehouse() {
        return warehouse;
    }

    public void setWareAdress(String wareAdress) {
        this.wareAdress = wareAdress;
    }

    public String getWareAdress() {
        return wareAdress;
    }
}
