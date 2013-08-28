package com.example.lifememory.db.service;

import java.util.ArrayList;
import java.util.List;

import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.db.PregnancyDiaryOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BillCatagoryService {
	
	private SQLiteDatabase db;
	
	public BillCatagoryService(Context context) {
		PregnancyDiaryOpenHelper helper = new PregnancyDiaryOpenHelper(context);
		db = helper.getReadableDatabase();
	}
	
	//��������һ���б�
	public List<BillCatagoryItem> findFirstLevel() {
		List<BillCatagoryItem> items = new ArrayList<BillCatagoryItem>();
		BillCatagoryItem item = null;
		Cursor cursor = db.rawQuery("select * from bill_catagory where parentid = 0", null);
		while(cursor.moveToNext()) {
			item = new BillCatagoryItem();
			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
			item.setImageId(cursor.getInt(cursor.getColumnIndex("imageid")));
			items.add(item);
		}
		return items;
	}
	
	//���ݸ�id���������
	public List<BillCatagoryItem> findSecondaryLevelByParentId(int parentId) {
		List<BillCatagoryItem> items = new ArrayList<BillCatagoryItem>();
		BillCatagoryItem item = null;
		Cursor cursor = db.rawQuery("select * from bill_catagory where parentid = ?", new String[]{String.valueOf(parentId)});
		while(cursor.moveToNext()) {
			item = new BillCatagoryItem();
			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
			item.setImageId(cursor.getInt(cursor.getColumnIndex("imageid")));
			item.setParentId(parentId);
			items.add(item);
		}
		return items;
	}
	//��������Ϣ
	public void addCatagory(BillCatagoryItem item) {
		db.execSQL("insert into bill_catagory (name, imageid, parentid) values (?, ?, ?)", new String[]{item.getName(), String.valueOf(item.getImageId()), String.valueOf(item.getParentId())});
	}
	
	public void closeDB() {
		db.close();
	}
	
	
	//���ݶ������id���������Ϣ     һ�����-������� ��  ����-��
	public String getCatagoryStr(int childid) {
		StringBuffer sb = new StringBuffer();
		
		Cursor cursor = db.rawQuery("select * from bill_catagory where idx = ?", new String[]{String.valueOf(childid)});
		cursor.moveToFirst();
		String childName = cursor.getString(cursor.getColumnIndex("name"));
		int parentid = cursor.getInt(cursor.getColumnIndex("parentid"));
		cursor = db.rawQuery("select name from bill_catagory where idx = ?", new String[]{String.valueOf(parentid)});
		cursor.moveToFirst();
		String parentName = cursor.getString(0);
		sb.append(parentName).append("-").append(childName);
		return sb.toString();
	}
	
}









