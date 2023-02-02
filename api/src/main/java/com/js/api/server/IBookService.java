package com.js.api.server;

import com.js.api.dto.BookRobDto;
import com.js.api.model.BookRob;

public interface IBookService {
    BookRob rob(BookRobDto dto);
    BookRob robLock(BookRobDto dto);
    int countBook(String bookNo);
}
