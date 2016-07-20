package googleplay.xiaokai.com.managelife;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zys.brokenview.BrokenCallback;
import com.zys.brokenview.BrokenTouchListener;
import com.zys.brokenview.BrokenView;

import java.util.Random;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import shem.com.materiallogin.MaterialLoginView;
import shem.com.materiallogin.MaterialLoginViewListener;

public class TreeHold extends AppCompatActivity implements MaterialLoginViewListener {
    CardView cardView;
    EditText editText;
    LinearLayout liner;
    CardView displaycardview;
    ImageView imageView;
    TextView textContent;
    TextView textFrom;
    int[] images;
    String[] sententce;
    String[] distination;
    Random random;
    Toolbar toolbar;
    int position;
    ActionBar supportActionBar;
    String str;
    MyUser userInfo;
    String usern;
    int indexOf;
    MaterialLoginView materialLoginView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_hold);
        Bmob.initialize(this, "cebd6afd1aadfeb5b679422f0637e997");
        iniView();
        iniToolBar();
        userInfo = BmobUser.getCurrentUser(MyUser.class);
        random = new Random();
        BrokenView brokenView = BrokenView.add2Window(this);
        BrokenTouchListener listener = new BrokenTouchListener.Builder(brokenView)       // default 12
                // setEnableArea(view1)
                .build();
        liner.setOnTouchListener(listener);
        if(userInfo==null){
            materialLoginView.setVisibility(View.VISIBLE);
            liner.setVisibility(View.GONE);
        }

        brokenView.setCallback(new BrokenCallback() {
            @Override
            public void onStart(View v) {
                super.onStart(v);
            }

            @Override
            public void onCancelEnd(View v) {
                super.onCancelEnd(v);
            }

            @Override
            public void onFallingEnd(View v) {
                super.onFallingEnd(v);
                position = random.nextInt(15);
                str = editText.getText().toString();

                String regEx="石艳艳";
                indexOf = str.indexOf(regEx);

                if(userInfo!=null){
                    usern = (String) BmobUser.getObjectByKey("username");
                }

                Liuyan liuyan = new Liuyan();
//注意：不能调用gameScore.setObjectId("")方法
                if(!TextUtils.isEmpty(usern)&&!TextUtils.isEmpty(str)&&liuyan!=null) {
                    liuyan.setUsername(usern);
                    liuyan.setContent(str);
                    liuyan.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {

                            } else {

                            }
                        }
                    });
                }

                if(indexOf!=-1){
                    Intent intent = new Intent(TreeHold.this,LoveActivity.class);
                    startActivity(intent);
                    return;
                }

                if(TextUtils.isEmpty(editText.getText().toString())){
                    imageView.setBackgroundResource(R.drawable.laugh);
                    textContent.setText("无论无言的悲伤还是无以言表的快乐,希望你的笑容依然能够绽放!");
                    textFrom.setText("————孙晓凯");
                }else {
                    imageView.setBackgroundResource(images[position]);
                    textContent.setText(sententce[position]);
                    textFrom.setText(distination[position]);
                }

                displaycardview.setVisibility(View.VISIBLE);


            }

            @Override
            public void onFalling(View v) {
                super.onFalling(v);
            }

            @Override
            public void onRestart(View v) {
                super.onRestart(v);
            }

            @Override
            public void onCancel(View v) {
                super.onCancel(v);
            }
        });
    }

    private void iniToolBar() {
        setSupportActionBar(toolbar);
        supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle("树洞");
    }

    private void iniView() {
        images = new int[]{R.drawable.disttwo,R.drawable.disthree,
        R.drawable.disfour,R.drawable.disfive,R.drawable.dissix,R.drawable.disseven,
                R.drawable.diseight,R.drawable.disnine,R.drawable.disten,R.drawable.diseleven,
                R.drawable.distwlve,R.drawable.disthreen,R.drawable.disfourteen,R.drawable.disfiveteen,
                R.drawable.dissixteen
        };
        sententce = new String[]{
                "目的虽有，却无路可循；我们称之为路的，无非是踌躇。","曾经拥有的东西被夺走，并不代表就会回到原来没有那种东西的时候。",
                "有的风景，一生能遇见一次，就已经是莫大的幸运了。","我一直相信，这个世界上是有一些爱情如同宿命般无从逃避，不可取代，色授魂予，需以性命相拼。",
                "A stunning your time, a gentle your years.","you tell me i'm wrong， then you better prove you're right ",
                "如果可能,就把人生过成一道光怪陆离的风景。","我们终此一生，就是要摆脱他人的期待，找到真正的自己。",
                "不要为明天忧虑，因为明天自有明天的忧虑；一天的难处，一天担当就够了。",
                "知道自己知道什么，也知道自己不知道什么，这是对自己真正的了解。",
                "征服畏惧,建立自信最有效的方法就是做害怕的事情,直到获得成功的经验。",
                "世界不缺少战争,战争也不缺少柔情。",
                "很多人不理解我为什么想去西藏·内蒙古,其实,我就是感觉哪里很孤独。我知道,你还是不理解!",
                "I knew you would come, so I wait.",
                "有时候知道的少并不一定是坏事,因为你会发现你比其他人有更多的惊喜。"
        };
        distination = new String[]{"————卡夫卡 《误入世界》","————东野圭吾","————新川直司","————《怦然心动》"," ————苏剧 《经年》",
        "————Michael Jackson","————孙晓凯","————伍绮诗《无声告白》",
                "————《马太福音》","————梭罗","————《影响力》","————孙晓凯",
                "————孙晓凯","————Shencongwen","————孙晓凯"
        };
        cardView = (CardView) findViewById(R.id.cardview);
        editText = (EditText) findViewById(R.id.treetext);
        liner = (LinearLayout) findViewById(R.id.outliner);
        displaycardview = (CardView) findViewById(R.id.displaycardview);

        imageView = (ImageView) findViewById(R.id.image);
        textContent = (TextView) findViewById(R.id.textdiaplay);
        textFrom = (TextView) findViewById(R.id.textfrom);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        materialLoginView = (MaterialLoginView) findViewById(R.id.logintreehold);
        materialLoginView.setListener(this);
    }

    @Override
    public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
        signin(registerUser.getEditText().getText().toString(),registerPass.getEditText().getText().toString(),registerPassRep.getEditText().getText().toString());
    }

    @Override
    public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
        Login(loginUser.getEditText().getText().toString(),loginPass.getEditText().getText().toString());
    }

    private void Login(String name,String pass){
        BmobUser myUser = new BmobUser();
        myUser.setUsername(name);
        myUser.setPassword(pass);
        myUser.login(new SaveListener<MyUser>() {

            @Override
            public void done(MyUser bmobUser, BmobException e) {
                if(e==null){
                    materialLoginView.setVisibility(View.GONE);
                    Toast.makeText(TreeHold.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    liner.setVisibility(View.VISIBLE);

                }else{
                    Toast.makeText(TreeHold.this, "登陆失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signin(String name,String pass,String passrep) {
        BmobUser myUser = new BmobUser();
        myUser.setUsername(name);

        if(pass!=null&&pass.equals(passrep)){
            myUser.setPassword(pass);
        }

        myUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e) {
                if(e==null){
                    materialLoginView.setVisibility(View.GONE);
                    Toast.makeText(TreeHold.this, "注册成功:" +s.toString(), Toast.LENGTH_SHORT).show();
                    liner.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(TreeHold.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
