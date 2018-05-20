package javahelps.com.test3database;

public class SpiderGraphDetails {

    private Double area;
    private Double price;
    private Double estimatedRent;
    private String houseType;
    private String zipCode;

    public SpiderGraphDetails(Double area, Double price, Double estimatedRent, String houseType, String zipCode) {
        this.area = area;
        this.price = price;
        this.estimatedRent = estimatedRent;
        this.houseType = houseType;
        this.zipCode = zipCode;
    }

    public Double getArea() {
        return area;
    }

    public Double getPrice() {
        return price;
    }

    public Double getEstimatedRent() {
        return estimatedRent;
    }

    public String getHouseType() {
        return houseType;
    }

    public String getZipCode() {
        return zipCode;
    }
}
