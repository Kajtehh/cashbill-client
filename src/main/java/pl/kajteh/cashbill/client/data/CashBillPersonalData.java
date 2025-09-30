package pl.kajteh.cashbill.client.data;

public class CashBillPersonalData {

    private String firstName;
    private String surname;
    private String email;
    private String country;
    private String city;
    private String postcode;
    private String street;
    private String house;
    private String flat;
    private String ip;

    public CashBillPersonalData firstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CashBillPersonalData surname(final String surname) {
        this.surname = surname;
        return this;
    }

    public CashBillPersonalData email(final String email) {
        this.email = email;
        return this;
    }

    public CashBillPersonalData country(final String country) {
        this.country = country;
        return this;
    }

    public CashBillPersonalData city(final String city) {
        this.city = city;
        return this;
    }

    public CashBillPersonalData postcode(final String postcode) {
        this.postcode = postcode;
        return this;
    }

    public CashBillPersonalData street(final String street) {
        this.street = street;
        return this;
    }

    public CashBillPersonalData house(final String house) {
        this.house = house;
        return this;
    }

    public CashBillPersonalData flat(final String flat) {
        this.flat = flat;
        return this;
    }

    public CashBillPersonalData ip(final String ip) {
        this.ip = ip;
        return this;
    }

    public String firstName() {
        return this.firstName;
    }

    public String surname() {
        return this.surname;
    }

    public String country() {
        return this.country;
    }

    public String email() {
        return this.email;
    }

    public String city() {
        return this.city;
    }

    public String postcode() {
        return this.postcode;
    }

    public String street() {
        return this.street;
    }

    public String flat() {
        return this.flat;
    }

    public String house() {
        return this.house;
    }

    public String ip() {
        return this.ip;
    }
}
