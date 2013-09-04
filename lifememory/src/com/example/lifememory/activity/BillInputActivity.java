package com.example.lifememory.activity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.Bill;
import com.example.lifememory.db.service.BillCatagoryService;
import com.example.lifememory.db.service.BillInfoService;
import com.example.lifememory.utils.AppAplication;
import com.example.lifememory.utils.ConstantUtil;
import com.example.lifememory.utils.CopyFileFromData;
import com.example.lifememory.utils.DateFormater;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class BillInputActivity extends Activity {
	private BillInfoService billService;
	private TextView zhichuBtn, shouruBtn, zhuanzhangBtn;
	private CheckBox baoxiaoCb = null;
	private ViewFlipper viewFlipper = null;
	private LayoutInflater inflater;
	private View childView1, childView2, childView3;
	private PopupWindow calculator = null; // ������
	private View popWinParentView = null;
	private TextView zhichuJine, shouruJine, zhuanzhangJine, jineTv;
	private String jie_txt;
	private Bill bill = null;
	private boolean isFloat = false; // ���Ϊ�����ڱ���Ƿ����˼������е�С����
	private String temp1Str, temp2Str; // ����ʱ�������˵����ݻ��� temp1Str +(-, *,
										// /)temp2Str
	private boolean isClickFlag = false; // ����Ƿ����˼Ӽ��˳���ť
	private int flagId; // 0��1��2��3��
	private int parentId = 1;		     //���ڼ�¼һ������б�ѡ�е����ݵ����ݿ�id
	private int catagoryChildId = 0;	 //���ڼ�¼������б�ѡ�е����ݵ����ݿ�id
	private BillCatagoryService dbService = null;
	private String catagoryStr;          //���ڱ����������ʾ���������
	private String accountStr;           //���ڱ����������ʾ���˻�����
	private String memberStr;            //���ڱ����������ʾ�ĳ�Ա����
	private TextView currentJinETextView = null;		//����ָ��ǰviewflipper��ʾ�Ľ����ϵ���ʾ������ݵ�textView
	private TextView inCatagoryTextView = null;    //����ָ��ǰviewflipper��ʾ�Ľ����ϵ���ʾ֧��������ݵ�textView
	private TextView outCatagoryTextView = null;   //����ָ��ǰviewflipper��ʾ�Ľ����ϵ���ʾ����������ݵ�textView
	private TextView currentAccountTextView = null;     //����ָ��ǰviewflipper��ʾ�Ľ����ϵ���ʾ�˻����ݵ�textView
	private TextView currentMemberTextView = null;     //����ָ��ǰviewflipper��ʾ�Ľ����ϵ���ʾ��Ա���ݵ�textView
	private TextView currentDateTextView = null;		//����ָ��ǰviewflipper��ʾ�Ľ����ϵ���ʾ�������ݵ�textView
	private TextView currentBeizhuTextView = null;     //����ָ��ǰviewflipper��ʾ�Ľ����ϵ���ʾ��ע���ݵ�textView
	private TextView transferInTextView = null;			//���ת���˺ŵ�����
	private TextView transferOutTextView = null;		//���ת���˺ŵ�����
	private int outCatagorySelectedId = 0;                 //���ڼ�¼һ������б�ѡ�е�������
	private int outCatagorySelectedChildId = 0;			//���ڼ�¼��������б�ѡ�е�������
	private int accountGroupSelectedIndex = 0;			//���ڼ�¼�˻�expandablelistview���������
	private int accountChildSelectedIndex = 0;  		//���ڼ�¼�˻�expandablelistview���ӵ�����
	private int transferOutGroupSelectedIndex = 2;		//���ڼ�¼ת��expandablelistview���������
	private int transferOutChildSelectedIndex = 0;		//���ڼ�¼ת��expandablelistview���ӵ�����
	private int transferInGroupSelectedIndex = 0;		//���ڼ�¼ת��expandablelistview���������
	private int transferInChildSelectedIndex = 0;		//���ڼ�¼ת��expandablelistview���ӵ�����
	private int memberSelectedIndex = 1;					//���ڼ�¼��Ա�б��е�listviewѡ������
	private int inCatagorySelectedIndex = 0;             //���ڼ�¼����������������
	private Intent intent;

	/*
	 * ���ڼ�¼�ȺŰ�ť�Ƿ�����,��ÿ�ε����+,-,*,/��ť�󣬽�isEqualBtnClick=false
	 * ����ÿ�����popwindow֮��Ļ���back�ر�popwindow��ʱ�򣬾��ж����isEqualBtnClick=false���ͽ�textview
	 * �Ľ����Ϊ���+��-,*,/֮ǰ��ֵ
	 */
	private boolean isEqualBtnClick = true;                
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_input_layout);
		
		billService = new BillInfoService(this);
		dbService = new BillCatagoryService(this);
		inflater = LayoutInflater.from(this);
		
		bill = new Bill();
		bill.setOutCatagory("����-���");
		bill.setAccount("�ֽ�");
		bill.setMember("�Լ�");
		bill.setInCatagory("����");
		bill.setTransferOut("����");
		bill.setTransferIn("�ֽ�");
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		bill.setDate(sdf.format(d));
		bill.setDateYMD(DateFormater.getInstatnce().getY_M_D());
		
		findViews();
		initViews();
		initCalculator();
		
