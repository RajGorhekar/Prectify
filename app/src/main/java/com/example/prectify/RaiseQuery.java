package com.example.prectify;

import android.app.ProgressDialog;
import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class RaiseQuery extends AppCompatActivity {
    Button btnsubmit;
    Button btnSelectImage;
    Button camera;
    ImageView upload;
    EditText description;
    Uri uri;
    String imageUrl;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_raise_query );
        upload=(ImageView)findViewById(R.id.imageView2);
        btnSelectImage=findViewById( R.id.button3 );
        btnsubmit=findViewById( R.id.button3 );
        camera=findViewById(R.id.button7);
        description=(EditText)findViewById(R.id.editText5);
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Data uploading...");

    }
    public void btnSelectImage(View view){
        Intent photopicker=new Intent(Intent.ACTION_PICK);
        photopicker.setType("image/*");
        startActivityForResult(photopicker,1);
    }
    public void camera(View view){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
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
        progressDialog.setMessage("Description Uploadind....");
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
                Toast.makeText(RaiseQuery.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
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

        UserData userData=new UserData(
                description.getText().toString(),
                imageUrl
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


}
