package uz.market.backend.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.market.backend.DTO.ProductDTO;
import uz.market.backend.DTO.XizmatDTO;
import uz.market.backend.domain.Xizmat;
import uz.market.backend.service.XizmatService;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class XizmatResource {

    private static final String UPLOADED_FOLDER = "C:\\Users\\asadb\\SuperProject\\clean-code\\uploads\\";

    private final XizmatService xizmatService;

    public XizmatResource(XizmatService xizmatService) {
        this.xizmatService = xizmatService;
    }
    @PostMapping("/xizmat/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public XizmatDTO addXizmat(@RequestParam ("name") @NotBlank String name,
                               @RequestParam ("description") @NotBlank String description,
                               @RequestParam("image") MultipartFile image) {
        String imageUrl = null;
        if (!image.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOADED_FOLDER);
                if (Files.notExists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                byte[] bytes = image.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + image.getOriginalFilename());
                Files.write(path, bytes);
                imageUrl = "C:\\Users\\asadb\\SuperProject\\clean-code\\img\\" + image.getOriginalFilename();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to upload image", e);
            }
        }
        XizmatDTO xizmatDTO = new XizmatDTO();
        xizmatDTO.setName(name);
        xizmatDTO.setDescription(description);
        xizmatDTO.setImageUrl(imageUrl);

        return xizmatService.addXizmat(xizmatDTO);
    }
    @PutMapping("/xizmat/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public XizmatDTO updateXizmat(@PathVariable Long id,
                                    @RequestParam("name") @NotBlank String name,
                                    @RequestParam("description") @NotBlank String description,
                                    @RequestParam(value = "image", required = false) MultipartFile image) {
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + image.getOriginalFilename());
                Files.write(path, bytes);
                imageUrl = "C:\\Users\\asadb\\SuperProject\\clean-code\\img\\" + image.getOriginalFilename();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to upload image");
            }
        }

        XizmatDTO xizmatDTO = new XizmatDTO();
        xizmatDTO.setId(id);
        xizmatDTO.setName(name);
        xizmatDTO.setDescription(description);
        if (imageUrl != null) {
            xizmatDTO.setImageUrl(imageUrl);
        }

        return xizmatService.updateXizmat(xizmatDTO);
    }

    @GetMapping("/xizmat/{id}")
    public XizmatDTO getXizmatById(@PathVariable Long id) {
        return xizmatService.getXizmatById(id);
    }

    @GetMapping("/xizmat/all")
    public ResponseEntity getAllXizmat() {
        return ResponseEntity.ok(xizmatService.findAllXizmat());
    }

    @DeleteMapping("/xizmat/delete/{id}")
    public ResponseEntity deleteXizmat(@PathVariable Long id){
        xizmatService.delete(id);
        return ResponseEntity.ok("Xizmat deleted successfully");
    }}
