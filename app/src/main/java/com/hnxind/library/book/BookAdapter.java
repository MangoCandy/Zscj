package com.hnxind.library.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hnxind.model.Book;
import com.hnxind.zscj.R;

import java.util.List;

/**
 * Created by Administrator on 2015/12/28.
 */
public class BookAdapter extends BaseAdapter {
    List<Book> books;
    Context context;
    public  BookAdapter(List<Book> books, Context context){
        this.books=books;
        this.context=context;
    }
    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Book book=books.get(position);
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.single_book,null);
            viewHolder=new ViewHolder();
            viewHolder.title=(TextView)convertView.findViewById(R.id.title);
            viewHolder.author=(TextView)convertView.findViewById(R.id.author);
            viewHolder.publisher=(TextView)convertView.findViewById(R.id.publisher);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(book.getTitle());
        viewHolder.author.setText(book.getAuthor());
        viewHolder.publisher.setText(book.getPublisher());
        return convertView;
    }

    class ViewHolder{
        TextView title;
        TextView author;
        TextView publisher;
    }
}
