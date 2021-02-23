package com.jds.model;
import com.jds.dao.entity.DoorType;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class ShortTemplate implements Comparable<ShortTemplate> {

    private int doorTypeId;
    private String typeName;
    private String className;

    public ShortTemplate(DoorType doorType) {
        this.doorTypeId = doorType.getId();
        typeName = doorType.getName();
        className = doorType.getDoorClass().getName();
    }

    @Override
    public int compareTo(ShortTemplate o) {
        return typeName.compareTo(o.getTypeName());

    }
}
