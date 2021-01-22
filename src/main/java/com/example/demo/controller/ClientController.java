package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Client;
import com.example.demo.repositories.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {
  
  @Autowired
  private ClientRepository clientRepository;

  @GetMapping()
  public List<Client> index() {
    return clientRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Client> show(@PathVariable("id") Long id) {
    Optional <Client> clientFind = clientRepository.findById(id);

    if (clientFind.isPresent()) {
      return ResponseEntity.ok(clientFind.get());
    }
    
    return ResponseEntity.noContent().build();
  }

  @PostMapping()
  @ResponseStatus(code = HttpStatus.CREATED)
  public Client create(@RequestBody Client client) {
    Client clientCreated = clientRepository.save(client);

    return clientCreated;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Client> update(@PathVariable("id") Long id, @RequestBody Client client) {
    Optional<Client> optionalClientUpdated = clientRepository.findById(id);
    
    if(optionalClientUpdated.isPresent()) {
      Client clientUpdated = optionalClientUpdated.get();
      clientUpdated.setName(client.getName());
      clientRepository.save(clientUpdated);

      return ResponseEntity.ok(clientUpdated);
    }
    
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable("id") Long id) {
    Optional<Client> optionalClientDeleted = clientRepository.findById(id);

    if (optionalClientDeleted.isPresent()) {
      clientRepository.deleteById(id);

      return ResponseEntity.ok().body("ok");
    }

    return ResponseEntity.noContent().build();
  }
}
