package com.multimodal.ihm;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.multimodal.ihm.adapter.ViewPagerAdapter;
import com.multimodal.ihm.fragment.FragmentOne;
import com.multimodal.ihm.fragment.FragmentThree;
import com.multimodal.ihm.fragment.FragmentTwo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private float xPos, yPos;
    private float xPosOnVolumeUpClicked, yPosOnVolumeUpClicked;
    private boolean isReleased = true;
    private int yCalibrate = 100;
    private float xAccel, yAccel;
    private float xMax, yMax;
    private SensorManager sensorManager;
    private ExecutorService executorService;
    private Future<View> futureView;
    private float standingCalibration = 0;
    private int calibrationCount = 0;
    private boolean isCalibrated;
    private  ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private FragmentOne fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        executorService = new ThreadPoolExecutor(1,1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        xMax = (float) size.x - 100;
        yMax = (float) size.y - calculateActionBarHeight() * 2;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        setupViewPager();
        setupCursor();
    }

    private void setupCursor() {
        FrameLayout frameLayout = findViewById(R.id.layout_main);
        BallView ballView = new BallView(this);
        frameLayout.addView(ballView);
    }

    private void setupViewPager() {
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragment1 = new FragmentOne();
        viewPagerAdapter.addFrag(fragment1, "ONE");
        viewPagerAdapter.addFrag(new FragmentTwo(), "TWO");
        viewPagerAdapter.addFrag(new FragmentThree(), "THREE");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        executorService = new ThreadPoolExecutor(1,1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(futureView != null) {
            futureView.cancel(true);
        }
        executorService.shutdownNow();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calibrate:
                isCalibrated = false;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void calibrate() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                vibrate();
                int position = viewPager.getCurrentItem();
                Fragment fragment =  viewPagerAdapter.getRegisteredFragment(position);
                boolean found = treeSearching(tabLayout, fragment, xPos, yPos);
                if(position == 0 && ! found) {
                    if(fragment1 != null){
                         searchInListView(fragment1, xPos, yPos);
                    }
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if(isReleased) {
                    xPosOnVolumeUpClicked = xPos;
                    yPosOnVolumeUpClicked = yPos;
                    isReleased = false;
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            isReleased = true;
            Log.e("main", "keyUp");
            float scrollingX = xPos - xPosOnVolumeUpClicked ;
            float scrollingY = yPos - yPosOnVolumeUpClicked;
            boolean shouldSlide = Math.abs(scrollingX) > Math.abs(scrollingY);
            if(shouldSlide) {
                if (scrollingX > 80) {
                    slideRight();
                } else if (scrollingX < -80) {
                    slideLeft();
                }
            }else{
                int nbOfItemsToScroll = Math.abs((int)(scrollingY / 100)) + 1;
                if (scrollingY > 80) {
                    scrollDown(nbOfItemsToScroll);
                } else if (scrollingY < -80) {
                    scrollUp(nbOfItemsToScroll);
                }
            }
        }
        return true;
    }

    private void scrollUp(int nbOfItemsToScroll) {
        ListView listView = fragment1.getListView();
        int currentPosition = listView.getFirstVisiblePosition();
        listView.smoothScrollToPosition(currentPosition - nbOfItemsToScroll);
    }

    private void scrollDown(int nbOfItemsToScroll) {
        ListView listView = fragment1.getListView();
        int currentPosition = listView.getLastVisiblePosition();
        listView.smoothScrollToPosition(currentPosition + nbOfItemsToScroll);
    }

    private void slideRight() {
        int currentItem = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentItem+1);
    }

    private void slideLeft() {
        int currentItem = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentItem-1);
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (vibrator != null) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        } else {
            //deprecated in API 26
            if (vibrator != null) {
                vibrator.vibrate(50);
            }
        }
    }

    private void searchInListView(FragmentOne fragment, float x, float y){
        ListView listView = fragment.getListView();
        if (listView != null) {
            int totalVisibleElement = listView.getLastVisiblePosition() - listView.getFirstVisiblePosition();
            for(int pos = 0; pos <= totalVisibleElement; pos++){
                View item = listView.getChildAt(pos);
                if(item != null) {
                    Rect bounds = new Rect();
                    item.getHitRect(bounds);
                    if( bounds.contains((int) x,(int) y - yCalibrate)){
                        listView.performItemClick(fragment.getView(),
                                pos + listView.getFirstVisiblePosition(),
                                listView.getAdapter().getItemId(pos));
                        break;
                    }
                }
            }
        }
    }

    private boolean treeSearching(TabLayout tabLayoutView, Fragment fragment, float x, float y) {
        searchElementInTabLayoutToSwitchPage(tabLayoutView, x, y);
        futureView = executorService.submit(() -> searchForElementToClick(fragment.getView(), x,y));
        try {
            View viewToClick = futureView.get();
            if(viewToClick != null) {
                simulatesClick(viewToClick);
                return true;
            }else{
                // TODO increment misClick counter (file?)
                Log.e("Main","");
            }
            return false;
        } catch (ExecutionException | InterruptedException ignored) {
            return false;
        }
    }

    private void simulatesClick(final View viewToClick) {
        //initiate the button
        viewToClick.performClick();
        viewToClick.setPressed(true);
        viewToClick.invalidate();
        // delay completion till animation completes
        //delay button
        viewToClick.postDelayed(() -> {
            viewToClick.setPressed(false);
            viewToClick.invalidate();
            //any other associated action
        }, 300);  // .3secs delay time
    }

    private void searchElementInTabLayoutToSwitchPage(View view, float x, float y) {
        Rect mainRect = new Rect();
        view.getHitRect(mainRect);
        int childRectWidth = view.getWidth() / 3;
        Rect rect1 = new Rect(view.getLeft(), view.getTop(), childRectWidth, view.getBottom());
        Rect rect2 = new Rect(rect1.left + childRectWidth, view.getTop(), rect1.left + childRectWidth*2, view.getBottom());
        Rect rect3 = new Rect(rect2.left + childRectWidth, view.getTop(), rect2.left + childRectWidth*2, view.getBottom());
        if(rect1.contains((int) x, (int) y)) {
            viewPager.setCurrentItem(0);
        }else if(rect2.contains((int) x, (int) y)){
            viewPager.setCurrentItem(1);
        }else if(rect3.contains((int) x, (int) y)){
            viewPager.setCurrentItem(2);
        }
    }

    private View searchForElementToClick(View view, float x, float y) {
        ArrayList<View> allViews = getAllClickableView((ViewGroup) view);
        for(int i = 0; i < allViews.size();  i++)
        {
            View child = allViews.get(i);
            Rect bounds = new Rect();
            child.getHitRect(bounds);
            if( bounds.contains((int) x,(int) y - yCalibrate)){
                return child;
            }
        }
        return null;
    }

    private ArrayList<View> getAllClickableView(ViewGroup v) {
        ArrayList<View> clickableViews = new ArrayList<>();
            for (int i = 0; i < v.getChildCount(); i++) {
                View child = v.getChildAt(i);
                if (child instanceof ViewGroup) {
                    clickableViews.addAll(getAllClickableView((ViewGroup) child));
                } else if (child instanceof TextView || child instanceof ImageView) {
                    clickableViews.add(child);
                }
            }
        return clickableViews;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xAccel = sensorEvent.values[0] * speedCalibrationX(sensorEvent);
            yAccel = -(sensorEvent.values[1] - standingCalibration) * speedCalibrationY(sensorEvent);
            calibrateOrUpdateBall(sensorEvent);
        }
    }


    private void calibrateOrUpdateBall(SensorEvent sensorEvent){
        if(!isCalibrated) {
            calibrationCount +=1;
            standingCalibration = (calibrationCount * standingCalibration + sensorEvent.values[1]) / (calibrationCount+1);
            isCalibrated = calibrationCount > 100;
        }
        if(isCalibrated) {
            updateBall();
        }
    }

    private float speedCalibrationX(SensorEvent sensorEvent) {
        if (sensorEvent.values[0] - xAccel > -2 && sensorEvent.values[0] - xAccel < 2) {
            return 0f; // si il y a une petite orientation => ne rien faire
        } else if (sensorEvent.values[0] - xAccel > -6 && sensorEvent.values[0] - xAccel < 6) {
            return 2f;
        } else {
            return 6f; // si il y a une grande orientation => multiplier l'effet par 6
        }
    }

    private float speedCalibrationY(SensorEvent sensorEvent) {
        if (sensorEvent.values[1] - yAccel > -1 && sensorEvent.values[1] - yAccel < 1) {
            return 0f; // si il y a une petite orientation => ne rien faire
        } else if (sensorEvent.values[1] - xAccel > -6 && sensorEvent.values[1] - xAccel < 6) {
            return 2f;
        } else {
            return 6f; // si il y a une grande orientation => multiplier l'effet par 6 //
        }
    }

    private void updateBall() {

        xPos -= xAccel;
        yPos -= yAccel;

        if (xPos > xMax) {
            xPos = xMax;
        } else if (xPos < 0) {
            xPos = 0;
        }

        if (yPos > yMax) {
            yPos = yMax;
        } else if (yPos < 0) {
            yPos = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private int calculateActionBarHeight(){
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            return TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        return 0;
    }

    private class BallView extends View {
        private Bitmap ball;

        public BallView(Context context) {
            super(context);
            Bitmap ballSrc = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
            final int dstWidth = 100;
            final int dstHeight = 100;
            ball = Bitmap.createScaledBitmap(ballSrc, dstWidth, dstHeight, true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(ball, xPos, yPos, null);
            invalidate();
        }
    }
}
