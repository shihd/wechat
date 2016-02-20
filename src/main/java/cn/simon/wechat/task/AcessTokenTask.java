package cn.simon.wechat.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AcessTokenTask {

	@Scheduled(fixedRate = 10 * 1000)
	public void handle() {
		// System.out.println(new Date().getTime());
	}
}
