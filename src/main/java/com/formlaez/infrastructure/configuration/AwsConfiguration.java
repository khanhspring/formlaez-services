package com.formlaez.infrastructure.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.formlaez.infrastructure.property.aws.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AwsConfiguration {

    @Bean
    public AmazonS3 s3Client(AwsProperties awsProperties) {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentials(awsProperties))
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build();
    }

    @Bean
    public AmazonSimpleEmailService sesService(AwsProperties awsProperties) {
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(awsCredentials(awsProperties))
                .withRegion(Regions.AP_SOUTHEAST_1).build();
    }

    private AWSStaticCredentialsProvider awsCredentials(AwsProperties awsProperties) {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(
                awsProperties.getCredentials().getAccessKey(),
                awsProperties.getCredentials().getSecretKey()
        );
        return new AWSStaticCredentialsProvider(basicAWSCredentials);
    }
}
