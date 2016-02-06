package ru.galuzin.hibernate.test;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@Table(name="address")
public class Address {
private long addressID;
private String streetAddress;
private String city;
private String zip;
@Id
@GeneratedValue(generator="increment")
@GenericGenerator(name = "increment" , strategy = "increment")
public long getAddressID() {
return addressID;
}
public void setAddressID(long addressID) {
this.addressID = addressID;
}
    @Column(name="streetaddress")
public String getStreetAddress() {
return streetAddress;
}
public void setStreetAddress(String streetAddress) {
this.streetAddress = streetAddress;
}
    @Column(name="city")
public String getCity() {
return city;
}
public void setCity(String city) {
this.city = city;
}
    @Column(name="zip")
public String getZip() {
return zip;
}
public void setZip(String zip) {
this.zip = zip;
}
}
