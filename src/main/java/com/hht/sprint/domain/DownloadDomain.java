package com.hht.sprint.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(callSuper = true)
public class DownloadDomain {
    private String filePath;
    private String fileName;
    private Long fileSize;
    private Long dlTime;
    private String fileSizeStr;
    private boolean completed;
}
