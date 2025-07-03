package socialbookstoreapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchStrategyFactory {
	@Autowired
	private ApproximateSearchStrategy approximateSearchStrategy;
	
	@Autowired
	private ExactSearchStrategy exactSearchStrategy;
	
	public SearchStrategy getSearchStrategy(String strategy) {
		switch (strategy) {
	        case "approximate":
	            return approximateSearchStrategy;
	        case "exact":
	            return exactSearchStrategy;
	        default:
	            throw new IllegalArgumentException("Invalid strategy method: " + strategy);
		}
	}
}
