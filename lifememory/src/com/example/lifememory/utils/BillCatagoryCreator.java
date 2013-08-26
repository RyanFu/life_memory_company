package com.example.lifememory.utils;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;

public class BillCatagoryCreator {
	BillCatagoryItem item = null;
	
	public BillCatagoryCreator() {};
	
	public void initFirstLevel(SQLiteDatabase db) {
		List<BillCatagoryItem> items = new ArrayList<BillCatagoryItem>();
		item = new BillCatagoryItem("����", R.drawable.changyong, 0);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.canyin, 0);
		items.add(item);
		item = new BillCatagoryItem("��ͨ", R.drawable.jiaotong, 0);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.gouw, 0);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.yule, 0);
		items.add(item);
		item = new BillCatagoryItem("ҽ��", R.drawable.yijiao, 0);
		items.add(item);
		item = new BillCatagoryItem("�Ӽ�", R.drawable.jujia, 0);
		items.add(item);
		item = new BillCatagoryItem("Ͷ��", R.drawable.touzi, 0);
		items.add(item);
		item = new BillCatagoryItem("����", R.drawable.renqing, 0);
		items.add(item);

		for(BillCatagoryItem bcItem : items) {
			db.execSQL("insert into bill_catagory (name, imageid) values (?, ?)", new String[]{bcItem.getName(), String.valueOf(bcItem.getImageId())});
		}
		
		
		
		
		
	}
	
	public void initSecondaryLevel() {
		
	}
}
