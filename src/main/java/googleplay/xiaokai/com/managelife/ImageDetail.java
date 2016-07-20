package googleplay.xiaokai.com.managelife;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

public class ImageDetail extends AppCompatActivity {
    private ImageView image;
    private Intent intent;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        image = (ImageView) findViewById(R.id.imagedetail);

        intent = getIntent();
        path = intent.getStringExtra("imagepath");
        if(!TextUtils.isEmpty(path)) {
            image.setImageBitmap(BitmapFactory.decodeFile(path));
        }else{
            image.setImageResource(R.drawable.touxiang);
        }
    }
}
