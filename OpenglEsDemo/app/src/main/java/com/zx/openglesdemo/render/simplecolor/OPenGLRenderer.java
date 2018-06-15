package com.zx.openglesdemo.render.simplecolor;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.zx.openglesdemo.IOpenGLDemo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * User: ShaudXiao
 * Date: 2018-06-13
 * Time: 16:00
 * Company: zx
 * Description:
 * FIXME
 */

public class OPenGLRenderer implements GLSurfaceView.Renderer {

    private final IOpenGLDemo mOpenGLDemo;

    public OPenGLRenderer(IOpenGLDemo demo) {
        this.mOpenGLDemo = demo;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float) width/ (float)height,
                0.1f, 100.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        if(null != mOpenGLDemo) {
            mOpenGLDemo.drawScene(gl10);
        }
    }
}
