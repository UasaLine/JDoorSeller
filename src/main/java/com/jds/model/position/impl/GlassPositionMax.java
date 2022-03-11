package com.jds.model.position.impl;

import com.jds.dao.entity.GlassPositionEntity;
import com.jds.model.position.GlassPosition;
import com.jds.model.position.GlassPositionAbstract;

public class GlassPositionMax extends GlassPositionAbstract implements GlassPosition {

    public GlassPositionMax(GlassPositionEntity position, int doorHeight, int doorWidth) {
        this.position = position;
        this.doorHeight = doorHeight;
        this.doorWidth = doorWidth;
    }

    @Override
    public int height() {
        return doorHeight - position.getValue3() - position.getValue4();
    }

    @Override
    public int width() {
        return doorWidth - position.getValue1() - position.getValue2();
    }

    @Override
    public int leftIndent() {
        return position.getValue1();
    }

    @Override
    public int bottomIndent() {
        return position.getValue3();
    }
}
