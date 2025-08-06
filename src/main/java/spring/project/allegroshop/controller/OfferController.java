package spring.project.allegroshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import spring.project.allegroshop.model.ProductDto;
import spring.project.allegroshop.service.IOfferService;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController
{
    private final IOfferService offerService;

    @Autowired
    public OfferController(IOfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchOffers(
            @RequestParam(defaultValue = "buty") String phrase,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "+price") String sort
    ) {
        return new ResponseEntity<>(offerService.searchOffers(phrase, limit, sort), HttpStatus.OK);
    }
}
