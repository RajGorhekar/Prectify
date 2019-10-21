package com.example.prectify;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
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
        description=(EditText)findViewById(R.id.editText5);
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Data uploading...");

    }
    public void btnSelectImage(View view){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("To add Image");
        String [] pictureDialogItems={"Open Camera","Choose from Device"};
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 1 :
                        Intent photopicker=new Intent(Intent.ACTION_PICK);
                        photopicker.setType("image/*");
                        startActivityForResult(photopicker,1);;
                        break;
                    case 2 :
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                       /* File file = new  File(getCacheDir(),String.valueOf(System.currentTimeMillis())+ ".jpg");
                        uri = Uri.fromFile(
                                file
                        );
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);*/
                        startActivityForResult(intent,2);
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       /* if(requestCode == CAMERA_REQUEST){
           // Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            //upload.setImageBitmap(bitmap);

        }*/
        if(resultCode == RESULT_OK && requestCode == 1){
            uri=data.getData();
            upload.setImageURI(uri);
        }
        else if (resultCode == RESULT_OK  && requestCode == 2){
            uri=data.getData();
            upload.setImageURI(uri);
        }
        else{
            Toast.makeText(this, "You havent picked an image", Toast.LENGTH_SHORT).show();
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
        post ++;
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
                imageUrl,qtype,mauth.getUid(),"Unseen"
        );
        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Description").child(String.valueOf(post))
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
}
