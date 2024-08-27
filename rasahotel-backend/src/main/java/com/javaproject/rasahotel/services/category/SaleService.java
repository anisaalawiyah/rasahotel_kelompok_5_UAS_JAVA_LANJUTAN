package com.javaproject.rasahotel.services.category;

import java.util.List;

import com.javaproject.rasahotel.dto.category.SaleRequestDto;
import com.javaproject.rasahotel.entities.Sale;

public interface SaleService {

    List<Sale> getAll();

    Sale get(String idSale);

    Sale set(SaleRequestDto dto);

    Sale update(String idSale, SaleRequestDto dto);

    void delete(String idSale);
}
