package org.uy.sdm.pasman.model.dto;

import java.time.LocalDateTime;

public record PostDto(String title, String content, LocalDateTime creationDate) {
}
