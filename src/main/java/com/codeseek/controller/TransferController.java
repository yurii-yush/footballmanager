package com.codeseek.controller;

import com.codeseek.common.Messages;
import com.codeseek.controller.dto.request.TransferRequestDTO;
import com.codeseek.controller.dto.response.TransferResponseDTO;
import com.codeseek.controller.request.TransferSearchRequest;
import com.codeseek.service.TransferService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(value = Messages.TRANSFER_CONTROLLER_URI)
public class TransferController {

    @Autowired
    private TransferService transferService;

    @ApiOperation(value = "This method is used to search existed Transfers")
    @GetMapping
    public ResponseEntity<Page<TransferResponseDTO>> searchTransfers(@Valid @RequestBody TransferSearchRequest searchRequest) {
        Page<TransferResponseDTO> TransfersDTO = transferService.searchTransfers(searchRequest);

        return ResponseEntity.status(HttpStatus.OK).body(TransfersDTO);
    }

    @ApiOperation(value = "This method is used to save new Transfer")
    @PostMapping
    public ResponseEntity<TransferResponseDTO> saveTransfer(@Valid @RequestBody TransferRequestDTO transferRequestDTO) {
        TransferResponseDTO createdTransferResponseDTO = transferService.saveTransfer(transferRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransferResponseDTO);
    }
}
