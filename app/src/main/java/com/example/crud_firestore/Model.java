package com.example.crud_firestore;

public class Model {

    String id,name,mailid;

    public Model(String id, String name, String mailid)
    {
        this.id = id;
        this.name = name;
        this.mailid = mailid;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid)
    {
        this.mailid = mailid;
    }

}
