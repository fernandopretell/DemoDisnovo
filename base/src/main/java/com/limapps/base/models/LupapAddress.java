package com.limapps.base.models;

import java.util.List;

public class LupapAddress {
    public static final String RESPONSE = "response";
    public static final String TYPE = "type";
    public static final String FEATURE = "Feature";
    public static final String FEATURE_COLLECTION = "FeatureCollection";
    public static final String FEATURES = "features";

    private String type;
    private Geometry geometry;
    private Properties properties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return properties.address != null ? properties.address : "";
    }

    public static class Geometry {
        public String type;
        public List<Double> coordinates; // [longitude, latitude]
    }

    public class Properties {
        public String id;
        public String commonName;
        public String country;
        public String address;
        public String city;
        public String postCode;
        public String attribution;
        public String admin1;
        public String admin2;
        public String admin3;
        public String admin4;
        public String admin5;
    }
}
