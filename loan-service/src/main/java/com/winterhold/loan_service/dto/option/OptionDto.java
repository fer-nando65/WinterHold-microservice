package com.winterhold.loan_service.dto.option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionDto {
    private String text;
    private String value;
    private Boolean selected;
}
