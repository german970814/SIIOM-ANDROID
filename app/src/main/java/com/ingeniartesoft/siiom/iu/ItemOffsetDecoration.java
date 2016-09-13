package com.ingeniartesoft.siiom.iu;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by german on 13/09/16.
 */
public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    // Medida en pixeles del espaciado
    private int mItemOffset;

    public ItemOffsetDecoration (Context context, @IntegerRes int integerResId) {
        int itemOffsetDp = context.getResources().getInteger(integerResId);
        mItemOffset = convertToPx(itemOffsetDp, context.getResources().getDisplayMetrics());
    }

    public int convertToPx (int offsetDp, DisplayMetrics metrics) {
        return offsetDp * (metrics.densityDpi / 160);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }
}
