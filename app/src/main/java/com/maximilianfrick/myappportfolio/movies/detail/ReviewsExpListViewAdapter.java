package com.maximilianfrick.myappportfolio.movies.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.movies.models.Review;

import java.util.Collections;
import java.util.List;


public class ReviewsExpListViewAdapter extends BaseExpandableListAdapter {

    private List<Review> reviews = Collections.emptyList();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return reviews.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return reviews.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return reviews.get(groupPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exp_list_group, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.exp_list_title);
        title.setText(reviews.get(groupPosition).getAuthor());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exp_list_item, parent, false);
        }
        TextView content = (TextView) convertView.findViewById(R.id.exp_list_item);
        content.setText(reviews.get(groupPosition).getContent());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
