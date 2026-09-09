package ma.company.inner.socle.domain.dto;

import lombok.Builder;

@Builder
public record VilleResponse(String code,
                            String label,
                            Boolean active) {
}