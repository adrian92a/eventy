package pl.net.rogala.eventy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.net.rogala.eventy.entity.Comment;
import pl.net.rogala.eventy.entity.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private User commentator; //TODO: conversion to Dto
    private LocalDateTime added;
    private String body;
    private Long eventId;

    public Comment toEntity() {
        return new Comment(id, commentator, added, body, eventId);
    }
}
