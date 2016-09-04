package com.example.user.stuqexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.user.stuqexample.api.KanzhihuService;
import com.example.user.stuqexample.model.TopUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gson.CustomConverterFactory;
import retrofit2.Retrofit;

public class ListActivity extends AppCompatActivity {

    private String baseURL = "http://api.kanzhihu.com/";

    private ImageView blurImageView;
    private ListView listView;

    private List<TopUser> topUsrs = new ArrayList<>();
    private ImageAndTextAdapter imgTxtAdapter;
    private int pageId = 1;
    private int itemSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initView();

        new Thread(() -> {

        });
    }


    boolean canStillLoadAfter = true;
    boolean mLastItemVisible = false;

    private void initView() {
        Button button = (Button) findViewById(R.id.btn_getAgree);
        button.setOnClickListener(v -> {
            updateDataByNetwork();
        });

        listView = (ListView) findViewById(R.id.id_list);
        LinearLayout footerView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.footer_view, null);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //如果列表滑动中止，并且最后一个元素可见
                if (scrollState == SCROLL_STATE_IDLE && mLastItemVisible) {
                    if (canStillLoadAfter) {
                        canStillLoadAfter = false;
                        //调用接口
                        pageId ++;
                        updateDataByNetwork();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
                //解释：
                //firstVisibleItem + visibleItemCount = 最后一个可见元素的序数
                //totalItemCount -1 = 因为是从0开始的，所以是最后一个元素的序号
                //如果可见元素序数 大于等于 最后一个元素序号，那么最后一个元素就是可见的
            }
        });

        listView.addFooterView(footerView);
    }

    private void updateDataByNetwork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TopUser> tmpUsr = startRequest(pageId, itemSize);
                topUsrs.addAll(tmpUsr);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imgTxtAdapter = new ImageAndTextAdapter(ListActivity.this, topUsrs);
                        listView.setAdapter(imgTxtAdapter);

                        imgTxtAdapter.notifyDataSetChanged();
                    }
                });

            }
        }).start();
    }

    private List<TopUser> startRequest(int page, int size) {
        //同步
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(CustomConverterFactory.create())//GsonConvert
                .build();
        KanzhihuService zhihuService = retrofit.create(KanzhihuService.class);
        try {
            List<TopUser> topUsrs = zhihuService.getAgreeUsers(page, size)
                    .execute()
                    .body();
            return topUsrs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
