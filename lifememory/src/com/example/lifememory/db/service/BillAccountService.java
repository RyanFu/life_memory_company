package com.example.lifememory.db.service;

import java.util.ArrayList;
import java.util.List;

import com.example.lifememory.activity.model.BillAccountItem;
import com.example.lifememory.db.PregnancyDiaryOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BillAccountService {
	private SQLiteDatabase db = null;
	public BillAccountService(Context context) {
		PregnancyDiaryOpenHelper helper = new PregnancyDiaryOpenHelper(context);
		db = helper.getReadableDatabase();
	}
	
	//���������˻���Ϣ
	public List<BillAccountItem> getAllAccounts() {
		List<BillAccountItem> items = new ArrayList<BillAccountItem>();
		BillAccountItem item = null;
		Cursor cursor = db.rawQuery("select * from bill_account", null);
		while(cursor.moveToNext()) {
			item = new BillAccountItem();
			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			item.setCatagoryname(cursor.getInt(cursor.getColumnIndex("catagoryname")));
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
			item.setImageId(cursor.getInt(cursor.getColumnIndex("imageid")));
			
			items.add(item);
		}
		return items;
	}
	

	
	public double getJingZiChan() {
		double jingzichan = 0;
		Cursor cursor = db.rawQuery("select dangqianyue from bill_account", null);
		while(cursor.moveToNext()) {
			jingzichan += cursor.getDouble(0);
		}
		return jingzichan;
	}
	
	
	//����idx�����˻���Ϣ��������ʾ�����˻����б�ֻ��Ҫidx��name����
	public BillAccountItem findItemById(int idx) {
		BillAccountItem item = new BillAccountItem();
		Cursor cursor = db.rawQuery("select * from bill_account where idx = ? ", new String[]{String.valueOf(idx)});
		cursor.moveToFirst();
		item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
		item.setName(cursor.getString(cursor.getColumnIndex("name")));
		item.setImageId(cursor.getInt(cursor.getColumnIndex("imageid")));
		return item;
	}
	
	//����idx�����˻�����ϸ��Ϣ,�����޸�ǰ��ʾ����
	public BillAccountItem findItemDetailById(int idx) {
		BillAccountItem item = new BillAccountItem();
		Cursor cursor = db.rawQuery("select * from bill_account where idx = ? ", new String[]{String.valueOf(idx)});
		cursor.moveToFirst();
		item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
		item.setCatagoryname(cursor.getInt(cursor.getColumnIndex("catagoryname")));
		item.setName(cursor.getString(cursor.getColumnIndex("name")));
		item.setBizhong(cursor.getString(cursor.getColumnIndex("bizhong")));
		item.setDangqianyue(cursor.getDouble(cursor.getColumnIndex("dangqianyue")));
		String booleanTemp = cursor.getString(cursor.getColumnIndex("isnotice"));
		if("false".equals(booleanTemp)) {
			item.setNotice(false);
		}else {
			item.setNotice(true);
		}
		item.setNoticeValue(cursor.getDouble(cursor.getColumnIndex("noticevalue")));
		item.setBeizhu(cursor.getString(cursor.getColumnIndex("beizhu")));
		return item;
	}

	
	
	public boolean addAccount(BillAccountItem item) {
		
		Cursor cursor = db.rawQuery("select count(*) from bill_account where name = ? and catagoryname = ?", new String[]{item.getName(), String.valueOf(item.getCatagoryname())});
		cursor.moveToFirst();
		Long count = cursor.getLong(0);
		if(count > 0) {
			return false;
		}
		db.execSQL("insert into bill_account (catagoryname, name, bizhong, dangqianyue, isnotice, noticevalue, imageid, beizhu) values (?, ?, ?, ?, ?, ?, ?, ?)", new String[]{
				String.valueOf(item.getCatagoryname()), item.getName(), item.getBizhong(), String.valueOf(item.getDangqianyue()),
				String.valueOf(item.isNotice()), String.valueOf(item.getNoticeValue()), String.valueOf(item.getImageId()), item.getBeizhu()
				
		});
		return true;
	}
	
	//����idɾ���˻���Ϣ
	public void deleteItemById(int idx) {
		db.execSQL("delete from bill_account where idx = ?", new String[]{String.valueOf(idx)});
	}
	
	public void updateItem(BillAccountItem item) {
		db.execSQL("update bill_account set catagoryname = ?, name = ?, bizhong = ?, dangqianyue = ?, isnotice = ?, noticevalue = ?, imageid =?, beizhu = ? where idx = ?", 
				new String[]{
				String.valueOf(item.getCatagoryname()), item.getName(), item.getBizhong(), String.valueOf(item.getDangqianyue()),
				String.valueOf(item.isNotice()), String.valueOf(item.getNoticeValue()), String.valueOf(item.getImageId()), item.getBeizhu(), String.valueOf(item.getIdx())
		});
	}
	
	//֧����������ӽ����Ĭ��account��Ϣ
	public BillAccountItem findItemsForAddViews() {
		BillAccountItem item = null;
		Cursor cursor = db.rawQuery("select * from bill_account where catagoryname = 1", null);
		if(cursor.moveToFirst()) {
			item = new BillAccountItem();
			item.setCatagoryname(1);
			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
		}else {
			cursor = db.rawQuery("select * from bill_account where catagoryname = 2", null);
			if(cursor.moveToFirst()) {
				item = new BillAccountItem();
				item.setCatagoryname(1);
				item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
				item.setName(cursor.getString(cursor.getColumnIndex("name")));
				return item;
			}else {
				cursor = db.rawQuery("select * from bill_account where catagoryname = 3", null);
				if(cursor.moveToFirst()) {
					item = new BillAccountItem();
					item.setCatagoryname(1);
					item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
					item.setName(cursor.getString(cursor.getColumnIndex("name")));
					return item;
				}else {
					cursor = db.rawQuery("select * from bill_account where catagoryname = 4", null);
					if(cursor.moveToFirst()) {
						item = new BillAccountItem();
						item.setCatagoryname(1);
						item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
						item.setName(cursor.getString(cursor.getColumnIndex("name")));
						return item;
					}
				}
			}
		}
		return item;
	}
	
	//ת����ӽ����Ĭ��ת��account��Ϣ  ˳���Ǵ�� ���ÿ��� ����֧���� �ֽ�
	public BillAccountItem findItemsForAddTransferInViews() {
		BillAccountItem item = null;
		Cursor cursor = db.rawQuery("select * from bill_account where catagoryname = 3", null);
		if(cursor.moveToFirst()) {
			item = new BillAccountItem();
			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			item.setCatagoryname(3);
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
		}else {
			cursor = db.rawQuery("select * from bill_account where catagoryname = 2", null);
			if(cursor.moveToFirst()) {
				item = new BillAccountItem();
				item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
				item.setCatagoryname(3);
				item.setName(cursor.getString(cursor.getColumnIndex("name")));
			}else {
			    cursor = db.rawQuery("select * from bill_account where catagoryname = 4", null);
				if(cursor.moveToFirst()) {
					item = new BillAccountItem();
					item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
					item.setCatagoryname(3);
					item.setName(cursor.getString(cursor.getColumnIndex("name")));
				}else {
					cursor = db.rawQuery("select * from bill_account where catagoryname = 1", null);
					if(cursor.moveToFirst()) {
						item = new BillAccountItem();
						item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
						item.setCatagoryname(3);
						item.setName(cursor.getString(cursor.getColumnIndex("name")));
					}
				}
			}
		}
		return item;
	}
//	//ת����ӽ����Ĭ��ת��account��Ϣ    ˳�����ֽ����ÿ��� ��� ����֧��
//	public BillAccountItem findItemsForAddTransferOutViews() {
//		BillAccountItem item = null;
//		Cursor cursor = db.rawQuery("select * from bill_account where catagoryname = 1", null);
//		if(cursor.moveToFirst()) {
//			item = new BillAccountItem();
//			item.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
//			item.setCatagoryname(1);
//			item.setName(cursor.getString(cursor.getColumnIndex("name")));
//		}
//		return item;
//	}
	
	public void closeDB() {
		db.close();
	}
	
	/*
	 * �����˻����Ͳ�����Ӧ���˻���Ϣ  // 1�ֽ� �� 2���ÿ��� 3��� 4����֧��
	 */
	//�ֽ����͵��˻�
	public List<BillAccountItem> findItemsByAccountCatagory(int catagoryType) {
		List<BillAccountItem> items = new ArrayList<BillAccountItem>();
		BillAccountItem item = null;
		Cursor cursor = db.rawQuery("select * from bill_account where catagoryname = ?", new String[]{String.valueOf(catagoryType)});
		while(cursor.moveToNext()) {
			item = new BillAccountItem();
			item.setName(cursor.getString(cursor.getColumnIndex("name")));
			item.setBizhong(cursor.getString(cursor.getColumnIndex("bizhong")));
			item.setDangqianyue(cursor.getDouble(cursor.getColumnIndex("dangqianyue")));
			items.add(item);
		}
		Log.i("a", "items size =" + items.toString());
		return items;
	}
	
}

























