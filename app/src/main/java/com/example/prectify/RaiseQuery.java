package com.example.prectify;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import static android.media.MediaRecorder.VideoSource.CAMERA;
import static android.media.MediaRecorder.VideoSource.DEFAULT;

public class RaiseQuery extends AppCompatActivity {
    Button btnsubmit;
    Button btnSelectImage;
    ImageView upload;
    EditText description;
    Uri uri;
    TextView abc ;
    String imageUrl;
    int trace=0;
    String qtype;
    private static final String IMAGE_DIRECTORY = "/Prectify camera";
   // String status="Unseen";
    ProgressDialog progressDialog;
    FirebaseAuth mauth;
    public static final int CAMERA_REQUEST=9999;
    static int post=0;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_raise_query );
        upload=(ImageView)findViewById(R.id.imageView2);
        btnSelectImage=findViewById( R.id.button6);
        btnsubmit=findViewById( R.id.button3 );
        mauth= FirebaseAuth.getInstance();
        abc=findViewById(R.id.type);
        description=(EditText)findViewById(R.id.editText5);
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Data uploading...");
        Intent c =getIntent();

        qtype = c.getStringExtra("query_type");
        abc.setText(qtype);


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
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
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
        i=new Intent(RaiseQuery.this,QueryType.class);
        startActivity(i);
        finish();
    }

    public void btnUploadImage(View view) {
        if(description.getText().toString().equals("")){
            description.setError("This Field Cannot be Empty");
        }
        else if(uri==null){
            Toast.makeText(RaiseQuery.this, "No Image selected", Toast.LENGTH_SHORT).show();
        }
        else{
        progressDialog.show();
        uploadimage();
        Intent intent;
        intent = new Intent( RaiseQuery.this , MainActivity.class );
        startActivity(intent);
        finish();
        Toast.makeText(RaiseQuery.this, "Upload succesfull \n Displaying your Query may take some Time", Toast.LENGTH_LONG).show();
        }}
    public void uploadDes(){



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
            //Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }



}
