package com.winterhold.customer_service.dto.option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OptionDto {
    private String text;
    private String value;
    private Boolean selected;
}
