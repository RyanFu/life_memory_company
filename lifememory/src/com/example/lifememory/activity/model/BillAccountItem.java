package com.example.lifememory.activity.model;

/**
 * �����е��˻�
 * @author Administrator
 *
 */
public class BillAccountItem {

	private int idx;
	private int catagoryname;    //�������   �ֽ� 1�� ���ÿ� 2�� ���� 3�� �������� 4
	private String name;
	private String bizhong;			//����
	private double dangqianyue;
	private boolean isNotice;		//�Ƿ񾯽�
	private double noticeValue;     //�����߽��
	private int imageId;
	
	public BillAccountItem(){}

	
	
	public BillAccountItem(int catagoryname, String name, String bizhong,
			double dangqianyue, boolean isNotice, double noticeValue,
			int imageId) {
		super();
		this.catagoryname = catagoryname;
		this.name = name;
		this.bizhong = bizhong;
		this.dangqianyue = dangqianyue;
		this.isNotice = isNotice;
		this.noticeValue = noticeValue;
		this.imageId = imageId;
	}



	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getCatagoryname() {
		return catagoryname;
	}

	public void setCatagoryname(int catagoryname) {
		this.catagoryname = catagoryname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBizhong() {
		return bizhong;
	}

	public void setBizhong(String bizhong) {
		this.bizhong = bizhong;
	}

	public double getDangqianyue() {
		return dangqianyue;
	}

	public void setDangqianyue(double dangqianyue) {
		this.dangqianyue = dangqianyue;
	}

	public boolean isNotice() {
		return isNotice;
	}

	public void setNotice(boolean isNotice) {
		this.isNotice = isNotice;
	}

	public double getNoticeValue() {
		return noticeValue;
	}

	public void setNoticeValue(double noticeValue) {
		this.noticeValue = noticeValue;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	
	
	
	

	
}
