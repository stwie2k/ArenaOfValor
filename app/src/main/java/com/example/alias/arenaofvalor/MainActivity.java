package com.example.alias.arenaofvalor;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    HeroLab mHeroLab;
    MyAdapter adapter;
    ImageView pic;
    ImageView back;
    Uri uri;
    Uri buri;
    RadioButton rb;

    // Start work on Search and filter

    public static String sPositionToFilter;
    RadioGroup mRadioGroupForSearch;
    RadioButton mRadioButtonForSearch;
    EditText mSearchEditText;
    Button mSearchButton;

    private BroadcastReceiver dynamicReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction("dynamicFilter");    //添加动态广播的Action
        dynamicReceiver = new DynamicReceiver();
        registerReceiver(dynamicReceiver, dynamic_filter);

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        SharedPreferences sharedPref = MainActivity.this.getSharedPreferences("MY_PREFERENCE", Context.MODE_PRIVATE);
        String done = sharedPref.getString("Done", "");
        if (done.equals("")) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Done", "done");
            editor.commit();

            Hero h = new Hero("http://game.gtimg.cn/images/yxzj/img201606/heroimg/105/105.jpg", "https://game.gtimg.cn/images/yxzj/img201606/heroimg/105/105-mobileskin-1.jpg", "廉颇", "正义爆轰", "上单", 100, 30, 40, 30);
            HeroLab.get(getApplicationContext()).addHero(h);

            h = new Hero("http://game.gtimg.cn/images/yxzj/img201606/heroimg/106/106.jpg", "https://game.gtimg.cn/images/yxzj/img201606/heroimg/106/106-mobileskin-1.jpg", "小乔", "恋之微风", "中单", 20, 10, 80, 30);
            HeroLab.get(getApplicationContext()).addHero(h);

            h = new Hero("http://game.gtimg.cn/images/yxzj/img201606/heroimg/107/107.jpg", "https://game.gtimg.cn/images/yxzj/img201606/heroimg/107/107-mobileskin-1.jpg", "赵云", "苍天翔龙", "打野", 60, 60, 60, 50);
            HeroLab.get(getApplicationContext()).addHero(h);

            h = new Hero("http://game.gtimg.cn/images/yxzj/img201606/heroimg/108/108.jpg", "https://game.gtimg.cn/images/yxzj/img201606/heroimg/108/108-mobileskin-1.jpg", "墨子", "和平守望", "上单", 50, 40, 50, 60);
            HeroLab.get(getApplicationContext()).addHero(h);

            h = new Hero("http://game.gtimg.cn/images/yxzj/img201606/heroimg/109/109.jpg", "https://game.gtimg.cn/images/yxzj/img201606/heroimg/109/109-mobileskin-1.jpg", "妲己", "魅力之狐", "中单", 20, 10, 80, 20);
            HeroLab.get(getApplicationContext()).addHero(h);

            h = new Hero("http://game.gtimg.cn/images/yxzj/img201606/heroimg/110/110.jpg", "https://game.gtimg.cn/images/yxzj/img201606/heroimg/110/110-mobileskin-1.jpg", "嬴政", "王者独尊", "中单", 30, 40, 100, 60);
            HeroLab.get(getApplicationContext()).addHero(h);

            h = new Hero("http://game.gtimg.cn/images/yxzj/img201606/heroimg/111/111.jpg", "https://game.gtimg.cn/images/yxzj/img201606/heroimg/111/111-mobileskin-1.jpg", "孙尚香", "千金重弩", "ADC", 30, 80, 50, 60);
            HeroLab.get(getApplicationContext()).addHero(h);

            h = new Hero("http://game.gtimg.cn/images/yxzj/img201606/heroimg/112/112.jpg", "https://game.gtimg.cn/images/yxzj/img201606/heroimg/112/112-mobileskin-1.jpg", "鲁班七号", "机关造物", "ADC", 10, 100, 30, 40);
            HeroLab.get(getApplicationContext()).addHero(h);

            h = new Hero("http://game.gtimg.cn/images/yxzj/img201606/heroimg/113/113.jpg", "https://game.gtimg.cn/images/yxzj/img201606/heroimg/113/113-mobileskin-1.jpg", "庄周", "逍遥幻梦", "辅助", 80, 20, 40, 20);
            HeroLab.get(getApplicationContext()).addHero(h);

            h = new Hero("http://game.gtimg.cn/images/yxzj/img201606/heroimg/116/116.jpg", "https://game.gtimg.cn/images/yxzj/img201606/heroimg/116/116-mobileskin-1.jpg", "阿珂", "信念之刃", "打野", 30, 100, 40, 60);
            HeroLab.get(getApplicationContext()).addHero(h);
        }


        mHeroLab = HeroLab.get(getApplicationContext());

        adapter = new MyAdapter(MainActivity.this, mHeroLab.getHeros());
        GridView lv = findViewById(R.id.lw);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, Info.class);

                intent.putExtra("hero", adapter.getFilteredHeroes().get(position));
                startActivityForResult(intent, 1);

            }

        });

        // For Search
        mRadioGroupForSearch = (RadioGroup) findViewById(R.id.mrg1);
        mRadioGroupForSearch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mRadioButtonForSearch = (RadioButton) findViewById(mRadioGroupForSearch.getCheckedRadioButtonId());
                sPositionToFilter = mRadioButtonForSearch.getText().toString();
                String strToSearch = mSearchEditText.getText().toString();
                adapter.getFilter().filter(strToSearch);
