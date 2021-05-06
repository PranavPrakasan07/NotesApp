package com.example.notesapp;

public class NotesData {

    String title;

    public NotesData(String title, String description, int image_id) {
        this.title = title;
        this.description = description;
        this.image_id = image_id;
    }

    String description;
    int image_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }
}
