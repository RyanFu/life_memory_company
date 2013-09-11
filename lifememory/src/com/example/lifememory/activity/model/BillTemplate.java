package com.example.lifememory.activity.model;


/**
 * idx integer primary key autoincrement, 
 * name text,catagoryname text,
 *  accountid integer, member text,
 *   canbaoxiao, transferinaccountdid integer,
 *    transferoutaccountid integer, billtype integer
 *
 */
public class BillTemplate {

	private int idx;
	private String name;
	private String inCatagoryName;
	private String outCatagoryName;
	private int accountid;
	private String member;
	private boolean canBaoXiao;
	private int transferInAccountId;
	private int transferOutAccountId;
	private int billType;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getInCatagoryName() {
		return inCatagoryName;
	}
	public void setInCatagoryName(String inCatagoryName) {
		this.inCatagoryName = inCatagoryName;
	}
	public String getOutCatagoryName() {
		return outCatagoryName;
	}
	public void setOutCatagoryName(String outCatagoryName) {
		this.outCatagoryName = outCatagoryName;
	}
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public boolean isCanBaoXiao() {
		return canBaoXiao;
	}
	public void setCanBaoXiao(boolean canBaoXiao) {
		this.canBaoXiao = canBaoXiao;
	}
	public int getTransferInAccountId() {
		return transferInAccountId;
	}
	public void setTransferInAccountId(int transferInAccountId) {
		this.transferInAccountId = transferInAccountId;
	}
	public int getTransferOutAccountId() {
		return transferOutAccountId;
	}
	public void setTransferOutAccountId(int transferOutAccountId) {
		this.transferOutAccountId = transferOutAccountId;
	}
	public int getBillType() {
		return billType;
	}
	public void setBillType(int billType) {
		this.billType = billType;
	}
}
