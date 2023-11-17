package com.common.logs;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @Author 张乔
 * @Date 2023/11/13 21:27
 */
@AutoConfiguration
@Import(LogAspect.class)
public class AutoConfig {
}
