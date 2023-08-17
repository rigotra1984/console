//package com.rigoberto.console.controllers;
//
//import com.rigoberto.console.dtos.CreateEventDto;
//import com.rigoberto.console.entities.Event;
//import com.rigoberto.console.mappers.EventMapper;
//import com.rigoberto.console.services.EventR2dbcService;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@RestController
//@RequestMapping("/api/event/r2dbc")
//public class EventR2dbcController {
//    private EventR2dbcService service;
//    private final EventMapper mapper;
//
//    public EventR2dbcController(EventR2dbcService service, EventMapper mapper) {
//        this.service = service;
//        this.mapper = mapper;
//    }
//
//    @GetMapping
//    public Flux<Event> getAll(){
//        return service.getAll();
//    }
//
//    @GetMapping("/{id}")
//    public Mono<Event> getById(@PathVariable Integer id ){
//        return service.getById(id);
//    }
//
//    @PostMapping
//    public Mono<Event> create(@RequestBody @Valid CreateEventDto dto){
//        return service.create(mapper.convertToEntity(dto));
//    }
//
//    @PutMapping("/{id}")
//    public Mono<ResponseEntity<Event>> update(@PathVariable Integer id, @RequestBody @Valid CreateEventDto dto){
//        return service.update(id, mapper.convertToEntity(dto))
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.badRequest().build());
//    }
//
//    @DeleteMapping("/{id}")
//    public Mono<ResponseEntity<Void>> delete(@PathVariable Integer id){
//        return service.delete(id)
//                .map( r -> ResponseEntity.ok().<Void>build())
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }
//
//}
