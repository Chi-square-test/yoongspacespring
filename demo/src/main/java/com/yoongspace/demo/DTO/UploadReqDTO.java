package com.yoongspace.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadReqDTO {
    private String bucketName;
    private String uploadFileName;
    private String localFileLocation;
}
