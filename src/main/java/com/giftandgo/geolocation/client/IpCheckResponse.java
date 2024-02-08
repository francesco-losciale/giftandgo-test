package com.giftandgo.geolocation.client;

public class IpCheckResponse {

    private String countryCode;
    private String isp;

    // needed for json mapping
    public IpCheckResponse() {
    }
    public IpCheckResponse(String countryCode, String isp) {
        this.countryCode = countryCode;
        this.isp = isp;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getIsp() {
        return isp;
    }

}
