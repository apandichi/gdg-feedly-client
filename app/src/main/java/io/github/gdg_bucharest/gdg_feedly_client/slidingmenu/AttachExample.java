package io.github.gdg_bucharest.gdg_feedly_client.slidingmenu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import io.github.gdg_bucharest.gdg_feedly_client.R;

/**
 * Created by pndl on 3/14/15.
 */
public class AttachExample extends FragmentActivity {

    private SlidingMenu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Title");

        // set the Above View
        setContentView(R.layout.content_frame);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new SampleListFragment())
                .commit();

        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_frame);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_frame, new SampleListFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            menu.showContent();
        } else {
            super.onBackPressed();
        }
    }
}
