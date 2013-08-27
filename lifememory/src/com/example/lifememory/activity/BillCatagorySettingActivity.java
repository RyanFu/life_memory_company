package com.example.lifememory.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.adapter.BillCatagoryGridViewAdapter;
import com.example.lifememory.adapter.BillCatatoryListAdapter;
import com.example.lifememory.db.service.BillCatagoryService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BillCatagorySettingActivity extends Activity {
	private ListView firstListView, secondaryListView;
	private BillCatagoryService dbService = null;
	private List<BillCatagoryItem> firstLevelItems = null;
	private List<BillCatagoryItem> secondaryLevelItems = null;
	private BillCatatoryListAdapter firstLevelAdapter, secondaryLevelAdapter;
	private BillCatagoryItem addItem;    //添加按钮
	private int parentId = 1;         //记录一级类别信息的id作为二级类别的parentid
	private int currentSelectedFirstLevel = 0;   //记录一级类别列表的选择标号
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				// 读取一级二级数据
				listViewAdapter();
				break;
			case 1:
				//根据parentid读取二级数据
				secondaryLevelAdapter.setDatas(secondaryLevelItems);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_catagoryselect_layout);

		dbService = new BillCatagoryService(this);

		findViews();
		initDatas();
		setListeners();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(dbService != null) {
			initDatas();
			new LoadDatasByParentIdThread(parentId).start();
		}
	}

	private void findViews() {
		firstListView = (ListView) this.findViewById(R.id.firstListView);
		secondaryListView = (ListView) this
				.findViewById(R.id.secondaryListView);
		addItem = new BillCatagoryItem();
		addItem.setName("新增");
		addItem.setImageId(R.drawable.bill_category_add);
	}

	private void initDatas() {
		new LoadDatasThread().start();
	}

	private void listViewAdapter() {

		firstLevelAdapter = new BillCatatoryListAdapter(
				BillCatagorySettingActivity.this,
				BillCatagorySettingActivity.this.firstLevelItems);
		firstLevelAdapter.setSelected(currentSelectedFirstLevel);
		secondaryLevelAdapter = new BillCatatoryListAdapter(
				BillCatagorySettingActivity.this,
				BillCatagorySettingActivity.this.secondaryLevelItems);
		firstListView.setAdapter(firstLevelAdapter);
		secondaryListView.setAdapter(secondaryLevelAdapter);

	}

	private void setListeners() {
		firstListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if(position != firstLevelItems.size() - 1) {
					parentId = firstLevelItems.get(position).getIdx();
					new LoadDatasByParentIdThread(parentId).start();
					firstLevelAdapter.setSelected(position);
					currentSelectedFirstLevel = position;
				}else {
					Intent intent = new Intent(BillCatagorySettingActivity.this, BillCatatoryAddActivity.class);
					intent.putExtra("title", "添加一级类别");
					intent.putExtra("parentId", 0);
					startActivity(intent);
				}
			}
		});
		
		secondaryListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position != secondaryLevelItems.size() - 1) {
					Toast.makeText(BillCatagorySettingActivity.this, secondaryLevelItems.get(position).getName(), 0).show();
				}else {
					Intent intent = new Intent(BillCatagorySettingActivity.this, BillCatatoryAddActivity.class);
					intent.putExtra("title", "添加二级类别");
					intent.putExtra("parentId", parentId);
					startActivity(intent);
				}
			}
		});
	}

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			BillCatagorySettingActivity.this.finish();
			overridePendingTransition(R.anim.activity_steady,
					R.anim.activity_down);
			break;
		}
	}

	// 读取数据库类别数据线程
	private class LoadDatasThread extends Thread {
		@Override
		public void run() {
			firstLevelItems = dbService.findFirstLevel();
			firstLevelItems.add(addItem);
			secondaryLevelItems = dbService.findSecondaryLevelByParentId(1);
			secondaryLevelItems.add(addItem);
			handler.sendEmptyMessage(0);
		}
	}
	
	private class LoadDatasByParentIdThread extends Thread {
		private int index = 1;
		public LoadDatasByParentIdThread(int _index) {
			this.index = _index;
		}
		@Override
		public void run() {
			secondaryLevelItems = dbService.findSecondaryLevelByParentId(index);
			secondaryLevelItems.add(addItem);
			handler.sendEmptyMessage(1);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbService.closeDB();
	}

}
