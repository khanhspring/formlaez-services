package com.formlaez.infrastructure.repository.custom;

import com.formlaez.application.model.request.AdvanceSearchFormSubmissionRequest;
import com.formlaez.infrastructure.enumeration.FormSubmissionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaFormSubmissionRepositoryCustomImpl implements JpaFormSubmissionRepositoryCustom {

    private static final int KEY_LENGTH = 21;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final FormSubmissionSearchResultRowMapper resultRowMapper;

    @Override
    public Page<JpaFormSubmissionSearchResult> searchAdvance(AdvanceSearchFormSubmissionRequest request, Pageable pageable) {
        Map<String, Object> paramMap = new HashMap<>();

        StringBuilder sql = new StringBuilder(" from form_submission s" +
                " inner join form f on f.id = s.form_id" +
                " left join \"user\" c on c.id = s.created_by" +
                " left join \"user\" m on m.id = s.last_modified_by" +
                " where 1=1" +
                " and s.status = '" + FormSubmissionStatus.Active.name() + "'" +
                " and f.code = :formCode");
        paramMap.put("formCode", request.getFormCode());

        if (Objects.nonNull(request.getFromDate())) {
            sql.append(" and s.createdDate >= :fromDate");
            paramMap.put("fromDate", request.getFromDate());
        }
        if (Objects.nonNull(request.getToDate())) {
            sql.append(" and s.createdDate <= :toDate");
            paramMap.put("fromDate", request.getFromDate());
        }
        if (Objects.nonNull(request.keywordsToMap())) {
            var i = 0;
            for (var entry : request.keywordsToMap().entrySet()) {
                if (entry.getKey().length() == KEY_LENGTH && !ObjectUtils.isEmpty(entry.getValue())) {
                    sql.append(" and s.data->>'").append(entry.getKey()).append("' like :value").append(i);
                    paramMap.put("value" + i, "%" + entry.getValue() + "%");
                    i++;
                }
            }
        }

        String countSql = "select count(1) " + sql;
        Long count = jdbcTemplate.queryForObject(countSql, paramMap, Long.class);

        sql.insert(0,
                "select s.id as id, s.code as code, s.data as data," +
                        " s.created_date as createdDate," +
                        " s.last_modified_date as lastModifiedDate," +
                        " c.id as createdByUserId," +
                        " c.first_name as createdByUserFirstName," +
                        " c.last_name as createdByUserLastName," +
                        " c.email as createdByUserEmail," +
                        " m.id as lastModifiedByUserId," +
                        " m.first_name as lastModifiedByUserFirstName," +
                        " m.last_name as lastModifiedByUserLastName," +
                        " m.email as lastModifiedByUserEmail");

        if (pageable.getSort().isSorted()) {
            String orderBy = pageable.getSort()
                    .get()
                    .map(order -> order.getProperty() + " " + order.getDirection())
                    .collect(Collectors.joining(", "));

            sql.append(" order by ").append(orderBy);
        }

        sql.append(" limit :limit offset :offset");
        paramMap.put("limit", pageable.getPageSize());
        paramMap.put("offset", pageable.getOffset());

        var results = jdbcTemplate.query(sql.toString(), paramMap, resultRowMapper);

        return new PageImpl<>(results, pageable, Objects.requireNonNullElse(count, 0L));
    }
}