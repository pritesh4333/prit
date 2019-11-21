package in.co.vyapari.database;

import java.util.Date;

/**
 * Created by bekirdursun on 5.02.2018.
 */

public class DbObject<T> {

    private String id;
    private T data;
    private String searchTags;
    private Date createdDate;
    private Date modifiedDate;
    private Date synchronizedDate;
    private boolean isSynchronized;
    private boolean isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DbObject(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getSearchTags() {
        return searchTags;
    }

    public void setSearchTags(String searchTags) {
        this.searchTags = searchTags;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getSynchronizedDate() {
        return synchronizedDate;
    }

    public void setSynchronizedDate(Date synchronizedDate) {
        this.synchronizedDate = synchronizedDate;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setSynchronized(boolean aSynchronized) {
        isSynchronized = aSynchronized;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}