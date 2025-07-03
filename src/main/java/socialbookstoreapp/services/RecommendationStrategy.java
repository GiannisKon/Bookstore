package socialbookstoreapp.services;

import java.util.List;

import org.springframework.stereotype.Component;

import socialbookstoreapp.formsdata.BookFormData;
import socialbookstoreapp.formsdata.RecommendationFormData;

@Component
public interface RecommendationStrategy {

	public List<BookFormData> recommend(String username, RecommendationFormData recommendationDTO);
}