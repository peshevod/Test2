package com.example.restservice;


public class MyRecord {
    private int id;
    private String name;
    private String desc;

    public int getId() { return id;}
    public String getName() { return name;}
    public String getDesc() {return desc;}

    public void setId(int id) { this.id=id;}
    public void setName(String name) { this.name=name;}
    public void setDesc(String desc) { this.desc=desc;}

    @Override
    public String toString() {
        return "MyRecord [id=" + id + ", name=" + name + ", desc=" + desc + "]";
    }

}
