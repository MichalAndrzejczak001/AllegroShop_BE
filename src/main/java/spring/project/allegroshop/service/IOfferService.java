package spring.project.allegroshop.service;

import reactor.core.publisher.Mono;
import spring.project.allegroshop.model.ProductDto;

import java.util.List;

public interface IOfferService {
    public List<ProductDto> searchOffers(String phrase, int limit, String sort);
}
