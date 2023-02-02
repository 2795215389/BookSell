package com.js.provider.service;

import com.js.api.dto.BookRobDto;
import com.js.api.model.BookRob;
import com.js.api.model.BookRobExample;
import com.js.api.server.IBookRecordService;
import com.js.provider.mapper.BookRobMapper;
import com.js.provider.mapper.BookStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class BookRecordServiceImpl implements IBookRecordService {
    @Autowired
    BookStockMapper bookStockMapper;
    @Autowired
    BookRobMapper bookRobMapper;

    @Override
    public boolean updateStock(String bookNo) {
        int flag=bookStockMapper.updateStock(bookNo);
        return flag>0;
    }

    @Override
    public boolean updateRob(BookRobDto dto) {
        Integer userId=dto.getUserId();
        String bookNo=dto.getBookNo();
        BookRob bookRob=new BookRob();
        bookRob.setBookno(bookNo);
        bookRob.setUserid(userId);
        bookRob.setRobTime(new Date());
        int flag=bookRobMapper.insertSelective(bookRob);
        return flag>0;
    }

    @Override
    public int queryStock(String bookNo) {
        int res= bookStockMapper.countStock(bookNo);
        return res;
    }

    @Override
    public boolean judgeUser(String booNo, Integer userId) {
        BookRobExample example=new BookRobExample();
        example.createCriteria().andBooknoEqualTo(booNo).andUseridEqualTo(userId);
        List<BookRob> robList=bookRobMapper.selectByExample(example);
        if(robList!=null &&robList.size()>0){
            return false;
        }else{
            return true;
        }
    }


}
