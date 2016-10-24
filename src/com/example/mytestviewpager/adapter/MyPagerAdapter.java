package com.example.mytestviewpager.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
/**
 * 用来承载viewPager的每一个view
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
	 * 调用Adapter中的该方法，往集合中添加页面view
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
	 * 用来判断前后两个页面是否同一个页面
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	/**
	 * 如果超出了viewPager的缓存页面，将页面销毁
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(arrList.get(position));
	}
	/**
	 * 将要缓存的view添加进入集合
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(arrList.get(position));
		return arrList.get(position);
	}
}
