package cn.th.phonerf.model;

public enum UreaDeviceNo {
    No1("863412042130032"),
    No2("868704045919774"),
    No3("868704045929534"),
    No4("863412042129984");

    public final String value;
    private UreaDeviceNo(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }


}
