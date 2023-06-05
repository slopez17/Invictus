package co.com.uma.mseei.invictus.model;

import java.io.Serializable;

public class AccelerometerServiceParameters implements Serializable {

    public static final String EXT_TXT = ".txt";
    public static final String SYMBOL_UNDERSCORE = "_";

    int gender;
    float weight;
    int sportId;
    SportType sportType;
    boolean isAutofinishOn;
    int samplesLimit;
    int samplesOnMemory;
    boolean isSaveOnSdOn;
    String fileName;
    boolean isDebugOn;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public SportType getSportType() {
        return sportType;
    }

    public void setSportType(SportType sportType) {
        this.sportType = sportType;
    }

    public boolean isAutofinishOn() {
        return isAutofinishOn;
    }

    public void setAutofinish(boolean autofinishState) {
        isAutofinishOn = autofinishState;
    }

    public int getSamplesLimit() {
        return samplesLimit;
    }

    public void setSamplesLimit(int samplesLimit) {
        this.samplesLimit = samplesLimit;
    }

    public int getSamplesOnMemory() {
        return samplesOnMemory;
    }

    public void setSamplesOnMemory(int samplesOnMemory) {
        this.samplesOnMemory = samplesOnMemory;
    }

    public boolean isSaveOnSdOn() {
        return isSaveOnSdOn;
    }

    public void setSaveOnSd(boolean saveOnSdState) {
        isSaveOnSdOn = saveOnSdState;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName, boolean isMultipleFilesOn) {
        this.fileName = isMultipleFilesOn ? fileName + SYMBOL_UNDERSCORE + sportId + EXT_TXT
                : fileName + EXT_TXT;
    }

    public boolean isDebugOn() {
        return isDebugOn;
    }

    public void setDebug(boolean debugState) {
        isDebugOn = debugState;
    }
}
