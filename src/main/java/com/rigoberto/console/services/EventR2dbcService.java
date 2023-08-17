//package com.rigoberto.console.services;
//
//import com.rigoberto.console.entities.Event;
//import com.rigoberto.console.repositories.EventR2dbcRepository;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Service
//public class EventR2dbcService {
//    private EventR2dbcRepository repository;
//
//    public EventR2dbcService(EventR2dbcRepository repository) {
//        this.repository = repository;
//    }
//
//    public Flux<Event> getAll(){
//        return repository.findAll();
//    }
//
//    public Mono<Event> getById(Integer id ){
//        return repository.findById(id);
//    }
//
//    public Mono<Event> create(Event event){
//        return repository.save(event);
//    }
//
//    public Mono<Event> update(Integer id,  Event event){
//        return repository.findById(id)
//                .flatMap(dbEvent -> {
//                    dbEvent.setPriority(event.getPriority());
//                    dbEvent.setDescription(event.getDescription());
//                    return repository.save(dbEvent);
//                });
//    }
//
//    public Mono<Event> delete(Integer id){
//        return repository.findById(id)
//                .flatMap(dbEvent -> repository.delete(dbEvent)
//                        .then(Mono.just(dbEvent)));
//    }
//}
