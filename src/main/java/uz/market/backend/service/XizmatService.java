package uz.market.backend.service;

import org.springframework.stereotype.Service;
import uz.market.backend.DTO.ProductDTO;
import uz.market.backend.DTO.XizmatDTO;
import uz.market.backend.domain.Xizmat;
import uz.market.backend.domain.product.Product;
import uz.market.backend.repository.XizmatRepository;
import uz.market.backend.security.ResourceNotFoundException;

import java.util.List;

@Service
public class XizmatService {
    private final XizmatRepository xizmatRepository;

    public XizmatService(XizmatRepository xizmatRepository) {
        this.xizmatRepository = xizmatRepository;
    }
    public Xizmat save(Xizmat xizmat) {
        return xizmatRepository.save(xizmat);
    }

    public XizmatDTO addXizmat(XizmatDTO xizmatDTO) {
        Xizmat xizmat = new Xizmat();
        xizmat.setName(xizmatDTO.getName());
        xizmat.setDescription(xizmatDTO.getDescription());
        xizmat.setImageUrl(xizmatDTO.getImageUrl());

        xizmatRepository.save(xizmat);

        xizmatDTO.setId(xizmat.getId());
        return xizmatDTO;
    }

    public XizmatDTO updateXizmat(XizmatDTO xizmatDTO) {
        Xizmat xizmat = xizmatRepository.findById(xizmatDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Xizmat not found with id " + xizmatDTO.getId()));

        xizmat.setName(xizmatDTO.getName());
        xizmat.setDescription(xizmatDTO.getDescription());

        if (xizmatDTO.getImageUrl() != null) {
            xizmat.setImageUrl(xizmatDTO.getImageUrl());
        }

        xizmatRepository.save(xizmat);
        return new XizmatDTO(xizmat);
    }

    public XizmatDTO getXizmatById(Long id) {
        Xizmat xizmat = xizmatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xizmat not found with id " + id));
        return new XizmatDTO(xizmat);
    }

    public List<Xizmat> findAllXizmat(){
        return xizmatRepository.findAll();
    }

    public void delete(Long id){
        xizmatRepository.deleteById(id);
    }
}
