package ma.company.inner.socle.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import ma.company.inner.socle.domain.dto.VilleAddRequest;
import ma.company.inner.socle.domain.dto.VilleResponse;
import ma.company.inner.socle.service.VilleService;
import ma.company.inner.socle.tool.constants.ErrorConstants;
import ma.company.inner.socle.tool.constants.GlobalConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = GlobalConstants.SOCLE_APIS_TAG)
@RestController
@RequestMapping(VilleAPI.BASE_URL)
public class VilleAPI {

    public static final String BASE_URL = "/v1/geo/villes";

    private final VilleService villeService;

    public VilleAPI(VilleService villeService) {
        this.villeService = villeService;
    }


    @Operation(summary = "Retreive a city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "a city", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VilleResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "city not found", content = @Content) })
    @GetMapping(value = "/{cityCode}", produces = "application/json")
    public VilleResponse  getVille(
            @PathVariable @Min(value = 1, message = ErrorConstants.ERR_CODE_PAYS_NOTEMPTY)
            int cityCode
    ) {
        return new VilleResponse("Ra", "Rabat", true);
    }

    @Operation(summary = "Retreive all Villes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List villes", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VilleResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Villes not found", content = @Content) })
    @GetMapping(produces = { "application/json" })
    public ResponseEntity<List<VilleResponse>> getAllVilles(
            @NotEmpty(message = ErrorConstants.ERR_CODE_PAYS_NOTEMPTY) @RequestParam(value = "pays_code", required = true) String paysCode) {
        return new ResponseEntity<>(
                villeService.getAllVilleByPaysCode(paysCode), HttpStatus.OK);
    }


    @Operation(summary = "Add new Ville")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville added", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VilleResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content) })
    @PostMapping(produces = { "application/json" })
    public ResponseEntity<VilleResponse> addNewVille(@Valid @RequestBody VilleAddRequest villeAddRequest) {
        return new ResponseEntity<>(villeService.addNewVille(villeAddRequest), HttpStatus.OK);
    }

}
