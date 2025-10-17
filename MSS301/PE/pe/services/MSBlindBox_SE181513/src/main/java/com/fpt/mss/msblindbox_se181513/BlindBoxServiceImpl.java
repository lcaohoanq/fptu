package com.fpt.mss.msblindbox_se181513;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlindBoxServiceImpl  implements BlindBoxService {

    private final ProductRepository blindBoxRepository;

    @Override
    public List<BlindBox> getAll() {
        return blindBoxRepository.findAll();
    }
}
