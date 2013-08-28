package com.example.lifememory.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillAccountExpandableListViewItem;
import com.example.lifememory.activity.model.BillAccountItem;
import com.example.lifememory.adapter.BillAccountExpandableListAdapter;
import com.example.lifememory.db.service.BillAccountService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BillAccountSettingActivity extends Activity {
	private ExpandableListView listView = null;
	private BillAccountService dbService;
	private List<BillAccountItem> accountItems;
	private List<BillAccountItem> accountItems1 = new ArrayList<BillAccountItem>();  //����ֽ�����
	private List<BillAccountItem> accountItems2 = new ArrayList<BillAccountItem>();	 //������ÿ�����
	private List<BillAccountItem> accountItems3 = new ArrayList<BillAccountItem>();  //��Ŵ�������
	private List<BillAccountItem> accountItems4 = new ArrayList<BillAccountItem>();  //�������֧������
	private BillAccountExpandableListViewItem expandableItem;
	private List<BillAccountExpandableListViewItem> expandableItems = new ArrayList<BillAccountExpandableListViewItem>();
	private BillAccountExpandableListAdapter adapter = null;
	private int accountGroupSelectedIndex = 0;			//���ڼ�¼�˻�expandablelistview���������
	private int accountChildSelectedIndex = 0;  		//���ڼ�¼�˻�expandablelistview���ӵ�����
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0) {
				listAdapter();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_account_setting_layout);
		
		dbService = new BillAccountService(this);
		
		this.accountGroupSelectedIndex = this.getIntent().getIntExtra("accountGroupSelectedIndex", 0);
		this.accountChildSelectedIndex = this.getIntent().getIntExtra("accountChildSelectedIndex", 0);
		
		findViews();
		new InitDatasThread().start();
	}
	

	private void findViews() {
		listView = (ExpandableListView) this.findViewById(R.id.listView);
		listView.setOnChildClickListener(new OnChildClickListener() {
				 
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				String name = expandableItems.get(groupPosition).getAccountItems().get(childPosition).getName();
				Intent intent = new Intent();
				intent.putExtra("accountGroupSelectedIndex", groupPosition);
				intent.putExtra("accountChildSelectedIndex", childPosition);
				intent.putExtra("accountStr", name);
				BillAccountSettingActivity.this.setResult(98, intent);
				BillAccountSettingActivity.this.finish();
				overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
				return false;
			}
		});
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			BillAccountSettingActivity.this.finish();
			overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
			break;
		case R.id.add:
			
			break;
		}
	}
	
	private class InitDatasThread extends Thread {
		@Override
		public void run() {
			accountItems = dbService.getAllAccounts();
			for(BillAccountItem item : accountItems) {
				switch (item.getCatagoryname()) {
				case 1:
					accountItems1.add(item);
					break;
				case 2:
					accountItems2.add(item);
					break;
				case 3:
					accountItems3.add(item);
					break;
				case 4:
					accountItems4.add(item);
					break;
				}
			}
			
			expandableItem = new BillAccountExpandableListViewItem();
			expandableItem.setTitle("�ֽ�");
			expandableItem.setAccountItems(accountItems1);
			expandableItems.add(expandableItem);
			
			expandableItem = new BillAccountExpandableListViewItem();
			expandableItem.setTitle("���ÿ�");
			expandableItem.setAccountItems(accountItems2);
			expandableItems.add(expandableItem);
			
			expandableItem = new BillAccountExpandableListViewItem();
			expandableItem.setTitle("����");
			expandableItem.setAccountItems(accountItems3);
			expandableItems.add(expandableItem);
			
			expandableItem = new BillAccountExpandableListViewItem();
			expandableItem.setTitle("����֧��");
			expandableItem.setAccountItems(accountItems4);
			expandableItems.add(expandableItem);
			
			handler.sendEmptyMessage(0);
		}
	}
	
	
	private void listAdapter() {
		adapter = new BillAccountExpandableListAdapter(BillAccountSettingActivity.this, expandableItems);
		listView.setAdapter(adapter);
		adapter.setSelected(accountGroupSelectedIndex, accountChildSelectedIndex);
		Log.i("a", accountGroupSelectedIndex + "-------" + accountGroupSelectedIndex);
		int groupCount = adapter.getGroupCount();
		for (int i = 0; i < groupCount; i++) {
			listView.expandGroup(i);
		}
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbService.closeDB();
	}
}





















