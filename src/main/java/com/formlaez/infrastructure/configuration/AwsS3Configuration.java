package com.formlaez.infrastructure.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.formlaez.infrastructure.property.AwsS3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AwsS3Configuration {

    @Bean
    public AmazonS3 getAmazonS3Client(AwsS3Properties awsS3Properties) {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(
                awsS3Properties.getCredentials().getAccessKey(),
                awsS3Properties.getCredentials().getSecretKey()
        );
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build();
    }
}
