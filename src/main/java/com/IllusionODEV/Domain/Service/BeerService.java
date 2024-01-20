package com.IllusionODEV.Domain.Service;

import com.IllusionODEV.ClientBeer.BeerClient;
import com.IllusionODEV.ClientBeer.Reponse.BeerResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BeerService {

    private  BeerClient beerClient;

    public List<BeerResponse> getRandomBeer() {
        return beerClient.getRandomBeer();
    }

    public List<BeerResponse> getBeerById(Long id) {
        return beerClient.getBeerById(id);
    }
}
