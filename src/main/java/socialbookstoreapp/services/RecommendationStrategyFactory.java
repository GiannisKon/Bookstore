package socialbookstoreapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecommendationStrategyFactory {
	@Autowired
	private AuthorsRecommendationStrategy authorsRecommendationStrategy;
	
	@Autowired
	private CategoriesRecommendationStrategy categoriesRecommendationStrategy;
	
	@Autowired
	private CompositeRecommendationStrategy compositeRecommendationStrategy;
	
	public RecommendationStrategy getRecommendationStrategy(String strategy) {
		switch (strategy) {
	        case "authors":
	            return authorsRecommendationStrategy;
	        case "categories":
	            return categoriesRecommendationStrategy;
	        case "composite":
	            return compositeRecommendationStrategy;
	        default:
	            throw new IllegalArgumentException("Invalid strategy method: " + strategy);
		}
	}
}
