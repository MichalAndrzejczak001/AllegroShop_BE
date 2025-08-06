package spring.project.allegroshop.service;

import reactor.core.publisher.Mono;

import java.util.List;

public interface IOfferService {
    public String searchOffers(String phrase, int limit, String sort);
}
