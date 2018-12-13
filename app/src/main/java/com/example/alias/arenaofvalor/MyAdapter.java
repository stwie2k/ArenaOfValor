package com.example.alias.arenaofvalor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter
        implements Filterable {
    private Context mContext;
    private List<Hero> mList;
    private List<Hero> mFilteredHeros;

    public MyAdapter(Context context, List<Hero> list) {
        mContext = context;
        mList = list;
        mFilteredHeros = list;
    }

    public void setHeroes(List<Hero> List) {
        mList = List;
        mFilteredHeros = List;
    }

    public List<Hero> getOrininalList(){
        return mList;
    }

    public List<Hero> getFilteredHeroes(){
        return mFilteredHeros;
    }

    @Override
    public int getCount() {
        return mFilteredHeros.size();
    }

    @Override
    public Object getItem(int i) {
        return mFilteredHeros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.herolist, null);
            viewHolder.mName = view.findViewById(R.id.name);
            viewHolder.mPicture = view.findViewById(R.id.imageView);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.mName.setText(mFilteredHeros.get(i).getName());

        String strURL = mFilteredHeros.get(i).getImage_url();

        if (strURL.equals("")) {
            viewHolder.mPicture.setImageResource(R.drawable.me);
        } else if (strURL.contains("http")) {
            try {
                Bitmap bitmap = getBitmap(strURL);
                viewHolder.mPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            viewHolder.mPicture.setImageURI(Uri.parse(strURL));
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String position = "";
                Boolean shouldFilterPos = true;
                if (    MainActivity.sPositionToFilter == null ||
                        MainActivity.sPositionToFilter.equals("全部")) {
                    shouldFilterPos = false;
                } else {
                    position = MainActivity.sPositionToFilter;
                }

                String charString = charSequence.toString();
                Boolean shouledFilterName = charString.equals("")||charString == null?false:true;
                if (!shouldFilterPos&&!shouledFilterName) {
                    mFilteredHeros = mList;
                } else {
                    List<Hero> filteringHeros = new ArrayList<>();
                    for (Hero tmpHero : mList) {
                        if (shouldFilterPos&&shouledFilterName) {
                            if (tmpHero.getName().contains(charString)
                                    && tmpHero.getPosition().equals(position)) {
                                filteringHeros.add(tmpHero);
                            }

                        } else if(!shouldFilterPos&&shouledFilterName){
                            if (tmpHero.getName().contains(charString)) {
                                filteringHeros.add(tmpHero);
                            }
                        }
                        else if(shouldFilterPos&&!shouledFilterName){
                            if(tmpHero.getPosition().equals(position)){
                                filteringHeros.add(tmpHero);
                            }
                        }
                    }

                    mFilteredHeros = filteringHeros;
                }
                FilterResults results = new FilterResults();
                results.values = mFilteredHeros;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredHeros = (ArrayList<Hero>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder {
        TextView mName;
        ImageView mPicture;
    }

    static public Bitmap getBitmap(String path) throws IOException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}

