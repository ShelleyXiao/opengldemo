package com.zx.openglesdemo.render;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.view.View;

import com.zx.openglesdemo.utils.LogUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

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

public class Oval extends Shape {

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



    private float radius = 1.0f;
    private int n = 360;
    private float[] shapePos;
    private float height = 0.0f;

    private final int vertexStride = 0;

    private int mProgram;
    private FloatBuffer mVertexBuffer;
    private ShortBuffer mIndexBuffer;

    private int mPositionHandler;
    private int mColorHandler;
    private int mMatrixHandler;

    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    public Oval(View view) {
       this(view, 0.0f);
    }

    public Oval(View view,float height) {
        super(view);

        this.height = height;
        shapePos = createPositions();

        ByteBuffer vbb = ByteBuffer.allocateDirect(shapePos.length * 4);
        vbb.order(ByteOrder.nativeOrder());

        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(shapePos);
        mVertexBuffer.position(0);


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


        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,
                0, shapePos.length / 3);

        GLES20.glDisableVertexAttribArray(mPositionHandler);
    }

    public void setRadius(float radius){
        this.radius=radius;
    }

    public void setMatrix(float[] matrix){
        this.mMVPMatrix=matrix;
    }

    private float[]  createPositions(){
        ArrayList<Float> data=new ArrayList<>();
        data.add(0.0f);             //设置圆心坐标
        data.add(0.0f);
        data.add(height);
        float angDegSpan=360f/n;
        for(float i=0;i<360+angDegSpan;i+=angDegSpan){
            data.add((float) (radius*Math.sin(i*Math.PI/180f)));
            data.add((float)(radius*Math.cos(i*Math.PI/180f)));
            data.add(height);
        }
        float[] f=new float[data.size()];
        for (int i=0;i<f.length;i++){
            f[i]=data.get(i);
        }
        return f;
    }
}
