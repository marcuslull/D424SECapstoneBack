package com.mybackyard.backend.dto.service.interfaces;

import com.mybackyard.backend.dto.model.YardDto;
import com.mybackyard.backend.model.Yard;

import java.util.List;

public interface YardDtoService {
    Yard processIncomingYardDto(YardDto yardDto, boolean isFromPatch);

    List<YardDto> processOutgoingYardList(List<Yard> yardList);

    YardDto processOutgoingYard(Yard yard);

    YardDto addId(YardDto yardDto, long yardId);
}
