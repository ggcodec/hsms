package com.hsms.core.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author haotchen
 * @time 2022/11/14-15:49
 */
@Data
public class RequestBodyHouse extends Houses{

    private List<String> urls;

}
