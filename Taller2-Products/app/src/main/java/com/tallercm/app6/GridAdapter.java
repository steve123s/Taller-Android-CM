package com.tallercm.app6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private Product[] products;

    // 1
    public GridAdapter(MainActivity context, Product[] products) {
        this.mContext = context;
        this.products = products;
    }

    // 2
    @Override
    public int getCount() {
        return products.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        Product product = products[position];

        // 2
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.linearlayout_product, null);
        }

        // 3
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView_cover_art);

        TextView prodNumTextView = (TextView)convertView.findViewById(R.id.textView_product_num);
        TextView nameTextView = (TextView)convertView.findViewById(R.id.textView_product_name);
        TextView descTextView = (TextView)convertView.findViewById(R.id.textView_product_descr);

        // 4
        if (product.getImageUrl() == "") {
            //imageView.setImageResource(product.getImageResource()); //carga imagen default de recursos
        } else {
            Picasso.with(mContext).load(product.getImageUrl()).into(imageView); //carga imagen de la url
            prodNumTextView.setText(Integer.toString(product.getNumProd()));
            nameTextView.setText(product.getTitle() + " ");
            descTextView.setText(" ");
        }






        //descTextView.setText(product.getDescription() + " ");


        System.out.println("saliendo de gridAdapter");

        return convertView;
    }

}