//		Log.i("a", "BillInputActivity onCreate catagorySelectedId = " + catagorySelectedId + " catagorySelectedChildId = " + catagorySelectedChildId);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 99) {
			//��BillCatagorySettingActivity����
			parentId = data.getIntExtra("parentId", 0);
			catagoryChildId = data.getIntExtra("catagoryid", 0);
			outCatagorySelectedId = data.getIntExtra("catagorySelectedId", 0);
			outCatagorySelectedChildId = data.getIntExtra("catagorySelectedChildId", 0);
			catagoryStr = dbService.getCatagoryStr(catagoryChildId);
//			Log.i("a", "BillInputActivity onActivityResult catagorySelectedId = " + catagorySelectedId + " catagorySelectedChildId = " + catagorySelectedChildId);
			bill.setOutCatagory(catagoryStr);
			outCatagoryTextView.setText(catagoryStr);
		}else if(resultCode == 98) {
			//��BillAccountSettingActivity����
			String accountFlag = data.getStringExtra("accountFlag");
			accountStr = data.getStringExtra("accountStr");
			if(accountFlag.equals(ConstantUtil.ACCOUNT_COMMON)) {
				accountGroupSelectedIndex = data.getIntExtra("currentGroupSelectedIndex", 0);
				accountChildSelectedIndex = data.getIntExtra("currentChildSelectedIndex", 0);
				bill.setAccount(accountStr);
				currentAccountTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.zhanghu);
				currentAccountTextView.setText(accountStr);
			}else if(accountFlag.equals(ConstantUtil.ACCOUNT_TRANSFER_OUT)) {
				transferOutGroupSelectedIndex = data.getIntExtra("currentGroupSelectedIndex", 0);
				transferOutChildSelectedIndex = data.getIntExtra("currentChildSelectedIndex", 0);
				bill.setTransferOut(accountStr);
				transferOutTextView.setText(accountStr);
			}else if(accountFlag.equals(ConstantUtil.ACCOUNT_TRANSFER_IN)) {
				transferInGroupSelectedIndex = data.getIntExtra("currentGroupSelectedIndex", 0);
				transferInChildSelectedIndex = data.getIntExtra("currentChildSelectedIndex", 0);
				bill.setTransferIn(accountStr);
				transferInTextView.setText(accountStr);
			}
