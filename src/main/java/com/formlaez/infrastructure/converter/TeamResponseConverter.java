package com.formlaez.infrastructure.converter;

import com.formlaez.application.model.response.TeamResponse;
import com.formlaez.infrastructure.model.entity.team.JpaTeam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TeamResponseConverter implements Converter<JpaTeam, TeamResponse> {

    private final TeamMemberResponseConverter teamMemberResponseConverter;

    @Nullable
    @Override
    public TeamResponse convert(@Nullable JpaTeam source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return TeamResponse.builder()
                .id(source.getId())
                .code(source.getCode())
                .description(source.getDescription())
                .name(source.getName())
                .createdDate(source.getCreatedDate())
                .lastModifiedDate(source.getLastModifiedDate())
                .members(teamMemberResponseConverter.convert(source.getMembers()))
                .build();
    }
}
