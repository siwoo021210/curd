package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeForm;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CoffeeService {
    @Autowired
    CoffeeRepository coffeeRepository;

    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee created(CoffeeForm dto) {
        Coffee created = dto.toEntity();
        return coffeeRepository.save(created);
    }


    public Coffee update(Long id,CoffeeForm dto) {
        Coffee coffee = dto.toEntity();
        Coffee target = coffeeRepository.findById(id).orElse(null);
        log.info("id: {} , name: {} ", id, coffee.toString());

        if (target == null || id != coffee.getId()) {
            log.info("잘못된 요청! id: {} , name: {}", id, coffee.toString());
        }
        target.patch(coffee);
        Coffee update = coffeeRepository.save(target);
        return update;
    }

    public Coffee delete(Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null){
            return null;
        }
        coffeeRepository.delete(target);
        return target;
    }
}
