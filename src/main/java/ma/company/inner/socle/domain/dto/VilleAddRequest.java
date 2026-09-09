package ma.company.inner.socle.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import ma.company.inner.socle.tool.constants.ErrorConstants;

@Builder
public record VilleAddRequest(
        @NotBlank(message = ErrorConstants.ERR_CODE_VILLE_NOTBLANK) String code,
        String label,
        Boolean active
) {
}
