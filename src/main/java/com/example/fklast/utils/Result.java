package com.example.fklast.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 卢本伟牛逼
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result
{
    private Boolean flag;

    private Object data;
    private String msg;



    public Result ( Boolean flag, Object data )
    {
        this.flag = flag;
        this.data = data;
    }

    public Result ( Boolean flag, String msg )
    {
        this.flag = flag;
        this.msg = msg;
    }

    public Result ( String msg )
    {
        this.flag = false;
        this.msg = msg;
    }

    public Result ( Boolean flag )
    {
        this.flag = flag;
    }
}
