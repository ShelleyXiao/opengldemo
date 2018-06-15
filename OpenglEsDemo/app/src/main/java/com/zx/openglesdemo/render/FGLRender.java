package com.zx.openglesdemo.render;

import android.opengl.GLES20;
import android.view.View;

import com.zx.openglesdemo.render.Triangle.Triangle;
import com.zx.openglesdemo.utils.LogUtils;

import java.lang.reflect.Constructor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * User: ShaudXiao
 * Date: 2018-06-15
 * Time: 11:09
 * Company: zx
 * Description:
 * FIXME
 */

public class FGLRender extends Shape {

    private Shape mShape;
    private Class<?  extends Shape> clazz = Triangle.class;

    public FGLRender(View view) {
        super(view);
    }

    public void setShape(Class< ? extends Shape > clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        LogUtils.i("onSurfaceCreated");
        GLES20.glClearColor(0.5f, 0.5f,0.5f, 1.0f );
        try {
            Constructor constructor = clazz.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            mShape = (Shape) constructor.newInstance(view);
        } catch (Exception e) {
            e.printStackTrace();
            mShape = new Triangle(view);
        }
        mShape.onSurfaceCreated(gl, config);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        LogUtils.i("onSurfaceChanged");
        GLES20.glViewport(0 , 0, width, height);
        mShape.onSurfaceChanged(gl, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        LogUtils.i("onDrawFrame");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
        mShape.onDrawFrame(gl);
    }
}
