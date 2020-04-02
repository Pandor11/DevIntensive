package ru.skillbranch.devintensive.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class ViewBelowAppBarBehavior extends AppBarLayout.ScrollingViewBehavior {
    private Context mContext;
    private Resources r = Resources.getSystem();
    private float mStartChildHeight;
    private float mAppBarStartYPosition;
    private int mAppBarFinalYPosition;
    private float mStartAppBarPosition;
    private int mMaxAppBarSize;
    private float mFinalChildHeight = Math.round(TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 68, r.getDisplayMetrics()));


    public ViewBelowAppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NotNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        InitProperties(child, dependency);

        float maxAppBarScrollDistance = (-462f);
        float PercentageOfAppBarScale = dependency.getY() / maxAppBarScrollDistance;
        float MaxCollapseSize = mStartChildHeight - mFinalChildHeight;

        if (dependency.getY() < mAppBarStartYPosition) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.height = (int) (mStartChildHeight - (MaxCollapseSize * PercentageOfAppBarScale));
            child.setLayoutParams(lp);
        } else {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.height = (int) mStartChildHeight;
            child.setLayoutParams(lp);
        }

        return false;
    }

    private void InitProperties(View child, View dependency) {
        if (mStartChildHeight == 0)
            mStartChildHeight = child.getHeight();

        if (mAppBarStartYPosition == 0)
            mAppBarStartYPosition = (int) (dependency.getY());

        if (mAppBarFinalYPosition == 0)
            mAppBarFinalYPosition = (dependency.getHeight() / 2);

        if (mStartAppBarPosition == 0)
            mStartAppBarPosition = dependency.getY();

        if (mMaxAppBarSize == 0)
            mMaxAppBarSize = dependency.getHeight();


    }
    
}
    

