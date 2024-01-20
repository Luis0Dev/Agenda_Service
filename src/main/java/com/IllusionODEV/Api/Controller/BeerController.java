package com.IllusionODEV.Api.Controller;

import com.IllusionODEV.ClientBeer.Reponse.BeerResponse;
import com.IllusionODEV.Domain.Service.BeerService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/beer")
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/api/{id}")
    public List<BeerResponse> buscarPorId(@PathVariable Long id) {
        return beerService.getBeerById(id);
    }

    @GetMapping()
    public List<BeerResponse> getRandomBeer() {
        return beerService.getRandomBeer();
    }
}
