package tinashechinyanga.zw.co.ruumz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUZ0007 on 2/22/2016.
 */
public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.RoomViewHolder> {

    private List<ParseObject> mRooms = new ArrayList<>();
    private String mSection;
    RoomRecyclerViewAdapter(List<ParseObject> rooms){
        this.mRooms = rooms;
    }

    public RoomRecyclerViewAdapter(List<ParseObject> mRooms, String section) {
        this.mRooms = mRooms;
        this.mSection = section;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected ImageView mRoomImage;
        protected TextView mRoomPrice;
        protected TextView mInclusiveOrNot;
        protected TextView mPropertyType;
        protected TextView mNumOfBeds;
        protected TextView mNumOfBaths;
        protected TextView mRoomLocation;

        private Context context;

        public RoomViewHolder(Context context, View itemView) {
            super(itemView);
            mRoomImage = (ImageView)itemView.findViewById(R.id.room_image);
            mRoomPrice = (TextView)itemView.findViewById(R.id.price_label);
            mInclusiveOrNot = (TextView)itemView.findViewById(R.id.incl_excl_label);
            mPropertyType = (TextView)itemView.findViewById(R.id.propertyType_label);
            mNumOfBeds = itemView.findViewById(R.id.num_beds_label);
            mNumOfBaths = itemView.findViewById(R.id.details_num_baths_label);
            mRoomLocation =(TextView)itemView.findViewById(R.id.location_label);
            this.context = context;
            //set onclick listener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            Intent intent;
            ParseObject room = mRooms.get(pos);
            //create the ParseObject proxy
            ParseProxyObject roomProxy = new ParseProxyObject(room);
            Toast.makeText(context, room.getString("roomSuburb"), Toast.LENGTH_LONG).show();
            //fork to corresponding activity
            if(mSection != null) {
                if (mSection.equals("My Rooms")) {
                    //start my rooms detail activity
                    intent = new Intent(context, MyRoomDetailActivity.class);
                    //add the proxy to the intent
                    intent.putExtra("roomObject", roomProxy);
                    context.startActivity(intent);
                }
            }else {
                intent = new Intent(context, RoomDetailActivity.class);
                //add the proxy to the intent
                intent.putExtra("roomObject", roomProxy);
                context.startActivity(intent);
            }

        }
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating the viewholder with the appropriate views
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_cardview, parent, false);

        return new RoomViewHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        ParseObject room = mRooms.get(position);
        holder.mRoomLocation.setText(room.getString("roomSuburb"));
        holder.mRoomPrice.setText(Integer.toString(room.getInt("roomMonthlyRent")));
        holder.mInclusiveOrNot.setText(room.getString("roomRentInclusiveOfBills"));
        holder.mPropertyType.setText(room.getString("roomPropertyType"));
        holder.mNumOfBeds.setText(Integer.toString(room.getInt("roomBedrooms")));
        holder.mNumOfBaths.setText(Integer.toString(room.getInt("roomBathrooms")));

        //get the image
        //check if its roomImage or image1 set
        ParseFile imageFile;
        if (room.getParseFile("roomImage") != null){
            //
            imageFile = room.getParseFile("roomImage");
        }else {
            imageFile = room.getParseFile("roomImage1");
        }

        //if there is no image saved in Parse
        if(imageFile == null){
            //continue to load predefined default image
            int r = R.mipmap.ic_launcher;
            Glide.with(holder.mRoomImage.getContext()).load(r)
                    .into(holder.mRoomImage);
        }else {
            Uri fileUri = Uri.parse(imageFile.getUrl());

            //image loader recommended by Google
            Glide.with(holder.mRoomImage.getContext()).load(fileUri.toString())
                    .thumbnail(0.6f)
                    .centerCrop()
                    .crossFade()
                    .into(holder.mRoomImage);
        }

    }

    @Override
    public int getItemCount() {
        if(mRooms.size() == 0){
            return 0;
        }else if (mRooms == null){
            return 0;
        }else {
            return mRooms.size();
        }
    }

    //update dataset change
    public void addAll(List<ParseObject> latestRooms){
        mRooms.addAll(0, latestRooms);
        //mRooms.add(i, latestRooms);
        notifyDataSetChanged();
    }
}
