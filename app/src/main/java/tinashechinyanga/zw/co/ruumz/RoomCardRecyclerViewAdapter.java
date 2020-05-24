package tinashechinyanga.zw.co.ruumz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tinashe on 2/22/2016.
 */
public class RoomCardRecyclerViewAdapter extends PagedListAdapter<ParseObject, RoomCardRecyclerViewAdapter.RoomViewHolder> {

    private List<ParseObject> mRooms = new ArrayList<>();
    private ParseObject room;
    private String mSection;

    public RoomCardRecyclerViewAdapter(){
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<ParseObject> DIFF_CALLBACK = new DiffUtil.ItemCallback<ParseObject>() {
        @Override
        public boolean areItemsTheSame(@NonNull ParseObject oldItem, @NonNull ParseObject newItem) {
            return oldItem.getObjectId() == newItem.getObjectId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ParseObject oldItem, @NonNull ParseObject newItem) {
            return (oldItem.getUpdatedAt().equals(newItem.getUpdatedAt()) && oldItem.getCreatedAt().equals(newItem.getCreatedAt()));
        }
    };
//    RoomCardRecyclerViewAdapter(List<ParseObject> rooms){
//        this.mRooms = rooms;
//    }
//
    public RoomCardRecyclerViewAdapter(String section) {
        this();
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
            mRoomImage = itemView.findViewById(R.id.room_image);
            mRoomPrice = itemView.findViewById(R.id.price_label);
            mInclusiveOrNot = itemView.findViewById(R.id.incl_excl_label);
            mPropertyType = itemView.findViewById(R.id.propertyType_label);
            mNumOfBeds = itemView.findViewById(R.id.num_beds_label);
            mNumOfBaths = itemView.findViewById(R.id.details_num_baths_label);
            mRoomLocation = itemView.findViewById(R.id.location_label);
            this.context = context;
            //set onclick listener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("Click event: ", "My room has been clicked.");
            int pos = getAdapterPosition();
            Intent intent;
            ParseObject room = getCurrentList().get(pos);

            //create the ParseObject proxy
            ParseProxyObject roomProxy = new ParseProxyObject(room);
            Toast.makeText(context, room.getString("roomSuburb"), Toast.LENGTH_LONG).show();
            //fork to corresponding activity
            if(mSection != null) {
                Log.i("mSection text: ", "mSection text is: " + mSection);
                if (mSection.equals("My Rooms")) {
                    //start my rooms detail activity
                    Log.i("My room: ", "Room selected " + roomProxy.getObjectId());
                    intent = new Intent(context, MyRoomDetailActivity.class);
                    //add the room to the intent
                    intent.putExtra("currentSelectedRoomObject", room);
                    Log.i("Selected room", "Put Extra, " + room);
                    intent.putExtra("roomObject", roomProxy);
                    context.startActivity(intent);
                }
            }else {
                Log.i("My room:", "RoomDetailActivity loaded for MyRoomDetail Activity instead");
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
        room = getItem(position);
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



//    @Override
//    public int getItemCount() {
//        if(mRooms.size() == 0){
//            return 0;
//        }else if (mRooms == null){
//            return 0;
//        }else {
//            return mRooms.size();
//        }
//    }

    public void addMoreRooms(List<ParseObject> newRooms){
        mRooms.addAll(newRooms);
        submitList((PagedList<ParseObject>) mRooms);
    }

    //update dataset change
    public void addAll(List<ParseObject> latestRooms){
        mRooms.addAll(0, latestRooms);
        //mRooms.add(i, latestRooms);
        notifyDataSetChanged();
    }
}
