package com.aladdinworks8.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StaticTransferSwitchPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<StaticTransferSwitchDTO> staticTransferSwitchs;
}





