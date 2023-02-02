package com.js.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookRob implements Serializable {
    private Integer id;

    private Integer userid;

    private String bookno;

    private Date robTime;
}