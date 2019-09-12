package com.test.samsapplication.ui.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.test.samsapplication.R;

/**
 * Item decorator for recycle view
 */
public class RecyclerViewItemSpacingDecoration  extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;
    private Context mContext;

    public RecyclerViewItemSpacingDecoration(Context context) {
        mContext = context;
        this.verticalSpaceHeight = (int) context.getResources().getDimension(R.dimen.common_margin_2dp);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
        if(UIHelper.isLargeDevice(mContext)){
            outRect.right = verticalSpaceHeight;
            outRect.left = verticalSpaceHeight;
        } else {
            outRect.right = verticalSpaceHeight * 2;
            outRect.left = verticalSpaceHeight * 2;
        }
        outRect.top = verticalSpaceHeight;
    }

}
