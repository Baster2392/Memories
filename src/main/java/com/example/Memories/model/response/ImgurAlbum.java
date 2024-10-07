package com.example.Memories.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImgurAlbum {
    public String id;
    public String title;
    public String description;
    public int datetime;
    public String cover;
    public int cover_width;
    public int cover_height;
    public String account_url;
    public Integer account_id;
    public String privacy;
    public String layout;
    public int views;
    public String link;
    public boolean favorite = false;
    public Boolean nsfw;
    public String section;
    public int order = 0;
    public String deletehash;
    public int images_count;
    public List<ImgurImage> images;
    public boolean in_gallery;
}

