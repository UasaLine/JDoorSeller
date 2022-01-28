package com.jds.model.position;

import com.jds.dao.entity.GlassPositionEntity;
import com.jds.model.enumModels.GlassPositionType;
import com.jds.model.position.impl.GlassPositionBottom;
import com.jds.model.position.impl.GlassPositionMax;

public class GlassPositionFactory {
    public GlassPosition getInstance(GlassPositionEntity positionEntity, int doorHeight, int doorWidth) {
        GlassPositionType type = positionEntity.getType();
        GlassPosition result;

        if (type == GlassPositionType.MAXIMUM_AREA) {
            result = new GlassPositionMax(positionEntity, doorHeight, doorWidth);
        } else if (type == GlassPositionType.BOTTOM) {
            result = new GlassPositionBottom(positionEntity, doorHeight, doorWidth);
        } else {
            throw new IllegalStateException("GlassPositionType in GlassPositionEntity has no implementation model");
        }
        return result;
    }
}
