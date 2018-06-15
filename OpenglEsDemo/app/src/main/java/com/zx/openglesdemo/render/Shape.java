package com.zx.openglesdemo.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.View;

import com.zx.openglesdemo.utils.LogUtils;

/**
 * User: ShaudXiao
 * Date: 2018-06-15
 * Time: 10:16
 * Company: zx
 * Description:
 * FIXME
 */

public abstract class Shape implements GLSurfaceView.Renderer {

    protected View view;

    public Shape(View view) {
        this.view = view;
    }

    public int loadShader(int type, String shaderSource) {
        final int shaderObjectId = GLES20.glCreateShader(type);
        if (shaderObjectId == 0) {
            LogUtils.i("Could not create new Shader");
            return 0;
        }
        GLES20.glShaderSource(shaderObjectId, shaderSource);
        GLES20.glCompileShader(shaderObjectId);

        LogUtils.i("compile info: "
                + GLES20.glGetShaderInfoLog(shaderObjectId));
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            GLES20.glDeleteShader(shaderObjectId);
            LogUtils.i("Could not compile  Shader "
                    + (type == GLES20.GL_VERTEX_SHADER ? " Vertex " : " Fragment"));
            return 0;
        }

        return shaderObjectId;
    }
}
