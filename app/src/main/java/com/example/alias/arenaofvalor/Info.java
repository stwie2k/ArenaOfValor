package com.example.alias.arenaofvalor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

import static com.example.alias.arenaofvalor.MyAdapter.getBitmap;

public class Info extends AppCompatActivity {

    Hero hero;

    HeroLab mHeroLab;

    CircleProgressView liveAbility;
    CircleProgressView attackAbility;
    CircleProgressView magicAbility;
    CircleProgressView operateAbility;

    /*
    改颜色的话用setColor
     */

    int liveNum,attackNum,magicNum,operateNum;

    Button nodifyButton;

    TextView positionText;
    TextView heroAlias;
    TextView heroName;
    FloatingActionButton fb;
    ImageView icon;

    ImageView pic;
    ImageView back;
    Uri uri;
    Uri buri;
    RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mHeroLab=HeroLab.get(getApplicationContext());

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());


        final Intent intent = getIntent();
        hero = (Hero) intent.getSerializableExtra("hero");

        liveNum = attackNum = magicNum = operateNum = 0;

        liveAbility = findViewById(R.id.circleProgressView);
        attackAbility = findViewById(R.id.circleProgressView2);
        magicAbility = findViewById(R.id.circleProgressView3);
        operateAbility = findViewById(R.id.circleProgressView4);

        nodifyButton = findViewById(R.id.shareButton);

        positionText = findViewById(R.id.positionText);
        heroAlias = findViewById(R.id.heroAlias);
        heroName = findViewById(R.id.heroName);

        icon = findViewById(R.id.imageView);
         fb=findViewById(R.id.modify);
        /*
        初始设置的代码
         */

        String strURL = hero.getBackground_url();
        if(strURL.equals("")){
            icon.setImageResource(R.drawable.defaultpost);
        }
        else if(strURL.contains("http")){
            try {
                Bitmap bitmap = getBitmap(strURL);
                icon.setImageBitmap(bitmap);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else{
            icon.setImageURI(Uri.parse(strURL));
        }

        WindowManager wm = (WindowManager) this.getSystemService(this.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        icon.setMinimumHeight(width/600*410);

        positionText.setText(hero.getPosition());
        heroName.setText(hero.getName());
        heroName.bringToFront();
        heroAlias.setText(hero.getAlias());
        heroAlias.bringToFront();
        positionText.bringToFront();
        nodifyButton.bringToFront();


        setAttackAbility(hero.getAttack());
        setLiveAbility(hero.getLive());
        setMagicAbility(hero.getSkill());
        setOperateAbility(hero.getDifficulty());

        nodifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String heroShareMessage =
                        "英雄名字: " + hero.getName() + "\n"
                                +   "英雄别名: " + hero.getAlias() + "\n"
                                +   "英雄位置: " + hero.getPosition() + "\n"
                                +   "属性值:   " + hero.getAttack() + "/"
                                + hero.getDifficulty() + "/"
                                + hero.getLive() + "/"
                                + hero.getSkill() ;


                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, heroShareMessage);
                i.putExtra(Intent.EXTRA_SUBJECT,
                        "分享英雄");
                //Always choose an activity to launch.
                i = Intent.createChooser(i, "请选择应用分享");
                startActivity(i);
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(Info.this);
                final View newView = factory.inflate(R.layout.dialoglayout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Info.this);

                pic = newView.findViewById(R.id.picture);

                String strURL = hero.getImage_url();

                if (strURL.equals("")) {

                } else if (strURL.contains("http")) {
                    try {
                        Bitmap bitmap = getBitmap(strURL);
                        pic.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    pic.setImageURI(Uri.parse(strURL));
                }




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


                String baURL = hero.getBackground_url();

                if (baURL.equals("")) {

                } else if (baURL.contains("http")) {
                    try {
                        Bitmap bitmap = getBitmap(baURL);
                        back.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    back.setImageURI(Uri.parse(baURL));
                }

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

              name.setText(hero.getName());
              alias.setText(hero.getAlias());


              sb1.setProgress(hero.getLive());
                sb2.setProgress(hero.getAttack());
                sb3.setProgress(hero.getSkill());
                sb4.setProgress(hero.getDifficulty());

                live.setText(String.valueOf(hero.getLive()));
                attack.setText(String.valueOf(hero.getAttack()));
                skill.setText(String.valueOf(hero.getSkill()));
                difficulty.setText(String.valueOf(hero.getDifficulty()));


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

                switch (hero.getPosition())
                {
                    case "上单" :
                        rg.check(R.id.rb1);
                        break;
                    case "中单" :
                        rg.check(R.id.rb2);
                        break;
                    case "辅助" :
                        rg.check(R.id.rb3);
                        break;
                    case "打野" :
                        rg.check(R.id.rb4);
                        break;
                    case "ADC" :
                        rg.check(R.id.rb5);
                        break;

                    default:
                        break;

                }




                builder.setView(newView);
                builder.setTitle("修改英雄");
                builder.setPositiveButton("确认修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String _uri;
                        if (uri == null) _uri = hero.getImage_url();
                        else _uri = uri.toString();

                        String _buri;
                        if(buri == null) _buri = hero.getBackground_url();
                        else _buri = buri.toString();

                        rb = newView.findViewById(rg.getCheckedRadioButtonId());

                        String _name = name.getText().toString();
                        String _alias = alias.getText().toString();
                        String _position = rb.getText().toString();
                        int _live = Integer.parseInt(live.getText().toString());
                        int _attack = Integer.parseInt(attack.getText().toString());
                        int _skill = Integer.parseInt(skill.getText().toString());
                        int _difficulty = Integer.parseInt(difficulty.getText().toString());

                        hero.setName(_name);
                        hero.setAlias(_alias);
                        hero.setPosition(_position);
                        hero.setLive(_live);
                        hero.setAttack(_attack);
                        hero.setSkill(_skill);
                        hero.setDifficulty(_difficulty);

                        hero.setImage_url(_uri);
                        hero.setBackground_url(_buri);


                        mHeroLab.updateHero(hero);


                        setAttackAbility(hero.getAttack());
                        setLiveAbility(hero.getLive());
                        setMagicAbility(hero.getSkill());
                        setOperateAbility(hero.getDifficulty());

                        positionText.setText(hero.getPosition());
                        heroName.setText(hero.getName());
                        heroName.bringToFront();
                        heroAlias.setText(hero.getAlias());
                        heroAlias.bringToFront();
                        positionText.bringToFront();
                        nodifyButton.bringToFront();

                        String strURL = hero.getBackground_url();
                        if(strURL.equals("")){
                            icon.setImageResource(R.drawable.defaultpost);
                        }
                        else if(strURL.contains("http")){
                            try {
                                Bitmap bitmap = getBitmap(strURL);
                                icon.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        else{
                            icon.setImageURI(Uri.parse(strURL));
                        }


                    }
                });
                builder.setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                builder.show();
            }
        });

    }

    public void setPosition(String s){
        positionText.setText(s);
    }

    public void setLiveAbility(int x) {
        liveAbility.setColor(Color.argb(255,250, 128 ,114));
        liveAbility.setCurrent(x);
        liveNum = x;
    }

    public void setAttackAbility(int x) {
        attackAbility.setColor(Color.argb(255,255, 215 ,0));
        attackAbility.setCurrent(x);
        attackNum = x;
    }

    public void setMagicAbility(int x) {
        magicAbility.setColor(Color.argb(255,135, 206 ,250));
        magicAbility.setCurrent(x);
        magicNum = x;
    }

    public void setOperateAbility(int x) {
        operateAbility.setColor(Color.argb(255,144, 238 ,144));
        operateAbility.setCurrent(x);
        operateNum = x;
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


}
