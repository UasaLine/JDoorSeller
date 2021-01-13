package com.jds.service;

import com.jds.dao.entity.DoorEntity;
import com.jds.dao.entity.DoorOrder;
import com.jds.dao.entity.LineSpecification;
import lombok.NonNull;

import java.util.List;

public interface DoorService {
    DoorEntity getDoor(@NonNull int id, @NonNull int orderId, @NonNull int typid);
    DoorEntity calculate(@NonNull DoorEntity door);
    DoorEntity saveDoor(@NonNull DoorEntity door);
    DoorOrder deleteDoorFromOrder(@NonNull String id, @NonNull String orderId);
    List<LineSpecification> getSpecificationByDoorId(@NonNull String doorId);
}
