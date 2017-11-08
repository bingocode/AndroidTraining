package com.whu.bingo.androidtraining.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.whu.bingo.androidtraining.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    static final int  REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    EditText editText;
    ImageView mimageView;
    VideoView mVideoView;
    String mCurrentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //通过inflater对象将自己写的资源文件转换成menu对象
        //参数1代表需要创建的菜单，参数2代表将菜单设置到对应的menu上
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gl_bt:
                startActivity(new Intent(this,OpenGLES20Activity.class));
                break;
            case R.id.delete_item:
                Toast.makeText(this, "del", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.editText);
        mimageView = (ImageView) findViewById(R.id.imageView);
        mVideoView = (VideoView) findViewById(R.id.videoView);
    }


    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showfragment(View view) {
        Intent intent = new Intent(this, FragmentTestActivity.class);
        startActivity(intent);
    }

    public void changephoto(View view){
//        简单的拍照并返回缩略图
//        Intent tackPictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(tackPictureIntent.resolveActivity(getPackageManager()) != null){
//            startActivityForResult(tackPictureIntent,REQUEST_IMAGE_CAPTURE);
//        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "创建相片文件错误", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.whu.bingo.androidtraining.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void takevidoe(View view){
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);//放在私有存储目录，其他应用不可见
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mimageView.getWidth();
        int targetH = mimageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mimageView.setImageBitmap(bitmap);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void doPhotoPrint() { //打印图片（会自动调用打印程序）
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        mimageView.setDrawingCacheEnabled(true);
        Bitmap bitmap=mimageView.getDrawingCache();
        mimageView.setDrawingCacheEnabled(false);

        photoPrinter.printBitmap("droids.jpg - test print", bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if (requestCode == REQUEST_TAKE_PHOTO ) {
//          方式一：获取拍照后android系统压缩后的缩略图设置到mimageView中
//             Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mimageView.setImageBitmap(imageBitmap);
            /*方式二：从文件中读取图片并缩放到目标视图尺寸

             */
                setPic();
                // galleryAddPic();如果将图片存储在getExternalFilesDir()提供的目录中，Media Scanner将无法访问到我们的文件，因为它们隶属于应用的私有数据()
            }
            if (requestCode == REQUEST_VIDEO_CAPTURE) {
                Uri videoUri = data.getData();
                mVideoView.setVideoURI(videoUri);
            }
        }

    }
}