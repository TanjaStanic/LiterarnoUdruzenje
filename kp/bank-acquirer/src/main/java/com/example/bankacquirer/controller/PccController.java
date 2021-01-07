package com.example.bankacquirer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankacquirer.dto.PccRequestDTO;
import com.example.bankacquirer.dto.PccResponseDTO;
import com.example.bankacquirer.service.PccService;

@RestController
@RequestMapping("/pcc")
public class PccController {

	@Autowired
	private PccService pccService;
	
	@RequestMapping(value = "/create-response", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> createResponse(@RequestBody PccRequestDTO pccRequestDto) {
		PccResponseDTO pccResponseDto = pccService.createResponse(pccRequestDto);

		return new ResponseEntity<>(pccResponseDto, HttpStatus.OK);
	}
	
	
}
