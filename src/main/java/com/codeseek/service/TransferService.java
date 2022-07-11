package com.codeseek.service;

import com.codeseek.controller.dto.request.TransferRequestDTO;
import com.codeseek.controller.dto.response.TransferResponseDTO;
import com.codeseek.controller.request.TransferSearchRequest;
import org.springframework.data.domain.Page;
public interface TransferService {
    Page<TransferResponseDTO> searchTransfers(TransferSearchRequest searchRequest);
    TransferResponseDTO saveTransfer(TransferRequestDTO transferRequestDTO);

}