package com.jds.model.position.impl;

import com.jds.dao.entity.GlassPositionEntity;
import com.jds.model.position.GlassPosition;
import com.jds.model.position.GlassPositionAbstract;

public class GlassPositionBottom extends GlassPositionAbstract implements GlassPosition {

    public GlassPositionBottom(GlassPositionEntity position, int doorHeight, int doorWidth) {
        this.position = position;
        this.doorHeight = doorHeight;
        this.doorWidth = doorWidth;
    }

    @Override
    public int height() {
        return position.getValue2();
    }

    @Override
    public int width() {
        return position.getValue1();
    }

    @Override
    public int leftIndent() {
        return (doorWidth - position.getValue1()) / 2;
    }

    @Override
    public int bottomIndent() {
        return position.getValue3();
    }
}
