package pl.piomin.services.redis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.piomin.services.redis.model.Customer;
import pl.piomin.services.redis.model.Transaction;
import pl.piomin.services.redis.repository.CustomerRepository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @RequestMapping
    public Customer add(@RequestBody Customer customer) {
        return repository.save(customer);
    }
    @RequestMapping("addList")
    public Customer addList(@RequestBody Customer customer) {
        return repository.save(customer);
    }


    @GetMapping("/{id}")
    public Customer findById(@PathVariable("id") Long id) {
        Optional<Customer> optCustomer = repository.findById(id);
        return optCustomer.orElse(null);
    }

}
