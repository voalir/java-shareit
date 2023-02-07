package ru.practicum.shareit.item.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

class CommentMapperTest {

    @Test
    void toComment() {
        CommentDto commentDto = new CommentDto(1, "text", "user");
        Comment comment = CommentMapper.toComment(commentDto);
        Assertions.assertThat(comment)
                .hasFieldOrPropertyWithValue("text", commentDto.getText());
    }

    @Test
    void toCommentDto() {
        Comment comment = getComment();
        CommentDto commentDto = CommentMapper.toCommentDto(comment);
        Assertions.assertThat(commentDto)
                .hasFieldOrPropertyWithValue("text", comment.getText())
                .hasFieldOrPropertyWithValue("authorName", comment.getAuthor().getName());
    }

    Comment getComment() {
        Item item = new Item();
        item.setId(1);
        item.setName("name");

        User user = new User();
        user.setId(1);
        user.setName("user");

        Comment comment = new Comment();
        comment.setId(1);
        comment.setText("text");
        comment.setItem(item);
        comment.setAuthor(user);

        return comment;
    }
}