package com.example.news.mapper;

import com.example.news.model.Comment;
import com.example.news.web.model.CategoryRequest;
import com.example.news.web.model.CommentListResponse;
import com.example.news.web.model.CommentRequest;
import com.example.news.web.model.CommentResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface CommentMapper {
    Comment requestToComment(CommentRequest request);

    @Mapping(source = "commentId", target = "id")
    Comment requestToComment(Long commentId, CommentRequest request);

    CommentResponse commentToResponse(Comment comment);

    List<CommentResponse> commentListToResponseList(List<Comment> comments);

    default CommentListResponse commentListToComment(List<Comment> comments) {
        CommentListResponse response = new CommentListResponse();
        response.setComments(commentListToResponseList(comments));

        return response;
    }
}
