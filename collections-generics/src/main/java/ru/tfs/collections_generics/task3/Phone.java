package ru.tfs.collections_generics.task3;

public class Phone {
    private String number;
    private PhoneType type;

    public Phone(String number, PhoneType type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public PhoneType getType() {
        return type;
    }

    public boolean isLandline(){
        return PhoneType.LANDLINE.equals(type);
    }
}
