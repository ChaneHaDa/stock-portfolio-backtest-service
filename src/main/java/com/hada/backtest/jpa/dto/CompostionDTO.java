package com.hada.backtest.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompostionDTO {
    private long id;
    private String name;
    private String description;
    private List<CompostionItemDTO> items;
    private int size;
}
