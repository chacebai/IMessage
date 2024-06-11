package com.javalang.imessage.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @RequestMapping("/getPreSigned")
    public String getPreSigned() {
        // 调用 GetPreSigned 逻辑
        return "Pre-signed URL";
    }
}
