package com.linkedin.api.twilio;

import com.linkedin.api.azure.SentimentAnalysisResponse;
import com.linkedin.api.azure.TextAnalyticsRequest;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface AzureSentimentClient {

    /**
     * SpringBoot Open Feign Project
     * @param apiKey
     * @param requestBody
     * @return SentimentAnalysisResponse
     */
    @RequestLine("POST /text/analytics/v3.0/sentiment")
    @Headers({"Content-Type:application/json","Ocp-Apim-Subscription-Key:{apiKey}"})
    public SentimentAnalysisResponse analyze(@Param("apiKey") String apiKey, TextAnalyticsRequest requestBody);

}
