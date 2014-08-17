package com.bahus.ConwayLife.GUI;

import java.awt.*;

/**
 * Created by denislavrov on 8/17/14.
 */
public class MouseTracer {
    private Point totalDelta;
    private Point deltaPosition;
    private Point currentPosition;
    private boolean hasReset = true;

    public MouseTracer(){
        totalDelta = new Point();
        deltaPosition = new Point();
        currentPosition = new Point();
    }

    public void updatePosition(Point currentPosition){
        if (!hasReset) {
            deltaPosition.x += (this.currentPosition.x - currentPosition.x);
            deltaPosition.y += (this.currentPosition.y - currentPosition.y);
        }
        this.currentPosition = currentPosition;
        hasReset = false;
    }

    public Point getDelta(){
        return deltaPosition;
    }

    public Point getTotalDelta(){
        return new Point(totalDelta.x+deltaPosition.x, totalDelta.y+deltaPosition.y);
    }

    public int tdx(){
        return totalDelta.x+deltaPosition.x;
    }

    public int tdy(){
        return totalDelta.y+deltaPosition.y;
    }

    public void resetDelta(){
        totalDelta.x += deltaPosition.x;
        totalDelta.y += deltaPosition.y;
        deltaPosition.x = 0;
        deltaPosition.y = 0;
        hasReset = true;
    }

    public void resetTotalDelta(){
        deltaPosition.x = 0;
        deltaPosition.y = 0;
        totalDelta.x = deltaPosition.x;
        totalDelta.y = deltaPosition.y;
    }
}
