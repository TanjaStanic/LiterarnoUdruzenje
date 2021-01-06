package com.example.bankacquirer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.bankacquirer.dto.PccRequestDTO;
import com.example.bankacquirer.dto.PccResponseDTO;
import com.example.bankacquirer.service.PccService;

@Controller
@RequestMapping("/pcc")
public class PccController {

	@Autowired
	private PccService pccService;
	
	@RequestMapping(value = "/create-response", method = RequestMethod.POST)
	public PccResponseDTO createResponse(@RequestBody PccRequestDTO pccRequestDto) {
		
		PccResponseDTO pccResponseDto = pccService.createResponse(pccRequestDto);

		return pccResponseDto;
	}
	
	
}
