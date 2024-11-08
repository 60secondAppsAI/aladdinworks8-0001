package com.aladdinworks8.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NetworkSwitchPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<NetworkSwitchDTO> networkSwitchs;
}





