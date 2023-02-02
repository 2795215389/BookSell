package com.js.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Strings;
import com.js.api.dto.BookRobDto;
import com.js.api.model.BookRob;
import com.js.api.response.BaseResponse;
import com.js.api.response.StatusCode;
import com.js.api.server.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private static final Logger log= LoggerFactory.getLogger(BookController.class);
    private static final String urlPrefix="/book";

    @Reference(
            version = "1.0.0",interfaceName = "IBookService",
            interfaceClass = IBookService.class,
            timeout = 120000
    )
    IBookService bookService;



    @GetMapping(value = urlPrefix+"/rob")
    public BaseResponse rob(BookRobDto dto){
        Integer userId=dto.getUserId();
        String bookNo=dto.getBookNo();
        if(Strings.isNullOrEmpty(bookNo) ||Strings.isNullOrEmpty(userId.toString())){
            return new BaseResponse(StatusCode.InvalidParam);
        }
        BaseResponse response=new BaseResponse(StatusCode.Failed);
        BookRob robResult=bookService.rob(dto);
        if(robResult!=null){
            response=new BaseResponse(StatusCode.Success);
            log.info("用户：{}，抢书{},成功！",userId,bookNo);
            int res=bookService.countBook(bookNo);
            response.setData("书籍"+bookNo+"，当前库存还有"+res);
            return response;
        }else{
            log.info("用户：{}，抢书{},失败！",userId,bookNo);
        }
        return response;
    }


    @GetMapping(value = urlPrefix+"/robLock")
    public BaseResponse robLock(BookRobDto dto){
        Integer userId=dto.getUserId();
        String bookNo=dto.getBookNo();
        if(Strings.isNullOrEmpty(bookNo) ||Strings.isNullOrEmpty(userId.toString())){
            return new BaseResponse(StatusCode.InvalidParam);
        }
        BaseResponse response=new BaseResponse(StatusCode.Failed);
        BookRob robResult=bookService.robLock(dto);
        if(robResult!=null){
            response=new BaseResponse(StatusCode.Success);
            log.info("用户：{}，抢书{},成功！",userId,bookNo);
            int res=bookService.countBook(bookNo);
            response.setData("抢书成功，书籍"+bookNo+"，当前库存还有"+res);
            return response;
        }else{
            log.info("用户：{}，抢书{},失败！",userId,bookNo);
        }
        return response;
    }








}
