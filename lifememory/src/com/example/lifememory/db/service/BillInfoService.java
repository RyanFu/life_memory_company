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
		db.execSQL("insert into bill_info (jine, outcatagory, account, accountid, date, dateymd, member, beizhu, isCanBaoXiao, billType) values (?,?,?,?,?,?,?,?,?, ?)", 
				new String[]{bill.getJine(), bill.getOutCatagory(), bill.getAccount(), String.valueOf(bill.getAccountid()), bill.getDate(),bill.getDateYMD(), bill.getMember(),
				bill.getBeizhu(), String.valueOf(bill.isCanBaoXiao()), String.valueOf(bill.getBillType())});
	}
	//����������͵��˵���Ϣ
	public void addInBill(Bill bill) {
		db.execSQL("insert into bill_info (jine, incatagory, account, accountid, date,dateymd, member, beizhu, billType) values (?, ?,?, ?, ?, ?, ?, ?, ?)", 
				new String[]{bill.getJine(), bill.getInCatagory(), bill.getAccount(), String.valueOf(bill.getAccountid()), bill.getDate(),bill.getDateYMD(), bill.getMember(),
				bill.getBeizhu(), String.valueOf(bill.getBillType())});
	}
	//���ת�����͵��˵���Ϣ
	public void addTransferBill(Bill bill) {
		db.execSQL("insert into bill_info (jine, date, dateymd, transferIn, transferInAccountId, transferOut, transferOutAccountId, billType) values (?, ?, ?, ?, ?, ?, ?, ?)", new String[]{
				bill.getJine(), bill.getDate(),bill.getDateYMD(), bill.getTransferIn(), String.valueOf(bill.getTransferInAccountId()), bill.getTransferOut(), String.valueOf(bill.getTransferOutAccountId()), String.valueOf(bill.getBillType())
		});
	}

	
	
	//�޸�֧�����͵��˵���Ϣ
	public void updateOutBill(Bill bill) {
		db.execSQL("update bill_info set jine = ?, outcatagory = ?, account = ?, accountid = ?,  date = ?, dateymd = ?, member = ?, beizhu = ?, isCanBaoXiao = ?, billType = ? where idx = ?", 
				new String[]{bill.getJine(), bill.getOutCatagory(), bill.getAccount(), String.valueOf(bill.getAccountid()), bill.getDate(),bill.getDateYMD(), bill.getMember(),
				bill.getBeizhu(), String.valueOf(bill.isCanBaoXiao()), String.valueOf(bill.getBillType()), String.valueOf(bill.getIdx())});
	}
	//�޸��������͵��˵���Ϣ
	public void updateInBill(Bill bill) {
		db.execSQL("update  bill_info set jine = ?, incatagory = ?, account = ?, accountid = ?, date = ?,dateymd = ?, member = ?, beizhu = ?, billType = ? where idx = ?", 
				new String[]{bill.getJine(), bill.getInCatagory(), bill.getAccount(), String.valueOf(bill.getAccountid()), bill.getDate(),bill.getDateYMD(), bill.getMember(),
				bill.getBeizhu(), String.valueOf(bill.getBillType()), String.valueOf(bill.getIdx())});
	}
	//�޸�ת�����͵��˵���Ϣ
	public void updateTransferBill(Bill bill) {
		db.execSQL("update bill_info set jine = ?, date = ?, dateymd = ?, transferIn = ?, transferInAccountId = ?, transferOut = ?, transferOutAccountId = ?, billType = ? where idx = ?", new String[]{
				bill.getJine(), bill.getDate(),bill.getDateYMD(), bill.getTransferIn(), String.valueOf(bill.getTransferInAccountId()), bill.getTransferOut(), String.valueOf(bill.getTransferOutAccountId()), String.valueOf(bill.getBillType()), String.valueOf(bill.getIdx())
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
	
	//�������ڲ����˵���Ϣ  yyyy-MM-d   ���ڼ�����ҳ�ı��ռ��˵�ͳ��
	public List<Bill> findBillByYMD(String ymd) {
		List<Bill> bills = new ArrayList<Bill>();
		Bill bill = null;
		String dateParam = "%" + ymd + "%";
//		Log.i("a", "dateParam = " + dateParam);
		Cursor cursor = db.rawQuery("select * from bill_info where billType != 3 and  date like ?", new String[]{dateParam});
		while(cursor.moveToNext()) {
			bill = new Bill();
			int billType = cursor.getInt(cursor.getColumnIndex("billType"));
			bill.setBillType(billType);
			bill.setJine(cursor.getString(cursor.getColumnIndex("jine")));
			bills.add(bill);
		}
		return bills;
	}
	
	
	//�������ڲ����˵���Ϣ  yyyy-MM-d   ���ڼ��˱�����ˮ��Ϣ
	public List<Bill> findBillByYMDInDetails(String ymd) {
		List<Bill> bills = new ArrayList<Bill>();
		Bill bill = null;
		String dateParam = "%" + ymd + "%";
//		Log.i("a", "dateParam = " + dateParam);
		Cursor cursor = db.rawQuery("select * from bill_info where date like ? order by date desc", new String[]{dateParam});
		while(cursor.moveToNext()) {
			bill = new Bill();
			bill.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			int billType = cursor.getInt(cursor.getColumnIndex("billType"));
			bill.setBillType(billType);
			if(billType == 1) {
				//֧��
				bill.setOutCatagory(cursor.getString(cursor.getColumnIndex("outcatagory")));
			}else if(billType == 2) {
				//����
				bill.setInCatagory(cursor.getString(cursor.getColumnIndex("incatagory")));
			}
			bill.setAccount(cursor.getString(cursor.getColumnIndex("account")));
			bill.setJine(cursor.getString(cursor.getColumnIndex("jine")));
			bill.setBeizhu(cursor.getString(cursor.getColumnIndex("beizhu")));
			bill.setTransferIn(cursor.getString(cursor.getColumnIndex("transferIn")));
			bill.setTransferOut(cursor.getString(cursor.getColumnIndex("transferOut")));
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
	
	//����idx���Ҽ�����Ϣ
	public Bill findBillByIdx(int idx) {
		Bill bill = new Bill();
		Cursor cursor = db.rawQuery("select * from bill_info where idx = ?", new String[]{String.valueOf(idx)});
		if(cursor.moveToFirst()) {
			bill.setIdx(idx);
			bill.setJine(cursor.getString(cursor.getColumnIndex("jine")));
			bill.setInCatagory(cursor.getString(cursor.getColumnIndex("incatagory")));
			bill.setOutCatagory(cursor.getString(cursor.getColumnIndex("outcatagory")));
			bill.setAccount(cursor.getString(cursor.getColumnIndex("account")));
			bill.setDate(cursor.getString(cursor.getColumnIndex("date")));
			bill.setDateYMD(cursor.getString(cursor.getColumnIndex("dateymd")));
			bill.setMember(cursor.getString(cursor.getColumnIndex("member")));
			bill.setBeizhu(cursor.getString(cursor.getColumnIndex("beizhu")));
			String canBaoxiaoStr = cursor.getString(cursor.getColumnIndex("isCanBaoXiao"));
			if("false".equals(canBaoxiaoStr)) {
				bill.setCanBaoXiao(false);
			}else if("true".equals(canBaoxiaoStr)) {
				bill.setCanBaoXiao(true);
			}
			bill.setTransferIn(cursor.getString(cursor.getColumnIndex("transferIn")));
			bill.setTransferOut(cursor.getString(cursor.getColumnIndex("transferOut")));
			bill.setBillType(cursor.getInt(cursor.getColumnIndex("billType")));
		}
		return bill;
	}
	
	//����IDXɾ����Ϣ
	public void deleteBillByIdx(int idx) {
		db.execSQL("delete from bill_info where idx = ?", new String[]{String.valueOf(idx)});
	}
}








