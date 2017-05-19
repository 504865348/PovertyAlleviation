package com.example.jayny.povertyalleviation.sort;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.jayny.povertyalleviation.Constant;
import com.example.jayny.povertyalleviation.PoorerListActivity;
import com.example.jayny.povertyalleviation.PoorerListDetailActivity;
import com.example.jayny.povertyalleviation.R;
import com.example.jayny.povertyalleviation.VillageInfoActivity;

import java.util.List;
import java.util.Map;

public class PoorerListAdapter extends BaseAdapter implements SectionIndexer{
	private List<Map<String,String>> list = null;
	private Activity mContext;

	public PoorerListAdapter(Activity mContext, List<Map<String,String>> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * ��ListView���ݷ����仯ʱ,���ô˷���������ListView
	 * @param list
	 */
	public void updateListView(List<Map<String,String>> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Map<String,String> getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		List<Map<String,String>> mValues = list;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.poorer_list_content, null);
			viewHolder.mIdView = (TextView) view.findViewById(R.id.id);
			viewHolder.mContentView = (TextView) view.findViewById(R.id.content);
			viewHolder.mContentType = (TextView) view.findViewById(R.id.contentType);
			viewHolder.mPoorList= (LinearLayout) view.findViewById(R.id.ll_poor_list);
			viewHolder.tvLetter= (TextView) view.findViewById(R.id.catalog);
			viewHolder.mView = view;
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.mItem = mValues.get(position);
		viewHolder.mIdView.setText(String.valueOf(position));
		viewHolder.mContentView.setText(mValues.get(position).get("name"));
		viewHolder.mContentType.setText(mValues.get(position).get("poorType"));
		final ViewHolder finalViewHolder = viewHolder;
		viewHolder.mView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				Intent intent = new Intent(context, PoorerListDetailActivity.class);
				intent.putExtra("name",finalViewHolder.mContentView.getText());
				intent.putExtra("statusForDocs", "1");
				intent.putExtra("status3",null==mContext.getIntent().getStringExtra("status3")?"0":mContext.getIntent().getStringExtra("status3"));
				intent.putExtra("status2", finalViewHolder.mItem.get("status2"));
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				Constant.aid = finalViewHolder.mItem.get("aid");
				context.startActivity(intent);
			}
		});
		view.setTag(viewHolder);
		//����position��ȡ���������ĸ��Char asciiֵ
		int section = getSectionForPosition(position);
		
		//�����ǰλ�õ��ڸ÷�������ĸ��Char��λ�� ������Ϊ�ǵ�һ�γ���
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mValues.get(position).get("sort"));
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
	

		return view;

	}
	


	final static class ViewHolder {
		TextView tvLetter;//�����ǰλ�õ��ڸ÷�������ĸ��Char��λ�� ������Ϊ�ǵ�һ�γ���
		View mView;
		TextView mIdView;
		TextView mContentView;
		TextView mContentType;
		LinearLayout mPoorList;
		Map<String, String> mItem;
	}


	/**
	 * ����ListView�ĵ�ǰλ�û�ȡ���������ĸ��Char asciiֵ
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).get("sort").charAt(0);
	}

	/**
	 * ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ��
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).get("sort");
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * ��ȡӢ�ĵ�����ĸ����Ӣ����ĸ��#���档
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}