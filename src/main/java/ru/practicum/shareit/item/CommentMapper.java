package ru.practicum.shareit.item;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoResponse;


@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface CommentMapper {

    CommentMapper MAP_COMMENT = Mappers.getMapper(CommentMapper.class);

    Comment toComment(CommentDto dto);

    @Mapping(target = "authorName", source = "user.name")
    CommentDtoResponse toDtoResponse(Comment comment);

//    @Mapping(target = "authorName", source = "user.name")
//    List<CommentDto> toDto(Iterable<Comment> Comment);
}