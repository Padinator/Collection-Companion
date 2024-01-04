package de.collectioncompanion.ComposerMS.data_files;

import de.collectioncompanion.ComposerMS.adapter.outbound.OPExecCmd;
import de.collectioncompanion.ComposerMS.adapter.outbound.RestServerOut;
import de.collectioncompanion.ComposerMS.ports.data_files.WebCrawler;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Getter
public class WebCrawlerImpl implements WebCrawler {

    private final String hostname, containerName, urlToWebCrawler;
    private boolean isRunning = true;

    public WebCrawlerImpl(String hostname, String containerName) {
        this.hostname = hostname;
        this.containerName = containerName;
        urlToWebCrawler = "http://" + hostname + ":8080/" + RestServerOut.STARTING;
    }

    @Override
    public String toString() {
        return "Hostname: " + hostname + ", Container-Name: " + containerName + ", URL to Webcrawler: " + urlToWebCrawler;
    }

    @Override
    public void stop() {
        if (isRunning) {
            while (getPendingRequests() > 0) // Wait until all requests are done
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            OPExecCmd.execCMD("docker stop " + containerName);
            isRunning = false;
        }
    }

    @Override
    public int getPendingRequests() {
        RestTemplate restTemplate = new RestTemplate();
        String workloadURL = urlToWebCrawler + "/workload";
        ResponseEntity<Integer> workload = restTemplate.getForEntity(workloadURL, Integer.class);

        if (workload.getBody() != null && workload.getStatusCode().value() == 200)
            return workload.getBody();
        return -2; // Stop web crawler or at least remove from list and return 0
    }

}
