package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeForm;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import com.example.firstproject.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController

public class CoffeeApiController {
    @Autowired
    private CoffeeService coffeeService;
    @GetMapping("/api/coffees")
    public List<Coffee> index(){
        return coffeeService.index();
    }
    @GetMapping("/api/coffees/{id}")
    public Coffee show(@PathVariable Long id){
        return coffeeService.show(id);
    }

    @PostMapping("/api/coffees")
    public ResponseEntity<Coffee> created(@RequestBody CoffeeForm dto){
        Coffee created = coffeeService.created(dto);
        return (created != null)?
                ResponseEntity.status(HttpStatus.OK).body(created):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeForm dto){
       Coffee update = coffeeService.update(id,dto);
        return (update != null)?
                ResponseEntity.status(HttpStatus.OK).body(update):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id){
        Coffee delete = coffeeService.delete(id);
        return (delete != null)?
                ResponseEntity.status(HttpStatus.OK).body(delete):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }


}
