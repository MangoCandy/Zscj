package com.hnxind.rollManager.Act_Contacts;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hnxind.model.Contact;
import com.hnxind.setting.Theme;
import com.hnxind.zscj.R;

import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.mViewHolder> {
    List<Contact> contacts = null;
    Context context;
    Theme theme;

    public ContactsAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        theme=new Theme(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_edit_contacts, null);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, int position) {
        final Contact contact = contacts.get(position);
        holder.name.setText(contact.getName());
        holder.num.setText(contact.getNum());
        holder.headText.setText((contact.getName().substring(0, 1)));
        holder.layout_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupWindow(contact);
            }
        });
    }

    String num;

    public void initPopupWindow(Contact contact) {//点击联系人后弹窗
        num = contact.getNum();
        AlertDialog.Builder alertDialog_builder;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_contacts, null);
        ((LinearLayout)view.findViewById(R.id.backgroud)).setBackgroundColor(theme.getMainColor());

        TextView contact_name = (TextView) view.findViewById(R.id.contact_name);//设置人名
        contact_name.setTextColor(theme.getTitleColor());

        LinearLayout call = (LinearLayout) view.findViewById(R.id.call);
        call.setOnClickListener(onClickListener);
        //跳转短信
        LinearLayout sms = (LinearLayout) view.findViewById(R.id.sms);
        sms.setOnClickListener(onClickListener);
        //复制号码
        LinearLayout copy = (LinearLayout) view.findViewById(R.id.copy);
        copy.setOnClickListener(onClickListener);
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB){
            copy.setVisibility(View.GONE);//API低于11 取消复制功能
        }

        contact_name.setText(contact.getName());
        alertDialog_builder = new AlertDialog.Builder(context);
        alertDialog_builder.setView(view);
        alertDialog_builder.show();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.call:
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + num));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {//Ȩ�޼��
                        return;
                    }
                    context.startActivity(intent);
                    break;
                case R.id.sms:
                    Intent smsitent=new Intent(Intent.ACTION_SENDTO);
                    smsitent.setData(Uri.parse("smsto:"+num));
                    context.startActivity(smsitent);
                    break;
                case R.id.copy:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                        cmb.setPrimaryClip(ClipData.newPlainText(null,num));
                        Toast.makeText(context,"号码已复制到剪切板",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView num;
        TextView headText;
        RelativeLayout layout_contacts;
        public mViewHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.single_contact_name);
            num=(TextView)itemView.findViewById(R.id.single_contact_num);
            headText=(TextView)itemView.findViewById(R.id.head_text);
            layout_contacts=(RelativeLayout)itemView.findViewById(R.id.layout_single_contact);
        }
    }
}
