package main.model;

import java.util.Date;

public class ToDo
{
    private int id;
    private String name;
    private int status;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus() {
        if(getStatus() == 0){
            status = 1;
        }
        else {
            status = 0;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
