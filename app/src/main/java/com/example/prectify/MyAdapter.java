package com.example.prectify;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<UserViewHolder>{
   private Context mContext;
   private List<UserData> myUserList;
   //private String key;


    public MyAdapter ( Context mContext , List<UserData> myUserList ) {
        this.mContext = mContext;
        this.myUserList = myUserList;
    }

    @Override
    public UserViewHolder onCreateViewHolder ( @NonNull ViewGroup viewGroup , int viewType ) {
        View mView = LayoutInflater.from( viewGroup.getContext()).inflate( R.layout.recycler_row_item,viewGroup,false );
        return new UserViewHolder(mView);
    }

    @Override
    public void onBindViewHolder ( @NonNull final UserViewHolder userViewHolder , int i ) {

        FirebaseUser user3=FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Description");
        Glide.with(mContext).load(myUserList.get(i).getImage())
                .into(userViewHolder.imageView);
       // userViewHolder.imageView.setImageResource( myUserList.get( i ).getImage() );
        userViewHolder.mTitle.setText( String.valueOf( myUserList.get( i ).getqTitle()) );
        userViewHolder.mDescription.setText( String.valueOf( myUserList.get( i ).getqDescription() ));
        userViewHolder.status.setText(myUserList.get(i).getStatus());
        if(myUserList.get(i).getStatus().equals("Unseen") ){
            userViewHolder.status.setTextColor(0xFFD81B60);
        }
        else if(myUserList.get(i).getStatus().equals("Seen")){
            userViewHolder.status.setTextColor(0xFF00BDEC);
        }
        else if(myUserList.get(i).getStatus().equals("Solved")){
            userViewHolder.status.setTextColor(0xFF00C41E);
        }

       if(user3 == null) {
            userViewHolder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  final String D= myUserList.get(userViewHolder.getAdapterPosition()).getqDescription();
                   // Toast.makeText(mContext, D, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder statusdialog = new AlertDialog.Builder(mContext);
                    statusdialog.setTitle("STATUS");
                    statusdialog.setPositiveButton("Seen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    //Toast.makeText(mContext, D, Toast.LENGTH_SHORT).show();
                                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                        if((snapshot.child("qDescription").getValue(String.class).equals(D)) && (snapshot.child("trace").getValue(int.class)==0)){
                                            String key=snapshot.getKey();
                                            changeSeen(key);
                                            userViewHolder.status.setTextColor(0xFF00A6FF);
                                        }
                                       /* else{
                                            Toast.makeText(mContext, "You cant change again contact the developer if you want to", Toast.LENGTH_SHORT).show();
                                        }*/

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).setNeutralButton("Solved", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                        if((snapshot.child("qDescription").getValue(String.class).equals(D)) && (snapshot.child("trace").getValue(int.class)== 1)){
                                            String key=snapshot.getKey();
                                            changeSolved(key);
                                            userViewHolder.status.setTextColor(0xFF2BC50B);
                                        }


                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });

                    statusdialog.show();
                }
            });

        }
      /* else{
           userViewHolder.status.setText(myUserList.get(i).getStatus());


        }*/


       /*     databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserData User3 = dataSnapshot.getValue(UserData.class);
                    userViewHolder.status.setText(String.valueOf(userViewHolder.getAdapterPosition());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/




        userViewHolder.mCardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                Intent intent =new Intent( mContext,DetailActivity.class );
                intent.putExtra( "Image",myUserList.get( userViewHolder.getAdapterPosition()).getImage() );
                intent.putExtra( "Description" ,myUserList.get( userViewHolder.getAdapterPosition()).getqDescription() );
                mContext.startActivity( intent );
            }
        } );



    }

    public void changeSeen(String key1){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Description").child(key1);
        reference.child("trace").setValue(1);
        reference.child("status").setValue("Seen");

    }

    public void changeSolved(String key2){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Description").child(key2);
        reference.child("trace").setValue(2);
        reference.child("status").setValue("Solved");
    }

    @Override
    public int getItemCount () {
        return myUserList.size();
    }

    public void filteredList(ArrayList<UserData> filterList) {

        myUserList=filterList;
        notifyDataSetChanged();

    }
}
class UserViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView mTitle,mDescription;
    TextView status;
    CardView mCardView;
    UserData sUserData;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();



    public UserViewHolder ( View itemView ) {
        super( itemView );

        imageView= itemView.findViewById( R.id.ivImage );
        mTitle=itemView.findViewById( R.id.tvTitle );
        mDescription=itemView.findViewById( R.id.tvDescription );
        status=itemView.findViewById( R.id.cbDone );
        setcolor(status);
        mCardView=itemView.findViewById( R.id.mycardView );



//


    }

     void setcolor(TextView t){
        if(t.getText().equals("Unseen")){
            t.setTextColor(0xFFD81B60);
        }
        else if(t.getText().equals("Seen")){
            t.setTextColor(0xFF00A6FF);
        }
        else if(t.getText().equals("Solved")){
            t.setTextColor(0xFF2BC50B);
        }
    }


}