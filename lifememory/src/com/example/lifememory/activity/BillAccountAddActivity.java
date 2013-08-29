package com.example.lifememory.activity;

import java.text.DecimalFormat;
import com.example.lifememory.R;
import com.example.lifememory.utils.ConstantUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class BillAccountAddActivity extends Activity {
	private Intent intent;
	private CheckBox noticeCb;
	private String title;								   //���͵�BillTextInputActivity�ı���ֵ
	private RelativeLayout accountNoticeValueLayout;       //�����߽��layout
//	private final static int EDIT_NAME_FINISHED = 99;           //���Ʊ༭���
//	private final static int EDIT_NOTICE_FINISHED = 98;         //�����߱༭���
//	private final static int EDIT_BEIZHU = 97;                  //��ע�༭���
//	private final static int EDIT_BIZHONGFINISHED = 96;         //����ѡ�����
//	private final static int EDIT_CATAGORYNAMEFINISHED = 95;    //�������ѡ�����
	private int editNum = 0;								//���͵�BillTextInputActivity�Ŀɱ༭������
	private String content;									//���ڽ���BillTextInputActivity�����ı༭����
	private String bizhong;                                 //���ڽ���BillAccountBiZhongSettingActivity�����ı�����Ϣ
	private String catagoryName;                             //���ڽ���BillAccountCatagoryNameSelectActivity���������������Ϣ
	private TextView accountNameTv, accountBiZhongTv, accountcatagorynameTv, accountyueTv, accountNoticeValueTv;
	private int bizhongCurrentSelectedIndex = 0;                   //�û����պʹ��͵�ǰѡ��Ļ�������
	private int catagorynameCurrentSelectedIndex = 0;
	private PopupWindow calculator = null; // ������
	private LayoutInflater inflater;
	LinearLayout cal_equal;
	LinearLayout cal_del;
	ImageView cal_sure;
	private boolean isClickFlag = false; // ����Ƿ����˼Ӽ��˳���ť
	private boolean isFloat = false; // ���Ϊ�����ڱ���Ƿ����˼������е�С����
	private String temp1Str, temp2Str; // ����ʱ�������˵����ݻ��� temp1Str +(-, *,
										// /)temp2Str
	private View popWinParentView = null;
	private String jie_txt;
	private int flagId; // 0��1��2��3��
	
	/*
	 * ��Ϊ��������棬������layout����󶼻�ʹ�õ���������popwindow��Ȼ��������еĸ��ְ�ť�ɻ�õ��ض���textview��ǰ
	 * ��ʾ�Ľ�������viewId����ʾ�ǵ�����Ǹ�layout��ȥȡ�����textview��ֵ
	 */
	private int viewId = 0;         
	
	/*
	 * ���ڼ�¼�ȺŰ�ť�Ƿ�����,��ÿ�ε����+,-,*,/��ť�󣬽�isEqualBtnClick=false
	 * ����ÿ�����popwindow֮��Ļ���back�ر�popwindow��ʱ�򣬾��ж����isEqualBtnClick=false���ͽ�textview
	 * �Ľ����Ϊ���+��-,*,/֮ǰ��ֵ
	 */
	private boolean isEqualBtnClick = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_account_add_layout);
		inflater = LayoutInflater.from(this);
		findViews();
		setListeners();
		initCalculator();
	}
	
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
						BillAccountAddActivity.this.isClickFlag = false;   //��ÿ�ιرռ�����ʱ�����Ƿ������Ÿ�λ
						BillAccountAddActivity.this.isFloat = false;       //��ÿ�ιرռ�����ʱ�����Ƿ��Ǹ�������λ
						
						if(viewId == R.id.accountyue) {
							accountyueTv.setText(temp1Str);
						}else if(viewId == R.id.accountNoticeValue) {
							accountNoticeValueTv.setText(temp1Str);
						}
						cal_equal.setVisibility(ViewGroup.GONE);
						cal_sure.setVisibility(ViewGroup.VISIBLE);
					}
					viewId = 0;
					
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


		// ����˼������ϵ����ּ�
		private void onClickNum(int numStr) {
			String jie_str = null;
			if(viewId == R.id.accountyue) {
				jie_str = accountyueTv.getText().toString();
			}else if(viewId == R.id.accountNoticeValue) {
				jie_str = accountNoticeValueTv.getText().toString();
			}
			
			
			
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
							jie_txt = accountyueTv.getText().toString() + numStr;
						}
					}
				}
			}
			
			if(viewId == R.id.accountyue) {
				accountyueTv.setText(jie_txt);
			}else if(viewId == R.id.accountNoticeValue) {
				accountNoticeValueTv.setText(jie_txt);
			}
		}

		// ����˼������ϵ�С����
		private void onClickDot() {
			isFloat = true;
		}

		// ����˼������ϵ�ɾ��
		private void onClickDel() {
			String jie_str = null;
			if(viewId == R.id.accountyue) {
				jie_str = accountyueTv.getText().toString();
			}else if(viewId == R.id.accountNoticeValue) {
				jie_str = accountNoticeValueTv.getText().toString();
			}
//			Log.i("a", "jine length = " + jie_str.length());
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
			if(viewId == R.id.accountyue) {
				accountyueTv.setText(jie_txt);
			}else if(viewId == R.id.accountNoticeValue) {
				accountNoticeValueTv.setText(jie_txt);
			}
		}

		// ����˼������ϵļӼ��˳�
		private void onClickFlag(int flagId) {
			cal_sure.setVisibility(ViewGroup.GONE);
			cal_equal.setVisibility(ViewGroup.VISIBLE);

			if (!isClickFlag) {
				
				if(viewId == R.id.accountyue) {
					temp1Str = accountyueTv.getText().toString();
					accountyueTv.setText("0");
				}else if(viewId == R.id.accountNoticeValue) {
					temp1Str = accountNoticeValueTv.getText().toString();
					accountNoticeValueTv.setText("0");
				}
				isClickFlag = true; // ����˼Ӽ��˳���ť
			}
			this.flagId = flagId;
			this.isFloat = false;    //������Ű�ť���Ƿ��Ǹ��������Ϊ��λ����Ȼ�ٵ������ʱ����С��
		}

		// ����˼������ϵĵȺ�
		private void onClickEqual() {
			cal_sure.setVisibility(ViewGroup.VISIBLE);
			cal_equal.setVisibility(ViewGroup.GONE);
			String jie_str = null;
			if(viewId == R.id.accountyue) {
				jie_str = accountyueTv.getText().toString();
			}else if(viewId == R.id.accountNoticeValue) {
				jie_str = accountNoticeValueTv.getText().toString();
			}
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
					if(viewId == R.id.accountyue) {
						accountyueTv.setText(resultStr);
					}else if(viewId == R.id.accountNoticeValue) {
						accountNoticeValueTv.setText(resultStr);
					}
//					Log.i("a", "temp1Float = " + temp1Float + "   temp2Float = " + temp2Float + "   result = " + resultFloat);

					break;
				case 1:
					// ��
					temp1Float = Float.parseFloat(temp1Str);
					temp2Float = Float.parseFloat(temp2Str);
					resultFloat = temp1Float - temp2Float;
					resultStr = resultStrDeleteZero(df.format(resultFloat));
					if(viewId == R.id.accountyue) {
						accountyueTv.setText(resultStr);
					}else if(viewId == R.id.accountNoticeValue) {
						accountNoticeValueTv.setText(resultStr);
					}
					break;
				case 2:
					// ��
					temp1Float = Float.parseFloat(temp1Str);
					temp2Float = Float.parseFloat(temp2Str);
					resultFloat = temp1Float * temp2Float;
					resultStr = resultStrDeleteZero(df.format(resultFloat));
					if(viewId == R.id.accountyue) {
						accountyueTv.setText(resultStr);
					}else if(viewId == R.id.accountNoticeValue) {
						accountNoticeValueTv.setText(resultStr);
					}
//					Log.i("a", "temp1Float = " + temp1Float + "   temp2Float = " + temp2Float + "   result = " + resultFloat);
					break;
				case 3:
					// ��
					temp1Float = Float.parseFloat(temp1Str);
					temp2Float = Float.parseFloat(temp2Str);
					resultFloat = temp1Float / temp2Float;
					resultStr = resultStrDeleteZero(df.format(resultFloat));
					if(viewId == R.id.accountyue) {
						accountyueTv.setText(resultStr);
					}else if(viewId == R.id.accountNoticeValue) {
						accountNoticeValueTv.setText(resultStr);
					}
//					Log.i("a", "temp1Float = " + temp1Float + "   temp2Float = " + temp2Float + "   result = " + resultFloat);
					break;
				}

			} else {
				if(viewId == R.id.accountyue) {
					accountyueTv.setText(temp1Str);
				}else if(viewId == R.id.accountNoticeValue) {
					accountNoticeValueTv.setText(temp1Str);
				}
				    //����������ź���û���������ֻ������������Ϊ0����ʱ���Ⱥ�ʱ��jineTv��ֵ����֮ǰ�����ֵtemp1Str
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
		
		
		
	
	private void findViews() {
		noticeCb = (CheckBox) this.findViewById(R.id.noticeCheckbox);
		accountNoticeValueLayout = (RelativeLayout) this.findViewById(R.id.accountNoticeValueLayout);
		accountNameTv = (TextView) this.findViewById(R.id.accountname);
		accountBiZhongTv = (TextView) this.findViewById(R.id.accountbizhong);
		accountcatagorynameTv = (TextView) this.findViewById(R.id.accountcatagoryname);
		accountyueTv = (TextView) this.findViewById(R.id.accountyue);
		accountNoticeValueTv = (TextView) this.findViewById(R.id.accountNoticeValue);
		popWinParentView = this.findViewById(R.id.popParentView);
	}
	
	private void setListeners() {
		noticeCb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					accountNoticeValueLayout.setVisibility(ViewGroup.VISIBLE);
				}else {
					accountNoticeValueLayout.setVisibility(ViewGroup.GONE);
				}
			}
		});
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.save:
			break;
		case R.id.accountCatagoryLayout:
			intent = new Intent(BillAccountAddActivity.this, BillAccountCatagoryNameSelectActivity.class);
			intent.putExtra("catagorynameCurrentSelectedIndex", catagorynameCurrentSelectedIndex);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		case R.id.accountNameLayout:
			intent = new Intent(BillAccountAddActivity.this, BillTextInputActivity.class);
			title = "�༭�˻�����";
			editNum = 10;
			intent.putExtra("title", title);
			intent.putExtra("fenlei", ConstantUtil.EDIT_NAME_FINISHED);
			intent.putExtra("editNum", editNum);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		case R.id.accountBizhongLayout:
			intent = new Intent(BillAccountAddActivity.this, BillAccountBiZhongSettingActivity.class);
			intent.putExtra("currentSelectedIndex", bizhongCurrentSelectedIndex);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		case R.id.accountyueLayout:
			intent = new Intent(BillAccountAddActivity.this, BillCalculatorActivity.class);
			intent.putExtra("num", accountyueTv.getText().toString());
			intent.putExtra("resultCode", ConstantUtil.EDIT_YUEFINISHED);
			startActivityForResult(intent, 100);
			break;
		case R.id.accountIsNoticeLayout:
			//�Ƿ�������layout
			if(noticeCb.isChecked()) {
				noticeCb.setChecked(false);
				accountNoticeValueLayout.setVisibility(ViewGroup.GONE);
			}else {
				noticeCb.setChecked(true);
				accountNoticeValueLayout.setVisibility(ViewGroup.VISIBLE);
			}
			break;
		case R.id.accountNoticeValueLayout:
			intent = new Intent(BillAccountAddActivity.this, BillCalculatorActivity.class);
			intent.putExtra("num", accountNoticeValueTv.getText().toString());
			intent.putExtra("resultCode", ConstantUtil.EDIT_NOTICEVALUEFINISHED);
			startActivityForResult(intent, 100);
			break;
		case R.id.beizhu:
			intent = new Intent(BillAccountAddActivity.this, BillTextInputActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		content = data.getStringExtra("content");
		switch (resultCode) {
		case ConstantUtil.EDIT_NAME_FINISHED:
			accountNameTv.setText(content);
			break;
		case ConstantUtil.EDIT_NOTICE_FINISHED:
			break;
		case ConstantUtil.EDIT_BEIZHU:
			break;
		case ConstantUtil.EDIT_BIZHONGFINISHED:
			bizhong = data.getStringExtra("bizhong");
			bizhongCurrentSelectedIndex = data.getIntExtra("currentSelectedIndex", 0);
			accountBiZhongTv.setText(bizhong);
			break;
		case ConstantUtil.EDIT_CATAGORYNAMEFINISHED:
			catagoryName = data.getStringExtra("catagoryName");
			catagorynameCurrentSelectedIndex = data.getIntExtra("catagorynameCurrentSelectedIndex", 0);
			accountcatagorynameTv.setText(catagoryName);
			break;
		case ConstantUtil.EDIT_NOTICEVALUEFINISHED:
			accountNoticeValueTv.setText(data.getStringExtra("num"));
			break;
		case ConstantUtil.EDIT_YUEFINISHED:
			accountyueTv.setText(data.getStringExtra("num"));
			break;
		}
	}
	
	private void back() {
		BillAccountAddActivity.this.finish();
		overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
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
