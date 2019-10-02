package tinashechinyanga.zw.co.ruumz.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "room_summary")
public class RoomSummaryEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "room_id")
    private String roomObjectID;

    @ColumnInfo(name = "room_location")
    private String roomLoaction;
    @ColumnInfo(name = "room_property_type")
    private String roomPropertyType;
    @ColumnInfo(name = "room_rental_inclOf_bills")
    private String roomRentalInclusiveOfBillsOrNot;
    @ColumnInfo (name = "room_rental_price")
    private int roomRentalPrice;
    @ColumnInfo(name = "room_numberOf_bedrooms")
    private int roomNumOfBedrooms;
    @ColumnInfo(name = "room_numberOf_bathrooms")
    private int roomNumOfBathrooms;

    public RoomSummaryEntity(){

    }

    public String getRoomLoaction() {
        return roomLoaction;
    }

    public String getRoomPropertyType() {
        return roomPropertyType;
    }

    public String getRoomRentalInclusiveOfBillsOrNot() {
        return roomRentalInclusiveOfBillsOrNot;
    }

    public int getRoomRentalPrice() {
        return roomRentalPrice;
    }

    public int getRoomNumOfBedrooms() {
        return roomNumOfBedrooms;
    }

    public int getRoomNumOfBathrooms() {
        return roomNumOfBathrooms;
    }
}
