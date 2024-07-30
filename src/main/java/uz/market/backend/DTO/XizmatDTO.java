package uz.market.backend.DTO;

import uz.market.backend.domain.Xizmat;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class XizmatDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "ImageUrl is mandatory")
    private String imageUrl;

    public XizmatDTO() {
    }

    public XizmatDTO(Xizmat xizmat) {
        this.id = xizmat.getId();
        this.name = xizmat.getName();
        this.description = xizmat.getDescription();
        this.imageUrl = xizmat.getImageUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Name is mandatory") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is mandatory") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Description is mandatory") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description is mandatory") String description) {
        this.description = description;
    }

    public @NotBlank(message = "ImageUrl is mandatory") String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NotBlank(message = "ImageUrl is mandatory") String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
