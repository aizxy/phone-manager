package com.example.mytestviewpager.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.renderscript.Element.DataType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseBaseAdapter<DataType> extends BaseAdapter{
	protected Context context;
	protected LayoutInflater layoutInflater;
	private List<DataType> dataList=new ArrayList<DataType>();
	
	public BaseBaseAdapter (Context context){
		this.context=context;
		layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public List<DataType> getDataList(){
		return dataList;
	}
	public void addDataToAdapter(DataType data){
		dataList.add(data);
	}
	public void setDataToAdapter(DataType data){
		dataList.clear();
		dataList.add(data);
	}
	public void setDataToAdapter(List<DataType> datas){
		dataList.clear();
		dataList.addAll(datas);
	}
	@Override
	public int getCount() {
		return dataList==null?0:dataList.size();
	}

	@Override
	public DataType getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getItemView(position, convertView, parent);
	}
	
	public abstract View getItemView(int position,View convertView,ViewGroup parent);

}
