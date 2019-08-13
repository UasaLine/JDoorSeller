package com.jds.model;

import com.jds.entity.BendSetting;
import com.jds.entity.DoorColors;
import com.jds.entity.DoorType;
import com.jds.entity.SalarySetting;
import com.jds.model.modelEnum.TypeOfSalaryConst;

import java.util.Map;

public class PayrollSettings {

    private DoorColors doorColors;
    private BendSetting bendSetting;
    private DoorType doorType;
    private SalarySetting salarySetting;
    private Map<TypeOfSalaryConst,Double> ConstMap;

    public PayrollSettings() {
    }

    public DoorColors getDoorColors() {
        return doorColors;
    }

    public void setDoorColors(DoorColors doorColors) {
        this.doorColors = doorColors;
    }

    public BendSetting getBendSetting() {
        return bendSetting;
    }

    public void setBendSetting(BendSetting bendSetting) {
        this.bendSetting = bendSetting;
    }

    public DoorType getDoorType() {
        return doorType;
    }

    public void setDoorType(DoorType doorType) {
        this.doorType = doorType;
    }

    public SalarySetting getSalarySetting() {
        return salarySetting;
    }

    public void setSalarySetting(SalarySetting salarySetting) {
        this.salarySetting = salarySetting;
    }

    public Map<TypeOfSalaryConst, Double> getConstMap() {
        return ConstMap;
    }

    public void setConstMap(Map<TypeOfSalaryConst, Double> constMap) {
        ConstMap = constMap;
    }
}