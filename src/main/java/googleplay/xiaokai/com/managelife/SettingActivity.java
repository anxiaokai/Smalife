package googleplay.xiaokai.com.managelife;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.materialdialog.MaterialDialog;
import shem.com.materiallogin.MaterialLoginView;
import shem.com.materiallogin.MaterialLoginViewListener;

public class SettingActivity extends AppCompatActivity implements MaterialLoginViewListener, View.OnClickListener {
    private MaterialLoginView materialLoginView;
    private Toolbar toolbar;
    ActionBar supportActionBar;
    CircleImageView circleImageView;
    TextView username;
    TextView sign;
    TextView blog;
    String usern;
    String qian;
    String blogadd;
    LinearLayout liner;
    MaterialDialog mMaterialDialog;
    Button button;
    LinearLayout selemenu;
    Button button1;
    Button button2;
    String picturepath;
    //    EditText editname;
//    EditText editsign;
//    EditText editblog;
    public final static int ALBUM_REQUEST_CODE = 1;
    public final static int CROP_REQUEST = 2;
    public final static int CAMERA_REQUEST_CODE = 3;
    // 拍照路径
    public static String SAVED_IMAGE_DIR_PATH =
            Environment.getExternalStorageDirectory().getPath()
                    + "/managelife/camera/";
    String cameraPath;
    int px = (int)(50*16.0f+0.5f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Bmob.initialize(this, "cebd6afd1aadfeb5b679422f0637e997");
        iniView();
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        if (userInfo == null) {
            materialLoginView.setVisibility(View.VISIBLE);
            liner.setVisibility(View.GONE);
        } else {
            materialLoginView.setVisibility(View.GONE);
            liner.setVisibility(View.VISIBLE);

            usern = (String) BmobUser.getObjectByKey("username");
//MyUser中的扩展属性
            qian = (String) BmobUser.getObjectByKey("qianming");

            blogadd = (String) BmobUser.getObjectByKey("blog");

            picturepath = (String) BmobUser.getObjectByKey("picpath");

            if (!TextUtils.isEmpty(usern)) {
                username.setText(usern);
            }
            if (!TextUtils.isEmpty(qian)) {
                sign.setText(qian);
            }
            if (!TextUtils.isEmpty(blogadd)) {
                blog.setText(blogadd);
            }
            if(!TextUtils.isEmpty(picturepath)){
               circleImageView.setImageBitmap(getScaleBitmap(picturepath,px,px));
            }
        }

    }

