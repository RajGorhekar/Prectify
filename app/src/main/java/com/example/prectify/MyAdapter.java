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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<UserViewHolder>{
   private Context mContext;
   private List<UserData> myUserList;

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
        Glide.with(mContext).load(myUserList.get(i).getImage())
                .into(userViewHolder.imageView);
       // userViewHolder.imageView.setImageResource( myUserList.get( i ).getImage() );
        userViewHolder.mTitle.setText( String.valueOf( myUserList.get( i ).getqTitle()) );
        userViewHolder.mDescription.setText( String.valueOf( myUserList.get( i ).getqDescription() ));
        //userViewHolder.mTitle.setText(String.valueOf(myUserList.get(i).getqTitle()));
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            userViewHolder.mseen.setOnContextClickListener(new View.OnContextClickListener() {
                @Override
                public boolean onContextClick(View v) {
                    AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
                    pictureDialog.setTitle("Select Progress !");
                    String [] pictureDialogItems={"Seen","Solved"};
                    pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case 1 :
                                    userViewHolder.mseen.setText("Seen");
                                    break;
                                case 2 :
                                    userViewHolder.mseen.setText("Solved");
                                    break;
                            }
                        }
                    });
                    pictureDialog.show();


                    return true;
                }
            });
        }*/

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
    TextView mseen;
    CardView mCardView;



    public UserViewHolder ( View itemView ) {
        super( itemView );

        imageView= itemView.findViewById( R.id.ivImage );
        mTitle=itemView.findViewById( R.id.tvTitle );
        mDescription=itemView.findViewById( R.id.tvDescription );
        mseen=itemView.findViewById( R.id.cbDone );
        mCardView=itemView.findViewById( R.id.mycardView );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mseen.setOnContextClickListener(new View.OnContextClickListener() {
                @Override
                public boolean onContextClick(View v) {
                    /*AlertDialog.Builder pictureDialog = new AlertDialog.Builder(MyAdapter.this);
                    pictureDialog.setTitle("Select Progress !");
                    String [] pictureDialogItems={"Seen","Solved"};
                    pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case 1 :
                                    mseen.setText("Seen");
                                    break;
                                case 2 :
                                    mseen.setText("Solved");
                                    break;
                            }
                        }
                    });
                    pictureDialog.show();*/
                    return true;
                }
            });
        }


    }


}