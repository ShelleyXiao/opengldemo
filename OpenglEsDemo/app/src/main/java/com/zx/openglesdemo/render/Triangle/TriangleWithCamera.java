package com.zx.openglesdemo.render.Triangle;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.view.View;

import com.zx.openglesdemo.render.Shape;
import com.zx.openglesdemo.utils.LogUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * User: ShaudXiao
 * Date: 2018-06-15
 * Time: 10:08
 * Company: zx
 * Description:
 * FIXME
 */

public class TriangleWithCamera extends Shape {

    private final String vertexShaderCode =
            "attribute vec4 vPosition;"
                    + "uniform mat4 vMatrix;"
                    + "void main() {"
                    + " gl_Position = vMatrix * vPosition;"
                    + "}";
    ;
    private final String fragmentShaderCode =
            "precision mediump float;"
                    + "uniform vec4 vColor;"
                    + "void main() { "
                    + " gl_FragColor = vColor;"
                    + "}";

    private float triangleCoords[] = {
            0.5f, 0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };

    private float[] color = {
            1.0f, 1.0f, 1.0f, 1.0f
    };

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;

    private final int vertexStride = COORDS_PER_VERTEX * 4;

    private float[] mVertexMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    private static final int COORDS_PER_VERTEX = 3;

    private FloatBuffer vertexBuffer;

    private int mPrograme;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMatrixHandler;


    public TriangleWithCamera(View view) {
        super(view);

        ByteBuffer vbb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        vbb.order(ByteOrder.nativeOrder());

        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mPrograme = GLES20.glCreateProgram();
        if(mPrograme == 0) {
            LogUtils.e("could not create program");
            return;
        }
        GLES20.glAttachShader(mPrograme, vertexShader);
        GLES20.glAttachShader(mPrograme, fragmentShader);
        GLES20.glLinkProgram(mPrograme);
        final int linkStatus[] = new int[1];
        GLES20.glGetProgramiv(mPrograme, GLES20.GL_LINK_STATUS, linkStatus, 0);

        LogUtils.d("Link program info: " +
            GLES20.glGetProgramInfoLog(mPrograme));

        if(linkStatus[0] == 0) {
            LogUtils.e("could not link program");
            return ;
        }

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        float ratio = (float) width / height;

        Matrix.frustumM(mProjectMatrix, 0,
                -ratio, ratio, -1, 1, 3, 7);
        Matrix.setLookAtM(mViewMatrix, 0,
                0, 0, 7.0f,
                0.0f, 0f, 0f,
                0f, 1.0f,0f);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glUseProgram(mPrograme);

        mMatrixHandler = GLES20.glGetUniformLocation(mPrograme, "vMatrix");
        GLES20.glUniformMatrix4fv(mMatrixHandler, 1, false, mMVPMatrix, 0);

        mPositionHandle = GLES20.glGetAttribLocation(mPrograme, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        mColorHandle = GLES20.glGetUniformLocation(mPrograme, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
