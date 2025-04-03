package com.winterhold.loan_service.dto.response.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseWithDataDto {
    private Integer status;
    private String message;
    private Object data;
}
