package com.example.Memories.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImgurImage {

    private String id;
    private String title;
    private String description;
    private long datetime;
    private String type;
    private boolean animated;
    private int width;
    private int height;
    private long size;
    private long views;
    private long bandwidth;
    private String vote;
    private boolean favorite;
    private boolean nsfw;
    private String section;
    private String account_url;
    private long account_id;
    private boolean is_ad;
    private boolean in_most_viral;
    private boolean has_sound;
    private String[] tags;
    private int ad_type;
    private String ad_url;
    private String edited;
    private boolean in_gallery;
    private String deletehash;
    private String name;
    private String link;
}
