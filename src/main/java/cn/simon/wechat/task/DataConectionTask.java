package cn.simon.wechat.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import cn.simon.wechat.service.AccessTokenService;

@Component
public class DataConectionTask {

	private static final Logger logger = LoggerFactory.getLogger(DataConectionTask.class);

	@Autowired
	private AccessTokenService accessTokenService;

	@Scheduled(fixedRate = 1000 * 60 * 60)
	public void doTask() {
		logger.info("start task");
		accessTokenService.getAccessToken();
		logger.info("end task");
	}
}
