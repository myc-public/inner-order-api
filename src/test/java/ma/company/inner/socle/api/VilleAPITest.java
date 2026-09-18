package ma.company.inner.socle.api;

import ma.company.inner.socle.domain.dto.VilleAddRequest;
import ma.company.inner.socle.domain.dto.VilleResponse;
import ma.company.inner.socle.service.VilleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VilleAPI.class)
class VilleAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VilleService villeService;

    @Test
    void getVille_shouldReturnFixedCity_whenCityCodeIsValid() throws Exception {
        mockMvc.perform(get(VilleAPI.BASE_URL + "/{cityCode}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("Ra"))
                .andExpect(jsonPath("$.label").value("Rabat"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void getVille_shouldReturnBadRequest_whenCityCodeIsLessThanOne() throws Exception {
        mockMvc.perform(get(VilleAPI.BASE_URL + "/{cityCode}", 0))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllVilles_shouldReturnServiceResult_whenPaysCodeIsProvided() throws Exception {
        List<VilleResponse> villes = List.of(
                new VilleResponse("Ra", "Rabat", true),
                new VilleResponse("Ca", "Casablanca", true));
        given(villeService.getAllVilleByPaysCode("MA")).willReturn(villes);

        mockMvc.perform(get(VilleAPI.BASE_URL).param("pays_code", "MA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].code").value("Ra"))
                .andExpect(jsonPath("$[1].code").value("Ca"));

        verify(villeService).getAllVilleByPaysCode("MA");
    }

    @Test
    void getAllVilles_shouldReturnBadRequest_whenPaysCodeIsMissing() throws Exception {
        mockMvc.perform(get(VilleAPI.BASE_URL))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(villeService);
    }

    @Test
    void getAllVilles_shouldReturnBadRequest_whenPaysCodeIsEmpty() throws Exception {
        mockMvc.perform(get(VilleAPI.BASE_URL).param("pays_code", ""))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(villeService);
    }

    @Test
    void addNewVille_shouldReturnServiceResult_whenRequestIsValid() throws Exception {
        VilleAddRequest request = new VilleAddRequest("Ra", "Rabat", true);
        VilleResponse response = new VilleResponse("Ra", "Rabat", true);
        given(villeService.addNewVille(request)).willReturn(response);

        mockMvc.perform(post(VilleAPI.BASE_URL)
                        .contentType("application/json")
                        .content("""
                                {"code":"Ra","label":"Rabat","active":true}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("Ra"))
                .andExpect(jsonPath("$.label").value("Rabat"))
                .andExpect(jsonPath("$.active").value(true));

        verify(villeService).addNewVille(request);
    }

    @Test
    void addNewVille_shouldReturnBadRequest_whenCodeIsBlank() throws Exception {
        mockMvc.perform(post(VilleAPI.BASE_URL)
                        .contentType("application/json")
                        .content("""
                                {"code":"","label":"Rabat","active":true}
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(villeService);
    }
}
