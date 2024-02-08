package com.giftandgo.geolocation.client;

public class IpCheckResponse {

    private String status;
    private String countryCode;
    private String isp;

    // needed for json mapping
    public IpCheckResponse() {
    }
    public IpCheckResponse(String countryCode, String isp, String status) {
        this.countryCode = countryCode;
        this.isp = isp;
        this.status = status;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getIsp() {
        return isp;
    }

    public String getStatus() {
        return status;
    }
}
