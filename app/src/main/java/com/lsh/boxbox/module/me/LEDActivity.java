package com.lsh.boxbox.module.me;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseCommonActivity;

import butterknife.BindView;

/**
 * 我的--LED
 */
public class LEDActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar_led)
    Toolbar toolbarLed;
    @BindView(R.id.content_led)
    TextInputEditText contentLed;
    @BindView(R.id.fontcolor_btn_led)
    AppCompatButton fontcolorBtnLed;
    @BindView(R.id.bgcolor_btn_led)
    AppCompatButton bgcolorBtnLed;
    @BindView(R.id.recommendcolor_btn_led)
    AppCompatButton recommendcolorBtnLed;
    @BindView(R.id.preview_led)
    AppCompatTextView previewLed;
    @BindView(R.id.reverseColor_led)
    ImageView reverseColorLed;
    @BindView(R.id.single_radiobtn_led)
    AppCompatRadioButton singleRadiobtnLed;
    @BindView(R.id.single_toss_radiobtn_led)
    AppCompatRadioButton singleTossRadiobtnLed;
    @BindView(R.id.rollspeed_seekbar_led)
    AppCompatSeekBar rollspeedSeekbarLed;
    @BindView(R.id.adaptive_radiobtn_led)
    AppCompatRadioButton adaptiveRadiobtnLed;
    @BindView(R.id.tv_lines_led)
    TextView tvLinesLed;
    @BindView(R.id.lines_seekbar_led)
    AppCompatSeekBar linesSeekbarLed;
    @BindView(R.id.magic_radiobtn_led)
    AppCompatRadioButton magicRadiobtnLed;
    @BindView(R.id.spinner_magicstyle_led)
    AppCompatSpinner spinnerMagicstyleLed;
    @BindView(R.id.showstyle_radiogroup_led)
    RadioGroup showstyleRadiogroupLed;
    @BindView(R.id.start_btn_led)
    AppCompatButton startBtnLed;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_led);
    }

    @Override
    public void initView() {
        initToolbar();
    }

    private void initToolbar() {
        toolbarLed.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbarLed.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