//			accountGroupSelectedIndex = data.getIntExtra("currentGroupSelectedIndex", 0);
//			accountChildSelectedIndex = data.getIntExtra("currentChildSelectedIndex", 0);
//			accountStr = data.getStringExtra("accountStr");
//			bill.setAccount(accountStr);
//			currentAccountTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.zhanghu);
//			currentAccountTextView.setText(accountStr);
		}else if(ConstantUtil.SELECT_MEMEBER_FINISHED == resultCode) {
			//��BillMemberSelect����
			memberSelectedIndex = data.getIntExtra("currentSelectedIndex", 0);
			memberStr = data.getStringExtra("member");
			bill.setMember(memberStr);
			currentMemberTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.chengyuan);
			currentMemberTextView.setText(memberStr);
		}else if(ConstantUtil.SELECTED_DATE_FINISHED == resultCode) {
			//ѡ���������
			String dateStr = data.getStringExtra("date");
			String dateYMD = data.getStringExtra("dateYMD");
			bill.setDate(dateStr);
			bill.setDateYMD(dateYMD);
			currentDateTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.date);
			currentDateTextView.setText(dateStr);
		}else if(ConstantUtil.EDIT_BEIZHU == resultCode) {
			String beizhuStr = data.getStringExtra("content");
			bill.setBeizhu(beizhuStr);
			currentBeizhuTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.beizhu);
			currentBeizhuTextView.setText(beizhuStr);
		}else if(ConstantUtil.SELECTED_INCATAGORY_FINISHED == resultCode) {
			String inCatagoryName = data.getStringExtra("catagoryname");
			inCatagorySelectedIndex = data.getIntExtra("currentSelectedIndex", 0);
			bill.setInCatagory(inCatagoryName);
			inCatagoryTextView.setText(inCatagoryName);
		}
	}

	private void findViews() {
		zhichuBtn = (TextView) this.findViewById(R.id.zhichu);
		shouruBtn = (TextView) this.findViewById(R.id.shouru);
		zhuanzhangBtn = (TextView) this.findViewById(R.id.zhuanzhang);
		baoxiaoCb = (CheckBox) this.findViewById(R.id.baoxiaocb);
		viewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);
		childView1 = viewFlipper.getChildAt(0);
		childView2 = viewFlipper.getChildAt(1);
		childView3 = viewFlipper.getChildAt(2);
		zhichuJine = (TextView) childView1.findViewById(R.id.jine);
		shouruJine = (TextView) childView2.findViewById(R.id.jine);
		zhuanzhangJine = (TextView) childView3.findViewById(R.id.jine);
		currentBeizhuTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.beizhu);
		outCatagoryTextView = (TextView) childView1.findViewById(R.id.leixing);
		inCatagoryTextView = (TextView) childView2.findViewById(R.id.leixing);
		inCatagoryTextView.setText(bill.getInCatagory());
		outCatagoryTextView.setText(bill.getOutCatagory());
		transferInTextView = (TextView) childView3.findViewById(R.id.zhuanru);
		transferOutTextView = (TextView) childView3.findViewById(R.id.zhuanchu);
		
		
		
		popWinParentView = this.findViewById(R.id.popWinParent);
	}

	private void initViews() {
		zhichuBtn.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
		zhichuBtn.setTextColor(Color.WHITE);
		currentDateTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.date);
		currentDateTextView.setText(bill.getDate());

	}

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.zhichu:
			// ֧����ť
			changeBtnBack(0);
			break;
		case R.id.shouru:
			// ���밴ť
			changeBtnBack(1);
			break;
		case R.id.zhuanzhang:
			// ת�˰�ť
			changeBtnBack(2);
			break;
		case R.id.jinelayout:
			// ���
			showCalculator();
			break;
		case R.id.leixinglayout:
			//���ת���������ý���
			if(viewFlipper.getDisplayedChild() == 1) {
				//���������������
				intent = new Intent(BillInputActivity.this, BillInCatagorySettingActivity.class);
				intent.putExtra("currentSelectedIndex", inCatagorySelectedIndex);
				this.startActivityForResult(intent, 100);
				overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			}else {
				//���֧�����������
			    intent = new Intent(BillInputActivity.this, BillCatagorySettingActivity.class);
				intent.putExtra("parentId", parentId);
				intent.putExtra("catagorySelectedId", outCatagorySelectedId);
				intent.putExtra("catagorySelectedChildId", outCatagorySelectedChildId);
				this.startActivityForResult(intent, 100);
				overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			}
			
			
			break;
		case R.id.zhanghulayout:
			intent = new Intent(BillInputActivity.this, BillAccountSettingActivity.class);
			intent.putExtra("currentGroupSelectedIndex", accountGroupSelectedIndex);
			intent.putExtra("currentChildSelectedIndex", accountChildSelectedIndex);
			intent.putExtra("accountFlag", ConstantUtil.ACCOUNT_COMMON);
			this.startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			
			break;
		case R.id.riqilayout:
			intent = new Intent(BillInputActivity.this, BillDataWheelPickerActivity.class);
			startActivityForResult(intent, 100);
			break;
		case R.id.chengyuanlayout:
			intent = new Intent(BillInputActivity.this, BillMemberSelectActivity.class);
			intent.putExtra("currentSelectedIndex", memberSelectedIndex);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		case R.id.beizhulayout:
			intent = new Intent(BillInputActivity.this, BillTextInputActivity.class);
			intent.putExtra("title", "�˵���ע");
			intent.putExtra("fenlei", ConstantUtil.EDIT_BEIZHU);
			intent.putExtra("editNum", 100);
			intent.putExtra("content", currentBeizhuTextView.getText().toString());
			startActivityForResult(intent, 100);
			break;
		case R.id.baoxiaolayout:
			if (baoxiaoCb.isChecked()) {
				baoxiaoCb.setChecked(false);
			} else {
				baoxiaoCb.setChecked(true);
			}
			break;
			
		case R.id.zhuanchulayout:
			
			intent = new Intent(BillInputActivity.this, BillAccountSettingActivity.class);
			intent.putExtra("currentGroupSelectedIndex", transferOutGroupSelectedIndex);
			intent.putExtra("currentChildSelectedIndex", transferOutChildSelectedIndex);
			intent.putExtra("accountFlag", ConstantUtil.ACCOUNT_TRANSFER_OUT);
			this.startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			
			break;
		case R.id.zhuanrulayout:
			
			intent = new Intent(BillInputActivity.this, BillAccountSettingActivity.class);
			intent.putExtra("currentGroupSelectedIndex", transferInGroupSelectedIndex);
			intent.putExtra("currentChildSelectedIndex", transferInChildSelectedIndex);
			intent.putExtra("accountFlag", ConstantUtil.ACCOUNT_TRANSFER_IN);
			this.startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			
			break;
		case R.id.back:
			back();
			break;
		case R.id.save:
			
			currentJinETextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.jine);
			if(currentJinETextView.getText().toString() == null || "".equals(currentJinETextView.getText().toString()) || "0".equals(currentJinETextView.getText().toString())) {
				Toast.makeText(BillInputActivity.this, "��������!", 0).show();
			}else {
				currentBeizhuTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.beizhu);
				bill.setBeizhu(currentBeizhuTextView.getText().toString());
				bill.setJine(currentJinETextView.getText().toString());
				bill.setBaoxiaoed(false);
				if(baoxiaoCb.isChecked()) {
					bill.setCanBaoXiao(true);
				}else {
					bill.setCanBaoXiao(false);
				}
				
				switch (viewFlipper.getDisplayedChild()) {
				case 0:
					//֧��
					bill.setBillType(1);
					billService.addOutBill(bill);
					break;
				case 1:
					//����
					bill.setBillType(2);
					billService.addInBill(bill);
					break;
				case 2:
					//ת��
					bill.setBillType(3);
					billService.addTransferBill(bill);
					break;
				}

