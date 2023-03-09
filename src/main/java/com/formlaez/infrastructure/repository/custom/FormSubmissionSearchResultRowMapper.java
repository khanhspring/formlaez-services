package com.formlaez.infrastructure.repository.custom;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formlaez.infrastructure.model.entity.JpaUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FormSubmissionSearchResultRowMapper implements RowMapper<JpaFormSubmissionSearchResult> {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public JpaFormSubmissionSearchResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        JpaUser createdBy = null;
        if (rs.getString("createdByUserId") != null) {
            createdBy = JpaUser.builder()
                    .id(UUID.fromString(rs.getString("createdByUserId")))
                    .firstName(rs.getString("createdByUserFirstName"))
                    .lastName(rs.getString("createdByUserLastName"))
                    .email(rs.getString("createdByUserEmail"))
                    .build();
        }

        JpaUser lastModifiedBy = null;
        if (rs.getString("lastModifiedByUserId") != null) {
            lastModifiedBy = JpaUser.builder()
                    .id(UUID.fromString(rs.getString("lastModifiedByUserId")))
                    .firstName(rs.getString("lastModifiedByUserFirstName"))
                    .lastName(rs.getString("lastModifiedByUserLastName"))
                    .email(rs.getString("lastModifiedByUserEmail"))
                    .build();
        }

        var dataObj = rs.getObject("data");
        JsonNode data = null;
        if (Objects.nonNull(dataObj)) {
            data = objectMapper.readTree(dataObj.toString());
        }

        var createdDate = rs.getTimestamp("createdDate");
        Instant createDateInstant = null;
        if (Objects.nonNull(createdDate)) {
            createDateInstant = createdDate.toInstant();
        }

        Instant lastModifiedDateInstant = null;
        var lastModifiedDate = rs.getTimestamp("lastModifiedDate");
        if (Objects.nonNull(lastModifiedDate)) {
            lastModifiedDateInstant = lastModifiedDate.toInstant();
        }

        return JpaFormSubmissionSearchResult.builder()
                .id(rs.getLong("id"))
                .code(rs.getString("code"))
                .data(data)
                .createdDate(createDateInstant)
                .lastModifiedDate(lastModifiedDateInstant)
                .createdBy(createdBy)
                .lastModifiedBy(lastModifiedBy)
                .build();
    }
}