//                Toast.makeText(MainActivity.this, sPositionToFilter + "被选中", Toast.LENGTH_SHORT).show();
            }
        });
        mSearchEditText = (EditText) findViewById(R.id.searchEditText);
        mSearchButton = (Button) findViewById(R.id.searchButton);

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        mSearchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String strToSearch = mSearchEditText.getText().toString();
//                adapter.getFilter().filter(strToSearch);
//            }
//        });


        final AlertDialog dialog1 = new AlertDialog.Builder(this).create();//创建对话框

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                dialog1.setIcon(R.mipmap.ic_launcher);//设置对话框icon

                dialog1.setTitle("删除英雄");
                dialog1.setMessage("是否删除该英雄？");//设置文字显示内容

                dialog1.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                    }
                });

                dialog1.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        heroes.remove(position);
                        mHeroLab.deleteHero(mHeroLab.getHeros().get(position));
                        adapter.setHeroes(mHeroLab.getHeros());
                        adapter.notifyDataSetChanged();

                        dialog.dismiss();//关闭对话框
                    }
                });


                dialog1.show();


                return true;
            }
        });


        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View newView = factory.inflate(R.layout.dialoglayout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                pic = newView.findViewById(R.id.picture);

                pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();

                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);

                        intent.setType("image/*");

                        startActivityForResult(intent, 0);
                    }
                });

                back = newView.findViewById(R.id.background);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();

                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);

                        intent.setType("image/*");

                        startActivityForResult(intent, 1);
                    }
                });

                final EditText name = newView.findViewById(R.id.name);
                final EditText alias = newView.findViewById(R.id.alias);

                final TextView live = newView.findViewById(R.id.live);
                final TextView attack = newView.findViewById(R.id.attack);
                final TextView skill = newView.findViewById(R.id.skill);
                final TextView difficulty = newView.findViewById(R.id.difficulty);


                final SeekBar sb1 = newView.findViewById(R.id.sb1);
                final SeekBar sb2 = newView.findViewById(R.id.sb2);
                final SeekBar sb3 = newView.findViewById(R.id.sb3);
                final SeekBar sb4 = newView.findViewById(R.id.sb4);


                sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        live.setText(String.valueOf(sb1.getProgress()));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        attack.setText(String.valueOf(sb2.getProgress()));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                sb3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        skill.setText(String.valueOf(sb3.getProgress()));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                sb4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        difficulty.setText(String.valueOf(sb4.getProgress()));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                final RadioGroup rg = newView.findViewById(R.id.rg);


                builder.setView(newView);
                builder.setTitle("添加英雄");
                builder.setPositiveButton("确认添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String _uri;
                        if (uri == null) _uri = "";
                        else _uri = uri.toString();

                        String _buri;
                        if(buri == null) _buri = "";
                        else _buri = buri.toString();

                        rb = newView.findViewById(rg.getCheckedRadioButtonId());

                        String _name = name.getText().toString();
                        String _alias = alias.getText().toString();
                        String _position = rb.getText().toString();
                        int _live = Integer.parseInt(live.getText().toString());
                        int _attack = Integer.parseInt(attack.getText().toString());
                        int _skill = Integer.parseInt(skill.getText().toString());
                        int _difficulty = Integer.parseInt(difficulty.getText().toString());
//                        Hero h = new Hero(_uri, _name, _alias, _position, _live, _attack, _skill, _difficulty);

                        // Add the background uri as the second _uri below.

                        Hero h = new Hero(_uri, _buri, _name, _alias, _position, _live, _attack, _skill, _difficulty);

                        mHeroLab.addHero(h);
                        adapter.setHeroes(mHeroLab.getHeros());
                        adapter.notifyDataSetChanged();

                        Intent intentBroadcast = new Intent();   //定义Intent
                        intentBroadcast.setAction("dynamicFilter");

                        intentBroadcast.putExtra("hero",h);

                        sendBroadcast(intentBroadcast);


                        Toast.makeText(getApplicationContext(),"添加英雄成功", Toast.LENGTH_SHORT).show();


                    }
                });
                builder.setNegativeButton("放弃添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                builder.show();

            }


        });


    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {

            if(requestCode == 0){
                uri = data.getData();
                pic.setImageURI(uri);
            }

            if(requestCode == 1){
                buri = data.getData();
                back.setImageURI(buri);
            }

        }

        super.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    protected void onResume()
    {
        super.onResume();

        adapter.setHeroes(mHeroLab.getHeros());
        adapter.notifyDataSetChanged();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dynamicReceiver);
    }



}
