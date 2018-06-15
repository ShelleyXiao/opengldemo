package com.zx.openglesdemo.render;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.view.View;

import com.zx.openglesdemo.utils.LogUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * User: ShaudXiao
 * Date: 2018-06-15
 * Time: 15:43
 * Company: zx
 * Description:
 * FIXME
 */

public class Square extends Shape {

    private final String vertexShaderCode =
            "attribute vec4 vPosition;"
                    + "uniform mat4 vMatrix;"
                    + "void main() {"
                    + " gl_Position = vMatrix * vPosition;"
                    + "}";
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {
            -0.5f,  0.5f, 0.0f, // top left
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f, // bottom right
            0.5f,  0.5f, 0.0f  // top right
    };

    static short index[]={
            0,1,2,0,2,3
    };

    //设置颜色，依次为红绿蓝和透明通道
    float color[] = {

            1.0f, 1.0f, 1.0f, 1.0f
    };

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX / 4;

    private int mProgram;
    private FloatBuffer mVertexBuffer;
    private ShortBuffer mIndexBuffer;

    private int mPositionHandler;
    private int mColorHandler;
    private int mMatrixHandler;

    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    public Square(View view) {
        super(view);

        ByteBuffer vbb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        vbb.order(ByteOrder.nativeOrder());

        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(triangleCoords);
        mVertexBuffer.position(0);

        ByteBuffer ibb = ByteBuffer.allocateDirect(index.length * 2);
        ibb.order(ByteOrder.nativeOrder());

        mIndexBuffer = ibb.asShortBuffer();
        mIndexBuffer.put(index);
        mIndexBuffer.position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);


        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        final int linkStatus[] = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);

        LogUtils.d("Link program info: " +
                GLES20.glGetProgramInfoLog(mProgram));

        if(linkStatus[0] == 0) {
            LogUtils.e("could not link program");
            return ;
        }

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio,
                -1, 1, 3, 7);
        Matrix.setLookAtM(mViewMatrix, 0,
                0, 0, 7.0f,
                0.0f, 0, 0,
                0, 1.0f, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glUseProgram(mProgram);

        mMatrixHandler = GLES20.glGetUniformLocation(mProgram, "vMatrix");
        GLES20.glUniformMatrix4fv(mMatrixHandler, 1, false, mMVPMatrix, 0);

        mPositionHandler = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandler);
        GLES20.glVertexAttribPointer(mPositionHandler, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, mVertexBuffer
                );

        mColorHandler = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandler, 1, color, 0);

        //绘制三角形
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);
        //索引法绘制正方形
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,
                index.length,
                GLES20.GL_UNSIGNED_SHORT,mIndexBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandler);
    }
}
