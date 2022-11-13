package com.example.fklast.utils;

import com.example.fklast.dto.UserDTO;

/**
 * @author 卢本伟牛逼
 */
public class UserHolder
{
    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser ( UserDTO userDTO )
    {
        tl.set(userDTO);
    }

    public static UserDTO getUser ()
    {
        return tl.get();
    }

    public static void removeUser ()
    {
        tl.remove();
    }
}
