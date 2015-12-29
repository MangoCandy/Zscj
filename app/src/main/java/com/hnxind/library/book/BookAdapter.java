package com.hnxind.library.book;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    List<Book> books;
    Context context;
    public BookAdapter(List<Book> books,Context context){
        this.books=books;
        this.context=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_book,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book=books.get(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.publisher.setText(book.getPublisher());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView author;
        TextView publisher;

        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            author=(TextView)itemView.findViewById(R.id.author);
            publisher=(TextView)itemView.findViewById(R.id.publisher);
        }
    }
}
