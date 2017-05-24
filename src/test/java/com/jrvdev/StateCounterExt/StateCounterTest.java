package com.jrvdev.StateCounterExt;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import org.junit.Test;

import javax.swing.KeyStroke;

import VASSAL.build.module.Map;
import VASSAL.command.Command;
import VASSAL.counters.GamePiece;
import VASSAL.counters.Stack;


public class StateCounterTest {
    
    private class MockGamePiece implements GamePiece {

        @Override
        public Object getLocalizedProperty(Object key) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void setMap(Map map) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Map getMap() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void draw(Graphics g, int x, int y, Component obs, double zoom) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Point getPosition() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void setPosition(Point p) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Rectangle boundingBox() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Shape getShape() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Stack getParent() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void setParent(Stack s) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Command keyEvent(KeyStroke stroke) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getName() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getLocalizedName() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getId() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void setId(String id) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public String getType() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getState() {
            return "Mock State";
        }

        @Override
        public void setState(String newState) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void setProperty(Object key, Object val) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Object getProperty(Object key) {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
    
    @Test
    public void parameterless_constructor_getName_does_not_throw() {
        StateCounter c  = new StateCounter();
        
        assertNotNull(c);
        assertEquals(null, c.getName());
    }
    
    @Test(expected = NullPointerException.class)
    public void parameterless_constructor_getState_throws_if_inner_state_not_set() {
        StateCounter c  = new StateCounter();
        
        assertNotNull(c);
        assertEquals(null, c.getState());
    }
    
    @Test
    public void parameterless_constructor_getState_does_not_throw_if_inner_state_set() {
        StateCounter c  = new StateCounter();
        
        assertNotNull(c);

        GamePiece mockGamePiece = new MockGamePiece();
        
        c.setInner(mockGamePiece);;

        assertEquals("\t" + mockGamePiece.getState(), c.getState());
        
    }

}
