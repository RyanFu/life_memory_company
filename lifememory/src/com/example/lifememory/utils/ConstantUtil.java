package com.example.lifememory.utils;

import java.util.List;

import com.example.lifememory.R;

public class ConstantUtil {

	/**
	 * ���ڼ���ģ���е�startActivityForResult�е�resultCode����
	 */
	public final static int EDIT_NAME_FINISHED = 99; // ���Ʊ༭���
	public final static int EDIT_NOTICE_FINISHED = 98; // �����߱༭���
	public final static int EDIT_BEIZHU = 97; // ��ע�༭���
	public final static int EDIT_BIZHONGFINISHED = 96; // ����ѡ�����
	public final static int EDIT_CATAGORYNAMEFINISHED = 95; // �������ѡ�����
	public final static int EDIT_NOTICEVALUEFINISHED = 94; // �����߽���������
	public final static int EDIT_YUEFINISHED = 93; // ��������������
	public final static int EDIT_NOCHANGE_BACK = 10; // ֱ�ӵ��back��������Ϣ

	/**
	 * BillAccountBiZhongSettingActivity �����˻�����
	 */
	public final static String[] BILL_ACCOUNT_BIZHONG_NAMES1 = { "�����", "��Ԫ",
			"ŷԪ", "�Ĵ�����Ԫ", "�������Ƕ�", "���ô�Ԫ", "�������", "��ʿ����", "Ӣ��", "��Ԫ", "��Ԫ",
			"��Ԫ", "����Ԫ", "���������ּ���", "������Ԫ", "Ų������", "���ɱ�����", "�¼���Ԫ", "������",
			"��̨��", "̩��", "˹������¬��" };

	public final static String[] BILL_ACCOUNT_BIZHONG_NAMES2 = { "CNY", "USD",
			"EUR", "AUD", "BRL", "CAD", "DKK", "CHF", "GBP", "HKD", "JPY",
			"KRW", "MOP", "MYR", "NZD", "NOK", "PHP", "SGD", "SEK", "TWD",
			"THB", "LKR"

	};

	public final static int[] BILL_ACCOUNT_BIZHONG_IMAGEIDS = {
			R.drawable.currency_icon_cny, R.drawable.currency_icon_usd,
			R.drawable.currency_icon_eur, R.drawable.currency_icon_aud,
			R.drawable.currency_icon_brl, R.drawable.currency_icon_cad,
			R.drawable.currency_icon_dkk, R.drawable.currency_icon_chf,
			R.drawable.currency_icon_gbp, R.drawable.currency_icon_hkd,
			R.drawable.currency_icon_jpy, R.drawable.currency_icon_krw,
			R.drawable.currency_icon_mop, R.drawable.currency_icon_myr,
			R.drawable.currency_icon_nzd, R.drawable.currency_icon_nok,
			R.drawable.currency_icon_php, R.drawable.currency_icon_sgd,
			R.drawable.currency_icon_sek, R.drawable.currency_icon_twd,
			R.drawable.currency_icon_thb, R.drawable.currency_icon_lkr };
}