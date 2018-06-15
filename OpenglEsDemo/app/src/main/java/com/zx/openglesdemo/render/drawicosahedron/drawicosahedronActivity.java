package com.zx.openglesdemo.render.drawicosahedron;

import android.os.Bundle;

import com.zx.openglesdemo.BaseActivity;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * User: ShaudXiao
 * Date: 2018-06-14
 * Time: 10:10
 * Company: zx
 * Description:
 * FIXME
 */

public class drawicosahedronActivity extends BaseActivity {

    private static float X = .525731112119133606f;
    private static float Z = .850650808352039932f;

    static float vertices[] = new float[]{ -X, 0.0f, Z,
            X, 0.0f, Z,
            -X, 0.0f, -Z,
            X, 0.0f, -Z,
            0.0f, Z,
            X, 0.0f, Z,
            -X, 0.0f, -Z,
            X, 0.0f, -Z,
            -X, Z,
            X, 0.0f, -Z,
            X, 0.0f, Z,
            -X, 0.0f, -Z,
            -X, 0.0f };
    static short indices[] = new short[]{
            0,4,1,
            0,9,4,
            9,5,4,
            4,5,8,
            4,8,1,
            8,10,1,
            8,3,10,
            5,3,8,
            5,2,3,
            2,7,3,
            7,10,3,
            7,6,10,
            7,11,6,
            11,0,6,
            0,1,6,
            6,1,10,
            9,0,11,
            9,11,2,
            9,2,5,
            7,2,11 };

    float[] colors = {
            0f, 0f, 0f,
            1f, 0f, 0f,
            1f, 1f, 0f,
            1f, 0f, 1f,
            0f, 1f, 1f,
            1f, 1f, 0f,
            0f, 1f, 1f,
            0f, 1f, 1f,
            1f, 1f, 0f,
            1f, 1f, 1f,
            1f, 1f, 1f,
            0f, 0f, 1f,
            0f, 1f, 0f,
            1f, 0f, 0f,
            1f, 1f, 1f,
            0f, 1f, 1f };

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private ShortBuffer indexBuffer;

    private float angle = 0.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 4);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    @Override
    public void drawScene(GL10 gl) {
        super.drawScene(gl);

        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);
        gl.glRotatef(angle, 0, 1, 0);

        gl.glFrontFace(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0 , colorBuffer);

        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glDisable(GL10.GL_CULL_FACE);
        angle++;


    }
}
