package com.telerikteamwork.woahme.Models;

public class Place {
    private int Id;
    private String Title;
    private String ImageSource;
    private String ImageOrientation;

    public int get_id()
    {
        return this.Id;
    }

    public String get_Title()
    {
        return this.Title;
    }

    public String get_ImageSource()
    {
        return this.ImageSource;
    }

    public String get_ImageOrientation()
    {
        return this.ImageOrientation;
    }
}
