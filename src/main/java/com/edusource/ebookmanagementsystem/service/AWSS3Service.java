package com.edusource.ebookmanagementsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.edusource.ebookmanagementsystem.exception.OurException;

import java.io.InputStream;
//

@Service
public class AWSS3Service {

    private final String bucketName = "edusource-book-images"; // Updated bucket name

    @Value("${aws.s3.access.key}")
    private String accessKey;

    @Value("${aws.s3.secret.key}")
    private String secretKey;

    public String saveBookFileToS3(MultipartFile bookFile, String fileType) {
        String s3Location = null;

        try {
            String s3Filename = bookFile.getOriginalFilename();

            String directory = fileType.equals("image") ? "images/" : "pdfs/";
            s3Filename = directory + s3Filename;

            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1)
                    .build();

            InputStream inputStream = bookFile.getInputStream();

            ObjectMetadata metadata = new ObjectMetadata();
            if (fileType.equals("image")) {
                metadata.setContentType("image/jpeg");
            } else if (fileType.equals("pdf")) {
                metadata.setContentType("application/pdf");
            }

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Filename, inputStream, metadata);
            s3Client.putObject(putObjectRequest);

            return "https://" + bucketName + ".s3.amazonaws.com/" + s3Filename;

        } catch (Exception e) {
            e.printStackTrace();
            throw new OurException("Unable to upload file to S3 bucket: " + e.getMessage());
        }
    }
//    public void deleteFileFromS3(String fileUrl) {
//        try {
//            // Extract file key from URL
//            String fileKey = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
//
//            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
//            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                    .withRegion(Regions.US_EAST_1)
//                    .build();
//
//            // Delete the file
//            s3Client.deleteObject(bucketName, fileKey);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new OurException("Unable to delete file from S3 bucket: " + e.getMessage());
//        }
//    }
public void deleteFileFromS3(String fileUrl) {
    try {
        // Extract file key from URL
        String fileKey = fileUrl.replace("https://" + bucketName + ".s3.amazonaws.com/", "");

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.US_EAST_1)
                .build();

        // Delete the file
        s3Client.deleteObject(bucketName, fileKey);

    } catch (Exception e) {
        e.printStackTrace();
        throw new OurException("Unable to delete file from S3 bucket: " + e.getMessage());
    }
}

}


//@Service
//public class AWSS3Service {
//
//    private final String bucketName="edusource-book-images";
//
//    @Value("${aws.s3.access.key}")
//    private String accessKey;
//
//    @Value("${aws.s3.secret.key}")
//    private String secretKey;
//
//    public String saveImageToS3(MultipartFile photo) {
//        String s3LocationImage = null;
//
//        try {
//
//            String s3Filename = photo.getOriginalFilename();
//
//            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
//            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                    .withRegion(Regions.US_EAST_1)
//                    .build();
//
//            InputStream inputStream = photo.getInputStream();
//
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType("image/jpeg");
//
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Filename, inputStream, metadata);
//            s3Client.putObject(putObjectRequest);
//            return "https://" + bucketName + ".s3.amazonaws.com/" + s3Filename;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new OurException("Unable to upload image to s3 bucket" + e.getMessage());
//        }
//    }
//}
