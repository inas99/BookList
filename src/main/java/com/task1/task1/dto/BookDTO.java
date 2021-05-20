package com.task1.task1.dto;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString()
@NoArgsConstructor

public class BookDTO {
    private String author;

    private String price;

    private String genre;

    private String description;

    private String id;

    private String title;

    private String publish_date;

    private boolean borrowed;

    private String borrowedBy;
}
