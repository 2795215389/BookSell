package com.js.api.server;

import com.js.api.dto.BookRobDto;

public interface IBookRecordService {
    boolean updateStock(String bookNo);
    boolean updateRob(BookRobDto dto);
    int queryStock(String bookNo);
    boolean judgeUser(String booNo,Integer userId);
}
