package com.perfulandia_spa.cl.Perfulandia_SPA.service;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Venta;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.VentaRepository;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> findAll(){
        return ventaRepository.findAll();
    }

    public Venta findById(long id){
        return ventaRepository.findById(id).get();
    }

    public Venta save(Venta venta){
        return ventaRepository.save(venta);
    }

    public void delete(Long id){
        ventaRepository.deleteById(id);
    }

}
