package com.perfulandia_spa.cl.Perfulandia_SPA.service;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Carrito;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.CarritoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    public List<Carrito> findAll(){
        return carritoRepository.findAll();
    }
    public Carrito findById(long id){
        return carritoRepository.findById(id).get();    
    }    
    public Carrito save(Carrito carrito){
        return carritoRepository.save(carrito);
    }
    public void deleteById(Long id){
        carritoRepository.deleteById(id);
    }
}
