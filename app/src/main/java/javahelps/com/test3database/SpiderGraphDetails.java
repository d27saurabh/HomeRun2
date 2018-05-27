package javahelps.com.test3database;

public class SpiderGraphDetails {

    private Double AreaSpace_SQFT = 0.0;
    private Double Price = 0.0;
    private Double EstimatedRent = 0.0;
    private String Status = "";
    private String ZipCode = "";

    public SpiderGraphDetails(Double areaSpace_SQFT, Double price, Double estimatedRent, String status, String zipCode) {
        AreaSpace_SQFT = areaSpace_SQFT;
        Price = price;
        EstimatedRent = estimatedRent;
        Status = status;
        ZipCode = zipCode;
    }

    public Double getAreaSpace_SQFT() {
        return AreaSpace_SQFT;
    }

    public Double getPrice() {
        return Price;
    }

    public Double getEstimatedRent() {
        return EstimatedRent;
    }

    public String getStatus() {
        return Status;
    }

    public String getZipCode() {
        return ZipCode;
    }
}
