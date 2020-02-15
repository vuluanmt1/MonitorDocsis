package com.example.monitordocsis;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

public class Global {
    public static void animator_button(View view){
        Animator scale = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 0.9f, 1), PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 0.9f, 1));
        scale.setDuration(200);
        scale.start( );
    }
}
