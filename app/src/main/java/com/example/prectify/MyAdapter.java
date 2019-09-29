package com.example.prectify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
        userViewHolder.mTitle.setText(String.valueOf(myUserList.get(i).getqTitle()));

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
    }
}