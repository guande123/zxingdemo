package com.demo.zgd.zxingdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Intents;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.CaptureActivity;

public class MainActivity extends AppCompatActivity {
    private static final int SCAN_QRCODE_REQ =1002 ;
    private static final String TAG="MainActivity";
    private Button btnCreate;
    private ImageView mImageView;
    private EditText mEdtContent;
    private Button btnScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCreate = (Button) findViewById(R.id.createBtn);
        mImageView = (ImageView) findViewById(R.id.imgQr);
        mEdtContent = (EditText) findViewById(R.id.edt_content);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String content =  mEdtContent.getText().toString().trim();
                Bitmap bitmap = encodeBitmap(content);
                if(bitmap!=null){
                    mImageView.setImageBitmap(bitmap);
                }else{
                    Toast.makeText(MainActivity.this,"create QRCode failed",Toast.LENGTH_LONG).show();
                }

            }
        });
        btnScan = (Button) findViewById(R.id.scanBtn);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivityForResult(new Intent(MainActivity.this,CaptureActivity.class),SCAN_QRCODE_REQ);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           if(requestCode==SCAN_QRCODE_REQ){
              String qrCodeMode =     data.getStringExtra(Intents.Scan.QR_CODE_MODE);
              Log.i(TAG, "onActivityResult: "+qrCodeMode);
           }
    }

    private Bitmap encodeBitmap(String content) {
        Bitmap bitmap = null;
        BitMatrix bitmatrix = null;
        MultiFormatWriter writer = null;
        try {
            bitmatrix =  writer.encode(content, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder encoder = new BarcodeEncoder();
            bitmap  = encoder.createBitmap(bitmatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
// 如果不使用 ZXing Android Embedded 的话，要写的代码

//        int w = result.getWidth();
//        int h = result.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            int offset = y * w;
//            for (int x = 0; x < w; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels,0,100,0,0,w,h);


        return bitmap ;
    }
}
