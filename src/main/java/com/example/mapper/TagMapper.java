package com.example.mapper;

import com.example.dto.TagDTO;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/16.
 * PS: Not easy to write code, please indicate.
 */


public interface TagMapper {
 //   @Insert("insert into  tag (CATEGOY_NAME,TAGS)   values (#{categoryName},#{tags})")
    void AddTag(TagDTO tagDTO);
}
