package com.formlaez.service.share.impl;

import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.model.entity.form.JpaFormSubmissionSnapshot;
import com.formlaez.infrastructure.repository.JpaFormSubmissionRepository;
import com.formlaez.infrastructure.repository.JpaFormSubmissionSnapshotRepository;
import com.formlaez.infrastructure.util.RandomUtils;
import com.formlaez.service.share.FormSubmissionSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FormSubmissionSnapshotServiceImpl implements FormSubmissionSnapshotService {

    private final JpaFormSubmissionRepository jpaFormSubmissionRepository;
    private final JpaFormSubmissionSnapshotRepository jpaFormSubmissionSnapshotRepository;

    @Override
    @Transactional
    public void takeSnapshot(Long submissionId) {
        var submission = jpaFormSubmissionRepository.findById(submissionId)
                .orElseThrow(InvalidParamsException::new);

        var submissionSnapshot = JpaFormSubmissionSnapshot.builder()
                .submission(submission)
                .data(submission.getData())
                .code(RandomUtils.randomNanoId())
                .form(submission.getForm())
                .build();

        jpaFormSubmissionSnapshotRepository.save(submissionSnapshot);
    }
}
