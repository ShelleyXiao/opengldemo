package com.zx.openglesdemo.render.solarsystem;

import android.opengl.GLU;
import android.os.Bundle;

import com.zx.openglesdemo.BaseActivity;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: ShaudXiao
 * Date: 2018-06-14
 * Time: 14:39
 * Company: zx
 * Description:
 * FIXME
 */

public class DrawSolarSystem extends BaseActivity {

    private Star sun = new Star();
    private Star earth = new Star();
    private Star moon = new Star();

    private int angle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void drawScene(GL10 gl) {
        super.drawScene(gl);

        gl.glLoadIdentity();;
        GLU.gluLookAt(gl, 0.0f, 0, 15.0f,
                0, 0, 0,
                0, 1.0f, 0);
        gl.glPushMatrix();

        gl.glRotatef(angle, 0, 0, 1);
        gl.glColor4f(1.0f, 0, 0, 1.0f);

        sun.draw(gl);

        gl.glPopMatrix();

        gl.glPushMatrix();

        gl.glRotatef(-angle, 0, 0, 1);
        gl.glTranslatef(3, 0, 0);
        gl.glScalef(0.5f, 0.5f, 0.5f);

        gl.glColor4f(0, 0, 1.0f, 1.0f);

        earth.draw(gl);

        gl.glPushMatrix();
        gl.glRotatef(-angle, 0, 0, 1);
        gl.glTranslatef(5, 0, 0);
        gl.glScalef(.5f, .5f, .5f);
        gl.glRotatef(angle*10, 0, 0, 1);
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        moon.draw(gl);

        gl.glPopMatrix();
        gl.glPopMatrix();

        angle++;
    }
}
