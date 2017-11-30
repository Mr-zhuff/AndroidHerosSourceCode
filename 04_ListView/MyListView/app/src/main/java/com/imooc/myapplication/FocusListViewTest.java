package com.imooc.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FocusListViewTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focus);

        ListView listView = (ListView) findViewById(R.id.focus_listView);
        List<String> data = new ArrayList<String>();
        data.add(" I am item 1");
        data.add(" I am item 2");
        data.add(" I am item 3");
        data.add(" I am item 4");
        data.add(" I am item 5");
        final FocusListViewAdapter adapter = new FocusListViewAdapter(this, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                adapter.setCurrentItem(position);
                /**
                 * getView()只在初始化的时候调用，后面再次点击Item，不会触发getView再次调用，
                 *      使用notifyDataSetChanged()方法帮助实现刷新布局的功能
                 */
                adapter.notifyDataSetChanged();
            }
        });
    }
}
