package com.ssafy.completionism.api.service.transaction;

import com.ssafy.completionism.api.controller.transaction.response.AIFeedBackResponse;
import com.ssafy.completionism.api.controller.transaction.response.FeedBackResponse;
import com.ssafy.completionism.domain.transaction.StatisticsType;

public interface AnalysisService {

    public AIFeedBackResponse getAIFeedBack(String loginId);

    public FeedBackResponse getFeedBack(String loginId, StatisticsType type);
}