//				Log.i("a", bill.toString());
				
				Toast.makeText(BillInputActivity.this, "�˵���Ϣ����ɹ�!", 0).show();
				BillInputActivity.this.finish();
				overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
			}
			
						
			
			break;
		}
	}

	private void changeBtnBack(int index) {
		switch (index) {
		case 0:
			// ֧��
			zhichuBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			zhichuBtn.setTextColor(Color.WHITE);
			shouruBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_normal);
			shouruBtn.setTextColor(Color.BLACK);
			zhuanzhangBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_normal);
			zhuanzhangBtn.setTextColor(Color.BLACK);

			if (viewFlipper.getDisplayedChild() != 0) {
				if (viewFlipper.getDisplayedChild() == 1) {
					viewFlipper.showPrevious();
				} else if (viewFlipper.getDisplayedChild() == 2) {
					viewFlipper.showPrevious();
					viewFlipper.showPrevious();
				}
			}

			break;
		case 1:
			// ����
			shouruBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			shouruBtn.setTextColor(Color.WHITE);
			zhuanzhangBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_normal);
			zhuanzhangBtn.setTextColor(Color.BLACK);
			zhichuBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_normal);
			zhichuBtn.setTextColor(Color.BLACK);

			if (viewFlipper.getDisplayedChild() != 1) {
				if (viewFlipper.getDisplayedChild() == 0) {
					viewFlipper.showNext();
				} else if (viewFlipper.getDisplayedChild() == 2) {
					viewFlipper.showPrevious();
				}
			}

			break;
		case 2:
			// ת��
			zhuanzhangBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_pressed);
			zhuanzhangBtn.setTextColor(Color.WHITE);
			zhichuBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_normal);
			zhichuBtn.setTextColor(Color.BLACK);
			shouruBtn
					.setBackgroundResource(R.drawable.exit_demo_mode_btn_normal);
			shouruBtn.setTextColor(Color.BLACK);

			if (viewFlipper.getDisplayedChild() != 2) {
				if (viewFlipper.getDisplayedChild() == 0) {
					viewFlipper.showNext();
					viewFlipper.showNext();
				} else if (viewFlipper.getDisplayedChild() == 1) {
					viewFlipper.showNext();
				}
			}
			break;
		}
		refreshJinETextView();
		refreshCatagoryTextView();
		refreshDateTextView();
		refreshBeizhuTextView();
		if(viewFlipper.getDisplayedChild() != 2) {
			refreshMemberTextView();
			refreshAccountTextView();
		}
	}



	LinearLayout cal_equal;
	LinearLayout cal_del;
	ImageView cal_sure;

	// ��ʼ�������� popwindow
	private void initCalculator() {
		View contentView = inflater.inflate(R.layout.popwindow_bill_counter,
				null);
		calculator = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		TextView cal_add = (TextView) contentView.findViewById(R.id.cal_add);
		TextView cal_minus = (TextView) contentView
				.findViewById(R.id.cal_minus);
		TextView cal_multiply = (TextView) contentView
				.findViewById(R.id.cal_multiply);
		TextView cal_divide = (TextView) contentView
				.findViewById(R.id.cal_divide);
		TextView cal_one = (TextView) contentView.findViewById(R.id.cal_one);
		TextView cal_two = (TextView) contentView.findViewById(R.id.cal_two);
		TextView cal_three = (TextView) contentView
				.findViewById(R.id.cal_three);
		TextView cal_four = (TextView) contentView.findViewById(R.id.cal_four);
		TextView cal_five = (TextView) contentView.findViewById(R.id.cal_five);
		TextView cal_six = (TextView) contentView.findViewById(R.id.cal_six);
		TextView cal_seven = (TextView) contentView
				.findViewById(R.id.cal_seven);
		TextView cal_eight = (TextView) contentView
				.findViewById(R.id.cal_eight);
		TextView cal_nine = (TextView) contentView.findViewById(R.id.cal_nine);
		TextView cal_zero = (TextView) contentView.findViewById(R.id.cal_zero);
		TextView cal_dot = (TextView) contentView.findViewById(R.id.cal_dot);
		cal_equal = (LinearLayout) contentView.findViewById(R.id.cal_equal);
		cal_del = (LinearLayout) contentView.findViewById(R.id.cal_del);
		cal_sure = (ImageView) contentView.findViewById(R.id.cal_sure);
		cal_add.setOnClickListener(new CalculatorBtnClickListener());
		cal_minus.setOnClickListener(new CalculatorBtnClickListener());
		cal_multiply.setOnClickListener(new CalculatorBtnClickListener());
		cal_divide.setOnClickListener(new CalculatorBtnClickListener());
		cal_one.setOnClickListener(new CalculatorBtnClickListener());
		cal_two.setOnClickListener(new CalculatorBtnClickListener());
		cal_three.setOnClickListener(new CalculatorBtnClickListener());
		cal_four.setOnClickListener(new CalculatorBtnClickListener());
		cal_five.setOnClickListener(new CalculatorBtnClickListener());
		cal_six.setOnClickListener(new CalculatorBtnClickListener());
		cal_seven.setOnClickListener(new CalculatorBtnClickListener());
		cal_eight.setOnClickListener(new CalculatorBtnClickListener());
		cal_nine.setOnClickListener(new CalculatorBtnClickListener());
		cal_zero.setOnClickListener(new CalculatorBtnClickListener());
		cal_dot.setOnClickListener(new CalculatorBtnClickListener());
		cal_del.setOnClickListener(new CalculatorBtnClickListener());
		cal_sure.setOnClickListener(new CalculatorBtnClickListener());
		cal_equal.setOnClickListener(new CalculatorBtnClickListener());
		calculator.setFocusable(true);
		calculator.setBackgroundDrawable(new ColorDrawable());
		calculator.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				if(!isEqualBtnClick) {
					BillInputActivity.this.isClickFlag = false;   //��ÿ�ιرռ�����ʱ�����Ƿ������Ÿ�λ
					BillInputActivity.this.isFloat = false;       //��ÿ�ιرռ�����ʱ�����Ƿ��Ǹ�������λ
					jineTv = (TextView) viewFlipper.getCurrentView().findViewById(
							R.id.jine);
					jineTv.setText(temp1Str);
					cal_equal.setVisibility(ViewGroup.GONE);
					cal_sure.setVisibility(ViewGroup.VISIBLE);
				}
			}
		});
	}

	private void showCalculator() {
		calculator.showAtLocation(popWinParentView, Gravity.BOTTOM, 0, 0);
	}

	private class CalculatorBtnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.cal_add:
				isEqualBtnClick = false;
				onClickFlag(0);
				break;
			case R.id.cal_minus:
				isEqualBtnClick = false;
				onClickFlag(1);
				break;
			case R.id.cal_multiply:
				isEqualBtnClick = false;
				onClickFlag(2);
				break;
			case R.id.cal_divide:
				isEqualBtnClick = false;
				onClickFlag(3);
				break;
			case R.id.cal_sure:
				if (calculator.isShowing()) {
					calculator.dismiss();
				}
				break;
			case R.id.cal_equal:
				isEqualBtnClick = true;
				onClickEqual();
				break;
			case R.id.cal_one:
				onClickNum(1);
				break;
			case R.id.cal_two:
				onClickNum(2);
				break;
			case R.id.cal_three:
				onClickNum(3);
				break;
			case R.id.cal_four:
				onClickNum(4);
				break;
			case R.id.cal_five:
				onClickNum(5);
				break;
			case R.id.cal_six:
				onClickNum(6);
				break;
			case R.id.cal_seven:
				onClickNum(7);
				break;
			case R.id.cal_eight:
				onClickNum(8);
				break;
			case R.id.cal_nine:
				onClickNum(9);
				break;
			case R.id.cal_dot:
				onClickDot();
				break;
			case R.id.cal_zero:
				onClickNum(0);
				break;
			case R.id.cal_del:
				onClickDel();
				break;
			}
		}
	}

	private void refreshJinETextView() {
		if (bill.getJine() == null || "".equals(bill.getJine())) {
			jie_txt = "0";
		} else {
			jie_txt = bill.getJine();
		}
		zhichuJine.setText(jie_txt);
		shouruJine.setText(jie_txt);
		zhuanzhangJine.setText(jie_txt);
	}
	
	private void refreshCatagoryTextView() {
		outCatagoryTextView.setText(bill.getOutCatagory());
		inCatagoryTextView.setText(bill.getInCatagory());
	}
	
	private void refreshAccountTextView() {
		currentAccountTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.zhanghu);
		currentAccountTextView.setText(bill.getAccount());
	}
	
	private void refreshMemberTextView() {
		currentMemberTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.chengyuan);
		currentMemberTextView.setText(bill.getMember());
	}
	
	private void refreshDateTextView() {
		currentDateTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.date);
		currentDateTextView.setText(bill.getDate());
	}
	
	private void refreshBeizhuTextView() {
		currentBeizhuTextView = (TextView) viewFlipper.getCurrentView().findViewById(R.id.beizhu);
		currentBeizhuTextView.setText(bill.getBeizhu());
	}
	
	// ����˼������ϵ����ּ�
	private void onClickNum(int numStr) {
		jineTv = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.jine);
		String jie_str = jineTv.getText().toString();
		Float jie_float = 0f;
		Long jie_long = 0l;
		if (jie_str.contains(".")) {
			// ��ǰֵ�Ǹ�����
			if (jie_str.substring(jie_str.indexOf("."), jie_str.length())
					.length() < 3) {
				// ����2λС��λ
				jie_float = Float.parseFloat(jie_str);
				jie_txt = jie_float + "" + numStr;
			}
		} else {
			// ��ǰֵ������
			jie_long = Long.parseLong(jie_str);
			if (isFloat) {
				// �����С����
				if (jie_long == 0) {
					jie_txt = "0." + numStr;
				} else {
					jie_txt = jie_str + "." + numStr;
				}
			} else {
				// δ���С����
				if (jie_str.length() < 7) {
					// ����λ����������
					if (jie_long == 0) {
						jie_txt = "" + numStr;
					} else {
						jie_txt = jineTv.getText().toString() + numStr;
					}
				}
			}
		}
		jineTv.setText(jie_txt);
		bill.setJine(jie_txt);
	}

	// ����˼������ϵ�С����
	private void onClickDot() {
		isFloat = true;
	}

	// ����˼������ϵ�ɾ��
	private void onClickDel() {
		jineTv = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.jine);
		String jie_str = jineTv.getText().toString();
