package com.zx.openglesdemo.render.drawline;

import com.zx.openglesdemo.BaseActivity;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: ShaudXiao
 * Date: 2018-06-14
 * Time: 09:56
 * Company: zx
 * Description:
 * FIXME
 */

public class LineSegment extends BaseActivity {

    float[] vertexArray = new float[]{
            -0.8f, -0.4f * 1.732f, 0.0f,
            0.8f, -0.4f * 1.732f, 0.0f,
            0.0f, 0.4f * 1.732f, 0.0f,
    };

    @Override
    public void drawScene(GL10 gl) {
        super.drawScene(gl);

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexArray.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertex = vbb.asFloatBuffer();
        vertex.put(vertexArray);
        vertex.position(0);

        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);
        int index = 0;
        index++;
        index%=10;
        switch (index){
            case 0:
            case 1:
            case 2:
                gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
                gl.glDrawArrays(GL10.GL_LINES, 0, 4);
                break;
            case 3:
            case 4:
            case 5:
                gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
                gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 4);
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
                gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);
                break;
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }
}
