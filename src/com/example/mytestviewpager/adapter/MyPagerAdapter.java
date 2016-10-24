package com.example.mytestviewpager.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
/**
 * ��������viewPager��ÿһ��view
 * @author Administrator
 *
 */

public class MyPagerAdapter extends PagerAdapter{
	private Context context;
	private ArrayList<View> arrList=new ArrayList<View>();
	public MyPagerAdapter(Context context){
		super();
		this.context=context;
	}
	/**
	 * ����Adapter�еĸ÷����������������ҳ��view
	 * @param view
	 */
	public void addToAdapterView(View view){
		arrList.add(view);
	}
	
	@Override
	public int getCount() {
		return arrList.size();
	}
	/**
	 * �����ж�ǰ������ҳ���Ƿ�ͬһ��ҳ��
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	/**
	 * ���������viewPager�Ļ���ҳ�棬��ҳ������
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(arrList.get(position));
	}
	/**
	 * ��Ҫ�����view��ӽ��뼯��
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(arrList.get(position));
		return arrList.get(position);
	}
}
