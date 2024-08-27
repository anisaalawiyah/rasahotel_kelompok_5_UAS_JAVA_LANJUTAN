package com.javaproject.rasahotel.services.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.rasahotel.dto.category.SaleRequestDto;
import com.javaproject.rasahotel.entities.Sale;
import com.javaproject.rasahotel.repositories.SaleRepository;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    SaleRepository saleRepository;

    @Override
    public List<Sale> getAll() {
        try {

            return saleRepository.findAll();
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Sale get(String idSale) {
        try {

            return saleRepository.findById(idSale).orElse(null);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Sale set(SaleRequestDto dto) {
        try {

            Sale newSale = new Sale();
            newSale.setName(dto.getName());
            newSale.setPrice(dto.getPrice());

            return saleRepository.save(newSale);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Sale update(String idSale, SaleRequestDto dto) {

        try {

            Sale oldSale = saleRepository.findById(idSale).orElse(null);
            oldSale.setName(dto.getName());
            oldSale.setPrice(dto.getPrice());

            return saleRepository.save(oldSale);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void delete(String idSale) {
        try {

            saleRepository.deleteById(idSale);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
