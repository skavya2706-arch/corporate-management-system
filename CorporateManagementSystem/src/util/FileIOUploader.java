package util;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileIOUploader {

    public static String uploadFile(File file) {
        if (file == null || !file.exists()) {
            System.out.println("Error: Tender report file does not exist.");
            return null;
        }

        try {
            Path filePath = file.toPath();

            System.out.println("\n========================================");
            System.out.println("        STARTING FILE UPLOAD...         ");
            System.out.println("========================================\n");

            // ----------------------------------------------------
            // STEP 1: BUILD MULTIPART REQUEST BODY
            // ----------------------------------------------------
            String boundary = "---JavaBoundary" + System.currentTimeMillis();

            String header = "--" + boundary + "\r\n"
                    + "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n"
                    + "Content-Type: text/plain\r\n\r\n";
            String footer = "\r\n--" + boundary + "--\r\n";

            byte[] headerBytes = header.getBytes(StandardCharsets.UTF_8);
            byte[] fileBytes = Files.readAllBytes(filePath);
            byte[] footerBytes = footer.getBytes(StandardCharsets.UTF_8);

            // Combine bytes into a single request array
            byte[] body = new byte[headerBytes.length + fileBytes.length + footerBytes.length];
            System.arraycopy(headerBytes, 0, body, 0, headerBytes.length);
            System.arraycopy(fileBytes, 0, body, headerBytes.length, fileBytes.length);
            System.arraycopy(footerBytes, 0, body, headerBytes.length + fileBytes.length, footerBytes.length);

            // ----------------------------------------------------
            // STEP 2: SEND REQUEST TO TMPFILES.ORG ENDPOINT
            // ----------------------------------------------------
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://tmpfiles.org/api/v1/upload"))
                    .header("User-Agent", "Mozilla/5.0")
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // ----------------------------------------------------
            // STEP 3: PARSE RESPONSE & CREATE DIRECT DOWNLOAD LINK
            // ----------------------------------------------------
            if (response.statusCode() == 200) {
                String responseBody = response.body();

                // Extract link from JSON: {"data":{"url":"https://tmpfiles.org/12345/report.txt"}}
                int urlStart = responseBody.indexOf("\"url\":\"") + 7;
                int urlEnd = responseBody.indexOf("\"", urlStart);

                String rawUrl = responseBody.substring(urlStart, urlEnd);

                // Convert to direct download link: https://tmpfiles.org/dl/12345/report.txt
                String directLink = rawUrl.replace("tmpfiles.org/", "tmpfiles.org/dl/");

                System.out.println("========================================");
                System.out.println("      TENDER REPORT UPLOAD SUCCESS      ");
                System.out.println("========================================");
                System.out.println("Direct Download Link: " + directLink);
                System.out.println("========================================\n");

                return directLink;
            } else {
                System.out.println("Upload failed with HTTP Status: " + response.statusCode());
                System.out.println("Response Body: " + response.body());
            }

        } catch (Exception e) {
            System.out.println("Error processing tender file: " + e.getMessage());
        }

        return null;
    }
}