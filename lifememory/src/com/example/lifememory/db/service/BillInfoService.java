package com.example.lifememory.db.service;

import java.util.ArrayList;
import java.util.List;

import com.example.lifememory.activity.model.Bill;
import com.example.lifememory.db.PregnancyDiaryOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BillInfoService {
	private SQLiteDatabase db;
	public BillInfoService(Context context) {
		PregnancyDiaryOpenHelper helper = new PregnancyDiaryOpenHelper(context);
		db = helper.getReadableDatabase();
	}
	
	/**bill���ݿ�����
	 * jine text, incatagory text, outcatagory text , 
	 * account text, date text, dateymd text, member text, 
	 * beizhu text, isCanBaoXiao text, isBaoxiaoed text, 
	 * transferIn text, transferOut text, billType integer
	 */
	
	
	
	//���֧�����͵��˵���Ϣ
	public void addOutBill(Bill bill) {
		db.execSQL("insert into bill_info (jine, outcatagory, account, date, dateymd, member, beizhu, isCanBaoXiao, billType) values (?,?,?,?,?,?,?,?,?)", 
				new String[]{bill.getJine(), bill.getOutCatagory(), bill.getAccount(), bill.getDate(),bill.getDateYMD(), bill.getMember(),
				bill.getBeizhu(), String.valueOf(bill.isCanBaoXiao()), String.valueOf(bill.getBillType())});
	}
	//����������͵��˵���Ϣ
	public void addInBill(Bill bill) {
		db.execSQL("insert into bill_info (jine, incatagory, account, date,dateymd, member, beizhu, billType) values (?, ?,?, ?, ?, ?, ?, ?)", 
				new String[]{bill.getJine(), bill.getInCatagory(), bill.getAccount(), bill.getDate(),bill.getDateYMD(), bill.getMember(),
				bill.getBeizhu(), String.valueOf(bill.getBillType())});
	}
	//���ת�����͵��˵���Ϣ
	public void addTransferBill(Bill bill) {
		db.execSQL("insert into bill_info (jine, date, dateymd, transferIn, transferOut, billType) values (?, ?, ?, ?, ?, ?)", new String[]{
				bill.getJine(), bill.getDate(),bill.getDateYMD(), bill.getTransferIn(), bill.getTransferOut(), String.valueOf(bill.getBillType())
		});
	}
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
	
	
	public void closeDB() {
		db.close();
	}
	
	//�������ڲ����˵���Ϣ  yyyy-MM-d
	public List<Bill> findBillByYMD(String ymd) {
		List<Bill> bills = new ArrayList<Bill>();
		Bill bill = null;
		String dateParam = "%" + ymd + "%";
//		Log.i("a", "dateParam = " + dateParam);
		Cursor cursor = db.rawQuery("select * from bill_info where billType != 3 and  date like ?", new String[]{dateParam});
		while(cursor.moveToNext()) {
			bill = new Bill();
			bill.setBillType(cursor.getInt(cursor.getColumnIndex("billType")));
			bill.setJine(cursor.getString(cursor.getColumnIndex("jine")));
			bills.add(bill);
		}
		return bills;
	}
	
	//���ҵ��µ����������˵���Ϣ billType = 2
	public List<Bill> findIncomeByYM(String ym) {
		List<Bill> bills = new ArrayList<Bill>();
		Bill bill = null;
		String dateParam = "%" + ym + "%";
		Cursor cursor = db.rawQuery("select * from bill_info where billType = 2 and  date like ?", new String[]{dateParam});
		while(cursor.moveToNext()) {
			bill = new Bill();
			bill.setJine(cursor.getString(cursor.getColumnIndex("jine")));
			bills.add(bill);
		}
		return bills;
	}
	
	//���ҵ��µ�����֧���˵���Ϣ billType = 1
	public List<Bill> findSpendByYM(String ym) {
		List<Bill> bills = new ArrayList<Bill>();
		Bill bill = null;
		String dateParam = "%" + ym + "%";
//		Log.i("a", "spend param = " + dateParam);
		Cursor cursor = db.rawQuery("select * from bill_info where billType = 1 and  date like ?", new String[]{dateParam});
		while(cursor.moveToNext()) {
			bill = new Bill();
			bill.setJine(cursor.getString(cursor.getColumnIndex("jine")));
			bills.add(bill);
		}
		return bills;
	}
	
	//�����·ݲ��ҵ�ǰ���Ƿ����˵���Ϣ
	public boolean isCurrentHaveBills(String ym) {
		String dateParam = "%" + ym + "%";
		Cursor cursor = db.rawQuery("select count(*) from bill_info where dateymd like ?", new String[]{dateParam});
		cursor.moveToFirst();
		Long count = cursor.getLong(0);
		return count > 0 ? true : false;
	}
	
	//�����·ݲ��ҵ�ǰ�����мǹ��ʵ�����  yyyy-MM-dd
	public List<String> findAllYMDInMonth(String ym) {
		List<String> ymds = new ArrayList<String>();
		String dateParam = "%" + ym + "%";
		Cursor cursor = db.rawQuery("select dateymd from bill_info where dateymd like ? group by dateymd", new String[]{dateParam});
		while(cursor.moveToNext()) {
			ymds.add(cursor.getString(0));
		}
		return ymds;
	}
}








