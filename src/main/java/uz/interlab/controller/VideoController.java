package uz.interlab.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class VideoController
{
    @GetMapping("/video/{file-name}")
    public ResponseEntity<byte[]> getVideo3(
            @RequestHeader HttpHeaders headers,
            @PathVariable("file-name") String fileName) throws IOException
    {
        ClassPathResource videoFile = new ClassPathResource("uploads/" + fileName);
        long fileSize = videoFile.contentLength();

        List<HttpRange> ranges = headers.getRange();
        if (ranges.isEmpty())
        {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize))
                    .body(StreamUtils.copyToByteArray(videoFile.getInputStream()));
        } else
        {
            HttpRange range = ranges.get(0);
            long start = range.getRangeStart(fileSize);
            long end = range.getRangeEnd(fileSize);
            long rangeLength = end - start + 1;

            InputStream inputStream = videoFile.getInputStream();
            inputStream.skip(start);

            byte[] buffer = new byte[(int) rangeLength];
            inputStream.read(buffer, 0, (int) rangeLength);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(rangeLength))
                    .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize)
                    .body(buffer);
        }
    }

}
