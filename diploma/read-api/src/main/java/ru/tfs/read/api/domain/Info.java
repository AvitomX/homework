package ru.tfs.read.api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "title", "language"})
public class Info {
    private Long id;

    private String title;

    private String description;

    private Language language;
}
