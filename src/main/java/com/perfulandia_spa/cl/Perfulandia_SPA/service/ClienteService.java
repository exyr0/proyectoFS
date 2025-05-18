package com.perfulandia_spa.cl.Perfulandia_SPA.service;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Cliente;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
@Transactional
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }
    public Cliente findById(long id){
        return clienteRepository.findById(id).get();    
    }    
    public Cliente save(Cliente cliente){
        String hash = encoder.encode(cliente.getContraseña());
        cliente.setContraseña(hash);                      
        return clienteRepository.save(cliente);
    }
    public void delete(Long id){
        clienteRepository.deleteById(id);
    }
}
