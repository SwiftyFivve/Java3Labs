package com.jordanweaver.billsplitter;

import java.io.Serializable;

/**
 * Created by jordanweaver on 5/22/15.
 */
public class BillsObject implements Serializable {

    String receiverName;
    String senderName;
    String date;
    String amount;
    String message;
    String objectId;

    public BillsObject(String receiverName, String senderName, String date, String amount, String message, String objectId) {
        this.receiverName = receiverName;
        this.senderName = senderName;
        this.date = date;
        this.amount = amount;
        this.message = message;
        this.objectId = objectId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String sender) {
        this.senderName = senderName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
