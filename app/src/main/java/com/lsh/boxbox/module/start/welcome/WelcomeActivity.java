package com.lsh.boxbox.module.start.welcome;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.lsh.boxbox.R;
import com.lsh.boxbox.config.Const;
import com.lsh.boxbox.database.CategoryDao;
import com.lsh.boxbox.database.FunctionDao;
import com.lsh.boxbox.model.CategoryManager;
import com.lsh.boxbox.model.Function;
import com.lsh.boxbox.model.FunctionBean;
import com.lsh.boxbox.utils.AppLogMessageMgr;
import com.lsh.boxbox.utils.SPUtils;
import com.lsh.boxbox.utils.StateBarTranslucentUtils;
import com.lsh.boxbox.utils.StreamUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 * 欢迎页
 */
public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        SPUtils.put(context, Const.FIRST_OPEN, true);
        context.startActivity(new Intent(context, WelcomeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //设置状态栏透明
        StateBarTranslucentUtils.setStateBarTranslucent(this);

        findViewById(R.id.bRetry).setOnClickListener(this);

        if (savedInstanceState == null) {
            replaceTutorialFragment();
        }

        saveCategoryToDB();
        saveFunctionToDB();

        saveFunctionBigToSP();

    }

    /**
     * 当第一次打开App时，保存3个大类别 (历史上的今天、老黄历、笑话大全) 到SharedPreferences
     */
    private void saveFunctionBigToSP() {
        SPUtils.put(this, Const.STAR_IS_OPEN, true);
        SPUtils.put(this, Const.STUFF_IS_OPEN, true);
        SPUtils.put(this, Const.JOKE_IS_OPEN, true);
    }

    /**
     * 当第一次打开App时，将功能类别存储到本地数据库
     */
    private void saveFunctionToDB() {
        Function function = null;

        try {
            function = (new Gson()).fromJson(StreamUtils.get(getApplicationContext(), R.raw.function), Function.class);
        } catch (Exception e) {
            AppLogMessageMgr.e("读取raw中的function.json文件异常：" + e.getMessage());
        }
        if (function != null && function.getFunction() != null && function.getFunction().size() != 0) {
            List<FunctionBean> functionBeanList = function.getFunction();
            FunctionDao functionDao = new FunctionDao(getApplicationContext());
            functionDao.insertFunctionBeanList(functionBeanList);
        }
    }

    /**
     * 第一次打开App时，将news的所有类别保存到本地数据库
     */
    private void saveCategoryToDB() {
        CategoryDao categoryDao = new CategoryDao(getApplicationContext());
        categoryDao.deleteAllCategory();
        categoryDao.insertCategoryList(new CategoryManager(this).getAllCategory());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRetry:
                replaceTutorialFragment();
                break;
        }
    }

    private void replaceTutorialFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_welcome, new CustomTutorialSupportFragment()).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
