package in.co.vyaparienterprise.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by bekirdursun on 6.02.2018.
 */

public class DbObjectCV<T> {

    private String id;
    private String data;
    private String searchTags;
    private String createdDate;
    private String modifiedDate;
    private String synchronizedDate;
    private int isSynchronized;
    private int isDeleted;

    public DbObjectCV(DbObject<T> dbObject) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        this.id = dbObject.getId();
        this.data = gson.toJson(dbObject.getData());
        this.searchTags = dbObject.getSearchTags();
        this.createdDate = dbObject.getCreatedDate() != null ? dbObject.getCreatedDate().toString() : null;
        this.modifiedDate = dbObject.getModifiedDate() != null ? dbObject.getModifiedDate().toString() : null;
        this.synchronizedDate = dbObject.getSynchronizedDate() != null ? dbObject.getSynchronizedDate().toString() : null;
        this.isSynchronized = dbObject.isSynchronized() ? 1 : 0;
        this.isDeleted = dbObject.isDeleted() ? 1 : 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSearchTags() {
        return searchTags;
    }

    public void setSearchTags(String searchTags) {
        this.searchTags = searchTags;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getSynchronizedDate() {
        return synchronizedDate;
    }

    public void setSynchronizedDate(String synchronizedDate) {
        this.synchronizedDate = synchronizedDate;
    }

    public int getIsSynchronized() {
        return isSynchronized;
    }

    public void setIsSynchronized(int isSynchronized) {
        this.isSynchronized = isSynchronized;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
