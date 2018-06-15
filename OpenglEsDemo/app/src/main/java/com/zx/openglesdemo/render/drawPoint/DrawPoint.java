package com.zx.openglesdemo.render.drawPoint;

import android.os.Bundle;

import com.zx.openglesdemo.BaseActivity;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: ShaudXiao
 * Date: 2018-06-13
 * Time: 16:47
 * Company: zx
 * Description:
 * FIXME
 */

public class DrawPoint extends BaseActivity {

    float[] vertexArray = new float[]{
            -0.8f, -0.4f * 1.732f, 0.0f,
            0.8f, -0.4f * 1.732f, 0.0f,
            0.0f, 0.4f * 1.732f, 0.0f,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void drawScene(GL10 gl) {
        super.drawScene(gl);

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexArray.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vetex = vbb.asFloatBuffer();
        vetex.put(vertexArray);
        vetex.position(0);

        gl.glClearColor(1.0f, 0, 0, 1.0F);
        gl.glPointSize(8f);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vetex);
        gl.glDrawArrays(GL10.GL_POINTS, 0, 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }
}
