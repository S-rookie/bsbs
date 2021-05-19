package com.whpu.house.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whpu.house.mapper.ReserveMapper;
import com.whpu.house.pojo.Reserve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ReserveService {
    private ReserveMapper reserveMapper;

    @Autowired
    public ReserveService(ReserveMapper reserveMapper) {
        this.reserveMapper = reserveMapper;
    }

    public int addReserve(Reserve reserve){
        return reserveMapper.insertReserve(reserve);
    }

    public int updateReserve(Reserve reserve){
        if (reserve.getClose() == 1){
            reserve.setStatus(111);
        }else if (reserve.getClose()==200){
            reserve.setStatus(200);
        } else if (reserve.getClose()==2){
            reserve.setStatus(222);
        } else if (reserve.getClose()==-1){
            reserve.setStatus(1111);
        }else if(reserve.getClose()==-2){
            reserve.setStatus(2222);
        }
        return reserveMapper.updateReserve(reserve);
    }

    public PageInfo<Map<Object, Object>> getReserveList(Integer user_id,Integer owen_id,Integer close,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Map<Object, Object>> maps = reserveMapper.selectReserve(user_id,owen_id,close);
        return new PageInfo<>(maps);
    }
}
