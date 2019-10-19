package com.example.prectify;


import android.app.ProgressDialog;


import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.BundleCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;

import java.util.Calendar;


import static android.media.MediaRecorder.VideoSource.CAMERA;

public class RaiseQuery extends AppCompatActivity {
    Button btnsubmit;
    Button btnSelectImage;
    ImageView upload;
    EditText description;
    Uri uri;
    String imageUrl;
    private static final String IMAGE_DIRECTORY = "/prectify camera";
    ProgressDialog progressDialog;
    FirebaseAuth mauth;
    int trace=0;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_raise_query );
        upload=(ImageView)findViewById(R.id.imageView2);
        btnSelectImage=findViewById( R.id.button6);
        btnsubmit=findViewById( R.id.button3 );
        mauth= FirebaseAuth.getInstance();
        description=(EditText)findViewById(R.id.editText5);
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Data uploading...");


    }
    public void btnSelectImage(View view){

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("To add Image");
        final String [] pictureDialogItems={"Open Camera","Choose from Device"};
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(pictureDialogItems[which].equals("Open Camera")) {
                    takePhotoFromCamera();
                }
                else  if(pictureDialogItems[which].equals("Choose from Device")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
            }
        });
        pictureDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if ((requestCode == 2)){
                uri = data.getData();
                upload.setImageURI(uri);
                Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
            }
            else if ((requestCode == 1)){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                upload.setImageBitmap(thumbnail);
                uri=Uri.fromFile(new File(saveImage(thumbnail)));


            }
        }

        else{
            Toast.makeText(this, "You haven't picked an image", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadimage(){

        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Upload").child(uri.getLastPathSegment());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Description Uploading....");
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage=uriTask.getResult();
                imageUrl=urlImage.toString();
                uploadDes();
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RaiseQuery.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i;
        i=new Intent(RaiseQuery.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void btnUploadImage(View view) {

        progressDialog.show();
        uploadimage();
        Intent intent;
        intent = new Intent( RaiseQuery.this , MainActivity.class );
        startActivity(intent);
        finish();
    }
    public void uploadDes(){
        Intent c =getIntent();
        String qtype = c.getStringExtra("query_type");
        UserData userData=new UserData(
                description.getText().toString(),
                imageUrl,qtype,mauth.getUid(),"Unseen",trace
        );
        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Description")
                .child(myCurrentDateTime).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(RaiseQuery.this, "Description uploaded", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RaiseQuery.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}
