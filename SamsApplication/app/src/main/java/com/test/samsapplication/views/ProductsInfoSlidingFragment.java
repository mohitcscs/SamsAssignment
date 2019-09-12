package com.test.samsapplication.views;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.test.samsapplication.R;
import com.test.samsapplication.ui.utils.UIHelper;
import com.test.samsapplication.ui.utils.adapters.ProductInfoPagerAdapter;

public class ProductsInfoSlidingFragment  extends Fragment {

    static final String TAG = ProductsInfoSlidingFragment.class.getSimpleName();

    private ViewPager mViewPager;
    private ImageView rightArrow;
    private ImageView leftArrow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        int currentPosition = 0;

        if(getActivity().getIntent() != null)  //Get selected item from previous page.
        {
            Bundle bundle = getActivity().getIntent().getExtras();
            if (bundle != null) {
                currentPosition = bundle.getInt(UIHelper.BUNDLE_PRODUCT_KEY);
            }
        }
        mViewPager = view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new ProductInfoPagerAdapter());

        // Move the mViewPager position to selected product position.
        Log.d(TAG, "Loading item with position:: " + currentPosition);
        mViewPager.setCurrentItem(currentPosition);

        //Show user right image to move to next asset..Which can be hidden in next launches
        rightArrow = view.findViewById(R.id.rightArrow);
        rightArrow.setOnClickListener(onClickListener);

        leftArrow = view.findViewById(R.id.leftArrow);
        leftArrow.setOnClickListener(onClickListener);
    }

    /**
     * Click listener
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == rightArrow)
            {
                mViewPager.arrowScroll(View.FOCUS_RIGHT);
            }
            else if (view == leftArrow)
            {
                mViewPager.arrowScroll(View.FOCUS_LEFT);
            }
        }
    };

}

