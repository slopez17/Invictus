package co.com.uma.mseei.invictus.model.service;

import static co.com.uma.mseei.invictus.util.GeneralConstants.EXT_TXT;
import static co.com.uma.mseei.invictus.util.GeneralConstants.UNDERSCORE;

import java.io.Serializable;

import co.com.uma.mseei.invictus.model.SportType;

/**
 * AccelerometerServiceParameters is a model class which represents business logic to deliver
 * configuration parameters used in ListenAccelerometerService.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class AccelerometerServiceParameters implements Serializable {

    private int gender;
    private float weight;
    private int sportId;
    private SportType sportType;
    private boolean isAutofinishOn;
    private int samplesLimit;
    private int samplesOnMemory;
    private boolean isSaveOnSdOn;
    private String fileName;
    private boolean isDebugOn;

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
        this.fileName = isMultipleFilesOn ? fileName + UNDERSCORE + sportId + EXT_TXT
                : fileName + EXT_TXT;
    }

    public boolean isDebugOn() {
        return isDebugOn;
    }

    public void setDebug(boolean debugState) {
        isDebugOn = debugState;
    }
}
