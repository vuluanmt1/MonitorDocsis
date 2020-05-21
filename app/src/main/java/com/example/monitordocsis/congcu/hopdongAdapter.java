package com.example.monitordocsis.congcu;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monitordocsis.R;

import java.util.List;

public class hopdongAdapter extends BaseAdapter {
    private List<hopdongModel>mList;
    Context context;

    public hopdongAdapter(List<hopdongModel> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view ;
        if(convertView==null){
            convertView= View.inflate(parent.getContext(), R.layout.contract_dialog,null);
        }else {
            view=convertView;
        }
        TextView txt_nameCustomer = convertView.findViewById(R.id.txt_tenkh);
        TextView txt_branch = convertView.findViewById(R.id.txt_chinhanh);
        TextView txt_phone = convertView.findViewById(R.id.txt_sodt);
        TextView txt_address = convertView.findViewById(R.id.txt_diachi);
        TextView txt_typeContract = convertView.findViewById(R.id.txt_loai_hd);
        TextView txt_statusContract = convertView.findViewById(R.id.txt_tinhtrang);
        TextView txt_subnum = convertView.findViewById(R.id.txt_mahd);

        txt_nameCustomer.setText(mList.get(position).getNameCustomer());
        txt_branch.setText(mList.get(position).getBranch());
        txt_phone.setText(mList.get(position).getPhone());
        txt_address.setText(mList.get(position).getAddress());
        txt_typeContract.setText(mList.get(position).getTypeContract());
        txt_statusContract.setText(mList.get(position).getStatusContract());
        txt_subnum.setText(mList.get(position).getSubnum());
//        final ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//        txt_phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String txt_phone = mList.get(position).getPhone();
//                ClipData clip = ClipData.newPlainText("Copy Phone:", txt_phone);
//                if (clipboard == null) return;
//                clipboard.setPrimaryClip(clip);
//                Toast.makeText(context, "Copy Phone:"+txt_phone,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        txt_subnum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String txt_subnum = mList.get(position).getSubnum();
//                ClipData clip = ClipData.newPlainText("Copy Mã HĐ:", txt_subnum);
//                if (clipboard == null) return;
//                clipboard.setPrimaryClip(clip);
//                Toast.makeText(context, "Copy Mã HĐ:"+txt_subnum,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
        return convertView;
    }
}
