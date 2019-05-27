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
    private String fileSizeStr;
    private Long startTime;
    private Long endTime;
    private Long dlTime;
    private boolean completed;
}