//		Log.i("a", "jine length = " + jie_str.length());
		if (jie_str.length() > 1) {
			jie_txt = jie_str.substring(0, jie_str.length() - 1); // ȥ�����һ���ַ�
			if (jie_txt.endsWith(".")) {
				// �����С�����β
				jie_txt = jie_txt.substring(0, jie_txt.length() - 1);
				isFloat = false;
			}
		} else {
			jie_txt = "0";
		}
		jineTv.setText(jie_txt);
		bill.setJine(jie_txt);
	}

	// ����˼������ϵļӼ��˳�
	private void onClickFlag(int flagId) {
		cal_sure.setVisibility(ViewGroup.GONE);
		cal_equal.setVisibility(ViewGroup.VISIBLE);

		if (!isClickFlag) {
			jineTv = (TextView) viewFlipper.getCurrentView().findViewById(
					R.id.jine);
			temp1Str = jineTv.getText().toString();
			jineTv.setText("0");
			isClickFlag = true; // ����˼Ӽ��˳���ť
		}
		this.flagId = flagId;
		this.isFloat = false;    //������Ű�ť���Ƿ��Ǹ��������Ϊ��λ����Ȼ�ٵ������ʱ����С��
	}

	// ����˼������ϵĵȺ�
	private void onClickEqual() {
		cal_sure.setVisibility(ViewGroup.VISIBLE);
		cal_equal.setVisibility(ViewGroup.GONE);

		jineTv = (TextView) viewFlipper.getCurrentView()
				.findViewById(R.id.jine);
		String jie_str = jineTv.getText().toString();
		if (jie_str.length() > 0 && !"0".equals(jie_str)) {
			// ���������֣����Ҳ���0
			DecimalFormat df = new DecimalFormat("0.00");
			temp2Str = jie_str;
			String resultStr;
			Float temp1Float = 0f;
			Float temp2Float = 0f;
			Float resultFloat = 0f;
			switch (this.flagId) {
			case 0:
				// ��
				temp1Float = Float.parseFloat(temp1Str);
				temp2Float = Float.parseFloat(temp2Str);
				resultFloat = temp1Float + temp2Float;
				resultStr = resultStrDeleteZero(df.format(resultFloat));
				jineTv.setText(resultStr);
//				Log.i("a", "temp1Float = " + temp1Float + "   temp2Float = " + temp2Float + "   result = " + resultFloat);
				bill.setJine(resultStr);

				break;
			case 1:
				// ��
				temp1Float = Float.parseFloat(temp1Str);
				temp2Float = Float.parseFloat(temp2Str);
				resultFloat = temp1Float - temp2Float;
				resultStr = resultStrDeleteZero(df.format(resultFloat));
				jineTv.setText(resultStr);
//				Log.i("a", "temp1Float = " + temp1Float + "   temp2Float = " + temp2Float + "   result = " + resultFloat);
				bill.setJine(resultStr);
				break;
			case 2:
				// ��
				temp1Float = Float.parseFloat(temp1Str);
				temp2Float = Float.parseFloat(temp2Str);
				resultFloat = temp1Float * temp2Float;
				resultStr = resultStrDeleteZero(df.format(resultFloat));
				jineTv.setText(resultStr);
//				Log.i("a", "temp1Float = " + temp1Float + "   temp2Float = " + temp2Float + "   result = " + resultFloat);
				bill.setJine(resultStr);
				break;
			case 3:
				// ��
				temp1Float = Float.parseFloat(temp1Str);
				temp2Float = Float.parseFloat(temp2Str);
				resultFloat = temp1Float / temp2Float;
				resultStr = resultStrDeleteZero(df.format(resultFloat));
				jineTv.setText(resultStr);
//				Log.i("a", "temp1Float = " + temp1Float + "   temp2Float = " + temp2Float + "   result = " + resultFloat);
				bill.setJine(resultStr);
				break;
			}

		} else {
			jineTv.setText(temp1Str);    //����������ź���û���������ֻ������������Ϊ0����ʱ���Ⱥ�ʱ��jineTv��ֵ����֮ǰ�����ֵtemp1Str
		}
		isClickFlag = false;
	}
	

	//�����Ľ���ַ��ͽ��ȥ��С��λ��0������12.30�� ȥ��0���12.3������12.00ȥ���Ϊ12
	private String resultStrDeleteZero(String resultStr) {
		String str = null;
		str = resultStr.substring(resultStr.lastIndexOf(".") + 1, resultStr.length());
		if(str.length() == 1){
			if(str.equals("0")) {
				return resultStr.substring(0, resultStr.length() - 2);
			}
		}else {
			String a = str.substring(1, 2);   //01 �е�1
			String b = str.substring(0, 1);	  //01�е�0
			if(a.equals("0")) {
				resultStr = resultStr.substring(0, resultStr.length() - 1);
				if(b.equals("0")) {
					return resultStr.substring(0, resultStr.length() - 2);
				}
			}
		}
		return resultStr;
	}
	
	

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbService.closeDB();
		billService.closeDB();
		dbService = null;
		billService = null;
	}
	
	
	private void back() {
		BillInputActivity.this.finish();
		overridePendingTransition(R.anim.activity_steady,
				R.anim.activity_down);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			back();
			break;
		}
		return true;
	}
	
}















