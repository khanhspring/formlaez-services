package com.formlaez.infrastructure.converter;

import com.formlaez.application.model.response.TeamMemberResponse;
import com.formlaez.application.model.response.TeamResponse;
import com.formlaez.application.model.response.form.FormFieldOptionResponse;
import com.formlaez.infrastructure.model.entity.form.JpaFormFieldOption;
import com.formlaez.infrastructure.model.entity.team.JpaTeam;
import com.formlaez.infrastructure.model.entity.team.JpaTeamMember;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeamMemberResponseConverter implements Converter<JpaTeamMember, TeamMemberResponse> {

    private final UserResponseConverter userResponseConverter;

    @Nullable
    @Override
    public TeamMemberResponse convert(@Nullable JpaTeamMember source) {
        if (Objects.isNull(source)) {
            return null;
        }
        var user = userResponseConverter.convert(source.getUser());
        return TeamMemberResponse.builder()
                .user(user)
                .role(source.getRole())
                .joinedDate(source.getCreatedDate())
                .build();
    }

    @Nullable
    public List<TeamMemberResponse> convert(@Nullable List<JpaTeamMember> source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return source.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
