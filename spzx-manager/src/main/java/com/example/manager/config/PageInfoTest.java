package com.example.manager.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/2 20:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoTest<T> {

    private List<T> list;
    private Long total;



}
