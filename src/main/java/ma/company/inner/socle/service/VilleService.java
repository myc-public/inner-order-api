package ma.company.inner.socle.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.company.inner.socle.domain.dto.VilleAddRequest;
import org.springframework.stereotype.Service;
import ma.company.inner.socle.domain.dto.VilleResponse;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VilleService {

    public List<VilleResponse> getAllVilleByPaysCode(String paysCode) {
        //log.info("Récuération de la liste des villes pour le code pays {}", paysCode);
//        return villeMapper.mapToVilleResponse(
//                villeRepository.findAllByPaysCode(paysCode));
        return null;
    }

    public VilleResponse addNewVille(@Valid VilleAddRequest villeAddRequest) {
        return null;
    }
}
