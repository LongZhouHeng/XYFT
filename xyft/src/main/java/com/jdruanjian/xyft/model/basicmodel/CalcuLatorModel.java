package com.jdruanjian.xyft.model.basicmodel;



import com.jdruanjian.xyft.model.entity.CalcuLatorList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 龙龙 on 2017/9/13.
 */

public class CalcuLatorModel implements Serializable{

    public String msg;

    public String msgContext;
    public List<CalcuLatorList> datas;

}
