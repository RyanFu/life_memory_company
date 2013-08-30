package com.example.lifememory.db.service;

import com.example.lifememory.db.PregnancyDiaryOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BillInfoService {
	private SQLiteDatabase db;
	public BillInfoService(Context context) {
		PregnancyDiaryOpenHelper helper = new PregnancyDiaryOpenHelper(context);
		db = helper.getReadableDatabase();
	}
	
	//���
	//ɾ��
	//�����˻�id���������˸��˻�id���˵���
	public boolean isRelatedWithAccount(int accountId) {
		Cursor cursor = db.rawQuery("select count(*) from bill_info where accountid = ?", new String[]{String.valueOf(accountId)});
		cursor.moveToFirst();
		Long num = cursor.getLong(0);
		if(num > 0) {
			return true;
		}else {
			return false;
		}
	}
	
}








