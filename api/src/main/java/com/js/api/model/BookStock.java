package com.js.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookStock implements Serializable {
    private Integer id;

    private String bookno;

    private Integer stock;

    private Byte isActive;

}