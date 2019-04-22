package com.zxiaofan.springbootredislettuce.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Project: OpenSource_Study
 * Author: zxiaofan
 * Date: 2019/4/22
 * Time: 19:24
 * Desc: create.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {
    private String bookName;
    private String bookAuthor;

}
