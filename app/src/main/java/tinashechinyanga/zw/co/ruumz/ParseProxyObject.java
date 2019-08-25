package tinashechinyanga.zw.co.ruumz;

/**
 * Created by Tinashe on 5/29/2016.
 */
// By Janaka Jayasuriya, @pinkydoe
// Original By Jamie Chapman, @chappers57
// License: open, do as you wish, just don't blame me if stuff breaks ;-)
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class ParseProxyObject implements Serializable {

    public static final String PARSE_LOCAL_OBJECT = "parse_local_object";

    private HashMap<String, Object> values = new HashMap<String, Object>();
    private HashMap<String, String> fileUrls = new HashMap<String, String>();
    private HashMap<String, double[]> geoPoints = new HashMap<String, double[]>();
    private String className;
    private Date createdAt;
    private String objectId;
    private Date updatedAt;

    public ParseProxyObject(ParseObject parseObject) {

        for(String key : parseObject.keySet()) {
            Class classType = parseObject.get(key).getClass();
            if(classType == Boolean.class ||
                    classType == byte[].class ||
                    classType == Date.class ||
                    classType == Double.class ||
                    classType == Integer.class ||
                    classType == Long.class ||
                    classType == Number.class ||
                    classType == String.class) {
                values.put(key, parseObject.get(key));
            } else if (classType == ParseFile.class) {
                // In the case of ParseFile, the url to the file will be retained as a String, since ParseFile is not serializable
                fileUrls.put(key, ((ParseFile) parseObject.get(key)).getUrl());
            } else if (classType == ParseGeoPoint.class) {
                // In the case of a ParseGeoPoint, the doubles values for lat, long will be retained in a double[], since ParseGeoPoint is not serializable
                double[] latlong = {((ParseGeoPoint)parseObject.get(key)).getLatitude(), ((ParseGeoPoint)parseObject.get(key)).getLongitude()};
                geoPoints.put(key, latlong);
            }

            this.className = parseObject.getClassName();
            this.createdAt = parseObject.getCreatedAt();
            this.objectId = parseObject.getObjectId();
            this.updatedAt = parseObject.getUpdatedAt();
        }
    }

    public boolean getBoolean(String key) {
        if(values.containsKey(key)) {
            return (Boolean)values.get(key);
        } else {
            return false;
        }
    }

    public byte[] getBytes(String key) {
        if(values.containsKey(key)) {
            return (byte[])values.get(key);
        } else {
            return null;
        }
    }

    public String getClassName() {
        return className;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getDate(String key) {
        if(values.containsKey(key)) {
            return (Date)values.get(key);
        } else {
            return null;
        }
    }

    public double getDouble(String key) {
        if(values.containsKey(key)) {
            return (Double) values.get(key);
        } else {
            return 0;
        }
    }

    public int getInt(String key) {
        if(values.containsKey(key)) {
            return (Integer)values.get(key);
        } else {
            return 0;
        }
    }

    public long getLong(String key) {
        if(values.containsKey(key)) {
            return (Long)values.get(key);
        } else {
            return 0;
        }
    }

    public Number getNumber(String key) {
        if(values.containsKey(key)) {
            return (Number)values.get(key);
        } else {
            return null;
        }
    }

    public String getObjectId() {
        return objectId;
    }

    //Note: only the url to the file is returned, not an actual ParseFile
    public String getRoomImageParseFile(String key) {
        if(fileUrls.containsKey(key)) {
            return fileUrls.get(key);
        } else {
            return null;
        }
    }

    // Note only the lat, long values are returned, not the actual ParseGeoPoint
    // [0] latitude
    // [1] longitude
    public double[] getParseGeoPointArray(String key) {
        if(geoPoints.containsKey(key)) {
            return geoPoints.get(key);
        } else {
            return null;
        }
    }

    public String getString(String key) {
        if(values.containsKey(key)) {
            return (String)values.get(key);
        } else {
            return null;
        }
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

}
