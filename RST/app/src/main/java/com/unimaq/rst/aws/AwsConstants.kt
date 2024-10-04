package com.unimaq.rst.aws

import com.amazonaws.regions.Regions

object AwsConstants {

    val COGNITO_IDENTITY_ID: String = "us-east-2:558f8fe5-bbd4-4b3c-a144-843015497702"
    val COGNITO_REGION: Regions = Regions.US_EAST_2 // Region
    val BUCKET_NAME: String = "unimaq-bucket"

    val S3_URL: String = "https://$BUCKET_NAME.s3.ap-south-1.amazonaws.com/"
    val folderPath = "images/"

}