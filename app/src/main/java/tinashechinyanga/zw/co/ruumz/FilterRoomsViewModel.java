package tinashechinyanga.zw.co.ruumz;

import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.HashMap;

public class FilterRoomsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    /*
    * contains, sortBy, budget, propertyType, amenities
    * */
    private int minBudget, maxBudget;
    private String propertyType;
    private Date sortByMostRecent;
    private int sortByLeastExpensive, sortByMostExpensive;
    private enum propertyType{
        FLAT("Flat"), FULL_HOUSE("Full House"), COTTAGE("Cottage");
        propertyType(String propertyType) {

        }
    }
    private int numOfBeds;
    private HashMap<String, Boolean> amenities = new HashMap<String, Boolean>();

    public int getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(int minBudget) {
        this.minBudget = minBudget;
    }

    public int getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(int maxBudget) {
        this.maxBudget = maxBudget;
    }

    public Date getSortByMostRecent() {
        return sortByMostRecent;
    }

    public void setSortByMostRecent(Date sortByMostRecent) {
        this.sortByMostRecent = sortByMostRecent;
    }

    public int getSortByLeastExpensive() {
        return sortByLeastExpensive;
    }

    public void setSortByLeastExpensive(int sortByLeastExpensive) {
        this.sortByLeastExpensive = sortByLeastExpensive;
    }

    public int getSortByMostExpensive() {
        return sortByMostExpensive;
    }

    public void setSortByMostExpensive(int sortByMostExpensive) {
        this.sortByMostExpensive = sortByMostExpensive;
    }

    public int getNumOfBeds() {
        return numOfBeds;
    }

    public void setNumOfBeds(int numOfBeds) {
        this.numOfBeds = numOfBeds;
    }

    public HashMap<String, Boolean> getAmenities() {
        return amenities;
    }

    public void setAmenities(HashMap<String, Boolean> amenities) {
        this.amenities = amenities;
    }
}
