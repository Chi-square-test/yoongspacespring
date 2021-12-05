package com.yoongspace.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder //builder 디자인 패턴
@NoArgsConstructor //매개변수 없는 생성자 구현
@AllArgsConstructor //모든 매개변수 받는 생성자 구현
@Data //getter setter 구현

public class ResponseDTO<T> {
    private String error;
    private List<T> data;
}
