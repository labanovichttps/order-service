package com.labanovich.order.controller;

import com.labanovich.order.dto.OrderCreateDto;
import com.labanovich.order.dto.OrderDto;
import com.labanovich.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Flux<OrderDto>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @PostMapping
    public Mono<ResponseEntity<OrderDto>> saveOrder(@RequestBody Mono<OrderCreateDto> orderCreateDto) {
        return orderService.createOrder(orderCreateDto)
                .map(orderDto ->  ResponseEntity.status(HttpStatus.CREATED).body(orderDto))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NO_CONTENT)));
    }
}
