package com.perfulandia_spa.cl.Perfulandia_SPA.service;
 
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Producto;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.ProductoRepository;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto findById(long id){
        return productoRepository.findById(id).get();
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public void delete(Long id){
        productoRepository.deleteById(id);
    }
}
