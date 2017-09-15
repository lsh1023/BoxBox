package com.lsh.boxbox.module.news.news_category;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseCommonActivity;
import com.lsh.boxbox.config.Const;
import com.lsh.boxbox.database.CategoryDao;
import com.lsh.boxbox.model.CategoryEntity;
import com.lsh.boxbox.model.CategoryManager;
import com.lsh.boxbox.module.news.news_category.draghelper.ItemDragHelperCallback;
import com.lsh.boxbox.utils.AppToastMgr;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻资讯--添加
 */
public class CategoryActivity extends BaseCommonActivity {

    private RecyclerView mRecy;
    private Toolbar mToolbar;
    List<CategoryEntity> items;
    List<CategoryEntity> otherItems;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_category);
    }

    @Override
    public void initView() {
        mRecy = ((RecyclerView) findViewById(R.id.recy));
        initToolbar();
        initRecy();
    }

    private void initToolbar() {
        mToolbar = ((Toolbar) findViewById(R.id.toolbar));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (CategoryEntity categ :
                        items) {
                    String name = categ.getName();
                    Logger.e(name);
                }
                finish();
            }
        });
        mToolbar.inflateMenu(R.menu.news_category);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                saveNewDataToDB();
                AppToastMgr.ToastShortCenter(CategoryActivity.this, "设置成功");
                setResult(Const.NEWSFRAGMENT_CATEGORYACTIVITY_RESULTCODE, null);
                finish();
                return false;
            }


        });
    }

    /**
     * 更新数据库的存储
     */
    private void saveNewDataToDB() {
        CategoryDao categoryDao = new CategoryDao(getApplicationContext());
        categoryDao.deleteAllCategory();
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setOrder(i);
        }
        categoryDao.insertCategoryList(items);
    }

    private void initRecy() {

        items = getCategoryFromDB();
        otherItems = new ArrayList<>();

        if (items == null) {
            items = new ArrayList<>();
        }

        List<CategoryEntity> allCategory = new CategoryManager(this).getAllCategory();

        for (CategoryEntity entity : allCategory) {
            if (!items.contains(entity)) {
                otherItems.add(entity);
            }
        }

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecy.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecy);

        final CategoryAdapter adapter = new CategoryAdapter(this, helper, items, otherItems);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == CategoryAdapter.TYPE_MY || viewType == CategoryAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        mRecy.setAdapter(adapter);

        adapter.setOnMyChannelItemClickListener(new CategoryAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(CategoryActivity.this, "请点击编辑或者长按进行操作！", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 获取本地数据库中 序列化的新闻类别
     *
     * @return
     */
    private List<CategoryEntity> getCategoryFromDB() {
        CategoryDao categoryDao = new CategoryDao(getApplicationContext());
        return categoryDao.queryCategoryList();
    }

    @Override
    public void initPresenter() {

    }
}
