package com.example.demo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


//    http-anrop 127:0:0:1:8080/player
//          127:0:0:1:8080/player/1

@RestController
public class DemoController {

    @Autowired
	private PlayerRepository playerRepository;
    
    @GetMapping(path="/player")
    List<Player> getAll(){
        var l = new ArrayList<Player>();
        for(Player r : playerRepository.findAll())
        {
            l.add(r);
        }
        return l;
    }

    @GetMapping(path="/player/{id}")
    Player getSingle(@PathVariable Integer id){
        return playerRepository.findById(id).get();
    }


    @PutMapping(path="/player/{id}", consumes="application/json",  produces = "application/json")
    Player update(@PathVariable Integer id, @RequestBody Player updatedPlayer){
        Player dbPlayer =  playerRepository.findById(id).get();
        dbPlayer.setBorn(updatedPlayer.getBorn());            
        dbPlayer.setAge(updatedPlayer.getAge());            
        dbPlayer.setJersey(updatedPlayer.getJersey());            
        dbPlayer.setNamn(updatedPlayer.getNamn());
        playerRepository.save(dbPlayer);
        return dbPlayer;
    }



    @PostMapping(path="/player", consumes="application/json",  produces = "application/json")
    ResponseEntity<Object> add(@RequestBody Player p){
        playerRepository.save(p);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(p.getId())
        .toUri();
        return ResponseEntity.created(location).build();        
    }


}
