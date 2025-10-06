package za.co.shyftit.capitectransactionaggregator.dtos;

import java.util.Map;

public record EntityPostDTO(Long id, Map<String, String> processingResult) {
}
