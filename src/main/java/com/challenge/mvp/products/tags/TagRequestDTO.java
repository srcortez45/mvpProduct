package com.challenge.mvp.products.tags;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDTO {
	private Integer id;
	@NotNull(message = "name is required")
	@NotBlank(message = "name cannot be blank")
    private String name;
}
