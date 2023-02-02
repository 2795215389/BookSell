package com.js.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.js.api.dto.BookRobDto;
import com.js.api.model.BookRob;
import com.js.api.server.IBookRecordService;
import com.js.api.server.IBookService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service(
        version = "1.0.0",interfaceName = "IBookService",
        interfaceClass = IBookService.class,
        timeout = 120000
)
public class BookServiceImpl implements IBookService {
    private static final Logger log= LoggerFactory.getLogger(BookServiceImpl.class);
    private static final String lockPrefix="/zkLock/";
    @Autowired
    IBookRecordService recordService;
    @Autowired
    CuratorFramework curatorFrameworkClient;


    @Override
    public BookRob rob(BookRobDto dto) {
        Integer userId=dto.getUserId();
        String bookNo=dto.getBookNo();

        BookRob bookRob=new BookRob();
        bookRob.setUserid(userId);
        bookRob.setBookno(bookNo);
        bookRob.setRobTime(new Date());
        //判断是否还有库存，以及该用户是否抢过。
        if(this.countBook(bookNo)>0 &&recordService.judgeUser(bookNo,userId)){
            //如果满足条件，可以抢，则更新抢断记录和库存记录
            recordService.updateRob(dto);
            recordService.updateStock(bookNo);
            log.info("用户：{}，抢书{},成功！",userId,bookNo);
            return bookRob;
        }else{
            log.info("用户：{}，抢书{},失败!",userId,bookNo);
            return null;
        }
    }

    @Override
    public BookRob robLock(BookRobDto dto) {
        Integer userId=dto.getUserId();
        String bookNo=dto.getBookNo();
        final String lockName=lockPrefix+bookNo+userId;
        InterProcessMutex mutex=new InterProcessMutex(curatorFrameworkClient,lockName);
        try{
            boolean lockFlag=mutex.acquire(10L, TimeUnit.SECONDS);
            if(lockFlag){
                BookRob bookRob=new BookRob();
                bookRob.setUserid(userId);
                bookRob.setBookno(bookNo);
                bookRob.setRobTime(new Date());
                //判断是否还有库存，以及该用户是否抢过。
                if(this.countBook(bookNo)>0 &&recordService.judgeUser(bookNo,userId)){
                    //如果满足条件，可以抢，则更新抢断记录和库存记录
                    recordService.updateRob(dto);
                    recordService.updateStock(bookNo);
                    log.info("用户：{}，抢书{},成功！",userId,bookNo);
                    return bookRob;
                }else{
                    log.info("用户：{}，抢书{},失败!",userId,bookNo);
                    return null;
                }
            }
        }catch (Exception e){
            log.info("zk锁{}加锁失败!",lockName);
        }finally {
            if(mutex!=null){
                try {
                    mutex.release();
                } catch (Exception e) {
                    log.info("zk锁{}解锁失败!",lockName);
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public int countBook(String bookNo) {
        return recordService.queryStock(bookNo);
    }
}