    private void iniView() {
        materialLoginView = (MaterialLoginView) findViewById(R.id.login);
        selemenu = (LinearLayout) findViewById(R.id.selectmenu);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        username = (TextView) findViewById(R.id.name);
        sign = (TextView) findViewById(R.id.signcontent);
        blog = (TextView) findViewById(R.id.blogcontent);
        liner = (LinearLayout) findViewById(R.id.setting);
        button = (Button) findViewById(R.id.signout);
        button1 = (Button) findViewById(R.id.but1);
        button2 = (Button) findViewById(R.id.but2);
//        editsign = (EditText) findViewById(R.id.edittextsign);
//        editblog = (EditText) findViewById(R.id.edittextblog);

        circleImageView.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        username.setOnClickListener(this);
        sign.setOnClickListener(this);
        blog.setOnClickListener(this);
        materialLoginView.setListener(this);
        button.setOnClickListener(this);
        selemenu.setOnClickListener(this);
        setSupportActionBar(toolbar);
        supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("设置");
        supportActionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void Login(String name, String pass) {
        BmobUser myUser = new BmobUser();
        myUser.setUsername(name);
        myUser.setPassword(pass);
        myUser.login(new SaveListener<MyUser>() {

            @Override
            public void done(MyUser bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(SettingActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    //通过BmobUser user = BmobUser.getCurrentUser(context)获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(context,MyUser.class)获取自定义用户信息
                    materialLoginView.setVisibility(View.GONE);
                    liner.setVisibility(View.VISIBLE);

                    usern = (String) BmobUser.getObjectByKey("username");
//MyUser中的扩展属性
                    qian = (String) BmobUser.getObjectByKey("qianming");

                    blogadd = (String) BmobUser.getObjectByKey("blog");

                    if (usern != null) {
                        username.setText(usern);
                    }
                    if (qian != null) {
                        sign.setText(qian);
                    }
                    if (blogadd != null) {
                        blog.setText(blogadd);
                    }
                } else {
                    Toast.makeText(SettingActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signin(String name, String pass, String passrep) {
        BmobUser myUser = new BmobUser();
        myUser.setUsername(name);

        if (pass != null && pass.equals(passrep)) {
            myUser.setPassword(pass);
        }

        myUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e) {
                if (e == null) {
                    materialLoginView.setVisibility(View.GONE);
                    liner.setVisibility(View.VISIBLE);

                    usern = (String) BmobUser.getObjectByKey("username");
//MyUser中的扩展属性
                    qian = (String) BmobUser.getObjectByKey("qianming");

                    blogadd = (String) BmobUser.getObjectByKey("blog");

                    if (usern != null) {
                        username.setText(usern);
                    }
                    if (qian != null) {
                        sign.setText(qian);
                    }
                    if (blogadd != null) {
                        blog.setText(blogadd);
                    }
                    Toast.makeText(SettingActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
        signin(registerUser.getEditText().getText().toString(), registerPass.getEditText().getText().toString(), registerPassRep.getEditText().getText().toString());
    }

    @Override
    public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
        Login(loginUser.getEditText().getText().toString(), loginPass.getEditText().getText().toString());
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        BmobUser.logOut();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name:
                showNameDialog();
                break;
            case R.id.signcontent:
                showSignDialog();
                break;
            case R.id.blogcontent:
                showBlogDialog();
                break;
            case R.id.signout:
                showSignOutDialog();
                break;
            case R.id.profile_image:
                selemenu.setVisibility(View.VISIBLE);
                break;
            case R.id.but1:
                takePhonto();
                selemenu.setVisibility(View.GONE);
                break;
            case R.id.but2:
                photo();
                selemenu.setVisibility(View.GONE);
                break;
            case R.id.selectmenu:
                selemenu.setVisibility(View.GONE);
                break;
        }
    }

    private void photo() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, ALBUM_REQUEST_CODE);
    }

    private void takePhonto() {
        // 指定相机拍摄照片保存地址
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            cameraPath = SAVED_IMAGE_DIR_PATH +
                    System.currentTimeMillis() + ".png";
            Intent intent = new Intent();
            // 指定开启系统相机的Action
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            String out_file_path = SAVED_IMAGE_DIR_PATH;
            File dir = new File(out_file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 把文件地址转换成Uri格式
            Uri uri = Uri.fromFile(new File(cameraPath));
            // 设置系统相机拍摄照片完成后图片文件的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void showNameDialog() {
        final EditText editTextname = new EditText(this);
        editTextname.setHint("修改用户名");
        mMaterialDialog = new MaterialDialog(this)
                .setView(editTextname)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BmobUser newUser = new BmobUser();
                        Toast.makeText(SettingActivity.this, editTextname.getText().toString(), Toast.LENGTH_SHORT).show();
                        newUser.setUsername(editTextname.getText().toString());
                        BmobUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
                        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    username.setText(editTextname.getText().toString());
                                    //Toast.makeText(SettingActivity.this, "更新成功!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SettingActivity.this, "请重新登陆再试!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    private void showSignDialog() {
        final EditText editTextsign = new EditText(this);
        editTextsign.setHint("修改签名");
        mMaterialDialog = new MaterialDialog(this)
                .setView(editTextsign)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUser newUser = new MyUser();
                        Toast.makeText(SettingActivity.this, editTextsign.getText().toString(), Toast.LENGTH_SHORT).show();
                        newUser.setQianming(editTextsign.getText().toString());
                        BmobUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
                        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    sign.setText(editTextsign.getText().toString());
                                    //Toast.makeText(SettingActivity.this, "更新成功!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SettingActivity.this, "请重新登陆再试!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    private void showBlogDialog() {
        final EditText editTextblog = new EditText(this);
        editTextblog.setHint("修改博客地址");
        mMaterialDialog = new MaterialDialog(this)
                .setView(editTextblog)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUser newUser = new MyUser();
                        Toast.makeText(SettingActivity.this, editTextblog.getText().toString(), Toast.LENGTH_SHORT).show();
                        newUser.setBlog(editTextblog.getText().toString());
                        BmobUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
                        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    blog.setText(editTextblog.getText().toString());
                                    //Toast.makeText(SettingActivity.this, "更新成功!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SettingActivity.this, "请重新登陆再试!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    private void showSignOutDialog() {
        mMaterialDialog = new MaterialDialog(this)
                .setTitle("退出登录!")
                .setMessage("确定要退出吗?")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BmobUser.logOut();
                        materialLoginView.setVisibility(View.VISIBLE);
                        liner.setVisibility(View.GONE);
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    @Override

    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
               // Log.i("flag", "path=" + cameraPath);
                Bitmap bitmap = BitmapFactory.decodeFile(cameraPath);
                circleImageView.setImageBitmap(getScaleBitmap(cameraPath,px,px));

                upDate(cameraPath);
            }
            if (requestCode == this.ALBUM_REQUEST_CODE) {
                try {

                    Uri uri = data.getData();
                    final String absolutePath=
                            getAbsolutePath(this, uri);

                    //Log.i("flag","path=" + absolutePath);
                    Bitmap bitmap = BitmapFactory.decodeFile(absolutePath);
                    circleImageView.setImageBitmap(getScaleBitmap(absolutePath,px,px));
                    upDate(absolutePath);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }
    }

    private void upDate(String cameraPath) {
        MyUser newUser = new MyUser();
        //Toast.makeText(SettingActivity.this, editTextname.getText().toString(), Toast.LENGTH_SHORT).show();
        newUser.setPicpath(cameraPath);
        BmobUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //username.setText(editTextname.getText().toString());
                    Toast.makeText(SettingActivity.this, "更新成功!", Toast.LENGTH_SHORT).show();
                } else {
                    //Log.i("flag","失败"+e.toString());
                }
            }
        });
    }

    public String getAbsolutePath(final Context context,
                                  final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA},
                    null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(
                            MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }     return data;
    }

    /*
     缩小图片
      */
    private Bitmap getScaleBitmap(String path,int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);

        options.inSampleSize = calculatInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path,options);
    }

    private int calculatInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if(width>reqWidth||height>reqHeight){
            int halfWidth = width/2;
            int halfHeight = height/2;

            while ((halfHeight/inSampleSize)>=reqHeight&&(halfWidth/inSampleSize)>=reqWidth){
                inSampleSize = inSampleSize * 2;
            }
        }

        return inSampleSize;
    }


}
