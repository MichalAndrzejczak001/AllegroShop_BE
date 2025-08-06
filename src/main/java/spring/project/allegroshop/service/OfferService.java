package spring.project.allegroshop.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import spring.project.allegroshop.model.ProductDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfferService implements IOfferService {
    private final WebClient webClient;

    private static final String BEARER_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2FsbGVncm8ucGwuYWxsZWdyb3NhbmRib3gucGwiLCJleHAiOjE3NTQ1Mjk2NzQsImNsaWVudF9pZCI6IjA0NTU2MGM3MDViYTQ4MzViNDQ2ZWM5MDdjMzU0OTU5IiwianRpIjoiNjE5YmM0ZDQtNTkwNS00ZTBiLTgxNjUtM2ZkNDk3MDYzMDU4Iiwic2NvcGUiOlsiYWxsZWdybzphcGk6b3JkZXJzOnJlYWQiLCJhbGxlZ3JvOmFwaTpmdWxmaWxsbWVudDpyZWFkIiwiYWxsZWdybzphcGk6cHJvZmlsZTp3cml0ZSIsImFsbGVncm86YXBpOnNhbGU6b2ZmZXJzOndyaXRlIiwiYWxsZWdybzphcGk6ZnVsZmlsbG1lbnQ6d3JpdGUiLCJhbGxlZ3JvOmFwaTpiaWxsaW5nOnJlYWQiLCJhbGxlZ3JvOmFwaTpjYW1wYWlnbnMiLCJhbGxlZ3JvOmFwaTpkaXNwdXRlcyIsImFsbGVncm86YXBpOmFmZmlsaWF0ZTp3cml0ZSIsImFsbGVncm86YXBpOnNhbGU6b2ZmZXJzOnJlYWQiLCJhbGxlZ3JvOmFwaTpiaWRzIiwiYWxsZWdybzphcGk6c2hpcG1lbnRzOndyaXRlIiwiYWxsZWdybzphcGk6b3JkZXJzOndyaXRlIiwiYWxsZWdybzphcGk6YWRzIiwiYWxsZWdybzphcGk6cGF5bWVudHM6d3JpdGUiLCJhbGxlZ3JvOmFwaTpzYWxlOnNldHRpbmdzOndyaXRlIiwiYWxsZWdybzphcGk6cHJvZmlsZTpyZWFkIiwiYWxsZWdybzphcGk6cmF0aW5ncyIsImFsbGVncm86YXBpOmFmZmlsaWF0ZTpyZWFkIiwiYWxsZWdybzphcGk6c2FsZTpzZXR0aW5nczpyZWFkIiwiYWxsZWdybzphcGk6cGF5bWVudHM6cmVhZCIsImFsbGVncm86YXBpOnNoaXBtZW50czpyZWFkIiwiYWxsZWdybzphcGk6bWVzc2FnaW5nIl0sImFsbGVncm9fYXBpIjp0cnVlfQ.I0Z7W5iLfOKwg1rIB7h78cc10_ut-mh4vctZssdA5Rx3na_imyYBeEERLfbl4SiFXxsx9Rxci2uPG-pqYLSYx1pIphap38Tmiyg3Hvd6QxXB_HnT581w3btCePsJnjuqLeuuAl0zklcUAyTV6tFqxtAyN02W2nEnH_3UCxJdvACfwjkG8nV_zitSGdgh_DJ0r-tjAdteQWmSWNYuj-FHGsCbFmkZeTYaZGtXe-V9ZaCPxEvtViyTi3eXpfO3zMom4P5aeXCdPC4_iHZ1IdzqW_zmQ9tGTqCNwkPKElR0oMtJhoiPxo_oJYQhNc8-KjqjJkMHKGEMUWPuUVOlB99nfkSe0S7q1S6HJ_k_kGMU0k1DBJq-7X1GtWizbfQkCjBzyoJdbVTZwfRd4Z884G8HYx2UznXNMXU7s_ydMwD2ZnCedXZPYnhmkXywP-7G8J2UjQvl-yXlLr_Zu6bVfnqb9qwzu8rsRXAgcNXqBIswxnVBRojrAmmWrSifKrw3YbOyqyKnhPWk3w4V3BjYkbpNzzNGiDf4AuEviaeZFQl_GFpOCIBrxhlXRg514Xtfe9_v6loBJpf3RznjlG8NDEoYnSdTD6111grXKRRcnxKpK0TgsTAppl4DNO8jERourb-Ea47hXaHwFX9CpowmmPsadK6e5RGl0bchgTbSiO5dUME";

    public OfferService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.allegro.pl.allegrosandbox.pl")
                .defaultHeader(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.allegro.public.v1+json")
                .build();
    }

    @Override
    public List<ProductDto> searchOffers(String phrase, int limit, String sort) {
        Mono<String> responseMono =  webClient.get()
               .uri(uriBuilder -> uriBuilder
                        .path("/offers/listing")
                        .queryParam("phrase", phrase)
                        .queryParam("limit", limit)
                        .queryParam("sort", sort)
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        String jsonString = responseMono.block();

        JSONObject json = new JSONObject(jsonString);
        JSONArray items = json.getJSONObject("items").getJSONArray("regular");

        String result = "";
        List<ProductDto> productDtos = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);

            String id = item.optString("id");

            String name = item.optString("name");

            String imageUrl = "";
            JSONArray images = item.optJSONArray("images");
            if (images != null && images.length() > 0) {
                imageUrl = images.getJSONObject(0).optString("url");
            }

            JSONObject priceObj = item.optJSONObject("sellingMode").optJSONObject("price");
            String price = priceObj != null ? priceObj.optString("amount") : "";

            productDtos.add(new ProductDto(id, name, imageUrl, price));

        }
        System.out.println(productDtos);
        return productDtos;
    }
}
