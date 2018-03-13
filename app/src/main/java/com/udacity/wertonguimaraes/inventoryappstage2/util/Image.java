package com.udacity.wertonguimaraes.inventoryappstage2.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wertonguimaraes on 13/03/18.
 */

public class Image {

    private static final String DIRECTORY = "imageDir";
    private static final long MAX_SIZE_IMAGE = 4 * 1024 * 1024;  //4MB
    private Context mContext;

    public Image(Context context) {
        mContext = context;
    }

    private File getDirectory() {
        ContextWrapper cw = new ContextWrapper(mContext);
        return cw.getDir(DIRECTORY, Context.MODE_PRIVATE);
    }

    public String saveToInternalStorage(String name, Bitmap bitmapImage) {
        File directory = getDirectory();
        File mypath = new File(directory, name + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public void loadImageFromStorage(String name, ImageView imageView) {
        try {
            File file = new File(getDirectory(), name + ".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
            imageView.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteImageFromStorage(String name) {
        File file = new File(getDirectory(), name + ".jpg");
        if (file.exists()) {
            file.delete();
        }
    }

    public boolean isBigImage(Bitmap imageBitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        return imageInByte.length > MAX_SIZE_IMAGE;
    }
}
